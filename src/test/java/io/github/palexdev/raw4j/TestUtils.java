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

package io.github.palexdev.raw4j;

import io.github.palexdev.raw4j.json.GsonInstance;
import io.github.palexdev.raw4j.oauth.OAuthInfo;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class TestUtils {
    private static final Path tokenFileStore = Path.of(System.getProperty("user.home") + "/Desktop/ReddStoredToken.json");
    private static final Path tokenFileStore2 = Path.of(System.getProperty("user.home") + "/Desktop/RedditStoredToken2.json");

    private TestUtils() {}

    public static void storeAuthInfo(OAuthInfo authInfo) {
        try {
            String toWrite = GsonInstance.gson().toJson(authInfo);
            if (!Files.exists(tokenFileStore)) {
                Files.createFile(tokenFileStore);
            }
            Files.writeString(tokenFileStore, toWrite, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static OAuthInfo loadAuthInfo() {
        OAuthInfo authInfo = null;

        try (FileReader reader = new FileReader(tokenFileStore.toFile())) {
            authInfo = GsonInstance.gson().fromJson(reader, OAuthInfo.class);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return authInfo;
    }

    public static void storeAuthInfo2(OAuthInfo authInfo) {
        try {
            String toWrite = GsonInstance.gson().toJson(authInfo);
            if (!Files.exists(tokenFileStore2)) {
                Files.createFile(tokenFileStore2);
            }
            Files.writeString(tokenFileStore2, toWrite, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static OAuthInfo loadAuthInfo2() {
        OAuthInfo authInfo = null;

        try (FileReader reader = new FileReader(tokenFileStore2.toFile())) {
            authInfo = GsonInstance.gson().fromJson(reader, OAuthInfo.class);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return authInfo;
    }

    public static <T> boolean isSorted(List<T> list, Comparator<T> comparator) {
        if (list.isEmpty() || list.size() == 1) {
            return true;
        }

        Iterator<T> iter = list.iterator();
        T current, previous = iter.next();
        while (iter.hasNext()) {
            current = iter.next();
            if (comparator.compare(previous, current) > 0) {
                return false;
            }
            previous = current;
        }
        return true;
    }
}
