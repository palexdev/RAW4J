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

package io.github.palexdev.raw4j.api.listing;

import io.github.palexdev.raw4j.api.listing.base.ListingRequestBuilder;
import io.github.palexdev.raw4j.data.listing.PostList;
import io.github.palexdev.raw4j.enums.PostListType;
import io.github.palexdev.raw4j.enums.endpoints.UserEndpoints;
import io.github.palexdev.raw4j.oauth.base.OAuthFlow;
import io.github.palexdev.raw4j.utils.NumberUtils;

import static io.github.palexdev.raw4j.json.GsonInstance.fromJson;

/**
 * Implementation of {@link ListingRequestBuilder} to manage {@link PostList} listings.
 * <p>
 * This builder needs a redditor's username to send the request.
 * <p></p>
 * PostLists are all the same, but they do differ in type, see {@link PostListType}.
 * <p></p>
 * Reddit has several endpoints that return PostList listings, so to avoid code duplication the same
 * builder is used for all the endpoints. However, request builders ideally should work only on one endpoint at a time
 * to ensure that {@link #next(PostList)} and {@link #previous(PostList)} methods return the correct listing.
 * <p>
 * For this reason this builder needs to know on which type of PostList is operating, {@link PostListType}.
 * The type is necessary to build the correct request url, {@link #buildRequestURL(String)}.
 */
public class PostListRequestBuilder extends ListingRequestBuilder<PostList> {
    //================================================================================
    // Properties
    //================================================================================
    private final PostListType type;
    private final String username;

    //================================================================================
    // Constructors
    //================================================================================

    /**
     * Builds a new instance of {@code PostListRequestBuilder} that operates on {@link PostList} listings of the given type
     * for the given redditor.
     */
    public PostListRequestBuilder(OAuthFlow authManager, PostListType type, String username) {
        super(authManager);
        this.type = type;
        this.username = username;
    }

    //================================================================================
    // Methods
    //================================================================================

    /**
     * Ensures that the given listing type and this builder's type are the same.
     *
     * @throws IllegalArgumentException if the types are not the same
     */
    protected void checkTypes(PostList postList) {
        if (postList.getPostListType() != type) {
            throw new IllegalArgumentException(
                    "This builder can work only on this type of PostLists: " + type +
                            ", your type was: " + postList.getPostListType()
            );
        }
    }

    //================================================================================
    // Overridden/Implemented Methods
    //================================================================================

    /**
     * Responsible for building the correct request URL from the desired type of {@link PostListType}.
     * <p></p>
     * This is also responsible for adding parameters to the URL if needed.
     */
    @Override
    protected String buildRequestURL(String parameters) {
        StringBuilder urlBuilder = new StringBuilder();
        switch (type) {
            case ALL -> urlBuilder.append(UserEndpoints.POSTS.getFullEndpointRaw());
            case DOWNVOTED -> urlBuilder.append(UserEndpoints.DOWNVOTED.getFullEndpointRaw());
            case HIDDEN -> urlBuilder.append(UserEndpoints.HIDDEN.getFullEndpointRaw());
            case UPVOTED -> urlBuilder.append(UserEndpoints.UPVOTED.getFullEndpointRaw());
        }
        urlBuilder.append(parameters);
        return String.format(urlBuilder.toString(), username);
    }

    /**
     * {@inheritDoc}
     * <p></p>
     * Overridden as this request builder has to set the postListType property of the {@link PostList},
     * see {@link PostList#setPostListType(PostListType)}.
     */
    @Override
    protected PostList get(String url) {
        PostList postList = fromJson(authManager.get(url), PostList.class);
        postList.setPostListType(type);
        updateCount(postList);
        return postList;
    }

    /**
     * {@inheritDoc}
     * <p></p>
     * Overridden to check types, {@link #checkTypes(PostList)}.
     */
    @Override
    public PostList next(PostList postList) {
        checkTypes(postList);
        return super.next(postList);
    }

    /**
     * {@inheritDoc}
     * <p></p>
     * Overridden to check types, {@link #checkTypes(PostList)}.
     */
    @Override
    public PostList previous(PostList postList) {
        checkTypes(postList);
        return super.previous(postList);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void updateCount(PostList postList) {
        count += NumberUtils.clamp(limit, 0, postList.posts().size());
    }

    /**
     * Sets the maximum number of items to fetch.
     */
    @Override
    public PostListRequestBuilder setLimit(int limit) {
        super.limit = limit;
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Class<PostList> getType() {
        return PostList.class;
    }
}
