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
import io.github.palexdev.raw4j.cache.ListingCache;
import io.github.palexdev.raw4j.data.UserList;
import io.github.palexdev.raw4j.enums.ApiEndpoints;
import io.github.palexdev.raw4j.enums.UserListType;
import io.github.palexdev.raw4j.json.GsonInstance;
import io.github.palexdev.raw4j.oauth.base.OAuthFlow;
import io.github.palexdev.raw4j.utils.NumberUtils;

import java.util.concurrent.Callable;

/**
 * Implementation of {@link ListingRequestBuilder} for {@link UserList} Listings.
 * <p></p>
 * To avoid code duplication this makes use of {@link UserListType}. When you want to get a {@link UserList},
 * you must specify which type you want, e.g. you want to see a list of friends, a list of blocked users...
 * <p></p>
 * The request URL is then built depending on the desired type and the GET request is sent.
 * <p></p>
 * This also implements the methods to get next and previous {@code UserLists} (two for each, more info in methods documentation).
 */
public class UserListRequestBuilder extends ListingRequestBuilder<UserList> {

    //================================================================================
    // Constructors
    //================================================================================
    public UserListRequestBuilder(OAuthFlow authManager) {
        super(authManager);
    }

    //================================================================================
    // Methods
    //================================================================================

    /**
     * Builds the request URL for the given type and returns a {@link UserList} of the given type.
     * <p></p>
     * Invokes private method {@link #get(UserListType, String)}
     */
    public UserList get(UserListType type) {
        String url = buildRequestURL(type, "");
        return get(type, url);
    }

    /**
     * Tells the auth manager to send a request with the given URL and retrieve a {@link UserList} object.
     * <p></p>
     * Once it is fetched there are some things to set/update:
     * <p> - It's needed to set the userListType property, {@link UserList#setUserListType(UserListType)};
     * <p> - It's needed to update the cache's current item with the fetched one
     * <p> - It's needed to add the fetched item to the cache
     * <p> - And finally update the count property, {@link #getCount()}
     *
     * @return a new {@link UserList} of the given type
     */
    private UserList get(UserListType type, String url) {
        UserList userList = GsonInstance.gson().fromJson(authManager.get(url), UserList.class);
        userList.setUserListType(type);
        cache.setCurrent(userList);
        cache.getCachedItems().put(userList.getAfter(), userList);
        count += NumberUtils.clamp(limit, 0, (userList.users() != null) ? userList.users().size() : 0);
        return userList;
    }

    //================================================================================
    // Override Methods
    //================================================================================

    /**
     * Responsible for building the correct request URL from the desired type.
     * <p></p>
     * This is also responsible for adding parameters to the URL if needed.
     */
    @Override
    protected String buildRequestURL(UserListType type, String parameters) {
        StringBuilder urlBuilder = new StringBuilder();
        switch (type) {
            case BLOCKED -> urlBuilder.append(ApiEndpoints.PREFS_BLOCKED.toStringRaw());
            case FRIENDS -> urlBuilder.append(ApiEndpoints.PREFS_FRIENDS.toStringRaw());
            case TRUSTED -> urlBuilder.append(ApiEndpoints.PREFS_TRUSTED.toStringRaw());
        }
        urlBuilder.append(parameters);
        return urlBuilder.toString();
    }

    /**
     * Returns the next {@link UserList} from the cache's current, {@link ListingCache#getCurrent()}.
     * <p>
     * If the current is null or the current's next is null the checks if the user has set a fallback action
     * and if it is enabled, {@link ListingCache#isFallbackOnMissEnabled()}, if all the conditions are met returns a new UserList
     * by calling the fallback action.
     *
     * @throws Exception the fallback action is a {@link Callable}, the {@link Callable#call()} method throws an exception
     */
    @Override
    public UserList next() throws Exception {
        UserList next = null;
        if (cache.getCurrent() != null && cache.next() != null) {
            next = cache.next();
        } else if (cache.isFallbackOnMissEnabled() && cache.getFallbackAction() != null) {
            next = cache.getFallbackAction().call();
        }
        return next;
    }

    /**
     * Returns the next {@link UserList} from the given one.
     * <p></p>
     * If the given UserList's after property is null then it returns the same given UserList.
     * <p></p>
     * If the cache doesn't contain the next, checks if the user has set a fallback action
     * and if it is enabled, {@link ListingCache#isFallbackOnMissEnabled()}, if these conditions are met
     * then returns a new UserList by calling the fallback action, otherwise executes a new {@link #get(UserListType, String)} request.
     *
     * @throws Exception the fallback action is a {@link Callable}, the {@link Callable#call()} method throws an exception
     */
    @Override
    public UserList next(UserList userList) throws Exception {
        if (userList.getAfter() == null) {
            return userList;
        }

        UserList next = cache.getCachedItems().get(userList.getAfter());
        if (next == null) {
            if (cache.isFallbackOnMissEnabled() && cache.getFallbackAction() != null) {
                next = cache.getFallbackAction().call();
            } else {
                String parameters = String.format("&after=%s&limit=%d&count=%d", userList.getAfter(), limit, count);
                String url = buildRequestURL(userList.getUserListType(), parameters);
                next = get(userList.getUserListType(), url);
            }
        }
        return next;
    }

    /**
     * Returns the previous {@link UserList} from the cache's current, {@link ListingCache#getCurrent()}.
     * <p>
     * If the current is null or the current's previous is null the checks if the user has set a fallback action
     * and if it is enabled, {@link ListingCache#isFallbackOnMissEnabled()}, if all the conditions are met returns a new UserList
     * by calling the fallback action.
     *
     * @throws Exception the fallback action is a {@link Callable}, the {@link Callable#call()} method throws an exception
     */
    @Override
    public UserList previous() throws Exception {
        UserList previous = null;
        if (cache.getCurrent() != null && cache.previous() != null) {
            previous = cache.previous();
        } else if (cache.isFallbackOnMissEnabled() && cache.getFallbackAction() != null) {
            previous = cache.getFallbackAction().call();
        }
        return previous;
    }

    /**
     * Returns the previous {@link UserList} from the given one.
     * <p></p>
     * If the given UserList's before property is null then it returns the same given UserList.
     * <p></p>
     * If the cache doesn't contain the previous, checks if the user has set a fallback action
     * and if it is enabled, {@link ListingCache#isFallbackOnMissEnabled()}, if these conditions are met
     * then returns a new UserList by calling the fallback action, otherwise executes a new {@link #get(UserListType, String)} request.
     *
     * @throws Exception the fallback action is a {@link Callable}, the {@link Callable#call()} method throws an exception
     */
    @Override
    public UserList previous(UserList userList) throws Exception {
        if (userList.getBefore() == null) {
            return userList;
        }

        UserList previous = cache.getCachedItems().get(userList.getBefore());
        if (previous == null ) {
            if (cache.isFallbackOnMissEnabled() && cache.getFallbackAction() != null) {
                previous = cache.getFallbackAction().call();
            } else {
                String parameters = String.format("&before=%s&limit=%d&count=%d", userList.getBefore(), limit, count);
                String url = buildRequestURL(userList.getUserListType(), parameters);
                previous = get(userList.getUserListType(), url);
            }
        }
        return previous;
    }

    //================================================================================
    // Builder Configuration
    //================================================================================

    /**
     * Delegate method to enable or disable the cache fallback action.
     */
    public UserListRequestBuilder enableFallbackOnMiss(boolean fallbackOnMissEnabled) {
        cache.enableFallbackOnMiss(fallbackOnMissEnabled);
        return this;
    }

    /**
     * Delegate method to set the cache fallback action on miss.
     */
    public UserListRequestBuilder setFallbackAction(Callable<UserList> fallbackAction) {
        cache.setFallbackAction(fallbackAction);
        return this;
    }

    /**
     * Sets the maximum number of items to fetch.
     */
    @Override
    public UserListRequestBuilder setLimit(int limit) {
        super.limit = limit;
        return this;
    }
}
