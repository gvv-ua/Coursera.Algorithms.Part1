/**
 * Created by gvv on 20.05.17.
 */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private WeightedQuickUnionUF data;
    private WeightedQuickUnionUF data1;
    private boolean[] sites;
    private int openSites = 0;
    private int topSite;
    private int bottomSite;
    private int n;

    /**
     * Perform trials independent experiments on an n-by-n grid
     **/
    public Percolation(int n) {
        this.n = n;
        if (n <= 0) {
            throw new IllegalArgumentException("n must be positive");
        }
        data = new WeightedQuickUnionUF(n * n + 2);
        data1 = new WeightedQuickUnionUF(n * n + 1);

        topSite = n * n;
        bottomSite = n * n + 1;

        sites = new boolean[n * n];
    }

    private int getIndex(int row, int col) {
        return (row - 1) * n + (col - 1);
    }

    private void unionUp(int row, int col) {
        if (row > 1) {
            if (isOpen(row - 1, col)) {
                data.union(getIndex(row, col), getIndex(row - 1, col));
                data1.union(getIndex(row, col), getIndex(row - 1, col));
            }
        } else {
            data.union(getIndex(row, col), topSite);
            data1.union(getIndex(row, col), topSite);
        }
    }

    private void unionDown(int row, int col) {
        if (row < n) {
            if (isOpen(row + 1, col)) {
                data.union(getIndex(row, col), getIndex(row + 1, col));
                data1.union(getIndex(row, col), getIndex(row + 1, col));
            }
        } else {
            data.union(getIndex(row, col), bottomSite);
        }
    }

    private void unionLeft(int row, int col) {
        if ((col > 1) && (isOpen(row, col - 1))) {
            data.union(getIndex(row, col), getIndex(row, col - 1));
            data1.union(getIndex(row, col), getIndex(row, col - 1));
        }
    }

    private void unionRight(int row, int col) {
        if ((col < n) && (isOpen(row, col + 1))) {
            data.union(getIndex(row, col), getIndex(row, col + 1));
            data1.union(getIndex(row, col), getIndex(row, col + 1));
        }
    }

    /**
     * Open site (row, col) if it is not open already
     */
    public void open(int row, int col) {
        if ((row <= 0) || (col <= 0) || (row > n) || (col > n)) {
            throw new IndexOutOfBoundsException();
        }
        if (!isOpen(row, col)) {
            sites[getIndex(row, col)] = true;
            openSites++;
            unionUp(row, col);
            unionDown(row, col);
            unionLeft(row, col);
            unionRight(row, col);
        }
    }

    /**
     * Is site (row, col) open?
     */
    public boolean isOpen(int row, int col) {
        if ((row <= 0) || (col <= 0) || (row > n) || (col > n)) {
            throw new IndexOutOfBoundsException();
        }

        return sites[getIndex(row, col)];
    }

    /**
     * Is site (row, col) full?
     */
    public boolean isFull(int row, int col) {
        if ((row <= 0) || (col <= 0) || (row > n) || (col > n)) {
            throw new IndexOutOfBoundsException();
        }

        return data1.connected(getIndex(row, col), topSite);
    }

    /**
     * Number of open sites
     */
    public int numberOfOpenSites() {
        return openSites;
    }

    /**
     * Does the system percolate?
     */
    public boolean percolates() {
        return data.connected(topSite, bottomSite);
    }

    /**
     * Test client (optional)
     */
    public static void main(String[] args) {

    }
}
