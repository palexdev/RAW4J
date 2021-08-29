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
import io.github.palexdev.raw4j.data.Post;
import io.github.palexdev.raw4j.data.base.AbstractListing;
import io.github.palexdev.raw4j.data.base.Listing;
import io.github.palexdev.raw4j.enums.PostListType;
import io.github.palexdev.raw4j.json.annotations.Wrapped;
import io.github.palexdev.raw4j.utils.filtering.PostListFilterHelper;
import io.github.palexdev.raw4j.utils.filtering.base.Filterable;
import io.github.palexdev.raw4j.utils.sorting.PostListSortHelper;
import io.github.palexdev.raw4j.utils.sorting.base.Sortable;

import java.util.List;

/**
 * This data structure represents a {@link Listing} of type {@link PostListType}.
 * <p></p>
 * This class offers a list of posts.
 * <p></p>
 * Implements {@link Filterable} and {@link Sortable}.
 */
@Wrapped("data")
public class PostList extends AbstractListing implements Filterable, Sortable {
    //================================================================================
    // Properties
    //================================================================================
    private transient final PostListFilterHelper filterHelper = PostListFilterHelper.filtering(this);
    private transient final PostListSortHelper sortHelper = PostListSortHelper.sorting(this);
    private transient PostListType postListType;

    @SerializedName("children")
    private List<Post> posts;

    //================================================================================
    // Methods
    //================================================================================

    /**
     * @return the instance of the {@link PostListFilterHelper} to filter the list of posts
     */
    @Override
    public PostListFilterHelper filtering() {
        return filterHelper;
    }

    /**
     * @return the instance of the {@link PostListSortHelper} to sort the list of posts
     */
    @Override
    public PostListSortHelper sorting() {
        return sortHelper;
    }

    //================================================================================
    // Getters
    //================================================================================

    /**
     * @return the list of posts
     */
    public List<Post> posts() {
        return posts;
    }

    /**
     * @return the type of this PostList
     * @see PostListType
     */
    public PostListType getPostListType() {
        return postListType;
    }

    /**
     * Sets this PostList type. Since this property is not coming from Reddit, but rather
     * on the RAW4J side, to avoid code duplication, after the PostList has been retrieved
     * from the server it's needed to set its type. This is done automatically by RAW4J, any
     * subsequent call of this method won't have any effect.
     */
    public void setPostListType(PostListType type) {
        if (postListType != null) {
            return;
        }
        postListType = type;
    }
}
