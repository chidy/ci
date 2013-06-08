
/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
 */
package nn.model.mlp;

//~--- non-JDK imports --------------------------------------------------------

import nn.model.Net;

import nn.util.Activation;
import nn.util.Matrix;
import nn.util.OutputFunction;

/**
 *
 * @author chidimuorah
 */
public class RBFNet extends Network {
    private double[] centres;
    private double   sigma;

    public RBFNet(int nin, int nhid, int nout, double sigma) {
        super(Net.RBF, nin, nhid, nout);
        setHiddenFn(Activation.GAUSSIAN);
        setOutFn(Activation.LINEAR);
        centres    = new double[nhid];
        this.sigma = sigma;
    }

    public double[] getCentres() {
        return centres;
    }

    public void setCentres(double[] centres) {
        this.centres = centres;
    }

    protected double[] calculateActivation(double[] x, boolean isHidden) {
        double[] y = new double[x.length];

        if (!isHidden) {
            y = x;
        } else {
            for (int i = 0; i < x.length; i++) {
                y[i] = OutputFunction.gaussian(x[i] + 1, centres[i], sigma);
            }
        }

        adjustWeightsIH(y);

        return y;
    }

    private void adjustWeightsIH(double[] y) {
        Matrix weights = Matrix.zeros(getNin(), y.length);

        for (int j = 0; j < getNin(); j++) {
            for (int i = 0; i < y.length; i++) {
                weights.set(j, i, y[i]);
            }
        }

        setWeightIH(weights);
    }

    @Override
    public Matrix feedForward(Matrix input) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public double calcError(Matrix input, Matrix target) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
