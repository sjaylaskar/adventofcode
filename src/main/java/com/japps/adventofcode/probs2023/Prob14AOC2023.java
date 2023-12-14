/*
 * Id: Prob14AOC2023.java 14-Dec-2023 SubhajoyLaskar
 * Copyright (©) 2023 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */

package com.japps.adventofcode.probs2023;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import com.japps.adventofcode.util.AbstractSolvable;
import com.japps.adventofcode.util.Loggable;

/**
 * The prob 14 AOC 2023.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public final class Prob14AOC2023 extends AbstractSolvable implements Loggable {

	/** The instance. */
	private static final Prob14AOC2023 INSTANCE = instance();

	/**
	 * Instantiates a new prob 14 AOC 2023.
	 */
	private Prob14AOC2023() {

	}

	/**
	 * Instance.
	 *
	 * @return the prob 14 AOC 2023
	 */
	private static Prob14AOC2023 instance() {

		return new Prob14AOC2023();
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

		final char[][] loads = new char[lines.size()][lines.get(0).length()];

		for (int row = 0; row < lines.size(); row++) {
			for (int col = 0; col < lines.get(row).length(); col++) {
				loads[row][col] = lines.get(row).charAt(col);
			}
		}

		final char[][] originalLoads = Arrays.copyOf(loads, loads.length);

		tiltPart1(loads);
		calculateLoadSum(loads);

		tiltPart2(originalLoads);
		calculateLoadSum(originalLoads);
	}

	/**
	 * Tilt part 2.
	 *
	 * @param loads the loads
	 */
	private void tiltPart2(final char[][] loads) {
		for (int i = 1; i <= 1000; i++) {
			// north
			northTilt(loads);
			// west
			westTilt(loads);
			// south
			southTilt(loads);
			// east
			eastTilt(loads);
		}
	}

	/**
	 * East tilt.
	 *
	 * @param loads the loads
	 */
	private void eastTilt(final char[][] loads) {
		for (int col = loads[0].length - 2; col >= 0; col--) {
			for (int row = 0; row < loads.length; row++) {
				if (loads[row][col] == 'O') {
					for (int index = col; index < loads[0].length - 1; index++) {
						if (loads[row][index + 1] == '#') {
							break;
						}
						if (loads[row][index + 1] == '.') {
							loads[row][index + 1] = loads[row][index];
							loads[row][index] = '.';
						}
					}
				}
			}
		}
	}

	/**
	 * South tilt.
	 *
	 * @param loads the loads
	 */
	private void southTilt(final char[][] loads) {
		for (int row = loads.length - 2; row >= 0; row--) {
			for (int col = 0; col < loads[row].length; col++) {
				if (loads[row][col] == 'O') {
					for (int index = row; index < loads.length - 1; index++) {
						if (loads[index + 1][col] == '#') {
							break;
						}
						if (loads[index + 1][col] == '.') {
							loads[index + 1][col] = loads[index][col];
							loads[index][col] = '.';
						}
					}
				}
			}
		}
	}

	/**
	 * West tilt.
	 *
	 * @param loads the loads
	 */
	private void westTilt(final char[][] loads) {
		for (int col = 1; col < loads[0].length; col++) {
			for (int row = 0; row < loads.length; row++) {
				if (loads[row][col] == 'O') {
					for (int index = col; index > 0; index--) {
						if (loads[row][index - 1] == '#') {
							break;
						}
						if (loads[row][index - 1] == '.') {
							loads[row][index - 1] = loads[row][index];
							loads[row][index] = '.';
						}
					}
				}
			}
		}
	}

	/**
	 * North tilt.
	 *
	 * @param loads the loads
	 */
	private void northTilt(final char[][] loads) {
		for (int row = 1; row < loads.length; row++) {
			for (int col = 0; col < loads[row].length; col++) {
				if (loads[row][col] == 'O') {
					for (int index = row; index > 0; index--) {
						if (loads[index - 1][col] == '#') {
							break;
						}
						if (loads[index - 1][col] == '.') {
							loads[index - 1][col] = loads[index][col];
							loads[index][col] = '.';
						}
					}
				}
			}
		}
	}

	/**
	 * Calculate load sum.
	 *
	 * @param loads the loads
	 */
	private void calculateLoadSum(final char[][] loads) {
		long loadSum = 0;

		for (int row = 0; row < loads.length; row++) {
			loadSum += String.valueOf(loads[row]).chars().filter(character -> character == 'O').count()
					* (loads.length - row);
		}

		println(loadSum);
	}

	/**
	 * Tilt part 1.
	 *
	 * @param loads the loads
	 */
	private void tiltPart1(final char[][] loads) {
		northTilt(loads);
	}
}
