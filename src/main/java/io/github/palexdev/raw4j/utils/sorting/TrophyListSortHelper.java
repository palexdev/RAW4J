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

import io.github.palexdev.raw4j.data.TrophyList;
import io.github.palexdev.raw4j.data.TrophyList.Trophy;
import io.github.palexdev.raw4j.utils.sorting.base.AbstractSortingHelper;

import java.util.Comparator;
import java.util.List;

public class TrophyListSortHelper extends AbstractSortingHelper<TrophyList, Trophy> {
    //================================================================================
    // Properties
    //================================================================================
    private final TrophyList trophyList;

    //================================================================================
    // Constructors
    //================================================================================
    public TrophyListSortHelper(TrophyList trophyList) {
        this.trophyList = trophyList;
    }

    //================================================================================
    // Methods
    //================================================================================
    public TrophyListSortHelper sortByDescription() {
        sortBy(Comparator.comparing(Trophy::getDescription));
        return this;
    }

    public TrophyListSortHelper sortByName() {
        sortBy(Comparator.comparing(Trophy::getTrophyName));
        return this;
    }

    public TrophyListSortHelper sortByTime() {
        sortBy(Comparator.comparing(Trophy::getGrantedTime));
        return this;
    }

    //================================================================================
    // Override Methods
    //================================================================================
    @Override
    public TrophyList sort() {
        if (getList() == null) {
            return trophyList;
        }

        getList().sort(comparator);
        return trophyList;
    }

    @Override
    public List<Trophy> getList() {
        return trophyList.trophies();
    }
}
