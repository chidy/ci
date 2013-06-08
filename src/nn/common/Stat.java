
/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
 */
package nn.common;

//~--- JDK imports ------------------------------------------------------------

import java.util.ArrayList;

/**
 * Class that implements basic statistical methods
 * @author chidimuorah
 */
public class Stat {

    /**
     * Given a matrix x, works out the variance of the columns
     * @param x x(the input)
     * @return a matrix where each column represents
     * the corresponding column variance of the input matrix
     */
    public static Matrix var(Matrix x) {
        double temp;
        Matrix mean   = mean(x);
        Matrix result = new Matrix(x.toArray());

        // compute (x - x_bar)^2
        for (int i = 0; i < x.columnLength(); i++) {
            for (int j = 0; j < x.rowLength(); j++) {
                temp = Math.pow(x.get(j, i) - mean.get(0, i), 2);
                result.set(j, i, temp);
            }
        }

        return mean(result);
    }

    /**
     * Standard deviation of the matrix x
     * Assumes individual columns as population
     * @param x x(input)
     * @return standard deviation of x
     */
    public static Matrix std(Matrix x) {
        Matrix result = var(x);

        for (int i = 0; i < result.columnLength(); i++) {
            result.set(0, i, Math.sqrt(result.get(0, i)));
        }

        return result;
    }

    /**
     * normalises x to zero mean and unit variance
     * @param x x(the data)
     * @return normalised version of x
     */
    public static Matrix normalise(Matrix x) {
        Matrix result = new Matrix(x.toArray());
        Matrix mu     = Stat.mean(x);
        Matrix std    = Stat.std(x);
        double val;

        for (int i = 0; i < x.columnLength(); i++) {
            for (int j = 0; j < x.rowLength(); j++) {
                val = (result.get(j, i) - mu.get(0, i)) / std.get(0, i);
                result.set(j, i, val);
            }
        }

        return result;
    }

    /**
     * Arithmetic Mean of x
     * assumes that each individual column is a population
     * @param x x
     * @return average
     */
    public static Matrix mean(Matrix x) {
        Matrix result = sum(x);
        double avg;

        for (int i = 0; i < result.length(); i++) {
            avg = result.get(0, i) / x.rowLength();
            result.set(0, i, avg);
        }

        return result;
    }

    /**
     * Method to calculate mean of an <code>ArrayList</code>
     * @param x x
     * @return mean of x
     */
    public static double mean(ArrayList<Double> x) {
        double sum = 0;

        for (Double n : x) {
            sum += n;
        }

        return sum / x.size();
    }

    /**
     * unbiased mean of a matrix
     * uses the number of elements-1 instead of n
     * @param x x
     * @return unbiased mean of x
     */
    public static Matrix unbiasedMean(Matrix x) {
        Matrix result = sum(x);
        double avg;

        for (int i = 0; i < result.length(); i++) {
            avg = result.get(0, i) / (x.rowLength() - 1);
            result.set(0, i, avg);
        }

        return result;
    }

    /**
     * summation of the columns of a vector
     * @param x x
     * @return sum of the columns
     */
    public static Matrix sum(Matrix x) {
        double sum = 0;

        // if it is a row vector
        if (x.rowLength() == 1) {
            Matrix result = Matrix.zeros(1, 1);

            for (int i = 0; i < x.columnLength(); i++) {
                sum += x.get(0, i);
            }

            result.set(0, 0, sum);

            return result;
        } else {
            int        columns = x.columnLength();
            Matrix     column;
            double[][] result = new double[1][columns];

            for (int j = 0; j < columns; j++) {
                column       = x.getColumn(j);
                sum          = sumColumn(column);
                result[0][j] = sum;
            }

            return new Matrix(result);
        }
    }

    private static double sumColumn(Matrix x) {
        if (x.size()[1] != 1) {
            throw new IllegalArgumentException("Input must be a column vector");
        }

        double result = 0.0;

        for (int i = 0; i < x.length(); i++) {
            result += x.get(i, 0);
        }

        return result;
    }

    /**
     * sums up an an array
     * @param input input
     * @return sum of the input
     */
    public static double sum(double[] input) {
        double sum = 0;

        for (int i = 0; i < input.length; i++) {
            sum += input[i];
        }

        return sum;
    }

    /**
     * calculates the average of an array
     * @param x x
     * @return the average of x
     */
    public static double mean(double[] x) {
        double sum = 0;

        for (int i = 0; i < x.length; i++) {
            sum += x[i];
        }

        return sum / x.length;
    }

    /**
     * calculates the average of an array
     * @param x x
     * @return the average of x
     */
    public static double mean(Double[] x) {
        double sum = 0;

        for (int i = 0; i < x.length; i++) {
            sum += x[i];
        }

        return sum / x.length;
    }

    public static void main(String[] args) {
        double[][] d = {
            { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 9 }
        };
        Matrix     D = new Matrix(d);

        System.out.println(D);
        System.out.println(mean(D));
    }
}
