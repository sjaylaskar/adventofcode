/*
 * Id: Prob1BThreeNumSumToGivenNumber.java 30-Nov-2021 1:52:54 am SubhajoyLaskar
 * Copyright (©) 2021 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.japps.adventofcode.probs2020;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.japps.adventofcode.util.AbstractSolvable;
import com.japps.adventofcode.util.Loggable;


/**
 * The prob 1 B three num sum to given number.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public final class Prob1BThreeNumSumToGivenNumber extends AbstractSolvable implements Loggable {

    /** The instance. */
    private static final Prob1BThreeNumSumToGivenNumber INSTANCE = instance();

    /**
     * Instantiates a new prob 1 B three num sum to given number.
     */
    private Prob1BThreeNumSumToGivenNumber() {

    }

    /**
     * Instance.
     *
     * @return the prob 1 B three num sum to given number
     */
    private static Prob1BThreeNumSumToGivenNumber instance() {
        return new Prob1BThreeNumSumToGivenNumber();
    }

    /**
     * The main method.
     *
     * @param args the arguments
     */
    public static void main(final String[] args) {
        try {
            INSTANCE.info(INSTANCE.findThreeNumProductThatSumToGivenNum(2020));
        } catch (final IOException exception) {
            INSTANCE.error(exception.getLocalizedMessage());
        }
    }

    /**
     * Finds the three num product that sum to given num.
     *
     * @param givenSumNum the given sum num
     * @return the three num product that sum to given num
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private long findThreeNumProductThatSumToGivenNum(final int givenSumNum) throws IOException {

        final List<Integer> nums = lines().stream().filter(line -> Integer.parseInt(line) <= givenSumNum)
            .map(Integer::parseInt).collect(Collectors.toList());

        for (int i = 0; i <= nums.size() - 2; i++) {
            final Set<Integer> numSet = new HashSet<>();
            final int sum = givenSumNum - nums.get(i);
            for (int j = i + 1; j < nums.size(); j++) {
                if (numSet.contains(sum - nums.get(j))) {
                    info("Found triplet: [" + nums.get(i) + ", " + nums.get(j) + ", " + (sum - nums.get(j)) + "]");
                    return (nums.get(i) * nums.get(j) * (sum - nums.get(j)));
                }
                numSet.add(nums.get(j));
            }
        }
        return -1;
    }

}
