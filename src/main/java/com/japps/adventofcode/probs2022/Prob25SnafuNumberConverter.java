/*
 * Id: Prob25SnafuNumberConverter.java 25-Dec-2022 SubhajoyLaskar
 * Copyright (©) 2022 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.japps.adventofcode.probs2022;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.japps.adventofcode.util.AbstractSolvable;
import com.japps.adventofcode.util.Loggable;

/**
 * The prob 25 snafu number converter.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public final class Prob25SnafuNumberConverter extends AbstractSolvable implements Loggable {

	/** The Constant INSTANCE. */
	private static final Prob25SnafuNumberConverter INSTANCE = instance();

	/**
	 * Instantiates a new prob 25 snafu number converter.
	 */
	private Prob25SnafuNumberConverter() {

	}

	/**
	 * Instance.
	 *
	 * @return the prob 25 snafu number converter
	 */
	private static Prob25SnafuNumberConverter instance() {

		return new Prob25SnafuNumberConverter();
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

	/** The Constant REVERSE_SNAFU_MAP. */
	private static final Map<Character, Integer> REVERSE_SNAFU_MAP = new HashMap<>();

	/** The Constant SNAFU_MAP. */
	private static final Map<Long, Character> SNAFU_MAP = new HashMap<>();

	static {
		REVERSE_SNAFU_MAP.put('2', 2);
		REVERSE_SNAFU_MAP.put('1', 1);
		REVERSE_SNAFU_MAP.put('0', 0);
		REVERSE_SNAFU_MAP.put('-', -1);
		REVERSE_SNAFU_MAP.put('=', -2);

		SNAFU_MAP.put(0L, '0');
		SNAFU_MAP.put(1L, '1');
		SNAFU_MAP.put(2L, '2');
		SNAFU_MAP.put(3L, '=');
		SNAFU_MAP.put(4L, '-');
	}

	/**
	 * Compute.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private void compute() throws IOException {
		final List<String> lines = lines();

		info("Result = " + snafu(lines.stream().mapToLong(this::reverseSnafu).sum()));
	}

	/**
	 * Reverse snafu.
	 *
	 * @param expression the expression
	 * @return the long
	 */
	private long reverseSnafu(final String expression) {
		long result = 0;
		long captureBand = 1;
		for (int i = expression.length() - 1; i >= 0; i--) {
			result += REVERSE_SNAFU_MAP.get(expression.charAt(i)) * captureBand;
			captureBand *= 5;
		}
		return result;
	}

	/**
	 * Snafu.
	 *
	 * @param value the value
	 * @return the string
	 */
	private String snafu(final long value) {
		final StringBuilder snafuStringBuilder = new StringBuilder();
        long snafuNum = value;
        while (snafuNum > 0) {
            final long remainder = snafuNum % 5;
            snafuStringBuilder.append(SNAFU_MAP.get(remainder));
            if (remainder > 2) {
                snafuNum += 5;
            }
            snafuNum /= 5;
        }
        return StringUtils.reverse(snafuStringBuilder.toString());
	}

}
