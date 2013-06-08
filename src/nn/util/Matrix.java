
/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
 */
package nn.util;

//~--- JDK imports ------------------------------------------------------------

import java.io.Serializable;

import java.util.Arrays;
import java.util.Random;

/**
 * Class that implements Matrix and Matrix operations
 * @author chidimuorah
 */
public class Matrix implements Serializable {
    private final int  row;
    private final int  column;
    private double[][] data;

    /**
     * Constructor to create an instance of a <code>Matrix</code> object
     * @param data data
     */
    public Matrix(double[][] data) {
        row       = data.length;
        column    = data[0].length;
        this.data = new double[row][column];

        for (int i = 0; i < row; i++) {
            System.arraycopy(data[i], 0, this.data[i], 0, column);
        }
    }

    private Matrix(Matrix a) {
        this(a.data);
    }

    /**
     * Constructor to create an instance of a <code>Matrix</code> object
     * @param row number of rows
     * @param column number of columns
     */
    public Matrix(int row, int column) {
        this.row    = row;
        this.column = column;
        data        = new double[row][column];
    }

    /**
     * Generates a matrix of 0s
     * @param row row length
     * @param column column length
     * @return matrix of zeros
     */
    public static Matrix zeros(int row, int column) {
        Matrix x = new Matrix(row, column);

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                x.data[i][j] = 0;
            }
        }

        return x;
    }

    /**
     * Setter for an element in a matrix
     * @param m row
     * @param n column
     * @param val value
     */
    public void set(int m, int n, double val) {
        data[m][n] = val;
    }

    /**
     * Generates a matrix of 1s
     * @param row
     * @param column
     * @return matrix of ones
     */
    public static Matrix ones(int row, int column) {
        Matrix result = new Matrix(row, column);

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                result.data[i][j] = 1;
            }
        }

        return result;
    }

    /**
     * Generates a random matrix distributed
     * according to uniform distribution
     * @param row
     * @param column
     * @return
     */
    public static Matrix random(int row, int column) {
        Matrix result = new Matrix(row, column);

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                result.data[i][j] = Math.random();
            }
        }

        return result;
    }

    /**
     * method that gets a column of a matrix
     * @param n the column
     * @return column n of the matrix
     */
    public Matrix getColumn(int n) {
        double[][] aColumn = new double[data.length][1];

        for (int i = 0; i < data.length; i++) {
            aColumn[i][0] = data[i][n];
        }

        return new Matrix(aColumn);
    }

    /**
     * method that concatenates two matrices
     * @param b the matrix to concatenate
     * @return this concatenated with b
     */
    public Matrix concat(Matrix b) {
        if (b.rowLength() != rowLength()) {
            throw new RuntimeException("Matrices must have the same dimensions to be concatenated");
        }

        return new Matrix(concatAll(transpose().toArray(), b.transpose().toArray())).transpose();
    }

    /**
     * method that appends matrix b to the current matrix
     * @param b the appendant
     * @return b appended to the current matrix
     */
    public Matrix append(Matrix b) {
        return new Matrix(concatAll(toArray(), b.toArray()));
    }

    /**
     * concatenates arrays of arbitrary length
     * @param first first array
     * @param rest the rest of the arrays
     * @return first ++ ... ++ rest
     */
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

    /**
     * method that gets a row of the current matrix
     * @param n row to get
     * @return row n
     */
    public Matrix getRow(int n) {
        double[][] aRow = new double[1][data.length];

        aRow[0] = data[n];

        return new Matrix(aRow);
    }

    /**
     * method that gets rows from m to n of the current matrix
     * ensures that m > n
     * @param m from row
     * @param n to row
     * @return m to n rows
     */
    public Matrix getRows(int m, int n) {
        if (m > n) {
            throw new IllegalArgumentException("m:" + m + " must be less that n:" + n);
        } else if (m == n) {
            return getRow(m);
        } else {
            double[][] someRows = new double[n - m + 1][data.length];

            for (int i = 0; i <= n - m; i++) {
                someRows[i] = getRowAsArray(m + i);
            }

            return new Matrix(someRows);
        }
    }

    /**
     * method that gets a row as an array
     * @param n row to get
     * @return array containing row n from left to right
     */
    public double[] getRowAsArray(int n) {
        return data[n];
    }

    /**
     * method that gets columns from m to n of the matrix
     * @param m from column
     * @param n to column
     * @return columns m to n
     */
    public Matrix getColumns(int m, int n) {
        if (m > n) {
            throw new IllegalArgumentException("arg1 must be less that arg2");
        } else if (m == n) {
            return getColumn(m);
        } else {
            double[][] someColumns = new double[data.length][n - m + 1];

            for (int i = 0; i < data[0].length; i++) {
                for (int j = 0; j <= n - m; j++) {
                    someColumns[i][j] = data[i][j + m];
                }
            }

            return new Matrix(someColumns);
        }
    }

    /**
     * method that generates a random matrix
     * distributed according to the normal distribution
     * @param row number or rows
     * @param column number of columns
     * @return random Gaussian matrix
     */
    public static Matrix randomGaussian(int row, int column) {
        Matrix result = new Matrix(row, column);
        Random r      = new Random();

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                result.data[i][j] = r.nextGaussian();
            }
        }

        return result;
    }

    /**
     * returns an identity matrix
     * @param dimension the dimension
     * @return dimension X dimension identity matrix
     */
    public static Matrix identity(int dimension) {
        Matrix E = new Matrix(dimension, dimension);

        for (int i = 0; i < dimension; i++) {
            E.data[i][i] = 1;
        }

        return E;
    }

    /**
     * getter method for an element of the matrix
     * @param m row
     * @param n column
     * @return element at m,n
     */
    public double get(int m, int n) {
        return data[m][n];
    }

    /**
     * method that swaps two rows
     * @param i row to swap
     * @param j row to swap
     */
    private void swap(int i, int j) {
        double[] temp = data[i];

        data[i] = data[j];
        data[j] = temp;
    }

    /**
     * Matrix transposition
     * @return transposed version of the matrix
     */
    public Matrix transpose() {
        Matrix result = new Matrix(column, row);

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                result.data[j][i] = this.data[i][j];
            }
        }

        return result;
    }

    /**
     * Matrix addition
     * @param y y
     * @return this + y
     */
    public Matrix plus(Matrix y) {
        Matrix x = this;

        if ((y.row != x.row) || (y.column != x.column)) {
            throw new RuntimeException("Illegal matrix dimensions." + row + " X " + column + " + " + y.row + " X "
                                       + y.column);
        }

        Matrix result = new Matrix(row, column);

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                result.data[i][j] = x.data[i][j] + y.data[i][j];
            }
        }

        return result;
    }

    /**
     * Matrix subtraction
     * @param y
     * @return
     */
    public Matrix minus(Matrix y) {
        Matrix x = this;

        if ((y.row != x.row) || (y.column != x.column)) {
            throw new RuntimeException("Illegal matrix dimensions." + row + " X " + column + " - " + y.row + " X "
                                       + y.column);
        }

        Matrix result = new Matrix(row, column);

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                result.data[i][j] = x.data[i][j] - y.data[i][j];
            }
        }

        return result;
    }

    /**
     * Matrix equality
     * @param y y
     * @param ε tolerance
     * @return true if both matrices are equal within the tolerance
     */
    public boolean equals(Matrix y, double ε) {
        Matrix x = this;

        if ((y.row != x.row) || (y.column != x.column)) {
            return false;
        }

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                if (Math.abs(x.data[i][j] - y.data[i][j]) > ε) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * length of the matrix
     * @return length
     */
    public int length() {
        int rowLength    = data.length;
        int columnLength = data[0].length;
        int length       = (rowLength > columnLength)
                           ? rowLength
                           : columnLength;

        return length;
    }

    /**
     * dimension of the matrix
     * @return size
     */
    public int[] size() {
        int   rowLength    = data.length;
        int   columnLength = data[0].length;
        int[] size         = { rowLength, columnLength };

        return size;
    }

    /**
     * matrix multiplication
     * @param y
     * @return this times y
     */
    public Matrix times(Matrix y) {
        Matrix x = this;

        if (x.column != y.row) {
            throw new RuntimeException("Illegal matrix dimensions.");
        }

        Matrix result = new Matrix(x.row, y.column);

        for (int i = 0; i < result.row; i++) {
            for (int j = 0; j < result.column; j++) {
                for (int k = 0; k < x.column; k++) {
                    result.data[i][j] += (x.data[i][k] * y.data[k][j]);
                }
            }
        }

        return result;
    }

    /**
     * element wise division
     * @param d the divisor
     * @return matrix with each element divided by d
     */
    public Matrix divide(double d) {
        return times(1 / d);
    }

    /**
     * element wise multiplication
     * @param x the coefficient of multiplication
     * @return matrix with each element multiplied by x
     */
    public Matrix times(double x) {
        Matrix result = new Matrix(row, column);

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                result.set(i, j, data[i][j] * x);
            }
        }

        return result;
    }

    /**
     * element wise hyperbolic tangent
     * @return matrix with element-wise tanh
     */
    public Matrix tanh() {
        Matrix result = new Matrix(row, column);

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                result.set(i, j, Math.tanh(data[i][j]));
            }
        }

        return result;
    }

    /**
     * element wise power
     * @param power the exponent
     * @return matrix with each element raised to power of the exponent
     */
    public Matrix power(int power) {
        Matrix result = new Matrix(row, column);

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                result.set(i, j, Math.pow(data[i][j], power));
            }
        }

        return result;
    }

    /**
     * element wise reciprocal
     * @return matrix with each element reciprocated
     */
    public Matrix reciprocal() {
        Matrix result = new Matrix(row, column);

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                result.set(i, j, 1 / data[i][j]);
            }
        }

        return result;
    }

    /**
     * element wise multiplication
     * @param y y
     * @return this element wise multiplied by y
     */
    public Matrix elemwiseTimes(Matrix y) {
        Matrix x = this;

        if ((y.row != x.row) || (y.column != x.column)) {
            throw new RuntimeException("Illegal matrix dimensions.");
        }

        Matrix result = new Matrix(row, column);

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                result.data[i][j] = x.data[i][j] * y.data[i][j];
            }
        }

        return result;
    }

    /**
     * element wise addition
     * @param y y
     * @return this plus y element wise
     */
    public Matrix elemwisePlus(Matrix y) {
        Matrix x = this;

        if ((y.row != x.row) || (y.column != x.column)) {
            throw new RuntimeException("Illegal matrix dimensions.");
        }

        Matrix result = new Matrix(row, column);

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                result.data[i][j] = x.data[i][j] + y.data[i][j];
            }
        }

        return result;
    }

    /**
     * element wise subtraction
     * @param y y
     * @return (this - y) element wise
     */
    public Matrix elemwiseMinus(Matrix y) {
        Matrix x = this;

        if ((y.row != x.row) || (y.column != x.column)) {
            throw new RuntimeException("Illegal matrix dimensions.");
        }

        Matrix result = new Matrix(row, column);

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                result.data[i][j] = x.data[i][j] * y.data[i][j];
            }
        }

        return result;
    }

    /**
     * element wise subtraction
     * @param x x
     * @return (this - x)
     */
    public Matrix minus(double x) {
        return plus(-x);
    }

    /**
     * element wise exponentiation to the negative element
     * @return e^elem where elem is the element of the matrix
     */
    public Matrix eToNegativeElement() {
        Matrix result = new Matrix(row, column);

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                result.set(i, j, Math.pow(Math.E, -data[i][j]));
            }
        }

        return result;
    }

    /**
     * element wise addition
     * @param x x
     * @return (this + x) element wise
     */
    public Matrix plus(double x) {
        Matrix result = new Matrix(row, column);

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                result.set(i, j, data[i][j] + x);
            }
        }

        return result;
    }

    /**
     * element wise natural logarithm
     * @return ln of each element of the matrix
     */
    public Matrix log() {
        Matrix result = new Matrix(row, column);

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                result.set(i, j, Math.log(data[i][j]));
            }
        }

        return result;
    }

    /**
     * Gaussian elimination division
     * @param rhs rhs
     * @return this/rhs
     */
    public Matrix divide(Matrix rhs) {
        if ((row != column) || (rhs.row != column) || (rhs.column != 1)) {
            throw new RuntimeException("Illegal matrix dimensions.");
        }

        // create copies of the data
        Matrix a = new Matrix(this);
        Matrix b = new Matrix(rhs);

        // Gaussian elimination with partial pivoting
        for (int i = 0; i < column; i++) {

            // find pivot row and swap
            int max = i;

            for (int j = i + 1; j < column; j++) {
                if (Math.abs(a.data[j][i]) > Math.abs(a.data[max][i])) {
                    max = j;
                }
            }

            a.swap(i, max);
            b.swap(i, max);

            // singular
            if (Math.abs(a.data[i][i] - 0.0) < 0.00000000000001) {
                throw new RuntimeException("Matrix is singular. Try using SVD");
            }

            // pivot within b
            for (int j = i + 1; j < column; j++) {
                b.data[j][0] -= b.data[i][0] * a.data[j][i] / a.data[i][i];
            }

            // pivot within x
            for (int j = i + 1; j < column; j++) {
                double m = a.data[j][i] / a.data[i][i];

                for (int k = i + 1; k < column; k++) {
                    a.data[j][k] -= a.data[i][k] * m;
                }

                a.data[j][i] = 0.0;
            }
        }

        // back substitution
        Matrix x = new Matrix(column, 1);

        for (int j = column - 1; j >= 0; j--) {
            double t = 0.0;

            for (int k = j + 1; k < column; k++) {
                t += a.data[j][k] * x.data[k][0];
            }

            x.data[j][0] = (b.data[j][0] - t) / a.data[j][j];
        }

        return x;
    }

    public double[][] toArray() {
        return data;
    }

    public int rowLength() {
        return row;
    }

    public int columnLength() {
        return column;
    }

    /**
     * method that gets columns from matrix
     * @param inDim
     * @return
     */
    public Matrix getColumns(int[] inDim) {
        Matrix result = null;

        for (int i = 0; i < inDim.length - 1; i++) {
            result = getColumn(inDim[i]).concat(getColumn(inDim[i + 1]));
        }

        return (inDim.length > 1)
               ? result
               : getColumn(inDim[0]);
    }

    /**
     * toObjectArray method
     * @return
     */
    public Double[][] toObjectArray() {
        Double[][] result = new Double[data.length][data[0].length];

        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < result[0].length; j++) {
                result[i][j] = data[i][j];
            }
        }

        return result;
    }

    /**
     * converts matrix to vector
     * @return
     */
    public double[] vectorise() {
        double[] result = getColumn(0).transpose().toArray()[0];

        if (column > 1) {
            for (int i = 1; i < column; i++) {
                result = concatAll(result, getColumn(i).transpose().toArray()[0]);
            }
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

    /**
     * toString method
     * @return string representation of the object
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                sb.append(data[i][j]).append(" ");
            }

            sb.append("\n");
        }

        return sb.toString();
    }

    public static void main(String[] args) {
        double[][] d = {
            { 100, 2, 3 }, { 4, 5, 6 }, { 9, 1, 3 }
        };

        /*
         *  Matrix D = new Matrix(d);
         *
         * System.out.println(D);
         *
         * Matrix x = Matrix.random(5, 5);
         *
         * System.out.println(x);
         * x.swap(1, 2);
         * System.out.println(x);
         *
         * Matrix B = x.transpose();
         *
         * System.out.println(B);
         *
         * Matrix result = Matrix.identity(5);
         *
         * System.out.println(result);
         * System.out.println(B);
         * System.out.println(x);
         *
         * // shouldn't be equal since AB != BA in general
         * System.out.println(x.times(B).eq(B.times(x)));
         * System.out.println();
         *
         * Matrix b = Matrix.random(5, 1);
         *
         * System.out.println(b);
         *
         * Matrix x = x.solve(b);
         *
         * System.out.println(x);
         * System.out.println(x.times(x));
         */
        Matrix D = new Matrix(d);

        System.out.println(D);
        System.out.println("------------------------------");

        double[] res = D.vectorise();

        System.out.println(D.append(D.getRow(0)));

//      double[] v = D.vectorise();
//      double[][] y = new double[1][v.length];
//      y[0] = v;
//      System.out.println(new Matrix(y));
        // System.out.println(D.getColumns(0, 0).pseudoInverse());

        // System.out.println(D.elemwiseTimes(2));
        // System.out.println(Math.log(100));
        // System.out.println(D.getColumns(1, 1));
    }
}
