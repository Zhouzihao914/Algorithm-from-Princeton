/* *****************************************************************************
 *  Name: Zihao Zhou
 *  Date: 9.3.2019
 *  Description: None
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private double[] succTimes;
    private double mean;
    private double stddev;

    // perform trials independent experiments on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new java.lang.IllegalArgumentException("n should be positive integer");
        }

        succTimes = new double[trials];
        for (int i = 0; i < trials; i++) {
            Percolation p = new Percolation(n);
            while (!p.percolates()) {
                int row = StdRandom.uniform(1, n + 1);
                int col = StdRandom.uniform(1, n + 1);
                p.open(row, col);
            }
            succTimes[i] = 1.0 * p.numberOfOpenSites() / (n * n);

        }
        mean = StdStats.mean(succTimes);
        stddev = StdStats.stddev(succTimes);
    }

    // sample mean of percolation threshold
    public double mean() {
        return mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return stddev;
    }

    // low  endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - 1.96 * stddev() / (Math.sqrt(succTimes.length));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + 1.96 * stddev() / (Math.sqrt(succTimes.length));
    }

    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);
        PercolationStats experiment = new PercolationStats(N, T);
        StdOut.println("mean = " + experiment.mean());
        StdOut.println("stddev = " + experiment.stddev());
        StdOut.println("95% confidence interval = [" + experiment.confidenceLo() + ", "
                               + experiment.confidenceHi() + "]");
    }
}
