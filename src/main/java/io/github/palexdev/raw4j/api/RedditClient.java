package io.github.palexdev.raw4j.api;

import io.github.palexdev.raw4j.enums.Scopes;
import io.github.palexdev.raw4j.oauth.InstalledAppOAuth;
import io.github.palexdev.raw4j.oauth.OAuthData;
import io.github.palexdev.raw4j.oauth.OAuthInfo;
import io.github.palexdev.raw4j.oauth.OAuthManager;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.List;

public class RedditClient {
    private final OAuthManager authManager;
    private final RedditApiWrapper apiWrapper;

    private RedditClient(OAuthManager authManager) {
        this.authManager = authManager;
        apiWrapper = new RedditApiWrapper(authManager);
    }

    public static RedditClient loginInstalledApp(String userAgent, String username, String clientID, URL redirectURI, List<Scopes> scopes, String fileStore) {
        RedditClient redditClient = new RedditClient(
                new InstalledAppOAuth.Builder()
                        .setUserAgent(userAgent)
                        .setUsername(username)
                        .setClientID(clientID)
                        .setRedirectURI(redirectURI)
                        .setScopes(scopes)
                        .setFileStore(Paths.get(fileStore))
                        .build()
        );
        return redditClient.login();
    }

    protected RedditClient login() {
        try {
            authManager.authenticate();
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return this;
    }

    public RedditApiWrapper api() {
        return apiWrapper;
    }

    public List<Scopes> getScopes() {
        return authManager.getScopes();
    }

    public OAuthInfo getAuthInfo() {
        return authManager.getAuthInfo();
    }

    public OAuthData getAuthData() {
        return authManager.getAuthData();
    }
}
