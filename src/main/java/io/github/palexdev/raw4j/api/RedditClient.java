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

import io.github.palexdev.raw4j.enums.Scopes;
import io.github.palexdev.raw4j.exception.OAuthException;
import io.github.palexdev.raw4j.oauth.*;
import io.github.palexdev.raw4j.oauth.base.OAuthFlow;

import java.util.List;

public class RedditClient {
    //================================================================================
    // Properties
    //================================================================================
    private final RedditApiWrapper apiWrapper;
    private final OAuthFlow authManager;

    //================================================================================
    // Constructors
    //================================================================================
    public RedditClient(OAuthFlow authManager) {
        this.authManager = authManager;
        apiWrapper = new RedditApiWrapper(authManager);
    }

    //================================================================================
    // Methods
    //================================================================================
    public static RedditClient login(OAuthParameters parameters) {
        OAuthFlow authManager;
        RedditClient redditClient;

        switch (parameters.getLoginType()) {
            case USERLESS_INSTALLED, USERLESS_WEB -> {
                authManager = new OAuthAppOnlyFlow.Builder().from(parameters);
                redditClient = new RedditClient(authManager);
                return redditClient.login();
            }
            case INSTALLED_APP, WEB_APP -> {
                authManager = new OAuthAuthorizationCodeFlow.Builder().from(parameters);
                redditClient = new RedditClient(authManager);
                return redditClient.login();
            }
            case SCRIPT -> {
                authManager = new OAuthScriptFlow.Builder().from(parameters);
                redditClient = new RedditClient(authManager);
                return redditClient.login();
            }
        }
        return null;
    }

    public RedditClient login() {
        try {
            authManager.authenticate();
        } catch (OAuthException ex) {
            ex.printStackTrace();
            return null;
        }
        return this;
    }

    //================================================================================
    // Getters
    //================================================================================
    public RedditApiWrapper api() {
        return apiWrapper;
    }

    public OAuthData getAuthData() {
        return authManager.getAuthData();
    }

    public OAuthInfo getAuthInfo() {
        return authManager.getAuthInfo();
    }

    public List<Scopes> getScopes() {
        return getAuthInfo().getScopes();
    }
}
