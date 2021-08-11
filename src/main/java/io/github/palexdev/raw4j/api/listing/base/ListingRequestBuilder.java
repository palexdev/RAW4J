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

package io.github.palexdev.raw4j.api.listing.base;

import io.github.palexdev.raw4j.cache.ListingCache;
import io.github.palexdev.raw4j.data.UserList;
import io.github.palexdev.raw4j.data.base.Listing;
import io.github.palexdev.raw4j.enums.UserListType;
import io.github.palexdev.raw4j.oauth.base.OAuthFlow;

public abstract class ListingRequestBuilder<T extends Listing> {
    //================================================================================
    // Properties
    //================================================================================
    protected final OAuthFlow authManager;
    protected final ListingCache<T> cache = new ListingCache<>();
    protected int count;
    protected int limit = 25;

    //================================================================================
    // Constructors
    //================================================================================
    protected ListingRequestBuilder(OAuthFlow authManager) {
        this.authManager = authManager;
    }

    //================================================================================
    // Abstract Methods
    //================================================================================
    protected abstract String buildRequestURL(UserListType type, String parameters);
    public abstract UserList next() throws Exception;
    public abstract UserList previous() throws Exception;
    public abstract UserList next(UserList userList) throws Exception;
    public abstract UserList previous(UserList userList) throws Exception;
    public abstract ListingRequestBuilder<T> setLimit(int limit);

    //================================================================================
    // Methods
    //================================================================================
    public ListingCache<T> cache() {
        return cache;
    }

    public int getCount() {
        return count;
    }

    protected void updateCount(int toAdd) {
        this.count += toAdd;
    }
}
