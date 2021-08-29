/*
 * Copyright (C) 2021 Parisi Alessandro
 * This file is part of RAW4J (https://github.com/palexdev/RAW4J).
 *
 * RAW4J is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * RAW4J is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with RAW4J.  If not, see <http://www.gnu.org/licenses/>.
 */

package io.github.palexdev.raw4j;

import io.github.palexdev.raw4j.api.RedditClient;
import io.github.palexdev.raw4j.base.CommonTestProperties;
import io.github.palexdev.raw4j.data.User;
import io.github.palexdev.raw4j.data.listing.TrophyList;
import io.github.palexdev.raw4j.data.listing.UserList;
import io.github.palexdev.raw4j.data.listing.UserList.ListingUser;
import io.github.palexdev.raw4j.enums.LoginType;
import io.github.palexdev.raw4j.enums.Scopes;
import io.github.palexdev.raw4j.enums.ThingType;
import io.github.palexdev.raw4j.enums.UserListType;
import io.github.palexdev.raw4j.oauth.OAuthParameters;
import org.junit.jupiter.api.*;

import java.util.stream.Collectors;

import static io.github.palexdev.raw4j.json.GsonInstance.toJson;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserApiTest extends CommonTestProperties {

    public UserApiTest() {
        super();
        parameters = new OAuthParameters.AuthCodeFlowBuilder()
                .setUserAgent(TestConfig.INSTALLED_USER_AGENT)
                .setClientID(TestConfig.INSTALLED_CLIENT_ID)
                .setRedirectURI(redirectURI)
                .setScopes(Scopes.all())
                .setPermanent(true)
                .setLoadAction(TestUtils::loadAuthInfo)
                .setStoreAction(TestUtils::storeAuthInfo)
                .build(LoginType.INSTALLED_APP);
    }

    @BeforeEach
    public void setUp() {
        redditClient = RedditClient.login(parameters);
        Assumptions.assumeTrue(redditClient != null);
    }

    @Test
    public void testAboutUser1() {
        User loggedUser = redditClient.api().accountApi().getLoggedUser();
        assertNotNull(loggedUser);
        assertTrue(loggedUser.getTotalKarma() > 0);
        assertFalse(loggedUser.isFriend());
        assertEquals(loggedUser.getType(), ThingType.T2);
        logger.trace("\n" + toJson(loggedUser));
    }

    @Test
    public void testAboutUser2() {
        User user = redditClient.api().userApi().getUser(TestConfig.TEST_USERNAME);
        assertNotNull(user);
        assertTrue(user.getTotalKarma() > 0);
        assertFalse(user.isFriend());
        logger.trace("\n" + toJson(user));
    }

    @Test
    public void testAboutUser3() {
        User user = redditClient.api().userApi().getUser(TestConfig.TEST_AVAILABLE_USERNAME);
        assertNull(user);
    }

    @Test
    @Order(1)
    public void testAddFriend() {
        ListingUser addedFriend = redditClient.userApi().addFriend(TestConfig.TEST_USERNAME2);
        logger.trace("\n" + toJson(addedFriend));
        assertNotNull(addedFriend);
        assertNotNull(addedFriend.getName());
        UserList friends = redditClient.accountApi().userListRequestBuilder(UserListType.FRIENDS).get();
        assertTrue(friends.users().stream().map(ListingUser::getName).collect(Collectors.toList()).contains(TestConfig.TEST_USERNAME2));
        logger.trace("\n" + toJson(friends));
    }

    @Test
    public void testAddFriend2() {
        ListingUser addedFriend = redditClient.userApi().addFriend(TestConfig.TEST_AVAILABLE_USERNAME);
        logger.trace("\n" + toJson(addedFriend));
        assertNotNull(addedFriend);
        assertNull(addedFriend.getName());
        UserList friends = redditClient.accountApi().userListRequestBuilder(UserListType.FRIENDS).get();
        assertFalse(friends.users().stream().map(ListingUser::getName).collect(Collectors.toList()).contains(TestConfig.TEST_AVAILABLE_USERNAME));
        logger.trace("\n" + toJson(friends));
    }

    @Test
    @Order(2)
    public void testRemoveFriend() {
        redditClient.userApi().removeFriend(TestConfig.TEST_USERNAME2);
        UserList friends = redditClient.accountApi().userListRequestBuilder(UserListType.FRIENDS).get();
        assertFalse(friends.users().stream().map(ListingUser::getName).collect(Collectors.toList()).contains(TestConfig.TEST_USERNAME2));
        logger.trace("\n" + toJson(friends));
    }

    @Test
    @Order(3)
    public void testBlockUser() {
        redditClient.userApi().blockUser(TestConfig.TEST_USERNAME);
        UserList blocked = redditClient.accountApi().userListRequestBuilder(UserListType.BLOCKED).get();
        assertTrue(blocked.users().stream()
                .map(ListingUser::getName)
                .collect(Collectors.toList())
                .contains(TestConfig.TEST_USERNAME)
        );
        logger.trace("\n" + toJson(blocked));
    }

    @Test
    @Order(4)
    public void testUnblockUser() {
        redditClient.userApi().unblockUser(TestConfig.TEST_USERNAME);
        UserList blocked = redditClient.accountApi().userListRequestBuilder(UserListType.BLOCKED).get();
        assertFalse(blocked.users().stream()
                .map(ListingUser::getName)
                .collect(Collectors.toList())
                .contains(TestConfig.TEST_USERNAME)
        );
        logger.trace("\n" + toJson(blocked));
    }

    @Test
    public void testUsernameUnavailable() {
        assertFalse(redditClient.userApi().usernameAvailable(TestConfig.USERNAME));
    }

    @Test
    public void testUsernameAvailable() {
        assertTrue(redditClient.userApi().usernameAvailable(TestConfig.TEST_AVAILABLE_USERNAME));
    }

    @Test
    public void testGetTrophies() {
        TrophyList trophies = redditClient.userApi().getTrophies(TestConfig.TEST_USERNAME2);
        assertEquals(1, trophies.trophies().size());
        logger.trace("\n" + toJson(trophies));
    }
}
