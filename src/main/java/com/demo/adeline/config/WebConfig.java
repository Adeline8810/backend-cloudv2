package com.demo.adeline.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/uploads/**")
                // Primero busca en la ruta de Linux/Railway
                .addResourceLocations("file:/tmp/uploads/")
                // Si no, busca en la carpeta del proyecto (Windows/Local)
                .addResourceLocations("file:uploads/")
                .setCachePeriod(0); 
    }
}