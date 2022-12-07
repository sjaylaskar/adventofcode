/*
 * Id: Prob7DirectorySizeComputer.java 07-Dec-2022 12:07:19 pm SubhajoyLaskar
 * Copyright (©) 2022 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */

package com.japps.adventofcode.probs2022;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import com.japps.adventofcode.util.AbstractSolvable;
import com.japps.adventofcode.util.Loggable;


/**
 * The prob 7 directory size computer.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public final class Prob7DirectorySizeComputer extends AbstractSolvable implements Loggable {

	/** The instance. */
	private static final Prob7DirectorySizeComputer INSTANCE = instance();


	/**
	 * Instantiates a new prob 7 directory size computer.
	 */
	private Prob7DirectorySizeComputer() {

	}


	/**
	 * Instance.
	 *
	 * @return the prob 7 directory size computer
	 */
	private static Prob7DirectorySizeComputer instance() {

		return new Prob7DirectorySizeComputer();
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
		final Map<String, Long> dirSizes = buildDirPathSizes();

		final long totalDirMemory = dirSizes.get("//");
		final long freeMemory = totalDirMemory - 40000000L;

		long lessThan100000SizeDirMemorySum = 0L;
		long smallestDeletableDirMemory = Long.MAX_VALUE;

		for (final Map.Entry<String, Long> entry : dirSizes.entrySet()) {
			if (entry.getValue() <= 100000) {
				lessThan100000SizeDirMemorySum += entry.getValue();
			}
			if (entry.getValue() >= freeMemory) {
				smallestDeletableDirMemory = Math.min(smallestDeletableDirMemory, entry.getValue());
			}
		}

		info(lessThan100000SizeDirMemorySum);
		info(smallestDeletableDirMemory);
	}


	/**
	 * Builds the dir path sizes.
	 *
	 * @return the map
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private Map<String, Long> buildDirPathSizes() throws IOException {
		final List<String> lines = lines();

		final Map<String, Long> dirSizes = new HashMap<>();
		final Stack<String> path = new Stack<>();

		for (final String line : lines) {
			final String[] commandInputs = line.split(" ");
			if (commandInputs.length >= 2 && commandInputs[1].equals("cd")) {
				if (commandInputs.length >= 3 && commandInputs[2].equals("..")) {
					path.pop();
				} else {
					path.add(commandInputs[2]);
				}
			} else if (commandInputs.length >= 2 && commandInputs[1].equals("ls")) {
				continue;
			} else if (commandInputs.length >= 1 && commandInputs[0].equals("dir")) {
				continue;
			} else {
				final long dirSize = Long.valueOf(commandInputs[0]);
				final StringBuilder dirPathBuilder = new StringBuilder();
				for (int i = 0; i < path.size(); i++) {
					dirPathBuilder.append("/").append(path.get(i));
					dirSizes.putIfAbsent(dirPathBuilder.toString(), 0L);
					dirSizes.put(dirPathBuilder.toString(), dirSizes.get(dirPathBuilder.toString()) + dirSize);
				}
			}
		}

		info("dirSizes: " + dirSizes);
		return dirSizes;
	}
}
