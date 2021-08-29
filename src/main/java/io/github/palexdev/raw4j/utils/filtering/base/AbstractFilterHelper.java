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

package io.github.palexdev.raw4j.utils.filtering.base;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public abstract class AbstractFilterHelper<T, I> {
    //================================================================================
    // Properties
    //================================================================================
    protected Predicate<I> predicate;

    //================================================================================
    // Abstract Methods
    //================================================================================

    /**
     * Filters the list with the built predicate.
     * <p></p>
     * <b>WARNING!!!</b>
     * <p>
     * <b>
     * This method makes use of {@link List#removeIf(Predicate)} so items that do
     * pass the predicate evaluation will be REMOVED from the original list.
     * </b>
     * <p></p>
     * <b>To leave the original list unmodified use {@link #getNewList()}</b>
     */
    public abstract T filter();

    public abstract List<I> getList();

    //================================================================================
    // Methods
    //================================================================================

    /**
     * Replaces the predicate with the given one.
     */
    public AbstractFilterHelper<T, I> filterBy(Predicate<I> predicate) {
        this.predicate = predicate;
        return this;
    }

    /**
     * Chains the predicate with the given one by calling {@link Predicate#and(Predicate)}.
     */
    public AbstractFilterHelper<T, I> and(Predicate<I> other) {
        this.predicate = predicate.and(other);
        return this;
    }

    /**
     * Chains the predicate with the given one by calling {@link Predicate#or(Predicate)}.
     */
    public AbstractFilterHelper<T, I> or(Predicate<I> other) {
        this.predicate = predicate.or(other);
        return this;
    }

    /**
     * Negates the built predicate by calling {@link Predicate#not(Predicate)}.
     */
    public AbstractFilterHelper<T, I> negate() {
        this.predicate = predicate.negate();
        return this;
    }

    /**
     * @return the built predicate
     */
    public Predicate<I> getPredicate() {
        return predicate;
    }

    /**
     * Creates a new separate list and filters it with the built predicate,
     * leaving the original list unmodified.
     */
    public List<I> getNewList() {
        return new ArrayList<>(getList()).stream()
                .filter(predicate)
                .collect(Collectors.toList());
    }
}
