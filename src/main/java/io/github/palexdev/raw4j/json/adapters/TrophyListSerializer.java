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

import com.google.gson.*;
import io.github.palexdev.raw4j.data.TrophyList;
import io.github.palexdev.raw4j.data.TrophyList.Trophy;
import io.github.palexdev.raw4j.enums.ThingType;

import java.lang.reflect.Type;

public class TrophyListSerializer implements JsonSerializer<TrophyList> {
    private final TrophySerializer trophySerializer = new TrophySerializer();

    @Override
    public JsonElement serialize(TrophyList src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject trophyListObject = new JsonObject();
        trophyListObject.addProperty("kind", ThingType.TROPHY_LIST.toString());
        JsonObject data = new JsonObject();
        trophyListObject.add("data", data);
        JsonArray trophyList = new JsonArray();
        src.trophies().forEach(trophy -> trophyList.add(trophySerializer.serialize(trophy, Trophy.class, context)));
        data.add("trophies", trophyList);
        return trophyListObject;
    }

    public static class TrophySerializer implements JsonSerializer<Trophy> {

        @Override
        public JsonElement serialize(Trophy src, Type typeOfSrc, JsonSerializationContext context) {
            JsonObject trophyObject = new JsonObject();
            trophyObject.addProperty("kind", ThingType.T6.toString());
            JsonObject data = new JsonObject();
            trophyObject.add("data", data);
            data.add("icon_70", context.serialize(src.getIcon70(), String.class));
            data.add("icon_40", context.serialize(src.getIcon40(), String.class));
            data.add("granted_at", context.serialize(src.getGrantedTime(), Long.class));
            data.add("url", context.serialize(src.getUrl(), String.class));
            data.add("name", context.serialize(src.getTrophyName(), String.class));
            data.add("award_id", context.serialize(src.getAwardID(), String.class));
            data.add("id", context.serialize(src.getID(), String.class));
            data.add("description", context.serialize(src.getDescription(), String.class));
            return trophyObject;
        }
    }
}
