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

package io.github.palexdev.raw4j.utils.filtering;

import io.github.palexdev.raw4j.data.listing.UserList;
import io.github.palexdev.raw4j.data.listing.UserList.ListingUser;
import io.github.palexdev.raw4j.utils.filtering.base.AbstractFilterHelper;

import java.time.Instant;
import java.util.List;
import java.util.function.Predicate;

/**
 * Filter helper for {@link UserList}s, extends {@link AbstractFilterHelper}.
 */
public class UserListFilterHelper extends AbstractFilterHelper<UserList, ListingUser> {
    //================================================================================
    // Properties
    //================================================================================
    private final UserList userList;

    //================================================================================
    // Constructors
    //================================================================================
    public UserListFilterHelper(UserList userList) {
        this.userList = userList;
    }

    public static UserListFilterHelper filtering(UserList userList) {
        return new UserListFilterHelper(userList);
    }

    //================================================================================
    // Predicates
    //================================================================================

    /**
     * @return a predicate that filters the list by the given names
     */
    public Predicate<ListingUser> filterByNames(String... names) {
        return user -> List.of(names).contains(user.getName());
    }

    /**
     * @return a predicate that filters the list by the users created
     * after the given date (as an {@link Instant})
     */
    public Predicate<ListingUser> filterByDateGreaterEqual(Instant instant) {
        return user -> user.getDate() >= instant.getEpochSecond();
    }

    /**
     * @return a predicate that filters the list by the users created
     * before the given date (as an {@link Instant})
     */
    public Predicate<ListingUser> filterByDateLesserEqual(Instant instant) {
        return user -> user.getDate() <= instant.getEpochSecond();
    }

    //================================================================================
    // Methods
    //================================================================================

    /**
     * {@inheritDoc}
     */
    @Override
    public UserList filter() {
        if (getList() != null) {
            getList().removeIf(predicate);
        }
        return userList;
    }

    /**
     * @return the list of {@link ListingUser}s
     */
    @Override
    public List<ListingUser> getList() {
        return userList.users();
    }
}
