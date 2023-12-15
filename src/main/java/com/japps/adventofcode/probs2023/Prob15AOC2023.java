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

/**
 * The prob 14 AOC 2023.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public final class Prob15AOC2023 extends AbstractSolvable implements Loggable {

	/** The instance. */
	private static final Prob15AOC2023 INSTANCE = instance();

	/**
	 * Instantiates a new prob 14 AOC 2023.
	 */
	private Prob15AOC2023() {

	}

	/**
	 * Instance.
	 *
	 * @return the prob 14 AOC 2023
	 */
	private static Prob15AOC2023 instance() {

		return new Prob15AOC2023();
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

		final String[] steps = lines.get(0).split(",");

		long sum = 0;
		for (final String step : steps) {
			long stepHash = 0;
			for (int i = 0; i < step.length(); i++) {
				stepHash += step.charAt(i);
				stepHash *= 17;
				stepHash %= 256;
			}
			sum += stepHash;
		}
		println(sum);
	}
}
