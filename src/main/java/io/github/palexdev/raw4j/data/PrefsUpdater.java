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
import io.github.palexdev.raw4j.enums.ApiEndpoints;
import io.github.palexdev.raw4j.enums.ApiEnumerators;
import io.github.palexdev.raw4j.enums.ApiEnumerators.AcceptPMsEnum;
import io.github.palexdev.raw4j.json.GsonInstance;
import io.github.palexdev.raw4j.oauth.base.OAuthFlow;
import okhttp3.MediaType;
import okhttp3.RequestBody;

import java.util.Locale;

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
    public Prefs patch() {
        String json = generateJson();
        RequestBody requestBody = RequestBody.create(json, MediaType.parse("application/json; charset=utf-8"));
        return GsonInstance.gson().fromJson(authManager.patch(ApiEndpoints.ME_PREFS.toString(), requestBody), Prefs.class);
    }

    private String generateJson() {
        JsonObject json = (JsonObject) GsonInstance.gson().toJsonTree(prefs);
        if (json.size() == 0) {
            throw new IllegalStateException("Cannot update preferences since no prefs have been changed!");
        }
        return json.toString();
    }

    //================================================================================
    // Request Configuration
    //================================================================================
    public PrefsUpdater setAcceptPms(AcceptPMsEnum acceptPms) {
        prefs.setAcceptPms(acceptPms);
        return this;
    }

    public PrefsUpdater setActivityRelevantAds(boolean activityRelevantAds) {
        prefs.setActivityRelevantAds(activityRelevantAds);
        return this;
    }

    public PrefsUpdater setAllowClickTracking(boolean allowClickTracking) {
        prefs.setAllowClickTracking(allowClickTracking);
        return this;
    }

    public PrefsUpdater setBeta(boolean beta) {
        prefs.setBeta(beta);
        return this;
    }

    public PrefsUpdater setClickGadget(boolean clickGadget) {
        prefs.setClickGadget(clickGadget);
        return this;
    }

    public PrefsUpdater setCollapseReadMessages(boolean collapseReadMessages) {
        prefs.setCollapseReadMessages(collapseReadMessages);
        return this;
    }

    public PrefsUpdater setCompress(boolean compress) {
        prefs.setCompress(compress);
        return this;
    }

    public PrefsUpdater setCountryCode(ApiEnumerators.CountryCode countryCode) {
        prefs.setCountryCode(countryCode);
        return this;
    }

    public PrefsUpdater setCredditAutorenew(boolean credditAutorenew) {
        prefs.setCredditAutorenew(credditAutorenew);
        return this;
    }

    public PrefsUpdater setDefaultCommentSort(ApiEnumerators.CommentSort defaultCommentSort) {
        prefs.setDefaultCommentSort(defaultCommentSort);
        return this;
    }

    public PrefsUpdater setDomainDetails(boolean domainDetails) {
        prefs.setDomainDetails(domainDetails);
        return this;
    }

    public PrefsUpdater setEmailChatRequest(boolean emailChatRequest) {
        prefs.setEmailChatRequest(emailChatRequest);
        return this;
    }

    public PrefsUpdater setEmailCommentReply(boolean emailCommentReply) {
        prefs.setEmailCommentReply(emailCommentReply);
        return this;
    }

    public PrefsUpdater setEmailDigests(boolean emailDigests) {
        prefs.setEmailDigests(emailDigests);
        return this;
    }

    public PrefsUpdater setEmailMessages(boolean emailMessages) {
        prefs.setEmailMessages(emailMessages);
        return this;
    }

    public PrefsUpdater setEmailPostReply(boolean emailPostReply) {
        prefs.setEmailPostReply(emailPostReply);
        return this;
    }

    public PrefsUpdater setEmailPrivateMessage(boolean emailPrivateMessage) {
        prefs.setEmailPrivateMessage(emailPrivateMessage);
        return this;
    }

    public PrefsUpdater setEmailUnsubscribeAll(boolean emailUnsubscribeAll) {
        prefs.setEmailUnsubscribeAll(emailUnsubscribeAll);
        return this;
    }

    public PrefsUpdater setEmailUpvoteComment(boolean emailUpvoteComment) {
        prefs.setEmailUpvoteComment(emailUpvoteComment);
        return this;
    }

    public PrefsUpdater setEmailUpvotePost(boolean emailUpvotePost) {
        prefs.setEmailUpvotePost(emailUpvotePost);
        return this;
    }

    public PrefsUpdater setEmailUserNewFollower(boolean emailUserNewFollower) {
        prefs.setEmailUserNewFollower(emailUserNewFollower);
        return this;
    }

    public PrefsUpdater setEmailUsernameMention(boolean emailUsernameMention) {
        prefs.setEmailUsernameMention(emailUsernameMention);
        return this;
    }

    public PrefsUpdater setEnableDefaultThemes(boolean enableDefaultThemes) {
        prefs.setEnableDefaultThemes(enableDefaultThemes);
        return this;
    }

    public PrefsUpdater setEnableFollowers(boolean enableFollowers) {
        prefs.setEnableFollowers(enableFollowers);
        return this;
    }

    public PrefsUpdater setFeedRecommendationsEnabled(boolean feedRecommendationsEnabled) {
        prefs.setFeedRecommendationsEnabled(feedRecommendationsEnabled);
        return this;
    }

    public PrefsUpdater setGeoPopular(ApiEnumerators.GeoPopular geoPopular) {
        prefs.setGeoPopular(geoPopular);
        return this;
    }

    public PrefsUpdater setHideAds(boolean hideAds) {
        prefs.setHideAds(hideAds);
        return this;
    }

    public PrefsUpdater setHideDowns(boolean hideDowns) {
        prefs.setHideDowns(hideDowns);
        return this;
    }

    public PrefsUpdater setHideFromRobots(boolean hideFromRobots) {
        prefs.setHideFromRobots(hideFromRobots);
        return this;
    }

    public PrefsUpdater setHideUps(boolean hideUps) {
        prefs.setHideUps(hideUps);
        return this;
    }

    public PrefsUpdater setHighlightControversial(boolean highlightControversial) {
        prefs.setHighlightControversial(highlightControversial);
        return this;
    }

    public PrefsUpdater setHighlightNewComments(boolean highlightNewComments) {
        prefs.setHighlightNewComments(highlightNewComments);
        return this;
    }

    public PrefsUpdater setIgnoreSuggestedSort(boolean ignoreSuggestedSort) {
        prefs.setIgnoreSuggestedSort(ignoreSuggestedSort);
        return this;
    }

    public PrefsUpdater setInRedesignBeta(boolean inRedesignBeta) {
        prefs.setInRedesignBeta(inRedesignBeta);
        return this;
    }

    public PrefsUpdater setLabelNsfw(boolean labelNsfw) {
        prefs.setLabelNsfw(labelNsfw);
        return this;
    }

    public PrefsUpdater setLang(Locale lang) {
        prefs.setLang(lang);
        return this;
    }

    public PrefsUpdater setLegacySearch(boolean legacySearch) {
        prefs.setLegacySearch(legacySearch);
        return this;
    }

    public PrefsUpdater setLiveOrangeReds(boolean liveOrangeReds) {
        prefs.setLiveOrangeReds(liveOrangeReds);
        return this;
    }

    public PrefsUpdater setMarkMessagesRead(boolean markMessagesRead) {
        prefs.setMarkMessagesRead(markMessagesRead);
        return this;
    }

    public PrefsUpdater setMedia(ApiEnumerators.Media media) {
        prefs.setMedia(media);
        return this;
    }

    public PrefsUpdater setMediaPreview(ApiEnumerators.Media mediaPreview) {
        prefs.setMediaPreview(mediaPreview);
        return this;
    }

    public PrefsUpdater setMinCommentScore(int minCommentScore) {
        prefs.setMinCommentScore(minCommentScore);
        return this;
    }

    public PrefsUpdater setMinLinkScore(int minLinkScore) {
        prefs.setMinLinkScore(minLinkScore);
        return this;
    }

    public PrefsUpdater setMonitorMentions(boolean monitorMentions) {
        prefs.setMonitorMentions(monitorMentions);
        return this;
    }

    public PrefsUpdater setNewWindow(boolean newWindow) {
        prefs.setNewWindow(newWindow);
        return this;
    }

    public PrefsUpdater setNightMode(boolean nightMode) {
        prefs.setNightMode(nightMode);
        return this;
    }

    public PrefsUpdater setNoProfanity(boolean noProfanity) {
        prefs.setNoProfanity(noProfanity);
        return this;
    }

    public PrefsUpdater setNumComments(int numComments) {
        prefs.setNumComments(numComments);
        return this;
    }

    public PrefsUpdater setNumSites(int numSites) {
        prefs.setNumSites(numSites);
        return this;
    }

    public PrefsUpdater setOrganic(boolean organic) {
        prefs.setOrganic(organic);
        return this;
    }

    public PrefsUpdater setOtherTheme(boolean otherTheme) {
        prefs.setOtherTheme(otherTheme);
        return this;
    }

    public PrefsUpdater setOver18(boolean over18) {
        prefs.setOver18(over18);
        return this;
    }

    public PrefsUpdater setPrivateFeeds(boolean privateFeeds) {
        prefs.setPrivateFeeds(privateFeeds);
        return this;
    }

    public PrefsUpdater setProfileOptOut(boolean profileOptOut) {
        prefs.setProfileOptOut(profileOptOut);
        return this;
    }

    public PrefsUpdater setPublicVotes(boolean publicVotes) {
        prefs.setPublicVotes(publicVotes);
        return this;
    }

    public PrefsUpdater setResearch(boolean research) {
        prefs.setResearch(research);
        return this;
    }

    public PrefsUpdater setSearchIncludeOver18(boolean searchIncludeOver18) {
        prefs.setSearchIncludeOver18(searchIncludeOver18);
        return this;
    }

    public PrefsUpdater setSendCrossPostMessages(boolean sendCrossPostMessages) {
        prefs.setSendCrossPostMessages(sendCrossPostMessages);
        return this;
    }

    public PrefsUpdater setSendWelcomeMessages(boolean sendWelcomeMessages) {
        prefs.setSendWelcomeMessages(sendWelcomeMessages);
        return this;
    }

    public PrefsUpdater setShowFlair(boolean showFlair) {
        prefs.setShowFlair(showFlair);
        return this;
    }

    public PrefsUpdater setShowGoldExpiration(boolean showGoldExpiration) {
        prefs.setShowGoldExpiration(showGoldExpiration);
        return this;
    }

    public PrefsUpdater setShowLinkFlair(boolean showLinkFlair) {
        prefs.setShowLinkFlair(showLinkFlair);
        return this;
    }

    public PrefsUpdater setShowLocationBasedRecommendations(boolean showLocationBasedRecommendations) {
        prefs.setShowLocationBasedRecommendations(showLocationBasedRecommendations);
        return this;
    }

    public PrefsUpdater setShowPresence(boolean showPresence) {
        prefs.setShowPresence(showPresence);
        return this;
    }

    public PrefsUpdater setShowPromote(boolean showPromote) {
        prefs.setShowPromote(showPromote);
        return this;
    }

    public PrefsUpdater setShowStylesheets(boolean showStylesheets) {
        prefs.setShowStylesheets(showStylesheets);
        return this;
    }

    public PrefsUpdater setShowTrending(boolean showTrending) {
        prefs.setShowTrending(showTrending);
        return this;
    }

    public PrefsUpdater setShowTwitter(boolean showTwitter) {
        prefs.setShowTwitter(showTwitter);
        return this;
    }

    public PrefsUpdater setStoreVisits(boolean storeVisits) {
        prefs.setStoreVisits(storeVisits);
        return this;
    }

    public PrefsUpdater setSurveyLastSeenTime(Long surveyLastSeenTime) {
        prefs.setSurveyLastSeenTime(surveyLastSeenTime);
        return this;
    }

    public PrefsUpdater setThemeSelector(String themeSelector) {
        prefs.setThemeSelector(themeSelector);
        return this;
    }

    public PrefsUpdater setThirdPartyDataPersonalizedAds(boolean thirdPartyDataPersonalizedAds) {
        prefs.setThirdPartyDataPersonalizedAds(thirdPartyDataPersonalizedAds);
        return this;
    }

    public PrefsUpdater setThirdPartyPersonalizedAds(boolean thirdPartyPersonalizedAds) {
        prefs.setThirdPartyPersonalizedAds(thirdPartyPersonalizedAds);
        return this;
    }

    public PrefsUpdater setThirdPartySiteDataPersonalizedAds(boolean thirdPartySiteDataPersonalizedAds) {
        prefs.setThirdPartySiteDataPersonalizedAds(thirdPartySiteDataPersonalizedAds);
        return this;
    }

    public PrefsUpdater setThirdPartySiteDataPersonalizedContent(boolean thirdPartySiteDataPersonalizedContent) {
        prefs.setThirdPartySiteDataPersonalizedContent(thirdPartySiteDataPersonalizedContent);
        return this;
    }

    public PrefsUpdater setThreadedMessages(boolean threadedMessages) {
        prefs.setThreadedMessages(threadedMessages);
        return this;
    }

    public PrefsUpdater setThreadedModMail(boolean threadedModMail) {
        prefs.setThreadedModMail(threadedModMail);
        return this;
    }

    public PrefsUpdater setTopKarmaSubreddits(boolean topKarmaSubreddits) {
        prefs.setTopKarmaSubreddits(topKarmaSubreddits);
        return this;
    }

    public PrefsUpdater setUseGlobalDefaults(boolean useGlobalDefaults) {
        prefs.setUseGlobalDefaults(useGlobalDefaults);
        return this;
    }

    public PrefsUpdater setVideoAutoplay(boolean videoAutoplay) {
        prefs.setVideoAutoplay(videoAutoplay);
        return this;
    }
}
