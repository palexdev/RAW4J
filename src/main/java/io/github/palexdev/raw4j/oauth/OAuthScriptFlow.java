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
import io.github.palexdev.raw4j.enums.URLEnum;
import io.github.palexdev.raw4j.exception.OAuthException;
import io.github.palexdev.raw4j.json.GsonInstance;
import io.github.palexdev.raw4j.oauth.base.AbstractOAuthFlow;
import io.github.palexdev.raw4j.oauth.base.OAuthFlow;
import okhttp3.FormBody;
import okhttp3.RequestBody;

import java.time.Instant;

public class OAuthScriptFlow extends AbstractOAuthFlow {

    //================================================================================
    // Constructors
    //================================================================================
    protected OAuthScriptFlow() {}

    //================================================================================
    // Override Methods
    //================================================================================
    @Override
    public void authenticate() throws OAuthException {
        retrieveAccessToken();
    }

    @Override
    protected void retrieveAccessToken() throws OAuthException {
        logger.debug("Retrieving token...");
        RequestBody requestBody = new FormBody.Builder()
                .add("grant_type", "password")
                .add("username", authData.getUsername())
                .add("password", authData.getPassword())
                .build();

        JsonObject response = post(URLEnum.OAUTH_TOKEN_URL.toString(), requestBody);
        authInfo = GsonInstance.gson().fromJson(response, OAuthInfo.class);
        if (response.toString().contains("invalid_grant")) {
            throw new OAuthException("Response was: \n" + GsonInstance.gson().toJson(response) + "\nPlease check that the parameters are correct");
        }
        authInfo.setExpireTime(Instant.now().getEpochSecond() + authInfo.getExpiresIn() - getExpireSecondsOffset());
        logger.debug("Token Retrieved");
    }

    @Override
    protected void refreshToken() {
        logger.debug("Refreshing token...");
        try {
            retrieveAccessToken();
        } catch (OAuthException ex) {
            logger.error("Failed to refresh the token, cause was:\n" + ex.getMessage());
        }
        logger.debug("Token refreshed");
    }

    @Override
    protected void revokeToken(boolean isAccessToken) {
        logger.debug("Revoking token...");
        String token = authInfo.getAccessToken();
        if (token == null || token.isBlank()) {
            return;
        }

        RequestBody requestBody = new FormBody.Builder()
                .add("token", token)
                .add("token_type_hint","access_token")
                .build();

        post(URLEnum.REVOKE_TOKEN_URL.toString(), requestBody);
        authInfo.revoke(true);
        logger.debug("Token revoked");
    }

    //================================================================================
    // Builders
    //================================================================================
    public static class Builder extends AbstractBuilder {
        private final OAuthScriptFlow authManager;

        public Builder() {
            authManager = new OAuthScriptFlow();
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
