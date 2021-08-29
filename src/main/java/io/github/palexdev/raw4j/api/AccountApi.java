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

package io.github.palexdev.raw4j.api;

import io.github.palexdev.raw4j.api.listing.UserListRequestBuilder;
import io.github.palexdev.raw4j.data.Prefs;
import io.github.palexdev.raw4j.data.PrefsUpdater;
import io.github.palexdev.raw4j.data.User;
import io.github.palexdev.raw4j.data.listing.KarmaList;
import io.github.palexdev.raw4j.data.listing.TrophyList;
import io.github.palexdev.raw4j.data.listing.UserList;
import io.github.palexdev.raw4j.enums.ThingType;
import io.github.palexdev.raw4j.enums.UserListType;
import io.github.palexdev.raw4j.enums.endpoints.AccountEndpoints;
import io.github.palexdev.raw4j.enums.endpoints.UserEndpoints;
import io.github.palexdev.raw4j.oauth.base.OAuthFlow;
import io.github.palexdev.raw4j.utils.iterators.ListingIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.github.palexdev.raw4j.json.GsonInstance.fromJson;

/**
 * This class contains all methods to interact with Account APIs.
 * <p></p>
 * In case of a logged user this also maintains a {@link User} reference of the logged user.
 * This reference is set for the first time by calling {@link #getLoggedUser()} (more info in the method documentation).
 */
public class AccountApi {
    //================================================================================
    // Properties
    //================================================================================
    private static final Logger logger = LoggerFactory.getLogger(RedditApiWrapper.class.getSimpleName()); // TODO remove?
    private final OAuthFlow authManager;
    private User loggedUser;

    //================================================================================
    // Constructors
    //================================================================================
    AccountApi(RedditApiWrapper wrapper) {
        this.authManager = wrapper.getAuthManager();
    }

    //================================================================================
    // API Implementation
    //================================================================================

    /**
     * Retrieves the {@link User} data structure of the logged user.
     * <p></p>
     * If the method has been already called once this won't execute a new request but rather return
     * the already retrieved reference.
     * <p>
     * If you need to refresh the data then you must call {@link #refreshLoggedUser()}
     * <p></p>
     * Notes on how this works: Reddit offers two ways of retrieving info about your own account, there's the
     * {@code `/api/v1/me`} endpoint and the {@code `/user/username/about`} endpoint. This may sound strange but this method
     * uses the latter one because they return almost the same info but the JSON structure of the first one would need a separate deserializer
     * or a new class, the latter one instead returns a JSON of type {@link ThingType#T2}.
     */
    public User getLoggedUser() {
        if (loggedUser != null) {
            return loggedUser;
        }

        if (!authManager.getAuthData().isValidUsername()) {
            User user = getMe();
            authManager.getAuthData().setUsername(user.getUsername());
        }

        String url = UserEndpoints.ABOUT.getFullEndpointRaw().formatted(authManager.getAuthData().getUsername());
        loggedUser = fromJson(authManager.get(url), User.class);
        return loggedUser;
    }

    /**
     * @return the {@link User} data structure of the logged user. This uses the {@code `/api/v1/me`} endpoint.
     */
    public User getMe() {
        String url = AccountEndpoints.ME.getFullEndpointRaw();
        return fromJson(authManager.get(url), User.class);
    }

    /**
     * @return a breakdown of subreddit karma.
     * @see KarmaList
     */
    public KarmaList getKarmaList() {
        String url = AccountEndpoints.ME_KARMA.getFullEndpointRaw();
        return fromJson(authManager.get(url), KarmaList.class);
    }

    /**
     * @return the settings list of the logged user.
     */
    public Prefs getPrefs() {
        String url = AccountEndpoints.ME_PREFS.getFullEndpointRaw();
        return fromJson(authManager.get(url), Prefs.class);
    }

    /**
     * @return a list of trophies for the current user.
     * @see TrophyList
     */
    public TrophyList getTrophyList() {
        String url = AccountEndpoints.ME_TROPHIES.getFullEndpointRaw();
        return fromJson(authManager.get(url), TrophyList.class);
    }

    /**
     * @return a new instance of {@link PrefsUpdater} to update the logged user's preferences.
     */
    public PrefsUpdater updatePrefs() {
        return new PrefsUpdater(authManager);
    }

    /**
     * This method should be used only when it's needed to refresh the info related to the logged user, note that
     * this returns an instance of this class, to get the new refreshed info you must then call {@link #getLoggedUser()}
     */
    public AccountApi refreshLoggedUser() {
        String url = UserEndpoints.ABOUT.getFullEndpointRaw().formatted(authManager.getAuthData().getUsername());
        loggedUser = fromJson(authManager.get(url), User.class);
        return this;
    }

    //================================================================================
    // Iterators
    //================================================================================

    /**
     * @return a new iterator to iterate through {@link UserList} listings of the type.
     */
    public ListingIterator<UserList> userListIterator(UserListType type) {
        return userListRequestBuilder(type).iterator();
    }

    //================================================================================
    // Request Builders
    //================================================================================

    /**
     * @return a new instance of {@link UserListRequestBuilder} for the given {@link UserListType}, to get and navigate through {@link UserList}s
     */
    public UserListRequestBuilder userListRequestBuilder(UserListType type) {
        return new UserListRequestBuilder(authManager, type);
    }
}
