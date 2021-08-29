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

package io.github.palexdev.raw4j.data.base;

import com.google.gson.annotations.SerializedName;
import io.github.palexdev.raw4j.data.Award;
import io.github.palexdev.raw4j.data.Comment;
import io.github.palexdev.raw4j.data.Gildings;
import io.github.palexdev.raw4j.data.Post;
import io.github.palexdev.raw4j.enums.SubredditType;
import io.github.palexdev.raw4j.json.annotations.Wrapped;

import java.util.List;

/**
 * Base class for all submissions.
 * Submission can either be {@link Post}s or {@link Comment}s;
 */
@Wrapped("data")
public abstract class Submission extends AbstractThing {
    //================================================================================
    // Properties
    //================================================================================
    @SerializedName("all_awardings")
    protected List<Award> awards;

    protected Boolean archived;
    protected String author;

    @SerializedName("created_utc")
    protected Long createdTime;

    protected String edited;
    protected Gildings gildings;
    protected Boolean likes;

    @SerializedName("num_comments")
    protected Integer numComments;

    @SerializedName("over_18")
    protected Boolean over18;

    protected Boolean saved;
    protected Integer score;
    protected String subreddit;

    @SerializedName("subreddit_id")
    protected String subredditID;

    @SerializedName("subreddit_type")
    protected SubredditType subredditType;

    //================================================================================
    // Getters
    //================================================================================

    /**
     * @return a list containing all the awards the submission has received
     */
    public List<Award> getAwards() {
        return awards;
    }

    /**
     * @return whether the submission is archived
     */
    public Boolean isArchived() {
        return archived;
    }

    /**
     * @return the submission's author
     */
    public String getAuthor() {
        return author;
    }

    /**
     * @return the Unix timestamp of when the submission was made
     */
    public Long getCreatedTime() {
        return createdTime;
    }

    /**
     * @return whether the submission has been edited
     */
    public Boolean hasBeenEdited() {
        Long val = editedTime();
        return val != -1L;
    }

    /**
     * @return the Unix timestamp of when the submission was edited,
     * or -1L if the submission was not edited
     */
    public Long editedTime() {
        try {
            return Long.parseLong(edited);
        } catch (NumberFormatException ex) {
            return -1L;
        }
    }

    /**
     * @return info about the gildings the submission has received
     */
    public Gildings getGildings() {
        return gildings;
    }

    /**
     * Returns:
     * <p> - null if the logged user has not voted the submission
     * <p> - true if the logged user has upvoted the submission
     * <p> - false if the logged user has downvoted the submission
     * <p></p>
     * Note that this will also return null if there's no user context (no logged user)
     */
    public Boolean likes() {
        return likes;
    }

    /**
     * @return the number of comments
     */
    public Integer getNumComments() {
        return numComments;
    }

    /**
     * @return whether the submission is NSFW
     */
    public Boolean isOver18() {
        return over18;
    }

    /**
     * @return whether the logged user has saved the submission. Always null if
     * there's no user context (no logged user)
     */
    public Boolean isSaved() {
        return saved;
    }

    /**
     * @return the number of upvotes (minus downvotes)
     */
    public Integer getScore() {
        return score;
    }

    /**
     * @return the subreddit's name
     */
    public String getSubreddit() {
        return subreddit;
    }

    /**
     * @return the subreddit's ID
     */
    public String getSubredditID() {
        return subredditID;
    }

    /**
     * @return the subreddit's type, see {@link SubredditType}
     */
    public SubredditType getSubredditType() {
        return subredditType;
    }

    @Override
    public String getName() {
        return getType() + "_" + getID();
    }
}
