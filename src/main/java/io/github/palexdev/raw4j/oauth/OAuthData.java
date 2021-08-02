package io.github.palexdev.raw4j.oauth;

import java.io.Serializable;

public class OAuthData implements Serializable {
    private String username;
    private String clientID;
    private String secret;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getClientID() {
        return clientID;
    }

    public void setClientID(String clientID) {
        this.clientID = clientID;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public boolean isValidUsername() {
        return username != null && !username.isBlank();
    }
}
