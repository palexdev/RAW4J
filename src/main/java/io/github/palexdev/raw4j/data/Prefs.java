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
import io.github.palexdev.raw4j.enums.PrefsEnumerators.*;

import java.util.Locale;

/**
 * This enormous class contains all the Reddit's account preferences.
 * <p></p>
 * It also offers a method to update the preferences from another {@code Prefs} instance.
 */
public class Prefs {
    //================================================================================
    // Properties
    //================================================================================
    @SerializedName("accept_pms")
    private AcceptPMsEnum acceptPms;

    @SerializedName("activity_relevant_ads")
    private Boolean activityRelevantAds;

    @SerializedName("allow_clicktracking")
    private Boolean allowClickTracking;

    @SerializedName("beta")
    private Boolean beta;

    @SerializedName("clickgadget")
    private Boolean clickGadget;

    @SerializedName("collapse_read_messages")
    private Boolean collapseReadMessages;

    @SerializedName("compress")
    private Boolean compress;

    @SerializedName("country_code")
    private CountryCode countryCode;

    @SerializedName("creddit_autorenew")
    private Boolean credditAutorenew;

    @SerializedName("default_comment_sort")
    private CommentSort defaultCommentSort;

    @SerializedName("domain_details")
    private Boolean domainDetails;

    @SerializedName("email_chat_request")
    private Boolean emailChatRequest;

    @SerializedName("email_comment_reply")
    private Boolean emailCommentReply;

    @SerializedName("email_digests")
    private Boolean emailDigests;

    @SerializedName("email_messages")
    private Boolean emailMessages;

    @SerializedName("email_post_reply")
    private Boolean emailPostReply;

    @SerializedName("email_private_message")
    private Boolean emailPrivateMessage;

    @SerializedName("email_unsubscribe_all")
    private Boolean emailUnsubscribeAll;

    @SerializedName("email_upvote_comment")
    private Boolean emailUpvoteComment;

    @SerializedName("email_upvote_post")
    private Boolean emailUpvotePost;

    @SerializedName("email_user_new_follower")
    private Boolean emailUserNewFollower;

    @SerializedName("email_username_mention")
    private Boolean emailUsernameMention;

    @SerializedName("enable_default_themes")
    private Boolean enableDefaultThemes;

    @SerializedName("enable_followers")
    private Boolean enableFollowers;

    @SerializedName("feed_recommendations_enabled")
    private Boolean feedRecommendationsEnabled;

    @SerializedName("geopopular")
    private GeoPopular geoPopular;

    @SerializedName("hide_ads")
    private Boolean hideAds;

    @SerializedName("hide_downs")
    private Boolean hideDowns;

    @SerializedName("hide_from_robots")
    private Boolean hideFromRobots;

    @SerializedName("hide_ups")
    private Boolean hideUps;

    @SerializedName("highlight_controversial")
    private Boolean highlightControversial;

    @SerializedName("highlight_new_comments")
    private Boolean highlightNewComments;

    @SerializedName("ignore_suggested_sort")
    private Boolean ignoreSuggestedSort;

    @SerializedName("in_redesign_beta")
    private Boolean inRedesignBeta;

    @SerializedName("label_nsfw")
    private Boolean labelNsfw;

    @SerializedName("lang")
    private Locale lang;

    @SerializedName("legacy_search")
    private Boolean legacySearch;

    @SerializedName("live_orangereds")
    private Boolean liveOrangeReds;

    @SerializedName("mark_messages_read")
    private Boolean markMessagesRead;

    @SerializedName("media")
    private Media media;

    @SerializedName("media_preview")
    private Media mediaPreview;

    @SerializedName("min_comment_score")
    private Integer minCommentScore;

    @SerializedName("min_link_score")
    private Integer minLinkScore;

    @SerializedName("monitor_mentions")
    private Boolean monitorMentions;

    @SerializedName("newwindow")
    private Boolean newWindow;

    @SerializedName("nightmode")
    private Boolean nightMode;

    @SerializedName("no_profanity")
    private Boolean noProfanity;

    @SerializedName("num_comments")
    private Integer numComments;

    @SerializedName("numsites")
    private Integer numSites;

    @SerializedName("organic")
    private Boolean organic;

    @SerializedName("other_theme")
    private String otherTheme;

    @SerializedName("over_18")
    private Boolean over18;

    @SerializedName("private_feeds")
    private Boolean privateFeeds;

    @SerializedName("profile_opt_out")
    private Boolean profileOptOut;

    @SerializedName("public_votes")
    private Boolean publicVotes;

    @SerializedName("research")
    private Boolean research;

    @SerializedName("search_include_over_18")
    private Boolean searchIncludeOver18;

    @SerializedName("send_crosspost_messages")
    private Boolean sendCrossPostMessages;

    @SerializedName("send_welcome_messages")
    private Boolean sendWelcomeMessages;

    @SerializedName("show_flair")
    private Boolean showFlair;

    @SerializedName("show_gold_expiration")
    private Boolean showGoldExpiration;

    @SerializedName("show_link_flair")
    private Boolean showLinkFlair;

    @SerializedName("show_location_based_recommendations")
    private Boolean showLocationBasedRecommendations;

    @SerializedName("show_presence")
    private Boolean showPresence;

    @SerializedName("show_promote")
    private Boolean showPromote;

    @SerializedName("show_stylesheets")
    private Boolean showStylesheets;

    @SerializedName("show_trending")
    private Boolean showTrending;

    @SerializedName("show_twitter")
    private Boolean showTwitter;

    @SerializedName("store_visits")
    private Boolean storeVisits;

    @SerializedName("survey_last_seen_time")
    private Long surveyLastSeenTime;

    @SerializedName("theme_selector")
    private String themeSelector;

    @SerializedName("third_party_data_personalized_ads")
    private Boolean thirdPartyDataPersonalizedAds;

    @SerializedName("third_party_personalized_ads")
    private Boolean thirdPartyPersonalizedAds;

    @SerializedName("third_party_site_data_personalized_ads")
    private Boolean thirdPartySiteDataPersonalizedAds;

    @SerializedName("third_party_site_data_personalized_content")
    private Boolean thirdPartySiteDataPersonalizedContent;

    @SerializedName("threaded_messages")
    private Boolean threadedMessages;

    @SerializedName("threaded_modmail")
    private Boolean threadedModMail;

    @SerializedName("top_karma_subreddits")
    private Boolean topKarmaSubreddits;

    @SerializedName("use_global_defaults")
    private Boolean useGlobalDefaults;

    @SerializedName("video_autoplay")
    private Boolean videoAutoplay;

    //================================================================================
    // Methods
    //================================================================================

    /**
     * Updates this instance properties by getting the ones in the given instance.
     * <p></p>
     * This is meant to be used when sending a PATCH request to Reddit using {@link PrefsUpdater#patch()}.
     */
    public void update(Prefs prefs) {
        acceptPms = prefs.acceptPms;
        activityRelevantAds = prefs.activityRelevantAds;
        allowClickTracking = prefs.allowClickTracking;
        beta = prefs.beta;
        clickGadget = prefs.clickGadget;
        collapseReadMessages = prefs.collapseReadMessages;
        compress = prefs.compress;
        countryCode = prefs.countryCode;
        credditAutorenew = prefs.credditAutorenew;
        defaultCommentSort = prefs.defaultCommentSort;
        domainDetails = prefs.domainDetails;
        emailChatRequest = prefs.emailChatRequest;
        emailCommentReply = prefs.emailCommentReply;
        emailDigests = prefs.emailDigests;
        emailMessages = prefs.emailMessages;
        emailPostReply = prefs.emailPostReply;
        emailPrivateMessage = prefs.emailPrivateMessage;
        emailUnsubscribeAll = prefs.emailUnsubscribeAll;
        emailUpvoteComment = prefs.emailUpvoteComment;
        emailUpvotePost = prefs.emailUpvotePost;
        emailUserNewFollower = prefs.emailUserNewFollower;
        emailUsernameMention = prefs.emailUsernameMention;
        enableDefaultThemes = prefs.enableDefaultThemes;
        enableFollowers = prefs.enableFollowers;
        feedRecommendationsEnabled = prefs.feedRecommendationsEnabled;
        geoPopular = prefs.geoPopular;
        hideAds = prefs.hideAds;
        hideDowns = prefs.hideDowns;
        hideFromRobots = prefs.hideFromRobots;
        hideUps = prefs.hideUps;
        highlightControversial = prefs.highlightControversial;
        highlightNewComments = prefs.highlightNewComments;
        ignoreSuggestedSort = prefs.ignoreSuggestedSort;
        inRedesignBeta = prefs.inRedesignBeta;
        labelNsfw = prefs.labelNsfw;
        lang = prefs.lang;
        legacySearch = prefs.legacySearch;
        liveOrangeReds = prefs.liveOrangeReds;
        markMessagesRead = prefs.markMessagesRead;
        media = prefs.media;
        mediaPreview = prefs.mediaPreview;
        minCommentScore = prefs.minCommentScore;
        minLinkScore = prefs.minLinkScore;
        monitorMentions = prefs.monitorMentions;
        newWindow = prefs.newWindow;
        nightMode = prefs.nightMode;
        noProfanity = prefs.noProfanity;
        numComments = prefs.numComments;
        numSites = prefs.numSites;
        organic = prefs.organic;
        otherTheme = prefs.otherTheme;
        over18 = prefs.over18;
        privateFeeds = prefs.privateFeeds;
        profileOptOut = prefs.profileOptOut;
        publicVotes = prefs.publicVotes;
        research = prefs.research;
        searchIncludeOver18 = prefs.searchIncludeOver18;
        sendCrossPostMessages = prefs.sendCrossPostMessages;
        sendWelcomeMessages = prefs.sendWelcomeMessages;
        showFlair = prefs.showFlair;
        showGoldExpiration = prefs.showGoldExpiration;
        showLinkFlair = prefs.showLinkFlair;
        showLocationBasedRecommendations = prefs.showLocationBasedRecommendations;
        showPresence = prefs.showPresence;
        showPromote = prefs.showPromote;
        showStylesheets = prefs.showStylesheets;
        showTrending = prefs.showTrending;
        showTwitter = prefs.showTwitter;
        storeVisits = prefs.storeVisits;
        surveyLastSeenTime = prefs.surveyLastSeenTime;
        themeSelector = prefs.themeSelector;
        thirdPartyDataPersonalizedAds = prefs.thirdPartyDataPersonalizedAds;
        thirdPartyPersonalizedAds = prefs.thirdPartyPersonalizedAds;
        thirdPartySiteDataPersonalizedAds = prefs.thirdPartySiteDataPersonalizedAds;
        thirdPartySiteDataPersonalizedContent = prefs.thirdPartySiteDataPersonalizedContent;
        threadedMessages = prefs.threadedMessages;
        threadedModMail = prefs.threadedModMail;
        topKarmaSubreddits = prefs.topKarmaSubreddits;
        useGlobalDefaults = prefs.useGlobalDefaults;
        videoAutoplay = prefs.videoAutoplay;
    }

    //================================================================================
    // GETTERS
    //================================================================================

    /**
     * Specifies who can send you personal messages (one of {@link AcceptPMsEnum}).
     */
    public AcceptPMsEnum getAcceptPms() {
        return acceptPms;
    }

    /**
     * Allow Reddit to use your activity on Reddit to show you more relevant advertisements.
     */
    public Boolean isActivityRelevantAds() {
        return activityRelevantAds;
    }

    /**
     * Allow Reddit to log my outbound clicks for personalization.
     */
    public Boolean isAllowClickTracking() {
        return allowClickTracking;
    }

    /**
     * I would like to beta test features for Reddit.
     */
    public Boolean isBeta() {
        return beta;
    }

    /**
     * Show me links I’ve recently viewed.
     */
    public Boolean isClickGadget() {
        return clickGadget;
    }

    /**
     * Collapse messages after I’ve read them.
     */
    public Boolean isCollapseReadMessages() {
        return collapseReadMessages;
    }

    /**
     * Compress the link display.
     */
    public Boolean isCompress() {
        return compress;
    }

    public CountryCode getCountryCode() {
        return countryCode;
    }

    /**
     * Use a creddit to automatically renew my gold if it expires.
     */
    public Boolean isCredditAutorenew() {
        return credditAutorenew;
    }

    /**
     * Default comment sort (one of {@link CommentSort}).
     */
    public CommentSort getDefaultCommentSort() {
        return defaultCommentSort;
    }

    /**
     * Show additional details in the domain text when available, such as the source subreddit or the content author’s url/name.
     */
    public Boolean isDomainDetails() {
        return domainDetails;
    }

    /**
     * Send chat requests as emails.
     */
    public Boolean isEmailChatRequest() {
        return emailChatRequest;
    }

    /**
     * Send comment replies as emails.
     */
    public Boolean isEmailCommentReply() {
        return emailCommentReply;
    }

    /**
     * Send email digests.
     */
    public Boolean isEmailDigests() {
        return emailDigests;
    }

    /**
     * Send messages as emails.
     */
    public Boolean isEmailMessages() {
        return emailMessages;
    }

    /**
     * Send post replies as emails.
     */
    public Boolean isEmailPostReply() {
        return emailPostReply;
    }

    /**
     * Send private messages as emails.
     */
    public Boolean isEmailPrivateMessage() {
        return emailPrivateMessage;
    }

    /**
     * Unsubscribe from all emails.
     */
    public Boolean isEmailUnsubscribeAll() {
        return emailUnsubscribeAll;
    }

    /**
     * Send comment upvote updates as emails.
     */
    public Boolean isEmailUpvoteComment() {
        return emailUpvoteComment;
    }

    /**
     * Send post upvote updates as emails.
     */
    public Boolean isEmailUpvotePost() {
        return emailUpvotePost;
    }

    /**
     * Send new follower alerts as emails.
     */
    public Boolean isEmailUserNewFollower() {
        return emailUserNewFollower;
    }

    /**
     * Send username mentions as emails.
     */
    public Boolean isEmailUsernameMention() {
        return emailUsernameMention;
    }

    /**
     * Use reddit theme.
     */
    public Boolean isEnableDefaultThemes() {
        return enableDefaultThemes;
    }

    public Boolean isEnableFollowers() {
        return enableFollowers;
    }

    /**
     * Enable feed recommendations.
     */
    public Boolean isFeedRecommendationsEnabled() {
        return feedRecommendationsEnabled;
    }

    /**
     * Location (one of {@link GeoPopular}).
     */
    public GeoPopular getGeoPopular() {
        return geoPopular;
    }

    /**
     * Hide ads.
     */
    public Boolean isHideAds() {
        return hideAds;
    }

    /**
     * Don’t show me submissions after I’ve downvoted them, except my own.
     */
    public Boolean isHideDowns() {
        return hideDowns;
    }

    /**
     * Don’t allow search engines to index my user profile.
     */
    public Boolean isHideFromRobots() {
        return hideFromRobots;
    }

    /**
     * Don’t show me submissions after I’ve upvoted them, except my own.
     */
    public Boolean isHideUps() {
        return hideUps;
    }

    /**
     * Show a dagger on comments voted controversial.
     */
    public Boolean isHighlightControversial() {
        return highlightControversial;
    }

    /**
     * Highlight new comments.
     */
    public Boolean isHighlightNewComments() {
        return highlightNewComments;
    }

    /**
     * Ignore suggested sorts.
     */
    public Boolean isIgnoreSuggestedSort() {
        return ignoreSuggestedSort;
    }

    /**
     * In redesign beta.
     */
    public Boolean isInRedesignBeta() {
        return inRedesignBeta;
    }

    /**
     * Label posts that are not safe for work.
     */
    public Boolean isLabelNsfw() {
        return labelNsfw;
    }

    /**
     * Interface language (IETF language tag, underscore separated).
     */
    public Locale getLang() {
        return lang;
    }

    /**
     * Show legacy search page.
     */
    public Boolean isLegacySearch() {
        return legacySearch;
    }

    /**
     * Send message notifications in my browser.
     */
    public Boolean isLiveOrangeReds() {
        return liveOrangeReds;
    }

    /**
     * Mark messages as read when I open my inbox.
     */
    public Boolean isMarkMessagesRead() {
        return markMessagesRead;
    }

    /**
     * Thumbnail preference (one of {@link Media}).
     */
    public Media getMedia() {
        return media;
    }

    /**
     * Media preview preference (one of {@link Media}.
     */
    public Media getMediaPreview() {
        return mediaPreview;
    }

    /**
     * Don’t show me comments with a score less than this number (between -100, 100).
     */
    public Integer getMinCommentScore() {
        return minCommentScore;
    }

    /**
     * Don’t show me submissions with a score less than this number (between -100, 100).
     */
    public Integer getMinLinkScore() {
        return minLinkScore;
    }

    /**
     * Notify me when people say my username.
     */
    public Boolean isMonitorMentions() {
        return monitorMentions;
    }

    /**
     * Open links in a new window.
     */
    public Boolean isNewWindow() {
        return newWindow;
    }

    /**
     * Enable night mode.
     */
    public Boolean isNightMode() {
        return nightMode;
    }

    /**
     * Don’t show thumbnails or media previews for anything labeled NSFW.
     */
    public Boolean isNoProfanity() {
        return noProfanity;
    }

    /**
     * Display this many comments by default (between 1, 500).
     */
    public Integer getNumComments() {
        return numComments;
    }

    /**
     * Number of links to display at once (between 1, 100).
     */
    public Integer getNumSites() {
        return numSites;
    }

    /**
     * Show the spotlight box on the home feed.
     */
    public Boolean isOrganic() {
        return organic;
    }

    /**
     * Subreddit theme to use (subreddit's name).
     */
    public String otherTheme() {
        return otherTheme;
    }

    /**
     * I am over eighteen years old and willing to view adult content.
     */
    public Boolean isOver18() {
        return over18;
    }

    /**
     * Enable private RSS feeds.
     */
    public Boolean isPrivateFeeds() {
        return privateFeeds;
    }

    /**
     * iew user profiles on desktop using legacy mode.
     */
    public Boolean isProfileOptOut() {
        return profileOptOut;
    }

    /**
     * Make my votes public.
     */
    public Boolean isPublicVotes() {
        return publicVotes;
    }

    /**
     * Allow my data to be used for research purposes.
     */
    public Boolean isResearch() {
        return research;
    }

    /**
     * Include not safe for work (NSFW) search results in searches.
     */
    public Boolean isSearchIncludeOver18() {
        return searchIncludeOver18;
    }

    /**
     * Send crosspost messages.
     */
    public Boolean isSendCrossPostMessages() {
        return sendCrossPostMessages;
    }

    /**
     * Send welcome messages.
     */
    public Boolean isSendWelcomeMessages() {
        return sendWelcomeMessages;
    }

    /**
     * Show user flair.
     */
    public Boolean isShowFlair() {
        return showFlair;
    }

    /**
     * Show how much gold you have remaining on your userpage.
     */
    public Boolean isShowGoldExpiration() {
        return showGoldExpiration;
    }

    /**
     * Show link flair.
     */
    public Boolean isShowLinkFlair() {
        return showLinkFlair;
    }

    /**
     * Show location based recommendations.
     */
    public Boolean isShowLocationBasedRecommendations() {
        return showLocationBasedRecommendations;
    }

    /**
     * Show presence.
     */
    public Boolean isShowPresence() {
        return showPresence;
    }

    /**
     * Show promote.
     */
    public Boolean isShowPromote() {
        return showPromote;
    }

    /**
     * Allow subreddits to show me custom themes.
     */
    public Boolean isShowStylesheets() {
        return showStylesheets;
    }

    /**
     * Show trending subreddits on the home feed.
     */
    public Boolean isShowTrending() {
        return showTrending;
    }

    /**
     * Show a link to your Twitter account on your profile.
     */
    public Boolean isShowTwitter() {
        return showTwitter;
    }

    /**
     * .
     */
    public Boolean isStoreVisits() {
        return storeVisits;
    }

    /**
     * Store visits.
     */
    public Long getSurveyLastSeenTime() {
        return surveyLastSeenTime;
    }

    /**
     * Theme selector (subreddit name).
     */
    public String getThemeSelector() {
        return themeSelector;
    }

    /**
     * Allow Reddit to use data provided by third-parties to show you more relevant advertisements on Reddit.
     */
    public Boolean isThirdPartyDataPersonalizedAds() {
        return thirdPartyDataPersonalizedAds;
    }

    /**
     * Allow personalization of advertisement.
     */
    public Boolean isThirdPartyPersonalizedAds() {
        return thirdPartyPersonalizedAds;
    }

    /**
     * Allow personalization of advertisements using third-party website data.
     */
    public Boolean isThirdPartySiteDataPersonalizedAds() {
        return thirdPartySiteDataPersonalizedAds;
    }

    /**
     * Allow personalization of content using third-party website data.
     */
    public Boolean isThirdPartySiteDataPersonalizedContent() {
        return thirdPartySiteDataPersonalizedContent;
    }

    /**
     * Show message conversations in the inbox.
     */
    public Boolean isThreadedMessages() {
        return threadedMessages;
    }

    /**
     * Enable threaded modmail display.
     */
    public Boolean isThreadedModMail() {
        return threadedModMail;
    }

    /**
     * Top karma subreddits.
     */
    public Boolean isTopKarmaSubreddits() {
        return topKarmaSubreddits;
    }

    /**
     * Use global default.
     */
    public Boolean isUseGlobalDefaults() {
        return useGlobalDefaults;
    }

    /**
     * Autoplay Reddit videos on the desktop comments page.
     */
    public Boolean isVideoAutoplay() {
        return videoAutoplay;
    }

    //================================================================================
    // SETTERS
    //================================================================================
    void setAcceptPms(AcceptPMsEnum acceptPms) {
        this.acceptPms = acceptPms;
    }

    void setActivityRelevantAds(Boolean activityRelevantAds) {
        this.activityRelevantAds = activityRelevantAds;
    }

    void setAllowClickTracking(Boolean allowClickTracking) {
        this.allowClickTracking = allowClickTracking;
    }

    void setBeta(Boolean beta) {
        this.beta = beta;
    }

    void setClickGadget(Boolean clickGadget) {
        this.clickGadget = clickGadget;
    }

    void setCollapseReadMessages(Boolean collapseReadMessages) {
        this.collapseReadMessages = collapseReadMessages;
    }

    void setCompress(Boolean compress) {
        this.compress = compress;
    }

    void setCountryCode(CountryCode countryCode) {
        this.countryCode = countryCode;
    }

    void setCredditAutorenew(Boolean credditAutorenew) {
        this.credditAutorenew = credditAutorenew;
    }

    void setDefaultCommentSort(CommentSort defaultCommentSort) {
        this.defaultCommentSort = defaultCommentSort;
    }

    void setDomainDetails(Boolean domainDetails) {
        this.domainDetails = domainDetails;
    }

    void setEmailChatRequest(Boolean emailChatRequest) {
        this.emailChatRequest = emailChatRequest;
    }

    void setEmailCommentReply(Boolean emailCommentReply) {
        this.emailCommentReply = emailCommentReply;
    }

    void setEmailDigests(Boolean emailDigests) {
        this.emailDigests = emailDigests;
    }

    void setEmailMessages(Boolean emailMessages) {
        this.emailMessages = emailMessages;
    }

    void setEmailPostReply(Boolean emailPostReply) {
        this.emailPostReply = emailPostReply;
    }

    void setEmailPrivateMessage(Boolean emailPrivateMessage) {
        this.emailPrivateMessage = emailPrivateMessage;
    }

    void setEmailUnsubscribeAll(Boolean emailUnsubscribeAll) {
        this.emailUnsubscribeAll = emailUnsubscribeAll;
    }

    void setEmailUpvoteComment(Boolean emailUpvoteComment) {
        this.emailUpvoteComment = emailUpvoteComment;
    }

    void setEmailUpvotePost(Boolean emailUpvotePost) {
        this.emailUpvotePost = emailUpvotePost;
    }

    void setEmailUserNewFollower(Boolean emailUserNewFollower) {
        this.emailUserNewFollower = emailUserNewFollower;
    }

    void setEmailUsernameMention(Boolean emailUsernameMention) {
        this.emailUsernameMention = emailUsernameMention;
    }

    void setEnableDefaultThemes(Boolean enableDefaultThemes) {
        this.enableDefaultThemes = enableDefaultThemes;
    }

    void setEnableFollowers(Boolean enableFollowers) {
        this.enableFollowers = enableFollowers;
    }

    void setFeedRecommendationsEnabled(Boolean feedRecommendationsEnabled) {
        this.feedRecommendationsEnabled = feedRecommendationsEnabled;
    }

    void setGeoPopular(GeoPopular geoPopular) {
        this.geoPopular = geoPopular;
    }

    void setHideAds(Boolean hideAds) {
        this.hideAds = hideAds;
    }

    void setHideDowns(Boolean hideDowns) {
        this.hideDowns = hideDowns;
    }

    void setHideFromRobots(Boolean hideFromRobots) {
        this.hideFromRobots = hideFromRobots;
    }

    void setHideUps(Boolean hideUps) {
        this.hideUps = hideUps;
    }

    void setHighlightControversial(Boolean highlightControversial) {
        this.highlightControversial = highlightControversial;
    }

    void setHighlightNewComments(Boolean highlightNewComments) {
        this.highlightNewComments = highlightNewComments;
    }

    void setIgnoreSuggestedSort(Boolean ignoreSuggestedSort) {
        this.ignoreSuggestedSort = ignoreSuggestedSort;
    }

    void setInRedesignBeta(Boolean inRedesignBeta) {
        this.inRedesignBeta = inRedesignBeta;
    }

    void setLabelNsfw(Boolean labelNsfw) {
        this.labelNsfw = labelNsfw;
    }

    void setLang(Locale lang) {
        this.lang = lang;
    }

    void setLegacySearch(Boolean legacySearch) {
        this.legacySearch = legacySearch;
    }

    void setLiveOrangeReds(Boolean liveOrangeReds) {
        this.liveOrangeReds = liveOrangeReds;
    }

    void setMarkMessagesRead(Boolean markMessagesRead) {
        this.markMessagesRead = markMessagesRead;
    }

    void setMedia(Media media) {
        this.media = media;
    }

    void setMediaPreview(Media mediaPreview) {
        this.mediaPreview = mediaPreview;
    }

    void setMinCommentScore(Integer minCommentScore) {
        this.minCommentScore = minCommentScore;
    }

    void setMinLinkScore(Integer minLinkScore) {
        this.minLinkScore = minLinkScore;
    }

    void setMonitorMentions(Boolean monitorMentions) {
        this.monitorMentions = monitorMentions;
    }

    void setNewWindow(Boolean newWindow) {
        this.newWindow = newWindow;
    }

    void setNightMode(Boolean nightMode) {
        this.nightMode = nightMode;
    }

    void setNoProfanity(Boolean noProfanity) {
        this.noProfanity = noProfanity;
    }

    void setNumComments(Integer numComments) {
        this.numComments = numComments;
    }

    void setNumSites(Integer numSites) {
        this.numSites = numSites;
    }

    void setOrganic(Boolean organic) {
        this.organic = organic;
    }

    void setOtherTheme(String otherTheme) {
        this.otherTheme = otherTheme;
    }

    void setOver18(Boolean over18) {
        this.over18 = over18;
    }

    void setPrivateFeeds(Boolean privateFeeds) {
        this.privateFeeds = privateFeeds;
    }

    void setProfileOptOut(Boolean profileOptOut) {
        this.profileOptOut = profileOptOut;
    }

    void setPublicVotes(Boolean publicVotes) {
        this.publicVotes = publicVotes;
    }

    void setResearch(Boolean research) {
        this.research = research;
    }

    void setSearchIncludeOver18(Boolean searchIncludeOver18) {
        this.searchIncludeOver18 = searchIncludeOver18;
    }

    void setSendCrossPostMessages(Boolean sendCrossPostMessages) {
        this.sendCrossPostMessages = sendCrossPostMessages;
    }

    void setSendWelcomeMessages(Boolean sendWelcomeMessages) {
        this.sendWelcomeMessages = sendWelcomeMessages;
    }

    void setShowFlair(Boolean showFlair) {
        this.showFlair = showFlair;
    }

    void setShowGoldExpiration(Boolean showGoldExpiration) {
        this.showGoldExpiration = showGoldExpiration;
    }

    void setShowLinkFlair(Boolean showLinkFlair) {
        this.showLinkFlair = showLinkFlair;
    }

    void setShowLocationBasedRecommendations(Boolean showLocationBasedRecommendations) {
        this.showLocationBasedRecommendations = showLocationBasedRecommendations;
    }

    void setShowPresence(Boolean showPresence) {
        this.showPresence = showPresence;
    }

    void setShowPromote(Boolean showPromote) {
        this.showPromote = showPromote;
    }

    void setShowStylesheets(Boolean showStylesheets) {
        this.showStylesheets = showStylesheets;
    }

    void setShowTrending(Boolean showTrending) {
        this.showTrending = showTrending;
    }

    void setShowTwitter(Boolean showTwitter) {
        this.showTwitter = showTwitter;
    }

    void setStoreVisits(Boolean storeVisits) {
        this.storeVisits = storeVisits;
    }

    void setSurveyLastSeenTime(Long surveyLastSeenTime) {
        this.surveyLastSeenTime = surveyLastSeenTime;
    }

    void setThemeSelector(String themeSelector) {
        this.themeSelector = themeSelector;
    }

    void setThirdPartyDataPersonalizedAds(Boolean thirdPartyDataPersonalizedAds) {
        this.thirdPartyDataPersonalizedAds = thirdPartyDataPersonalizedAds;
    }

    void setThirdPartyPersonalizedAds(Boolean thirdPartyPersonalizedAds) {
        this.thirdPartyPersonalizedAds = thirdPartyPersonalizedAds;
    }

    void setThirdPartySiteDataPersonalizedAds(Boolean thirdPartySiteDataPersonalizedAds) {
        this.thirdPartySiteDataPersonalizedAds = thirdPartySiteDataPersonalizedAds;
    }

    void setThirdPartySiteDataPersonalizedContent(Boolean thirdPartySiteDataPersonalizedContent) {
        this.thirdPartySiteDataPersonalizedContent = thirdPartySiteDataPersonalizedContent;
    }

    void setThreadedMessages(Boolean threadedMessages) {
        this.threadedMessages = threadedMessages;
    }

    void setThreadedModMail(Boolean threadedModMail) {
        this.threadedModMail = threadedModMail;
    }

    void setTopKarmaSubreddits(Boolean topKarmaSubreddits) {
        this.topKarmaSubreddits = topKarmaSubreddits;
    }

    void setUseGlobalDefaults(Boolean useGlobalDefaults) {
        this.useGlobalDefaults = useGlobalDefaults;
    }

    void setVideoAutoplay(Boolean videoAutoplay) {
        this.videoAutoplay = videoAutoplay;
    }
}
