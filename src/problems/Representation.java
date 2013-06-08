package problems;

/**
 * Created with IntelliJ IDEA.
 * User: chidimuorah
 * Date: 04/06/2013
 * Time: 20:29
 * To change this template use File | Settings | File Templates.
 */
public class Representation {
    private boolean[] solution;

    public Representation(int size) {
        this.solution = new boolean[size];
    }

    public boolean[] getSolution() {
        return solution;
    }
}
