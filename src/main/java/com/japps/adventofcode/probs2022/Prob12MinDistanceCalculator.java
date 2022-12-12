/*
 * Id: Prob12MinDistanceCalculator.java 12-Dec-2022 SubhajoyLaskar
 * Copyright (©) 2022 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */

package com.japps.adventofcode.probs2022;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import com.japps.adventofcode.util.AbstractSolvable;
import com.japps.adventofcode.util.IntPair;
import com.japps.adventofcode.util.Loggable;


/**
 * The prob 12 min distance calculator.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public final class Prob12MinDistanceCalculator extends AbstractSolvable implements Loggable {

	/** The instance. */
	private static final Prob12MinDistanceCalculator INSTANCE = instance();

	/**
	 * Instantiates a new prob 12 min distance calculator.
	 */
	private Prob12MinDistanceCalculator() {

	}

	/**
	 * Instance.
	 *
	 * @return the prob 12 min distance calculator
	 */
	private static Prob12MinDistanceCalculator instance() {

		return new Prob12MinDistanceCalculator();
	}

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(final String[] args) {

		try {
			INSTANCE.compute();
		} catch (final IOException exception) {
			INSTANCE.error(exception.getLocalizedMessage());
		}
	}

	/**
	 * Compute.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private void compute() throws IOException {
		final List<String> lines = lines();
		final char[][] grid = new char[lines.size()][lines.get(0).length()];
		for (int i = 0; i < lines.size(); i++) {
			for (int j = 0; j < lines.get(i).length(); j++) {
				grid[i][j] = lines.get(i).charAt(j);
			}
		}
		info("Minimum steps: " + bfsMinimumDistance(grid, false));
		info("Minimum steps from any 'a': : " + bfsMinimumDistance(grid, true));
	}

	/**
	 * The position.
	 *
	 * @author Subhajoy Laskar
	 * @version 1.0
	 */
	private static final class Position {

		/** The row. */
		int row;

		/** The col. */
		int col;

		/** The distance. */
		int distance;

		/**
		 * Instantiates a new position.
		 *
		 * @param row      the row
		 * @param col      the col
		 * @param distance the distance
		 */
		public Position(final int row, final int col, final int distance) {
			this.row = row;
			this.col = col;
			this.distance = distance;
		}
	}

	/**
	 * Bfs minimum distance.
	 *
	 * @param grid                       the grid
	 * @param isSourceAnyPosAtElevationA the is source any pos at elevation A
	 * @return the int
	 */
	private static int bfsMinimumDistance(final char[][] grid, final boolean isSourceAnyPosAtElevationA) {
		int minDistanceFromSource = Integer.MAX_VALUE;
		final Position source = new Position(0, 0, 0);
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				if (!isSourceAnyPosAtElevationA) {
					if (grid[i][j] == 'S') {
						source.row = i;
						source.col = j;
						grid[i][j] = 'a';
						return bfsMinimumDistanceFromSource(source, grid);
					}
				} else if (grid[i][j] == 'S' || grid[i][j] == 'a') {
					source.row = i;
					source.col = j;
					grid[i][j] = 'a';
					final int minDist = bfsMinimumDistanceFromSource(source, grid);
					minDistanceFromSource = minDist > 0 ? Math.min(minDistanceFromSource, minDist) : minDistanceFromSource;
				}
			}
		}

		return minDistanceFromSource;
	}

	/**
	 * Bfs minimum distance from source.
	 *
	 * @param source the source
	 * @param grid   the grid
	 * @return the int
	 */
	private static int bfsMinimumDistanceFromSource(final Position source, final char[][] grid) {
		final Queue<Position> pathQueue = new LinkedList<>();
		pathQueue.add(new Position(source.row, source.col, 0));

		final boolean[][] positionsVisited = new boolean[grid.length][grid[0].length];
		positionsVisited[source.row][source.col] = true;

		while (!pathQueue.isEmpty()) {
			final Position position = pathQueue.remove();

			if (grid[position.row][position.col] == 'E') {
				return position.distance;
			}

			final IntPair currentPos = IntPair.of(position.row, position.col);
			if (isValid(currentPos, IntPair.of(position.row - 1, position.col), grid, positionsVisited)) {
				pathQueue.add(new Position(position.row - 1, position.col, position.distance + 1));
				positionsVisited[position.row - 1][position.col] = true;
			}

			if (isValid(currentPos, IntPair.of(position.row + 1, position.col), grid, positionsVisited)) {
				pathQueue.add(new Position(position.row + 1, position.col, position.distance + 1));
				positionsVisited[position.row + 1][position.col] = true;
			}

			if (isValid(currentPos, IntPair.of(position.row, position.col - 1), grid, positionsVisited)) {
				pathQueue.add(new Position(position.row, position.col - 1, position.distance + 1));
				positionsVisited[position.row][position.col - 1] = true;
			}

			if (isValid(currentPos, IntPair.of(position.row, position.col + 1), grid, positionsVisited)) {
				pathQueue.add(new Position(position.row, position.col + 1, position.distance + 1));
				positionsVisited[position.row][position.col + 1] = true;
			}
		}
		return -1;
	}

	/**
	 * Checks if is valid.
	 *
	 * @param currentPos     the current pos
	 * @param destinationPos the destination pos
	 * @param grid           the grid
	 * @param positionsVisited the positions visited
	 * @return true, if is valid
	 */
	private static boolean isValid(final IntPair currentPos, final IntPair destinationPos, final char[][] grid,
			final boolean[][] positionsVisited) {
		return destinationPos.getX() >= 0 && destinationPos.getY() >= 0 && destinationPos.getX() < grid.length
				&& destinationPos.getY() < grid[0].length && isDestinationValid(currentPos, destinationPos, grid)
				&& positionsVisited[destinationPos.getX()][destinationPos.getY()] == false;
	}

	/**
	 * Checks if is destination valid.
	 *
	 * @param currentPos     the current pos
	 * @param destinationPos the destination pos
	 * @param grid           the grid
	 * @return true, if is destination valid
	 */
	private static boolean isDestinationValid(final IntPair currentPos, final IntPair destinationPos,
			final char[][] grid) {
		return grid[destinationPos.getX()][destinationPos.getY()] - grid[currentPos.getX()][currentPos.getY()] <= 1;
	}
}
