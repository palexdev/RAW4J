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

import io.github.palexdev.raw4j.enums.ThingType;

/**
 * Public API for all {@code Listings}.
 * <p>
 * According to Reddit documentation a listing is like a page that can show up to 100 items at a time.
 * To navigate through pages they include two string properties, 'after' and 'before'.
 */
public interface Listing {

    /**
     * @return the type of the listing
     */
    ThingType getType();

    /**
     * @return the after String of the listing
     */
    String getAfter();

    /**
     * @return the before String of the listing
     */
    String getBefore();
}
