package com.star.bajaj_assesment.service.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.star.bajaj_assesment.exception.ApiException;
import com.star.bajaj_assesment.model.request.InitialRequest;
import com.star.bajaj_assesment.model.response.WebhookResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@RequiredArgsConstructor
public class ApiServiceImpl implements ApiService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Value("${api.base-url}")
    private String baseUrl;

    @Value("${api.generate-webhook-endpoint}")
    private String generateWebhookEndpoint;

    @Override
    @Retryable(
        value = {RestClientException.class},
        maxAttempts = 4,
        backoff = @Backoff(delay = 1000, multiplier = 2, maxDelay = 10000)
    )
    public WebhookResponse generateWebhook(InitialRequest request) {
        try {
            log.info("Making initial API call to generate webhook with request: {}", request);
            String url = baseUrl + generateWebhookEndpoint;
            
            log.info("POST request to URL: {}", url);
            
            ResponseEntity<WebhookResponse> response = restTemplate.postForEntity(
                url,
                request,
                WebhookResponse.class
            );
            
            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                WebhookResponse webhookResponse = response.getBody();
                
                try {
                    // Log the complete response
                    log.info("API Response: {}", objectMapper.writeValueAsString(webhookResponse));
                } catch (Exception e) {
                    log.debug("Could not serialize response for logging: {}", e.getMessage());
                }
                
                log.debug("Response data structure: {}", webhookResponse.getData());
                return webhookResponse;
            } else {
                log.error("Failed to generate webhook. Status code: {}", response.getStatusCode());
                throw new ApiException("Failed to generate webhook. API returned status: " + response.getStatusCode());
            }
        } catch (RestClientException e) {
            log.error("Error making API call to generate webhook", e);
            throw e;
        } catch (Exception e) {
            log.error("Unexpected error when generating webhook", e);
            throw new ApiException("Unexpected error when generating webhook", e);
        }
    }
} 