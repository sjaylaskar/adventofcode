/*
 * Id: Prob8AOC2023.java 08-Dec-2023 SubhajoyLaskar
 * Copyright (©) 2023 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.japps.adventofcode.probs2023;

import java.io.IOException;
import java.math.BigInteger;
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
 * The prob 8 AOC 2023.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public final class Prob8AOC2023 extends AbstractSolvable implements Loggable {

	/** The instance. */
	private static final Prob8AOC2023 INSTANCE = instance();

	/**
	 * Instantiates a new prob 8 AOC 2023.
	 */
	private Prob8AOC2023() {

	}

	/**
	 * Instance.
	 *
	 * @return the prob 8 AOC 2023
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

		// computePartTwo(pathMap, instructions);

		computePartTwoFaster(pathMap, instructions);
	}

	/**
	 * Compute part one.
	 *
	 * @param pathMap the path map
	 * @param instructions the instructions
	 */
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

	/**
	 * Next key.
	 *
	 * @param pathValue the path value
	 * @param instruction the instruction
	 * @return the string
	 */
	private String nextKey(final Pair<String, String> pathValue, final char instruction) {
		return switch (instruction) {
		case 'L' -> pathValue.getLeft();
		case 'R' -> pathValue.getRight();
		default -> "";
		};
	}

	/**
	 * Compute part two.
	 *
	 * @param pathMap the path map
	 * @param instructions the instructions
	 */
	private void computePartTwo(final Map<String, Pair<String, String>> pathMap, final String instructions) {

		Set<String> keys = pathMap.keySet().stream().filter(key -> key.endsWith("A")).collect(Collectors.toSet());
		long stepCount = 0;
		final List<Character> instructionChars = instructions.chars().mapToObj(charVal -> (char) charVal)
				.collect(Collectors.toList());
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

	/**
	 * Ends with Z.
	 *
	 * @param keys the keys
	 * @return true, if successful
	 */
	private boolean endsWithZ(final Set<String> keys) {
		return CollectionUtils.isNotEmpty(keys) && keys.stream().allMatch(key -> key.endsWith("Z"));
	}

	/**
	 * Compute part two faster.
	 *
	 * @param pathMap the path map
	 * @param instructions the instructions
	 */
	private void computePartTwoFaster(final Map<String, Pair<String, String>> pathMap, final String instructions) {

		long stepsCount = 1;
		long cycleSize = 0;
		long cycleStepsCounter = 0;
		final List<String> keys = pathMap.keySet().stream().filter(key -> key.endsWith("A"))
				.collect(Collectors.toList());
		int instructionStepIndex = 0;
		while (cycleSize < keys.size()) {
			final char instruction = instructions.charAt(instructionStepIndex++ % instructions.length());
			for (int stepIndex = 0; stepIndex < keys.size(); stepIndex++) {
				keys.set(stepIndex, nextKey(pathMap.get(keys.get(stepIndex)), instruction));
			}
			for (int stepIndex = 0; stepIndex < keys.size(); stepIndex++) {
				if ((cycleStepsCounter & 1 << stepIndex) == 0 && keys.get(stepIndex).endsWith("Z")) {
					cycleStepsCounter |= 1 << stepIndex;
					cycleSize++;
					stepsCount = lcm(BigInteger.valueOf(stepsCount), BigInteger.valueOf(instructionStepIndex));
				}
			}
		}
		println(stepsCount);
	}

	/**
	 * Lcm.
	 *
	 * @param number1 the number 1
	 * @param number2 the number 2
	 * @return the long
	 */
	public static long lcm(final BigInteger number1, final BigInteger number2) {
		final BigInteger gcd = number1.gcd(number2);
		final BigInteger productAbsoluteValue = number1.multiply(number2).abs();
		return productAbsoluteValue.divide(gcd).longValue();
	}

}
