/*
 * Id: Prob9PositionsCounter.java 10-Dec-2022 SubhajoyLaskar
 * Copyright (©) 2022 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.japps.adventofcode.probs2022;

import java.io.IOException;

import com.japps.adventofcode.util.AbstractSolvable;
import com.japps.adventofcode.util.Loggable;


/**
 * The prob 9 positions counter.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public final class Prob9PositionsCounter extends AbstractSolvable implements Loggable {

	/** The instance. */
	private static final Prob9PositionsCounter INSTANCE = instance();

	/**
	 * Instantiates a new prob 9 positions counter.
	 */
	private Prob9PositionsCounter() {

	}

	/**
	 * Instance.
	 *
	 * @return the prob 9 positions counter
	 */
	private static Prob9PositionsCounter instance() {

		return new Prob9PositionsCounter();
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

	}

}
