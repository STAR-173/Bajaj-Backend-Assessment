package com.star.bajaj_assesment.model.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MutualFollowerResult {
    private List<int[]> pairs;
} 