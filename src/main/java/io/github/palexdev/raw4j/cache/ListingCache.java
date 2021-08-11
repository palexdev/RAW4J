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

package io.github.palexdev.raw4j.cache;

import io.github.palexdev.raw4j.api.listing.UserListRequestBuilder;
import io.github.palexdev.raw4j.cache.base.IListingCache;
import io.github.palexdev.raw4j.data.base.Listing;
import io.github.palexdev.raw4j.utils.ExecutorUtils;
import io.github.palexdev.raw4j.utils.collections.FixedSizeCacheMap;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * This is an implementation of {@link IListingCache}
 * <p></p>
 * Other than implementing the {@link IListingCache} interface, this class adds a new feature which is
 * the possibility of specifying an action to perform in case the cache can't find the next/previous item.
 * <p>
 * Note that the action is not called by the {@code ListingCache} itself but should be called by the classes that use it
 * (see {@link UserListRequestBuilder} for example).
 *
 * @param <T> the type of Listing
 */
public class ListingCache<T extends Listing> implements IListingCache<T> {
    //================================================================================
    // Properties
    //================================================================================
    protected final FixedSizeCacheMap<String, T> cachedItems;
    protected int numToCache;
    protected T current;

    protected Future<?> pollingFuture;
    protected boolean shouldAutoPurge;
    protected long purgePollTime = 120;

    protected boolean fallbackOnMissEnabled = false;
    protected Callable<T> fallbackAction;

    //================================================================================
    // Constructors
    //================================================================================
    public ListingCache() {
        this(10);
    }

    public ListingCache(int numToCache) {
        this.numToCache = numToCache;
        this.cachedItems = new FixedSizeCacheMap<>(numToCache);
    }

    //================================================================================
    // Override Methods
    //================================================================================
    @Override
    public T next() {
        return cachedItems.get(current.getAfter());
    }

    @Override
    public T previous() {
        return cachedItems.get(current.getBefore());
    }

    @Override
    public boolean itemExists(String key) {
        return cachedItems.containsKey(key);
    }

    @Override
    public T getCurrent() {
        return current;
    }

    @Override
    public void setCurrent(T current) {
        this.current = current;
    }

    @Override
    public void setMaxToCache(int max) {
        numToCache = max;
    }

    @Override
    public FixedSizeCacheMap<String, T> getCachedItems() {
        return cachedItems;
    }

    /**
     * {@inheritDoc}
     * <p></p>
     * If the auto-purge is enabled (and was not already enabled) then schedules a task to {@link ExecutorUtils#scheduled()}.
     * <p>
     * If it is disabled then the task is cancelled.
     */
    @Override
    public void shouldAutoPurge(boolean purge) {
        if (purge && !shouldAutoPurge) {
            pollingFuture = ExecutorUtils.scheduled().scheduleAtFixedRate(cachedItems::clear, 0, purgePollTime, TimeUnit.SECONDS);
        } else {
            pollingFuture.cancel(true);
        }
        this.shouldAutoPurge = purge;
    }

    /**
     * {@inheritDoc}
     * <p></p>
     * Temporarily disables the auto-purge feature, then update the purgePollTime and then re-enables it.
     * <p></p>
     * The default value is 120s
     */
    @Override
    public void setAutomaticPurgeTime(long time) {
        shouldAutoPurge(false);
        this.purgePollTime = time;
        shouldAutoPurge(true);
    }

    //================================================================================
    // Getters, Setters
    //================================================================================

    /**
     * Checks whether the fallback action should be used or not.
     */
    public boolean isFallbackOnMissEnabled() {
        return fallbackOnMissEnabled;
    }

    /**
     * Enables/Disables the fallback action.
     * <p></p>
     * By default it's false.
     */
    public void enableFallbackOnMiss(boolean fallbackOnMissEnabled) {
        this.fallbackOnMissEnabled = fallbackOnMissEnabled;
    }

    /**
     * @return the fallback action
     */
    public Callable<T> getFallbackAction() {
        return fallbackAction;
    }

    /**
     * Sets the fallback action to the given one.
     */
    public void setFallbackAction(Callable<T> fallbackAction) {
        this.fallbackAction = fallbackAction;
    }

}
