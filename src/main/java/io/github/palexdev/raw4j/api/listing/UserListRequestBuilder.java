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
import io.github.palexdev.raw4j.data.listing.UserList;
import io.github.palexdev.raw4j.enums.UserListType;
import io.github.palexdev.raw4j.enums.endpoints.AccountEndpoints;
import io.github.palexdev.raw4j.oauth.base.OAuthFlow;
import io.github.palexdev.raw4j.utils.NumberUtils;

import static io.github.palexdev.raw4j.json.GsonInstance.fromJson;

/**
 * Implementation of {@link ListingRequestBuilder} to manage {@link UserList} listings.
 * <p></p>
 * UserLists are all the same, but they do differ in type, see {@link UserListType}.
 * <p></p>
 * Reddit has several endpoints that return UserList listings, so to avoid code duplication the same
 * builder is used for all the endpoints. However, request builders ideally should work only on one endpoint at a time
 * to ensure that {@link #next(UserList)} and {@link #previous(UserList)} methods return the correct listing.
 * <p>
 * For this reason this builder needs to know on which type of UserList is operating, {@link UserListType}.
 * The type is necessary to build the correct request url, {@link #buildRequestURL(String)}.
 */
public class UserListRequestBuilder extends ListingRequestBuilder<UserList> {
    //================================================================================
    // Properties
    //================================================================================
    private final UserListType type;

    //================================================================================
    // Constructors
    //================================================================================

    /**
     * Builds a new instance of {@code UserListRequestBuilder} that operates on {@link UserList} listings of the given type.
     */
    public UserListRequestBuilder(OAuthFlow authManager, UserListType type) {
        super(authManager);
        this.type = type;
    }

    //================================================================================
    // Methods
    //================================================================================

    /**
     * Ensures that the given listing type and this builder's type are the same.
     *
     * @throws IllegalArgumentException if the types are not the same
     */
    protected void checkTypes(UserList userList) {
        if (userList.getUserListType() != type) {
            throw new IllegalArgumentException(
                    "This builder can work only on this type of UserLists: " + type +
                            ", your type was: " + userList.getUserListType()
            );
        }
    }

    //================================================================================
    // Overridden/Implemented Methods
    //================================================================================

    /**
     * Responsible for building the correct request URL from the desired type of {@link UserList}, see {@link UserListType}.
     * <p></p>
     * This is also responsible for adding parameters to the URL if needed.
     */
    @Override
    protected String buildRequestURL(String parameters) {
        StringBuilder urlBuilder = new StringBuilder();
        switch (type) {
            case BLOCKED -> urlBuilder.append(AccountEndpoints.PREFS_BLOCKED.getFullEndpointRaw());
            case FRIENDS -> urlBuilder.append(AccountEndpoints.PREFS_FRIENDS.getFullEndpointRaw());
            case TRUSTED -> urlBuilder.append(AccountEndpoints.PREFS_TRUSTED.getFullEndpointRaw());
        }
        urlBuilder.append(parameters);
        return urlBuilder.toString();
    }

    /**
     * {@inheritDoc}
     * <p></p>
     * Overridden as this request builder has to set the userListType property of the {@link UserList},
     * see {@link UserList#setUserListType(UserListType)}.
     */
    @Override
    protected UserList get(String url) {
        UserList userList = fromJson(authManager.get(url), UserList.class);
        userList.setUserListType(type);
        updateCount(userList);
        return userList;
    }

    /**
     * {@inheritDoc}
     * <p></p>
     * Overridden to check types, {@link #checkTypes(UserList)}.
     */
    @Override
    public UserList next(UserList userList) {
        checkTypes(userList);
        return super.next(userList);
    }

    /**
     * {@inheritDoc}
     * <p></p>
     * Overridden to check types, {@link #checkTypes(UserList)}.
     */
    @Override
    public UserList previous(UserList userList) {
        checkTypes(userList);
        return super.previous(userList);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void updateCount(UserList userList) {
        count += NumberUtils.clamp(limit, 0, userList.users().size());
    }

    /**
     * Sets the maximum number of items to fetch.
     */
    @Override
    public UserListRequestBuilder setLimit(int limit) {
        super.limit = limit;
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Class<UserList> getType() {
        return UserList.class;
    }
}
