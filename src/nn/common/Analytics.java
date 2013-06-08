
/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
 */
package nn.common;

/**
 * Class for analysing performance of models
 * Compares the results against a random walk model
 * Also computes the mse, rmse and sse of the model
 * @author Chidi
 */
public class Analytics {
    private double[] output, target, rw;
    private double   iorw;
    private double   rmse;
    private double   mse;
    private double   sse;
    private double   rwRMSE;


    public Analytics(double[] output, double[] target) {
        this.output = output;
        this.target = target;
        rmse        = CostFunction.rootMeanSquaredError(output, target);
        sse         = CostFunction.sumSquaredError(output, target);
        mse         = CostFunction.meanSquaredError(output, target);
    }

    /**
     * Constructor for <code>Analytics</code> class
     * @param output output
     * @param target target
     * @param rw random walk prediction
     */
    public Analytics(double[] output, double[] target, double[] rw) {
        this(output, target);
        this.rw = rw;
        calcIORW();
    }

    /**
     * helper method for calculating the improvement over random walk
     */
    private void calcIORW() {
        rwRMSE = CostFunction.rootMeanSquaredError(rw, target);
        iorw   = (rwRMSE - rmse) / rwRMSE;
    }

    /**
     * getter method for improvement over random walk
     * @return iorw
     */
    public double getIorw() {
        return iorw;
    }

    /**
     * getter for mean square error
     * @return mse
     */
    public double getMse() {
        return mse;
    }

    /**
     * getter for root mean square error
     * @return rmse
     */
    public double getRmse() {
        return rmse;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("RMSE: ").append(rmse).append("\n");
        sb.append("MSE: ").append(mse).append("\n");

        // sb.append("SSE: ").append(sse).append("\n");
        sb.append("IORW: ").append(iorw).append("\n");

        return sb.toString();
    }
}
