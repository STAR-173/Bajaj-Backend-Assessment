package com.star.bajaj_assesment.model.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MutualFollowerResult {
    /**
     * Pairs of users who follow each other, represented as [min_id, max_id] pairs.
     * Each array contains exactly 2 elements, with the smaller ID first.
     * Example: [[1, 2], [3, 4]]
     */
    @JsonIgnore
    private List<int[]> pairs;
    
    /**
     * The registration number to be included in the webhook response.
     */
    private String regNo;
    
    /**
     * The outcome field for the webhook response.
     * This contains the pairs of mutual followers.
     */
    private List<int[]> outcome;
    
    /**
     * Constructor that automatically sets the outcome to the pairs.
     */
    public MutualFollowerResult(List<int[]> pairs, String regNo) {
        this.pairs = pairs;
        this.regNo = regNo;
        this.outcome = pairs;
    }
} 