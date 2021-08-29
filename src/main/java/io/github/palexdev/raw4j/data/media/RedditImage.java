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

package io.github.palexdev.raw4j.data.media;

import java.util.Comparator;
import java.util.List;

// TODO variants not introduced, seem unnecessary

/**
 * Extends {@link Media}.
 * <p></p>
 * Data structure to represent a Reddit image.
 */
public class RedditImage extends Media {
    //================================================================================
    // Properties
    //================================================================================
    private String id;
    private List<Source> resolutions;
    private Source source;

    //================================================================================
    // Getters
    //================================================================================

    /**
     * @return the ID of the image
     */
    public String getId() {
        return id;
    }

    /**
     * @return the list of resolutions for this image
     */
    public List<Source> getResolutions() {
        return resolutions;
    }

    /**
     * @return the maximum resolution for this image
     */
    public Source getMaxResolution() {
        return resolutions.stream().max(Comparator.comparing(src -> src.height * src.width)).orElse(null);
    }

    /**
     * @return the image's source
     */
    public Source getSource() {
        return source;
    }
}
