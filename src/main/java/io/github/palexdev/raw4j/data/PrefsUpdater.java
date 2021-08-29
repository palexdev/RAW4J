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

import com.google.gson.JsonObject;
import io.github.palexdev.raw4j.enums.PrefsEnumerators.*;
import io.github.palexdev.raw4j.enums.endpoints.AccountEndpoints;
import io.github.palexdev.raw4j.oauth.base.OAuthFlow;
import okhttp3.MediaType;
import okhttp3.RequestBody;

import java.util.Locale;

import static io.github.palexdev.raw4j.json.GsonInstance.fromJson;
import static io.github.palexdev.raw4j.json.GsonInstance.toJsonTree;

/**
 * This class is a helper to update the logged user preferences. Helps to create a PATCH request.
 * <p>
 * After sending the request with {@link #patch()} you will receive a new instance of {@link Prefs},
 * if you already requested an instance of {@link Prefs} before note that they are not valid anymore!
 * <p>
 * You have two options, replace the old instance with the new one or update the old one using {@link Prefs#update(Prefs)}
 * by passing the new received instance.
 */
public class PrefsUpdater {
    //================================================================================
    // Properties
    //================================================================================
    private final OAuthFlow authManager;
    private final Prefs prefs = new Prefs();

    //================================================================================
    // Constructors
    //================================================================================
    public PrefsUpdater(OAuthFlow authManager) {
        this.authManager = authManager;
    }

    //================================================================================
    // Methods
    //================================================================================

    /**
     * Calls {@link #generateJson()} and then sends the request.
     */
    public Prefs patch() {
        String json = generateJson();
        RequestBody requestBody = RequestBody.create(json, MediaType.parse("application/json; charset=utf-8"));
        return fromJson(authManager.patch(AccountEndpoints.ME_PREFS.getFullEndpoint(), requestBody), Prefs.class);
    }

    /**
     * Generate the JSON to sent to the server.
     *
     * @return the generated JSON String
     * @throws IllegalStateException if no preferences were set, in other words if the generated JSON is empty
     */
    private String generateJson() {
        JsonObject json = (JsonObject) toJsonTree(prefs);
        if (json.size() == 0) {
            throw new IllegalStateException("Cannot update preferences since no prefs have been changed!");
        }
        return json.toString();
    }

    //================================================================================
    // Request Configuration
    //================================================================================

    /**
     * {@link Prefs#getAcceptPms()}
     */
    public PrefsUpdater setAcceptPms(AcceptPMsEnum acceptPms) {
        prefs.setAcceptPms(acceptPms);
        return this;
    }

    /**
     * {@link Prefs#isActivityRelevantAds()}
     */
    public PrefsUpdater setActivityRelevantAds(boolean activityRelevantAds) {
        prefs.setActivityRelevantAds(activityRelevantAds);
        return this;
    }

    /**
     * {@link Prefs#isAllowClickTracking()}
     */
    public PrefsUpdater setAllowClickTracking(boolean allowClickTracking) {
        prefs.setAllowClickTracking(allowClickTracking);
        return this;
    }

    /**
     * {@link Prefs#isBeta()}
     */
    public PrefsUpdater setBeta(boolean beta) {
        prefs.setBeta(beta);
        return this;
    }

    /**
     * {@link Prefs#isClickGadget()}
     */
    public PrefsUpdater setClickGadget(boolean clickGadget) {
        prefs.setClickGadget(clickGadget);
        return this;
    }

    /**
     * {@link Prefs#isCollapseReadMessages()}
     */
    public PrefsUpdater setCollapseReadMessages(boolean collapseReadMessages) {
        prefs.setCollapseReadMessages(collapseReadMessages);
        return this;
    }

    /**
     * {@link Prefs#isCompress()}
     */
    public PrefsUpdater setCompress(boolean compress) {
        prefs.setCompress(compress);
        return this;
    }

    /**
     * {@link Prefs#getCountryCode()}
     */
    public PrefsUpdater setCountryCode(CountryCode countryCode) {
        prefs.setCountryCode(countryCode);
        return this;
    }

    /**
     * {@link Prefs#isCredditAutorenew()}
     */
    public PrefsUpdater setCredditAutorenew(boolean credditAutorenew) {
        prefs.setCredditAutorenew(credditAutorenew);
        return this;
    }

    /**
     * {@link Prefs#getDefaultCommentSort()}
     */
    public PrefsUpdater setDefaultCommentSort(CommentSort defaultCommentSort) {
        prefs.setDefaultCommentSort(defaultCommentSort);
        return this;
    }

    /**
     * {@link Prefs#isDomainDetails()}
     */
    public PrefsUpdater setDomainDetails(boolean domainDetails) {
        prefs.setDomainDetails(domainDetails);
        return this;
    }

    /**
     * {@link Prefs#isEmailChatRequest()}
     */
    public PrefsUpdater setEmailChatRequest(boolean emailChatRequest) {
        prefs.setEmailChatRequest(emailChatRequest);
        return this;
    }

    /**
     * {@link Prefs#isEmailCommentReply()}
     */
    public PrefsUpdater setEmailCommentReply(boolean emailCommentReply) {
        prefs.setEmailCommentReply(emailCommentReply);
        return this;
    }

    /**
     * {@link Prefs#isEmailDigests()}
     */
    public PrefsUpdater setEmailDigests(boolean emailDigests) {
        prefs.setEmailDigests(emailDigests);
        return this;
    }

    /**
     * {@link Prefs#isEmailMessages()}
     */
    public PrefsUpdater setEmailMessages(boolean emailMessages) {
        prefs.setEmailMessages(emailMessages);
        return this;
    }

    /**
     * {@link Prefs#isEmailPostReply()}
     */
    public PrefsUpdater setEmailPostReply(boolean emailPostReply) {
        prefs.setEmailPostReply(emailPostReply);
        return this;
    }

    /**
     * {@link Prefs#isEmailPrivateMessage()}
     */
    public PrefsUpdater setEmailPrivateMessage(boolean emailPrivateMessage) {
        prefs.setEmailPrivateMessage(emailPrivateMessage);
        return this;
    }

    /**
     * {@link Prefs#isEmailUnsubscribeAll()}
     */
    public PrefsUpdater setEmailUnsubscribeAll(boolean emailUnsubscribeAll) {
        prefs.setEmailUnsubscribeAll(emailUnsubscribeAll);
        return this;
    }

    /**
     * {@link Prefs#isEmailUpvoteComment()}
     */
    public PrefsUpdater setEmailUpvoteComment(boolean emailUpvoteComment) {
        prefs.setEmailUpvoteComment(emailUpvoteComment);
        return this;
    }

    /**
     * {@link Prefs#isEmailUpvoteComment()}
     */
    public PrefsUpdater setEmailUpvotePost(boolean emailUpvotePost) {
        prefs.setEmailUpvotePost(emailUpvotePost);
        return this;
    }

    /**
     * {@link Prefs#isEmailUserNewFollower()}
     */
    public PrefsUpdater setEmailUserNewFollower(boolean emailUserNewFollower) {
        prefs.setEmailUserNewFollower(emailUserNewFollower);
        return this;
    }

    /**
     * {@link Prefs#isEmailUsernameMention()}
     */
    public PrefsUpdater setEmailUsernameMention(boolean emailUsernameMention) {
        prefs.setEmailUsernameMention(emailUsernameMention);
        return this;
    }

    /**
     * {@link Prefs#isEnableDefaultThemes()}
     */
    public PrefsUpdater setEnableDefaultThemes(boolean enableDefaultThemes) {
        prefs.setEnableDefaultThemes(enableDefaultThemes);
        return this;
    }

    /**
     * {@link Prefs#isEnableFollowers()}
     */
    public PrefsUpdater setEnableFollowers(boolean enableFollowers) {
        prefs.setEnableFollowers(enableFollowers);
        return this;
    }

    /**
     * {@link Prefs#isFeedRecommendationsEnabled()}
     */
    public PrefsUpdater setFeedRecommendationsEnabled(boolean feedRecommendationsEnabled) {
        prefs.setFeedRecommendationsEnabled(feedRecommendationsEnabled);
        return this;
    }

    /**
     * {@link Prefs#getGeoPopular()}
     */
    public PrefsUpdater setGeoPopular(GeoPopular geoPopular) {
        prefs.setGeoPopular(geoPopular);
        return this;
    }

    /**
     * {@link Prefs#isHideAds()}
     */
    public PrefsUpdater setHideAds(boolean hideAds) {
        prefs.setHideAds(hideAds);
        return this;
    }

    /**
     * {@link Prefs#isHideDowns()}
     */
    public PrefsUpdater setHideDowns(boolean hideDowns) {
        prefs.setHideDowns(hideDowns);
        return this;
    }

    /**
     * {@link Prefs#isHideFromRobots()}
     */
    public PrefsUpdater setHideFromRobots(boolean hideFromRobots) {
        prefs.setHideFromRobots(hideFromRobots);
        return this;
    }

    /**
     * {@link Prefs#isHideUps()}
     */
    public PrefsUpdater setHideUps(boolean hideUps) {
        prefs.setHideUps(hideUps);
        return this;
    }

    /**
     * {@link Prefs#isHighlightControversial()}
     */
    public PrefsUpdater setHighlightControversial(boolean highlightControversial) {
        prefs.setHighlightControversial(highlightControversial);
        return this;
    }

    /**
     * {@link Prefs#isHighlightNewComments()}
     */
    public PrefsUpdater setHighlightNewComments(boolean highlightNewComments) {
        prefs.setHighlightNewComments(highlightNewComments);
        return this;
    }

    /**
     * {@link Prefs#isIgnoreSuggestedSort()}
     */
    public PrefsUpdater setIgnoreSuggestedSort(boolean ignoreSuggestedSort) {
        prefs.setIgnoreSuggestedSort(ignoreSuggestedSort);
        return this;
    }

    /**
     * {@link Prefs#isInRedesignBeta()}
     */
    public PrefsUpdater setInRedesignBeta(boolean inRedesignBeta) {
        prefs.setInRedesignBeta(inRedesignBeta);
        return this;
    }

    /**
     * {@link Prefs#isLabelNsfw()}
     */
    public PrefsUpdater setLabelNsfw(boolean labelNsfw) {
        prefs.setLabelNsfw(labelNsfw);
        return this;
    }

    /**
     * {@link Prefs#getLang()}
     */
    public PrefsUpdater setLang(Locale lang) {
        prefs.setLang(lang);
        return this;
    }

    /**
     * {@link Prefs#isLegacySearch()}
     */
    public PrefsUpdater setLegacySearch(boolean legacySearch) {
        prefs.setLegacySearch(legacySearch);
        return this;
    }

    /**
     * {@link Prefs#isLiveOrangeReds()}
     */
    public PrefsUpdater setLiveOrangeReds(boolean liveOrangeReds) {
        prefs.setLiveOrangeReds(liveOrangeReds);
        return this;
    }

    /**
     * {@link Prefs#isMarkMessagesRead()}
     */
    public PrefsUpdater setMarkMessagesRead(boolean markMessagesRead) {
        prefs.setMarkMessagesRead(markMessagesRead);
        return this;
    }

    /**
     * {@link Prefs#getMedia()}
     */
    public PrefsUpdater setMedia(Media media) {
        prefs.setMedia(media);
        return this;
    }

    /**
     * {@link Prefs#getMediaPreview()}
     */
    public PrefsUpdater setMediaPreview(Media mediaPreview) {
        prefs.setMediaPreview(mediaPreview);
        return this;
    }

    /**
     * {@link Prefs#getMinCommentScore()}
     */
    public PrefsUpdater setMinCommentScore(int minCommentScore) {
        prefs.setMinCommentScore(minCommentScore);
        return this;
    }

    /**
     * {@link Prefs#getMinLinkScore()}
     */
    public PrefsUpdater setMinLinkScore(int minLinkScore) {
        prefs.setMinLinkScore(minLinkScore);
        return this;
    }

    /**
     * {@link Prefs#isMonitorMentions()}
     */
    public PrefsUpdater setMonitorMentions(boolean monitorMentions) {
        prefs.setMonitorMentions(monitorMentions);
        return this;
    }

    /**
     * {@link Prefs#isNewWindow()}
     */
    public PrefsUpdater setNewWindow(boolean newWindow) {
        prefs.setNewWindow(newWindow);
        return this;
    }

    /**
     * {@link Prefs#isNightMode()}
     */
    public PrefsUpdater setNightMode(boolean nightMode) {
        prefs.setNightMode(nightMode);
        return this;
    }

    /**
     * {@link Prefs#isNoProfanity()}
     */
    public PrefsUpdater setNoProfanity(boolean noProfanity) {
        prefs.setNoProfanity(noProfanity);
        return this;
    }

    /**
     * {@link Prefs#getNumComments()}
     */
    public PrefsUpdater setNumComments(int numComments) {
        prefs.setNumComments(numComments);
        return this;
    }

    /**
     * {@link Prefs#getNumSites()}
     */
    public PrefsUpdater setNumSites(int numSites) {
        prefs.setNumSites(numSites);
        return this;
    }

    /**
     * {@link Prefs#isOrganic()}
     */
    public PrefsUpdater setOrganic(boolean organic) {
        prefs.setOrganic(organic);
        return this;
    }

    /**
     * {@link Prefs#otherTheme()}
     */
    public PrefsUpdater setOtherTheme(String otherTheme) {
        prefs.setOtherTheme(otherTheme);
        return this;
    }

    /**
     * {@link Prefs#isOver18()}
     */
    public PrefsUpdater setOver18(boolean over18) {
        prefs.setOver18(over18);
        return this;
    }

    /**
     * {@link Prefs#isPrivateFeeds()}
     */
    public PrefsUpdater setPrivateFeeds(boolean privateFeeds) {
        prefs.setPrivateFeeds(privateFeeds);
        return this;
    }

    /**
     * {@link Prefs#isProfileOptOut()}
     */
    public PrefsUpdater setProfileOptOut(boolean profileOptOut) {
        prefs.setProfileOptOut(profileOptOut);
        return this;
    }

    /**
     * {@link Prefs#isPublicVotes()}
     */
    public PrefsUpdater setPublicVotes(boolean publicVotes) {
        prefs.setPublicVotes(publicVotes);
        return this;
    }

    /**
     * {@link Prefs#isResearch()}
     */
    public PrefsUpdater setResearch(boolean research) {
        prefs.setResearch(research);
        return this;
    }

    /**
     * {@link Prefs#isSearchIncludeOver18()}
     */
    public PrefsUpdater setSearchIncludeOver18(boolean searchIncludeOver18) {
        prefs.setSearchIncludeOver18(searchIncludeOver18);
        return this;
    }

    /**
     * {@link Prefs#isSendCrossPostMessages()}
     */
    public PrefsUpdater setSendCrossPostMessages(boolean sendCrossPostMessages) {
        prefs.setSendCrossPostMessages(sendCrossPostMessages);
        return this;
    }

    /**
     * {@link Prefs#isSendWelcomeMessages()}
     */
    public PrefsUpdater setSendWelcomeMessages(boolean sendWelcomeMessages) {
        prefs.setSendWelcomeMessages(sendWelcomeMessages);
        return this;
    }

    /**
     * {@link Prefs#isShowFlair()}
     */
    public PrefsUpdater setShowFlair(boolean showFlair) {
        prefs.setShowFlair(showFlair);
        return this;
    }

    /**
     * {@link Prefs#isShowGoldExpiration()}
     */
    public PrefsUpdater setShowGoldExpiration(boolean showGoldExpiration) {
        prefs.setShowGoldExpiration(showGoldExpiration);
        return this;
    }

    /**
     * {@link Prefs#isShowLinkFlair()}
     */
    public PrefsUpdater setShowLinkFlair(boolean showLinkFlair) {
        prefs.setShowLinkFlair(showLinkFlair);
        return this;
    }

    /**
     * {@link Prefs#isShowLocationBasedRecommendations()}
     */
    public PrefsUpdater setShowLocationBasedRecommendations(boolean showLocationBasedRecommendations) {
        prefs.setShowLocationBasedRecommendations(showLocationBasedRecommendations);
        return this;
    }

    /**
     * {@link Prefs#isShowPresence()}
     */
    public PrefsUpdater setShowPresence(boolean showPresence) {
        prefs.setShowPresence(showPresence);
        return this;
    }

    /**
     * {@link Prefs#isShowPromote()}
     */
    public PrefsUpdater setShowPromote(boolean showPromote) {
        prefs.setShowPromote(showPromote);
        return this;
    }

    /**
     * {@link Prefs#isShowStylesheets()}
     */
    public PrefsUpdater setShowStylesheets(boolean showStylesheets) {
        prefs.setShowStylesheets(showStylesheets);
        return this;
    }

    /**
     * {@link Prefs#isShowTrending()}
     */
    public PrefsUpdater setShowTrending(boolean showTrending) {
        prefs.setShowTrending(showTrending);
        return this;
    }

    /**
     * {@link Prefs#isShowTwitter()}
     */
    public PrefsUpdater setShowTwitter(boolean showTwitter) {
        prefs.setShowTwitter(showTwitter);
        return this;
    }

    /**
     * {@link Prefs#isStoreVisits()}
     */
    public PrefsUpdater setStoreVisits(boolean storeVisits) {
        prefs.setStoreVisits(storeVisits);
        return this;
    }

    /**
     * {@link Prefs#getSurveyLastSeenTime()}
     */
    public PrefsUpdater setSurveyLastSeenTime(Long surveyLastSeenTime) {
        prefs.setSurveyLastSeenTime(surveyLastSeenTime);
        return this;
    }

    /**
     * {@link Prefs#getThemeSelector()}
     */
    public PrefsUpdater setThemeSelector(String themeSelector) {
        prefs.setThemeSelector(themeSelector);
        return this;
    }

    /**
     * {@link Prefs#isThirdPartyDataPersonalizedAds()}
     */
    public PrefsUpdater setThirdPartyDataPersonalizedAds(boolean thirdPartyDataPersonalizedAds) {
        prefs.setThirdPartyDataPersonalizedAds(thirdPartyDataPersonalizedAds);
        return this;
    }

    /**
     * {@link Prefs#isThirdPartyPersonalizedAds()}
     */
    public PrefsUpdater setThirdPartyPersonalizedAds(boolean thirdPartyPersonalizedAds) {
        prefs.setThirdPartyPersonalizedAds(thirdPartyPersonalizedAds);
        return this;
    }

    /**
     * {@link Prefs#isThirdPartySiteDataPersonalizedAds()}
     */
    public PrefsUpdater setThirdPartySiteDataPersonalizedAds(boolean thirdPartySiteDataPersonalizedAds) {
        prefs.setThirdPartySiteDataPersonalizedAds(thirdPartySiteDataPersonalizedAds);
        return this;
    }

    /**
     * {@link Prefs#isThirdPartySiteDataPersonalizedContent()}
     */
    public PrefsUpdater setThirdPartySiteDataPersonalizedContent(boolean thirdPartySiteDataPersonalizedContent) {
        prefs.setThirdPartySiteDataPersonalizedContent(thirdPartySiteDataPersonalizedContent);
        return this;
    }

    /**
     * {@link Prefs#isThreadedMessages()}
     */
    public PrefsUpdater setThreadedMessages(boolean threadedMessages) {
        prefs.setThreadedMessages(threadedMessages);
        return this;
    }

    /**
     * {@link Prefs#isThreadedModMail()}
     */
    public PrefsUpdater setThreadedModMail(boolean threadedModMail) {
        prefs.setThreadedModMail(threadedModMail);
        return this;
    }

    /**
     * {@link Prefs#isTopKarmaSubreddits()}
     */
    public PrefsUpdater setTopKarmaSubreddits(boolean topKarmaSubreddits) {
        prefs.setTopKarmaSubreddits(topKarmaSubreddits);
        return this;
    }

    /**
     * {@link Prefs#isUseGlobalDefaults()}
     */
    public PrefsUpdater setUseGlobalDefaults(boolean useGlobalDefaults) {
        prefs.setUseGlobalDefaults(useGlobalDefaults);
        return this;
    }

    /**
     * {@link Prefs#isVideoAutoplay()}
     */
    public PrefsUpdater setVideoAutoplay(boolean videoAutoplay) {
        prefs.setVideoAutoplay(videoAutoplay);
        return this;
    }
}
