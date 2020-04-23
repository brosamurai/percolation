/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.Stopwatch;

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
                if (numOfSitesOpened > n && perc.percolates()) {
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
        Stopwatch timer = new Stopwatch();
        System.out.println("Elapsed Time 1: " + String.valueOf(timer.elapsedTime()));
        PercolationStats perc = new PercolationStats(sizeOfGrid, numOfTrials);
        System.out.println("Elapsed Time 2: " + String.valueOf(timer.elapsedTime()));
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
