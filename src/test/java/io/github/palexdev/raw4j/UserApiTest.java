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
import io.github.palexdev.raw4j.enums.LoginType;
import io.github.palexdev.raw4j.enums.Scopes;
import io.github.palexdev.raw4j.enums.ThingType;
import io.github.palexdev.raw4j.oauth.OAuthParameters;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserApiTest extends CommonTestProperties {

    public UserApiTest() {
        super();
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
        Assumptions.assumeTrue(redditClient != null);
    }

    @Test
    public void testAboutUser1() {
        User loggedUser = redditClient.api().accountApi().getLoggedUser();
        assertNotNull(loggedUser);
        assertTrue(loggedUser.getTotalKarma() > 0);
        assertFalse(loggedUser.isFriend());
        assertEquals(loggedUser.getType(), ThingType.T2);
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
