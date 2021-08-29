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

package io.github.palexdev.raw4j.json.adapters;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import io.github.palexdev.raw4j.data.listing.KarmaList;
import io.github.palexdev.raw4j.enums.ThingType;

import java.lang.reflect.Type;

@Deprecated(forRemoval = true)
public class KarmaListSerializer implements JsonSerializer<KarmaList> {

    @Override
    public JsonElement serialize(KarmaList src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject karmaListObject = new JsonObject();
        karmaListObject.addProperty("kind", ThingType.KARMA_LIST.toString());
        JsonElement subreddits = context.serialize(src.subreddits());
        karmaListObject.add("data", subreddits);
        return karmaListObject;
    }
}
