package io.github.palexdev.raw4j;

import io.github.palexdev.raw4j.api.RedditClient;
import io.github.palexdev.raw4j.data.User;
import io.github.palexdev.raw4j.enums.LoginType;
import io.github.palexdev.raw4j.enums.Scopes;
import io.github.palexdev.raw4j.oauth.OAuthParameters;
import io.github.palexdev.raw4j.utils.ClientUtils;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;

public class AccountApiTest {
    private final String host = "127.0.0.1";
    private final int port = 8888;
    private final URL redirectURI = ClientUtils.url("http", host, port, "/");
    private final OAuthParameters parameters;
    private RedditClient redditClient;

    public AccountApiTest() {
        parameters = new OAuthParameters.AuthCodeFlowBuilder()
                .setUserAgent(TestConfig.INSTALLED_USER_AGENT)
                .setClientID(TestConfig.INSTALLED_CLIENT_ID)
                .setRedirectURI(redirectURI)
                .setScopes(Scopes.all())
                .setPermanent(false)
                .setLoadAction(TestUtils::loadAuthInfo)
                .setStoreAction(TestUtils::storeAuthInfo)
                .build(LoginType.INSTALLED_APP);
    }

    @BeforeEach
    public void setUp() {
        redditClient = RedditClient.login(parameters);
        Assumptions.assumeFalse(redditClient == null);
    }

    @Test
    public void testGetMe() {
        User getMe = redditClient.api().accountApi().getMe();
        assertNotNull(getMe);
        assertNotNull(getMe.getName());
    }

    @Test
    public void testAboutUser1() {
        User loggedUser = redditClient.api().accountApi().getLoggedUser();
        assertNotNull(loggedUser);
        assertTrue(loggedUser.getTotalKarma() > 0);
        assertFalse(loggedUser.isFriend());
    }

    @Test
    public void testAboutUser2() {
        User loggedUser = redditClient.api().userApi().getUser("xiongchiamiov");
        assertNotNull(loggedUser);
        assertTrue(loggedUser.getTotalKarma() > 0);
        assertFalse(loggedUser.isFriend());
    }

    @Test
    public void testAboutUser3() {
        User loggedUser = redditClient.api().userApi().getUser("ThreeSixty405");
        assertNull(loggedUser);
    }
}
