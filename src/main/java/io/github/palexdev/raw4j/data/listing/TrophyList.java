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

package io.github.palexdev.raw4j.data.listing;

import com.google.gson.annotations.SerializedName;
import io.github.palexdev.raw4j.data.base.AbstractThing;
import io.github.palexdev.raw4j.data.base.Thing;
import io.github.palexdev.raw4j.enums.ThingType;
import io.github.palexdev.raw4j.json.annotations.Wrapped;
import io.github.palexdev.raw4j.utils.filtering.TrophyFilterHelper;
import io.github.palexdev.raw4j.utils.filtering.base.Filterable;
import io.github.palexdev.raw4j.utils.sorting.TrophyListSortHelper;
import io.github.palexdev.raw4j.utils.sorting.base.Sortable;

import java.util.List;

/**
 * Thing of type {@link ThingType#TROPHY_LIST}, implements {@link Filterable} and {@link Sortable}, has empty name(not present in JSON).
 * <p></p>
 * This class gives a list of the curren user's trophies.
 */
@Wrapped("data")
public class TrophyList extends AbstractThing implements Filterable, Sortable {
    //================================================================================
    // Properties
    //================================================================================
    private transient final TrophyFilterHelper filterHelper = TrophyFilterHelper.filtering(this);
    private transient final TrophyListSortHelper sortHelper = TrophyListSortHelper.sorting(this);

    @SerializedName("trophies")
    private List<Trophy> trophyList;

    //================================================================================
    // Methods
    //================================================================================

    /**
     * @return the instance of the {@link TrophyFilterHelper} to filter the list of trophies
     */
    @Override
    public TrophyFilterHelper filtering() {
        return filterHelper;
    }

    /**
     * @return the instance of the {@link TrophyListSortHelper} to sort the list of trophies
     */
    @Override
    public TrophyListSortHelper sorting() {
        return sortHelper;
    }

    /**
     * @return the list of trophies
     * @see Trophy
     */
    public List<Trophy> trophies() {
        return trophyList;
    }

    //================================================================================
    // Getters
    //================================================================================
    @Override
    public String getName() {
        return "";
    }

    //================================================================================
    // Nested Classes
    //================================================================================

    /**
     * This represents the data structure in the 'trophies' array of the JSON returned by the Reddit API.
     * <p></p>
     * This structure has info about the trophy: id, description, granted date, icon40 and icon70, trophy name, url.
     * <p></p>
     * This is a {@link Thing} of type {@link ThingType#T6}, has empty name(not present in JSON).
     */
    @Wrapped("data")
    public static class Trophy extends AbstractThing {
        //================================================================================
        // Properties
        //================================================================================
        @SerializedName("award_id")
        private String awardID;

        private String description;

        @SerializedName("granted_at")
        private Long grantedTime;

        @SerializedName("icon_40")
        private String icon40;

        @SerializedName("icon_70")
        private String icon70;

        @SerializedName("name")
        private String trophyName;

        private String url;

        //================================================================================
        // Getters
        //================================================================================
        public String getAwardID() {
            return awardID;
        }

        public String getDescription() {
            return description;
        }

        public Long getGrantedTime() {
            return grantedTime;
        }

        public String getIcon40() {
            return icon40;
        }

        public String getIcon70() {
            return icon70;
        }

        public String getTrophyName() {
            return trophyName;
        }

        public String getUrl() {
            return url;
        }

        @Override
        public String getName() {
            return "";
        }
    }
}
