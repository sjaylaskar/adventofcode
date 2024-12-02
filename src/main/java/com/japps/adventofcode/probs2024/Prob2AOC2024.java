/*
 * Copyright (©) 2024 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.japps.adventofcode.probs2024;

import java.io.*;
import java.util.*;
import java.util.function.*;
import java.util.stream.*;

import com.japps.adventofcode.util.*;

public final class Prob2AOC2024 extends AbstractSolvable implements Loggable {

    private static final Prob2AOC2024 INSTANCE = instance();

    private Prob2AOC2024() {

    }

	private static Prob2AOC2024 instance() {

        return new Prob2AOC2024();
    }

    public static void main(final String[] args) {

        try {
            INSTANCE.compute();
        } catch (final IOException exception) {
            INSTANCE.error(exception.getLocalizedMessage());
        }
    }

	private void compute() throws IOException {
		List<String> lines = lines();
		safeCount(lines, false);
		safeCount(lines, true);
	}

	private void safeCount(List<String> lines, boolean isComputeSafeByLevelRemoval) {
		long safeCount = 0;
		for (String line : lines) {
			List<Long> levels = Stream.of(line.split(" ")).map(Long::valueOf).toList();
			boolean isSafe = isSafe(levels);
			if (isSafe) {
				safeCount++;
			} else if (isComputeSafeByLevelRemoval && isSafeForAnyLevelRemoval(levels)) {
				safeCount++;
			}
		}
		println(safeCount);
	}

	private boolean isSafeForAnyLevelRemoval(List<Long> levels) {
		return IntStream.range(0, levels.size()).anyMatch(i -> isSafe(removeLevel(levels, i)));
	}

	private List<Long> removeLevel(List<Long> levels, int i) {
		return IntStream.range(0, levels.size()).filter(index -> index != i).mapToObj(levels::get).toList();
	}

	private static boolean isSafe(List<Long> levels) {
		long firstDiff = levels.get(0) - levels.get(1);
		if (firstDiff == 0L) {
			return false;
		}
		boolean isDecreasing = firstDiff > 0;
		return isSafe(levels, isDecreasing);
	}

	private static boolean isSafe(List<Long> levels, boolean isDecreasing) {
		return IntStream.range(0, levels.size() - 1)
				.mapToLong(index -> diff(levels, index))
				.allMatch(isSafePredicate(isDecreasing));
	}

	private static long diff(List<Long> levels, int index) {
		return levels.get(index) - levels.get(index + 1);
	}

	private static LongPredicate isSafePredicate(boolean isDecreasing) {
		return diff -> isSafe(isDecreasing, diff);
	}

	private static boolean isSafe(boolean isDecreasing, long diff) {
		return isInRange(diff) && isConsistent(diff, isDecreasing);
	}

	private static boolean isConsistent(long diff, boolean isDecreasing) {
		return diff > 0 == isDecreasing;
	}

	private static boolean isInRange(long diff) {
		return Math.abs(diff) >= 1 && Math.abs(diff) <= 3;
	}
}
