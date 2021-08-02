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
import com.google.gson.JsonElement;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.PathNotFoundException;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import static com.jayway.jsonpath.JsonPath.compile;

public final class JsonPathTypeAdapterFactory implements TypeAdapterFactory {
    private static final TypeAdapterFactory jsonPathTypeAdapterFactory = new JsonPathTypeAdapterFactory();

    private JsonPathTypeAdapterFactory() {
    }

    static TypeAdapterFactory getJsonPathTypeAdapterFactory() {
        return jsonPathTypeAdapterFactory;
    }

    @Override
    public <T> TypeAdapter<T> create(final Gson gson, final TypeToken<T> typeToken) {
        final TypeAdapter<T> delegateAdapter = gson.getDelegateAdapter(this, typeToken);
        final Collection<FieldInfo> fieldInfos = FieldInfo.of(new ArrayList<>(), typeToken.getRawType());
        return fieldInfos.isEmpty() ? delegateAdapter : new JsonPathTypeAdapter<>(gson, delegateAdapter, gson.getAdapter(JsonElement.class), fieldInfos);
    }

    private static final class JsonPathTypeAdapter<T> extends TypeAdapter<T> {
        private final Gson gson;
        private final TypeAdapter<T> delegateAdapter;
        private final TypeAdapter<JsonElement> jsonElementTypeAdapter;
        private final Collection<FieldInfo> fieldInfos;

        private JsonPathTypeAdapter(final Gson gson, final TypeAdapter<T> delegateAdapter, final TypeAdapter<JsonElement> jsonElementTypeAdapter, final Collection<FieldInfo> fieldInfos) {
            this.gson = gson;
            this.delegateAdapter = delegateAdapter;
            this.jsonElementTypeAdapter = jsonElementTypeAdapter;
            this.fieldInfos = fieldInfos;
        }

        @Override
        public void write(final JsonWriter out, final T value) throws IOException {
            delegateAdapter.write(out, value);
        }

        @Override
        public T read(final JsonReader in) throws IOException {
            final JsonElement outerJsonElement = jsonElementTypeAdapter.read(in).getAsJsonObject();
            final T value = delegateAdapter.fromJsonTree(outerJsonElement);
            for (final FieldInfo fieldInfo : fieldInfos) {
                try {
                    final JsonElement innerJsonElement = fieldInfo.jsonPath.read(outerJsonElement);
                    final Object innerValue = gson.fromJson(innerJsonElement, fieldInfo.field.getType());
                    fieldInfo.field.set(value, innerValue);
                } catch (final PathNotFoundException ignored) {
                } catch (final IllegalAccessException ex) {
                    throw new IOException(ex);
                }
            }
            return value;
        }

    }

    private static final class FieldInfo {
        private final Field field;
        private final JsonPath jsonPath;

        private FieldInfo(final Field field, final JsonPath jsonPath) {
            this.field = field;
            this.jsonPath = jsonPath;
        }

        private static Collection<FieldInfo> of(Collection<FieldInfo> fieldInfo, Class<?> clazz) {
            fieldInfo.addAll(
                    Arrays.stream(clazz.getDeclaredFields())
                            .filter(f -> f.getAnnotation(JsonPathExpression.class) != null)
                            .map(f -> {
                                final JsonPathExpression jsonPathExpression = f.getAnnotation(JsonPathExpression.class);
                                f.setAccessible(true);
                                return new FieldInfo(f, compile(jsonPathExpression.value()));
                            })
                            .collect(Collectors.toList())
            );

            if (clazz.getSuperclass() != null) {
                of(fieldInfo, clazz.getSuperclass());
            }

            return fieldInfo;
        }
    }
}