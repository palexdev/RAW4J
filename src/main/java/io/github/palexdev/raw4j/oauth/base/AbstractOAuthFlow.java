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

package io.github.palexdev.raw4j.oauth.base;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import io.github.palexdev.raw4j.exception.OAuthException;
import io.github.palexdev.raw4j.json.GsonInstance;
import io.github.palexdev.raw4j.oauth.OAuthData;
import io.github.palexdev.raw4j.oauth.OAuthInfo;
import io.github.palexdev.raw4j.oauth.OAuthParameters;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * This is the base class of all OAuth flows, implements {@link OAuthFlow}.
 * <p></p>
 * This class defines two separate {@link OkHttpClient}s, one for GET requests and one for POST/PATCH requests.
 * <p></p>
 * This class also keeps a reference for: {@link OAuthInfo}, {@link OAuthData} and {@link OAuthParameters}.
 * <p></p>
 * On Reddit the access token expire after 1h, to be sure a request is sent with an invalid token we make the
 * token expire after 57min (3min less, can be changed).
 * <p></p>
 * Defines three abstract methods to: retrieve the access token, refresh the token, revoke a token.
 */
public abstract class AbstractOAuthFlow implements OAuthFlow {
    //================================================================================
    // Properties
    //================================================================================
    protected static final Logger logger = LoggerFactory.getLogger(OAuthFlow.class.getSimpleName());
    protected OkHttpClient getClient;
    protected OkHttpClient postClient;

    protected final OAuthData authData;
    protected OAuthInfo authInfo;
    protected OAuthParameters parameters;
    private long expireSecondsOffset = 180;

    //================================================================================
    // Constructors
    //================================================================================
    public AbstractOAuthFlow() {
        authData = new OAuthData();

        getClient = buildClient(null);
        postClient = buildClient((route, response) -> {
            String username = authData.getClientID();
            String password = authData.getClientSecret() == null ? "" : authData.getClientSecret();
            String credential = Credentials.basic(username, password);
            return response.request().newBuilder().header("Authorization", credential).build();
        });
    }

    //================================================================================
    // Abstract Methods
    //================================================================================
    protected abstract void retrieveAccessToken() throws OAuthException;
    protected abstract void refreshToken();
    protected abstract void revokeToken(boolean isAccessToken);

    //================================================================================
    // Methods
    //================================================================================

    /**
     * Utility method to build an {@link OkHttpClient} instance with the given authenticator
     * and an interceptor that specifies the app User-Agent in the headers.
     */
    protected OkHttpClient buildClient(Authenticator authenticator) {
        Interceptor interceptor = chain -> chain.proceed(
                chain.request()
                        .newBuilder()
                        .addHeader("User-Agent", parameters.getUserAgent())
                        .build()
        );
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .addNetworkInterceptor(interceptor);

        if (authenticator != null) {
            builder.authenticator(authenticator);
        }
        return builder.build();
    }

    //================================================================================
    // Override Methods
    //================================================================================
    @SuppressWarnings("ConstantConditions")
    @Override
    public JsonObject get(String url) {
        JsonObject object = null;
        String responseBody = "";
        try {
            if (!authInfo.isValid()) {
                refreshToken();
            }

            Request request = new Request.Builder()
                    .header("User-Agent", parameters.getUserAgent())
                    .header("Authorization", "Bearer " + authInfo.getAccessToken())
                    .url(url)
                    .build();
            Call call = getClient.newCall(request);
            Response response = call.execute();
            responseBody = response.body().string();
            object = GsonInstance.gson().fromJson(responseBody, JsonObject.class);
        } catch (IOException ex) {
            logger.error("GET failed for: [" + url + "]");
            logger.error("Body was: \n" + responseBody);
            logger.error("Exception was: ");
            ex.printStackTrace();
        }
        return object;
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public JsonObject patch(String url, RequestBody requestBody) {
        JsonObject object = null;
        String responseBody = "";
        try {
            if (!authInfo.isValid()) {
                refreshToken();
            }

            Request request = new Request.Builder()
                    .header("User-Agent", parameters.getUserAgent())
                    .header("Authorization", "Bearer " + authInfo.getAccessToken())
                    .url(url)
                    .patch(requestBody)
                    .build();
            Call call = postClient.newCall(request);
            Response response = call.execute();
            responseBody = response.body().string();
            object = GsonInstance.gson().fromJson(responseBody, JsonObject.class);
        } catch (JsonSyntaxException | IOException ex) {
            logger.error("PATCH failed for: [" + url + "]");
            logger.error("Body was: \n" + responseBody);
            logger.error("Exception was: ");
            ex.printStackTrace();
        }
        return object;
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public JsonObject post(String url, RequestBody requestBody) {
        JsonObject object = null;
        String responseBody = "";
        try {
            Request request = new Request.Builder()
                    .url(url)
                    .post(requestBody)
                    .build();
            Call call = postClient.newCall(request);
            Response response = call.execute();
            responseBody = response.body().string();
            object = GsonInstance.gson().fromJson(responseBody, JsonObject.class);
        } catch (IOException ex) {
            logger.error("POST failed for: [" + url + "]");
            logger.error("Body was: \n" + responseBody);
            logger.error("Exception was: ");
            ex.printStackTrace();
        }
        return object;
    }

    //================================================================================
    // Getters, Setters
    //================================================================================

    /**
     * The seconds to subtract from the real token expire time, by default 180s (token expires in 60min - 3min = 57min).
     */
    protected long getExpireSecondsOffset() {
        return expireSecondsOffset;
    }

    /**
     * Sets the seconds to subtract from the real token expire time.
     */
    protected void setExpireSecondsOffset(long expireSecondsOffset) {
        this.expireSecondsOffset = Math.abs(expireSecondsOffset);
    }

    @Override
    public OAuthInfo getAuthInfo() {
        return authInfo;
    }

    @Override
    public OAuthData getAuthData() {
        return authData;
    }

    @Override
    public OAuthParameters getParameters() {
        return parameters;
    }

    /**
     * Since we rely on builder classes and no-arg constructor to build an OAuthFlow instance,
     * this is needed to set the authentication parameters specified by the user.
     */
    protected void setParameters(OAuthParameters parameters) {
        this.parameters = parameters;
    }

    //================================================================================
    // Builders
    //================================================================================

    /**
     * Abstract base Builder for all OAuthFlows extending this class.
     */
    protected abstract static class AbstractBuilder {

        /**
         * Sets the {@link OAuthData} properties of this flow from the given parameters,
         * this should be done in the {@link #from(OAuthParameters)} method.
         */
        protected void setAuthDataFrom(OAuthParameters parameters) {
            getOAuthManager().getAuthData().setClientID(parameters.getClientID());
            getOAuthManager().getAuthData().setClientSecret(parameters.getClientSecret());
            getOAuthManager().getAuthData().setUsername(parameters.getUsername());
            getOAuthManager().getAuthData().setPassword(parameters.getPassword());
        }

        public abstract AbstractOAuthFlow from(OAuthParameters parameters);
        protected abstract OAuthFlow getOAuthManager();
    }
}
