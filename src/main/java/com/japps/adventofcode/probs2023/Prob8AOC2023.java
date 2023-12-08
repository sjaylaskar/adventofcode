/*
 * Id: Prob8AOC2023.java 08-Dec-2023 SubhajoyLaskar
 * Copyright (©) 2023 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.japps.adventofcode.probs2023;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import com.japps.adventofcode.util.AbstractSolvable;
import com.japps.adventofcode.util.Loggable;

/**
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public final class Prob8AOC2023 extends AbstractSolvable implements Loggable {

	/** The instance. */
	private static final Prob8AOC2023 INSTANCE = instance();

	/**
	 */
	private Prob8AOC2023() {

	}

	/**
	 * Instance.
	 *
	 */
	private static Prob8AOC2023 instance() {

		return new Prob8AOC2023();
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

		final String instructions = lines.get(0);

		final Map<String, Pair<String, String>> pathMap = new HashMap<>();

		for (int lineIndex = 2; lineIndex < lines.size(); lineIndex++) {
			final String line = lines.get(lineIndex);
			final String[] path = line.split("=");
			final String pathKey = path[0].trim();
			final String pathValues = path[1].trim();
			final String[] pathLeftRight = pathValues.trim().replace("(", "").replace(")", "").replace(",", "")
					.split(" ");
			pathMap.put(pathKey, Pair.of(pathLeftRight[0].trim(), pathLeftRight[1].trim()));
		}

		computePartOne(pathMap, instructions);

		computePartTwo(pathMap, instructions);
	}

	private void computePartOne(final Map<String, Pair<String, String>> pathMap, final String instructions) {
		long stepCount = 0;
		String key = "AAA";
		while (!StringUtils.equals(key, "ZZZ")) {
			for (int i = 0; i < instructions.length(); i++) {
				stepCount++;
				key = nextKey(pathMap.get(key), instructions.charAt(i));
				if (StringUtils.equals(key, "ZZZ")) {
					break;
				}
			}
		}

		println(stepCount);
	}

	private String nextKey(final Pair<String, String> pathValue, final char instruction) {
		return switch (instruction) {
		case 'L' -> pathValue.getLeft();
		case 'R' -> pathValue.getRight();
		default -> "";
		};
	}

	/**
	 * @param pathMap
	 * @param instructions
	 */
	private void computePartTwo(final Map<String, Pair<String, String>> pathMap, final String instructions) {

		Set<String> keys = pathMap.keySet().stream().filter(key -> key.endsWith("A")).collect(Collectors.toSet());
		long stepCount = 0;
		final List<Character> instructionChars = instructions.chars().mapToObj(charVal -> (char)charVal).collect(Collectors.toList());
		while (!endsWithZ(keys)) {
			for (final Character instructionChar : instructionChars) {
				stepCount++;
				keys = keys.stream().map(key -> nextKey(pathMap.get(key), instructionChar)).collect(Collectors.toSet());
				println(keys + " - " + stepCount);
				if (endsWithZ(keys)) {
					break;
				}
			}
		}

		println(stepCount);
	}

	private boolean endsWithZ(final Set<String> keys) {
		return CollectionUtils.isNotEmpty(keys) && keys.stream().allMatch(key -> key.endsWith("Z"));
	}

}
