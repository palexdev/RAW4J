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

package io.github.palexdev.raw4j.utils.sorting.base;

import io.github.palexdev.raw4j.enums.Sorting;

import java.util.Comparator;
import java.util.List;

/**
 * Base class for all sorting helpers.
 * <p></p>
 * This class as the instance of a {@link Comparator} because it allows chaining multiple
 * comparators by using {@link #sortBy(Comparator)} first and then {@link #andThen(Comparator)}.
 * <p></p>
 * To specify if the sort is ASCENDING or DESCENDING use {@link #mode(Sorting)}.
 *
 * @param <T> since helpers use fluent API, this parameter is needed to return the correct object after calling {@link #sort()}
 * @param <C> the type of objects that may be compared by the comparators
 */
public abstract class AbstractSortingHelper<T, C> {
    //================================================================================
    // Properties
    //================================================================================
    protected Comparator<C> comparator;

    //================================================================================
    // Abstract Methods
    //================================================================================
    public abstract T sort();
    public abstract List<C> getList();

    //================================================================================
    // Methods
    //================================================================================

    /**
     * Replaces the comparator with the given one
     */
    public AbstractSortingHelper<T, C> sortBy(Comparator<C> comparator) {
        this.comparator = comparator;
        return this;
    }

    /**
     * Chains the comparator with the given one by calling {@link Comparator#thenComparing(Comparator)}
     */
    public AbstractSortingHelper<T, C> andThen(Comparator<C> comparator) {
        this.comparator = this.comparator.thenComparing(comparator);
        return this;
    }

    /**
     * Sets the sorting mode on ASCENDING or DESCENDING.
     */
    public AbstractSortingHelper<T, C> mode(Sorting sorting) {
        if (sorting == Sorting.DESCENDING) {
            comparator = comparator.reversed();
        }
        return this;
    }
}
