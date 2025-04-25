package com.star.bajaj_assesment.model.response;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.star.bajaj_assesment.model.domain.Problem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WebhookResponse {
    private String webhook;
    private String accessToken;
    private Object data;
    
    public Problem extractProblem() {
        return extractProblemFromData(data);
    }
    
    private Problem extractProblemFromData(Object data) {
        if (data == null) {
            return null;
        }
        
        try {
            ObjectMapper mapper = new ObjectMapper();
            
            // Check if data is already a Problem
            if (data instanceof Map) {
                // The 'users' field in the response is a nested object that contains the real problem data
                Map<String, Object> dataMap = (Map<String, Object>) data;
                
                // Handle nested structure where the problem data is inside a 'users' field
                if (dataMap.containsKey("users") && dataMap.get("users") instanceof Map) {
                    return mapper.convertValue(dataMap.get("users"), Problem.class);
                }
            }
            
            // Fallback to direct conversion
            return mapper.convertValue(data, Problem.class);
        } catch (Exception e) {
            log.error("Failed to convert data to Problem: {}", e.getMessage());
            return null;
        }
    }
} 