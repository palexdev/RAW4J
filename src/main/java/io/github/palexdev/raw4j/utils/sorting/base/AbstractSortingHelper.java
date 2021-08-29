/*
 * Copyright (I) 2021 Parisi Alessandro
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

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Base class for all sorting helpers.
 * <p></p>
 * This class as the instance of a {@link Comparator} because it allows chaining multiple
 * comparators by using {@link #sortBy(Comparator)} first and then {@link #andThen(Comparator)}.
 * <p></p>
 * To specify if the sort is ASCENDING or DESCENDING use {@link #mode(Sorting)}, this should be done
 * at the end as it just inverts the built comparator (if DESCENDING).
 *
 * @param <T> since helpers use fluent API, this parameter is needed to return the correct object after calling {@link #sort()}
 * @param <I> the type of objects that may be compared by the comparators
 */
public abstract class AbstractSortingHelper<T, I> {
    //================================================================================
    // Properties
    //================================================================================
    protected Comparator<I> comparator;

    //================================================================================
    // Abstract Methods
    //================================================================================

    /**
     * Sorts the list with the built comparator.
     */
    public abstract T sort();

    public abstract List<I> getList();

    //================================================================================
    // Methods
    //================================================================================

    /**
     * Replaces the comparator with the given one.
     */
    public AbstractSortingHelper<T, I> sortBy(Comparator<I> comparator) {
        this.comparator = comparator;
        return this;
    }

    /**
     * Chains the comparator with the given one by calling {@link Comparator#thenComparing(Comparator)}.
     */
    public AbstractSortingHelper<T, I> andThen(Comparator<I> comparator) {
        this.comparator = this.comparator.thenComparing(comparator);
        return this;
    }

    /**
     * Sets the sorting mode on ASCENDING or DESCENDING.
     */
    public AbstractSortingHelper<T, I> mode(Sorting sorting) {
        if (sorting == Sorting.DESCENDING) {
            comparator = comparator.reversed();
        }
        return this;
    }

    /**
     * @return the built comparator
     */
    public Comparator<I> getComparator() {
        return comparator;
    }

    /**
     * Creates a new separate list and sorts it with the built comparator,
     * leaving the original list unmodified.
     */
    public List<I> getNewList() {
        return new ArrayList<>(getList()).stream()
                .sorted(comparator)
                .collect(Collectors.toList());
    }

}
