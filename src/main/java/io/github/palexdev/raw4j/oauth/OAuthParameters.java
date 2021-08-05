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

import io.github.palexdev.raw4j.enums.LoginType;
import io.github.palexdev.raw4j.enums.Scopes;
import io.github.palexdev.raw4j.utils.ClientUtils;

import java.net.URL;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.function.Consumer;

public class OAuthParameters {
    //================================================================================
    // Properties
    //================================================================================
    private LoginType loginType;
    private String userAgent;
    private String clientID;
    private String clientSecret;
    private URL redirectURI;
    private String username;
    private String password;
    private boolean permanent = true;
    private List<Scopes> scopes;
    private Callable<OAuthInfo> loadAction;
    private Consumer<OAuthInfo> storeAction;

    //================================================================================
    // Constructors
    //================================================================================
    private OAuthParameters() {
    }

    //================================================================================
    // Getters
    //================================================================================
    public LoginType getLoginType() {
        return loginType;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public String getClientID() {
        return clientID;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public URL getRedirectURI() {
        return redirectURI;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public boolean isPermanent() {
        return permanent;
    }

    public List<Scopes> getScopes() {
        return scopes;
    }

    public Consumer<OAuthInfo> getStoreAction() {
        return storeAction;
    }

    public Callable<OAuthInfo> getLoadAction() {
        return loadAction;
    }

    //================================================================================
    // Builders
    //================================================================================
    public static class AppOnlyFlowBuilder {
        private final OAuthParameters parameters;

        public AppOnlyFlowBuilder() {
            parameters = new OAuthParameters();
        }

        public AppOnlyFlowBuilder setUserAgent(String userAgent) {
            parameters.userAgent = userAgent;
            return this;
        }

        public AppOnlyFlowBuilder setClientID(String clientID) {
            parameters.clientID = clientID;
            return this;
        }

        public AppOnlyFlowBuilder setClientSecret(String clientSecret) {
            parameters.clientSecret = clientSecret;
            return this;
        }

        public AppOnlyFlowBuilder setPermanent(boolean permanent) {
            parameters.permanent = permanent;
            return this;
        }

        public AppOnlyFlowBuilder setScopes(List<Scopes> scopes) {
            parameters.scopes = scopes;
            return this;
        }

        public AppOnlyFlowBuilder setLoadAction(Callable<OAuthInfo> loadAction) {
            parameters.loadAction = loadAction;
            return this;
        }

        public AppOnlyFlowBuilder setStoreAction(Consumer<OAuthInfo> storeAction) {
            parameters.storeAction = storeAction;
            return this;
        }

        public OAuthParameters build(LoginType loginType) {
            if (loginType != LoginType.USERLESS_WEB && loginType != LoginType.USERLESS_INSTALLED) {
                throw new IllegalArgumentException("Invalid login type, can only be: [" + LoginType.USERLESS_WEB + ", " + LoginType.USERLESS_INSTALLED + "]");
            }
            parameters.loginType = loginType;
            ClientUtils.checkParameters(loginType, parameters);
            return parameters;
        }
    }

    public static class AuthCodeFlowBuilder {

        private final OAuthParameters parameters;

        public AuthCodeFlowBuilder() {
            parameters = new OAuthParameters();
        }

        public AuthCodeFlowBuilder setUserAgent(String userAgent) {
            parameters.userAgent = userAgent;
            return this;
        }

        public AuthCodeFlowBuilder setClientID(String clientID) {
            parameters.clientID = clientID;
            return this;
        }

        public AuthCodeFlowBuilder setClientSecret(String clientSecret) {
            parameters.clientSecret = clientSecret;
            return this;
        }

        public AuthCodeFlowBuilder setRedirectURI(URL redirectURI) {
            parameters.redirectURI = redirectURI;
            return this;
        }

        public AuthCodeFlowBuilder setPermanent(boolean permanent) {
            parameters.permanent = permanent;
            return this;
        }

        public AuthCodeFlowBuilder setScopes(List<Scopes> scopes) {
            parameters.scopes = scopes;
            return this;
        }

        public AuthCodeFlowBuilder setLoadAction(Callable<OAuthInfo> loadAction) {
            parameters.loadAction = loadAction;
            return this;
        }

        public AuthCodeFlowBuilder setStoreAction(Consumer<OAuthInfo> storeAction) {
            parameters.storeAction = storeAction;
            return this;
        }

        public OAuthParameters build(LoginType loginType) {
            if (loginType != LoginType.INSTALLED_APP && loginType != LoginType.WEB_APP) {
                throw new IllegalArgumentException("Invalid login type, can only be: [" + LoginType.INSTALLED_APP + ", " + LoginType.WEB_APP + "]");
            }
            parameters.loginType = loginType;
            ClientUtils.checkParameters(loginType, parameters);
            return parameters;
        }

    }

    public static class ScriptFlowBuilder {

        private final OAuthParameters parameters;

        public ScriptFlowBuilder() {
            parameters = new OAuthParameters();
            parameters.loginType = LoginType.SCRIPT;
        }

        public ScriptFlowBuilder setUserAgent(String userAgent) {
            parameters.userAgent = userAgent;
            return this;
        }

        public ScriptFlowBuilder setClientID(String clientID) {
            parameters.clientID = clientID;
            return this;
        }

        public ScriptFlowBuilder setClientSecret(String clientSecret) {
            parameters.clientSecret = clientSecret;
            return this;
        }

        public ScriptFlowBuilder setUsername(String username) {
            parameters.username = username;
            return this;
        }

        public ScriptFlowBuilder setPassword(String password) {
            parameters.password = password;
            return this;
        }

        public OAuthParameters build() {
            ClientUtils.checkParameters(LoginType.SCRIPT, parameters);
            return parameters;
        }
    }
}
