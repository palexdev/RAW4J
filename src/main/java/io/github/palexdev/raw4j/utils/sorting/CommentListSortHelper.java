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

import io.github.palexdev.raw4j.data.Comment;
import io.github.palexdev.raw4j.data.base.Submission;
import io.github.palexdev.raw4j.data.listing.CommentList;
import io.github.palexdev.raw4j.utils.sorting.base.AbstractSortingHelper;

import java.util.Comparator;
import java.util.List;

/**
 * Sort helper for {@link CommentList}s, extends {@link AbstractSortingHelper}.
 */
public class CommentListSortHelper extends AbstractSortingHelper<CommentList, Comment> {
    //================================================================================
    // Properties
    //================================================================================
    private final CommentList commentList;

    //================================================================================
    // Constructors
    //================================================================================
    public CommentListSortHelper(CommentList commentList) {
        this.commentList = commentList;
    }

    public static CommentListSortHelper sorting(CommentList commentList) {
        return new CommentListSortHelper(commentList);
    }

    //================================================================================
    // Comparators
    //================================================================================

    /**
     * @return a comparator that sorts the comments by the time they were made
     */
    public static Comparator<Comment> sortByCreatedTime() {
        return Comparator.comparing(Submission::getCreatedTime);
    }

    /**
     * @return a comparator that sorts the comments by NSFW
     */
    public  static Comparator<Comment> sortByOver18() {
        return Comparator.comparing(Submission::isOver18);
    }

    /**
     * @return a comparator that sorts the comments by score
     */
    public static Comparator<Comment> sortByScore() {
        return Comparator.comparing(Submission::getScore);
    }

    /**
     * @return a comparator that sorts the comments by subreddit
     */
    public static Comparator<Comment> sortBySubreddit() {
        return Comparator.comparing(Submission::getSubreddit);
    }

    //================================================================================
    // Methods
    //================================================================================

    /**
     * {@inheritDoc}
     */
    @Override
    public CommentList sort() {
        if (getList() != null) {
            getList().sort(comparator);
        }
        return commentList;
    }

    /**
     * @return the list of {@link Comment}s
     */
    @Override
    public List<Comment> getList() {
        return commentList.comments();
    }
}
