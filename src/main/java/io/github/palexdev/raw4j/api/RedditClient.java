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

/**
 * This is the main class of this framework. RedditClient is responsible for authenticating the app/script,
 * logging in and accessing APIs
 * <p></p>
 * There are two ways to obtain a RedditClient instance:
 * <p> 1) Via static method {@link #login(OAuthParameters)} (preferred way)
 * <p> 2) Via constructor {@link #RedditClient(OAuthFlow)}
 *<p></p>
 * <pre>First method example
 * {@code
 * // Build the OAuthParams object
 * OAuthParameters parameters = new OAuthParameters.AuthCodeFlowBuilder()
 *         .setUserAgent(INSTALLED_USER_AGENT)
 *         .setClientID(INSTALLED_CLIENT_ID)
 *         .setRedirectURI(redirectURI)
 *         .setScopes(Scopes.all())
 *         .build(LoginType.INSTALLED_APP);
 *
 * // Obtain the RedditClient instance
 * RedditClient client = RedditClient.login(parameters);
 * }
 * </pre>
 *
 * <pre>Second method example
 * {@code
 * // Build the OAuthParams object
 * OAuthParameters parameters = new OAuthParameters.AuthCodeFlowBuilder()
 *         .setUserAgent(INSTALLED_USER_AGENT)
 *         .setClientID(INSTALLED_CLIENT_ID)
 *         .setRedirectURI(redirectURI)
 *         .setScopes(Scopes.all())
 *         .build(LoginType.INSTALLED_APP);
 *
 * // Obtain an instance of OAuthFlow (depends on the app type you need, let's suppose a web app, logged user)
 * OAuthFlow authManager = new OAuthAuthorizationCodeFlow.Builder().from(parameters);
 *
 * // Obtain the RedditClient instance
 * RedditClient client = new RedditClient(authManager).login();
 * }
 * </pre>
 * <p></p>
 * NOTE: this should be implied but... It's not recommended having more than one client at the same time
 */
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
        apiWrapper = new RedditApiWrapper(this);
    }

    //================================================================================
    // Methods
    //================================================================================

    /**
     * From the given parameters builds the appropriate {@link OAuthFlow}, builds a RedditClient instance
     * and then calls {@link #login()}.
     */
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

    /**
     * Responsible for authenticating the client, calls {@link OAuthFlow#authenticate()}.
     * <p>
     * If the authentication process went well returns the RedditClient instance otherwise returns null.
     */
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

    /**
     * @return an instance of {@link RedditApiWrapper} to access the various APIs
     */
    public RedditApiWrapper api() {
        return apiWrapper;
    }

    /**
     * @return an instance {@link OAuthData}
     */
    public OAuthData getAuthData() {
        return authManager.getAuthData();
    }

    /**
     * @return an instance of {@link OAuthInfo}
     */
    public OAuthInfo getAuthInfo() {
        return authManager.getAuthInfo();
    }

    /**
     * @return the list of specified scopes
     */
    public List<Scopes> getScopes() {
        return getAuthInfo().getScopes();
    }

    OAuthFlow getAuthManager() {
        return authManager;
    }

    //================================================================================
    // Delegate Methods
    //================================================================================

    /**
     * @return an instance of {@link AccountApi} to use Account APIs
     */
    public AccountApi accountApi() {
        return apiWrapper.accountApi();
    }

    /**
     * @return an instance of {@link UserApi} to use User APIs
     */
    public UserApi userApi() {
        return apiWrapper.userApi();
    }
}
