/*
 * Copyright (©) 2024 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.japps.adventofcode.probs2024;

import com.japps.adventofcode.util.AbstractSolvable;
import com.japps.adventofcode.util.Loggable;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public final class Prob11AOC2024 extends AbstractSolvable implements Loggable {

    private static final Prob11AOC2024 INSTANCE = instance();
    public static final String SPACE = " ";
    public static final String UNDERSCORE_KEY_UNDERSCORE = "_KEY_";

    private Prob11AOC2024() {

    }

    private static Prob11AOC2024 instance() {

        return new Prob11AOC2024();
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
		String[] stones = lines.getFirst().split(SPACE);
        computeWithMemo(stones); // USE DYNAMIC PROGRAMMING.
        computeWithoutMemo(stones); // VERY SLOW and MEMORY INEFFICIENT.
    }

    private static final Map<String, Long> MEMO = new HashMap<>();

    private void computeWithMemo(String[] stones) {
        println(Stream.of(stones).mapToLong(stone -> computeWithMemo(Long.parseLong(stone), 25)).sum());
        println(Stream.of(stones).mapToLong(stone -> computeWithMemo(Long.parseLong(stone), 75)).sum());
    }

    public static long computeWithMemo(long stone, int repetition) {
        String memoKey = stone + UNDERSCORE_KEY_UNDERSCORE + repetition;
        if (MEMO.containsKey(memoKey)) {
            return MEMO.get(memoKey);
        }
        long stoneCount = (repetition == 0) ? 1 : (stone == 0) ? computeWithMemo(1, repetition - 1) : stoneCountForEvenOddDigitedStone(stone, repetition);
        MEMO.put(memoKey, stoneCount);
        return stoneCount;
    }

    private static long stoneCountForEvenOddDigitedStone(long stone, int repetition) {
        String stoneString = String.valueOf(stone);
        return (stoneString.length() % 2 == 0)
                ? computeWithMemo(Long.parseLong(stoneString.substring(0, stoneString.length() / 2)), repetition - 1)
                    + computeWithMemo(Long.parseLong(stoneString.substring(stoneString.length() / 2)), repetition - 1)
                : computeWithMemo(stone * 2024, repetition - 1);
    }

    private void computeWithoutMemo(String[] stones) {
        println(rearrangeStones(Arrays.copyOf(stones, stones.length), 25).length);
        println(rearrangeStones(Arrays.copyOf(stones, stones.length), 75).length);
    }

    private static String[] rearrangeStones(String[] stones, int repetition) {
        for (int i = 1; i <= repetition; i++) {
            stones = rearrangeStones(stones, new StringBuilder());
        }
        return stones;
    }

    private static String[] rearrangeStones(String[] stones, StringBuilder stonesBuilder) {
        Stream.of(stones).forEach(stone -> stonesBuilder.append((stone.length() == 1 && Long.parseLong(stone) == 0) ? 1 + SPACE : (stone.length() % 2 != 0) ? Long.parseLong(stone) * 2024 + SPACE : Long.parseLong(stone.substring(0, stone.length() / 2)) + SPACE + Long.parseLong(stone.substring(stone.length() / 2)) + SPACE));
        return stonesBuilder.toString().trim().split(SPACE);
    }
}