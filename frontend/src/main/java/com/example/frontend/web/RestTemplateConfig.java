package com.example.frontend.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@ComponentScan
@Configuration
public class RestTemplateConfig {
    @Bean
    RestTemplate getRestTemplate() {
        return new RestTemplate();
    }
}
