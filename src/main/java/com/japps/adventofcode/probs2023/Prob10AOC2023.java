/*
 * Id: Prob10AOC2023.java 10-Dec-2023 SubhajoyLaskar
 * Copyright (©) 2023 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */

package com.japps.adventofcode.probs2023;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.japps.adventofcode.util.AbstractSolvable;
import com.japps.adventofcode.util.Loggable;
import com.japps.adventofcode.util.Point;

/**
 * The prob 10 AOC 2023.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public final class Prob10AOC2023 extends AbstractSolvable implements Loggable {

	/** The instance. */
	private static final Prob10AOC2023 INSTANCE = instance();

	/**
	 * Instantiates a new prob 10 AOC 2023.
	 */
	private Prob10AOC2023() {

	}

	/**
	 * Instance.
	 *
	 * @return the prob 10 AOC 2023
	 */
	private static Prob10AOC2023 instance() {

		return new Prob10AOC2023();
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

	/** The Constant PIPE_MAP. */
	private static final Map<Integer, Set<Character>> PIPE_MAP = Map.of(0, Set.of('|', 'F', '7'), 1,
			Set.of('-', 'J', '7'), 2, Set.of('|', 'J', 'L'), 3, Set.of('-', 'F', 'L'));

	/** The Constant DIRECTION_MAP. */
	private static final Map<Character, Set<Integer>> DIRECTION_MAP = Map.of('.', Set.of(), '7', Set.of(2, 3), 'F',
			Set.of(1, 2), 'L', Set.of(0, 1), 'J', Set.of(0, 3), '|', Set.of(0, 2), '-', Set.of(1, 3), 'S',
			Set.of(0, 1, 2, 3));

	/** The Constant DIRECTIONS_X_COORDINATE. */
	private static final int DIRECTIONS_X_COORDINATE[] = new int[] { -1, 0, 1, 0 };

	/** The Constant DIRECTIONS_Y_COORDINATE. */
	private static final int DIRECTIONS_Y_COORDINATE[] = new int[] { 0, 1, 0, -1 };

	/**
	 * Compute.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private void compute() throws IOException {

		final List<String> lines = lines();

		final int rows = lines.size();
		final int cols = lines.get(0).length();

		final char[][] path = new char[rows][cols];

		final int[][] visitedPath = new int[rows][cols];

		final Point startPos = processPath(lines, rows, cols, path, visitedPath);

		final int direction = DIRECTION_MAP.get(path[startPos.getX()][startPos.getY()]).iterator().next();
		final Point position = Point.of(startPos.getX() + DIRECTIONS_X_COORDINATE[direction],
				startPos.getY() + DIRECTIONS_Y_COORDINATE[direction]);
		visitedPath[position.getX()][position.getY()] = 1;
		final int pathSize = computePart1(path, visitedPath, startPos, position, (direction + 2) % 4);
		println(pathSize / 2);
		println(computePart2(path, visitedPath));
	}

	private Point processPath(final List<String> lines, final int rows, final int cols, final char[][] path,
			final int[][] visitedPath) {
		Point startPos = null;

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				path[i][j] = lines.get(i).charAt(j);
				if (path[i][j] == 'S') {
					startPos = Point.of(i, j);
					visitedPath[i][j] = 1;
				}
			}
		}

		final Set<Integer> directions = new HashSet<>();
		for (int i = 0; i < DIRECTIONS_X_COORDINATE.length; i++) {
			final int xCoordinate = startPos.getX() + DIRECTIONS_X_COORDINATE[i];
			final int yCoordinate = startPos.getY() + DIRECTIONS_Y_COORDINATE[i];
			if (xCoordinate < 0 || xCoordinate >= rows || yCoordinate < 0 || yCoordinate >= cols) {
				continue;
			}
			if (PIPE_MAP.get(i).contains(path[xCoordinate][yCoordinate])) {
				directions.add(i);
			}
		}

		for (final char directionCharacter : DIRECTION_MAP.keySet()) {
			if (directions.equals(DIRECTION_MAP.get(directionCharacter))) {
				path[startPos.getX()][startPos.getY()] = directionCharacter;
			}
		}
		return startPos;
	}

	/**
	 * Compute part 1.
	 *
	 * @param path the path
	 * @param visitedPath the visited path
	 * @param startPos the start pos
	 * @param position the position
	 * @param direction the direction
	 * @return the int
	 */
	private int computePart1(final char[][] path, final int[][] visitedPath, final Point startPos, final Point position,
			int direction) {
		int pathSize = 1;
		char currentPipePosition = path[position.getX()][position.getY()];
		int nextDirection = 0;

		while (!position.equals(startPos)) {
			for (final int currentDirection : DIRECTION_MAP.get(currentPipePosition)) {
				if (direction != currentDirection) {
					nextDirection = currentDirection;
				}
			}

			direction = (nextDirection + 2) % 4;
			position.setX(position.getX() + DIRECTIONS_X_COORDINATE[nextDirection]);
			position.setY(position.getY() + DIRECTIONS_Y_COORDINATE[nextDirection]);
			currentPipePosition = path[position.getX()][position.getY()];
			visitedPath[position.getX()][position.getY()] = 1;
			pathSize++;
		}

		return pathSize;
	}

	/**
	 * Compute part 2.
	 *
	 * @param path the path
	 * @param visitedPath the visited path
	 * @return the int
	 */
	private int computePart2(final char[][] path, final int[][] visitedPath) {
		boolean isEnclosed = false;
		int numberOfPipes = 0;
		int numberOfEnclosedTiles = 0;
		for (int i = 0; i < visitedPath.length; i++) {
			for (int j = 0; j < visitedPath[i].length; j++) {
				if (visitedPath[i][j] == 0) {
					if (isEnclosed) {
						numberOfEnclosedTiles++;
						visitedPath[i][j] = 2;
					}
				} else {
					final char pathChar = path[i][j];
					if (pathChar == '|' || pathChar == 'L' || pathChar == 'J') {
						numberOfPipes++;
						isEnclosed = numberOfPipes % 2 != 0;
					}
				}
			}
			isEnclosed = false;
			numberOfPipes = 0;
		}
		return numberOfEnclosedTiles;
	}
}
