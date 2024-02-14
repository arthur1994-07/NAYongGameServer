package com.common.nayong.configuration;

import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.OAuthTokenCredential;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class PaymentConfig {
    @Value("${paypal.mode}")
    private String mMode;
    @Value("${paypal.client.id}")
    private String mClientId;
    @Value("${paypal.client.secret}")
    private String mClientSecret;

    @Bean
    public Map<String, String> paypalSdkConfig() {
        Map<String, String> sdkConfig = new HashMap<>();
        sdkConfig.put("mode", mMode);
        return sdkConfig;
    }

    @Bean
    public OAuthTokenCredential authTokenCredential() {
        return new OAuthTokenCredential(mClientId, mClientSecret, paypalSdkConfig());
    }

    @Bean
    public APIContext apiContext() throws Exception {
        var apiContext = new APIContext(mClientId, mClientSecret, mMode);
        apiContext.setConfigurationMap(paypalSdkConfig());
        return apiContext;
    }
}
