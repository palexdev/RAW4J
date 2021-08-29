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
import io.github.palexdev.raw4j.data.Comment;
import io.github.palexdev.raw4j.data.Post;
import io.github.palexdev.raw4j.data.listing.Overview;
import io.github.palexdev.raw4j.data.listing.PostList;
import io.github.palexdev.raw4j.enums.OverviewType;
import io.github.palexdev.raw4j.enums.PostListType;
import io.github.palexdev.raw4j.enums.endpoints.UserEndpoints;
import io.github.palexdev.raw4j.oauth.base.OAuthFlow;
import io.github.palexdev.raw4j.utils.NumberUtils;

import static io.github.palexdev.raw4j.json.GsonInstance.fromJson;

/**
 * Implementation of {@link ListingRequestBuilder} to manage {@link Overview} like listings.
 * <p>
 * This builder needs a redditor's username to send the request.
 * <p></p>
 * Why did I say "Overview like"?
 * <p>
 * In general overviews are listings that contain both {@link Post}s and {@link Comment}s.
 * Overviews are all the same, but they do differ in type, see {@link OverviewType}.
 * <p></p>
 * Reddit has several endpoints that return overview like listings, so to avoid code duplication the same
 * builder is used for all the endpoints. However, request builders ideally should work only on one endpoint at a time
 * to ensure that {@link #next(Overview)} and {@link #previous(Overview)} methods return the correct listing.
 * <p>
 * For this reason this builder needs to know on which type of overview is operating, {@link OverviewType}.
 * The type is necessary to build the correct request url, {@link #buildRequestURL(String)}.
 */
public class OverviewRequestBuilder extends ListingRequestBuilder<Overview> {
    //================================================================================
    // Properties
    //================================================================================
    private final OverviewType type;
    private final String username;

    //================================================================================
    // Constructors
    //================================================================================

    /**
     * Builds a new instance of {@code OverviewRequestBuilder} that operates on {@link Overview} listings of the given type
     * for the given redditor.
     */
    public OverviewRequestBuilder(OAuthFlow authManager, OverviewType type, String username) {
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
    protected void checkTypes(Overview overview) {
        if (overview.getOverviewType() != type) {
            throw new IllegalArgumentException(
                    "This builder can work only on this type of Overviews: " + type +
                            ", your type was: " + overview.getOverviewType()
            );
        }
    }

    //================================================================================
    // Overridden/Implemented Methods
    //================================================================================

    /**
     * Responsible for building the correct request URL from the desired type of {@link OverviewType}.
     * <p></p>
     * This is also responsible for adding parameters to the URL if needed.
     */
    @Override
    protected String buildRequestURL(String parameters) {
        StringBuilder urlBuilder = new StringBuilder();
        switch (type) {
            case ALL -> urlBuilder.append(UserEndpoints.OVERVIEW.getFullEndpointRaw());
            case AWARDED -> urlBuilder.append(UserEndpoints.GILDED.getFullEndpointRaw());
            case AWARD_GIVEN -> urlBuilder.append(UserEndpoints.GILDED_GIVEN.getFullEndpointRaw());
            case SAVED -> urlBuilder.append(UserEndpoints.SAVED.getFullEndpointRaw());
        }
        urlBuilder.append(parameters);
        return String.format(urlBuilder.toString(), username);
    }

    /**
     * {@inheritDoc}
     * <p></p>
     * Overridden as this request builder has to set the overviewType property of the {@link Overview},
     * see {@link Overview#setOverviewType(OverviewType)}.
     */
    @Override
    protected Overview get(String url) {
        Overview overview = fromJson(authManager.get(url), Overview.class);
        overview.setOverviewType(type);
        updateCount(overview);
        return overview;
    }

    /**
     * {@inheritDoc}
     * <p></p>
     * Overridden as this request builder has to set the postListType property of the {@link PostList},
     * see {@link PostList#setPostListType(PostListType)}.
     */
    @Override
    public Overview next(Overview overview) {
        checkTypes(overview);
        return super.next(overview);
    }

    /**
     * {@inheritDoc}
     * <p></p>
     * Overridden as this request builder has to set the postListType property of the {@link PostList},
     * see {@link PostList#setPostListType(PostListType)}.
     */
    @Override
    public Overview previous(Overview overview) {
        checkTypes(overview);
        return super.previous(overview);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void updateCount(Overview overview) {
        count += NumberUtils.clamp(limit, 0, overview.submissions().size());
    }

    /**
     * Sets the maximum number of items to fetch.
     */
    @Override
    public OverviewRequestBuilder setLimit(int limit) {
        super.limit = limit;
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Class<Overview> getType() {
        return Overview.class;
    }
}
