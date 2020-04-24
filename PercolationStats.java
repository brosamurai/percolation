/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

import java.util.InputMismatchException;

public class PercolationStats {

    private final int numOfTrials;
    private double threshold;
    private final double[] trialResults;
    private double mean;
    private double stddev;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            IllegalArgumentException ex = new IllegalArgumentException(
                    "input must be greater than 0");
            throw ex;
        }

        // array to hold results of all the trials
        trialResults = new double[trials];
        numOfTrials = trials;

        for (int i = 0; i < trials; i++) {
            // after every trial, we create a new percolation object
            Percolation perc = new Percolation(n);
            int randomRow = StdRandom.uniform(1, n + 1);
            int randomCol = StdRandom.uniform(1, n + 1);
            // keep opening new sites until percolation occurs and store this threshold
            for (double numOfSitesOpened = 1; numOfSitesOpened <= (n * n); numOfSitesOpened++) {
                // ensure that our random row and col corrsepond to a blocked site
                while (perc.isOpen(randomRow, randomCol)) {
                    randomRow = StdRandom.uniform(1, n + 1);
                    randomCol = StdRandom.uniform(1, n + 1);
                }
                // open the blocked site
                perc.open(randomRow, randomCol);
                // if the system percolates now, calculate the threshold
                if (perc.percolates()) {
                    threshold = numOfSitesOpened / (n * n);
                    break;
                }
            }
            // Store results (threshold) of trial
            trialResults[i] = threshold;
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        mean = StdStats.mean(trialResults);
        return mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        stddev = StdStats.stddev(trialResults);
        return stddev;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        double confidenceLo = mean - ((1.96 * stddev) / Math.sqrt(numOfTrials));
        return confidenceLo;
    }

    public double confidenceHi() {
        double confidenceHi = mean + ((1.96 * stddev) / Math.sqrt(numOfTrials));
        return confidenceHi;
    }

    // test client
    public static void main(String[] args) {
        int sizeOfGrid = 0;
        int numOfTrials = 0;
        try {
            sizeOfGrid = StdIn.readInt();
            numOfTrials = StdIn.readInt();
            if (sizeOfGrid <= 0 || numOfTrials <= 0) {
                IllegalArgumentException expection = new IllegalArgumentException(
                        "Inputs must be greater than zero!");
                throw expection;
            }
        }
        catch (InputMismatchException expection) {
            System.out.println("Only integers are allowed as inputs!");
            return;
        }

        PercolationStats perc = new PercolationStats(sizeOfGrid, numOfTrials);
        double myMean = perc.mean();
        double myStddev = perc.stddev();
        double myConfidenceHi = perc.confidenceHi();
        double myConfidenceLo = perc.confidenceLo();

        System.out.println("mean = " + myMean);
        System.out.println("stddev = " + myStddev);
        System.out.println("95% confidence interval = [" + myConfidenceLo + ", "
                                   + myConfidenceHi + "]");
    }
}
