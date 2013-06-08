package problems.discrete;

//~--- JDK imports ------------------------------------------------------------

import java.util.List;
import java.util.Set;

public interface CombinatorialProblem {
    public List<boolean[]> getNeighbours(boolean[] solution);

    public List<boolean[]> getNeighbours(boolean[] solution, Set<Integer> tabuList);

    public Number evaluate(boolean[] solution);

    public <S> S decode(boolean[] solution);

    public boolean[] randomSolution();
}
