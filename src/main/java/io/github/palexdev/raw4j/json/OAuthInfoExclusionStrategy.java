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

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import io.github.palexdev.raw4j.json.annotations.Exclude;
import io.github.palexdev.raw4j.oauth.OAuthInfo;

/**
 * Custom exclusion strategy for Gson and {@link OAuthInfo}. Fields annotated with {@link Exclude} won't be serialized.
 */
public class OAuthInfoExclusionStrategy implements ExclusionStrategy {
    //================================================================================
    // Properties
    //================================================================================
    private static final OAuthInfoExclusionStrategy strategy = new OAuthInfoExclusionStrategy();

    //================================================================================
    // Constructors
    //================================================================================
    private OAuthInfoExclusionStrategy() {}

    //================================================================================
    // Getters
    //================================================================================
    public static OAuthInfoExclusionStrategy instance() {
        return strategy;
    }

    //================================================================================
    // Override Methods
    //================================================================================
    @Override
    public boolean shouldSkipField(FieldAttributes field) {
        return field.getAnnotation(Exclude.class) != null;
    }

    @Override
    public boolean shouldSkipClass(Class<?> clazz) {
        return false;
    }
}
