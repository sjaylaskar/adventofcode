/*
 * Id: Prob5AOC2023.java 05-Dec-2023 SubhajoyLaskar
 * Copyright (©) 2023 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.japps.adventofcode.probs2023;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.Range;

import com.japps.adventofcode.util.AbstractSolvable;
import com.japps.adventofcode.util.Loggable;

/**
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public final class Prob5AOC2023 extends AbstractSolvable implements Loggable {

	/** The instance. */
	private static final Prob5AOC2023 INSTANCE = instance();

	/**
	 */
	private Prob5AOC2023() {

	}

	/**
	 * Instance.
	 *
	 */
	private static Prob5AOC2023 instance() {

		return new Prob5AOC2023();
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

		final List<Long> seeds = Arrays.asList(lines.get(0).split(":")[1].trim().split(" ")).stream().map(Long::valueOf).collect(Collectors.toList());
		final Map<Long, Long> seedsMap = new HashMap<>();
		seeds.stream().map(Long::valueOf)
				.collect(Collectors.toSet()).forEach(seed -> seedsMap.put(seed, seed));
		println(seedsMap.size());

		process(lines, seedsMap);


		final  Map<Range<Long>, Range<Long>> seedsRangeMap = new HashMap<>();
		for (int i = 0; i <= seeds.size() - 2; i += 2) {
			final Long rangeStart = seeds.get(i);
			final long rangeEnd = rangeStart + seeds.get(i + 1) - 1;
			final Range<Long> range = Range.between(rangeStart, rangeEnd);
			seedsRangeMap.put(range, range);
		}
		processRange(lines, seedsRangeMap);
	}

	/**
	 * @param lines
	 * @param seedsRangeMap
	 */
	private void processRange(final List<String> lines, final  Map<Range<Long>, Range<Long>> seedsRangeMap) {
		final Map<Range<Long>, Range<Long>> resultMap =
				buildRecurringRangeMap(lines, 146, 190,
						buildRecurringRangeMap(lines, 134, 144,
								buildRecurringRangeMap(lines, 119, 132,
										buildRecurringRangeMap(lines, 99, 117,
												buildRecurringRangeMap(lines, 55, 97,
														buildRecurringRangeMap(lines, 34, 53,
																buildRecurringRangeMap(lines, 3, 32, seedsRangeMap)))))));

		resultMap.values().stream().map(Range::getMinimum).mapToLong(Long::valueOf).min().ifPresent(this::println);
	}

	private  Map<Range<Long>, Range<Long>> buildRecurringRangeMap(final List<String> lines, final int lineRangeStart, final int lineRangeEnd, final  Map<Range<Long>, Range<Long>> referRangeMap) {
		final Map<Range<Long>, Range<Long>> resultMap = new HashMap<>();
		final Set<Range<Long>> extraRanges = new HashSet<>();
		processRanges(lines, lineRangeStart, lineRangeEnd, referRangeMap.values(), resultMap, extraRanges, true);
		if (CollectionUtils.isNotEmpty(extraRanges)) {
			processRanges(lines, lineRangeStart, lineRangeEnd, extraRanges, resultMap, extraRanges, false);
		}
		println(resultMap);
		return resultMap;
	}

	private void processRanges(final List<String> lines, final int lineRangeStart, final int lineRangeEnd,
			final Collection<Range<Long>> referRanges, final Map<Range<Long>, Range<Long>> resultMap,
			final Set<Range<Long>> extraRanges, final boolean isFirstRound) {
		for (final Range<Long> referRange : referRanges) {
			for (int lineIndex = lineRangeStart; lineIndex < lineRangeEnd; lineIndex++) {
				final String[] destinationSourceRangeStr = lines.get(lineIndex).split(" ");
				final Long rangeDiff = Long.valueOf(destinationSourceRangeStr[2]) - 1;
				final long sourceRangeStart = Long.valueOf(destinationSourceRangeStr[1]);
				final long sourceRangeEnd = sourceRangeStart + rangeDiff;
				final Range<Long> sourceRange = Range.between(sourceRangeStart, sourceRangeEnd);
				if (referRange.equals(sourceRange)) {
					resultMap.put(referRange, Range.between(Long.valueOf(destinationSourceRangeStr[0]), Long.valueOf(destinationSourceRangeStr[0]) + rangeDiff));
				} else if (sourceRange.containsRange(referRange)) {
					final long destinationRangeStartDiff = referRange.getMinimum() - sourceRangeStart;
					final long destinationRangeEndDiff = referRange.getMaximum() - sourceRangeStart;
					resultMap.put(referRange, Range.between(Long.valueOf(destinationSourceRangeStr[0]) + destinationRangeStartDiff, Long.valueOf(destinationSourceRangeStr[0]) + destinationRangeEndDiff));
				} else if (sourceRange.contains(referRange.getMaximum())) {
					if (isFirstRound) {
						final Range<Long> firstRange = Range.between(referRange.getMinimum(), sourceRangeStart - 1);
						extraRanges.add(firstRange);
					}
					final long secondRangeDiff = referRange.getMaximum() - sourceRangeStart;
					resultMap.put(Range.between(sourceRangeStart, referRange.getMaximum()), Range.between(Long.valueOf(destinationSourceRangeStr[0]), Long.valueOf(destinationSourceRangeStr[0]) + secondRangeDiff));
				} else if (sourceRange.contains(referRange.getMinimum())) {
					if (isFirstRound) {
						final Range<Long> firstRange = Range.between(sourceRangeEnd + 1, referRange.getMaximum());
						extraRanges.add(firstRange);
					}
					final long secondRangeStartDiff = referRange.getMinimum() - sourceRange.getMinimum();
					final long secondRangeEndDiff = sourceRangeEnd - referRange.getMinimum();
					resultMap.put(
							Range.between(referRange.getMinimum(), sourceRangeEnd),
							Range.between(Long.valueOf(destinationSourceRangeStr[0]) + secondRangeStartDiff, Long.valueOf(destinationSourceRangeStr[0]) + secondRangeEndDiff));
				}
			}
			if (resultMap.keySet().stream().allMatch(resultRange -> !resultRange.isOverlappedBy(referRange))) {
				resultMap.put(referRange, referRange);
			}
		}
	}

	private void process(final List<String> lines, final Map<Long, Long> seedsMap) {
		final Map<Long, Long> resultMap =
				buildRecurringMap(lines, 146, 190,
						buildRecurringMap(lines, 134, 144,
								buildRecurringMap(lines, 119, 132,
										buildRecurringMap(lines, 99, 117,
												buildRecurringMap(lines, 55, 97,
														buildRecurringMap(lines, 34, 53,
																buildRecurringMap(lines, 3, 32, seedsMap)))))));

		resultMap.values().stream().mapToLong(Long::valueOf).min().ifPresent(this::println);
	}

	private Map<Long, Long> buildRecurringMap(final List<String> lines, final int lineRangeStart, final int lineRangeEnd, final Map<Long, Long> referMap) {
		final Map<Long, Long> resultMap = new HashMap<>();
		for (final Long referValue : referMap.values()) {
			for (int lineIndex = lineRangeStart; lineIndex < lineRangeEnd; lineIndex++) {
				final String[] destinationSourceRangeStr = lines.get(lineIndex).split(" ");
				final long numberRangeStart = Long.valueOf(destinationSourceRangeStr[1]);
				final long numberRangeEnd = numberRangeStart + Long.valueOf(destinationSourceRangeStr[2]) - 1;
				if (numberRangeStart <= referValue && referValue <= numberRangeEnd) {
					final long actualRange = Long.valueOf(destinationSourceRangeStr[2]) - (numberRangeEnd - referValue) - 1;
					resultMap.put(referValue, Long.valueOf(destinationSourceRangeStr[0]) + actualRange);
				}
			}
		}
		referMap.values().stream().filter(referValue -> !resultMap.containsKey(referValue))
				.forEach(referValue -> resultMap.put(referValue, referValue));
		return resultMap;
	}
}
