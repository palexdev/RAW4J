package io.github.palexdev.raw4j.oauth;

import com.google.gson.JsonObject;
import io.github.palexdev.raw4j.enums.Scopes;
import okhttp3.RequestBody;

import java.io.IOException;
import java.util.List;

public interface OAuthManager {
    void authenticate() throws IOException;
    JsonObject get(String url);
    JsonObject post(String url, RequestBody requestBody);
    List<Scopes> getScopes();
    void setScopes(List<Scopes> scopes);
    OAuthInfo getAuthInfo();
    OAuthData getAuthData();
}
