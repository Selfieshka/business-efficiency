package com.technokratos.kirillakhmetov.config;

import com.cloudinary.Cloudinary;
import com.technokratos.kirillakhmetov.config.properties.CloudinaryProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class CloudinaryConfig {

    @Bean
    public Cloudinary cloudinary(CloudinaryProperties cloudinaryProperties) {
        return new Cloudinary(
                Map.of(
                        "cloud_name", cloudinaryProperties.getCloudName(),
                        "api_key", cloudinaryProperties.getApiKey(),
                        "api_secret", cloudinaryProperties.getApiSecret()
                )
        );
    }
}
