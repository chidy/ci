package nn.util;

/**
 * Created with IntelliJ IDEA.
 * User: chidimuorah
 * Date: 03/06/2013
 * Time: 23:37
 * To change this template use File | Settings | File Templates.
 */
public class Matrix {
    private double[][] data;

    public Matrix(double[][] data) {
        this.data = data;
    }

    public Matrix plus(Matrix y) {
        return null;
    }

    public Matrix minus(Matrix y) {
        return null;
    }

    public Matrix times(Matrix y) {
        return null;
    }

    public Matrix ln() {
        double[][] result = new double[data.length][data[0].length];

        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[0].length; j++) {
                result[i][j] = Math.log(data[i][j]);
            }
        }

        return new Matrix(result);
    }

    public Matrix log() {
        double[][] result = new double[data.length][data[0].length];

        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[0].length; j++) {
                result[i][j] = Math.log10(data[i][j]);
            }
        }

        return new Matrix(result);
    }

    public Matrix tanh() {
        double[][] result = new double[data.length][data[0].length];

        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[0].length; j++) {
                result[i][j] = Math.tanh(data[i][j]);
            }
        }

        return new Matrix(result);
    }
}
