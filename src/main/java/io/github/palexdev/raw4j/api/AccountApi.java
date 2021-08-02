package io.github.palexdev.raw4j.api;

import com.google.gson.JsonObject;
import io.github.palexdev.raw4j.data.AccountData;
import io.github.palexdev.raw4j.enums.ApiEndpoints;
import io.github.palexdev.raw4j.json.GsonInstance;
import io.github.palexdev.raw4j.oauth.OAuthManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AccountApi {
    private static final Logger logger = LoggerFactory.getLogger(RedditApiWrapper.class.getSimpleName());
    private final OAuthManager authManager;
    private AccountData loggedUser;

    AccountApi(OAuthManager authManager) {
        this.authManager = authManager;
    }

    public AccountData getMe() {
        String url = ApiEndpoints.ME.toStringRaw();
        return GsonInstance.gson().fromJson(authManager.get(url), AccountData.class);
    }

    public AccountData getLoggedUser() {
        if (loggedUser != null) {
            return loggedUser;
        }

        if (!authManager.getAuthData().isValidUsername()) {
            logger.error("Cannot retrieve account data as username has not been set");
            return null;
        }

        String url = ApiEndpoints.USER.toStringRaw().formatted(authManager.getAuthData().getUsername());
        JsonObject jsonObject = authManager.get(url);
        loggedUser =  GsonInstance.gson().fromJson(jsonObject, AccountData.class);
        return loggedUser;
    }

    public AccountApi refreshLoggedUser() {
        String url = ApiEndpoints.USER.toStringRaw().formatted(authManager.getAuthData().getUsername());
        loggedUser =  GsonInstance.gson().fromJson(authManager.get(url), AccountData.class);
        return this;
    }
}
