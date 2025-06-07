package com.technokratos.kirillakhmetov.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "google.oauth")
public class GoogleOAuthProperties {
    private String clientId;
    private String clientSecret;
    private String redirectUri;
    private String authUri;
    private String tokenUrl;
    private String userInfoUrl;
    private String responseType;
    private String accessType;
    private String prompt;
    private String scope;
    private String grantType;
} 