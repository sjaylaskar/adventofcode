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
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public final class Prob6AOC2023 extends AbstractSolvable implements Loggable {

	/** The instance. */
	private static final Prob6AOC2023 INSTANCE = instance();

	/**
	 */
	private Prob6AOC2023() {

	}

	/**
	 * Instance.
	 *
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
		final List<Integer> times = List.of(57, 72, 69, 92);
		final List<Integer> distances = List.of(291, 1172, 1176, 2026);
		println(times);
	    println(distances);

	    int beat = 1;
	    for (int i = 0; i < times.size(); i++) {
	    	 int numberOfWays = 0;
	    	 for (int time = 1; time < times.get(i); time++) {
	    		  if (time * (times.get(i) - time) > distances.get(i)) {
	    			  numberOfWays = times.get(i) - 2 * time + 1;
	    			  break;
	    		  }
	    	 }
	    	 beat *= numberOfWays;
	    }
	    println(beat);

	    final long raceTime = 57726992L;
	    final long raceDistance = 291117211762026L;
	    println(raceTime);
	    println(raceDistance);
	    long numberOfWays = 0;
	    for (int time = 1; time < raceTime; time++) {
	    	if (time * (raceTime - time) > raceDistance) {
  			  numberOfWays = raceTime - 2 * time + 1;
  			  break;
  		  }
	    }
	    println(numberOfWays);
	}
}
