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
import io.github.palexdev.raw4j.data.Prefs;
import io.github.palexdev.raw4j.data.User;
import io.github.palexdev.raw4j.data.listing.KarmaList;
import io.github.palexdev.raw4j.data.listing.TrophyList;
import io.github.palexdev.raw4j.data.listing.UserList;
import io.github.palexdev.raw4j.enums.*;
import io.github.palexdev.raw4j.oauth.OAuthParameters;
import io.github.palexdev.raw4j.utils.sorting.KarmaListSortHelper;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Comparator;

import static io.github.palexdev.raw4j.json.GsonInstance.toJson;
import static org.junit.jupiter.api.Assertions.*;

public class AccountApiTest extends CommonTestProperties {

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
        User getMe = redditClient.accountApi().getMe();
        assertNotNull(getMe);
        assertNotNull(getMe.getName());
        assertNull(getMe.getType());
    }

    @Test
    public void testKarmaList() {
        KarmaList karmaList = redditClient.accountApi().getKarmaList().sorting()
                .sortBy(KarmaListSortHelper.sortByTotalKarma())
                .mode(Sorting.ASCENDING)
                .sort();
        assertTrue(TestUtils.isSorted(karmaList.subreddits(), Comparator.comparing(s -> (s.getLinkKarma() + s.getCommentKarma()))));
        assertFalse(karmaList.subreddits().isEmpty());
    }

    @Test
    public void testPrefs() {
        Prefs prefs = redditClient.accountApi().getPrefs();
        assertNotNull(prefs);
    }

    @Test
    public void testTrophyList() {
        TrophyList trophyList = redditClient.accountApi().getTrophyList();
        assertEquals(ThingType.T6, trophyList.trophies().get(0).getType());
        assertNotNull(trophyList.getType());
        assertNull(trophyList.getID());
        assertFalse(trophyList.trophies().isEmpty());
        logger.trace("\n" + toJson(trophyList));
    }

    @Test
    public void testBlocked() {
        UserList blocked = redditClient.accountApi().userListRequestBuilder(UserListType.BLOCKED).get();
        assertNotNull(blocked);
        assertFalse(blocked.users().isEmpty());
        assertEquals(UserListType.BLOCKED, blocked.getUserListType());
        assertEquals(ThingType.USER_LIST, blocked.getType());
        logger.trace("\n" + toJson(blocked));
    }

    @Test
    public void testFriends() {
        UserList friends = redditClient.accountApi().userListRequestBuilder(UserListType.FRIENDS).get();
        assertNotNull(friends);
        assertTrue(friends.users().isEmpty());
        assertEquals(UserListType.FRIENDS, friends.getUserListType());
        assertEquals(ThingType.USER_LIST, friends.getType());
    }

    @Test
    public void testTrusted() {
        UserList trusted = redditClient.accountApi().userListRequestBuilder(UserListType.TRUSTED).get();
        assertNotNull(trusted);
        assertTrue(trusted.users().isEmpty());
        assertEquals(UserListType.TRUSTED, trusted.getUserListType());
        assertEquals(ThingType.USER_LIST, trusted.getType());
    }

    @Test
    public void testUpdatePrefs() {
        Prefs initialPrefs = redditClient.accountApi().getPrefs();
        Prefs prefs = redditClient.accountApi().updatePrefs()
                .setVideoAutoplay(false)
                .patch();
        assertTrue(initialPrefs.isVideoAutoplay());
        assertFalse(prefs.isVideoAutoplay());

        Prefs reset = redditClient.accountApi().updatePrefs()
                .setVideoAutoplay(true)
                .patch();
        assertTrue(reset.isVideoAutoplay());
    }
}
