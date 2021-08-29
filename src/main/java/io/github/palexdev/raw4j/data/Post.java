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
import io.github.palexdev.raw4j.data.media.Preview;
import io.github.palexdev.raw4j.data.media.RedditVideo;
import io.github.palexdev.raw4j.enums.PostType;
import io.github.palexdev.raw4j.enums.ThingType;
import io.github.palexdev.raw4j.json.annotations.Wrapped;

import java.util.ArrayList;
import java.util.List;

/**
 * Data structure to represent Reddit's posts.
 * <p></p>
 * Extends {@link Submission}, it's a {@link Thing} of type {@link ThingType#T3}
 */
@Wrapped("data")
public class Post extends Submission {
    //================================================================================
    // Properties
    //================================================================================
    @SerializedName("author_fullname")
    private String authorFullname;

    private final List<Collection> collections = new ArrayList<>();

    @SerializedName("is_crosspostable")
    private Boolean crosspostable;

    @SerializedName("is_followed")
    private boolean followed;

    @SerializedName("is_gallery")
    private boolean gallery;

    private boolean hidden;

    @SerializedName("hide_score")
    private boolean hideScore;

    private boolean locked;

    @SerializedName("media")
    private RedditVideo redditVideo;

    @SerializedName("num_crossposts")
    private int numCrossPosts;

    @SerializedName("is_original_content")
    private boolean originalContent;

    private boolean pinned;
    private Preview preview;

    @SerializedName("post_hint")
    private PostType postType;

    @SerializedName("is_robot_indexable")
    private boolean robotIndexable;

    @SerializedName("is_self")
    private Boolean self;

    private Boolean spoiler;

    @SerializedName("selftext")
    private String text;
    private String title;

    @SerializedName("upvote_ratio")
    private float upvoteRatio;

    private String url;

    //================================================================================
    // Getters
    //================================================================================

    /**
     * @return the author name prepended with "t2_",
     * not available if the post was removed or deleted
     */
    public String getAuthorFullname() {
        return authorFullname;
    }

    /**
     * @return the collections list of this post
     */
    public List<Collection> getCollections() {
        return collections;
    }

    /**
     * @return whether the post is crosspostable,
     * false if the post was removed or deleted
     */
    public Boolean isCrosspostable() {
        return crosspostable;
    }

    /**
     * @return true if the post is being followed by the current user,
     * false if the post is not being followed or there is no user context (no logged user).
     */
    public boolean isFollowed() {
        return followed;
    }

    /**
     * @return whether the post is a gallery post,
     * false if it's a crosspost to a gallery post
     */
    public boolean isGallery() {
        return gallery;
    }

    /**
     * @return whether the post is hidden
     */
    public boolean isHidden() {
        return hidden;
    }

    /**
     * @return whether the score is hidden
     */
    public boolean isScoreHidden() {
        return hideScore;
    }

    /**
     * @return whether the post is locked
     */
    public boolean isLocked() {
        return locked;
    }

    /**
     * @return the {@link RedditVideo} object of this post,
     * null if it's not a video post, check with {@link #isVideo()}
     */
    public RedditVideo getVideo() {
        return redditVideo;
    }

    /**
     * @return the number of crossposts
     */
    public int getNumCrossPosts() {
        return numCrossPosts;
    }

    /**
     * @return whether the post is marked as OC
     */
    public boolean isOriginalContent() {
        return originalContent;
    }

    /**
     * @return whether the post is pinned to the
     * poster's profile
     */
    public boolean isPinned() {
        return pinned;
    }

    /**
     * @return the {@link Preview} object of this post,
     * null if the post has been removed or deleted or if it's
     * a text post, check with {@link #getPostType()}
     */
    public Preview getPreview() {
        return preview;
    }

    /**
     * @return the type of post,
     * null if it's a gallery post or if the
     * preview is not present
     */
    public PostType getPostType() {
        return postType;
    }

    /**
     * @return whether the post is robot-indexable,
     * false if the post was removed or deleted
     */
    public boolean isRobotIndexable() {
        return robotIndexable;
    }

    /**
     * @return true if it's a text post,
     * false if it's a crosspost to a text post
     */
    public Boolean isSelf() {
        return self;
    }

    /**
     * @return whether the post is marked as a spoiler
     */
    public Boolean isSpoiler() {
        return spoiler;
    }

    /**
     * @return the post's text
     */
    public String getText() {
        return text;
    }

    /**
     * @return the post's title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @return the upvote/downvote ratio
     */
    public float getUpvoteRatio() {
        return upvoteRatio;
    }

    /**
     * @return the url of the post
     */
    public String getUrl() {
        return url;
    }

    /**
     * @return whether the post is a video post
     */
    public Boolean isVideo() {
        return postType == PostType.VIDEO;
    }
}
