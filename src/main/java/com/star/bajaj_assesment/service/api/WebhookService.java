package com.star.bajaj_assesment.service.api;

import com.star.bajaj_assesment.model.request.WebhookRequest;

public interface WebhookService {
    boolean submitResult(String webhookUrl, String token, WebhookRequest request);
} 