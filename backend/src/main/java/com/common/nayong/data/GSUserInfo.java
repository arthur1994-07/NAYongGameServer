package com.common.nayong.data;

import com.common.nayong.entity.GSUserEntity;
import com.fasterxml.jackson.annotation.JsonProperty;

public class GSUserInfo {
    public static class Data {
        @JsonProperty public int UserNum;
        @JsonProperty public String UserId;
        @JsonProperty public String UserEmail;
        @JsonProperty public int UserType;

        public Data() {}
        public Data(GSUserEntity entity) {
            this.UserNum = entity.getUserNum();
            this.UserId = entity.getUserID();
            this.UserEmail = entity.getUserEmail();
            this.UserType = entity.getUserType();
        }
    }
}
