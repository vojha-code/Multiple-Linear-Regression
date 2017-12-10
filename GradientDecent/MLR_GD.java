/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testruns;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author vojha
 */
public class MLR_GD {

    // data variables
    private double[][] X;
    private double[] Y;
    private double[][] mat;
    private int row;
    private int col;
    private int examples;
    private int features;
    private double[] w;

    /**
     * Function or the leaner regression equations implementation
     *
     * @param x D-dimensional inputs
     * @param w (D+1)-dimensional weight vector
     * @return predicted output
     */
    private double prediction(double[] x, double[] w) {
        double pred = 0.0;
        for (int i = 0; i < w.length; i++) {
            pred = pred + w[i] * x[i];
        }
        return pred;
    }

    /**
     *
     * @return weight vector corresponding to features
     */
    private double[] initialweights() {
        System.out.println("Weights:" + features);
        double[] w = new double[features];
        for (int i = 0; i < features; i++) {
            w[i] = 0.0;//Math.random();
        }
        return w;
    }

    /**
     *
     * @param y target output of the i-the sample
     * @param p predicted output of the i-the sample
     * @param x vector of the input sample
     * @return gradient corresponding to input samples
     */
    private double[] compute_gradient(double y, double p, double[] x) {
        double grad[] = new double[features];
        for (int i = 0; i < features; i++) {
            //grad[i] = (y - p) * p * (1 - p) * x[i];
            double e = (y - p);// error of i-th sample
            grad[i] = (x[i]) * e;
        }
        return grad;
    }

    /**
     * 
     * @param eta learning rate
     * @return 
     */
    private double updateWeights(double eta) {
        double[] P = new double[examples];//holds predictions per example--dimension N 
        double[][] g = new double[examples][features];//holds gradient per example per feature
        for (int i = 0; i < examples; i++) {
            double[] x = X[i];//ith vector of inputs + bias
            double y = Y[i];//ith target 
            double p = prediction(x, w);//Calcluate predictions for example i
            P[i] = p;//target
            g[i] = compute_gradient(y, p, x);//computing gradient vector on ith examples
        }

        //Compute average gradient per feature
        double[] ag = new double[features];//holds average gradient per feature
        for (int i = 0; i < examples; i++) {
            for (int j = 0; j < features; j++) {
                if (i == 0) {
                    ag[j] = 0.0; //initializing
                }
                ag[j] = ag[j] + g[i][j];
            }
        }
        
        //System.out.println("Printing gradient sum -> mean - eta*gradient:\n");
        for (int j = 0; j < features; j++) {
            //System.out.print(ag[j]+ " -> ");
            ag[j] = ag[j] / (double)examples;//mean  gradient per weight/feature
            //System.out.print(ag[j] +" -> ");
            ag[j] = ag[j]*eta;//multiplusing gradients with learning rate
            //System.out.println(ag[j]);
        }
        
        
        //update weights
        //System.out.println("Printing weights:\n");
        for (int j = 0; j < features; j++) {
            w[j] = w[j] - ag[j];
            //System.out.println(w[j]);
        }

        for (int i = 0; i < examples; i++) {
            P[i] = prediction(X[i], w);//Calcluate predictions
        }
        return cost(Y, P);
    }

    /**
     *
     * @param y target outputs column
     * @param p predicted outputs column of the model
     * @return Mean Squared Error
     */
    private double cost(double[] y, double[] p) {
        double error = 0.0;
        for (int i = 0; i < examples; i++) {
            error = error + (y[i] - p[i]) * y[i] - p[i];
        }
        return error * (1.0 / (2.0 * examples));//MSE
    }

    /**
     * Arranging inputs and outputs
     */
    private void organizedata() {
        examples = row;
        features = col - 1;
        //Add a bais term
        features = features + 1;

        X = new double[examples][features];
        Y = new double[examples];
        for (int i = 0; i < examples; i++) {
            //System.arraycopy(mat[i], 0, X[i], 0, features);
            System.arraycopy(mat[i], 0, X[i], 0, features - 1);//when adding bais
            X[i][features - 1] = 1.0;//Adding a bis term
            Y[i] = mat[i][col - 1];
        }

    }

    /**
     * mean normalization feature scaling method
     */
    private double[][] meannormalizedata(double[][] X) {
        int feature = features-1;
        //computing range of the features
        System.out.println("\nRange:\n");
        double[] min = new double[feature];
        double[] max = new double[feature];
        double[] range = new double[feature];
        for (int j = 0; j < feature; j++) {
            min[j] = X[0][j];
            max[j] = X[0][j];
        }

        for (int i = 1; i < examples; i++) {
            for (int j = 0; j < feature; j++) {
                if (X[i][j] < min[j]) {
                    min[j] = X[i][j];
                }
                if (X[i][j] > max[j]) {
                    max[j] = X[i][j];
                }
            }
        }

        for (int j = 0; j < feature; j++) {
            range[j] = max[j] - min[j];
            System.out.printf("  %.3f", range[j]);
        }

        //computing mean of the features
        System.out.println("\nMean:\n");
        double[] sum = new double[feature];
        for (int i = 0; i < examples; i++) {
            for (int j = 0; j < feature; j++) {
                sum[j] += X[i][j];
            }
        }

        double[] mean = new double[feature];
        for (int j = 0; j < feature; j++) {
            mean[j] = sum[j] / examples;
            System.out.printf("  %.3f", mean[j]);
        }

        //subtracting thye featues by the mean and deviding by the reange 
        System.out.println("\nNormalize data:\n");
        for (int i = 0; i < examples; i++) {
            for (int j = 0; j < feature; j++) {
                X[i][j] = X[i][j] - mean[j];
                X[i][j] = X[i][j] / range[j];
                if (X[i][j] < 0) {
                    //System.out.printf("  %.3f", mat[i][j]);
                } else {
                    //System.out.printf("   %.3f", mat[i][j]);
                }
            }
            //System.out.println();
        }
        return X;
    }

    //Normalizing data value
    public double normalize(double x, double dataLow, double dataHigh, double normalizedLow, double normalizedHigh) {
        return ((x - dataLow) / (dataHigh - dataLow)) * (normalizedHigh - normalizedLow) + normalizedLow;
    }

    private double[] minmaxnormalization(double[] Y, double low, double high) {
        //computing range of the features
        System.out.println("\nRange:\n");
        double min = Y[0];
        double max = Y[0];

        for (int i = 1; i < examples; i++) {
            if (Y[i] < min) {
                min = Y[i];
            }
            if (Y[i] > max) {
                max = Y[i];
            }
        }
        for (int i = 0; i < examples; i++) {
            Y[i] = normalize(Y[i], min, max, low, high);
        }
        
        //Check normalization correctness
//        min = Y[0];
//        max = Y[0];
//        for (int i = 1; i < examples; i++) {
//            if (Y[i] < min) {
//                min = Y[i];
//            }
//            if (Y[i] > max) {
//                max = Y[i];
//            }
//        }
//        System.out.println("Min: "+min);
//        System.out.println("Min: "+max);
        return Y;
    }

    /**
     *
     */
    private void gradient_decent() {
        //find inputs and outputs
        organizedata();
        X = meannormalizedata(X);
        Y = minmaxnormalization(Y,-1.0,1.0);
        printdata();

        //initializing weight vector
        w = initialweights();
        int maxiteration = 10;
        double eta = 0.0005;//learning rate
        for (int i = 0; i < maxiteration; i++) {
            System.out.println(i + " error: " + updateWeights(eta));
        }

        System.out.println("Weights are:");
        for (int i = 0; i < features; i++) {
            System.out.printf(" %.5f", w[i]);
        }
        System.out.println();

    }

    /**
     * CSV file reader code
     */
    private void readData() {
        String fileName = "Advertising.csv";//read training data from this file
        System.out.println("Reading data file: " + fileName);
        mat = readDataFromFile(fileName);
        row = mat.length;
        col = mat[0].length;
        System.out.println("Compleated.");
    }

    /**
     *
     * @param fileName
     * @return data matrix of input and outputs column
     */
    private double[][] readDataFromFile(String fileName) {
        int rowlength = 0;
        int collength = 0;
        double[][] data = null;
        String absolutePath = fileName;
        try {
            FileReader fin = new FileReader(absolutePath);//readt output Train to make ensemble
            BufferedReader br = new BufferedReader(fin);
            String line;
            String[] tokens;
            rowlength = 0;
            if ((line = br.readLine()) != null) {
                tokens = line.split(",");
                collength = tokens.length;
            }
            while ((line = br.readLine()) != null) {
                rowlength++;
            }
            rowlength++;//count the last row

            System.out.println("    The file has " + rowlength + " samples");
            System.out.println("    Each sample has a leangth " + collength);

            data = new double[rowlength][collength];

            br.close();
            fin.close();

            FileReader fin1 = new FileReader(absolutePath);//readt output Train to make ensemble
            BufferedReader br1 = new BufferedReader(fin1);

            System.out.println("Data:");
            for (int i = 0; i < rowlength; i++) {
                line = br1.readLine();
                tokens = line.split(",");
                for (int j = 0; j < collength; j++) {
                    data[i][j] = Double.parseDouble(tokens[j]);
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("Errot in data reading" + e);
        }
        return data;
    }

    public static void main(String[] args) {
        MLR_GD mlrgd = new MLR_GD();
        mlrgd.readData();
        mlrgd.gradient_decent();
    }

    private void printdata() {
        System.out.println("Examples:" + examples);
        System.out.println("Features:" + features);
        System.out.println("\nTraining data:\n");
        for (int i = 0; i < examples; i++) {
            for (int j = 0; j < features; j++) {
                if (X[i][j] < 0) {
                    System.out.printf("  %.3f", X[i][j]);
                } else {
                    System.out.printf("   %.3f", X[i][j]);
                }
            }
            if (Y[i] < 0) {
                System.out.printf("  %.3f", Y[i]);
            } else {
                System.out.printf("   %.3f", Y[i]);
            }
            System.out.println();
        }
    }
}
