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
import io.github.palexdev.raw4j.enums.Urls;
import io.github.palexdev.raw4j.exception.OAuthException;
import io.github.palexdev.raw4j.json.GsonInstance;
import io.github.palexdev.raw4j.utils.StringUtils;
import okhttp3.FormBody;
import okhttp3.RequestBody;

import java.awt.*;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.Instant;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class InstalledAppOAuth extends AbstractOAuthManager {
    //================================================================================
    // Properties
    //================================================================================
    private HttpServer httpServer;
    private String randomState;
    private String authCode;
    private URL redirectURI;
    private Path authFileStore;

    // TODO implement constructor with arguments
    protected InstalledAppOAuth() {

    }

    //================================================================================
    // Override Methods
    //================================================================================
    @Override
    public void authenticate() throws IOException {
        if (authFileStore == null) {
            startServer();
            authorizeApp();
        }

        try {
            authInfo = tryLoadStoredAuth();
        } catch (OAuthException ex) {
            ex.printStackTrace();
            startServer();
            authorizeApp();
            return;
        }


        if (authInfo.isValid()) {
            System.out.println("Valid OAuthInfo Loaded");
        } else {
            refreshToken();
            storeAuth();
            System.out.println("Token Refreshed");
            System.out.println(GsonInstance.gson().toJson(authInfo));
        }
    }

    //================================================================================
    // Methods
    //================================================================================
    private void startServer() throws IOException {
        final ExecutorService executor = Executors.newSingleThreadExecutor(runnable -> {
            Thread thread = Executors.defaultThreadFactory().newThread(runnable);
            thread.setName("Authorization Server");
            thread.setDaemon(true);
            return thread;
        });

        if (httpServer != null) {
            httpServer.stop(0);
        }

        httpServer = HttpServer.create(new InetSocketAddress(redirectURI.getHost(), redirectURI.getPort()), 0);
        httpServer.createContext(redirectURI.getPath(), new OAuthHttpHandler(executor, httpServer));

        httpServer.setExecutor(executor);
        httpServer.start();
        System.out.println("HTTP server started");
    }

    private void authorizeApp() throws IOException {
        if (scopes.isEmpty()) {
            throw new IllegalStateException("No scope set");
        }

        randomState = StringUtils.randomString(10);

        String authUrl = String.format(
                Urls.OAUTH_AUTH_URL +
                        "?client_id=%s&" +
                        "response_type=%s&" +
                        "state=%s&" +
                        "redirect_uri=%s&" +
                        "scope=%s&" +
                        "duration=%s",
                authData.getClientID(),
                "code",
                randomState,
                redirectURI,
                Scopes.from(scopes),
                "permanent"
        );

        Desktop.getDesktop().browse(URI.create(authUrl));
    }

    private OAuthInfo tryLoadStoredAuth() throws OAuthException {
        try {
            FileReader reader = new FileReader(authFileStore.toFile());
            authInfo = GsonInstance.gson().fromJson(reader, OAuthInfo.class);
            return authInfo;
        } catch (IOException ex) {
            // TODO replace with logging
            throw new OAuthException("Error reading token from specified store file. Fallback to full authorization");
        }
    }

    @Override
    protected void retrieveAccessToken() {
        RequestBody requestBody = new FormBody.Builder()
                .add("grant_type", "authorization_code")
                .add("code", authCode)
                .add("redirect_uri", redirectURI.toString())
                .build();

        JsonObject response = post(Urls.OAUTH_TOKEN_URL.toString(), requestBody);
        authInfo = GsonInstance.gson().fromJson(response, OAuthInfo.class);
        authInfo.setExpireTime(Instant.now().getEpochSecond() + authInfo.getExpiresIn() - 10);
    }

    @Override
    protected void refreshToken() {
        String refreshToken = authInfo.getRefreshToken();
        RequestBody requestBody = new FormBody.Builder()
                .add("grant_type", "refresh_token")
                .add("refresh_token", refreshToken)
                .build();

        JsonObject response = post(Urls.OAUTH_TOKEN_URL.toString(), requestBody);
        authInfo = GsonInstance.gson().fromJson(response, OAuthInfo.class);
        authInfo.setExpireTime(Instant.now().getEpochSecond() + authInfo.getExpiresIn() - 10);
        authInfo.setRefreshToken(refreshToken);
    }

    private void proceed(String authCode) throws IOException {
        this.authCode = authCode;
        retrieveAccessToken();
        storeAuth();
    }

    protected void storeAuth() throws IOException {
        if (authFileStore == null) {
            return;
        }

        String toWrite = GsonInstance.gson().toJson(authInfo);
        if (!Files.exists(authFileStore)) {
            Files.createFile(authFileStore);
        }
        Files.writeString(authFileStore, toWrite, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    }

    //================================================================================
    // Getters, Setters
    //================================================================================
    public void setRedirectURI(URL redirectURI) {
        this.redirectURI = redirectURI;
    }

    public void setFileStore(Path fileStore) {
        this.authFileStore = fileStore;
    }

    //================================================================================
    // Nested Classes
    //================================================================================
    private class OAuthHttpHandler implements HttpHandler {
        private final ExecutorService executor;
        private final HttpServer httpServer;

        public OAuthHttpHandler(ExecutorService executor, HttpServer httpServer) {
            this.executor = executor;
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

            executor.submit(() -> {
                httpServer.stop(0);
                System.out.println("HTTP server stopper");
            });

            try {
                checkAccessGranted(httpExchange.getRequestURI().getQuery());
                proceed(retrieveCode(httpExchange.getRequestURI().getQuery()));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        private void checkAccessGranted(String query) {
            if (query.contains("access_denied")) {
                throw new OAuthException("Access was denied");
            }
        }

        private String retrieveCode(String query) {
            return query.substring(query.indexOf("code=")).replace("code=", "");
        }
    }

    public static class Builder extends AbstractBuilder {
        private final InstalledAppOAuth authManager;

        public Builder() {
            authManager = new InstalledAppOAuth();
        }

        public Builder setUserAgent(String userAgent) {
            authManager.setUserAgent(userAgent);
            return this;
        }

       public Builder setUsername(String username) {
            authManager.authData.setUsername(username);
            return this;
        }

        public Builder setClientID(String clientID) {
            authManager.authData.setClientID(clientID);
            return this;
        }

        public Builder setRedirectURI(URL redirectURI) {
            authManager.setRedirectURI(redirectURI);
            return this;
        }

        public Builder setFileStore(Path fileStore) {
            authManager.setFileStore(fileStore);
            return this;
        }

        @Override
        public Builder setScopes(List<Scopes> scopes) {
            authManager.setScopes(scopes);
            return this;
        }

        @Override
        public AbstractOAuthManager build() {
            checkArguments();
            return authManager;
        }

        private void checkArguments() {
            StringBuilder sb = new StringBuilder();
            sb.append(checkString(authManager.userAgent, "User-Agent, "));
            sb.append(checkString(authManager.authData.getClientID(), "ClientID, "));
            sb.append(checkString(authManager.redirectURI.toString(), "Redirect URI, "));
            if (authManager.scopes == null || authManager.scopes.isEmpty()) {
                sb.append("Scopes, ");
            }

            if (!sb.toString().isEmpty()) {
                String s = StringUtils.replaceLast(sb.toString(), ",", "");
                throw new IllegalStateException(s + "not set");
            }
        }

        private String checkString(String s, String returnS) {
            return (s == null || s.isBlank()) ? returnS : "";
        }
    }
}
