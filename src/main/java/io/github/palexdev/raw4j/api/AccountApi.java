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

package io.github.palexdev.raw4j.api;

import io.github.palexdev.raw4j.data.AccountData;
import io.github.palexdev.raw4j.enums.ApiEndpoints;
import io.github.palexdev.raw4j.json.GsonInstance;
import io.github.palexdev.raw4j.oauth.base.OAuthFlow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AccountApi {
    //================================================================================
    // Properties
    //================================================================================
    private static final Logger logger = LoggerFactory.getLogger(RedditApiWrapper.class.getSimpleName());
    private final OAuthFlow authManager;
    private AccountData loggedUser;

    //================================================================================
    // Constructors
    //================================================================================
    AccountApi(OAuthFlow authManager) {
        this.authManager = authManager;
    }

    //================================================================================
    // API Implementation
    //================================================================================
    public AccountData getLoggedUser() {
        if (loggedUser != null) {
            return loggedUser;
        }

        if (!authManager.getAuthData().isValidUsername()) {
            AccountData accountData = getMe();
            authManager.getAuthData().setUsername(accountData.getName());
        }

        String url = ApiEndpoints.USER.toStringRaw().formatted(authManager.getAuthData().getUsername());
        loggedUser =  GsonInstance.gson().fromJson(authManager.get(url), AccountData.class);
        return loggedUser;
    }

    public AccountData getMe() {
        String url = ApiEndpoints.ME.toStringRaw();
        return GsonInstance.gson().fromJson(authManager.get(url), AccountData.class);
    }

    public AccountApi refreshLoggedUser() {
        String url = ApiEndpoints.USER.toStringRaw().formatted(authManager.getAuthData().getUsername());
        loggedUser =  GsonInstance.gson().fromJson(authManager.get(url), AccountData.class);
        return this;
    }
}
