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

package io.github.palexdev.raw4j.data.listing;

import com.google.gson.annotations.SerializedName;
import io.github.palexdev.raw4j.data.base.AbstractListing;
import io.github.palexdev.raw4j.data.base.Listing;
import io.github.palexdev.raw4j.enums.UserListType;
import io.github.palexdev.raw4j.json.annotations.Wrapped;
import io.github.palexdev.raw4j.utils.filtering.UserListFilterHelper;
import io.github.palexdev.raw4j.utils.filtering.base.Filterable;
import io.github.palexdev.raw4j.utils.sorting.UserListSortHelper;
import io.github.palexdev.raw4j.utils.sorting.base.Sortable;

import java.util.List;

/**
 * This data structure represents a {@link Listing} of type {@link UserListType}.
 * <p></p>
 * This class offers info about friends, blocked and trusted users.
 * <p></p>
 * Implements {@link Filterable} and {@link Sortable}.
 */
@Wrapped("data")
public class UserList extends AbstractListing implements Filterable, Sortable {
    //================================================================================
    // Properties
    //================================================================================
    private transient final UserListFilterHelper filterHelper = UserListFilterHelper.filtering(this);
    private transient final UserListSortHelper sortHelper = UserListSortHelper.sorting(this);
    private transient UserListType userListType;

    @SerializedName("children")
    private List<ListingUser> listingUsers;

    //================================================================================
    // Methods
    //================================================================================

    /**
     * @return the instance of the {@link UserListFilterHelper} to filter the list of users
     */
    @Override
    public UserListFilterHelper filtering() {
        return filterHelper;
    }

    /**
     * @return the instance of the {@link UserListSortHelper} to sort the list of users
     */
    @Override
    public UserListSortHelper sorting() {
        return sortHelper;
    }

    //================================================================================
    // Getters, Setters
    //================================================================================

    /**
     * @return the list of users
     * @see ListingUser
     */
    public List<ListingUser> users() {
        return listingUsers;
    }

    /**
     * @return the type of this UserList
     * @see UserListType
     */
    public UserListType getUserListType() {
        return userListType;
    }

    /**
     * Sets this UserList type. Since this property is not coming from Reddit, but rather
     * on the RAW4J side, to avoid code duplication, after the UserList has been retrieved
     * from the server it's needed to set its type. This is done automatically by RAW4J, any
     * subsequent call of this method won't have any effect.
     */
    public void setUserListType(UserListType type) {
        if (userListType != null) {
            return;
        }
        userListType = type;
    }

    //================================================================================
    // Nested Classes
    //================================================================================

    /**
     * This represents the data structure in the 'children' array of the JSON returned by the Reddit API.
     * <p></p>
     * This structure has info about: the user's name and id, the time it was added to the UserList (in other words
     * when you became friends, or when you blocked him).
     */
    public static class ListingUser {
        //================================================================================
        // Properties
        //================================================================================
        private Long date;
        private String id;
        private String name;

        @SerializedName("rel_id")
        private String relID;

        //================================================================================
        // Getters
        //================================================================================
        public Long getDate() {
            return date;
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getRelID() {
            return relID;
        }
    }
}
