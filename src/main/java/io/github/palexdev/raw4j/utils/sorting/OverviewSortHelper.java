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

import io.github.palexdev.raw4j.data.base.Submission;
import io.github.palexdev.raw4j.data.listing.Overview;
import io.github.palexdev.raw4j.enums.SubmissionType;
import io.github.palexdev.raw4j.enums.ThingType;
import io.github.palexdev.raw4j.utils.sorting.base.AbstractSortingHelper;

import java.util.Comparator;
import java.util.List;

/**
 * Sort helper for {@link Overview}s, extends {@link AbstractSortingHelper}.
 */
public class OverviewSortHelper extends AbstractSortingHelper<Overview, Submission> {
    //================================================================================
    // Properties
    //================================================================================
    private final Overview overview;

    //================================================================================
    // Constructors
    //================================================================================
    public OverviewSortHelper(Overview overview) {
        this.overview = overview;
    }

    public static OverviewSortHelper sorting(Overview overview) {
        return new OverviewSortHelper(overview);
    }

    //================================================================================
    // Comparators
    //================================================================================

    /**
     * @return a comparator that sorts the submission by the time they were made
     */
    public static Comparator<Submission> sortByCreatedTime() {
        return Comparator.comparing(Submission::getCreatedTime);
    }

    /**
     * @return a comparator that sorts the submissions by NSFW
     */
    public static Comparator<Submission> sortByOver18() {
        return Comparator.comparing(Submission::isOver18);
    }

    /**
     * @return a comparator that sorts the submissions by score
     */
    public static Comparator<Submission> sortByScore() {
        return Comparator.comparing(Submission::getScore);
    }

    /**
     * @return a comparator that sorts the submissions by subreddit
     */
    public static Comparator<Submission> sortBySubreddit() {
        return Comparator.comparing(Submission::getSubreddit);
    }

    /**
     * @return a comparator that sorts the submissions by type
     *
     * @see SubmissionType
     */
    public static Comparator<Submission> sortByType(SubmissionType type) {
        ThingType thingType = ThingType.from(type);
        return Comparator.comparing(submission -> submission.getType() == thingType);
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
    public Overview sort() {
        if (getList() != null && !areEmpty()) {
            getList().sort(comparator);
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
