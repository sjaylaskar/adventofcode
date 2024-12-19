/*
 * Copyright (©) 2024 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.japps.adventofcode.probs2024;

import static com.japps.adventofcode.util.StringUtil.*;

import java.io.*;
import java.util.*;
import java.util.stream.*;

import com.japps.adventofcode.util.*;

public final class Prob19AOC2024 extends AbstractSolvable implements Loggable {

    private static final Prob19AOC2024 INSTANCE = instance();

    private Prob19AOC2024() {

    }

    private static Prob19AOC2024 instance() {

        return new Prob19AOC2024();
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
		Set<String> tokens = Stream.of(lines.getFirst().split(", ")).collect(Collectors.toSet());
        println(IntStream.range(2, lines.size()).filter(index -> isConstructable(lines.get(index), tokens)).count());
        println(IntStream.range(2, lines.size()).mapToLong(index -> constructableWays(lines.get(index), tokens)).sum());
    }


}
