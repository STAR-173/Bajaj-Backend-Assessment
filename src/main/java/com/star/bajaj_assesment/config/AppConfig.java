package com.star.bajaj_assesment.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.retry.annotation.EnableRetry;

import com.star.bajaj_assesment.model.request.InitialRequest;

@Configuration
@EnableRetry
@EnableAspectJAutoProxy
public class AppConfig {

    @Value("${app.user.name}")
    private String userName;

    @Value("${app.user.regNo}")
    private String regNo;

    @Value("${app.user.email}")
    private String email;

    @Bean
    public InitialRequest initialRequest() {
        return InitialRequest.builder()
                .name(userName)
                .regNo(regNo)
                .email(email)
                .build();
    }
    
    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
} 