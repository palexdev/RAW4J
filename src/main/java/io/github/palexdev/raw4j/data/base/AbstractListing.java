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

import com.google.gson.annotations.SerializedName;
import io.github.palexdev.raw4j.enums.ThingType;
import io.github.palexdev.raw4j.json.annotations.IgnoreWrap;
import io.github.palexdev.raw4j.json.annotations.Wrapped;

/**
 * This is the base class for every {@code Listing}.
 * <p></p>
 * Common properties of {@code Listings} are:
 * <p> - type (JSON: kind)
 * <p> - after: specifies the which will be the next listing
 * <p> - before: specifies which was the previous listing
 * <p></p>
 * Implements {@link Listing}
 */
@Wrapped("data")
public abstract class AbstractListing implements Listing {
    //================================================================================
    // Properties
    //================================================================================
    @SerializedName("kind")
    @IgnoreWrap
    private ThingType type;

    private String after;
    private String before;

    //================================================================================
    // Getters
    //================================================================================
    @Override
    public ThingType getType() {
        return type;
    }

    @Override
    public String getAfter() {
        return after;
    }

    @Override
    public String getBefore() {
        return before;
    }
}
