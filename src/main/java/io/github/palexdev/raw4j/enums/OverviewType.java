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

import io.github.palexdev.raw4j.data.listing.Overview;

/**
 * Enumerator to specify the type of {@link Overview}.
 */
public enum OverviewType {

    /**
     * All posts and comments.
     */
    ALL,

    /**
     * Awarded posts and comments.
     */
    AWARDED,

    /**
     * Posts and comments to which an award was given.
     */
    AWARD_GIVEN,

    /**
     * Saved posts and comments
     */
    SAVED
}
