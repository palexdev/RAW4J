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

package io.github.palexdev.raw4j.api.listing.base;

import com.google.gson.Gson;
import io.github.palexdev.raw4j.data.base.Listing;
import io.github.palexdev.raw4j.oauth.base.OAuthFlow;
import io.github.palexdev.raw4j.utils.iterators.ListingIterator;
import io.github.palexdev.raw4j.utils.stream.ListingStreamSupport;

import java.util.stream.Stream;

import static io.github.palexdev.raw4j.json.GsonInstance.fromJson;

/**
 * Listings can represent up to 100 items at a time, but they specify two
 * string properties ('after' and 'before') that allow navigation between listings.
 * <p>
 * This helper class is responsible for managing and navigating through listings.
 * <p>
 * Note that this is an abstract class because it specifies the common properties and behavior
 * for all endpoints that return a listing but sub-classes must specify how to build the request url
 * and how to update the fetched items count.
 * <p></p>
 * Common properties and behavior are:
 * <p> - count property: the total number of items fetched
 * <p> - limit property: the max number of items to fetch when sending a request (by default 25, max value is 100)
 * <p> - get(): to get the "first page" (listing)
 * <p> - next(T listing): to get the "next page" (listing) starting from the given listing
 * <p> - previous(T listing): to get the "previous page" (listing) starting from the given listing
 * <p></p>
 * The class implements {@link Iterable} making navigation through listings even easier by using an iterator, see
 * {@link ListingIterator}, and it supports {@link Stream}s too, see {@link ListingStreamSupport}.
 *
 * @param <T> the type of {@link Listing}
 */
public abstract class ListingRequestBuilder<T extends Listing> implements Iterable<T> {
    //================================================================================
    // Properties
    //================================================================================
    protected final OAuthFlow authManager;
    protected int count;
    protected int limit = 25;

    //================================================================================
    // Constructors
    //================================================================================
    protected ListingRequestBuilder(OAuthFlow authManager) {
        this.authManager = authManager;
    }

    //================================================================================
    // Abstract Methods
    //================================================================================

    /**
     * Sets the maximum number of items to fetch.
     */
    public abstract ListingRequestBuilder<T> setLimit(int limit);

    /**
     * Must return the exact type of {@link Listing}.
     * <p>
     * This is needed by Gson for {@link Gson#fromJson(String, Class)}.
     *
     * @return the listing class
     */
    protected abstract Class<T> getType();

    /**
     * The implementation of this method should produce a correct URL
     * for the request to the Reddit API.
     */
    protected abstract String buildRequestURL(String parameters);

    /**
     * Updates the number of items fetched.
     */
    protected abstract void updateCount(T listing);

    //================================================================================
    // Methods
    //================================================================================

    /**
     * Builds the request URL and returns a {@link Listing} of type {@link T}.
     * <p></p>
     * Invokes protected method {@link #get(String)}.
     */
    public T get() {
        String url = buildRequestURL("");
        return get(url);
    }

    /**
     * Tells the auth manager to send a request with the given URL and retrieve a {@link Listing} object.
     * <p>
     * Once it is fetched the count property is updated, {@link #getCount()}.
     *
     * @return a new {@link Listing} of type {@link T}
     */
    protected T get(String url) {
        T listing = fromJson(authManager.get(url), getType());
        updateCount(listing);
        return listing;
    }

    /**
     * Makes a request to the Reddit API by calling {@link #get(String)}, to fetch the next listing from the
     * given one. The parameters include the {@link Listing#getAfter()} string, the limit of items to fetch and the
     * count of items fetched till now.
     *
     * @return the next listing or null if the 'after' property of the given listing is null
     */
    public T next(T listing) {
        if (listing.getAfter() == null) {
            return null;
        }

        String parameters = String.format("&after=%s&limit=%d&count=%d", listing.getAfter(), limit, count);
        String url = buildRequestURL(parameters);
        return get(url);
    }

    /**
     * Makes a request to the Reddit API by calling {@link #get(String)}, to fetch the previous listing from the
     * given one. The parameters include the {@link Listing#getBefore()} string, the limit of items to fetch and the
     * count of items fetched till now.
     *
     * @return the previous listing or null if the 'before' property of the given listing is null
     */
    public T previous(T listing) {
        if (listing.getBefore() == null) {
            return null;
        }

        String parameters = String.format("&before=%s&limit=%d&count=%d", listing.getBefore(), limit, count);
        String url = buildRequestURL(parameters);
        return get(url);
    }

    /**
     * @return the number of fetched items
     */
    public int getCount() {
        return count;
    }

    /**
     * @return a new instance of {@link ListingIterator}
     */
    @Override
    public ListingIterator<T> iterator() {
        return new ListingIterator<>(this);
    }
}
