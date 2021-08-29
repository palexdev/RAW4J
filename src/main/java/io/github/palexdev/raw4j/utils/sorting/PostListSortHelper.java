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

import io.github.palexdev.raw4j.data.Post;
import io.github.palexdev.raw4j.data.base.Submission;
import io.github.palexdev.raw4j.data.listing.PostList;
import io.github.palexdev.raw4j.enums.PostType;
import io.github.palexdev.raw4j.utils.sorting.base.AbstractSortingHelper;

import java.util.Comparator;
import java.util.List;

/**
 * Sort helper for {@link PostList}s, extends {@link AbstractSortingHelper}.
 */
public class PostListSortHelper extends AbstractSortingHelper<PostList, Post> {
    //================================================================================
    // Properties
    //================================================================================
    private final PostList postList;

    //================================================================================
    // Constructors
    //================================================================================
    public PostListSortHelper(PostList postList) {
        this.postList = postList;
    }

    public static PostListSortHelper sorting(PostList postList) {
        return new PostListSortHelper(postList);
    }

    //================================================================================
    // Comparators
    //================================================================================

    /**
     * @return a comparator that sorts the posts by the time they were made
     */
    public static Comparator<Post> sortByCreatedTime() {
        return Comparator.comparing(Submission::getCreatedTime);
    }

    /**
     * @return a comparator that sorts the posts by NSFW
     */
    public static Comparator<Post> sortByOver18() {
        return Comparator.comparing(Submission::isOver18);
    }

    /**
     * @return a comparator that sorts the posts by their pinned state
     */
    public static Comparator<Post> sortByPinned() {
        return Comparator.comparing(Post::isPinned);
    }

    /**
     * @return a comparator that sorts the posts by score
     */
    public static Comparator<Post> sortByScore() {
        return Comparator.comparing(Submission::getScore);
    }

    /**
     * @return a comparator that sorts the posts by subreddit
     */
    public static Comparator<Post> sortBySubreddit() {
        return Comparator.comparing(Submission::getSubreddit);
    }

    /**
     * @return a comparator that sorts the posts by title
     */
    public static Comparator<Post> sortByTitle() {
        return Comparator.comparing(Post::getTitle);
    }

    /**
     * @return a comparator that sorts the posts by type.
     * @see PostType
     */
    public static Comparator<Post> sortByType() {
        return Comparator.comparing(Post::getPostType);
    }

    //================================================================================
    // Methods
    //================================================================================

    /**
     * {@inheritDoc}
     */
    @Override
    public PostList sort() {
        if (getList() != null) {
            getList().sort(comparator);
        }
        return postList;
    }

    /**
     * @return the list of {@link Post}s
     */
    @Override
    public List<Post> getList() {
        return postList.posts();
    }
}
