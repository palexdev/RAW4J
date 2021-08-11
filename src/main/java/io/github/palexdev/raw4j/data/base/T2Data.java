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

package io.github.palexdev.raw4j.data.base;

import io.github.palexdev.raw4j.api.AccountApi;
import io.github.palexdev.raw4j.api.UserApi;

public interface T2Data {

    /**
     * @return the user's karma from received awards
     */
    int getAwardeeKarma();

    /**
     * @return the user's karma from given awards
     */
    int getAwarderKarma();

    /**
     * @return the user's comment karma
     */
    int getCommentKarma();

    /**
     * @return whether the user has unread mails. Always false if not your account
     */
    boolean hasUnreadMail();

    /**
     * @return whether the user has unread mod mails. Always false if not your account
     */
    boolean hasUnreadModMail();

    /**
     * @return whether the user's email is verified
     */
    boolean isEmailVerified();

    /**
     * @return the number of unread messages in the inbox. Null if not your account
     */
    Integer getInboxCount();

    /**
     * @return whether the logged-in user has this user set as a friend. This is null if calling {@link AccountApi#getMe()}
     * or false if calling {@link UserApi#getUser(String)} on your own account
     */
    Boolean isFriend();

    /**
     * @return the reddit gold status
     */
    boolean isGold();

    /**
     * @return whether this account moderates any subreddits
     */
    boolean isMod();

    /**
     * @return the user's link karma
     */
    int getLinkKarma();

    /**
     * @return the current modhash. Null if not your account
     */
    String getModhash();

    /**
     * @return whether this account is set to be over 18
     */
    boolean isOver18();

    /**
     * @return the Reddit username
     */
    String getUsername();

    /**
     * @return the sum of {@link #getAwardeeKarma()}, {@link #getAwarderKarma()}, {@link #getCommentKarma()} and {@link #getLinkKarma()}
     */
    int getTotalKarma();
}
