/*
 * Id: Prob6MessageMarker.java 06-Dec-2022 10:28:44 am SubhajoyLaskar
 * Copyright (©) 2022 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.japps.adventofcode.probs2022;

import java.io.IOException;

import com.japps.adventofcode.util.AbstractSolvable;
import com.japps.adventofcode.util.Loggable;


/**
 * The prob 6.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public final class Prob6MessageMarker extends AbstractSolvable implements Loggable {

	/** The instance. */
	private static final Prob6MessageMarker INSTANCE = instance();

	/**
	 * Instantiates a new prob 6.
	 */
	private Prob6MessageMarker() {

	}

	/**
	 * Instance.
	 *
	 * @return the prob 6.
	 */
	private static Prob6MessageMarker instance() {

		return new Prob6MessageMarker();
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
		final String line = lines().get(0);
		info("Packet marker after: " + computeMarkerEntryIndex(line, 4) + " characters.");
		info("Message marker after: " + computeMarkerEntryIndex(line, 14) + " characters.");
	}

	/**
	 * Compute marker entry index.
	 *
	 * @param line the line
	 * @param markerLength the marker length
	 * @return the marker entry index
	 */
	private int computeMarkerEntryIndex(final String line, final int markerLength) {
		int start = 0;
		int end = markerLength;
		while (true) {
			final String packetMarker = line.substring(start, end);
			if (packetMarker.chars().distinct().count() == markerLength) {
				break;
			}
			start++;
			end = start + markerLength;
		}
		return end;
	}

}
