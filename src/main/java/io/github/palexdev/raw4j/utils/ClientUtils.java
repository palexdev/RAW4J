package io.github.palexdev.raw4j.utils;

import io.github.palexdev.raw4j.enums.LoginType;
import io.github.palexdev.raw4j.enums.Scopes;
import io.github.palexdev.raw4j.oauth.OAuthParameters;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import static io.github.palexdev.raw4j.utils.StringUtils.checkString;

public class ClientUtils {

    //================================================================================
    // Constructors
    //================================================================================
    private ClientUtils() {
    }

    //================================================================================
    // Public API
    //================================================================================
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

    private static void installedAppOnlyCheck(String userAgent, String clientID, List<Scopes> scopes) {
        StringBuilder sb = new StringBuilder();
        sb.append(checkString(userAgent, "User-Agent, "));
        sb.append(checkString(clientID, "ClientID, "));
        if (scopes == null || scopes.isEmpty()) {
            sb.append("Scopes, ");
        }
        checkErrors(sb.toString());
    }

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


    private static void scriptCheck(String userAgent, String username, String password, String clientID, String clientSecret) {
        StringBuilder sb = new StringBuilder();
        sb.append(checkString(userAgent, "User-Agent, "));
        sb.append(checkString(username, "Username, "));
        sb.append(checkString(password, "Password, "));
        sb.append(checkString(clientID, "ClientID, "));
        sb.append(checkString(clientSecret, "Client Secret, "));
        checkErrors(sb.toString());
    }

    private static void checkErrors(String errors) throws IllegalStateException {
        if (!errors.isEmpty()) {
            String s = StringUtils.replaceLast(errors, ",", "");
            throw new IllegalStateException(s + "not set");
        }
    }
}
