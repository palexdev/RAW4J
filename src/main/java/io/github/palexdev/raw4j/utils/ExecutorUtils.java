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

package io.github.palexdev.raw4j.utils;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class ExecutorUtils {
    //================================================================================
    // Properties
    //================================================================================
    private static final ScheduledExecutorService scheduled = Executors.newScheduledThreadPool(1, r -> {
        Thread thread = Executors.defaultThreadFactory().newThread(r);
        thread.setName("RAW4J - ScheduledExecutor");
        thread.setDaemon(true);
        return thread;
    });

    //================================================================================
    // Constructors
    //================================================================================
    private ExecutorUtils() {}

    //================================================================================
    // Methods
    //================================================================================
    public static ScheduledExecutorService scheduled() {
        return scheduled;
    }

}
