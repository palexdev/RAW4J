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
import io.github.palexdev.raw4j.exception.OAuthException;
import io.github.palexdev.raw4j.json.GsonInstance;
import io.github.palexdev.raw4j.oauth.OAuthData;
import io.github.palexdev.raw4j.oauth.OAuthInfo;
import io.github.palexdev.raw4j.oauth.OAuthParameters;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Locale;

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
    private int expireSecondsOffset = 180;

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
    @Override
    public JsonObject get(String url) {
        JsonObject jsonObject = null;
        try {
            if (!authInfo.isValid()) {
                refreshToken();
            }

            Request request = new Request.Builder()
                    .header("User-Agent", parameters.getUserAgent())
                    .header("Authorization", "Bearer " + authInfo.getAccessToken())
                    .header("Accept-Language", Locale.US.getLanguage())
                    .url(url)
                    .build();
            Call call = getClient.newCall(request);
            Response response = call.execute();
            jsonObject = GsonInstance.gson().fromJson(response.body().string(), JsonObject.class);
        } catch (IOException ex) {
            logger.error("Request failed for: [" + url + "], exception was:");
            ex.printStackTrace();
        }
        return jsonObject;
    }

    @Override
    public JsonObject post(String url, RequestBody requestBody) {
        JsonObject jsonObject = null;
        try {
            Request request = new Request.Builder()
                    .url(url)
                    .post(requestBody)
                    .build();
            Call call = postClient.newCall(request);
            Response response = call.execute();
            jsonObject = GsonInstance.gson().fromJson(response.body().string(), JsonObject.class);
        } catch (IOException ex) {
            logger.error("Request failed for: [" + url + "], exception was:");
            ex.printStackTrace();
        }
        return jsonObject;
    }

    //================================================================================
    // Getters, Setters
    //================================================================================
    protected int getExpireSecondsOffset() {
        return expireSecondsOffset;
    }

    protected void setExpireSecondsOffset(int expireSecondsOffset) {
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

    protected void setParameters(OAuthParameters parameters) {
        this.parameters = parameters;
    }

    //================================================================================
    // Builders
    //================================================================================
    protected abstract static class AbstractBuilder {
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
