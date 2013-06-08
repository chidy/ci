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
        System.out.println("T* = " + T);
        System.out.println("α  = " + α);

        List<boolean[]> neighbours = problem.getNeighbours(bestSolution);
        Random          r          = new Random();
        int             i          = 0;
        boolean[]       y;

        while (i < maxIterations) {
            y = neighbours.get(r.nextInt(neighbours.size()));

            if (problem.evaluate(bestSolution).doubleValue() > problem.evaluate(y).doubleValue()) {
                bestSolution = y;
            } else {

                // accept the solution with a certain probability
                double prob = Math.exp(-(problem.evaluate(y).doubleValue()
                                         - problem.evaluate(bestSolution).doubleValue()) / T);

                if (prob >= 0.5) {
                    bestSolution = y;
                }
            }

            T = α * T;
            i++;
        }
    }

    private void initT() {
        boolean[] y = getWorstNeighbour();

        Δ = problem.evaluate(y).doubleValue() - problem.evaluate(bestSolution).doubleValue();
        System.out.println("Δ = " + Δ);
        T = Δ / Math.log(2);
    }

    private boolean[] getWorstNeighbour() {
        List<boolean[]> neighbours = problem.getNeighbours(bestSolution);
        boolean[]       y          = neighbours.get(0);

        // get the worst neighbour
        for (boolean[] neighbour : neighbours) {
            if (problem.evaluate(neighbour).doubleValue() > problem.evaluate(y).doubleValue()) {
                y = neighbour;
            }
        }

        return y;
    }

    private void initα() {
        α = Math.pow(Δ / (T * Math.log(1 / δ)), (1.0 / maxIterations));
    }

    public Number bestSolutionValue() {
        return problem.evaluate(bestSolution);
    }

    public boolean[] getBestSolution() {
        return bestSolution;
    }
}
