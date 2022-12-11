/*
 * Id: Prob11Stub.java 11-Dec-2022 SubhajoyLaskar
 * Copyright (©) 2022 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */

package com.japps.adventofcode.probs2022;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;

import org.apache.commons.lang3.ArrayUtils;

import com.japps.adventofcode.util.AbstractSolvable;
import com.japps.adventofcode.util.IntPair;
import com.japps.adventofcode.util.Loggable;

/**
 * The prob 11 stub.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public final class Prob11MonkeyLevel extends AbstractSolvable implements Loggable {

	/** The instance. */
	private static final Prob11MonkeyLevel INSTANCE = instance();

	/**
	 * Instantiates a new prob 11 stub.
	 */
	private Prob11MonkeyLevel() {

	}

	/**
	 * Instance.
	 *
	 * @return the prob 11 stub
	 */
	private static Prob11MonkeyLevel instance() {

		return new Prob11MonkeyLevel();
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

		final List<LinkedList<Long>> monkeyLists = new ArrayList<>();
		final int numberOfMonkeys = (int) lines.stream().filter(line -> line.startsWith("Monkey")).count();
		IntStream.range(0, numberOfMonkeys).forEach(index -> monkeyLists.add(new LinkedList<>()));
		final List<Long> divisibilityTests = new ArrayList<>();
		final List<IntPair> testTrueFalseThrowPairs = new ArrayList<>();
		final long[] inspectionCounts = new long[numberOfMonkeys];
		for (int i = 1, index = 1; i <= numberOfMonkeys; i++, index += 7) {
			monkeyLists.get(i - 1).addAll(
					Arrays.asList(lines.get(index).substring(18).split(", ")).stream().map(Long::valueOf).toList());
			divisibilityTests.add(Long.valueOf(lines.get(index + 2).substring(21)));
			testTrueFalseThrowPairs.add(IntPair.of(Integer.valueOf(lines.get(index + 3).substring(29)),
					Integer.valueOf(lines.get(index + 4).substring(30))));
		}

		computeLevel(monkeyLists, numberOfMonkeys, divisibilityTests, testTrueFalseThrowPairs, inspectionCounts, 20);

		computeLevel(monkeyLists, numberOfMonkeys, divisibilityTests, testTrueFalseThrowPairs, inspectionCounts, 10000);
	}

	/**
	 * Compute level.
	 *
	 * @param monkeyLists             the monkey lists
	 * @param numberOfMonkeys         the number of monkeys
	 * @param divisibilityTests       the divisibility tests
	 * @param testTrueFalseThrowPairs the test true false throw pairs
	 * @param inspectionCounts        the inspection counts
	 * @param maxRounds               the max rounds
	 */
	private void computeLevel(final List<LinkedList<Long>> monkeyLists, final int numberOfMonkeys,
			final List<Long> divisibilityTests, final List<IntPair> testTrueFalseThrowPairs,
			final long[] inspectionCounts, final int maxRounds) {
		for (int round = 1; round <= maxRounds; round++) {
			for (int index = 0; index < numberOfMonkeys; index++) {
				while (!monkeyLists.get(index).isEmpty()) {
					final long item = monkeyLists.get(index).poll();
					final long opItem = operation(index, item) / (maxRounds == 20 ? 3L : 1L);
					monkeyLists.get(divisibilityTest(index, opItem, divisibilityTests)
							? testTrueFalseThrowPairs.get(index).getX()
							: testTrueFalseThrowPairs.get(index).getY()).addLast(opItem);
					inspectionCounts[index]++;
				}
			}
			// printInspectionRounds(inspectionCounts, round);
		}
		Arrays.sort(inspectionCounts);
		final long level = inspectionCounts[numberOfMonkeys - 1] * inspectionCounts[numberOfMonkeys - 2];
		info("Level: " + level);
	}

	/**
	 * Prints the inspection rounds.
	 *
	 * @param inspectionCounts the inspection counts
	 * @param round            the round
	 */
	private void printInspectionRounds(final long[] inspectionCounts, final int round) {
		if (round == 1 || round == 20 || round == 1000 || round == 2000 || round == 3000 || round == 4000
				|| round == 5000 || round == 6000 || round == 7000 || round == 8000 || round == 9000
				|| round == 10000) {
			info("Inspections: Round: " + round + " -> " + ArrayUtils.toString(inspectionCounts));
		}
	}

	/**
	 * Divisibility test.
	 *
	 * @param index             the index
	 * @param opItem            the op item
	 * @param divisibilityTests the divisibility tests
	 * @return true, if successful
	 */
	private boolean divisibilityTest(final int index, final long opItem, final List<Long> divisibilityTests) {
		return opItem % divisibilityTests.get(index) == 0L;
	}

	/**
	 * Operation.
	 *
	 * @param index the index
	 * @param item  the item
	 * @return the long
	 */
	private long operation(final int index, final long item) {

		return switch (index) {
		case 0 -> item * 3;
		case 1 -> item + 2;
		case 2 -> item + 1;
		case 3 -> item + 5;
		case 4 -> item + 4;
		case 5 -> item + 8;
		case 6 -> item * 7;
		case 7 -> item * item;
		default -> throw new IllegalStateException("Unsupported operation.");
		};

		/*
		 * return switch (index) { case 0 -> item * 19; case 1 -> item + 6; case 2 ->
		 * item * item; case 3 -> item + 3; default -> throw new
		 * IllegalStateException("Unsupported operation."); };
		 */
	}
}
