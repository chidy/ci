package nio.soea;

//~--- non-JDK imports --------------------------------------------------------

import problems.discrete.CombinatorialProblem;

//~--- JDK imports ------------------------------------------------------------

import java.util.List;
import java.util.Random;

public class SteepestDescent {
    private CombinatorialProblem problem;
    private int                  maxIterations;
    private boolean[]            bestSolution;

    public SteepestDescent(CombinatorialProblem problem) {
        this(problem, 500);
    }

    public SteepestDescent(CombinatorialProblem problem, int maxIterations) {
        this.problem       = problem;
        this.maxIterations = maxIterations;
    }

    public void optimise() {
        bestSolution = problem.randomSolution();

        List<boolean[]> neighbours = problem.getNeighbours(bestSolution);
        Random          r          = new Random();
        int             i          = 0;
        boolean[]       y;
        boolean         converged = false;

        while ((i < maxIterations) &&!converged) {
            y = getBestNeighbour(bestSolution, neighbours);

            if (problem.evaluate(bestSolution) > problem.evaluate(y)) {
                bestSolution = y;
            } else {
                converged = !converged;
            }

            i++;
        }
    }

    private boolean[] getBestNeighbour(boolean[] x, List<boolean[]> neighbours) {
        boolean[] best = x;

        for (boolean[] y : neighbours) {
            if (problem.evaluate(best) < problem.evaluate(y)) {
                best = y;
            }
        }

        return best;
    }

    public double bestSolutionValue() {
        return problem.evaluate(bestSolution);
    }

    public boolean[] getBestSolution() {
        return bestSolution;
    }
}
