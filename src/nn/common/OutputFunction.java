
/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
 */
package nn.common;

/**
 *
 * @author Chidi
 */
public class OutputFunction {

    /**
     * given a matrix x, computes the sigmoid of each element
     * such that each element (a) is now 1/(1 + e^-x)
     * @param x
     * @return Matrix of sigmoid applied to each element of x
     */
    public static Matrix sigmoid(Matrix x) {
        Matrix y = x.eToNegativeElement();

        y = y.plus(1);
        y = y.reciprocal();

        return y;
    }

    public static double sigmoid(double x) {
        return 1.0 / (1.0 + Math.exp(-x));
    }

    public static double[] softmax(double[] x) {
        x = normalise(x);

        double[] y   = new double[x.length];
        double   sum = sumExp(x);

        for (int i = 0; i < x.length; i++) {
            y[i] = Math.exp(x[i]) / sum;
        }

        return y;
    }

    public static double sigmoidDerivative(double sigmoid) {
        return 0.1 + sigmoid * (1 - sigmoid);
    }

    private static double sumExp(double[] x) {
        double sum = Math.exp(x[0]);

        for (int i = 1; i < x.length; i++) {
            sum += Math.exp(x[i]);
        }

        return sum;
    }

    public static double gaussian(double x, double mu, double sigma) {
        return Math.exp(-Math.pow(Math.abs(x - mu), 2) / (2 * sigma * sigma));
    }

    private static double[] normalise(double[] x) {
        double[] y = new double[x.length];

        for (int i = 0; i < x.length; i++) {
            y[i] = Math.min(x[i], Double.MAX_EXPONENT);
            y[i] = Math.max(x[i], Double.MIN_EXPONENT);

            if (y[i] == Double.MIN_EXPONENT) {
                y[i] = y[i] * 2;
            }
        }

        return y;
    }
}
