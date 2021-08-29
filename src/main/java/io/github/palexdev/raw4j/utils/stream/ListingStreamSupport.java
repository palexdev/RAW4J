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

package io.github.palexdev.raw4j.utils.stream;

import io.github.palexdev.raw4j.api.listing.base.ListingRequestBuilder;
import io.github.palexdev.raw4j.data.base.Listing;
import io.github.palexdev.raw4j.utils.iterators.ListingIterator;

import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * Helper class to add {@link Stream} support to {@link Listing}s by using a {@link ListingIterator}.
 */
public class ListingStreamSupport {

    //================================================================================
    // Constructors
    //================================================================================
    private ListingStreamSupport() {}

    //================================================================================
    // Methods
    //================================================================================

    /**
     * Builds a {@link Stream} for the given {@link Listing} type from a {@link ListingRequestBuilder}.
     *
     * @param <T> the type of listing
     */
    public static <T extends Listing> Stream<T> stream(ListingRequestBuilder<T> requestBuilder) {
        return asStream(requestBuilder.iterator());
    }

    /**
     * Builds a {@link Stream} for the given {@link Listing} type from a {@link ListingIterator}.
     *
     * @param <T> the type of listing
     */
    public static <T extends Listing> Stream<T> asStream(ListingIterator<T> iterator) {
        Iterable<T> iterable = () -> iterator;
        return StreamSupport.stream(
                iterable.spliterator(),
                false
        );
    }
}
