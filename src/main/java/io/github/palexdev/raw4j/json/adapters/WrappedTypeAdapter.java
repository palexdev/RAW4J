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

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import io.github.palexdev.raw4j.data.Comment;
import io.github.palexdev.raw4j.data.Post;
import io.github.palexdev.raw4j.data.base.Submission;
import io.github.palexdev.raw4j.data.listing.Overview;
import io.github.palexdev.raw4j.enums.ThingType;
import io.github.palexdev.raw4j.json.GsonInstance;
import io.github.palexdev.raw4j.json.annotations.IgnoreWrap;
import io.github.palexdev.raw4j.json.annotations.Wrapped;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Gson adapter to deal with most Reddit models.
 * <p>
 * At Reddit they like to json data in other useless objects, this adapter
 * deals with this by unwrapping the data during deserialization and wrapping it back
 * during serialization.
 * <p></p>
 * To achieve this a model class (or fields) must make use of these new annotations:
 * {@link Wrapped}, {@link IgnoreWrap}
 */
public class WrappedTypeAdapter<T> extends TypeAdapter<T> {
    private final Wrapped wrapped;
    private final TypeAdapter<T> delegateAdapter;
    private final TypeAdapter<JsonElement> elementTypeAdapter;

    public WrappedTypeAdapter(TypeToken<T> type, TypeAdapter<T> delegateAdapter) {
        this.wrapped = type.getRawType().getAnnotation(Wrapped.class);
        this.delegateAdapter = delegateAdapter;
        this.elementTypeAdapter = GsonInstance.getAdapter(JsonElement.class);
    }

    @Override
    public void write(JsonWriter out, T value) throws IOException {
        JsonElement element = delegateAdapter.toJsonTree(value);
        if (element.isJsonNull()) {
            out.nullValue();
            return;
        }

        JsonObject root = element.getAsJsonObject(); // TODO may throw exception getAsJsonObject()
        JsonObject finalObject = relocate(root, value);

        // Special handling for Overviews
        if (value instanceof Overview overview) {
            List<Submission> submissions = overview.submissions();
            finalObject.getAsJsonObject("data").add("children", GsonInstance.toJsonTree(submissions));
        }

        elementTypeAdapter.write(out, finalObject);
    }

    protected JsonObject relocate(JsonObject root, T value) {
        List<FieldData> fieldData = retrieveFieldData(value.getClass());
        for (FieldData fd : fieldData) {
            if (!fd.path.equals("root")) {
                String name = getPath(fd.path);
                JsonObject path;
                try {
                    path = root.get(name).getAsJsonObject();
                } catch (NullPointerException ex) {
                    path = handlePath(root, fd.path);
                }
                JsonElement element = root.remove(fd.name);
                path.add(fd.name, element);
            }
        }
        return root;
    }


    private JsonObject handlePath(JsonObject root, String path) {
        String[] paths = path.split("\\.");
        JsonObject last = null;
        for (String s : paths) {
            JsonObject obj = new JsonObject();
            root.add(s, obj);
            last = obj;
        }
        return last;
    }

    private String getPath(String fullPath) {
        String[] paths = fullPath.split("\\.");
        return paths[paths.length - 1];
    }

    private List<FieldData> retrieveFieldData(Class<?> clazz) {
        List<FieldData> fieldData = new ArrayList<>();
        while (clazz != Object.class) {
            List<Field> declaredFields = Arrays.stream(clazz.getDeclaredFields())
                    .filter(field -> !Modifier.isTransient(field.getModifiers()))
                    .collect(Collectors.toList());
            for (Field field : declaredFields) {
                FieldData data = new FieldData(field, field.getGenericType(), getPropertyName(field), getPropertyPath(field, clazz));
                fieldData.add(data);
            }
            clazz = clazz.getSuperclass();
        }
        fieldData.sort(Comparator.comparing(FieldData::getName));
        return fieldData;
    }

    private String getPropertyName(Field field) {
        SerializedName serializedName = field.getAnnotation(SerializedName.class);
        return serializedName != null ? serializedName.value() : field.getName();
    }

    private String getPropertyPath(Field field, Class<?> clazz) {
        Wrapped classWrapped = clazz.getAnnotation(Wrapped.class);
        Wrapped fieldWrapped = field.getAnnotation(Wrapped.class);

        String path = "root";
        if (fieldWrapped != null) {
            path = fieldWrapped.value();
        } else if (classWrapped != null && !field.isAnnotationPresent(IgnoreWrap.class)) {
            path = classWrapped.value();
        }
        return path;
    }

    @Override
    public T read(JsonReader in) throws IOException {
        JsonElement element = elementTypeAdapter.read(in);
        if (element.isJsonNull()) {
            return null;
        }

        JsonObject json = element.getAsJsonObject();
        JsonObject unwrapped = (JsonObject) json.remove(wrapped.value());
        if (unwrapped != null && !unwrapped.equals(json)) {
            unwrapped.entrySet().forEach(entry -> json.add(entry.getKey(), entry.getValue()));
        }

        T val = delegateAdapter.fromJsonTree(json);

        // Special handling for Overviews
        if (val.getClass() == Overview.class) {
            List<Comment> comments = new ArrayList<>();
            List<Post> posts = new ArrayList<>();
            JsonArray submissions = json.getAsJsonArray("children");
            for (JsonElement submission : submissions) {
                JsonObject objSub = submission.getAsJsonObject();
                ThingType type = ThingType.from(objSub.get("kind").getAsString());
                if (type == ThingType.T1) {
                    Comment comment = GsonInstance.fromJson(objSub, Comment.class);
                    comments.add(comment);
                } else {
                    Post post = GsonInstance.fromJson(objSub, Post.class);
                    posts.add(post);
                }
            }

            try {
                Field commentsField = val.getClass().getDeclaredField("comments");
                Field postsField = val.getClass().getDeclaredField("posts");
                commentsField.setAccessible(true);
                postsField.setAccessible(true);
                commentsField.set(val, comments);
                postsField.set(val, posts);
            } catch (NoSuchFieldException | IllegalAccessException ex) {
                ex.printStackTrace();
            }
        }

        return val;
    }

    private record FieldData(Field field, Type type, String name, String path) {

        @SuppressWarnings("All")
        public void write(JsonWriter writer, Object value) throws IOException {
            try {
                Object fieldValue = field.get(value);
                TypeAdapter t = GsonInstance.getAdapter(TypeToken.get(type));
                t.write(writer, fieldValue);
            } catch (IllegalAccessException ex) {
                ex.printStackTrace();
            }
        }

        public String getName() {
            return name;
        }

        private int childrenNum() {
            return path.split("\\.").length;
        }
    }
}
