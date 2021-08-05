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

package io.github.palexdev.raw4j.oauth;

import com.google.gson.annotations.SerializedName;
import io.github.palexdev.raw4j.enums.Scopes;
import io.github.palexdev.raw4j.json.Exclude;

import java.time.Instant;
import java.util.List;

public class OAuthInfo {
    //================================================================================
    // Properties
    //================================================================================
    @SerializedName("access_token")
    @Exclude
    private String accessToken;

    @SerializedName("toke_type")
    @Exclude
    private String tokenType;

    @SerializedName("expires_in")
    @Exclude
    private Integer expiresIn;

    @SerializedName("expire_time")
    @Exclude
    private Long expireTime;

    @SerializedName("refresh_token")
    private String refreshToken;

    private transient boolean permanent;
    private transient List<Scopes> scopes;

    //================================================================================
    // Getters, Setters
    //================================================================================
    public String getAccessToken() {
        return accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public Integer getExpiresIn() {
        return expiresIn;
    }

    public Long getExpireTime() {
        return expireTime;
    }

    void setExpireTime(Long expireTime) {
        this.expireTime = expireTime;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public boolean isPermanent() {
        return permanent;
    }

    void setPermanent(boolean permanent) {
        this.permanent = permanent;
    }

    public List<Scopes> getScopes() {
        return scopes;
    }

    void setScopes(List<Scopes> scopes) {
        this.scopes = scopes;
    }

    public boolean isValid() {
        return expiresIn != null && Instant.now().getEpochSecond() < expireTime;
    }

    void revoke(boolean isAccessToken) {
        if (isAccessToken) {
            accessToken = null;
            tokenType = null;
            expiresIn = null;
            expireTime = null;
        } else {
            refreshToken = null;
        }
    }
}
