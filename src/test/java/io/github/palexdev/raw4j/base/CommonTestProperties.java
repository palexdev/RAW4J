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

package io.github.palexdev.raw4j.base;

import io.github.palexdev.raw4j.api.RedditClient;
import io.github.palexdev.raw4j.oauth.OAuthParameters;
import io.github.palexdev.raw4j.utils.ClientUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;

public class CommonTestProperties {
    protected final Logger logger = LoggerFactory.getLogger("Test Logger");

    protected final String host = "127.0.0.1";
    protected final int port = 8888;
    protected final URL redirectURI = ClientUtils.url("http", host, port, "/");
    protected OAuthParameters parameters;
    protected RedditClient redditClient;
}
