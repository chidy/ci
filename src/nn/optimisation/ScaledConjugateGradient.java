package nn.optimisation;

//~--- non-JDK imports --------------------------------------------------------

import nn.model.mlp.Network;

import nn.optimisation.interfaces.DifferentiableFunction;

import nn.util.Matrix;

//~--- JDK imports ------------------------------------------------------------

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: chidimuorah
 * Date: 03/06/2013
 * Time: 23:46
 * To change this template use File | Settings | File Templates.
 */
public class ScaledConjugateGradient {
    public final static double     β_MIN = Math.exp(-15);
    public final static double     β_MAX = Math.exp(100);
    private DifferentiableFunction net;
    private Matrix                 input, target;
    private int                    maxIters;
    private double[]               weights;
    private Matrix                 Δold, Δnew;
    private int                    nSuccess;
    private Matrix                 δw;
    private boolean                success;
    private double                 errorOld, errorNew;
    private double                 α, β, μ, κ, δ, σ, θ, σ_0, γ;
    private int                    nparams;
    private ArrayList<Double>      errorLog;
    private Matrix                 w;
    private Matrix                 wPlus, gradPlus, wNew;
    private double                 Δ;
    private double                 error;
    private int                    iter;

    /**
     * Constructor for the <code>ScaledConjugateGradient</code> class
     * @param net a differentiable function
     */
    public ScaledConjugateGradient(DifferentiableFunction net) {
        this.net = net;
        errorLog = new ArrayList<Double>();
    }

    private void init() {
        weights  = net.getAllWeights();
        errorOld = net.calcError(input, target);
        σ_0      = Math.exp(-4);
        error    = errorOld;
        Δnew     = net.calcJacobian(input, target);
        Δold     = Δnew;
        δw       = Δnew.times(-1);
        success  = true;
        β        = 1.0;
        nparams  = weights.length;
    }

    /**
     * main optimisation loop
     */
    private void optimise() {
        double[][] wt = new double[1][weights.length];

        wt[0] = weights;
        w     = new Matrix(wt);

        for (iter = 0; iter < maxIters; iter++) {
            if (success) {
                μ = δw.times(Δnew.transpose()).get(0, 0);

                if (μ >= 0) {
                    δw = Δnew.times(-1);
                    μ  = δw.times(Δnew.transpose()).get(0, 0);
                }

                κ     = δw.times(δw.transpose()).get(0, 0);
                σ     = σ_0 / Math.sqrt(κ);
                wPlus = w.plus(δw.times(σ));
                net.setWeights(wPlus.vectorise());
                gradPlus = net.calcJacobian(input, target);
                θ        = (δw.times((gradPlus.transpose().minus(Δnew.transpose())))).divide(σ).get(0, 0);
            }

            // increase effective curvature and evaluate step size
            increaseEffectiveCurvature();

            // calculate comparison ratio
            calculateComparisonRatio();

            if (success) {
                errorOld = errorNew;
                Δold     = Δnew;
                net.setWeights(w.vectorise());
                Δnew = net.calcJacobian(input, target);

                // terminate if error is very close to zero
                if (Math.abs(Δnew.times(Δnew.transpose()).get(0, 0) - 0) < 0.000001) {
                    return;
                }
            }

            // heuristic to make hessian inverse positive definite
            if (Δ < 0.25) {
                β = Math.min(4 * β, β_MAX);
            }

            if (Δ > 0.75) {
                β = Math.max(0.5 * β, β_MIN);
            }

            updateDirection();
            weights = w.vectorise();
            net.setWeights(weights);
            errorLog.add(error);
        }
    }

    /**
     * method to calculate comparison ratio
     */
    private void calculateComparisonRatio() {
        wNew = w.plus(δw.times(α));
        net.setWeights(wNew.vectorise());
        errorNew = net.calcError(input, target);
        Δ        = 2 * (errorNew - errorOld) / (α * μ);

        if (Δ >= 0) {
            success = true;
            nSuccess++;
            w     = wNew;
            error = errorNew;
        } else {
            success = false;
            error   = errorOld;
        }
    }

    /**
     * method to increase effective curvature
     * for approximation of the inverse Hessian
     */
    private void increaseEffectiveCurvature() {
        δ = θ + β * κ;

        if (δ <= 0) {
            δ = β * κ;
            β -= θ / κ;
        }

        α = -μ / δ;
    }

    private void updateDirection() {

        // update search direction using Polak-Ribiere formula
        if (nSuccess == nparams) {
            δw       = Δnew.times(-1);
            nSuccess = 0;
        } else {
            if (success) {
                γ  = (Δold.minus(Δnew)).times(Δnew.transpose().divide(μ)).get(0, 0);
                δw = (δw.times(γ)).minus(Δnew);
            }
        }
    }

    /**
     * Feed forward method for the function
     * to be used after optimisation
     * @param input
     * @return output from the function
     */
    public Matrix feedForward(Matrix input) {
        return getNetwork().feedForward(input);
    }

    public Network getNetwork() {
        return (Network) net;
    }

    /**
     * Method for training the network
     * @param input the inputs
     * @param target targets
     * @param maxIters the maximum number of iterations
     */
    public void train(Matrix input, Matrix target, int maxIters) {
        this.input    = input;
        this.target   = target;
        this.maxIters = maxIters;
        init();
        optimise();
    }

    /**
     * Getter method for error log
     * @return errorLog
     */
    public ArrayList<Double> getErrorLog() {
        return errorLog;
    }
}
