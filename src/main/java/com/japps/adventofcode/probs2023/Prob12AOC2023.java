/*
 * Id: Prob12AOC2023.java 03-Jan-2024 SubhajoyLaskar
 * Copyright (©) 2024 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */

package com.japps.adventofcode.probs2023;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

import com.japps.adventofcode.util.AbstractSolvable;
import com.japps.adventofcode.util.Loggable;

/**
 * The prob 12 AOC 2023.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public final class Prob12AOC2023 extends AbstractSolvable implements Loggable {

	/** The instance. */
	private static final Prob12AOC2023 INSTANCE = instance();

	/**
	 * Instantiates a new prob 12 AOC 2023.
	 */
	private Prob12AOC2023() {

	}

	/**
	 * Instance.
	 *
	 * @return the prob 12 AOC 2023
	 */
	private static Prob12AOC2023 instance() {

		return new Prob12AOC2023();
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

	/** The Constant CACHE. */
	private static final HashMap<Arrangement, Long> CACHE = new HashMap<>();

	/** The Constant OPERATIONAL. */
	private static final char OPERATIONAL = '.';

	/** The Constant DAMAGED. */
	private static final char DAMAGED = '#';

	/** The Constant UNKNOWN. */
	private static final char UNKNOWN = '?';

	/**
	 * Compute.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private void compute() throws IOException {
		final List<String> lines = lines();

		final List<Arrangement> arrangementsPart1 = new ArrayList<>();
		final List<Arrangement> arrangementsPart2 = new ArrayList<>();
		processArrangements(lines, arrangementsPart1, arrangementsPart2);
		println(calculate(arrangementsPart1));
		println(calculate(arrangementsPart2));
	}

	/**
	 * Append char.
	 *
	 * @param linePart  the line part
	 * @param character the character
	 * @return the string
	 */
	private String appendChar(final String linePart, final char character) {
		final StringBuilder linePartBuilder = new StringBuilder(linePart);
		IntStream.range(0, 4).forEach(index -> {
			linePartBuilder.append(character);
			linePartBuilder.append(linePart);
		});
		return linePartBuilder.toString();
	}

	/**
	 * Process arrangements.
	 *
	 * @param lines             the lines
	 * @param arrangementsPart1 the arrangements part 1
	 * @param arrangementsPart2 the arrangements part 2
	 */
	private void processArrangements(final List<String> lines, final List<Arrangement> arrangementsPart1,
			final List<Arrangement> arrangementsPart2) {
		for (final String line : lines) {
			final String rowPart1 = line.split(" ")[0];
			final String rowPart2 = appendChar(rowPart1, UNKNOWN);
			final String countsPart1 = line.split(" ")[1];
			final String countsPart2 = appendChar(countsPart1, ',');
			arrangementsPart1.add(Arrangement.of(rowPart1.toCharArray(),
					Arrays.stream(countsPart1.split(",")).map(Integer::valueOf).toList(), 0, 0, 0));
			arrangementsPart2.add(Arrangement.of(rowPart2.toCharArray(),
					Arrays.stream(countsPart2.split(",")).map(Integer::valueOf).toList(), 0, 0, 0));
		}
	}

	/**
	 * Calculate.
	 *
	 * @param arrangements the arrangements
	 * @return the long
	 */
	private long calculate(final List<Arrangement> arrangements) {
		long sum = 0;
		for (final Arrangement row : arrangements) {
			sum += row.calculate();
			CACHE.clear();
		}
		return sum;
	}

	/**
	 * The arrangement.
	 *
	 * @author Subhajoy Laskar
	 * @version 1.0
	 */
	private static final class Arrangement {

		/** The row chars. */
		private final char[] rowChars;

		/** The counts. */
		private final List<Integer> counts;

		/** The row char index. */
		private final int rowCharIndex;

		/** The count index. */
		private final int countIndex;

		/** The invalid. */
		private final int invalid;

		/**
		 * Of.
		 *
		 * @param rowChars     the row chars
		 * @param counts       the counts
		 * @param rowCharIndex the row char index
		 * @param countIndex   the count index
		 * @param invalid      the invalid
		 * @return the arrangement
		 */
		private static Arrangement of(final char[] rowChars, final List<Integer> counts, final int rowCharIndex,
				final int countIndex, final int invalid) {
			return new Arrangement(rowChars, counts, rowCharIndex, countIndex, invalid);
		}

		/**
		 * Instantiates a new arrangement.
		 *
		 * @param rowChars     the row chars
		 * @param counts       the counts
		 * @param rowCharIndex the row char index
		 * @param countIndex   the count index
		 * @param invalid      the invalid
		 */
		private Arrangement(final char[] rowChars, final List<Integer> counts, final int rowCharIndex,
				final int countIndex, final int invalid) {
			this.rowChars = rowChars;
			this.counts = counts;
			this.rowCharIndex = rowCharIndex;
			this.countIndex = countIndex;
			this.invalid = invalid;
		}

		/**
		 * Calculate.
		 *
		 * @return the long
		 */
		private long calculate() {
			if (CACHE.containsKey(this)) {
				return CACHE.get(this);
			}

			long count = 0;

			if (rowCharIndex == rowChars.length) {
				CACHE.put(this, count = countIndex == counts.size() && invalid == 0
						|| countIndex == counts.size() - 1 && invalid == counts.get(countIndex) ? 1 : count);
				return count;
			}

			CACHE.put(this, count = switch (rowChars[rowCharIndex]) {
			case OPERATIONAL -> calculateOperational();
			case DAMAGED -> calculateDamaged();
			case UNKNOWN -> calculateOperational() + calculateDamaged();
			default -> count;
			});
			return count;
		}

		/**
		 * Calculate operational.
		 *
		 * @return the long
		 */
		private long calculateOperational() {
			return invalid > 0
					? counts.get(countIndex) != invalid ? 0
							: of(rowChars, counts, rowCharIndex + 1, countIndex + 1, 0).calculate()
					: of(rowChars, counts, rowCharIndex + 1, countIndex, 0).calculate();
		}

		/**
		 * Calculate damaged.
		 *
		 * @return the long
		 */
		private long calculateDamaged() {
			return countIndex == counts.size() || invalid >= counts.get(countIndex) ? 0
					: of(rowChars, counts, rowCharIndex + 1, countIndex, invalid + 1).calculate();
		}

		/**
		 * Hash code.
		 *
		 * @return the int
		 */
		@Override
		public int hashCode() {
			return Objects.hash(countIndex, invalid, rowCharIndex);
		}

		/**
		 * Equals.
		 *
		 * @param obj the obj
		 * @return true, if successful
		 */
		@Override
		public boolean equals(final Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (getClass() != obj.getClass()) {
				return false;
			}
			final Arrangement other = (Arrangement) obj;
			return countIndex == other.countIndex && invalid == other.invalid && rowCharIndex == other.rowCharIndex;
		}
	}
}
