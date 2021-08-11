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

import io.github.palexdev.raw4j.data.base.AbstractThing;
import io.github.palexdev.raw4j.json.annotations.JsonPathExpression;
import io.github.palexdev.raw4j.utils.sorting.TrophyListSortHelper;
import io.github.palexdev.raw4j.utils.sorting.base.Sortable;

import java.util.List;

public class TrophyList extends AbstractThing implements Sortable {
    //================================================================================
    // Properties
    //================================================================================
    private transient final TrophyListSortHelper helper = new TrophyListSortHelper(this);

    @JsonPathExpression("data.trophies")
    private List<Trophy> trophyList;

    //================================================================================
    // Constructors
    //================================================================================
    public TrophyList(List<Trophy> trophyList) {
        this.trophyList = trophyList;
    }

    //================================================================================
    // Methods
    //================================================================================
    @Override
    public TrophyListSortHelper sorting() {
        return helper;
    }

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
    public static class Trophy extends AbstractThing {
        //================================================================================
        // Properties
        //================================================================================
        @JsonPathExpression("data.award_id")
        private String awardID;

        @JsonPathExpression("data.description")
        private String description;

        @JsonPathExpression("data.granted_at")
        private Long grantedTime;

        @JsonPathExpression("data.icon_40")
        private String icon40;

        @JsonPathExpression("data.icon_70")
        private String icon70;

        @JsonPathExpression("data.name")
        private String trophyName;

        @JsonPathExpression("data.url")
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
