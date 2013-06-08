
/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
 */
package nn.model.mlp;

//~--- non-JDK imports --------------------------------------------------------

import nn.model.Net;

import nn.optimisation.interfaces.DifferentiableFunction;

import nn.util.Activation;
import nn.util.Matrix;
import nn.util.Stat;

/**
 *
 * @author Chidi
 */
public class MultilayerPerceptron extends Network implements DifferentiableFunction {
    private Matrix hid;
    private Matrix gradWeightHO, gradWeightIH;
    private Matrix gradBiasIH, gradBiasHO;

    public MultilayerPerceptron(int nin, int nhid, int nout, Activation outputFunction) {
        super(Net.MLP, nin, nhid, nout);
        setHiddenFn(Activation.SIGMOID);
        setOutFn(outputFunction);
    }

    public Matrix feedForward(Matrix input) {
        Matrix bias = Matrix.ones(input.rowLength(), 1).times(getBiasIH());

        hid  = input.times(getWeightIH()).plus(bias);
        hid  = hid.tanh();
        bias = Matrix.ones(hid.rowLength(), 1).times(getBiasHO());

        Matrix out = hid.times(getWeightHO()).plus(bias);

        return out;
    }

    public double calcError(Matrix input, Matrix target) {
        Matrix out = feedForward(input);

        out = out.minus(target).power(2);

        double error = Stat.sum(Stat.sum(out)).get(0, 0);

        return 0.5 * error;
    }

    public Matrix calcJacobian(Matrix input, Matrix target) {
        Matrix out    = feedForward(input);
        Matrix deltas = out.minus(target);

        backPropagate(input, deltas);

        double[] g = Matrix.concatAll(gradWeightIH.vectorise(), gradBiasIH.vectorise(), gradWeightHO.vectorise(),
                                      gradBiasHO.vectorise());
        double[][] grad = new double[1][g.length];

        grad[0] = g;

        return new Matrix(grad);
    }

    public void backPropagate(Matrix input, Matrix deltas) {

        // eval second layer gradient
        gradWeightHO = hid.transpose().times(deltas);
        gradBiasHO   = Stat.sum(deltas);

        // back propagate
        Matrix deltaHidden = deltas.times(getWeightHO().transpose());

        deltaHidden = deltaHidden.elemwiseTimes(hid.elemwiseTimes(hid).times(-1).plus(1));

        // evaluate first layer gradients
        gradWeightIH = input.transpose().times(deltaHidden);
        gradBiasIH   = Matrix.ones(1, getBiasIH().length()).times(Stat.sum(deltaHidden).get(0, 0));
    }

    public double[] getAllWeights() {
        Matrix w1 = getWeightIH();
        Matrix b1 = getBiasIH();
        Matrix w2 = getWeightHO();
        Matrix b2 = getBiasHO();

        return Matrix.concatAll(w1.vectorise(), b1.vectorise(), w2.vectorise(), b2.vectorise());
    }

    public void setWeights(double[] weights) {
        if (weights.length != this.getNwts()) {
            throw new IllegalArgumentException("Invalid number of weights ");
        }

        int k = 0;

        // set weightsIH
        setWeightIH(convertToMatrix(weights, k, getNin(), getNhidden()));
        k = getNin() * getNhidden();

        // set the biasIH vector
        setBiasIH(convertToMatrix(weights, k, 1, getNhidden()));
        k += getNhidden();

        // set weightHO
        setWeightHO(convertToMatrix(weights, k, getNhidden(), getNout()));
        k = k + (getNhidden() * getNout());

        // setBiasHO
        setBiasHO(convertToMatrix(weights, k, 1, getNout()));
    }

    private Matrix convertToMatrix(double[] weights, int startIndex, int row, int column) {
        double[][] w1 = new double[row][column];

        for (int i = 0; i < w1[0].length; i++) {
            for (int j = 0; j < w1.length; j++) {
                w1[j][i] = weights[startIndex];
                startIndex++;
            }
        }

        return new Matrix(w1);
    }

    public Matrix getGradWeightIH() {
        return gradWeightIH;
    }

    public Matrix getGradWeightHO() {
        return gradWeightHO;
    }
}
