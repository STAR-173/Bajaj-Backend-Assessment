package com.star.bajaj_assesment.service.solver;

import com.star.bajaj_assesment.model.domain.MutualFollowerResult;
import com.star.bajaj_assesment.model.domain.Problem;
import com.star.bajaj_assesment.model.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class MutualFollowerProblemSolver implements ProblemSolver {

    @Override
    public Object solve(Problem problem) {
        if (!problem.isMutualFollowerProblem()) {
            throw new IllegalArgumentException("Not a mutual follower problem");
        }

        log.info("Solving mutual follower problem");
        List<User> users = problem.getUsers();
        
        // Build follower relationship map for efficient lookup
        Map<Integer, Set<Integer>> followersMap = buildFollowersMap(users);
        
        // Find all pairs of users who follow each other
        List<int[]> mutualFollowerPairs = findMutualFollowers(followersMap);
        
        log.info("Found {} mutual follower pairs", mutualFollowerPairs.size());
        return new MutualFollowerResult(mutualFollowerPairs);
    }
    
    private Map<Integer, Set<Integer>> buildFollowersMap(List<User> users) {
        Map<Integer, Set<Integer>> followersMap = new HashMap<>();
        
        for (User user : users) {
            Integer userId = user.getId();
            List<Integer> followsList = user.getFollows();
            
            // Initialize the set if needed
            followersMap.putIfAbsent(userId, new HashSet<>());
            
            // Add each followed user
            if (followsList != null) {
                for (Integer followedId : followsList) {
                    followersMap.get(userId).add(followedId);
                }
            }
        }
        
        return followersMap;
    }
    
    private List<int[]> findMutualFollowers(Map<Integer, Set<Integer>> followersMap) {
        List<int[]> result = new ArrayList<>();
        Set<String> processedPairs = new HashSet<>();
        
        for (Map.Entry<Integer, Set<Integer>> entry : followersMap.entrySet()) {
            Integer userId = entry.getKey();
            Set<Integer> userFollows = entry.getValue();
            
            for (Integer followedId : userFollows) {
                // Skip if we've already processed this pair
                String pairKey = Math.min(userId, followedId) + "_" + Math.max(userId, followedId);
                if (processedPairs.contains(pairKey)) {
                    continue;
                }
                
                // Check if the followed user also follows the current user
                Set<Integer> followedUserFollows = followersMap.get(followedId);
                if (followedUserFollows != null && followedUserFollows.contains(userId)) {
                    // Add to result with lower ID first
                    int[] pair = userId < followedId
                            ? new int[]{userId, followedId}
                            : new int[]{followedId, userId};
                    
                    result.add(pair);
                    processedPairs.add(pairKey);
                }
            }
        }
        
        return result;
    }
}