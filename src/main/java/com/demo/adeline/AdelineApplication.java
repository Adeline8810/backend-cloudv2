package com.demo.adeline;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate;
import jakarta.annotation.PostConstruct;

@SpringBootApplication
@ComponentScan(basePackages = "com.demo.adeline")
public class AdelineApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdelineApplication.class, args);
    }
    
    @PostConstruct
    public void test() {
        System.out.println("URL: " + System.getenv("SPRING_DATASOURCE_URL"));
    }
    
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}