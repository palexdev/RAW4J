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

import io.github.palexdev.raw4j.oauth.base.OAuthFlow;

/**
 * This is just a wrapper class that contain all the instances to use the various APIs.
 */
public class RedditApiWrapper {
    //================================================================================
    // Properties
    //================================================================================
    private final OAuthFlow authManager;
    private final AccountApi accountApi;
    private final UserApi userApi;

    //================================================================================
    // Constructors
    //================================================================================
    RedditApiWrapper(RedditClient client) {
        this.authManager = client.getAuthManager();
        accountApi = new AccountApi(this);
        userApi = new UserApi(this);
    }

    //================================================================================
    // APIs
    //================================================================================

    /**
     * @return an instance of {@link AccountApi} to use Account APIs
     */
    public AccountApi accountApi() {
        return accountApi;
    }

    /**
     * @return an instance of {@link UserApi} to use User APIs
     */
    public UserApi userApi() {
        return userApi;
    }

    //================================================================================
    // Getters
    //================================================================================
    OAuthFlow getAuthManager() {
        return authManager;
    }
}
