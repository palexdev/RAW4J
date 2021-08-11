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

package io.github.palexdev.raw4j.utils;

import io.github.palexdev.raw4j.enums.LoginType;
import io.github.palexdev.raw4j.enums.Scopes;
import io.github.palexdev.raw4j.oauth.OAuthParameters;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import static io.github.palexdev.raw4j.utils.StringUtils.checkString;

/**
 * Utils class for the client.
 */
public class ClientUtils {

    //================================================================================
    // Constructors
    //================================================================================
    private ClientUtils() {
    }

    //================================================================================
    // Public API
    //================================================================================

    /**
     * Checks if the specified parameters are correct for the specified login type.
     *
     * @throws IllegalStateException if the parameters are not valid
     */
    public static void checkParameters(LoginType loginType, OAuthParameters parameters) {
        switch (loginType) {
            case USERLESS_INSTALLED -> installedAppOnlyCheck(
                    parameters.getUserAgent(),
                    parameters.getClientID(),
                    parameters.getScopes()
            );
            case USERLESS_WEB -> webAppOnlyCheck(
                    parameters.getUserAgent(),
                    parameters.getClientID(),
                    parameters.getClientSecret(),
                    parameters.getScopes()
            );
            case INSTALLED_APP -> installedCheck(
                    parameters.getUserAgent(),
                    parameters.getClientID(),
                    parameters.getRedirectURI(),
                    parameters.getScopes()
            );
            case WEB_APP -> webCheck(
                    parameters.getUserAgent(),
                    parameters.getClientID(),
                    parameters.getClientSecret(),
                    parameters.getRedirectURI(),
                    parameters.getScopes()
            );
            case SCRIPT -> scriptCheck(
                    parameters.getUserAgent(),
                    parameters.getUsername(),
                    parameters.getPassword(),
                    parameters.getClientID(),
                    parameters.getClientSecret()
            );
        }
    }

    /**
     * Builds a URL from the given arguments, {@link MalformedURLException} is caught by a try catch block.
     */
    public static URL url(String protocol, String host, int port, String path) {
        URL url = null;
        try {
            url = new URL(protocol, host, port, path);
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        }
        return url;
    }

    //================================================================================
    // Private API
    //================================================================================

    /**
     * Userless Web Apps check, needed parameters are: User-Agent, ClientID, ClientSecret and Scopes.
     */
    private static void webAppOnlyCheck(String userAgent, String clientID, String clientSecret, List<Scopes> scopes) {
        StringBuilder sb = new StringBuilder();
        sb.append(checkString(userAgent, "User-Agent, "));
        sb.append(checkString(clientID, "ClientID, "));
        sb.append(checkString(clientSecret, "Client Secret, "));
        if (scopes == null || scopes.isEmpty()) {
            sb.append("Scopes, ");
        }
        checkErrors(sb.toString());
    }

    /**
     * Userless Installed Apps check, needed parameters are: User-Agent, ClientID and Scopes.
     */
    private static void installedAppOnlyCheck(String userAgent, String clientID, List<Scopes> scopes) {
        StringBuilder sb = new StringBuilder();
        sb.append(checkString(userAgent, "User-Agent, "));
        sb.append(checkString(clientID, "ClientID, "));
        if (scopes == null || scopes.isEmpty()) {
            sb.append("Scopes, ");
        }
        checkErrors(sb.toString());
    }

    /**
     * Logged user Installed Apps check, needed parameters are: User-Agent, ClientID, RedirectURI and Scopes.
     */
    private static void installedCheck(String userAgent, String clientID, URL redirectURI, List<Scopes> scopes) {
        StringBuilder sb = new StringBuilder();
        sb.append(checkString(userAgent, "User-Agent, "));
        sb.append(checkString(clientID, "ClientID, "));
        sb.append(checkString(redirectURI.toString(), "Redirect URI, "));
        if (scopes == null || scopes.isEmpty()) {
            sb.append("Scopes, ");
        }
        checkErrors(sb.toString());
    }

    /**
     * Logged user Web Apps check, needed parameters are: User-Agent, ClientID, ClientSecret, RedirectURI and Scopes.
     */
    private static void webCheck(String userAgent, String clientID, String clientSecret, URL redirectURI, List<Scopes> scopes) {
        StringBuilder sb = new StringBuilder();
        sb.append(checkString(userAgent, "User-Agent, "));
        sb.append(checkString(clientID, "ClientID, "));
        sb.append(checkString(clientSecret, "ClientSecret, "));
        sb.append(checkString(redirectURI.toString(), "Redirect URI, "));
        if (scopes == null || scopes.isEmpty()) {
            sb.append("Scopes, ");
        }
        checkErrors(sb.toString());
    }

    /**
     * Scripts check, needed parameters are: User-Agent, Username, Password, ClientID and ClientSecret.
     */
    private static void scriptCheck(String userAgent, String username, String password, String clientID, String clientSecret) {
        StringBuilder sb = new StringBuilder();
        sb.append(checkString(userAgent, "User-Agent, "));
        sb.append(checkString(username, "Username, "));
        sb.append(checkString(password, "Password, "));
        sb.append(checkString(clientID, "ClientID, "));
        sb.append(checkString(clientSecret, "Client Secret, "));
        checkErrors(sb.toString());
    }

    /**
     * Checks if the given string is not empty and throws an {@link IllegalStateException}.
     */
    private static void checkErrors(String errors) throws IllegalStateException {
        if (!errors.isEmpty()) {
            String s = StringUtils.replaceLast(errors, ",", "");
            throw new IllegalStateException(s + "not set");
        }
    }
}
