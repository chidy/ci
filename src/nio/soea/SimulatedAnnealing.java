package nio.soea;

//~--- non-JDK imports --------------------------------------------------------

import problems.discrete.CombinatorialProblem;

//~--- JDK imports ------------------------------------------------------------

import java.util.List;
import java.util.Random;


public class SimulatedAnnealing {
    private boolean[]            bestSolution;
    private int                  maxIterations;
    private CombinatorialProblem problem;
    private Double               T, α, Δ, δ;

    public SimulatedAnnealing(CombinatorialProblem problem) {
        this(problem, 500);
    }

    public SimulatedAnnealing(CombinatorialProblem problem, int maxIterations) {
        this.problem       = problem;
        this.maxIterations = maxIterations;
        this.δ             = 0.01;
    }

    public void optimise() {
        bestSolution = problem.randomSolution();
        initT();
        initα();

        List<boolean[]> neighbours = problem.getNeighbours(bestSolution);
        Random          r          = new Random();
        int             i          = 0;
        boolean[]       y;

        while ((i < maxIterations)) {
            y = neighbours.get(r.nextInt(neighbours.size()));

            if (problem.evaluate(bestSolution) >= problem.evaluate(y)) {
                bestSolution = y;
            } else {

                // accept the solution with a certain probability
                double prob = Math.exp(-(problem.evaluate(y) - problem.evaluate(bestSolution)) / T);

                if (prob > 0.5) {
                    bestSolution = y;
                }
            }

            T = α * T;
            i++;
        }
    }

    private void initT() {
        boolean[] y = getWorstNeighbour();

        Δ = problem.evaluate(y) - problem.evaluate(bestSolution);
        T = Δ / Math.log(2);
    }

    private boolean[] getWorstNeighbour() {
        List<boolean[]> neighbours = problem.getNeighbours(bestSolution);
        boolean[]       y          = neighbours.get(0);

        // get the worst neighbour
        for (boolean[] neighbour : neighbours) {
            if (problem.evaluate(neighbour) > problem.evaluate(y)) {
                y = neighbour;
            }
        }

        return y;
    }

    private void initα() {
        α = Math.pow((Math.log(2) / Math.log(1 / δ)), (1 / maxIterations));
    }

    public double bestSolutionValue() {
        return problem.evaluate(bestSolution);
    }

    public boolean[] getBestSolution() {
        return bestSolution;
    }
}
