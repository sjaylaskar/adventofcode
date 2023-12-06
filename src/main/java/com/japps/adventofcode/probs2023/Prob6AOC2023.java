/*
 * Id: Prob6AOC2023.java 06-Dec-2023 SubhajoyLaskar
 * Copyright (©) 2023 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.japps.adventofcode.probs2023;

import java.io.IOException;
import java.util.List;

import com.japps.adventofcode.util.AbstractSolvable;
import com.japps.adventofcode.util.Loggable;

/**
 * The prob 6 AOC 2023.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public final class Prob6AOC2023 extends AbstractSolvable implements Loggable {

	/** The instance. */
	private static final Prob6AOC2023 INSTANCE = instance();

	/**
	 * Instantiates a new prob 6 AOC 2023.
	 */
	private Prob6AOC2023() {

	}

	/**
	 * Instance.
	 *
	 * @return the prob 6 AOC 2023
	 */
	private static Prob6AOC2023 instance() {

		return new Prob6AOC2023();
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
		final List<Long> times = List.of(57L, 72L, 69L, 92L);
		final List<Long> distances = List.of(291L, 1172L, 1176L, 2026L);
		println(times);
	    println(distances);

	    long beat = 1;
	    for (int i = 0; i < times.size(); i++) {
	    	 beat *= findNumberOfWaysToBeat(times.get(i), distances.get(i));
	    }
	    println(beat);

	    final long raceTime = 57726992L;
	    final long raceDistance = 291117211762026L;
	    println(raceTime);
	    println(raceDistance);
	    println(findNumberOfWaysToBeat(raceTime, raceDistance));
	}

	/**
	 * Find number of ways to beat.
	 *
	 * @param raceTime the race time
	 * @param raceDistance the race distance
	 * @return the number of ways to beat
	 */
	private long findNumberOfWaysToBeat(final long raceTime, final long raceDistance) {
		for (long time = 1; time < raceTime; time++) {
			  if (time * (raceTime - time) > raceDistance) {
				  return raceTime - 2 * time + 1;
			  }
		 }
		return 0;
	}
}
