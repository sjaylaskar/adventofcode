/*
 * Copyright (©) 2024 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.japps.adventofcode.probs2024;

import java.io.*;
import java.util.*;
import java.util.stream.*;

import com.japps.adventofcode.util.*;

public final class Prob25AOC2024 extends AbstractSolvable implements Loggable {

    private static final Prob25AOC2024 INSTANCE = instance();

    private Prob25AOC2024() {

    }

    private static Prob25AOC2024 instance() {

        return new Prob25AOC2024();
    }

    public static void main(final String[] args) {

        try {
            INSTANCE.compute();
        } catch (final IOException exception) {
            INSTANCE.error(exception.getLocalizedMessage());
        }
    }

    private static final String LOCK_TOP_PATTERN = "#####";
    private static final String KEY_TOP_PATTERN = ".....";
    private static final char FILLED_PATTERN = '#';

    private void compute() throws IOException {
		List<String> lines = lines();
        List<String> locks = new ArrayList<>();
        List<String> keys = new ArrayList<>();
		for (int i = 0; i < lines.size(); i+=8) {
            List<String> lockOrKey = lines.subList(i, i + 7);
            if (LOCK_TOP_PATTERN.equals(lockOrKey.getFirst())) {
                locks.add(buildCombination(lockOrKey));
            } else if (KEY_TOP_PATTERN.equals(lockOrKey.getFirst())) {
                keys.add(buildCombination(lockOrKey));
            }
        }

        println(locks.stream().mapToLong(lock -> keys.stream().filter(key -> isFitPair(lock, key)).count()).sum());
    }

    private static boolean isFitPair(String lock, String key) {
        return IntStream.range(0, key.length()).allMatch(columnIndex -> Integer.parseInt(String.valueOf(lock.charAt(columnIndex))) + Integer.parseInt(String.valueOf(key.charAt(columnIndex))) <= key.length());
    }

    private String buildCombination(List<String> lockOrKey) {
        StringBuilder lockOrKeyPatternBuilder = new StringBuilder();
        IntStream.range(0, lockOrKey.getFirst().length())
                .forEach(columnIndex -> lockOrKeyPatternBuilder.append(
                        lockOrKey.stream().filter(lockOrKeyLine -> FILLED_PATTERN == lockOrKeyLine.charAt(columnIndex)).count() - 1));
        return lockOrKeyPatternBuilder.toString();
    }
}
