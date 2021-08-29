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
import io.github.palexdev.raw4j.data.listing.CommentList;
import io.github.palexdev.raw4j.enums.endpoints.UserEndpoints;
import io.github.palexdev.raw4j.oauth.base.OAuthFlow;
import io.github.palexdev.raw4j.utils.NumberUtils;

/**
 * Implementation of {@link ListingRequestBuilder} to manage {@link CommentList} listings.
 * <p>
 * This builder needs a redditor's username to send the request.
 */
public class CommentListRequestBuilder extends ListingRequestBuilder<CommentList> {
    //================================================================================
    // Properties
    //================================================================================
    private final String username;

    //================================================================================
    // Constructors
    //================================================================================

    /**
     * Builds a new instance of {@code CommentListRequestBuilder} that operates on {@link CommentList} listings of the given type
     * for the given redditor.
     */
    public CommentListRequestBuilder(OAuthFlow authManager, String username) {
        super(authManager);
        this.username = username;
    }

    //================================================================================
    // Overridden/Implemented Methods
    //================================================================================

    /**
     * Responsible for building the correct request URL to get a listing of comments.
     * <p></p>
     * This is also responsible for adding parameters to the URL if needed.
     */
    @Override
    protected String buildRequestURL(String parameters) {
        return String.format(UserEndpoints.COMMENTS.getFullEndpointRaw(), username) + parameters;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void updateCount(CommentList commentList) {
        count += NumberUtils.clamp(limit, 0, commentList.comments().size());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CommentListRequestBuilder setLimit(int limit) {
        super.limit = limit;
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Class<CommentList> getType() {
        return CommentList.class;
    }
}
