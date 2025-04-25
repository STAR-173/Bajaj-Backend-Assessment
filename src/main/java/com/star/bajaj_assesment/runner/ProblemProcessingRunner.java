package com.star.bajaj_assesment.runner;

import com.star.bajaj_assesment.service.ProblemProcessingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProblemProcessingRunner implements CommandLineRunner {

    private final ProblemProcessingService problemProcessingService;

    @Override
    public void run(String... args) {
        log.info("Starting problem processing");
        boolean success = problemProcessingService.processProblem();
        
        if (success) {
            log.info("Problem processed and result submitted successfully");
        } else {
            log.error("Failed to process problem or submit result");
        }
    }
} 