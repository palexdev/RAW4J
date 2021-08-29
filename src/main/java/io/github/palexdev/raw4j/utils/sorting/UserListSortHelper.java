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

package io.github.palexdev.raw4j.utils.sorting;

import io.github.palexdev.raw4j.data.listing.KarmaList;
import io.github.palexdev.raw4j.data.listing.UserList;
import io.github.palexdev.raw4j.data.listing.UserList.ListingUser;
import io.github.palexdev.raw4j.utils.sorting.base.AbstractSortingHelper;

import java.util.Comparator;
import java.util.List;

/**
 * Sort helper for {@link UserList}s, extends {@link AbstractSortingHelper}.
 */
public class UserListSortHelper extends AbstractSortingHelper<UserList, ListingUser> {
    //================================================================================
    // Properties
    //================================================================================
    private final UserList userList;

    //================================================================================
    // Constructors
    //================================================================================
    public UserListSortHelper(UserList userList) {
        this.userList = userList;
    }

    public static UserListSortHelper sorting(UserList userList) {
        return new UserListSortHelper(userList);
    }

    //================================================================================
    // Comparators
    //================================================================================

    /**
     * @return a comparator that sorts the users by their name
     */
    public static Comparator<ListingUser> sortByName() {
        return Comparator.comparing(ListingUser::getName);
    }

    /**
     * @return a comparator that sorts the user by their creation time
     */
    public static Comparator<ListingUser> sortByTime() {
        return Comparator.comparing(ListingUser::getDate);
    }

    //================================================================================
    // Methods
    //================================================================================

    /**
     * {@inheritDoc}
     */
    @Override
    public UserList sort() {
        if (getList() != null) {
            getList().sort(comparator);
        }
        return userList;
    }

    /**
     * @return the list of {@link KarmaList.KarmaListSubreddit}s
     */
    @Override
    public List<ListingUser> getList() {
        return userList.users();
    }
}
