package com.common.nayong.data;

import com.common.core.web.permission.PermissionSystem;
import com.common.nayong.entity.RoleEntity;
import com.common.nayong.permission.LicenseRightAnnotation;
import com.fasterxml.jackson.annotation.JsonProperty;


public class RoleInfo {
    public static class Base {
        @JsonProperty public long id;
        @JsonProperty public String name;

        public Base() {}
        public Base(long id, String name) {
            this.id = id;
            this.name = name;
        }
        public Base(RoleEntity entity) {
            this.id = entity.getId();
            this.name = entity.getName();
        }
    }

    public static class Data extends Base {
        @JsonProperty public String[] rights;

        public Data() {}
        public Data(RoleEntity entity) {
            super(entity);
            rights = entity.getRights().toArray(String[]::new);
        }
    }

    public static class Right {
        @JsonProperty public String id;
        @JsonProperty public String displayKey;

        public Right() {}
        public Right(PermissionSystem.Data<LicenseRightAnnotation> data) {
            this.id = data.databaseKey;
            this.displayKey = data.annotation.displayKey();
        }
    }
}
