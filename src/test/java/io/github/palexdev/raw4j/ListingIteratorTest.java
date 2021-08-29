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
import io.github.palexdev.raw4j.api.listing.PostListRequestBuilder;
import io.github.palexdev.raw4j.base.CommonTestProperties;
import io.github.palexdev.raw4j.data.Post;
import io.github.palexdev.raw4j.data.listing.PostList;
import io.github.palexdev.raw4j.enums.LoginType;
import io.github.palexdev.raw4j.enums.PostListType;
import io.github.palexdev.raw4j.enums.Scopes;
import io.github.palexdev.raw4j.oauth.OAuthParameters;
import io.github.palexdev.raw4j.utils.iterators.ListingIterator;
import io.github.palexdev.raw4j.utils.stream.ListingStreamSupport;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class ListingIteratorTest extends CommonTestProperties {

    public ListingIteratorTest() {
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
    public void testPostsIterator() {
        List<String> postTitles = new ArrayList<>();
        ListingIterator<PostList> iterator = redditClient.userApi().postListIterator(PostListType.ALL, TestConfig.USERNAME);
        while (iterator.hasNext()) {
            PostList postList = iterator.next();
            postTitles.addAll(postList.posts().stream().map(Post::getTitle).collect(Collectors.toList()));
        }
        postTitles.sort(String::compareTo);
        debugListing(postTitles);
        assertTrue(postTitles.size() >= 48);
        assertEquals(postTitles.size(), iterator.getCount());
    }

    @Test
    public void testPostsForEach() {
        List<String> postTitles = new ArrayList<>();
        PostListRequestBuilder requestBuilder = redditClient.userApi().postListRequestBuilder(PostListType.ALL, TestConfig.USERNAME);
        requestBuilder.forEach(postList -> postTitles.addAll(postList.posts().stream().map(Post::getTitle).collect(Collectors.toList())));
        postTitles.sort(String::compareTo);
        debugListing(postTitles);
        assertTrue(postTitles.size() >= 48);
        assertEquals(postTitles.size(), requestBuilder.getCount());
    }

    @Test
    public void testIteratorAfterBefore() {
        ListingIterator<PostList> iterator = redditClient.userApi().postListIterator(PostListType.ALL, TestConfig.USERNAME);

        // Get first and collect titles
        List<String> firstTitle = iterator.next().posts().stream()
                .map(Post::getTitle)
                .collect(Collectors.toList());

        // Get Next
        iterator.next();

        // Get previous and collect titles
        List<String> sameAsBefore = iterator.previous().posts().stream()
                .map(Post::getTitle)
                .collect(Collectors.toList());

        // Lists should be the same
        assertEquals(firstTitle, sameAsBefore);
    }

    @Test
    public void testStreamListings() {
        List<PostList> posts = ListingStreamSupport.asStream(redditClient.userApi().postListIterator(PostListType.ALL, TestConfig.USERNAME))
                .collect(Collectors.toList());
        assertTrue(posts.stream().mapToLong(postList -> postList.posts().size()).sum() >= 48);
    }

    @Test
    public void testPostsCollections() {
        List<PostList> postLists = ListingStreamSupport.asStream(redditClient.userApi().postListIterator(PostListType.ALL, TestConfig.USERNAME))
                .collect(Collectors.toList());

        List<Post> posts = postLists.stream()
                .flatMap(postList -> postList.posts().stream())
                .filter(post -> !post.getCollections().isEmpty())
                .collect(Collectors.toList());
        assertFalse(posts.isEmpty());
    }

    private void debugListing(List<String> listing) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n[\n");
        listing.forEach(s -> sb.append(s).append("\n"));
        sb.append("]\n");
        logger.trace(sb.toString());

    }
}
