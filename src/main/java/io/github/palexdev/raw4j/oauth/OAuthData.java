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

import java.io.Serializable;

/**
 * This class contains all the info related to the user and the app such as:
 * <p> - username
 * <p> - password (only for {@link OAuthScriptFlow}
 * <p> - clientID
 * <p> - clientSecret (only for Web Apps and Scripts)
 */
public class OAuthData implements Serializable {
    //================================================================================
    // Properties
    //================================================================================
    private String username;
    private String password;
    private String clientID;
    private String clientSecret;

    //================================================================================
    // Getters, Setters
    //================================================================================
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getClientID() {
        return clientID;
    }

    public void setClientID(String clientID) {
        this.clientID = clientID;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public boolean isValidUsername() {
        return username != null && !username.isBlank();
    }
}
