package problems.discrete;

//~--- JDK imports ------------------------------------------------------------

import java.util.ArrayList;
import java.util.List;

public class SubsetSum extends AbstractSubsetProblem {
    private Number[] problem;

    public SubsetSum(Number[] problem) {
        this.problem = problem;
    }



    @Override
    public Integer evaluate(boolean[] aSolution) {
        int sum = 0;

        for (int i = 0; i < aSolution.length; i++) {
            if (aSolution[i]) {
                sum += problem[i].intValue();
            }
        }

        return Math.abs(sum);
    }

    @Override
    public List<Number> decode(boolean[] solution) {
        List<Number> result = new ArrayList<Number>();

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
