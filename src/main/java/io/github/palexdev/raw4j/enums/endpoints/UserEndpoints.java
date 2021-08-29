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

package io.github.palexdev.raw4j.enums.endpoints;

/**
 * Enumerator that specifies all the 'User' endpoints, implements {@link Endpoints}.
 */
public enum UserEndpoints implements Endpoints {
    ABOUT("/user/%s/about"),
    BLOCK("/api/block_user"),
    COMMENTS("/user/%s/comments"),
    DOWNVOTED("/user/%s/downvoted"),
    FRIENDS("/api/v1/me/friends/%s"),
    GILDED("/user/%s/gilded"),
    GILDED_GIVEN("/user/%s/gilded/given"),
    HIDDEN("/user/%s/hidden"),
    OVERVIEW("/user/%s/overview"),
    POSTS("/user/%s/submitted"),
    REPORT("/api/report_user"),
    SAVED("/user/%s/saved"),
    TROPHIES("/api/v1/user/%s/trophies"),
    UNFRIEND("/api/unfriend"),
    UPVOTED("/user/%s/upvoted"),
    USERNAME_AVAILABLE("/api/username_available?user=%s");

    private final String endpoint;

    UserEndpoints(String endpoint) {
        this.endpoint = endpoint;
    }

    @Override
    public String getEndpoint() {
        return endpoint;
    }
}
