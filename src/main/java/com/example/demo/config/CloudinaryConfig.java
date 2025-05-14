package com.example.demo.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryConfig {

    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "dzk5oxhem",
                "api_key", "755272792445633",
                "api_secret", "SfmZbjpkp11IycMWKMTuEm5cXaU"
        ));
    }
}
