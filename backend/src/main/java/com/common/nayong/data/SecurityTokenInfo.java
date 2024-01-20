package com.common.nayong.data;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SecurityTokenInfo {
    @JsonProperty public String accessToken;
    @JsonProperty public long expiration;


    public SecurityTokenInfo() {}
    public SecurityTokenInfo(String accessToken, long expiration) {
        this.accessToken = accessToken;
        this.expiration = expiration;
    }
}
