package com.pdev.rempms.communicationservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author maleeshasa
 * @Date 30/11/2023
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Allow all paths
                .allowedOrigins("http://localhost:4200") // With my angular front end URL
                .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH") // Allowed Http methods
                .allowedHeaders("*"); // Allowed headers
    }
}
