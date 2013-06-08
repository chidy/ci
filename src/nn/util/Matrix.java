package nn.util;

//~--- JDK imports ------------------------------------------------------------

import java.io.Serializable;

import java.util.Arrays;
import java.util.Random;

public class Matrix implements Serializable {
    private static final double eps = 1e-5;
    private double[][]          data;
    private final int           row, column;

    public Matrix(double[][] data) {
        this.data = data;
        row       = data.length;
        column    = data[0].length;
    }

    private Matrix(Matrix a) {
        this(a.data);
    }

    public Matrix(int row, int column) {
        this.data   = new double[row][column];
        this.row    = row;
        this.column = column;
    }

    public double get(int m, int n) {
        return data[m][n];
    }

    public void set(int rowIndex, int columnIndex, double val) {
        data[rowIndex][columnIndex] = val;
    }

    public Matrix getColumn(int n) {
        double[][] aColumn = new double[data.length][1];

        for (int i = 0; i < data.length; i++) {
            aColumn[i][0] = data[i][n];
        }

        return new Matrix(aColumn);
    }

    public Matrix getRow(int m) {
        double[][] aRow = new double[1][data.length];

        aRow[0] = data[m];

        return new Matrix(aRow);
    }

    public double[] getRowAsArray(int m) {
        return data[m];
    }

    public Matrix concat(Matrix b) {
        if (b.rowLength() != row) {
            throw new IllegalArgumentException("Matrices must have matching row dimensions to be concatenated");
        }

        return new Matrix(concatAll(transpose().toArray(), b.transpose().toArray())).transpose();
    }

    public Matrix append(Matrix b) {
        return new Matrix(concatAll(toArray(), b.toArray()));
    }

    public static double[] concatAll(double[] first, double[]... rest) {
        int totalLength = first.length;

        for (double[] array : rest) {
            totalLength += array.length;
        }

        double[] result = Arrays.copyOf(first, totalLength);
        int      offset = first.length;

        for (double[] array : rest) {
            System.arraycopy(array, 0, result, offset, array.length);
            offset += array.length;
        }

        return result;
    }

    public static double[][] concatAll(double[][] first, double[][]... rest) {
        int totalLength = first.length;

        for (double[][] array : rest) {
            totalLength += array.length;
        }

        double[][] result = Arrays.copyOf(first, totalLength);
        int        offset = first.length;

        for (double[][] array : rest) {
            System.arraycopy(array, 0, result, offset, array.length);
            offset += array.length;
        }

        return result;
    }

    public Matrix transpose() {
        Matrix result = new Matrix(column, row);

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                result.data[j][i] = this.data[i][j];
            }
        }

        return result;
    }

    public Matrix plus(Matrix y) {
        if ((y.rowLength() != row) && (y.columnLength() != column)) {
            throw new IllegalArgumentException("Matrices must have equal row and length to subtract");
        }

        Matrix result = new Matrix(row, column);

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                result.set(i, j, data[i][j] + y.get(i, j));
            }
        }

        return result;
    }

    public Matrix plus(double x) {
        Matrix result = new Matrix(row, column);

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                result.data[i][j] = data[i][j] + x;
            }
        }

        return result;
    }

    public Matrix times(double x) {
        Matrix result = new Matrix(row, column);

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                result.data[i][j] = data[i][j] * x;
            }
        }

        return result;
    }

    public Matrix divide(double x) {
        return times(1.0 / x);
    }

    public Matrix minus(double x) {
        return plus(-x);
    }

    public Matrix minus(Matrix y) {
        if ((y.rowLength() != row) && (y.columnLength() != column)) {
            throw new IllegalArgumentException("Matrices must have equal row and length to subtract");
        }

        Matrix result = new Matrix(row, column);

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                result.set(i, j, data[i][j] - y.get(i, j));
            }
        }

        return result;
    }

    public Matrix times(Matrix y) {
        Matrix x = this;

        if (x.column != y.row) {
            throw new IllegalArgumentException("Illegal matrix dimensions for multiplication");
        }

        Matrix result = new Matrix(x.row, y.column);

        for (int i = 0; i < result.row; i++) {
            for (int j = 0; j < result.column; j++) {
                for (int k = 0; k < x.column; k++) {
                    result.data[i][j] += x.data[i][k] * y.data[k][j];
                }
            }
        }

        return result;
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

    public static Matrix rand(int m, int n) {
        double[][] result = new double[m][n];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                result[i][j] = Math.random();
            }
        }

        return new Matrix(result);
    }

    public static Matrix randn(int m, int n) {
        double[][] result = new double[m][n];
        Random     r      = new Random();

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                result[i][j] = r.nextGaussian();
            }
        }

        return new Matrix(result);
    }

    public static Matrix zeros(int row, int column) {
        Matrix x = new Matrix(row, column);

        x = initMatrixWithValue(x, 0);

        return x;
    }

    public static Matrix ones(int row, int column) {
        Matrix x = new Matrix(row, column);

        x = initMatrixWithValue(x, 1);

        return x;
    }

    private static Matrix initMatrixWithValue(Matrix x, double value) {
        for (int i = 0; i < x.row; i++) {
            for (int j = 0; j < x.column; j++) {
                x.data[i][j] = value;
            }
        }

        return x;
    }

    public int rowLength() {
        return row;
    }

    public int columnLength() {
        return column;
    }

    public double[][] toArray() {
        return data;
    }

    public double[] vectorise() {
        double[] result = getColumn(0).transpose().toArray()[0];

        if (column > 1) {
            for (int i = 1; i < column; i++) {
                result = concatAll(result, getColumn(i).transpose().toArray()[0]);
            }
        }

        return result;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                sb.append(data[i][j]).append(" ");
            }

            sb.append("\n");
        }

        return sb.toString();
    }
}
