package io.github.palexdev.raw4j;

import io.github.palexdev.raw4j.api.RedditClient;
import io.github.palexdev.raw4j.enums.Scopes;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class OAuthTest {
    private final String host = "127.0.0.1";
    private final int port = 8888;
    private final String fileStore = System.getProperty("user.home") + "/Desktop/ReddStoredToken";

    @Test
    public void testAuth() throws MalformedURLException {
            RedditClient client = RedditClient.loginInstalledApp(
                    TestConfig.USER_AGENT,
                    TestConfig.USERNAME,
                    TestConfig.CLIENT_ID,
                    new URL("http", host, port, "/"),
                    Scopes.all(),
                    fileStore
            );
            Awaitility.await().until(() -> client.getAuthInfo() != null);
            assertTrue(client.getAuthInfo().getExpiresIn() > 0);
    }
}
