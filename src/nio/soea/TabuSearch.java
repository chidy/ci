package nio.soea;

//~--- non-JDK imports --------------------------------------------------------

import problems.discrete.CombinatorialProblem;

//~--- JDK imports ------------------------------------------------------------

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class TabuSearch {
    private int                  tenure;
    private Set<Integer>         tabuList;
    private boolean[]            bestSolution;
    private int                  maxIterations;
    private CombinatorialProblem problem;

    public TabuSearch(CombinatorialProblem problem, int maxIterations, int tenure) {
        this.maxIterations = maxIterations;
        this.tenure        = tenure;
        this.problem       = problem;
        tabuList           = new HashSet<Integer>();
    }

    public void optimise() {
        bestSolution = problem.randomSolution();

        List<boolean[]> neighbours = problem.getNeighbours(bestSolution, tabuList);
        Random          r          = new Random();
        int             i          = 0;
        boolean[]       y;
        boolean         converged = false;

        while ((i < maxIterations) &&!converged) {
            y = getBestNeighbour(bestSolution, neighbours);

            if (problem.evaluate(bestSolution) < problem.evaluate(y)) {
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
