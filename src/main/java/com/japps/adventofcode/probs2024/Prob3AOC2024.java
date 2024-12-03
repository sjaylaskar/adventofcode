/*
 * Copyright (©) 2024 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.japps.adventofcode.probs2024;

import java.io.*;
import java.util.*;
import java.util.regex.*;

import com.japps.adventofcode.util.*;

public final class Prob3AOC2024 extends AbstractSolvable implements Loggable {

    private static final Prob3AOC2024 INSTANCE = instance();

    private Prob3AOC2024() {

    }

    private static Prob3AOC2024 instance() {

        return new Prob3AOC2024();
    }

    public static void main(final String[] args) {

        try {
            INSTANCE.compute();
        } catch (final IOException exception) {
            INSTANCE.error(exception.getLocalizedMessage());
        }
    }

    private static final Pattern PATTERN = Pattern.compile("mul\\([0-9]+,[0-9]+\\)");
    private static final Pattern NUM_PATTERN = Pattern.compile("[0-9]+");
    private static final String DONT_PATTERN = "don't()";
    private static final String DO_PATTERN = "do()";


    private void compute() throws IOException {
		compute(false);
        compute(true);
    }

    private void compute(boolean isWithDoDontInstructions) throws IOException {
        computeSum((!isWithDoDontInstructions) ? multiplications() : multiplicationsWithDoDontInstructions());
    }

    private List<String> multiplications() throws IOException {
        return lines().stream().map(Prob3AOC2024::multiplications).flatMap(List::stream).toList();
    }

    private static List<String> multiplications(String line) {
        List<String> multiplications = new ArrayList<>();
        Matcher matcher = PATTERN.matcher(line);
        while (matcher.find()) {
            multiplications.add(matcher.group());
        }
        return multiplications;
    }

    private List<String> multiplicationsWithDoDontInstructions() throws IOException {
        List<String> multiplications = new ArrayList<>();
        multiplicationsWithDoDontInstructions(String.join("", lines()), multiplications);
        return multiplications;
    }

    private static void multiplicationsWithDoDontInstructions(String instruction, List<String> multiplications) {
        int beginIndex = 0;
        int endIndex = instruction.indexOf(DONT_PATTERN);
        while (beginIndex >= 0 && endIndex >= 0 && beginIndex < instruction.length() && endIndex < instruction.length()) {
            String line = instruction.substring(beginIndex, endIndex);
            Matcher matcher = PATTERN.matcher(line);
            while (matcher.find()) {
                multiplications.add(matcher.group());
            }
            beginIndex = instruction.indexOf(DO_PATTERN, endIndex + DONT_PATTERN.length() + 1);
            endIndex = instruction.indexOf(DONT_PATTERN, beginIndex + DO_PATTERN.length() + 1);
        }
    }

    private void computeSum(List<String> multiplications) {
        long sum = 0;
        for (String multiplication : multiplications) {
            Matcher matcher = NUM_PATTERN.matcher(multiplication);
            List<Long> nums = new ArrayList<>();
            while (matcher.find()) {
                nums.add(Long.valueOf(matcher.group()));
            }
            sum += (nums.get(0) * nums.get(1));
        }
        println(sum);
    }
}
