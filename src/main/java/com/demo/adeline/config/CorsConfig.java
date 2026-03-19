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
	            registry.addMapping("/**") // Cambiamos /api/** por /** para cubrir todo
	                    //.allowedOrigins("*")
	                    .allowedOrigins("https://adeline8810.github.io")
	                    .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
	                    .allowedHeaders("Origin", "Content-Type", "Accept", "Authorization", "X-Requested-With", "Access-Control-Allow-Origin")
	                    .exposedHeaders("Content-Type", "Access-Control-Allow-Origin")
	                    .allowCredentials(true)
	                    .maxAge(3600)
	                    .exposedHeaders("*"); // Ayuda a que el navegador vea las respuestas
	            
	                       
	        }
	    };
	}
}
