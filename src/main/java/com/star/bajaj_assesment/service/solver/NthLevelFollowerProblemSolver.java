package com.star.bajaj_assesment.service.solver;

import com.star.bajaj_assesment.model.domain.NthLevelResult;
import com.star.bajaj_assesment.model.domain.Problem;
import com.star.bajaj_assesment.model.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class NthLevelFollowerProblemSolver implements ProblemSolver {

    @Value("${app.user.regNo}")
    private String regNo;

    @Override
    public Object solve(Problem problem) {
        if (!problem.isNthLevelFollowerProblem()) {
            throw new IllegalArgumentException("Not an nth level follower problem");
        }

        log.info("Solving nth level follower problem for user {} at level {}", 
                problem.getFindId(), problem.getN());
                
        List<User> users = problem.getUsers();
        int targetUserId = problem.getFindId();
        int targetLevel = problem.getN();
        
        // Build adjacency list for the follower graph
        Map<Integer, List<Integer>> adjacencyList = buildAdjacencyList(users);
        
        // Find users at the target level using BFS
        List<Integer> nthLevelFollowers = findNthLevelFollowers(
                adjacencyList, targetUserId, targetLevel);
        
        log.info("Found {} followers at level {} for user {}", 
                nthLevelFollowers.size(), targetLevel, targetUserId);
                
        return new NthLevelResult(nthLevelFollowers, regNo);
    }
    
    private Map<Integer, List<Integer>> buildAdjacencyList(List<User> users) {
        Map<Integer, List<Integer>> adjacencyList = new HashMap<>();
        
        // Initialize adjacency list for all users
        for (User user : users) {
            adjacencyList.put(user.getId(), new ArrayList<>());
        }
        
        // Populate each user's followers (reverse the "follows" relationship)
        for (User user : users) {
            if (user.getFollows() != null) {
                for (Integer followedId : user.getFollows()) {
                    // Add the current user as a follower of the followed user
                    adjacencyList.get(followedId).add(user.getId());
                }
            }
        }
        
        return adjacencyList;
    }
    
    private List<Integer> findNthLevelFollowers(
            Map<Integer, List<Integer>> adjacencyList, 
            int startUserId, 
            int targetLevel) {
        
        Queue<Integer> queue = new LinkedList<>();
        Map<Integer, Integer> levelMap = new HashMap<>();
        Set<Integer> visited = new HashSet<>();
        
        // Start BFS from the target user
        queue.add(startUserId);
        levelMap.put(startUserId, 0);
        visited.add(startUserId);
        
        // BFS traversal
        while (!queue.isEmpty()) {
            int currentUserId = queue.poll();
            int currentLevel = levelMap.get(currentUserId);
            
            // Skip if we've gone past the target level
            if (currentLevel > targetLevel) {
                continue;
            }
            
            // Process neighbors (followers)
            for (int followerId : adjacencyList.getOrDefault(currentUserId, Collections.emptyList())) {
                if (!visited.contains(followerId)) {
                    queue.add(followerId);
                    levelMap.put(followerId, currentLevel + 1);
                    visited.add(followerId);
                }
            }
        }
        
        // Collect all users at the target level
        List<Integer> result = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : levelMap.entrySet()) {
            if (entry.getValue() == targetLevel) {
                result.add(entry.getKey());
            }
        }
        
        Collections.sort(result);
        return result;
    }
} 