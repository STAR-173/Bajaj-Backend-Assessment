package com.star.bajaj_assesment.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InitialRequest {
    private String name;
    private String regNo;
    private String email;
} 