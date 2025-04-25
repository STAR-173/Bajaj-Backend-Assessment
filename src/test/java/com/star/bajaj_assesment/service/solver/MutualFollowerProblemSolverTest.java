package com.star.bajaj_assesment.service.solver;

import com.star.bajaj_assesment.model.domain.MutualFollowerResult;
import com.star.bajaj_assesment.model.domain.Problem;
import com.star.bajaj_assesment.model.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MutualFollowerProblemSolverTest {

    private final MutualFollowerProblemSolver solver = new MutualFollowerProblemSolver();
    private final String TEST_REG_NO = "RA2211029010006";

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(solver, "regNo", TEST_REG_NO);
    }

    @Test
    void shouldFindMutualFollowers() {
        // Given
        User user1 = new User(1, "User 1", Arrays.asList(2, 3));
        User user2 = new User(2, "User 2", Arrays.asList(1, 3));
        User user3 = new User(3, "User 3", Arrays.asList(1));
        
        List<User> users = Arrays.asList(user1, user2, user3);
        Problem problem = new Problem(users, null, null);
        
        // When
        MutualFollowerResult result = (MutualFollowerResult) solver.solve(problem);
        
        // Then
        assertNotNull(result);
        assertEquals(2, result.getPairs().size());
        
        // Check that the pairs are [1, 2] and [1, 3]
        assertTrue(containsPair(result.getPairs(), 1, 2), "Should contain pair [1, 2]");
        assertTrue(containsPair(result.getPairs(), 1, 3), "Should contain pair [1, 3]");
        
        // Check registration number and outcome
        assertEquals(TEST_REG_NO, result.getRegNo());
        assertEquals(result.getPairs(), result.getOutcome());
    }
    
    private boolean containsPair(List<int[]> pairs, int a, int b) {
        for (int[] pair : pairs) {
            if (pair.length == 2 && pair[0] == a && pair[1] == b) {
                return true;
            }
        }
        return false;
    }
    
    @Test
    void shouldHandleEmptyUserList() {
        // Given
        Problem problem = new Problem(List.of(), null, null);
        
        // When
        MutualFollowerResult result = (MutualFollowerResult) solver.solve(problem);
        
        // Then
        assertNotNull(result);
        assertTrue(result.getPairs().isEmpty());
        assertEquals(TEST_REG_NO, result.getRegNo());
        assertTrue(result.getOutcome().isEmpty());
    }
    
    @Test
    void shouldThrowExceptionForNonMutualFollowerProblem() {
        // Given
        Problem problem = new Problem(List.of(), 1, 2);
        
        // Then
        assertThrows(IllegalArgumentException.class, () -> solver.solve(problem));
    }
} 