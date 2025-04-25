package com.star.bajaj_assesment.service.solver;

import com.star.bajaj_assesment.model.domain.NthLevelResult;
import com.star.bajaj_assesment.model.domain.Problem;
import com.star.bajaj_assesment.model.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class NthLevelFollowerProblemSolverTest {

    private final NthLevelFollowerProblemSolver solver = new NthLevelFollowerProblemSolver();
    private final String TEST_REG_NO = "RA2211029010006";

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(solver, "regNo", TEST_REG_NO);
    }

    @Test
    void shouldFindFirstLevelFollowers() {
        // Given
        User user1 = new User(1, "User 1", List.of());
        User user2 = new User(2, "User 2", List.of(1));
        User user3 = new User(3, "User 3", List.of(1));
        User user4 = new User(4, "User 4", List.of(2));
        
        List<User> users = Arrays.asList(user1, user2, user3, user4);
        Problem problem = new Problem(users, 1, 1);
        
        // When
        NthLevelResult result = (NthLevelResult) solver.solve(problem);
        
        // Then
        assertNotNull(result);
        assertEquals(2, result.getUsers().size());
        assertTrue(result.getUsers().contains(2));
        assertTrue(result.getUsers().contains(3));
        
        // Check registration number and outcome
        assertEquals(TEST_REG_NO, result.getRegNo());
        assertEquals(result.getUsers(), result.getOutcome());
    }
    
    @Test
    void shouldFindSecondLevelFollowers() {
        // Given
        User user1 = new User(1, "User 1", List.of());
        User user2 = new User(2, "User 2", List.of(1));
        User user3 = new User(3, "User 3", List.of(1));
        User user4 = new User(4, "User 4", List.of(2));
        User user5 = new User(5, "User 5", List.of(3));
        
        List<User> users = Arrays.asList(user1, user2, user3, user4, user5);
        Problem problem = new Problem(users, 2, 1);
        
        // When
        NthLevelResult result = (NthLevelResult) solver.solve(problem);
        
        // Then
        assertNotNull(result);
        assertEquals(2, result.getUsers().size());
        assertTrue(result.getUsers().contains(4));
        assertTrue(result.getUsers().contains(5));
        
        // Check registration number and outcome
        assertEquals(TEST_REG_NO, result.getRegNo());
        assertEquals(result.getUsers(), result.getOutcome());
    }
    
    @Test
    void shouldHandleEmptyUserList() {
        // Given
        Problem problem = new Problem(List.of(), 1, 1);
        
        // When
        NthLevelResult result = (NthLevelResult) solver.solve(problem);
        
        // Then
        assertNotNull(result);
        assertTrue(result.getUsers().isEmpty());
        assertEquals(TEST_REG_NO, result.getRegNo());
        assertTrue(result.getOutcome().isEmpty());
    }
    
    @Test
    void shouldThrowExceptionForNonNthLevelFollowerProblem() {
        // Given
        Problem problem = new Problem(List.of(), null, null);
        
        // Then
        assertThrows(IllegalArgumentException.class, () -> solver.solve(problem));
    }
} 