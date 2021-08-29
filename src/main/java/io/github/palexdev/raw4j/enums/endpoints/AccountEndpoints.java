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
 * Enumerator that specifies all the 'Account' endpoints, implements {@link Endpoints}.
 */
public enum AccountEndpoints implements Endpoints {
    ME("/api/v1/me"),
    ME_KARMA("/api/v1/me/karma"),
    ME_PREFS("/api/v1/me/prefs"),
    ME_TROPHIES("/api/v1/me/trophies"),
    PREFS_FRIENDS("/api/v1/me/friends"),
    PREFS_BLOCKED("/prefs/blocked"),
    PREFS_TRUSTED("/prefs/trusted");


    private final String endpoint;

    AccountEndpoints(String endpoint) {
        this.endpoint = endpoint;
    }

    @Override
    public String getEndpoint() {
        return endpoint;
    }
}
