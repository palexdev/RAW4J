package io.github.palexdev.raw4j.api;

import io.github.palexdev.raw4j.data.AccountData;
import io.github.palexdev.raw4j.enums.ApiEndpoints;
import io.github.palexdev.raw4j.json.GsonInstance;
import io.github.palexdev.raw4j.oauth.OAuthManager;

public class UserApi {
    private final OAuthManager authManager;

    UserApi(OAuthManager authManager) {
        this.authManager = authManager;
    }

    public AccountData getUser(String username) {
        String url = ApiEndpoints.USER.toStringRaw().formatted(username);
        AccountData accountData = GsonInstance.gson().fromJson(authManager.get(url), AccountData.class);
        return userExists(accountData) ? accountData : null;
    }

    public boolean userExists(AccountData accountData) {
        return accountData.getName() != null;
    }

    public boolean userExists(String username) {
        AccountData accountData = getUser(username);
        return userExists(accountData);
    }
}
