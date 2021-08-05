/*
 * Copyright (C) 2021 Parisi Alessandro
 * This file is part of RAW4J (https://github.com/palexdev/RAW4J).
 *
 * RAW4J is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * RAW4J is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with RAW4J.  If not, see <http://www.gnu.org/licenses/>.
 */

package io.github.palexdev.raw4j.oauth;

import com.google.gson.JsonObject;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import io.github.palexdev.raw4j.enums.Scopes;
import io.github.palexdev.raw4j.enums.URLEnum;
import io.github.palexdev.raw4j.exception.OAuthException;
import io.github.palexdev.raw4j.json.GsonInstance;
import io.github.palexdev.raw4j.oauth.base.AbstractOAuthFlow;
import io.github.palexdev.raw4j.oauth.base.OAuthFlow;
import io.github.palexdev.raw4j.utils.StringUtils;
import okhttp3.FormBody;
import okhttp3.RequestBody;

import java.awt.*;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URL;
import java.time.Instant;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.function.Consumer;

public class OAuthAuthorizationCodeFlow extends AbstractOAuthFlow {
    //================================================================================
    // Properties
    //================================================================================
    private final CountDownLatch countDownLatch = new CountDownLatch(1);
    private HttpServer httpServer;
    private String authCode;
    private String randomState;
    private String serverQuery;

    //================================================================================
    // Constructors
    //================================================================================
    protected OAuthAuthorizationCodeFlow() {}

    //================================================================================
    // Override Methods
    //================================================================================
    @Override
    public void authenticate() throws OAuthException {
        try {
            authInfo = loadAuthInfo();
            if (authInfo != null && authInfo.getRefreshToken() != null) {
                refreshToken();
                storeAuthInfo();
                return;
            }

            logger.debug("Refresh token was invalid, starting full authentication...");
            startServer();
            authorizeApp();
            checkAccessGranted(serverQuery);
            this.authCode = retrieveCode(serverQuery);
            retrieveAccessToken();
            storeAuthInfo();
        } catch (Exception ex) {
            throw new OAuthException("Authentication failed, cause was: \n" + ex.getMessage());
        }
    }

    @Override
    protected void retrieveAccessToken() throws OAuthException {
        logger.debug("Retrieving token...");
        RequestBody requestBody = new FormBody.Builder()
                .add("grant_type", "authorization_code")
                .add("code", authCode)
                .add("redirect_uri", parameters.getRedirectURI().toString())
                .build();

        JsonObject response = post(URLEnum.OAUTH_TOKEN_URL.toString(), requestBody);
        authInfo = GsonInstance.gson().fromJson(response, OAuthInfo.class);
        authInfo.setExpireTime(Instant.now().getEpochSecond() + authInfo.getExpiresIn() - getExpireSecondsOffset());
        if (!authInfo.isValid()) {
            throw new OAuthException("Invalid AuthInfo, check that the specified client parameters are correct");
        }
        authInfo.setPermanent(parameters.isPermanent());
        authInfo.setScopes(parameters.getScopes());
        logger.debug("Token retrieved");
    }

    @Override
    protected void refreshToken() {
        logger.debug("Refreshing token...");
        String refreshToken = authInfo.getRefreshToken();
        if (refreshToken == null || refreshToken.isBlank()) {
            authInfo.revoke(true);
            logger.error("Cannot refresh as it was not permanent, as a consequence the expired token has been revoked");
            return;
        }

        RequestBody requestBody = new FormBody.Builder()
                .add("grant_type", "refresh_token")
                .add("refresh_token", refreshToken)
                .build();

        JsonObject response = post(URLEnum.OAUTH_TOKEN_URL.toString(), requestBody);
        authInfo = GsonInstance.gson().fromJson(response, OAuthInfo.class);
        authInfo.setExpireTime(Instant.now().getEpochSecond() + authInfo.getExpiresIn() - getExpireSecondsOffset());
        authInfo.setRefreshToken(refreshToken);
        authInfo.setPermanent(parameters.isPermanent());
        authInfo.setScopes(parameters.getScopes());
        logger.debug("Token refreshed");
    }

    @Override
    protected void revokeToken(boolean isAccessToken) {
        logger.debug("Revoking token...");
        String token = isAccessToken ? authInfo.getAccessToken() : authInfo.getRefreshToken();
        if (token == null || token.isBlank()) {
            return;
        }

        RequestBody requestBody = new FormBody.Builder()
                .add("token", token)
                .add("token_type_hint", isAccessToken ? "access_token" : "refresh_token")
                .build();

        post(URLEnum.REVOKE_TOKEN_URL.toString(), requestBody);
        authInfo.revoke(isAccessToken);
        logger.debug("Token revoked");
    }

    //================================================================================
    // Methods
    //================================================================================
    private void startServer() throws IOException {
        if (httpServer != null) {
            httpServer.stop(0);
        }

        URL redirect = parameters.getRedirectURI();
        httpServer = HttpServer.create(new InetSocketAddress(redirect.getHost(), redirect.getPort()), 0);
        httpServer.createContext(redirect.getPath(), new OAuthHttpHandler(httpServer));
        httpServer.start();
        logger.debug("Authorization Server started");
    }

    private void authorizeApp() throws IOException {
        randomState = StringUtils.randomString(10);

        String authUrl = String.format(
                URLEnum.OAUTH_AUTH_URL +
                        "?client_id=%s&" +
                        "response_type=%s&" +
                        "state=%s&" +
                        "redirect_uri=%s&" +
                        "scope=%s&" +
                        "duration=%s",
                authData.getClientID(),
                "code",
                randomState,
                parameters.getRedirectURI(),
                Scopes.from(parameters.getScopes()),
                parameters.isPermanent() ? "permanent" : "temporary"
        );

        Desktop.getDesktop().browse(URI.create(authUrl));
        try {
            logger.trace("CDL Await");
            countDownLatch.await();
        } catch (InterruptedException ex) {
            logger.error("Authentication failed, cause was:\n" + ex.getMessage());
        }
    }

    protected void storeAuthInfo() {
        Consumer<OAuthInfo> storeAction = parameters.getStoreAction();
        if (storeAction == null) {
            return;
        }
        storeAction.accept(authInfo);
        logger.debug("Auth Info stored");
    }

    protected OAuthInfo loadAuthInfo() {
        Callable<OAuthInfo> loadAction = parameters.getLoadAction();
        if (loadAction == null) {
            return null;
        }

        OAuthInfo authInfo = null;
        try {
            authInfo = loadAction.call();
            logger.debug("Auth Info loaded");
        } catch (Exception ex) {
            logger.warn("Failed to read TokenFileStore because: " + ex.getMessage());
        }
        return authInfo;
    }

    private void checkAccessGranted(String query) throws OAuthException {
        if (query.contains("error") || !query.contains(randomState)) {
            throw new OAuthException("Authorization error, query was: " + "\"" + query + "\"" + ", and sent state was: " + "\"" + randomState + "\"");
        }
    }

    private String retrieveCode(String query) {
        return query.substring(query.indexOf("code=")).replace("code=", "");
    }

    //================================================================================
    // Nested Classes
    //================================================================================
    private class OAuthHttpHandler implements HttpHandler {
        private final HttpServer httpServer;

        public OAuthHttpHandler(HttpServer httpServer) {
            this.httpServer = httpServer;
        }

        @Override
        public void handle(HttpExchange httpExchange) {
            try {
                String response = "You can close this page now";
                httpExchange.sendResponseHeaders(200, response.length());
                OutputStream os = httpExchange.getResponseBody();
                os.write(response.getBytes());
                os.flush();
                os.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            String query = httpExchange.getRequestURI().getQuery();
            if (query != null) {
                serverQuery = httpExchange.getRequestURI().getQuery();
                httpServer.stop(0);
                countDownLatch.countDown();
                logger.trace("CDL Countdown");
                logger.debug("Authorization Server stopped");
            }
        }
    }

    //================================================================================
    // Builders
    //================================================================================
    public static class Builder extends AbstractBuilder {
        private final OAuthAuthorizationCodeFlow authManager;

        public Builder() {
            authManager = new OAuthAuthorizationCodeFlow();
        }

        @Override
        protected OAuthFlow getOAuthManager() {
            return authManager;
        }

        @Override
        public AbstractOAuthFlow from(OAuthParameters parameters) {
            setAuthDataFrom(parameters);
            authManager.setParameters(parameters);
            return authManager;
        }
    }
}
