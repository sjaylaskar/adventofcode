/*
 * Id: Prob14AOC2023.java 14-Dec-2023 SubhajoyLaskar
 * Copyright (©) 2023 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */


package com.japps.adventofcode.probs2023;

import java.io.IOException;
import java.util.List;

import com.japps.adventofcode.util.AbstractSolvable;
import com.japps.adventofcode.util.Loggable;


public final class Prob14AOC2023 extends AbstractSolvable implements Loggable {

	/** The instance. */
	private static final Prob14AOC2023 INSTANCE = instance();

	/**
	 */
	private Prob14AOC2023() {

	}

	/**
	 * Instance.
	 *
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

		long loadSum = 0;

		for (int row = 0; row < loads.length; row++) {
			loadSum += String.valueOf(loads[row]).chars().filter(character -> character == 'O').count() * (lines.size() - row);
		}

		println(loadSum);
	}
}
