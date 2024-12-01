/*
 * Id: Prob16AOC2023.java 16-Dec-2023 SubhajoyLaskar
 * Copyright (©) 2023 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */

package com.japps.adventofcode.probs2023;

import java.io.IOException;

import com.japps.adventofcode.util.AbstractSolvable;
import com.japps.adventofcode.util.Loggable;

/**
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public final class Prob17AOC2023 extends AbstractSolvable implements Loggable {

	/** The instance. */
	private static final Prob17AOC2023 INSTANCE = instance();

	/**
	 */
	private Prob17AOC2023() {

	}

	/**
	 * Instance.
	 *
	 */
	private static Prob17AOC2023 instance() {

		return new Prob17AOC2023();
	}

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	/*
	 * public static void main(final String[] args) {
	 *
	 * try { INSTANCE.compute(); } catch (final IOException exception) {
	 * INSTANCE.error(exception.getLocalizedMessage()); } }
	 *
	 *//**
		 * Compute.
		 *
		 * @throws IOException Signals that an I/O exception has occurred.
		 *//*
			 * private void compute() throws IOException {
			 *
			 * final List<String> lines = lines();
			 *
			 * }
			 */
 public static void main(final String[] args) {
	        final int[][] grid = {
	                {2, 4, 1, 3, 4, 3, 2, 3, 1, 1, 3, 2, 3},
	                {3, 2, 1, 5, 4, 5, 3, 5, 3, 5, 6, 2, 3},
	                {3, 2, 5, 5, 2, 4, 5, 6, 5, 4, 2, 5, 4},
	                {3, 4, 4, 6, 5, 8, 5, 8, 4, 5, 4, 5, 2},
	                {4, 5, 4, 6, 6, 5, 7, 8, 6, 7, 5, 3, 6},
	                {1, 4, 3, 8, 5, 9, 8, 7, 9, 8, 4, 5, 4},
	                {4, 4, 5, 7, 8, 7, 6, 9, 8, 7, 7, 6, 6},
	                {3, 6, 3, 7, 8, 7, 7, 9, 7, 9, 6, 5, 3},
	                {4, 6, 5, 4, 9, 6, 7, 9, 8, 8, 8, 7, 6},
	                {4, 5, 6, 4, 6, 7, 9, 9, 8, 6, 8, 8, 7},
	                {1, 2, 2, 4, 6, 8, 6, 8, 6, 5, 5, 6, 3},
	                {2, 5, 4, 6, 5, 4, 8, 8, 8, 7, 7, 3, 5},
	                {4, 3, 2, 2, 6, 7, 4, 6, 5, 5, 5, 3, 3},
	        };

	        final int result = findMinimumHeatLoss(grid);
	        System.out.println("The least heat loss is: " + result);
	    }

	    private static int findMinimumHeatLoss(final int[][] grid) {
	        final int rows = grid.length;
	        final int cols = grid[0].length;

	        // Create a 4D array to store the minimum heat loss for each position and direction
	        final int[][][][] dp = new int[rows][cols][4][4];

	        // Initialize the dp array with a large value
	        final int INF = Integer.MAX_VALUE / 2;
	        for (int i = 0; i < rows; i++) {
	            for (int j = 0; j < cols; j++) {
	                for (int k = 0; k < 4; k++) {
	                    for (int l = 0; l < 4; l++) {
	                        dp[i][j][k][l] = INF;
	                    }
	                }
	            }
	        }

	        // Set the initial position's heat loss to 0
	        dp[0][0][0][0] = grid[0][0];

	        // Define the possible directions (right, down, left, up)
	        final int[] dx = {0, 1, 0, -1};
	        final int[] dy = {1, 0, -1, 0};

	        // Iterate through each position in the grid
	        for (int i = 0; i < rows; i++) {
	            for (int j = 0; j < cols; j++) {
	                // Iterate through the current direction
	                for (int k = 0; k < 4; k++) {
	                    // Iterate through the consecutive count
	                    for (int l = 0; l < 4; l++) {
	                        // Try moving in the current direction
	                        for (int m = 1; m <= 3; m++) {
	                            final int ni = i + m * dx[k];
	                            final int nj = j + m * dy[k];

	                            // Check if the new position is within bounds
	                            if (ni >= 0 && ni < rows && nj >= 0 && nj < cols) {
	                                // Update the minimum heat loss for the new position and direction
	                                dp[ni][nj][(k + 1) % 4][l + 1] =
	                                        Math.min(dp[ni][nj][(k + 1) % 4][l + 1], dp[i][j][k][l] + grid[ni][nj]);
	                            }
	                        }
	                    }
	                }
	            }
	        }

	        // Find the minimum heat loss for reaching the machine parts factory
	        int minHeatLoss = INF;
	        for (int k = 0; k < 4; k++) {
	            for (int l = 0; l < 4; l++) {
	                minHeatLoss = Math.min(minHeatLoss, dp[rows - 1][cols - 1][k][l]);
	            }
	        }

	        return minHeatLoss;
	    }


}
