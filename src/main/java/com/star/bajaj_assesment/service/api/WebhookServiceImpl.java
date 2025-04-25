package com.star.bajaj_assesment.service.api;

import com.star.bajaj_assesment.exception.ApiException;
import com.star.bajaj_assesment.model.request.WebhookRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
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
public class WebhookServiceImpl implements WebhookService {

    private final RestTemplate restTemplate;

    @Override
    @Retryable(
        value = {RestClientException.class},
        maxAttempts = 4,
        backoff = @Backoff(delay = 1000, multiplier = 2, maxDelay = 10000)
    )
    public boolean submitResult(String webhookUrl, String token, WebhookRequest request) {
        try {
            log.info("Submitting result to webhook: {}", webhookUrl);
            
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", token);
            headers.set("Content-Type", "application/json");
            
            HttpEntity<WebhookRequest> entity = new HttpEntity<>(request, headers);
            
            ResponseEntity<Void> response = restTemplate.postForEntity(
                webhookUrl,
                entity,
                Void.class
            );
            
            if (response.getStatusCode() == HttpStatus.OK) {
                log.info("Successfully submitted result to webhook");
                return true;
            } else {
                log.error("Failed to submit result to webhook. Status code: {}", response.getStatusCode());
                return false;
            }
        } catch (RestClientException e) {
            log.error("Error submitting result to webhook", e);
            throw e;
        } catch (Exception e) {
            log.error("Unexpected error when submitting result to webhook", e);
            throw new ApiException("Unexpected error when submitting result to webhook", e);
        }
    }
} 