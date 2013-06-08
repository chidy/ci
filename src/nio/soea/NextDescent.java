package nio.soea;

//~--- non-JDK imports --------------------------------------------------------

import problems.discrete.CombinatorialProblem;

//~--- JDK imports ------------------------------------------------------------

import java.util.List;
import java.util.Random;

public class NextDescent {
    private boolean[]            bestSolution;
    private int                  maxIterations;
    private CombinatorialProblem problem;

    public NextDescent(CombinatorialProblem problem) {
        this(problem, 500);
    }

    public NextDescent(CombinatorialProblem problem, int maxIterations) {
        this.problem       = problem;
        this.maxIterations = maxIterations;
    }

    public void optimise() {
        bestSolution = problem.randomSolution();
        List<boolean[]> neighbours   = problem.getNeighbours(bestSolution);
        Random          r            = new Random();
        int             i            = 0;
        boolean[]       y;
        boolean         converged = false;

        while ((i < maxIterations) &&!converged) {
            y = neighbours.get(r.nextInt(neighbours.size()));

            if (problem.evaluate(bestSolution) > problem.evaluate(y)) {
                bestSolution = y;
            } else {
                converged = !converged;
            }

            i++;
        }
    }

    public double bestSolutionValue() {
        return problem.evaluate(bestSolution);
    }

    public boolean[] getBestSolution() {
        return bestSolution;
    }
}
