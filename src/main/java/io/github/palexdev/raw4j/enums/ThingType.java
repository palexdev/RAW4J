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

import java.util.Arrays;

public enum ThingType {
    @SerializedName("t1")
    T1("t1"),

    @SerializedName("t2")
    T2("t2"),

    @SerializedName("t3")
    T3("t3"),

    @SerializedName("t4")
    T4("t4"),

    @SerializedName("t5")
    T5("t5"),

    @SerializedName("t6")
    T6("t6"),

    @SerializedName("KarmaList")
    KARMA_LIST("KarmaList"),

    @SerializedName("Listing")
    LISTING("Listing"),

    @SerializedName("TrophyList")
    TROPHY_LIST("TrophyList"),

    @SerializedName("UserList")
    USER_LIST("UserList")
    ;

    private final String s;

    ThingType(String s) {
        this.s = s;
    }

    public static ThingType from(String name) {
        return Arrays.stream(values())
                .filter(t -> t.toString().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }

    @Override
    public String toString() {
        return s;
    }
}
