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

import io.github.palexdev.raw4j.data.listing.KarmaList;
import io.github.palexdev.raw4j.data.listing.KarmaList.KarmaListSubreddit;
import io.github.palexdev.raw4j.utils.filtering.base.AbstractFilterHelper;

import java.util.List;
import java.util.function.Predicate;

/**
 * Filter helper for {@link KarmaList}s, extends {@link AbstractFilterHelper}.
 */
public class KarmaListFilterHelper extends AbstractFilterHelper<KarmaList, KarmaListSubreddit> {
    //================================================================================
    // Properties
    //================================================================================
    private final KarmaList karmaList;

    //================================================================================
    // Constructors
    //================================================================================
    public KarmaListFilterHelper(KarmaList karmaList) {
        this.karmaList = karmaList;
    }

    public static KarmaListFilterHelper filtering(KarmaList karmaList) {
        return new KarmaListFilterHelper(karmaList);
    }

    //================================================================================
    // Predicates
    //================================================================================

    /**
     * @return a predicate that filters the list by the items for which the total
     * karma is greater or equal to the given amount
     */
    public Predicate<KarmaListSubreddit> filterByKarmaGreaterEqual(int karma) {
        return kls -> kls.getTotalKarma() >= karma;
    }

    /**
     * @return a predicate that filters the list by the items for which the total
     * karma is lesser or equal to the given amount
     */
    public Predicate<KarmaListSubreddit> filterByKarmaLesserEqual(int karma) {
        return kls -> kls.getTotalKarma() <= karma;
    }

    /**
     * @return a predicate that filters the list by the given subreddit names
     */
    public Predicate<KarmaListSubreddit> filterBySubredditName(String... subreddits) {
        return kls -> List.of(subreddits).contains(kls.getName());
    }

    //================================================================================
    // Override Methods
    //================================================================================

    /**
     * {@inheritDoc}
     */
    @Override
    public KarmaList filter() {
        if (getList() != null) {
            getList().removeIf(predicate);
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
