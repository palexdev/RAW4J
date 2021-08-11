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

package io.github.palexdev.raw4j.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.Option;
import com.jayway.jsonpath.spi.json.GsonJsonProvider;
import com.jayway.jsonpath.spi.json.JsonProvider;
import com.jayway.jsonpath.spi.mapper.GsonMappingProvider;
import com.jayway.jsonpath.spi.mapper.MappingProvider;
import io.github.palexdev.raw4j.data.KarmaList;
import io.github.palexdev.raw4j.data.TrophyList;
import io.github.palexdev.raw4j.data.UserList;
import io.github.palexdev.raw4j.json.adapters.JsonPathTypeAdapterFactory;
import io.github.palexdev.raw4j.json.adapters.KarmaListSerializer;
import io.github.palexdev.raw4j.json.adapters.TrophyListSerializer;
import io.github.palexdev.raw4j.json.adapters.UserListSerializer;

import java.util.EnumSet;
import java.util.Set;

/**
 * Class to obtain a global instance of Gson
 */
public class GsonInstance {
    //================================================================================
    // Properties
    //================================================================================
    private final static Gson gson;

    //================================================================================
    // Initialization
    //================================================================================
    static {
        gson = new GsonBuilder()
                .addSerializationExclusionStrategy(OAuthInfoExclusionStrategy.instance())
                .disableHtmlEscaping()
                .registerTypeAdapter(KarmaList.class, new KarmaListSerializer())
                .registerTypeAdapter(TrophyList.class, new TrophyListSerializer())
                .registerTypeAdapter(UserList.class, new UserListSerializer())
                .registerTypeAdapterFactory(JsonPathTypeAdapterFactory.getJsonPathTypeAdapterFactory())
                .setPrettyPrinting()
                .create();

        final JsonProvider jsonProvider = new GsonJsonProvider(gson);
        final MappingProvider gsonMappingProvider = new GsonMappingProvider(gson);
        Configuration.setDefaults(new Configuration.Defaults() {
            @Override
            public JsonProvider jsonProvider() {
                return jsonProvider;
            }

            @Override
            public MappingProvider mappingProvider() {
                return gsonMappingProvider;
            }

            @Override
            public Set<Option> options() {
                return EnumSet.noneOf(Option.class);
            }
        });
    }

    //================================================================================
    // Constructors
    //================================================================================
    private GsonInstance() {}

    //================================================================================
    // Getters
    //================================================================================
    public static Gson gson() {
        return gson;
    }
}
