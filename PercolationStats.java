/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private int numOfTrials;
    private double threshold;
    private double[] trialResults;
    private double mean;
    private double stddev;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        // array to hold results of all the trials
        trialResults = new double[trials];
        numOfTrials = trials;

        for (int i = 0; i < trials; i++) {
            // after every trial, we create a new percolation object
            Percolation perc = new Percolation(n);
            // keep opening new sites until percolation occurs and store this threshold
            for (double numOfSitesOpened = 1; numOfSitesOpened <= (n * n); numOfSitesOpened++) {
                perc.open(StdRandom.uniform(1, n + 1), StdRandom.uniform(1, n + 1));
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
        int sizeOfGrid = StdIn.readInt();
        int numOfTrials = StdIn.readInt();

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
