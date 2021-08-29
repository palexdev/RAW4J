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

import io.github.palexdev.raw4j.data.listing.TrophyList;
import io.github.palexdev.raw4j.data.listing.TrophyList.Trophy;
import io.github.palexdev.raw4j.utils.filtering.base.AbstractFilterHelper;

import java.time.Instant;
import java.util.List;
import java.util.function.Predicate;

/**
 * Filter helper for {@link TrophyList}s, extends {@link AbstractFilterHelper}.
 */
public class TrophyFilterHelper extends AbstractFilterHelper<TrophyList, Trophy> {
    //================================================================================
    // Properties
    //================================================================================
    private final TrophyList trophyList;

    //================================================================================
    // Constructors
    //================================================================================
    public TrophyFilterHelper(TrophyList trophyList) {
        this.trophyList = trophyList;
    }

    public static TrophyFilterHelper filtering(TrophyList trophyList) {
        return new TrophyFilterHelper(trophyList);
    }

    //================================================================================
    // Predicates
    //================================================================================

    /**
     * @return a predicate that filters the list by the trophies granted
     * after the given date (as an {@link Instant})
     */
    public Predicate<Trophy> filterByDateGreaterEqual(Instant instant) {
        return trophy -> trophy.getGrantedTime() >= instant.getEpochSecond();
    }

    /**
     * @return a predicate that filters the list by the trophies granted
     * before the given date (as an {@link Instant})
     */
    public Predicate<Trophy> filterByDateLesserEqual(Instant instant) {
        return trophy -> trophy.getGrantedTime() <= instant.getEpochSecond();
    }

    //================================================================================
    // Methods
    //================================================================================

    /**
     * {@inheritDoc}
     */
    @Override
    public TrophyList filter() {
        if (getList() != null) {
            getList().removeIf(predicate);
        }
        return trophyList;
    }

    /**
     * @return the list of {@link Trophy}s
     */
    @Override
    public List<Trophy> getList() {
        return trophyList.trophies();
    }
}
