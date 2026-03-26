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
                    // IMPORTANTE: No uses "*". Pon las URLs específicas.
                    .allowedOrigins("https://adeline8810.github.io", "http://localhost:4200", 
                    		"http://localhost:8080",
                    		"https://adeline8810.github.io/proyectoSlam/slam",
                    		"https://adeline8810.github.io/proyectoSlam/","https://backend-ruth-slam.onrender.com")
                    .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                    .allowedHeaders("*")
                    .allowCredentials(true);
            }
        };
    }
}