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

import io.github.palexdev.raw4j.data.User;
import io.github.palexdev.raw4j.enums.ApiEndpoints;
import io.github.palexdev.raw4j.json.GsonInstance;
import io.github.palexdev.raw4j.oauth.base.OAuthFlow;

/**
 * This class contains all methods to interact with User APIs.
 */
public class UserApi {
    //================================================================================
    // Properties
    //================================================================================
    private final OAuthFlow authManager;

    //================================================================================
    // Constructors
    //================================================================================
    UserApi(OAuthFlow authManager) {
        this.authManager = authManager;
    }

    //================================================================================
    // API Implementation
    //================================================================================

    /**
     * Retrieves the {@link User} data structure for the given username.
     * <p></p>
     * Before returning checks if the user exists, in case it doesn't, returns null.
     */
    public User getUser(String username) {
        String url = ApiEndpoints.USER.toStringRaw().formatted(username);
        User user = GsonInstance.gson().fromJson(authManager.get(url), User.class);
        return userExists(user) ? user : null;
    }

    /**
     * Checks if the given {@link User} exists.
     */
    public boolean userExists(User user) {
        return user.getUsername() != null;
    }

    /**
     * Checks if the given username exists.
     * <p></p>
     * This works by retrieving the {@link User} data structure for the given username and
     * then checking its properties.
     */
    public boolean userExists(String username) {
        User user = getUser(username);
        return userExists(user);
    }
}
