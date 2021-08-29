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
 * Enumeration for all the Reddit OAuth URLs.
 */
public enum URLEnum {

    /**
     * The base URL for all API endpoints.
     */
    OAUTH_API_BASE_URL("https://oauth.reddit.com"),

    /**
     * URL used by Apps without a logged-in user.
     */
    OAUTH_APP_ONLY_URL("https://oauth.reddit.com/grants/installed_client"),

    /**
     * URL used by Web Apps and Installed Apps with a logged-in user.
     */
    OAUTH_AUTH_URL("https://ssl.reddit.com/api/v1/authorize"),

    /**
     * URL used to retrieve access tokens.
     */
    OAUTH_TOKEN_URL("https://ssl.reddit.com/api/v1/access_token"),

    /**
     * URL used to revoke access and refresh tokens.
     */
    REVOKE_TOKEN_URL("https://www.reddit.com/api/v1/revoke_token")
    ;

    private final String url;

    URLEnum(String url) {
        this.url = url;
    }

    /**
     * Overridden to return the URL String.
     */
    @Override
    public String toString() {
        return url;
    }
}
