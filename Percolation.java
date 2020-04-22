/* *****************************************************************************
 *  Name: Percolation
 *  Date: 2020-04-21
 *  Description: Percolation data type used to model the percolation system
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    // 0 - blocked/full; 1 - empty
    private int[][] grid;
    private boolean percolationPossible = false;
    private WeightedQuickUnionUF unionMap;
    private int sizeOfUnionFindObject;

    // creates n-by-n grid, with all sites intially blocked
    public Percolation(int n) {
        try {
            grid = new int[n][n];
            // +2 for virtual top and bottom sites
            sizeOfUnionFindObject = grid.length * grid.length + 2;
            unionMap = new WeightedQuickUnionUF(sizeOfUnionFindObject);
        }
        catch (IllegalArgumentException exception) {
            System.out.println("Invalid entry");
        }
    }

    // opens the site (row,col) if it is not open already
    public void open(int row, int col) {
        grid[row - 1][col - 1] = 1;
    }

    // is the site at (row,col) open?
    public boolean isOpen(int row, int col) {
        return grid[row - 1][col - 1] == 1;
    }

    // is the site at (row,col) full?
    public boolean isFull(int row, int col) {
        int index = rowAndColToIndex(row, col);
        return unionMap.find(0) == unionMap.find(index);
    }

    // returns number of open sites in grid
    public int numberOfOpenSites() {
        return unionMap.count();
    }

    // does the system percolate?
    public boolean percolates() {
        // setup the union-find data structure
        unionFindStuff();

        return percolationPossible;
    }

    private void unionFindStuff() {
        for (int i = 1; i <= grid.length; i++) {
            for (int j = 1; j <= grid.length; j++) {
                if (isOpen(i, j)) {
                    checkAdjacentSites(unionMap, i, j);
                }
            }
        }

        if (unionMap.find(0) == unionMap.find(sizeOfUnionFindObject - 1))
            percolationPossible = true;
    }

    private void checkAdjacentSites(WeightedQuickUnionUF unionFind, int i, int j) {
        int siteIndexA = rowAndColToIndex(i, j);

        // Check site to bottom
        if (i < grid.length && isOpen(i + 1, j)) {
            int siteIndexB = rowAndColToIndex(i + 1, j);
            unionFind.union(siteIndexA, siteIndexB);
        }
        // if we're on the last row, link site to virtual finish site
        else if (i == grid.length) {
            unionFind.union(siteIndexA, sizeOfUnionFindObject - 1);
        }

        // Check site to top
        if (i > 1 && isOpen(i - 1, j)) {
            int siteIndexB = rowAndColToIndex(i - 1, j);
            unionFind.union(siteIndexA, siteIndexB);
        }
        // if we're on the first row, link open sites to virtual start site
        else if (i == 1) {
            unionFind.union(siteIndexA, 0);
        }

        // Check site to left
        if (j > 1 && isOpen(i, j - 1)) {
            int siteIndexB = rowAndColToIndex(i, j - 1);
            unionFind.union(siteIndexA, siteIndexB);
        }

        // Check site to right
        if (j < grid.length && isOpen(i, j + 1)) {
            int siteIndexB = rowAndColToIndex(i, j + 1);
            unionFind.union(siteIndexA, siteIndexB);
        }
    }

    // find the index of the element that corresponds to our site within the grid
    private int rowAndColToIndex(int row, int col) {
        return ((row - 1) * grid.length) + (col - 1);
    }

    // test client (optional)
    public static void main(String[] args) {
        int counter = 0;
        Percolation test = new Percolation(1);
        for (int i = 0; i < test.grid.length; i++) {
            // for (int j = 0; j < test.grid.length; j++) {
            test.open(i, counter);
            counter++;
            //}
        }
        System.out.println(test.percolates());
    }
}
