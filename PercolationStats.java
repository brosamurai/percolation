/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private int threshold;
    private int[] trialResults;
    private double mean;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        // array to hold results of all the trials
        trialResults = new int[trials];

        for (int i = 0; i < trials; i++) {
            // after every trial, we create a new percolation object
            Percolation perc = new Percolation(n);
            // keep opening new sites until percolation occurs and store this threshold
            for (int numOfSitesOpened = 1; numOfSitesOpened <= n; numOfSitesOpened++) {
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
        return 1;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return 1;
    }

    public double confidenceHi() {
        return 1;
    }

    // test client
    public static void main(String[] args) {
        for (int i = 0; i <= 50; i++) {
            System.out.println(StdRandom.uniform(1, 11));
        }
    }
}
