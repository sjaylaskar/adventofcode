/*
 * Id: Prob25AOC2023.java 26-Dec-2023 SubhajoyLaskar
 * Copyright (©) 2023 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */

package com.japps.adventofcode.probs2023;

import java.io.IOException;
import java.util.List;

import com.japps.adventofcode.util.AbstractSolvable;
import com.japps.adventofcode.util.Loggable;

/**
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public final class Prob25AOC2023 extends AbstractSolvable implements Loggable {

	/** The instance. */
	private static final Prob25AOC2023 INSTANCE = instance();

	/**
	 */
	private Prob25AOC2023() {

	}

	/**
	 * Instance.
	 *
	 */
	private static Prob25AOC2023 instance() {

		return new Prob25AOC2023();
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

		println(new Day25().part1Solution(lines));

	}
}
