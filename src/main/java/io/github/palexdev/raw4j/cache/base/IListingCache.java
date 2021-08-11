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

package io.github.palexdev.raw4j.cache.base;

import io.github.palexdev.raw4j.utils.collections.FixedSizeCacheMap;

/**
 * Public API all ListingCaches must implement.
 * <p></p>
 * This cache mechanism is backed by a {@link FixedSizeCacheMap}
 *
 * @param <T> the type of Listing
 */
public interface IListingCache<T> {

    /**
     * @return the instance of the last fetched item
     */
    T getCurrent();

    /**
     * Sets the current fetched item to the given one.
     */
    void setCurrent(T current);

    /**
     * Searches for the next listing in the map.
     */
    T next();

    /**
     * Searched for the previous listing in the map.
     */
    T previous();

    /**
     * Sets the maximum number of listings the map can contain.
     */
    void setMaxToCache(int max);

    /**
     * @return the cache map
     */
    FixedSizeCacheMap<String, T> getCachedItems();

    /**
     * Checks if the given key exists.
     */
    boolean itemExists(String key);

    /**
     * Specifies whether the cache system should clear the map after the specified amount of time, {@link #setAutomaticPurgeTime(long)}
     */
    void shouldAutoPurge(boolean purge);

    /**
     * Sets the number of seconds after which the map will be cleared (if the auto-purge feature is enabled).
     */
    void setAutomaticPurgeTime(long time);
}
