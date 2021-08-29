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

import io.github.palexdev.raw4j.data.base.Submission;
import io.github.palexdev.raw4j.data.listing.Overview;
import io.github.palexdev.raw4j.utils.filtering.base.AbstractFilterHelper;

import java.util.List;
import java.util.function.Predicate;

/**
 * Filter helper for {@link Overview}s, extends {@link AbstractFilterHelper}.
 */
public class OverviewFilterHelper extends AbstractFilterHelper<Overview, Submission> {
    //================================================================================
    // Properties
    //================================================================================
    private final Overview overview;

    //================================================================================
    // Constructors
    //================================================================================
    public OverviewFilterHelper(Overview overview) {
        this.overview = overview;
    }

    public static OverviewFilterHelper filtering(Overview overview) {
        return new OverviewFilterHelper(overview);
    }

    //================================================================================
    // Predicates
    //================================================================================

    /**
     * @return a predicate that filters the list by archived comments
     */
    public static Predicate<Submission> filterByArchived() {
        return Submission::isArchived;
    }

    /**
     * @return a predicate that filters the list by the given author
     */
    public static Predicate<Submission> filterByAuthor(String author) {
        return submission -> submission.getAuthor().equals(author);
    }

    /**
     * @return a predicate that filters the list by edited comments
     */
    public static Predicate<Submission> filterByEdited() {
        return Submission::hasBeenEdited;
    }

    /**
     * @return a predicate that filters the list by nsfw comments
     */
    public static Predicate<Submission> filterByNsfw() {
        return Submission::isOver18;
    }

    /**
     * @return a predicate that filters the list by saved comments
     * @see Submission#isSaved()
     */
    public static Predicate<Submission> filterBySaved() {
        return Submission::isSaved;
    }

    /**
     * @return a predicate that filters the list by the items for which the score
     * is lesser or equal to the given amount
     */
    public static Predicate<Submission> filterByScoreGreaterEqual(int score) {
        return submission -> submission.getScore() >= score;
    }

    /**
     * @return a predicate that filters the list by the items for which the score
     * is greater or equal to the given amount
     */
    public static Predicate<Submission> filterByScoreLesserEqual(int score) {
        return submission -> submission.getScore() <= score;
    }

    /**
     * @return a predicate that filters the list by the given subreddit names
     */
    public static Predicate<Submission> filterBySubreddit(String... subreddits) {
        return submission -> List.of(subreddits).contains(submission.getSubreddit());
    }

    /**
     * @return a predicate that filters the list by upvoted comments
     * @see Submission#likes()
     */
    public static Predicate<Submission> filterByUpvoted() {
        return Submission::likes;
    }

    //================================================================================
    // Methods
    //================================================================================

    /**
     * Checks if both the comments list and the posts list are empty.
     */
    private boolean areEmpty() {
        return overview.posts().isEmpty() && overview.comments().isEmpty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Overview filter() {
        if (getList() != null && !areEmpty()) {
            getList().removeIf(predicate);
        }
        return overview;
    }

    /**
     * @return the list of {@link Submission}s
     */
    @Override
    public List<Submission> getList() {
        return overview.submissions();
    }
}
