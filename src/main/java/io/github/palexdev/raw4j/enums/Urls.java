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

public enum Urls {
    OAUTH_API_BASE_URL("https://oauth.reddit.com"),
    OAUTH_AUTH_URL("https://ssl.reddit.com/api/v1/authorize"),
    OAUTH_TOKEN_URL("https://ssl.reddit.com/api/v1/access_token"),
    ;

    private final String url;

    Urls(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return url;
    }
}
