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
