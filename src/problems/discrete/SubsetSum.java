package problems.discrete;

//~--- JDK imports ------------------------------------------------------------

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class SubsetSum implements CombinatorialProblem {
    private int[] problem;

    public SubsetSum(int[] problem) {
        this.problem = problem;
    }

    @Override
    public List<boolean[]> getNeighbours(boolean[] solution) {
        List<boolean[]> neighbours = new ArrayList<boolean[]>();

        for (int i = 0; i < solution.length; i++) {
            boolean[] neighbour;

            neighbour    = Arrays.copyOf(solution, 0);
            neighbour[i] = !neighbour[i];
            neighbours.add(neighbour);
        }

        return neighbours;
    }

    @Override
    public List<boolean[]> getNeighbours(boolean[] solution, Set<Integer> tabuList) {
        List<boolean[]> neighbours = new ArrayList<boolean[]>();

        for (int i = 0; i < solution.length; i++) {
            boolean[] neighbour;

            neighbour = Arrays.copyOf(solution, 0);

            if (!tabuList.contains(i)) {
                neighbour[i] = !neighbour[i];
                neighbours.add(neighbour);
            }
        }

        return neighbours;
    }

    @Override
    public double evaluate(boolean[] aSolution) {
        int sum = 0;

        for (int i = 0; i < aSolution.length; i++) {
            if (aSolution[i]) {
                sum += problem[i];
            }
        }

        return sum;
    }

    @Override
    public List<Integer> decode(boolean[] solution) {
        List<Integer> result = new ArrayList<Integer>();

        for (int i = 0; i < solution.length; i++) {
            if (solution[i]) {
                result.add(problem[i]);
            }
        }

        return result;
    }

    @Override
    public boolean[] randomSolution() {
        boolean[] randomSolution = new boolean[problem.length];

        for (int i = 0; i < randomSolution.length; i++) {
            if (Math.random() > 0.5) {
                randomSolution[i] = true;
            }
        }

        return randomSolution;
    }
}
