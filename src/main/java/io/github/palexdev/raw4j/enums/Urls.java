package io.github.palexdev.raw4j.enums;

public enum Urls {
    OAUTH_API_BASE_URL("https://oauth.reddit.com"),
    OAUTH_AUTH_URL("https://ssl.reddit.com/api/v1/authorize"),
    OAUTH_TOKEN_URL("https://ssl.reddit.com/api/v1/access_token"),
    ;

    private final String url;

    Urls(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return url;
    }
}
