package io.github.palexdev.raw4j.data;

import com.google.gson.annotations.SerializedName;
import io.github.palexdev.raw4j.data.base.AbstractThing;
import io.github.palexdev.raw4j.data.base.T2Data;
import io.github.palexdev.raw4j.json.JsonPathExpression;

import java.io.Serializable;

public class AccountData extends AbstractThing implements T2Data, Serializable {

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

    public AccountData() {
        super();
    }

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
