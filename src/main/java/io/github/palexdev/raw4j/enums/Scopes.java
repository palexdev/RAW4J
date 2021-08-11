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

import io.github.palexdev.raw4j.utils.StringUtils;

import java.util.List;

/**
 * This is an enumeration of all the Reddit Scopes
 */
public enum Scopes {
    ACCOUNT,
    CREDDITS,
    EDIT,
    FLAIR,
    HISTORY,
    IDENTITY,
    LIVEMANAGE,
    MODCONFIG,
    MODCONTRIBUTORS,
    MODFLAIR,
    MODLOG,
    MODMAIL,
    MODOTHERS,
    MODPOSTS,
    MODSELF,
    MODTRAFFIC,
    MODWIKI,
    MYSUBREDDITS,
    PRIVATEMESSAGES,
    READ,
    REPORT,
    SAVE,
    STRUCTUREDSTYLES,
    SUBMIT,
    SUBSCRIBE,
    VOTE,
    WIKIEDIT,
    WIKIREAD;

    /**
     * Overridden to return the enumeration name lower case.
     */
    @Override
    public String toString() {
        return name().toLowerCase();
    }

    /**
     * @return a list containing all the scopes
     */
    public static List<Scopes> all() {
        return List.of(values());
    }

    /**
     * Scopes are passed as a String separated by a space or a comma.
     * <p>
     * This method does exactly that, from a list of scopes builds a comma separated
     * String that contains all the scopes specified in the list.
     */
    public static String from(List<Scopes> scopes) {
        StringBuilder sb = new StringBuilder();
        for (Scopes scope : scopes) {
            sb.append(scope.toString()).append(",");
        }
        return StringUtils.replaceLast(sb.toString(), ",", "");
    }
}
