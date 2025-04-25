package com.star.bajaj_assesment.model.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NthLevelResult {
    /**
     * Users that are exactly N steps away from the starting user.
     * This is used internally.
     */
    @JsonIgnore
    private List<Integer> users;
    
    /**
     * The registration number to be included in the webhook response.
     */
    private String regNo;
    
    /**
     * The outcome field for the webhook response.
     * This contains the list of user IDs that are exactly N steps away.
     */
    private List<Integer> outcome;
    
    /**
     * Constructor that automatically sets the outcome to the users.
     */
    public NthLevelResult(List<Integer> users, String regNo) {
        this.users = users;
        this.regNo = regNo;
        this.outcome = users;
    }
} 