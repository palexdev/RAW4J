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
import io.github.palexdev.raw4j.data.UserList;
import io.github.palexdev.raw4j.enums.ApiEndpoints;
import io.github.palexdev.raw4j.enums.UserListType;
import io.github.palexdev.raw4j.json.GsonInstance;
import io.github.palexdev.raw4j.oauth.base.OAuthFlow;
import io.github.palexdev.raw4j.utils.NumberUtils;

import java.util.concurrent.Callable;

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
    public UserList get(UserListType type) {
        String url = buildRequestURL(type, "");
        return get(type, url);
    }

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
    public UserListRequestBuilder enableFallbackOnMiss(boolean fallbackOnMissEnabled) {
        cache.enableFallbackOnMiss(fallbackOnMissEnabled);
        return this;
    }
    public UserListRequestBuilder setFallbackAction(Callable<UserList> fallbackAction) {
        cache.setFallbackAction(fallbackAction);
        return this;
    }

    @Override
    public UserListRequestBuilder setLimit(int limit) {
        super.limit = limit;
        return this;
    }
}
