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

package io.github.palexdev.raw4j.data;

import com.google.gson.annotations.SerializedName;
import io.github.palexdev.raw4j.data.base.Submission;
import io.github.palexdev.raw4j.data.base.Thing;
import io.github.palexdev.raw4j.enums.ThingType;
import io.github.palexdev.raw4j.json.annotations.Wrapped;

// TODO check replies
/**
 * Data structure to represent Reddit's comments.
 * <p></p>
 * Extends {@link Submission}, it's a {@link Thing} of type {@link ThingType#T1}
 */
@Wrapped("data")
public class Comment extends Submission {
    //================================================================================
    // Properties
    //================================================================================
    private String body;

    @SerializedName("parent_id")
    private String parentID;

    @SerializedName("link_permalink")
    private String permalink;

    private Boolean stickied;

    @SerializedName("link_id")
    private String submissionID;

    @SerializedName("is_submitter")
    private Boolean submitter;

    //================================================================================
    // Getters
    //================================================================================

    /**
     * @return the comment's text
     */
    public String getBody() {
        return body;
    }

    /**
     * @return the ID of the comment's parent
     */
    public String getParentID() {
        return parentID;
    }

    /**
     * @return the url of the comment
     */
    public String getUrl() {
        return permalink;
    }

    /**
     * @return whether the comment is stickied
     */
    public Boolean isStickied() {
        return stickied;
    }

    /**
     * @return the link of the submission this comment belongs to
     */
    public String getSubmissionID() {
        return submissionID;
    }

    /**
     * @return true if the author is the submission author
     */
    public Boolean isSubmitter() {
        return submitter;
    }
}
