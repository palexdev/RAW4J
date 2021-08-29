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

package io.github.palexdev.raw4j.utils.iterators;

import io.github.palexdev.raw4j.api.listing.base.ListingRequestBuilder;
import io.github.palexdev.raw4j.data.base.Listing;

/**
 * Iterator for {@link Listing}s, implements {@link BiDirectionalIterator}.
 * <p>
 * The iterator makes use of a {@link ListingRequestBuilder} to fetch the listings.
 *
 * @param <T> the type of {@link Listing}
 */
public class ListingIterator<T extends Listing> implements BiDirectionalIterator<T> {
    //================================================================================
    // Properties
    //================================================================================
    private T listing;
    private final ListingRequestBuilder<T> requestBuilder;

    //================================================================================
    // Constructors
    //================================================================================
    public ListingIterator(ListingRequestBuilder<T> requestBuilder) {
        this.requestBuilder = requestBuilder;
    }

    public ListingIterator(T listing, ListingRequestBuilder<T> requestBuilder) {
        this.listing = listing;
        this.requestBuilder = requestBuilder;
    }

    //================================================================================
    // Methods
    //================================================================================

    /**
     * If the current listing is null retrieves one by calling {@link ListingRequestBuilder#get()}.
     * <p></p>
     * According to the specified direction parameter retrieves the next or previous listing from
     * the current one by calling {@link ListingRequestBuilder#next(Listing)} or {@link ListingRequestBuilder#previous(Listing)}.
     * <p></p>
     * Always updates the current listing with the one fetched.
     */
    private T getListing(Direction direction) {
        if (listing == null) {
            listing = requestBuilder.get();
            return listing;
        }

        listing = switch (direction) {
            case FORWARD -> requestBuilder.next(listing);
            case BACKWARD -> requestBuilder.previous(listing);
        };
        return listing;
    }

    //================================================================================
    // Implemented Methods
    //================================================================================

    /**
     * {@inheritDoc}
     * <p></p>
     * Checks if the listing is null or the after property of the listing is not null.
     */
    @Override
    public boolean hasNext() {
        return listing == null || listing.getAfter() != null;
    }

    /**
     * {@inheritDoc}
     * <p></p>
     * Calls {@link #getListing(Direction)} with FORWARD as direction.
     */
    @Override
    public T next() {
        return getListing(Direction.FORWARD);
    }

    /**
     * {@inheritDoc}
     * <p></p>
     * Checks if the listing is not null and the before property of the listing is not null.
     */
    @Override
    public boolean hasPrevious() {
        return listing != null && listing.getBefore() != null;
    }

    /**
     * {@inheritDoc}
     * <p></p>
     * Calls {@link #getListing(Direction)} with BACKWARD as direction.
     */
    @Override
    public T previous() {
        return getListing(Direction.BACKWARD);
    }

    //================================================================================
    // Delegate Methods
    //================================================================================

    /**
     * Delegate to {@link ListingRequestBuilder#setLimit(int)}.
     */
    public ListingRequestBuilder<T> setLimit(int limit) {
        return requestBuilder.setLimit(limit);
    }

    /**
     * Delegate to {@link ListingRequestBuilder#getCount()}.
     */
    public int getCount() {
        return requestBuilder.getCount();
    }
}
