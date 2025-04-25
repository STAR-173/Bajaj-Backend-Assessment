package com.star.bajaj_assesment.model.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Problem {
    private List<User> users;
    private Integer n;
    private Integer findId;
    
    /**
     * Determines if this is a mutual follower problem (Question 1).
     * Question 1 is assigned if the registration number ends with an odd digit.
     * In the API response, a mutual follower problem has users list but no n or findId.
     */
    public boolean isMutualFollowerProblem() {
        // For Question 1, we have users but no n or findId values
        if (users != null && n == null && findId == null) {
            return true;
        }
        return false;
    }
    
    /**
     * Determines if this is an nth level follower problem (Question 2).
     * Question 2 is assigned if the registration number ends with an even digit.
     * In the API response, an nth level follower problem has users, n, and findId.
     */
    public boolean isNthLevelFollowerProblem() {
        // For Question 2, we need to have users, n, and findId
        if (users != null && n != null && findId != null) {
            return true;
        }
        return false;
    }
} 