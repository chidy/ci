package problems.discrete;

//~--- JDK imports ------------------------------------------------------------

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Author: Chidi Muorah
 * Date: 08/06/2013
 * Time: 17:03
 */
public class SetCover<T> extends AbstractSubsetProblem {
    private T[] problem;

    public SetCover(T[] problem) {
        this.problem = problem;
    }

    @Override
    public Number evaluate(boolean[] solution) {
        return 0;
    }

    @Override
    public Set<T> decode(boolean[] solution) {
        Set<T> result = new HashSet<T>();

        for (int i = 0; i < solution.length; i++) {
            if (solution[i]) {
                result.add(problem[i]);
            }
        }

        return result;
    }

    @Override
    public boolean[] randomSolution() {
        return new boolean[0];    // To change body of implemented methods use File | Settings | File Templates.
    }
}
