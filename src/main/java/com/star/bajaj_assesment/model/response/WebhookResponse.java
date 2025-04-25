package com.star.bajaj_assesment.model.response;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.star.bajaj_assesment.model.domain.Problem;
import com.star.bajaj_assesment.model.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WebhookResponse {
    private String webhook;
    private String accessToken;
    private Object data;
    
    /**
     * Extracts the problem from the response data.
     * Handles both Question 1 and Question 2 formats.
     *
     * @return Problem object or null if extraction fails
     */
    public Problem extractProblem() {
        return extractProblemFromData(data);
    }
    
    private Problem extractProblemFromData(Object data) {
        if (data == null) {
            log.error("Response data is null");
            return null;
        }
        
        try {
            ObjectMapper mapper = new ObjectMapper();
            
            // First convert the data to a JsonNode for easier navigation
            JsonNode dataNode = mapper.valueToTree(data);
            log.debug("Data structure: {}", dataNode);
            
            // Check if this is Question 1 format (just a list of users)
            if (dataNode.has("users") && dataNode.get("users").isArray()) {
                List<User> users = new ArrayList<>();
                JsonNode usersNode = dataNode.get("users");
                
                for (JsonNode userNode : usersNode) {
                    User user = mapper.treeToValue(userNode, User.class);
                    users.add(user);
                }
                
                return new Problem(users, null, null);
            }
            
            // Check if this is Question 2 format (users, n, findId)
            else if (dataNode.has("users") && dataNode.get("users").has("users")) {
                Integer n = null;
                Integer findId = null;
                List<User> users = new ArrayList<>();
                
                if (dataNode.get("users").has("n")) {
                    n = dataNode.get("users").get("n").asInt();
                }
                
                if (dataNode.get("users").has("findId")) {
                    findId = dataNode.get("users").get("findId").asInt();
                }
                
                if (dataNode.get("users").has("users") && dataNode.get("users").get("users").isArray()) {
                    JsonNode usersNode = dataNode.get("users").get("users");
                    
                    for (JsonNode userNode : usersNode) {
                        User user = mapper.treeToValue(userNode, User.class);
                        users.add(user);
                    }
                }
                
                return new Problem(users, n, findId);
            }
            
            // Fallback to direct conversion if the above doesn't work
            log.warn("Using fallback method for problem extraction");
            return mapper.convertValue(data, Problem.class);
            
        } catch (Exception e) {
            log.error("Failed to convert data to Problem: {}", e.getMessage());
            return null;
        }
    }
} 