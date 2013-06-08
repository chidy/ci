/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nn.util;

/**
 *
 * @author chidimuorah
 */
public class CostFunction {

    public static double sumSquaredError(double[] output, double[] target) {
        double error = 0;

        for (int i = 0; i < output.length; i++) {
            error += Math.pow(target[i] - output[i], 2);
        }
        return 0.5 * error;
    }

    public static double meanSquaredError(double[] output, double[] target) {
        return sumSquaredError(output, target) / output.length;
    }

    public static double rootMeanSquaredError(double[] output, double[] target) {
        return Math.sqrt(meanSquaredError(output, target));
    }
   
    private static double min(double[] x) {
        double min = Double.POSITIVE_INFINITY;
        for (int i = 0; i < x.length; i++) {
            min = min > x[i] ? x[i] : min;
        }
        return min;
    }

    private static double max(double[] x) {
        double max = Double.NEGATIVE_INFINITY;
        for (int i = 0; i < x.length; i++) {
            max = max < x[i] ? x[i] : max;
        }
        return max;
    }
}
