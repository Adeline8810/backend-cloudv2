package com.demo.adeline.config;

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
                registry.addMapping("/**")
                    .allowedOrigins(
                        "https://adeline8810.github.io", 
                        "http://localhost:4200"
                    ) // Eliminamos la URL del propio backend (no se necesita a sí mismo)
                    .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                    .allowedHeaders("*")
                    .exposedHeaders("Authorization") // Importante si luego usas tokens
                    .allowCredentials(true)
                    .maxAge(3600); // Cachear la respuesta de CORS por 1 hora
            }
        };
    }
}