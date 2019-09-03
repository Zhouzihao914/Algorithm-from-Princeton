/* *****************************************************************************
 *  Name: Zihao Zhou
 *  Date: 9.3.2019
 *  Description: None
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private boolean[][] matrix;
    private int count;
    private WeightedQuickUnionUF uf1;
    private WeightedQuickUnionUF uf2;

    // create n-by-n grid, with all sites blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("size n can not be negative");
        }
        matrix = new boolean[n][n];

        uf1 = new WeightedQuickUnionUF(n * n + 1);
        uf2 = new WeightedQuickUnionUF(n * n + 2);
    }

    // open site (row, col) if it is not open already
    public void open(int row, int col) {

        paraValid(row, col);
        if (isOpen(row, col)) return;
        int n = matrix.length;
        count++;
        matrix[row - 1][col - 1] = true;
        int index = (row - 1) * n + col;
        if (row == 1) {
            uf1.union(0, index);
            uf2.union(0, index);
        }
        if (row == n) {
            uf2.union(index, n * n + 1);
        }
        if (col - 1 > 0) {
            if (isOpen(row, col - 1)) {
                uf1.union(index - 1, index);
                uf2.union(index - 1, index);
            }
        }
        if (col + 1 <= n) {
            if (isOpen(row, col + 1)) {
                uf1.union(index, index + 1);
                uf2.union(index, index + 1);
            }
        }
        if (row - 1 > 0) {
            if (isOpen(row - 1, col)) {
                uf1.union(index - n, index);
                uf2.union(index - n, index);
            }

        }
        if (row + 1 <= n) {
            if (isOpen(row + 1, col)) {
                uf1.union(index, index + n);
                uf2.union(index, index + n);
            }
        }
    }

    // is site (row, col) open?
    public boolean isOpen(int row, int col) {
        paraValid(row, col);
        return matrix[row - 1][col - 1];
    }

    // is site (row, col) full?
    public boolean isFull(int row, int col) {
        paraValid(row, col);
        int n = matrix.length;
        return uf1.connected(0, (row - 1) * n + col);
    }

    // number of open sites
    public int numberOfOpenSites() {
        return count;
    }

    // does the system percolate?
    public boolean percolates() {
        int n = matrix.length;
        return uf2.connected(0, n * n + 1);
    }

    private void paraValid(int row, int col) {
        int n = matrix.length;
        if (row < 1 || row > n || col < 1 || col > n) {
            throw new IllegalArgumentException("parameters should be in range");
        }
    }
}
