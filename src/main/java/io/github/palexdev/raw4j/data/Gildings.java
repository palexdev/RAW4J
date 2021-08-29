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

package io.github.palexdev.raw4j.data;

import com.google.gson.annotations.SerializedName;

/**
 * Data structure to represent info about Reddit gildings.
 */
public class Gildings {
    //================================================================================
    // Properties
    //================================================================================
    @SerializedName("gid_1")
    public Integer silver;

    @SerializedName("gid_2")
    public Integer gold;

    @SerializedName("gid_3")
    public Integer platinum;

    //================================================================================
    // Getters
    //================================================================================
    public Integer getSilver() {
        return silver;
    }

    public Integer getGold() {
        return gold;
    }

    public Integer getPlatinum() {
        return platinum;
    }
}
