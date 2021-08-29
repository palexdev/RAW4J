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

import io.github.palexdev.raw4j.data.Comment;
import io.github.palexdev.raw4j.data.Post;
import io.github.palexdev.raw4j.data.base.AbstractListing;
import io.github.palexdev.raw4j.data.base.Listing;
import io.github.palexdev.raw4j.data.base.Submission;
import io.github.palexdev.raw4j.enums.OverviewType;
import io.github.palexdev.raw4j.json.annotations.Wrapped;
import io.github.palexdev.raw4j.utils.filtering.OverviewFilterHelper;
import io.github.palexdev.raw4j.utils.filtering.base.Filterable;
import io.github.palexdev.raw4j.utils.sorting.OverviewSortHelper;
import io.github.palexdev.raw4j.utils.sorting.base.Sortable;

import java.util.List;
import java.util.stream.Stream;

/**
 * This data structure represents a {@link Listing} of type {@link OverviewType}.
 * <p></p>
 * This class offers two lists of {@link Submission}s, one for {@link Post}s and one for {@link Comment}s.
 * <p></p>
 * Implements {@link Filterable} and {@link Sortable}
 */
@Wrapped("data")
public class Overview extends AbstractListing implements Filterable, Sortable {
    //================================================================================
    // Properties
    //================================================================================
    private transient final OverviewFilterHelper filterHelper = OverviewFilterHelper.filtering(this);
    private transient final OverviewSortHelper sortHelper = OverviewSortHelper.sorting(this);
    private transient OverviewType type;

    private transient List<Comment> comments;
    private transient List<Post> posts;

    //================================================================================
    // Methods
    //================================================================================

    /**
     * @return the instance of the {@link OverviewFilterHelper} to filter the list of submissions
     */
    @Override
    public OverviewFilterHelper filtering() {
        return filterHelper;
    }

    /**
     * @return the instance of the {@link OverviewSortHelper} to sort the list of submissions
     */
    @Override
    public OverviewSortHelper sorting() {
        return sortHelper;
    }

    //================================================================================
    // Getters
    //================================================================================

    /**
     * @return the list of comments
     */
    public List<Comment> comments() {
        return comments;
    }

    /**
     * @return the list of posts
     */
    public List<Post> posts() {
        return posts;
    }

    /**
     * @return a new list that combines both {@link #comments()} and {@link #posts()}
     */
    public List<Submission> submissions() {
        return Stream.concat(comments.stream(), posts.stream()).toList();
    }

    /**
     * @return the type of this Overview
     * @see OverviewType
     */
    public OverviewType getOverviewType() {
        return type;
    }

    /**
     * Sets this Overview type. Since this property is not coming from Reddit, but rather
     * on the RAW4J side, to avoid code duplication, after the PostList has been retrieved
     * from the server it's needed to set its type. This is done automatically by RAW4J, any
     * subsequent call of this method won't have any effect.
     */
    public void setOverviewType(OverviewType type) {
        this.type = type;
    }
}
