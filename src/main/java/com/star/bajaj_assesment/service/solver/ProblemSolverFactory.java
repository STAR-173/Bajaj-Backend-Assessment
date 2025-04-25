package com.star.bajaj_assesment.service.solver;

import com.star.bajaj_assesment.model.domain.Problem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProblemSolverFactory {

    private final MutualFollowerProblemSolver mutualFollowerProblemSolver;
    private final NthLevelFollowerProblemSolver nthLevelFollowerProblemSolver;

    public ProblemSolver getSolver(Problem problem) {
        if (problem.isMutualFollowerProblem()) {
            return mutualFollowerProblemSolver;
        } else if (problem.isNthLevelFollowerProblem()) {
            return nthLevelFollowerProblemSolver;
        } else {
            throw new IllegalArgumentException("Unknown problem type");
        }
    }
}