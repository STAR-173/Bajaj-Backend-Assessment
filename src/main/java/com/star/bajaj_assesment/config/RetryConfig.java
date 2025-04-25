package com.star.bajaj_assesment.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

@Configuration
public class RetryConfig {

    @Value("${retry.max-attempts:4}")
    private int maxAttempts;

    @Value("${retry.initial-backoff:1000}")
    private long initialBackoff;

    @Value("${retry.backoff-multiplier:2}")
    private double backoffMultiplier;

    @Value("${retry.max-backoff:10000}")
    private long maxBackoff;

    @Bean
    public RetryTemplate retryTemplate() {
        RetryTemplate retryTemplate = new RetryTemplate();
        
        ExponentialBackOffPolicy backOffPolicy = new ExponentialBackOffPolicy();
        backOffPolicy.setInitialInterval(initialBackoff);
        backOffPolicy.setMultiplier(backoffMultiplier);
        backOffPolicy.setMaxInterval(maxBackoff);
        
        SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy();
        retryPolicy.setMaxAttempts(maxAttempts);
        
        retryTemplate.setBackOffPolicy(backOffPolicy);
        retryTemplate.setRetryPolicy(retryPolicy);
        
        return retryTemplate;
    }
} 