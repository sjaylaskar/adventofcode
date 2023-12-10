/*
 * Id: Prob9AOC2023.java 09-Dec-2023 SubhajoyLaskar
 * Copyright (©) 2023 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.japps.adventofcode.probs2023;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.japps.adventofcode.util.AbstractSolvable;
import com.japps.adventofcode.util.Loggable;

/**
 * The prob 9 AOC 2023.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public final class Prob9AOC2023 extends AbstractSolvable implements Loggable {

	/** The instance. */
	private static final Prob9AOC2023 INSTANCE = instance();

	/**
	 * Instantiates a new prob 9 AOC 2023.
	 */
	private Prob9AOC2023() {

	}

	/**
	 * Instance.
	 *
	 * @return the prob 9 AOC 2023
	 */
	private static Prob9AOC2023 instance() {

		return new Prob9AOC2023();
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

		final List<LineResult> lineResults = lines.stream().map(this::processMissingNums).toList();

		println(lineResults.stream().mapToLong(LineResult::part1).sum());

		println(lineResults.stream().mapToLong(LineResult::part2).sum());

	}

	/**
	 * Process missing nums.
	 *
	 * @param line the line
	 * @return the line result
	 */
	private LineResult processMissingNums(final String line) {
		List<Long> nums = Arrays.stream(line.split(" ")).map(Long::valueOf).toList();

		long missingEndNum = 0;

		final List<Long> missingBeginningNums = new ArrayList<>();

		while (isScrollable(nums)) {
			missingBeginningNums.add(nums.get(0));
			missingEndNum += nums.get(nums.size() - 1);
			final List<Long> nextNums = new ArrayList<>();
			for (int index = 1; index < nums.size(); index++) {
				nextNums.add(nums.get(index) - nums.get(index - 1));
			}
			nums = nextNums;
		}

		long missingBeginningNumDiff = 0;
		for (int index = missingBeginningNums.size() - 1; index >= 0; index--) {
			missingBeginningNumDiff = missingBeginningNums.get(index) - missingBeginningNumDiff;
		}

		return new LineResult().withPart1(missingEndNum).withPart2(missingBeginningNumDiff);

	}

	/**
	 * Checks if is scrollable.
	 *
	 * @param nums the nums
	 * @return true, if is scrollable
	 */
	private boolean isScrollable(final List<Long> nums) {
		return nums.size() > 1 && nums.stream().anyMatch(num -> num != 0);
	}

	/**
	 * The line result.
	 *
	 * @author Subhajoy Laskar
	 * @version 1.0
	 */
	private static final class LineResult {

		/** The part 1. */
		private long part1;

		/** The part 2. */
		private long part2;

		/**
		 * With part 1.
		 *
		 * @param part1 the part 1
		 * @return the line result
		 */
		private LineResult withPart1(final long part1) {
			this.part1 = part1;
			return this;
		}

		/**
		 * With part 2.
		 *
		 * @param part2 the part 2
		 * @return the line result
		 */
		private LineResult withPart2(final long part2) {
			this.part2 = part2;
			return this;
		}

		/**
		 * Part 1.
		 *
		 * @return the part1
		 */
		public long part1() {
			return part1;
		}

		/**
		 * Part 2.
		 *
		 * @return the part2
		 */
		public long part2() {
			return part2;
		}


	}

}
