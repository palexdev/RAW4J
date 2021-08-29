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

package io.github.palexdev.raw4j.api;

import com.google.gson.JsonObject;
import io.github.palexdev.raw4j.api.listing.CommentListRequestBuilder;
import io.github.palexdev.raw4j.api.listing.OverviewRequestBuilder;
import io.github.palexdev.raw4j.api.listing.PostListRequestBuilder;
import io.github.palexdev.raw4j.data.User;
import io.github.palexdev.raw4j.data.listing.CommentList;
import io.github.palexdev.raw4j.data.listing.Overview;
import io.github.palexdev.raw4j.data.listing.PostList;
import io.github.palexdev.raw4j.data.listing.TrophyList;
import io.github.palexdev.raw4j.data.listing.UserList.ListingUser;
import io.github.palexdev.raw4j.enums.OverviewType;
import io.github.palexdev.raw4j.enums.PostListType;
import io.github.palexdev.raw4j.enums.endpoints.UserEndpoints;
import io.github.palexdev.raw4j.oauth.base.OAuthFlow;
import io.github.palexdev.raw4j.utils.iterators.ListingIterator;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.RequestBody;

import static io.github.palexdev.raw4j.json.GsonInstance.fromJson;

/**
 * This class contains all methods to interact with User APIs.
 */
public class UserApi {
    //================================================================================
    // Properties
    //================================================================================
    private final RedditApiWrapper wrapper;
    private final OAuthFlow authManager;

    //================================================================================
    // Constructors
    //================================================================================
    UserApi(RedditApiWrapper wrapper) {
        this.wrapper = wrapper;
        this.authManager = wrapper.getAuthManager();
    }

    //================================================================================
    // API Implementation
    //================================================================================

    /**
     * Adds the given user to the friends list.
     *
     * @return the user object on success
     */
    public ListingUser addFriend(String username) {
        String url = String.format(UserEndpoints.FRIENDS.getFullEndpoint(), username);
        JsonObject obj = new JsonObject();
        obj.addProperty("user", username);

        RequestBody requestBody = FormBody.create(obj.toString(), MediaType.parse("application/json; charset=utf-8"));
        return fromJson(authManager.put(url, requestBody), ListingUser.class);
    }

    /**
     * Blocks the given user.
     */
    public void blockUser(String username) {
        String url = UserEndpoints.BLOCK.getFullEndpoint();
        RequestBody requestBody = new FormBody.Builder()
                .add("name", username)
                .build();
        authManager.post(url, requestBody);
    }

    public TrophyList getTrophies(String username) {
        String url = String.format(UserEndpoints.TROPHIES.getFullEndpoint(), username);
        return fromJson(authManager.get(url), TrophyList.class);
    }

    /**
     * Retrieves the {@link User} data structure for the given username.
     * <p></p>
     * Before returning checks if the user exists, in case it doesn't, returns null.
     */
    public User getUser(String username) {
        String url = UserEndpoints.ABOUT.getFullEndpointRaw().formatted(username);
        return userExists(username) ? fromJson(authManager.get(url), User.class) : null;
    }

    /**
     * Removes the given user from the friends list.
     */
    public void removeFriend(String username) {
        String url = String.format(UserEndpoints.FRIENDS.getFullEndpoint(), username);
        authManager.delete(url);
    }

    /**
     * Reports the given user with the given reason.
     * <p>
     * The reason string must not exceed 100 characters otherwise throws an {@code IllegalArgumentException}.
     */
    public void reportUser(String username, String reason) {
        if (reason.length() > 100) {
            throw new IllegalArgumentException("The reason exceeds the characters limit of 100, length is: " + reason.length());
        }

        String url = UserEndpoints.REPORT.getFullEndpoint();
        RequestBody requestBody = new FormBody.Builder()
                .add("user", username)
                .add("reason", reason)
                .build();
        authManager.post(url, requestBody);
    }

    /**
     * Reports the given user with the given reason and the given details as a json string.
     * <p>
     * The reason string must not exceed 100 characters otherwise throws an {@code IllegalArgumentException}.
     */
    public void reportUser(String username, String reason, String jsonData) {
        if (reason.length() > 100) {
            throw new IllegalArgumentException("The reason exceeds the characters limit of 100, length is: " + reason.length());
        }

        fromJson(jsonData, JsonObject.class);
        String url = UserEndpoints.REPORT.getFullEndpoint();
        RequestBody requestBody = new FormBody.Builder()
                .add("user", username)
                .add("reason", reason)
                .add("details", jsonData)
                .build();
        authManager.post(url, requestBody);
    }

    /**
     * Unblocks the given user.
     */
    public void unblockUser(String username) {
        String url = UserEndpoints.UNFRIEND.getFullEndpoint();
        String loggedUserID = wrapper.accountApi().getLoggedUser().getName();
        RequestBody requestBody = new FormBody.Builder()
                .add("type", "enemy")
                .add("container", loggedUserID)
                .add("name", username)
                .build();
        authManager.post(url, requestBody);
    }

    /**
     * Calls !{@link #usernameAvailable(String)}
     */
    public Boolean userExists(String username) {
        return !usernameAvailable(username);
    }

    /**
     * Checks if the given username is available.
     */
    public Boolean usernameAvailable(String username) {
        String url = String.format(UserEndpoints.USERNAME_AVAILABLE.getFullEndpoint(), username);
        return authManager.getBoolean(url);
    }

    //================================================================================
    // Iterators
    //================================================================================

    /**
     * @return a new iterator to iterate through {@link CommentList} listings of the given user.
     */
    public ListingIterator<CommentList> commentListIterator(String username) {
        return commentListsRequestBuilder(username).iterator();
    }

    /**
     * @return a new iterator to iterate through {@link Overview} listings of the given user.
     */
    public ListingIterator<Overview> overviewIterator(OverviewType type, String username) {
        return overviewRequestBuilder(type, username).iterator();
    }

    /**
     * @return a new iterator to iterate through {@link PostList} listings of the given user.
     */
    public ListingIterator<PostList> postListIterator(PostListType type, String username) {
        return postListRequestBuilder(type, username).iterator();
    }

    //================================================================================
    // Request Builders
    //================================================================================

    /**
     * @return a new instance of {@link CommentListRequestBuilder} for the given username, to get and navigate through {@link CommentList}s
     */
    public CommentListRequestBuilder commentListsRequestBuilder(String username) {
        return new CommentListRequestBuilder(authManager, username);
    }

    /**
     * @return a new instance of {@link OverviewRequestBuilder} for the given username, to get and navigate through {@link Overview}s
     */
    public OverviewRequestBuilder overviewRequestBuilder(OverviewType type, String username) {
        return new OverviewRequestBuilder(authManager, type, username);
    }

    /**
     * @return a new instance of {@link PostListRequestBuilder} for the given username, to get and navigate through {@link PostList}s
     */
    public PostListRequestBuilder postListRequestBuilder(PostListType type, String username) {
        return new PostListRequestBuilder(authManager, type, username);
    }
}
