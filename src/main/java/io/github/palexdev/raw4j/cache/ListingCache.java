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

import io.github.palexdev.raw4j.cache.base.IListingCache;
import io.github.palexdev.raw4j.data.base.Listing;
import io.github.palexdev.raw4j.utils.ExecutorUtils;
import io.github.palexdev.raw4j.utils.collections.FixedSizeCacheMap;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

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

    @Override
    public void shouldAutoPurge(boolean purge) {
        if (purge && !shouldAutoPurge) {
            pollingFuture = ExecutorUtils.scheduled().scheduleAtFixedRate(cachedItems::clear, 0, purgePollTime, TimeUnit.SECONDS);
        } else {
            pollingFuture.cancel(true);
        }
        this.shouldAutoPurge = purge;
    }

    @Override
    public void setAutomaticPurgeTime(long time) {
        shouldAutoPurge(false);
        this.purgePollTime = time;
        shouldAutoPurge(true);
    }

    //================================================================================
    // Getters, Setters
    //================================================================================
    public boolean isFallbackOnMissEnabled() {
        return fallbackOnMissEnabled;
    }

    public void enableFallbackOnMiss(boolean fallbackOnMissEnabled) {
        this.fallbackOnMissEnabled = fallbackOnMissEnabled;
    }

    public Callable<T> getFallbackAction() {
        return fallbackAction;
    }

    public void setFallbackAction(Callable<T> fallbackAction) {
        this.fallbackAction = fallbackAction;
    }

}
