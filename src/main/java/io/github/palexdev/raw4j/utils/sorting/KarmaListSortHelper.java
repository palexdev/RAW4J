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

import io.github.palexdev.raw4j.data.KarmaList;
import io.github.palexdev.raw4j.data.KarmaList.KarmaListSubreddit;
import io.github.palexdev.raw4j.utils.sorting.base.AbstractSortingHelper;

import java.util.Comparator;
import java.util.List;

public class KarmaListSortHelper extends AbstractSortingHelper<KarmaList, KarmaListSubreddit> {
    //================================================================================
    // Properties
    //================================================================================
    private final KarmaList karmaList;

    //================================================================================
    // Constructors
    //================================================================================
    public KarmaListSortHelper(KarmaList karmaList) {
        this.karmaList = karmaList;
    }

    //================================================================================
    // Methods
    //================================================================================
    public KarmaListSortHelper sortByName() {
        sortBy(Comparator.comparing(KarmaListSubreddit::getName));
        return this;
    }

    public KarmaListSortHelper sortByTotalKarma() {
        sortBy(Comparator.comparing(s -> s.getLinkKarma() + s.getCommentKarma()));
        return this;
    }

    //================================================================================
    // Override Methods
    //================================================================================
    @Override
    public KarmaList sort() {
        if (getList() == null) {
            return karmaList;
        }

        getList().sort(comparator);
        return karmaList;
    }

    @Override
    public List<KarmaListSubreddit> getList() {
        return karmaList.subreddits();
    }
}
