package com.demo.adeline.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
	    // Esto es más compatible con Railway
	    registry.addResourceHandler("/uploads/**")
	            .addResourceLocations("file:uploads/"); 
	}
}
