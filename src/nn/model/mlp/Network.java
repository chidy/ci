
/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
 */
package nn.model.mlp;

//~--- non-JDK imports --------------------------------------------------------

import nn.model.Net;

import nn.common.Activation;
import nn.common.Matrix;

//~--- JDK imports ------------------------------------------------------------

import java.io.Serializable;

/**
 *
 * @author Chidi
 */
public abstract class Network implements Serializable {
    private Matrix       biasIH, biasHO;
    private int          nin, nhidden, nout, nwts;
    private Net          type;
    private Matrix       weightIH, weightHO;
    protected double[]   output, input, hidden, deltaH, deltaO;
    protected Activation outFn;
    protected Activation hiddenFn;

    public Network(Net type, int nin, int nhidden, int nout) {
        this.type    = type;
        this.nin     = nin;
        this.nhidden = nhidden;
        this.nout    = nout;
        weightIH     = Matrix.randomGaussian(nin, nhidden);
        weightHO     = Matrix.randomGaussian(nhidden, nout);
        nwts         = (nin + 1) * nhidden + (nhidden + 1) * nout;
        biasIH       = Matrix.randomGaussian(1, nhidden).times(1 / Math.sqrt(nin + 1));
        biasHO       = Matrix.randomGaussian(1, nout).times(1 / Math.sqrt(nhidden + 1));
        output       = new double[nout];
        deltaO       = new double[nout];
        hidden       = new double[nhidden];
        deltaH       = new double[nhidden];
        input        = new double[nin];
    }

    public double[] getOutput() {
        return output;
    }

    public abstract Matrix feedForward(Matrix input);

    public abstract double calcError(Matrix input, Matrix target);

    public void setHidden(double[] hidden) {
        this.hidden = hidden;
    }

    public double[] getHidden() {
        return hidden;
    }

    public Matrix getBiasHO() {
        return biasHO;
    }

    public Matrix getBiasIH() {
        return biasIH;
    }

    public void setBiasHO(Matrix biasHO) {
        this.biasHO = biasHO;
    }

    public void setBiasIH(Matrix biasIH) {
        this.biasIH = biasIH;
    }

    public void setWeightHO(Matrix weightHO) {
        this.weightHO = weightHO;
    }

    public void setWeightIH(Matrix weightIH) {
        this.weightIH = weightIH;
    }

    public Matrix getWeightIH() {
        return weightIH;
    }

    public Matrix getWeightHO() {
        return weightHO;
    }

    public Net getType() {
        return type;
    }

    public int getNin() {
        return nin;
    }

    public int getNout() {
        return nout;
    }

    public int getNhidden() {
        return nhidden;
    }

    public int getNwts() {
        return nwts;
    }

    public void setNhidden(int nhidden) {
        this.nhidden = nhidden;
    }

    public void setNout(int nout) {
        this.nout = nout;
    }

    public void setNin(int nin) {
        this.nin = nin;
    }

    public void setOutFn(Activation outFn) {
        this.outFn = outFn;
    }

    public void setHiddenFn(Activation hiddenFn) {
        this.hiddenFn = hiddenFn;
    }

    public Activation getHiddenFn() {
        return hiddenFn;
    }

    public Activation getOutFn() {
        return outFn;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Network Type\t").append(type).append("\n");
        sb.append("Num. Inputs\t").append(nin).append("\n");
        sb.append("Num. of hidden units\t").append(nhidden).append("\n");
        sb.append("Num. of outputs\t").append(nout).append("\n");

        return sb.toString();
    }
}
