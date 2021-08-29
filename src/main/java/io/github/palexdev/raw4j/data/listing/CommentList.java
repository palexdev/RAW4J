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
import io.github.palexdev.raw4j.data.Comment;
import io.github.palexdev.raw4j.data.base.AbstractListing;
import io.github.palexdev.raw4j.data.base.Listing;
import io.github.palexdev.raw4j.json.annotations.Wrapped;
import io.github.palexdev.raw4j.utils.filtering.CommentListFilterHelper;
import io.github.palexdev.raw4j.utils.filtering.base.Filterable;
import io.github.palexdev.raw4j.utils.sorting.CommentListSortHelper;
import io.github.palexdev.raw4j.utils.sorting.base.Sortable;

import java.util.List;

/**
 * This data structure represents a {@link Listing} of {@link Comment}s.
 * <p></p>
 * Implements {@link Filterable} and {@link Sortable}.
 */
@Wrapped("data")
public class CommentList extends AbstractListing implements Filterable, Sortable {
    //================================================================================
    // Properties
    //================================================================================
    private transient final CommentListFilterHelper filterHelper = CommentListFilterHelper.filtering(this);
    private transient final CommentListSortHelper sortHelper = CommentListSortHelper.sorting(this);

    @SerializedName("children")
    private List<Comment> comments;

    //================================================================================
    // Methods
    //================================================================================

    /**
     * @return the instance of the {@link CommentListFilterHelper} to filter the list of comments
     */
    @Override
    public CommentListFilterHelper filtering() {
        return filterHelper;
    }

    /**
     * @return the instance of the {@link CommentListSortHelper} to sort the list of comments
     */
    @Override
    public CommentListSortHelper sorting() {
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
}
