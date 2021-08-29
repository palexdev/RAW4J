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
import io.github.palexdev.raw4j.api.AccountApi;
import io.github.palexdev.raw4j.api.UserApi;
import io.github.palexdev.raw4j.data.base.AbstractThing;
import io.github.palexdev.raw4j.data.base.Thing;
import io.github.palexdev.raw4j.enums.ThingType;
import io.github.palexdev.raw4j.json.annotations.Wrapped;

/**
 * This data structure contains all the info about a user.
 * <p>
 * It's a {@link Thing} of type {@link ThingType#T2}.
 */
@Wrapped("data")
public class User extends AbstractThing {
    //================================================================================
    // Properties
    //================================================================================
    @SerializedName("awardee_karma")
    private int awardeeKarma;

    @SerializedName("awarder_karma")
    private int awarderKarma;

    @SerializedName("comment_karma")
    private int commentKarma;

    @SerializedName("has_mail")
    private boolean unreadMail;

    @SerializedName("has_mod_mail")
    private boolean unreadModMail;

    @SerializedName("has_verified_email")
    private boolean verifiedEmail;

    @SerializedName("inbox_count")
    private Integer inboxCount;

    @SerializedName("is_friend")
    private Boolean friend;

    @SerializedName("is_gold")
    private boolean gold;

    @SerializedName("is_mod")
    private boolean mod;

    @SerializedName("link_karma")
    private int linkKarma;

    @SerializedName("modhash")
    private String modhash;

    @SerializedName("over_18")
    private boolean over18;

    @SerializedName("name")
    private String username;

    //================================================================================
    // Getters
    //================================================================================

    /**
     * @return the user's karma from received awards
     */
    public int getAwardeeKarma() {
        return awardeeKarma;
    }

    /**
     * @return the user's karma from given awards
     */
    public int getAwarderKarma() {
        return awarderKarma;
    }

    /**
     * @return the user's comment karma
     */
    public int getCommentKarma() {
        return commentKarma;
    }

    /**
     * @return whether the user has unread mails. Always false if not your account
     */
    public boolean hasUnreadMail() {
        return unreadMail;
    }

    /**
     * @return whether the user has unread mod mails. Always false if not your account
     */
    public boolean hasUnreadModMail() {
        return unreadModMail;
    }

    /**
     * @return whether the user's email is verified
     */
    public boolean isEmailVerified() {
        return verifiedEmail;
    }

    /**
     * @return the number of unread messages in the inbox. Null if not your account
     */
    public Integer getInboxCount() {
        return inboxCount;
    }

    /**
     * @return whether the logged-in user has this user set as a friend. This is null if calling {@link AccountApi#getMe()}
     * or false if calling {@link UserApi#getUser(String)} on your own account
     */
    public Boolean isFriend() {
        return friend;
    }

    /**
     * @return the reddit gold status
     */
    public boolean isGold() {
        return gold;
    }

    /**
     * @return whether this account moderates any subreddits
     */
    public boolean isMod() {
        return mod;
    }

    /**
     * @return the user's link karma
     */
    public int getLinkKarma() {
        return linkKarma;
    }

    /**
     * @return the current modhash. Null if not your account
     */
    public String getModhash() {
        return modhash;
    }

    /**
     * @return whether this account is set to be over 18
     */
    public boolean isOver18() {
        return over18;
    }

    /**
     * @return the Reddit username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @return the sum of {@link #getAwardeeKarma()}, {@link #getAwarderKarma()}, {@link #getCommentKarma()} and {@link #getLinkKarma()}
     */
    public int getTotalKarma() {
        return awardeeKarma + awarderKarma + commentKarma + linkKarma;
    }

    @Override
    public String getName() {
        return (getType() == null) ? "" : (getType() + "_" + getID());
    }
}
