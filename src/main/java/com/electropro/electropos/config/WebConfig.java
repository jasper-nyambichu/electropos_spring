package com.electropro.electropos.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Enables cross-origin requests from the frontend.
 *
 * Allowed origins are read from the ALLOWED_ORIGINS env var (comma separated),
 * e.g. "http://localhost:3000,https://your-frontend.vercel.app".
 * Falls back to localhost:3000 for local dev if the env var isn't set.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${ALLOWED_ORIGINS:http://localhost:3000}")
    private String allowedOrigins;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(allowedOrigins.split(","))
                .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(3600);
    }
}