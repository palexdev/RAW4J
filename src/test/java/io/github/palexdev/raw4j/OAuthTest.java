package io.github.palexdev.raw4j;

import io.github.palexdev.raw4j.api.RedditClient;
import io.github.palexdev.raw4j.enums.LoginType;
import io.github.palexdev.raw4j.enums.Scopes;
import io.github.palexdev.raw4j.oauth.OAuthParameters;
import io.github.palexdev.raw4j.utils.ClientUtils;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.net.URL;
import java.util.List;

import static io.github.palexdev.raw4j.TestConfig.*;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class OAuthTest {
    private final int port = 8888;
    private final String host = "127.0.0.1";
    private final URL redirectURI = ClientUtils.url("http", host, port, "/");

    @Test
    @Order(1)
    public void testDenyAuth() {
        OAuthParameters parameters = new OAuthParameters.AuthCodeFlowBuilder()
                .setUserAgent(INSTALLED_USER_AGENT)
                .setClientID(INSTALLED_CLIENT_ID)
                .setRedirectURI(redirectURI)
                .setScopes(Scopes.all())
                .build(LoginType.INSTALLED_APP);
        RedditClient client = RedditClient.login(parameters);
        assertNull(client);
    }

    @Test
    @Order(2)
    @SuppressWarnings("All")
    public void testFailAuth2() {
        assertThrows(NullPointerException.class, () -> RedditClient.login(null));
    }

    @Test
    @Order(3)
    public void testFailAuth3() {
        assertThrows(IllegalStateException.class, () ->
                new OAuthParameters.AuthCodeFlowBuilder()
                        .setUserAgent(INSTALLED_USER_AGENT)
                        .setClientID(INSTALLED_CLIENT_ID)
                        .setRedirectURI(redirectURI)
                        .setScopes(List.of())
                        .build(LoginType.INSTALLED_APP)
        );
    }

    @Test
    @Order(4)
    public void testTemporaryAuth() {
        OAuthParameters parameters = new OAuthParameters.AuthCodeFlowBuilder()
                .setUserAgent(INSTALLED_USER_AGENT)
                .setClientID(INSTALLED_CLIENT_ID)
                .setRedirectURI(redirectURI)
                .setScopes(Scopes.all())
                .setPermanent(false)
                .build(LoginType.INSTALLED_APP);
        RedditClient redditClient = RedditClient.login(parameters);
        assertNotNull(redditClient);
        assertNull(redditClient.getAuthInfo().getRefreshToken());
        assertFalse(redditClient.getAuthInfo().isPermanent());
    }

    @Test
    @Order(5)
    public void testAppOnlyAuth() {
        OAuthParameters parameters = new OAuthParameters.AppOnlyFlowBuilder()
                .setUserAgent(INSTALLED_USER_AGENT)
                .setClientID(INSTALLED_CLIENT_ID)
                .setScopes(Scopes.all())
                .setLoadAction(TestUtils::loadAuthInfo2)
                .setStoreAction(TestUtils::storeAuthInfo2)
                .build(LoginType.USERLESS_INSTALLED);
        RedditClient redditClient = RedditClient.login(parameters);
        assertNotNull(redditClient);
        assertNotNull(redditClient.getAuthInfo().getRefreshToken());
        assertTrue(redditClient.getAuthInfo().isValid());
        assertTrue(redditClient.getAuthInfo().isPermanent());
    }

    @Test
    @Order(6)
    public void testWebAppAuth() {
        OAuthParameters parameters = new OAuthParameters.AuthCodeFlowBuilder()
                .setUserAgent(WEB_USER_AGENT)
                .setClientID(WEB_CLIENT_ID)
                .setClientSecret(WEB_CLIENT_SECRET)
                .setRedirectURI(redirectURI)
                .setPermanent(false)
                .setScopes(Scopes.all())
                .build(LoginType.WEB_APP);
        RedditClient client = RedditClient.login(parameters);
        assertNotNull(client);
        assertTrue(client.getAuthInfo().isValid());
    }

    @Test
    @Order(7)
    public void testInstalledAppAuth() {
        OAuthParameters parameters = new OAuthParameters.AuthCodeFlowBuilder()
                .setUserAgent(INSTALLED_USER_AGENT)
                .setClientID(INSTALLED_CLIENT_ID)
                .setRedirectURI(redirectURI)
                .setScopes(Scopes.all())
                .setLoadAction(TestUtils::loadAuthInfo)
                .setStoreAction(TestUtils::storeAuthInfo)
                .build(LoginType.INSTALLED_APP);
        RedditClient client = RedditClient.login(parameters);
        assertNotNull(client);
        assertTrue(client.getAuthInfo().isValid());
        System.out.println(client.getAuthInfo().getAccessToken());
    }

    @Test
    @Order(8)
    public void testScriptAuth() {
        OAuthParameters parameters = new OAuthParameters.ScriptFlowBuilder()
                .setUserAgent(SCRIPT_USER_AGENT)
                .setUsername(USERNAME)
                .setPassword(PASSWORD)
                .setClientID(SCRIPT_CLIENT_ID)
                .setClientSecret(SCRIPT_CLIENT_SECRET)
                .build();
        RedditClient client = RedditClient.login(parameters);
        assertNotNull(client);
        assertTrue(client.getAuthInfo().isValid());
    }
}
