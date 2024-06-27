package com.common.nayong.configuration;

import com.common.core.web.permission.PermissionSystem;
import com.common.nayong.constants.PermissionConstant;
import com.common.nayong.permission.LicenseRightAnnotation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PermissionConfiguration {
    public static class DefaultPermissionSystem extends PermissionSystem<LicenseRightAnnotation> {
        public DefaultPermissionSystem() throws Exception {
            super(PermissionConstant.class, LicenseRightAnnotation.class);
        }
    }
    @Bean
    private static PermissionSystem<LicenseRightAnnotation> getPermissionRight() throws Exception { return new DefaultPermissionSystem(); }
}
