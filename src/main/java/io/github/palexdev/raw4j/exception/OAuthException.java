package io.github.palexdev.raw4j.exception;

public class OAuthException extends RuntimeException {

    public OAuthException() {
    }

    public OAuthException(String message) {
        super(message);
    }

    public OAuthException(String message, Throwable cause) {
        super(message, cause);
    }

    public OAuthException(Throwable cause) {
        super(cause);
    }
}
