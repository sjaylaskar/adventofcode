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

import org.apache.commons.lang3.StringUtils;

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
		final List<Operation> operations = new ArrayList<>();
		final List<Integer> divisibilityTests = new ArrayList<>();
		final List<IntPair> testTrueFalseThrowPairs = new ArrayList<>();
		final long[] inspectionCounts = new long[numberOfMonkeys];
		for (int i = 1, index = 1; i <= numberOfMonkeys; i++, index += 7) {
			monkeyLists.get(i - 1).addAll(Arrays.asList(lines.get(index).substring(18).split(", ")).stream()
					.map(Integer::valueOf).map(Long::valueOf).toList());
			final String []operationStr =  lines.get(index + 1).substring(19).split(" ");
			operations.add(Operation.of(operationStr[1].charAt(0), operationStr[0], operationStr[2]));
			divisibilityTests.add(Integer.valueOf(lines.get(index + 2).substring(21)));
			testTrueFalseThrowPairs.add(IntPair.of(Integer.valueOf(lines.get(index + 3).substring(29)),
					Integer.valueOf(lines.get(index + 4).substring(30))));
		}

		computeLevel(monkeyLists, numberOfMonkeys, operations, divisibilityTests, testTrueFalseThrowPairs, inspectionCounts, 20);

		computeLevel(monkeyLists, numberOfMonkeys, operations, divisibilityTests, testTrueFalseThrowPairs, inspectionCounts, 10000);
	}

	/**
	 * Compute level.
	 *
	 * @param monkeyLists             the monkey lists
	 * @param numberOfMonkeys         the number of monkeys
	 * @param operations the operations
	 * @param divisibilityTests       the divisibility tests
	 * @param testTrueFalseThrowPairs the test true false throw pairs
	 * @param inspectionCounts        the inspection counts
	 * @param maxRounds               the max rounds
	 */
	private void computeLevel(final List<LinkedList<Long>> monkeyLists, final int numberOfMonkeys, final List<Operation> operations,
			final List<Integer> divisibilityTests, final List<IntPair> testTrueFalseThrowPairs,
			final long[] inspectionCounts, final int maxRounds) {
		for (int round = 1; round <= maxRounds; round++) {
			for (int index = 0; index < numberOfMonkeys; index++) {
				while (!monkeyLists.get(index).isEmpty()) {
					final long item = monkeyLists.get(index).poll();
					final long opItem = operations.get(index).perform(item) / (maxRounds == 20 ? 3 : 1);
					monkeyLists.get(divisibilityTest(index, opItem, divisibilityTests)
							? testTrueFalseThrowPairs.get(index).getX()
							: testTrueFalseThrowPairs.get(index).getY()).addLast(opItem);
					inspectionCounts[index] += 1;
				}
			}
		}
		Arrays.sort(inspectionCounts);
		info("Level: " + inspectionCounts[numberOfMonkeys - 1] * inspectionCounts[numberOfMonkeys - 2]);
	}

	/**
	 * Divisibility test.
	 *
	 * @param index             the index
	 * @param opItem            the op item
	 * @param divisibilityTests the divisibility tests
	 * @return true, if successful
	 */
	private boolean divisibilityTest(final int index, final long opItem, final List<Integer> divisibilityTests) {
		return 0 == opItem % divisibilityTests.get(index);
	}

	/**
	 * The operation.
	 *
	 * @author Subhajoy Laskar
	 * @version 1.0
	 */
	private static final class Operation {

		/** The operator. */
		private final char operator;

		/** The operand 1. */
		private final String operand1;

		/** The operand 2. */
		private final String operand2;

		/**
		 * Instantiates a new operation.
		 *
		 * @param operator the operator
		 * @param operand1 the operand 1
		 * @param operand2 the operand 2
		 */
		private Operation(final char operator, final String operand1, final String operand2) {
			this.operator = operator;
			this.operand1 = operand1;
			this.operand2 = operand2;
		}

		/**
		 * Of.
		 *
		 * @param operator the operator
		 * @param operand1 the operand 1
		 * @param operand2 the operand 2
		 * @return the operation
		 */
		private static Operation of(final char operator, final String operand1, final String operand2) {
			return new Operation(operator, operand1, operand2);
		}

		/**
		 * Perform.
		 *
		 * @param item the item
		 * @return the long
		 */
		private long perform(final long item) {
			if (StringUtils.equals(operand1, operand2)) {
				return operation(operator, item, item);
			} else {
				return operation(operator, Long.valueOf(item), Long.valueOf(operand2));
			}
		}

		/**
		 * Operation.
		 *
		 * @param operator the operator
		 * @param operand1 the operand 1
		 * @param operand2 the operand 2
		 * @return the Long
		 */
		private Long operation(final char operator, final long operand1, final long operand2) {

			return switch (operator) {
			case '+' -> operand1 + operand2;
			case '-' -> operand1 - operand2;
			case '*' -> operand1 * operand2;
			case '/' -> operand1 / operand2;
			default -> throw new IllegalStateException("Unsupported operation.");
			};
		}
	}
}
