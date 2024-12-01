/*
 * Copyright (©) 2024 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.japps.adventofcode.probs2024;

import java.io.*;
import java.util.*;
import java.util.stream.*;

import com.japps.adventofcode.util.*;

/**
 * The prob 1 AOC 2024.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public final class Prob1AOC2024 extends AbstractSolvable implements Loggable {

    /** The instance. */
    private static final Prob1AOC2024 INSTANCE = instance();


    /**
     * Instantiates a new prob 01.
     */
    private Prob1AOC2024() {

    }


    /**
     * Instance.
     *
     * @return the prob 01
     */
    private static Prob1AOC2024 instance() {

        return new Prob1AOC2024();
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
