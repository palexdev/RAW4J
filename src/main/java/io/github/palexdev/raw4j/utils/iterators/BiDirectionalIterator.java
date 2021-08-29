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

package io.github.palexdev.raw4j.utils.iterators;

import java.util.Iterator;

/**
 * Extension of {@link Iterator} to add hasPrevious() and previous() support.
 */
public interface BiDirectionalIterator<T> extends Iterator<T> {

    enum Direction {
        FORWARD, BACKWARD
    }

    /**
     * Returns {@code true} if the iteration has more elements when
     * traversing the in the forward direction. (In other words,
     * returns {@code true} if {@link #next} would return an element).
     *
     * @return {@code true} if the iteration has more elements when
     * traversing in the forward direction
     */
    boolean hasNext();

    /**
     * Returns the next element in the iteration.
     *
     * @return the next element in the iteration
     */
    T next();

    /**
     * Returns {@code true} if the iteration has more elements when
     * traversing in the reverse direction.  (In other words,
     * returns {@code true} if {@link #previous} would return an element.)
     *
     * @return {@code true} if the iteration has more elements when
     * traversing in the reverse direction
     */
    boolean hasPrevious();

    /**
     * Returns the previous element in the iteration.
     *
     * @return the previous element in the list
     */
    T previous();
}
