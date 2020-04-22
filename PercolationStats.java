/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;

public class PercolationStats {

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        for (int i = 1; i <= trials; i++) {
            Percolation perc = new Percolation(n);
            perc.open(StdRandom.uniform(1, n + 1), StdRandom.uniform(1, n + 1));
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return 1;
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
