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

import io.github.palexdev.raw4j.data.Comment;
import io.github.palexdev.raw4j.data.base.Submission;
import io.github.palexdev.raw4j.data.listing.CommentList;
import io.github.palexdev.raw4j.utils.filtering.base.AbstractFilterHelper;

import java.util.List;
import java.util.function.Predicate;

/**
 * Filter helper for {@link CommentList}s, extends {@link AbstractFilterHelper}.
 */
public class CommentListFilterHelper extends AbstractFilterHelper<CommentList, Comment> {
    //================================================================================
    // Properties
    //================================================================================
    private final CommentList commentList;

    //================================================================================
    // Constructors
    //================================================================================
    public CommentListFilterHelper(CommentList commentList) {
        this.commentList = commentList;
    }

    public static CommentListFilterHelper filtering(CommentList commentList) {
        return new CommentListFilterHelper(commentList);
    }

    //================================================================================
    // Predicates
    //================================================================================

    /**
     * @return a predicate that filters the list by archived comments
     */
    public static Predicate<Comment> filterByArchived() {
        return Comment::isArchived;
    }

    /**
     * @return a predicate that filters the list by the given author
     */
    public static Predicate<Comment> filterByAuthor(String author) {
        return comment -> comment.getAuthor().equals(author);
    }

    /**
     * @return a predicate that filters the list by edited comments
     */
    public static Predicate<Comment> filterByEdited() {
        return Comment::hasBeenEdited;
    }

    /**
     * @return a predicate that filters the list by nsfw comments
     */
    public static Predicate<Comment> filterByNsfw() {
        return Comment::isOver18;
    }

    /**
     * @return a predicate that filters the list by saved comments
     * @see Submission#isSaved()
     */
    public static Predicate<Comment> filterBySaved() {
        return Comment::isSaved;
    }

    /**
     * @return a predicate that filters the list by the items for which the score
     * is lesser or equal to the given amount
     */
    public static Predicate<Comment> filterByScoreGreaterEqual(int score) {
        return comment -> comment.getScore() >= score;
    }

    /**
     * @return a predicate that filters the list by the items for which the score
     * is greater or equal to the given amount
     */
    public static Predicate<Comment> filterByScoreLesserEqual(int score) {
        return comment -> comment.getScore() <= score;
    }

    /**
     * @return a predicate that filters the list by the given subreddit names
     */
    public static Predicate<Comment> filterBySubreddit(String... subreddits) {
        return comment -> List.of(subreddits).contains(comment.getSubreddit());
    }

    /**
     * @return a predicate that filters the list by stickied comments
     */
    public static Predicate<Comment> filterByStickied() {
        return Comment::isStickied;
    }

    /**
     * @return a predicate that filters the list by upvoted comments
     * @see Submission#likes()
     */
    public static Predicate<Comment> filterByUpvoted() {
        return Comment::likes;
    }

    //================================================================================
    // Override Methods
    //================================================================================

    /**
     * {@inheritDoc}
     */
    @Override
    public CommentList filter() {
        if (getList() != null) {
            getList().removeIf(predicate);
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
