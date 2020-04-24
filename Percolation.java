/* *****************************************************************************
 *  Name: Percolation
 *  Date: 2020-04-21
 *  Description: Percolation data type used to model the percolation system
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int[][] grid;
    private WeightedQuickUnionUF unionMap;
    private WeightedQuickUnionUF unionMapForFullness;
    private int sizeOfUnionFindObject;

    // creates n-by-n grid, with all sites intially blocked
    public Percolation(int n) {
        if (n <= 0) {
            IllegalArgumentException expection = new IllegalArgumentException(
                    "input must be greater than 0!");
            throw expection;
        }
        grid = new int[n][n];
        // +2 for virtual top and bottom sites
        sizeOfUnionFindObject = grid.length * grid.length + 2;
        unionMap = new WeightedQuickUnionUF(sizeOfUnionFindObject);
        unionMapForFullness = new WeightedQuickUnionUF(grid.length * grid.length + 1);
    }

    // opens the site (row,col) if it is not open already
    public void open(int row, int col) {
        if (row <= 0 || col <= 0) {
            IllegalArgumentException expection = new IllegalArgumentException(
                    "input must be greater than 0");
            throw expection;
        }
        if (!isOpen(row, col)) {
            grid[row - 1][col - 1] = 1;
            // if any adjacent sites are open, connect em
            checkAdjacentSites(row, col);
        }
    }

    // is the site at (row,col) open?
    public boolean isOpen(int row, int col) {
        if (row <= 0 || col <= 0) {
            IllegalArgumentException ex = new IllegalArgumentException(
                    "input must be greater than 0");
            throw ex;
        }
        return grid[row - 1][col - 1] == 1;
    }

    // is the site at (row,col) full?
    public boolean isFull(int row, int col) {
        if (row <= 0 || col <= 0) {
            IllegalArgumentException ex = new IllegalArgumentException(
                    "input must be greater than 0");
            throw ex;
        }
        int index = rowAndColToIndex(row, col);
        return unionMapForFullness.find(0) == unionMapForFullness.find(index);
    }

    // returns number of open sites in grid
    public int numberOfOpenSites() {
        return unionMap.count();
    }

    // does the system percolate?
    public boolean percolates() {
        return unionMap.find(0) == unionMap.find(sizeOfUnionFindObject - 1);
    }

    private void checkAdjacentSites(int i, int j) {
        int siteIndexA = rowAndColToIndex(i, j);

        // Check site to bottom
        if (i < grid.length && isOpen(i + 1, j)) {
            int siteIndexB = rowAndColToIndex(i + 1, j);
            unionMap.union(siteIndexA, siteIndexB);
            unionMapForFullness.union(siteIndexA, siteIndexB);
        }
        else if (i == grid.length) {
            unionMap.union(siteIndexA, sizeOfUnionFindObject - 1);
        }

        // Check site to top
        if (i > 1 && isOpen(i - 1, j)) {
            int siteIndexB = rowAndColToIndex(i - 1, j);
            unionMap.union(siteIndexA, siteIndexB);
            unionMapForFullness.union(siteIndexA, siteIndexB);
        }
        // if we're on the first row, link open sites to virtual start site
        else if (i == 1) {
            unionMap.union(siteIndexA, 0);
            unionMapForFullness.union(siteIndexA, 0);
        }

        // Check site to left
        if (j > 1 && isOpen(i, j - 1)) {
            int siteIndexB = rowAndColToIndex(i, j - 1);
            unionMap.union(siteIndexA, siteIndexB);
            unionMapForFullness.union(siteIndexA, siteIndexB);
        }

        // Check site to right
        if (j < grid.length && isOpen(i, j + 1)) {
            int siteIndexB = rowAndColToIndex(i, j + 1);
            unionMap.union(siteIndexA, siteIndexB);
            unionMapForFullness.union(siteIndexA, siteIndexB);
        }
    }

    // find the index of the element that corresponds to our site within the grid.
    // We're converting from a 2D grid to a 1D array (the unionFind object)
    private int rowAndColToIndex(int row, int col) {
        return ((row - 1) * grid.length) + (col - 1) + 1;
    }

    // test client (optional)
    public static void main(String[] args) {

    }
}
