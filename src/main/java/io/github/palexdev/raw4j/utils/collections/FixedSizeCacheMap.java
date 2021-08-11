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

package io.github.palexdev.raw4j.utils.collections;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Implementation of a {@link LinkedHashMap} with a maximum capacity
 */
public class FixedSizeCacheMap<K, V> extends LinkedHashMap<K, V> {
    //================================================================================
    // Properties
    //================================================================================
    private int maxSize;

    //================================================================================
    // Constructors
    //================================================================================
    public FixedSizeCacheMap(int maxSize) {
        super(maxSize + 2, 1F);
        this.maxSize = maxSize;
    }

    //================================================================================
    // Methods
    //================================================================================

    /**
     * Sets the maximum capacity of the map.
     */
    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }

    //================================================================================
    // Override Methods
    //================================================================================

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return size() > maxSize;
    }
}
