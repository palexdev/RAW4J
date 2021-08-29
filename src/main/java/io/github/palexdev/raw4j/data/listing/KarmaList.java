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

package io.github.palexdev.raw4j.data.listing;

import com.google.gson.annotations.SerializedName;
import io.github.palexdev.raw4j.data.base.AbstractThing;
import io.github.palexdev.raw4j.enums.ThingType;
import io.github.palexdev.raw4j.utils.filtering.KarmaListFilterHelper;
import io.github.palexdev.raw4j.utils.filtering.base.Filterable;
import io.github.palexdev.raw4j.utils.sorting.KarmaListSortHelper;
import io.github.palexdev.raw4j.utils.sorting.base.Sortable;

import java.util.List;

/**
 * Thing of type {@link ThingType#KARMA_LIST}, implements {@link Filterable} and {@link Sortable}, has empty name(not present in JSON).
 * <p></p>
 * This class offers a breakdown of subreddit karma.
 */
public class KarmaList extends AbstractThing implements Filterable, Sortable {
    //================================================================================
    // Properties
    //================================================================================
    private transient final KarmaListFilterHelper filterHelper = KarmaListFilterHelper.filtering(this);
    private transient final KarmaListSortHelper sortHelper = KarmaListSortHelper.sorting(this);

    @SerializedName("data")
    private List<KarmaListSubreddit> subredditList;

    //================================================================================
    // Methods
    //================================================================================

    /**
     * @return the instance of the {@link KarmaListFilterHelper} to filter the list of subreddits
     */
    @Override
    public KarmaListFilterHelper filtering() {
        return filterHelper;
    }

    /**
     * @return the instance of the {@link KarmaListSortHelper} to sort the list of subreddits
     */
    @Override
    public KarmaListSortHelper sorting() {
        return sortHelper;
    }

    /**
     * @return the list of subreddits
     * @see KarmaListSubreddit
     */
    public List<KarmaListSubreddit> subreddits() {
        return subredditList;
    }
    //================================================================================
    // Getters
    //================================================================================

    @Override
    public String getName() {
        return "";
    }

    //================================================================================
    // Nested Classes
    //================================================================================

    /**
     * This represents the data structure in the 'data' array of the JSON returned by the Reddit API.
     * <p></p>
     * This structure has info about: the subreddit name, the comment karma and link karma accumulated in that subreddit.
     */
    public static class KarmaListSubreddit {
        //================================================================================
        // Properties
        //================================================================================
        @SerializedName("sr")
        private String name;

        @SerializedName("comment_karma")
        private int commentKarma;

        @SerializedName("link_karma")
        private int linkKarma;

        //================================================================================
        // Getters
        //================================================================================
        public String getName() {
            return name;
        }

        public int getCommentKarma() {
            return commentKarma;
        }

        public int getLinkKarma() {
            return linkKarma;
        }

        public int getTotalKarma() {
            return commentKarma + linkKarma;
        }
    }
}
