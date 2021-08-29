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

import com.google.gson.annotations.SerializedName;

public enum SubredditType {

    @SerializedName("archived")
    ARCHIVED("archived"),

    @SerializedName("employees_only")
    EMPLOYEES_ONLY("employees_only"),

    @SerializedName("gold_only")
    GOLD_ONLY("gold_only"),

    @SerializedName("gold_restricted")
    GOLD_RESTRICTED("gold_restricted"),

    @SerializedName("private")
    PRIVATE("private"),

    @SerializedName("public")
    PUBLIC("public"),

    @SerializedName("restricted")
    RESTRICTED("restricted"),

    @SerializedName("user")
    USER("user");

    private final String s;

    SubredditType(String s) {
        this.s = s;
    }

    @Override
    public String toString() {
        return s;
    }
}
