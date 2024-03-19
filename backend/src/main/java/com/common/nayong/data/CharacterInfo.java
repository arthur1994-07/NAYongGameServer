package com.common.nayong.data;

import com.common.nayong.entity.CharacterEntity;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CharacterInfo {
    public static class Data {
        @JsonProperty public int ChaNum;
        @JsonProperty public String ChaName;
        @JsonProperty public int ChaSchool;
        @JsonProperty public int ChaLevel;
        @JsonProperty public int ChaOnline;
        @JsonProperty public int ContributePoint;
        @JsonProperty public String ChaClass;
        @JsonProperty public int UserNum;

        public Data() {}
        public Data(CharacterEntity entity) {
            this.ChaNum = entity.getChaNum();
            this.ChaName = entity.getChaName();
            this.ChaSchool = entity.getChaSchool();
            this.ChaLevel = entity.getChaLevel();
            this.ChaOnline = entity.getChaOnline();
            this.ContributePoint = entity.getContributionPoint();
            this.ChaClass = entity.getChaClass();
            this.UserNum = entity.getUserNum();
        }
    }
}
