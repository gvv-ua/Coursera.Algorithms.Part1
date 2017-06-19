/**
 * Created by gvv on 20.05.17.
 */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private int n;
    private int trials;
    private double[] x;
    private double v1, v2;

    /**
     * Perform trials independent experiments on an n-by-n grid
     */
    public PercolationStats(int n, int trials) {
        if ((n <= 0) || (trials <= 0)) {
            throw new IllegalArgumentException("n and trials must be positive");
        }
        this.n = n;
        this.trials = trials;
        x = new double[trials];
        double vSum1 = 0;
        for (int i = 0; i < trials; i++) {
            Percolation percolation = new Percolation(n);
            while (!percolation.percolates()) {
                int index = StdRandom.uniform(n * n);
                int row = getRowByIndex(index);
                int col = getColByIndex(index);

                while (percolation.isOpen(row, col)) {
                    index = StdRandom.uniform(n * n);
                    row = getRowByIndex(index);
                    col = getColByIndex(index);
                }

                percolation.open(row, col);
            }

            x[i] = (double) percolation.numberOfOpenSites() / (n * n);
            vSum1 += x[i];
        }
        v1 = vSum1 / trials;
        double vSum2 = 0;
        for (int i = 0; i < trials; i++) {
            vSum2 += (v1 - x[i]) * (v1 - x[i]);
        }
        v2 = Math.sqrt(vSum2 / (trials - 1));
    }

    private int getRowByIndex(int index) {
        return (index / n) + 1;
    }

    private int getColByIndex(int index) {
        return index - (index / n) * n + 1;
    }

    /**
     * Sample mean of percolation threshold
     */
    public double mean() {
        return StdStats.mean(x);

    }

    /**
     * Sample standard deviation of percolation threshold
     */
    public double stddev() {
        return StdStats.stddev(x);
    }

    /**
     * Low  endpoint of 95% confidence interval
     */
    public double confidenceLo() {
        return v1 - 1.96 * v2 / Math.sqrt(trials);
    }

    /**
     * High endpoint of 95% confidence interval
     */
    public double confidenceHi() {
        return v1 + 1.96 * v2 / Math.sqrt(trials);
    }

    /**
     * Test client (described below)
     */
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);

        PercolationStats percolationStats = new PercolationStats(n, t);
        System.out.println(String.format("mean                    = %f", percolationStats.mean()));
        System.out.println(String.format("stddev                  = %f", percolationStats.stddev()));
        System.out.println(String.format("95%% confidence interval = [%f, %f]",
                percolationStats.confidenceLo(), percolationStats.confidenceHi()));
    }
}
