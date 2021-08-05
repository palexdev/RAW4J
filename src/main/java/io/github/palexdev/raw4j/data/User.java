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
import io.github.palexdev.raw4j.data.base.AbstractThing;
import io.github.palexdev.raw4j.data.base.T2Data;
import io.github.palexdev.raw4j.json.JsonPathExpression;

import java.io.Serializable;

public class User extends AbstractThing implements T2Data, Serializable {
    //================================================================================
    // Properties
    //================================================================================
    @JsonPathExpression("data.awardee_karma")
    @SerializedName("awardee_karma")
    private int awardeeKarma;

    @JsonPathExpression("data.awarder_karma")
    @SerializedName("awarder_karma")
    private int awarderKarma;

    @JsonPathExpression("data.comment_karma")
    @SerializedName("comment_karma")
    private int commentKarma;

    @JsonPathExpression("data.has_mail")
    @SerializedName("has_mail")
    private boolean unreadMail;

    @JsonPathExpression("data.has_mod_mail")
    @SerializedName("has_mod_mail")
    private boolean unreadModMail;

    @JsonPathExpression("data.has_verified_email")
    @SerializedName("has_verified_email")
    private boolean verifiedEmail;

    @JsonPathExpression("data.inbox_count")
    @SerializedName("inbox_count")
    private Integer inboxCount;

    @JsonPathExpression("data.is_friend")
    @SerializedName("is_friend")
    private Boolean friend;

    @JsonPathExpression("data.is_gold")
    @SerializedName("is_gold")
    private boolean gold;

    @JsonPathExpression("data.is_mod")
    @SerializedName("is_mod")
    private boolean mod;

    @JsonPathExpression("data.link_karma")
    @SerializedName("link_karma")
    private int linkKarma;

    @JsonPathExpression("data.modhash")
    @SerializedName("modhash")
    private String modhash;

    @JsonPathExpression("data.over_18")
    @SerializedName("over_18")
    private boolean over18;

    //================================================================================
    // Getters
    //================================================================================
    @Override
    public int getAwardeeKarma() {
        return awardeeKarma;
    }

    @Override
    public int getAwarderKarma() {
        return awarderKarma;
    }

    @Override
    public int getCommentKarma() {
        return commentKarma;
    }

    @Override
    public boolean hasUnreadMail() {
        return unreadMail;
    }

    @Override
    public boolean hasUnreadModMail() {
        return unreadModMail;
    }

    @Override
    public boolean isEmailVerified() {
        return verifiedEmail;
    }

    @Override
    public Integer getInboxCount() {
        return inboxCount;
    }

    @Override
    public Boolean isFriend() {
        return friend;
    }

    @Override
    public boolean isGold() {
        return gold;
    }

    @Override
    public boolean isMod() {
        return mod;
    }

    @Override
    public int getLinkKarma() {
        return linkKarma;
    }

    @Override
    public String getModhash() {
        return modhash;
    }

    @Override
    public boolean isOver18() {
        return over18;
    }

    @Override
    public int getTotalKarma() {
        return awardeeKarma + awarderKarma + commentKarma + linkKarma;
    }
}
