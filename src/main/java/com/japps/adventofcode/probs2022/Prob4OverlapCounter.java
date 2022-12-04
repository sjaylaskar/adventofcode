/*
 * Id: Prob4Test.java 04-Dec-2022 10:46:50 am SubhajoyLaskar
 * Copyright (©) 2022 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.japps.adventofcode.probs2022;

import java.io.IOException;
import java.util.List;

import com.japps.adventofcode.util.AbstractSolvable;
import com.japps.adventofcode.util.Loggable;

/**
 * The prob 4 overlap counter.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public final class Prob4OverlapCounter extends AbstractSolvable implements Loggable {

	/** The instance. */
	private static final Prob4OverlapCounter INSTANCE = instance();

	/**
	 * Instantiates a new prob 04.
	 */
	private Prob4OverlapCounter() {

	}

	/**
	 * Instance.
	 *
	 * @return the prob 04
	 */
	private static Prob4OverlapCounter instance() {

		return new Prob4OverlapCounter();
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
		int fullOverlapCount = 0;
		int overlapCount = 0;
		for (final String line : lines) {
			final String[] pairs = line.split(",");
			final String[] pair1Bounds = pairs[0].split("-");
			final String[] pair2Bounds = pairs[1].split("-");

			final int pair1Low = Integer.valueOf(pair1Bounds[0]);
			final int pair1High = Integer.valueOf(pair1Bounds[1]);

			final int pair2Low = Integer.valueOf(pair2Bounds[0]);
			final int pair2High = Integer.valueOf(pair2Bounds[1]);

			if (isFullOverlap(pair1Low, pair1High, pair2Low, pair2High)) {
				++fullOverlapCount;
			}

			if (isOverlap(pair1Low, pair1High, pair2Low, pair2High)) {
				++overlapCount;
			}
		}
		info("Full overlap count: " + fullOverlapCount);
		info("Overlap count: " + overlapCount);

	}

	/**
	 * Checks if is overlap.
	 *
	 * @param pair1Low the pair 1 low
	 * @param pair1High the pair 1 high
	 * @param pair2Low the pair 2 low
	 * @param pair2High the pair 2 high
	 * @return true, if is overlap
	 */
	private boolean isOverlap(final int pair1Low, final int pair1High, final int pair2Low, final int pair2High) {
		return pair2Low <= pair1Low && pair1Low <= pair2High || pair2Low <= pair1High && pair1High <= pair2High
				|| pair1Low <= pair2Low && pair2Low <= pair1High || pair1Low <= pair2High && pair2High <= pair1High;
	}

	/**
	 * Checks if is full overlap.
	 *
	 * @param pair1Low the pair 1 low
	 * @param pair1High the pair 1 high
	 * @param pair2Low the pair 2 low
	 * @param pair2High the pair 2 high
	 * @return true, if is full overlap
	 */
	private boolean isFullOverlap(final int pair1Low, final int pair1High, final int pair2Low, final int pair2High) {
		return pair2Low <= pair1Low && pair1High <= pair2High || pair1Low <= pair2Low && pair2High <= pair1High;
	}
}
