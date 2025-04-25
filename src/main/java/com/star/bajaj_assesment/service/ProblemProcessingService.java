package com.star.bajaj_assesment.service;

import com.star.bajaj_assesment.exception.ApiException;
import com.star.bajaj_assesment.model.domain.Problem;
import com.star.bajaj_assesment.model.request.InitialRequest;
import com.star.bajaj_assesment.model.request.WebhookRequest;
import com.star.bajaj_assesment.model.response.WebhookResponse;
import com.star.bajaj_assesment.service.api.ApiService;
import com.star.bajaj_assesment.service.api.WebhookService;
import com.star.bajaj_assesment.service.auth.AuthService;
import com.star.bajaj_assesment.service.solver.ProblemSolver;
import com.star.bajaj_assesment.service.solver.ProblemSolverFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProblemProcessingService {

    private final ApiService apiService;
    private final WebhookService webhookService;
    private final AuthService authService;
    private final ProblemSolverFactory problemSolverFactory;
    private final InitialRequest initialRequest;
    
    @Value("${app.user.regNo}")
    private String regNo;

    public boolean processProblem() {
        try {
            // Step 1: Generate webhook and obtain problem
            log.info("Starting problem processing flow");
            WebhookResponse webhookResponse = apiService.generateWebhook(initialRequest);
            String webhookUrl = webhookResponse.getWebhook();
            String token = webhookResponse.getAccessToken();
            
            // Step 2: Save the JWT token
            authService.setToken(token);
            
            // Step 3: Extract and validate the problem
            Problem problem = webhookResponse.extractProblem();
            if (problem == null) {
                throw new ApiException("Failed to extract valid problem from API response");
            }
            
            // Step 4: Solve the problem using appropriate solver
            ProblemSolver solver = problemSolverFactory.getSolver(problem);
            Object solution = solver.solve(problem);
            
            log.info("Problem solved. Solution: {}", solution);
            
            // Step 5: Submit the solution - COMMENTED OUT FOR NOW
            /* 
            WebhookRequest webhookRequest = WebhookRequest.builder()
                    .regNo(regNo)
                    .outcome(solution)
                    .build();
            
            return webhookService.submitResult(webhookUrl, token, webhookRequest);
            */
            
            // Just return true since we're not submitting yet
            return true;
        } catch (Exception e) {
            log.error("Error during problem processing", e);
            return false;
        }
    }
} 