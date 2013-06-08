package nn.optimisation.interfaces;

import nn.util.Matrix;

/**
 * Created with IntelliJ IDEA.
 * User: Chidi
 * Date: 08/06/13
 * Time: 20:42
 * To change this template use File | Settings | File Templates.
 */
public interface DifferentiableFunction extends Function{
    public Matrix calcJacobian(Matrix input, Matrix target);

}