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

import com.google.gson.annotations.SerializedName;
import io.github.palexdev.raw4j.json.annotations.Wrapped;

/**
 * Extends {@link Media}.
 * <p></p>
 * Data structure to represent a Reddit video.
 */
@Wrapped("reddit_video")
public class RedditVideo extends Media {
    //================================================================================
    // Properties
    //================================================================================
    @SerializedName("bitrate_kbps")
    private Double bitrate;

    @SerializedName("dash_url")
    private String dashUrl;

    @SerializedName("duration")
    private Double duration;

    @SerializedName("fallback_url")
    private String fallbackUrl;

    @SerializedName("hls_url")
    private String hlsUrl;

    @SerializedName("is_gif")
    private Boolean gif;

    @SerializedName("scrubber_media_url")
    private String scrubberMediaUrl;

    @SerializedName("transcoding_status")
    private String transcodingStatus;

    //================================================================================
    // Getters
    //================================================================================

    /**
     * @return the video's bitrate
     */
    public Double getBitrate() {
        return bitrate;
    }

    /**
     * @return the MPEG-DASH url to the video
     */
    public String getDashUrl() {
        return dashUrl;
    }

    /**
     * @return the video's duration
     */
    public Double getDuration() {
        return duration;
    }

    /**
     * @return a fallback url to the video
     */
    public String getFallbackUrl() {
        return fallbackUrl;
    }

    /**
     * @return the HLS url to the video
     */
    public String getHlsUrl() {
        return hlsUrl;
    }

    /**
     * @return whether this video is a gif
     */
    public Boolean isGif() {
        return gif;
    }

    public String getScrubberMediaUrl() {
        return scrubberMediaUrl;
    }

    public String getTranscodingStatus() {
        return transcodingStatus;
    }
}
