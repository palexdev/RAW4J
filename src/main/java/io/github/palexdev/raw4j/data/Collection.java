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

import java.util.List;

/**
 * Data structure to represent Reddit's collections.
 */
public class Collection {

    @SerializedName("author_name")
    private String author;

    @SerializedName("author_id")
    private String authorID;

    @SerializedName("collection_id")
    private String collectionID;

    @SerializedName("created_at_utc")
    private Long createdTime;

    private String description;

    @SerializedName("last_update_utc")
    private Long lastUpdateTime;

    @SerializedName("link_ids")
    private List<String> linkIDs;

    @SerializedName("subreddit_id")
    private String subredditID;

    private String title;

    @SerializedName("permalink")
    private String url;

    /**
     * @return the collection's author
     */
    public String getAuthor() {
        return author;
    }

    /**
     * @return the collection's author ID
     */
    public String getAuthorID() {
        return authorID;
    }

    /**
     * @return the collection's ID
     */
    public String getCollectionID() {
        return collectionID;
    }

    /**
     * @return the UNIX timestamp of when the collection was created
     */
    public Long getCreatedTime() {
        return createdTime;
    }

    /**
     * @return the collection's description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return the UNIX timestamp of when the last post was added
     */
    public Long getLastUpdateTime() {
        return lastUpdateTime;
    }

    /**
     * @return the full IDs of the submissions contained in the collection
     */
    public List<String> getLinkIDs() {
        return linkIDs;
    }

    /**
     * @return the full ID of the subreddit
     */
    public String getSubredditID() {
        return subredditID;
    }

    /**
     * @return the collection's title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @return the collection's link
     */
    public String getUrl() {
        return url;
    }
}
