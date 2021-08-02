package io.github.palexdev.raw4j.api;

import io.github.palexdev.raw4j.oauth.OAuthManager;

public class RedditApiWrapper {
    private final AccountApi accountApi;
    private final UserApi userApi;

    RedditApiWrapper(OAuthManager authManager) {
        accountApi = new AccountApi(authManager);
        userApi = new UserApi(authManager);
    }

    public AccountApi accountApi() {
        return accountApi;
    }

    public UserApi userApi() {
        return userApi;
    }
}
