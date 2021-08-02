package io.github.palexdev.raw4j;

import io.github.palexdev.raw4j.api.RedditClient;
import io.github.palexdev.raw4j.data.AccountData;
import io.github.palexdev.raw4j.enums.Scopes;
import io.github.palexdev.raw4j.json.GsonInstance;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;

public class AccountApiTest {
    private RedditClient redditClient;
    private final String host = "127.0.0.1";
    private final int port = 8888;
    private final String fileStore = System.getProperty("user.home") + "/Desktop/ReddStoredToken";

    @BeforeEach
    public void setUp() throws MalformedURLException {
        redditClient = RedditClient.loginInstalledApp(
                TestConfig.USER_AGENT,
                TestConfig.USERNAME,
                TestConfig.CLIENT_ID,
                new URL("http", host, port, "/"),
                Scopes.all(),
                fileStore
        );
        Awaitility.await().until(() -> redditClient.getAuthInfo() != null);
        Assumptions.assumeFalse(redditClient == null);
    }

    @Test
    public void testGetMe() {
        AccountData getMe = redditClient.api().accountApi().getMe();
        System.out.println(GsonInstance.gson().toJson(getMe));
        assertNotNull(getMe);
        assertNotNull(getMe.getName());
    }

    @Test
    public void testAboutUser1() {
        AccountData loggedUser = redditClient.api().accountApi().getLoggedUser();
        System.out.println(GsonInstance.gson().toJson(loggedUser));
        assertNotNull(loggedUser);
        assertTrue(loggedUser.getTotalKarma() > 0);
        assertFalse(loggedUser.isFriend());
    }

    @Test
    public void testAboutUser2() {
        AccountData loggedUser = redditClient.api().userApi().getUser("xiongchiamiov");
        System.out.println(GsonInstance.gson().toJson(loggedUser));
        assertNotNull(loggedUser);
        assertTrue(loggedUser.getTotalKarma() > 0);
        assertFalse(loggedUser.isFriend());
    }

    @Test
    public void testAboutUser3() {
        AccountData loggedUser = redditClient.api().userApi().getUser("ThreeSixty405");
        assertNull(loggedUser);
    }
}
