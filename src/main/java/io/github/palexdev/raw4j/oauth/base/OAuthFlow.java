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

package io.github.palexdev.raw4j.oauth.base;

import com.google.gson.JsonObject;
import io.github.palexdev.raw4j.api.UserApi;
import io.github.palexdev.raw4j.exception.OAuthException;
import io.github.palexdev.raw4j.oauth.OAuthData;
import io.github.palexdev.raw4j.oauth.OAuthInfo;
import io.github.palexdev.raw4j.oauth.OAuthParameters;
import okhttp3.RequestBody;

/**
 * Public API that all OAuth flows must implement.
 */
public interface OAuthFlow {

    /**
     * Main method, this is responsible for authenticating the app.
     *
     * @throws OAuthException in case the authentication fails for whatever reason
     */
    void authenticate() throws OAuthException;

    /**
     * This is the implementation of the HTTP DELETE method using OkHttp.
     *
     * @param url the resource URL
     * @return the response as a {@link JsonObject}
     */
    JsonObject delete(String url);

    /**
     * This is the implementation of the HTTP GET method using OkHttp.
     *
     * @param url the resource URL
     * @return the response as a {@link JsonObject}
     */
    JsonObject get(String url);

    /**
     * Executes an HTTP GET request and parses a boolean from the response.
     * Needed for {@link UserApi#usernameAvailable(String)} as the response is not
     * a valid JSON but just a boolean.
     *
     * @param url the resource URL
     * @return the response as a boolean, if failed to parse returns null
     */
    Boolean getBoolean(String url);

    /**
     * This is the implementation of the HTTP PATCH method using OkHttp.
     *
     * @param url the resource URL
     * @param requestBody the request body
     * @return the response as a {@link JsonObject}
     */
    JsonObject patch(String url, RequestBody requestBody);

    /**
     * This is the implementation of the HTTP POST method using OkHttp.
     *
     * @param url the resource URL
     * @param requestBody the request body
     * @return the response as a {@link JsonObject}
     */
    JsonObject post(String url, RequestBody requestBody);

    /**
     * This is the implementation of the HTTP PUT method using OkHttp.
     *
     * @param url the resource URL
     * @param requestBody the request body
     * @return the response as a {@link JsonObject}
     */
    JsonObject put(String url, RequestBody requestBody);

    /**
     * @return an instance of {@link OAuthInfo} relative to the current/used OAuthFlow
     */
    OAuthInfo getAuthInfo();

    /**
     * @return an instance of {@link OAuthData} relative to the current/used OAuthFlow
     */
    OAuthData getAuthData();

    /**
     * @return the parameters used for the authentication
     */
    OAuthParameters getParameters();
}
