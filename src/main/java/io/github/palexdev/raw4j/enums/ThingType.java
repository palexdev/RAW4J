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

package io.github.palexdev.raw4j.enums;

import com.google.gson.annotations.SerializedName;
import io.github.palexdev.raw4j.data.base.Thing;
import io.github.palexdev.raw4j.data.listing.KarmaList;
import io.github.palexdev.raw4j.data.listing.TrophyList;
import io.github.palexdev.raw4j.data.listing.UserList;

import java.util.Arrays;

/**
 * Enumeration of all the {@link Thing}s types.
 */
public enum ThingType {

    /**
     * According to Reddit API documentation this is for comments.
     */
    @SerializedName("t1")
    T1("t1"),

    /**
     * According to Reddit API documentation this is for accounts.
     */
    @SerializedName("t2")
    T2("t2"),

    /**
     * According to Reddit API documentation this is for links/posts.
     */
    @SerializedName("t3")
    T3("t3"),

    /**
     * According to Reddit API documentation this is for messages.
     */
    @SerializedName("t4")
    T4("t4"),

    /**
     * According to Reddit API documentation this is for subreddits.
     */
    @SerializedName("t5")
    T5("t5"),

    /**
     * According to Reddit API documentation this is for awards.
     */
    @SerializedName("t6")
    T6("t6"),

    /**
     * According to Reddit API documentation this is for generic listings
     */
    @SerializedName("Listing")
    LISTING("Listing"),

    /**
     * Not documented but this is for {@link KarmaList}s
     */
    @SerializedName("KarmaList")
    KARMA_LIST("KarmaList"),

    /**
     * Not documented but this is for {@link TrophyList}s
     */
    @SerializedName("TrophyList")
    TROPHY_LIST("TrophyList"),

    /**
     * Not documented but this is for {@link UserList}s
     */
    @SerializedName("UserList")
    USER_LIST("UserList")
    ;

    private final String s;

    ThingType(String s) {
        this.s = s;
    }

    /**
     * Tries to parse the correct ThingType from the given String,
     * in case it fails, null is returned.
     */
    public static ThingType from(String name) {
        return Arrays.stream(values())
                .filter(t -> t.toString().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }

    /**
     * Converts a {@link SubredditType} to a thing type.
     */
    public static ThingType from(SubmissionType submissionType) {
        return submissionType == SubmissionType.COMMENT ? T1 : T3;
    }

    @Override
    public String toString() {
        return s;
    }
}
