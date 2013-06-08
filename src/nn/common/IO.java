
/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
 */
package nn.common;

//~--- JDK imports ------------------------------------------------------------

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Utility class for performing IO operations
 * @author chidimuorah
 */
public class IO {

    /**
     * Read inputs from a file into a Matrix data set
     * @param filename filename
     * @return input data
     */
    public static Matrix readInputAsDouble(String filename) {
        ArrayList<double[]> inputs = new ArrayList<double[]>();
        String[]            temp;

        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));

            try {
                String line = reader.readLine();

                while (line != null) {
                    temp = line.split(",");
                    inputs.add(parseDouble(temp));
                    line = reader.readLine();
                }
            } catch (IOException ex) {
                Logger.getLogger(IO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(IO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return createMatrix(inputs);
    }

    /**
     * Method to read input file as an 2D array of integers
     * @param filename filename
     * @return inputs
     */
    public static int[][] readInputAsInt(String filename) throws FileNotFoundException, IOException {
        ArrayList<int[]> inputs = new ArrayList<int[]>();
        String[]         temp;
        BufferedReader   reader = new BufferedReader(new FileReader(filename));
        String           line   = reader.readLine();

        while (line != null) {
            temp = line.split(",");
            inputs.add(parseInt(temp));
            line = reader.readLine();
        }

        return createIntMatrix(inputs);
    }

    public static void writeOutput(String filename, ArrayList<Double> data) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filename));

            for (Double error : data) {
                writer.write(error + " ");
            }

            writer.close();
        } catch (IOException ex) {
            Logger.getLogger(IO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void writeOutput(String filename, double[] input) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filename));

            for (int i = 0; i < input.length; i++) {
                writer.write(input[i] + "\n");
            }

            writer.close();
        } catch (IOException ex) {
            Logger.getLogger(IO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Method to write array to file
     * @param filename filename
     * @param output data to write
     */
    public static void writeOutput(String filename, double[][] output) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
            Matrix         m      = new Matrix(output);

            writer.write(m.toString());
            writer.close();
        } catch (IOException ex) {
            Logger.getLogger(IO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Generalised parseDouble method for arrays of Strings
     * @param input input
     * @return parsed input
     */
    private static double[] parseDouble(String[] input) {
        double[] result = new double[input.length];

        for (int i = 0; i < result.length; i++) {
            result[i] = Double.parseDouble(input[i]);
        }

        return result;
    }

    /**
     * Utility method for parsing a String of inputs as an integer
     * @param input input
     * @return parsed inputs
     */
    private static int[] parseInt(String[] input) {
        int[] result = new int[input.length];

        for (int i = 0; i < result.length; i++) {
            result[i] = Integer.parseInt(input[i]);
        }

        return result;
    }

    /**
     * Utility method to create a Matrix from an ArrayList of arrays
     * @param inputs inputs
     * @return Matrix containing the inputs
     */
    private static Matrix createMatrix(ArrayList<double[]> inputs) {
        double[][] result = new double[inputs.size()][inputs.get(0).length];
        double[]   entry;

        for (int j = 0; j < inputs.size(); j++) {
            entry = inputs.get(j);
            System.arraycopy(entry, 0, result[j], 0, entry.length);
        }

        return new Matrix(result);
    }

    /**
     * Utility method to create a 2D array from an ArrayList of arrays
     * @param inputs inputs
     * @return 2D array containing the inputs
     */
    private static int[][] createIntMatrix(ArrayList<int[]> inputs) {
        int[][] result = new int[inputs.size()][inputs.get(0).length];
        int[]   entry;

        for (int j = 0; j < inputs.size(); j++) {
            entry = inputs.get(j);
            System.arraycopy(entry, 0, result[j], 0, entry.length);
        }

        return result;
    }
}
