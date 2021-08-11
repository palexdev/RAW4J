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
    public AbstractSortingHelper<T, C> sortBy(Comparator<C> comparator) {
        this.comparator = comparator;
        return this;
    }

    public AbstractSortingHelper<T, C> andThen(Comparator<C> comparator) {
        this.comparator = this.comparator.thenComparing(comparator);
        return this;
    }
    public AbstractSortingHelper<T, C> mode(Sorting sorting) {
        if (sorting == Sorting.DESCENDING) {
            comparator = comparator.reversed();
        }
        return this;
    }
}
