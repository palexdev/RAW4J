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
import io.github.palexdev.raw4j.data.listing.KarmaList.KarmaListSubreddit;
import io.github.palexdev.raw4j.utils.sorting.base.AbstractSortingHelper;

import java.util.Comparator;
import java.util.List;

/**
 * Sort helper for {@link KarmaList}s, extends {@link AbstractSortingHelper}.
 */
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

    public static KarmaListSortHelper sorting(KarmaList karmaList) {
        return new KarmaListSortHelper(karmaList);
    }

    //================================================================================
    // Comparators
    //================================================================================

    /**
     * @return a comparator that sorts the karma list by subreddit name
     */
    public static Comparator<KarmaListSubreddit> sortByName() {
        return Comparator.comparing(KarmaListSubreddit::getName);
    }

    /**
     * @return a comparator that sorts the karma list by total karma
     */
    public static Comparator<KarmaListSubreddit> sortByTotalKarma() {
        return Comparator.comparing(KarmaListSubreddit::getTotalKarma);
    }

    //================================================================================
    // Methods
    //================================================================================

    /**
     * {@inheritDoc}
     */
    @Override
    public KarmaList sort() {
        if (getList() != null) {
            getList().sort(comparator);
        }
        return karmaList;
    }

    /**
     * @return the list of {@link KarmaListSubreddit}s
     */
    @Override
    public List<KarmaListSubreddit> getList() {
        return karmaList.subreddits();
    }
}
