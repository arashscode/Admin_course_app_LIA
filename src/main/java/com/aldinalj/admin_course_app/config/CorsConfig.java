package com.aldinalj.admin_course_app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // Tillåter alla endpoints
                        .allowedOrigins("http://localhost:5173") // Anpassa efter frontendens URL
                        .allowedMethods("GET", "POST", "PUT", "DELETE") // Tillåtna HTTP-metoder
                        .allowedHeaders("*") // Tillåter alla headers
                        .allowCredentials(true);
            }
        };
    }
}
