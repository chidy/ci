package problems.discrete;

//~--- JDK imports ------------------------------------------------------------

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * Author: Chidi Muorah
 * Date: 08/06/2013
 * Time: 17:38
 */
public abstract class AbstractSubsetProblem implements CombinatorialProblem {
    @Override
    public List<boolean[]> getNeighbours(boolean[] solution) {
        List<boolean[]> neighbours = new ArrayList<boolean[]>();

        for (int i = 0; i < solution.length; i++) {
            boolean[] neighbour = Arrays.copyOf(solution, solution.length);

            neighbour[i] = !neighbour[i];
            neighbours.add(neighbour);
        }

        return neighbours;
    }

    @Override
    public List<boolean[]> getNeighbours(boolean[] solution, Set<Integer> tabuList) {
        List<boolean[]> neighbours = new ArrayList<boolean[]>();

        for (int i = 0; i < solution.length; i++) {
            boolean[] neighbour = Arrays.copyOf(solution, solution.length);

            if (!tabuList.contains(i)) {
                neighbour[i] = !neighbour[i];
                neighbours.add(neighbour);
            }
        }

        return neighbours;
    }

    @Override
    public abstract Number evaluate(boolean[] solution);

    @Override
    public abstract <S> S decode(boolean[] solution);

    @Override
    public abstract boolean[] randomSolution();
}
