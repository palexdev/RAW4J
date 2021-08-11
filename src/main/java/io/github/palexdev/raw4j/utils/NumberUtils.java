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

package io.github.palexdev.raw4j.utils;

/**
 * Utils class for working with numbers.
 */
public class NumberUtils {

    //================================================================================
    // Constructors
    //================================================================================
    private NumberUtils() {}

    //================================================================================
    // Methods
    //================================================================================

    /**
     * Limits the given value to the given min-max range by returning the nearest bound
     * if it exceeds or val if it's in range.
     */
    public static double clamp(double val, double min, double max) {
        return Math.max(min, Math.min(max, val));
    }

    /**
     * Limits the given value to the given min-max range by returning the nearest bound
     * if it exceeds or val if it's in range.
     */
    public static float clamp(float val, float min, float max) {
        return Math.max(min, Math.min(max, val));
    }

    /**
     * Limits the given value to the given min-max range by returning the nearest bound
     * if it exceeds or val if it's in range.
     */
    public static int clamp(int val, int min, int max) {
        return Math.max(min, Math.min(max, val));
    }

    /**
     * Limits the given value to the given min-max range by returning the nearest bound
     * if it exceeds or val if it's in range.
     */
    public static long clamp(long val, long min, long max) {
        return Math.max(min, Math.min(max, val));
    }

}
