package com.star.bajaj_assesment.service.api;

import com.star.bajaj_assesment.model.request.InitialRequest;
import com.star.bajaj_assesment.model.response.WebhookResponse;

public interface ApiService {
    WebhookResponse generateWebhook(InitialRequest request);
} 