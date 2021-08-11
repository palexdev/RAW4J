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

    @Override
    public String toString() {
        return name().toLowerCase();
    }

    public static List<Scopes> all() {
        return List.of(values());
    }

    public static String from(List<Scopes> scopes) {
        StringBuilder sb = new StringBuilder();
        for (Scopes scope : scopes) {
            sb.append(scope.toString()).append(",");
        }
        return StringUtils.replaceLast(sb.toString(), ",", "");
    }
}
