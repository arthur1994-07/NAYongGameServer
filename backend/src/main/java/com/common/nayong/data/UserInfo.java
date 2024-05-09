package com.common.nayong.data;

import com.common.nayong.entity.UserEntity;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UserInfo {
    public static class Data {
        @JsonProperty public int UserNum;
        @JsonProperty public String UserId;
        @JsonProperty public String UserEmail;
        @JsonProperty public int UserType;

        public Data() {}
        public Data(UserEntity entity) {
            this.UserNum = entity.getUserNum();
            this.UserId = entity.getUserID();
            this.UserEmail = entity.getUserEmail();
            this.UserType = entity.getUserType();
        }
    }
}
