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
import io.github.palexdev.raw4j.data.base.AbstractThing;
import io.github.palexdev.raw4j.data.base.Thing;
import io.github.palexdev.raw4j.enums.ThingType;

/**
 * Data structure to represent Reddit's awards.
 * <p></p>
 * Extends {@link AbstractThing}, it's a {@link Thing} of type {@link ThingType#T6}
 */
public class Award extends AbstractThing {
    //================================================================================
    // Properties
    //================================================================================
    @SerializedName("name")
    private String awardName;

    @SerializedName("coin_price")
    private int cost;

    private String description;

    @SerializedName("icon_url")
    private String iconUrl;

    public Award() {}

    public Award(String awardName, int cost, String description, String iconUrl) {
        this.awardName = awardName;
        this.cost = cost;
        this.description = description;
        this.iconUrl = iconUrl;
    }

    /**
     * @return the award's name
     */
    public String getAwardName() {
        return awardName;
    }

    /**
     * @return the award's cost
     */
    public int getCost() {
        return cost;
    }

    /**
     * @return the award's description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return the award's icon url
     */
    public String getIconUrl() {
        return iconUrl;
    }

    /**
     * @return the gild type (award id), same as {@link #getID()}
     */
    public String getGildType() {
        return getID();
    }

    @Override
    public String getName() {
        return "";
    }
}
