package nn.optimisation.interfaces;

//~--- non-JDK imports --------------------------------------------------------

import nn.common.Matrix;

/**
 * Created with IntelliJ IDEA.
 * User: Chidi
 * Date: 08/06/13
 * Time: 20:42
 * To change this template use File | Settings | File Templates.
 */
public interface Function {
    public double calcError(Matrix input, Matrix target);

    public void backPropagate(Matrix input, Matrix deltas);

    public double[] getAllWeights();

    public void setWeights(double[] weights);
}
