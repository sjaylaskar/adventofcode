/*
 * Copyright (©) 2024 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.japps.adventofcode.probs2024;

import java.io.*;
import java.util.*;
import java.util.stream.*;

import com.japps.adventofcode.util.*;

public final class Prob1AOC2024 extends AbstractSolvable implements Loggable {

    private static final Prob1AOC2024 INSTANCE = instance();

    private Prob1AOC2024() {

    }

    private static Prob1AOC2024 instance() {

        return new Prob1AOC2024();
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
		List<Long> leftList = new ArrayList<>();
		List<Long> rightList = new ArrayList<>();
		for (String line : lines) {
			String []tokens = line.split(" {3}");
			leftList.add(Long.valueOf(tokens[0]));
			rightList.add(Long.valueOf(tokens[1]));
		}
		Collections.sort(leftList);
		Collections.sort(rightList);
		println(IntStream.range(0, leftList.size()).mapToLong(index -> Math.abs(rightList.get(index) - leftList.get(index))).sum());
		println(leftList.stream().mapToLong(n -> n * Collections.frequency(rightList, n)).sum());
    }
}
