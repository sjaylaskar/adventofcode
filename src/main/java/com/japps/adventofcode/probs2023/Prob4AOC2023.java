/*
 * Id: Prob4AOC2023.java 04-Dec-2023 SubhajoyLaskar
 * Copyright (©) 2023 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.japps.adventofcode.probs2023;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import org.apache.commons.lang3.math.NumberUtils;

import com.japps.adventofcode.util.AbstractSolvable;
import com.japps.adventofcode.util.Loggable;

/**
 * The prob 4 AOC 2023.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public final class Prob4AOC2023 extends AbstractSolvable implements Loggable {

	/** The instance. */
	private static final Prob4AOC2023 INSTANCE = instance();

	/**
	 * Instantiates a new prob 4 AOC 2023.
	 */
	private Prob4AOC2023() {

	}

	/**
	 * Instance.
	 *
	 * @return the prob 4 AOC 2023
	 */
	private static Prob4AOC2023 instance() {

		return new Prob4AOC2023();
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
		long matchCountSum = 0;
		final Map<Long, Long> copyValuesMap = new HashMap<>();
		long cardNumber = 0;
		for (final String line : lines) {
			final long matchCount = computeMatchCount(line);
			matchCountSum += matchCount > 0 ? Math.pow(2, matchCount - 1) : 0;
			computeCopyScratchCards(copyValuesMap, cardNumber++, matchCount);
		}
		println(matchCountSum);
		println(copyValuesMap.values().stream().mapToLong(Long::valueOf).sum());
	}

	/**
	 * Compute match count.
	 *
	 * @param line the line
	 * @return the long
	 */
	private long computeMatchCount(final String line) {
		final String cardNums = line.split(":")[1].trim();
		final Set<Long> winNums = Arrays.asList(cardNums.substring(0, cardNums.indexOf("|")).trim().split(" ")).stream().map(String::trim).filter(NumberUtils::isCreatable).map(Long::valueOf).collect(Collectors.toSet());
		final Set<Long> cardPoints = Arrays.asList(cardNums.substring(cardNums.indexOf("|") + 1).trim().split(" ")).stream().map(String::trim).filter(NumberUtils::isCreatable).map(Long::valueOf).collect(Collectors.toSet());
		return cardPoints.stream().filter(points -> winNums.contains(points)).count();
	}

	/**
	 * Compute copy scratch cards.
	 *
	 * @param copyValuesMap the copy values map
	 * @param cardNumber the card number
	 * @param matchCount the match count
	 */
	private void computeCopyScratchCards(final Map<Long, Long> copyValuesMap, final long cardNumber, final long matchCount) {
		copyValuesMap.putIfAbsent(cardNumber, 0L);
		copyValuesMap.put(cardNumber, copyValuesMap.get(cardNumber) + 1);
		LongStream.range(0, matchCount).forEach(matchedCopyVal -> {
			final long copiedCardNumber = cardNumber + matchedCopyVal + 1;
			copyValuesMap.put(copiedCardNumber, copyValuesMap.getOrDefault(copiedCardNumber, 0L) + copyValuesMap.get(cardNumber));
		});
	}
}
