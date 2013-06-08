package sample;

//~--- non-JDK imports --------------------------------------------------------

import nio.soea.NextDescent;
import nio.soea.SimulatedAnnealing;
import nio.soea.SteepestDescent;

import problems.discrete.CombinatorialProblem;
import problems.discrete.SubsetSum;

//~--- JDK imports ------------------------------------------------------------

import java.util.*;

/**
 * Author: Chidi Muorah
 * Date: 08/06/2013
 * Time: 18:05
 */
public class Experiment {
    public static Integer[] subsetSumGenerator(int size, int lowerLimit, int upperLimit) {
        Random    r       = new Random();
        Integer[] problem = new Integer[size];

        for (int i = 0; i < problem.length; i++) {
            problem[i] = lowerLimit + r.nextInt(upperLimit - lowerLimit);
        }

        return problem;
    }

    public static void main(String[] args) {
        int                  maxIters = 25000;
        CombinatorialProblem problem  = new SubsetSum(subsetSumGenerator(100, -50, 100));
        SimulatedAnnealing   saSolver = new SimulatedAnnealing(problem, maxIters);

        saSolver.optimise();

        List<Integer> bestSolution = problem.decode(saSolver.getBestSolution());

        System.out.println("Simulated Annealing: " + saSolver.bestSolutionValue());
        System.out.println(bestSolution);
        System.out.println("--------------------------------------------------");

        NextDescent ndSolver = new NextDescent(problem, maxIters);

        ndSolver.optimise();
        bestSolution = problem.decode(ndSolver.getBestSolution());
        System.out.println("Next Descent: " + ndSolver.bestSolutionValue());
        System.out.println(bestSolution);
        System.out.println("--------------------------------------------------");

        SteepestDescent sdSolver = new SteepestDescent(problem, maxIters);

        sdSolver.optimise();
        bestSolution = problem.decode(sdSolver.getBestSolution());
        System.out.println("Steepest Descent: " + sdSolver.bestSolutionValue());
        System.out.println(bestSolution);
    }
}
