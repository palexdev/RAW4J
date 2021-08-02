package io.github.palexdev.raw4j.enums;

public enum ApiEndpoints {
    ME("/api/v1/me"),
    USER("/user/%s/about");


    private final String endpoint;

    ApiEndpoints(String endpoint) {
        this.endpoint = endpoint;
    }

    @Override
    public String toString() {
        return Urls.OAUTH_API_BASE_URL + endpoint;
    }

    public String toStringRaw() {
        return Urls.OAUTH_API_BASE_URL + endpoint + "?raw_json=1";
    }
}
