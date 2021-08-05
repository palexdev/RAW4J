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
import java.util.UUID;

public class OAuthAppOnlyFlow extends OAuthAuthorizationCodeFlow {

    //================================================================================
    // Constructors
    //================================================================================
    protected OAuthAppOnlyFlow() {}

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
                .add("grant_type", URLEnum.OAUTH_APP_ONLY_URL.toString())
                .add("device_id", generateDeviceID())
                .build();

        String url = URLEnum.OAUTH_TOKEN_URL + (parameters.isPermanent() ? "?duration=permanent" : "");
        JsonObject response = post(url, requestBody);
        authInfo = GsonInstance.gson().fromJson(response, OAuthInfo.class);
        authInfo.setExpireTime(Instant.now().getEpochSecond() + authInfo.getExpiresIn() - getExpireSecondsOffset());
        if (!authInfo.isValid()) {
            throw new OAuthException("Invalid AuthInfo, check that the specified client parameters are correct");
        }
        authInfo.setPermanent(parameters.isPermanent());
        authInfo.setScopes(parameters.getScopes());
        logger.debug("Token retrieved");
    }

    //================================================================================
    // Methods
    //================================================================================
    private String generateDeviceID() {
        return UUID.randomUUID().toString();
    }

    //================================================================================
    // Builders
    //================================================================================
    public static class Builder extends AbstractBuilder {
        private final OAuthAppOnlyFlow authManager;

        public Builder() {
            authManager = new OAuthAppOnlyFlow();
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
