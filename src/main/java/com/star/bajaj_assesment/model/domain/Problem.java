package com.star.bajaj_assesment.model.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Problem {
    private List<User> users;
    private Integer n;
    private Integer findId;
    
    public boolean isMutualFollowerProblem() {
        return n == null && findId == null;
    }
    
    public boolean isNthLevelFollowerProblem() {
        return n != null && findId != null;
    }
} 