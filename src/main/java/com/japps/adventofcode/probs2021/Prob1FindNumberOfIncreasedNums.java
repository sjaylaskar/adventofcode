/*
 * Id: Prob1FindNumberOfIncreasedNums.java 01-Dec-2021 10:33:11 am SubhajoyLaskar
 * Copyright (©) 2021 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.japps.adventofcode.probs2021;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import com.japps.adventofcode.util.AbstractSolvable;
import com.japps.adventofcode.util.Loggable;


/**
 * The prob 1 find number of increased nums.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public final class Prob1FindNumberOfIncreasedNums extends AbstractSolvable implements Loggable {

    /** The instance. */
    private static final Prob1FindNumberOfIncreasedNums INSTANCE = instance();

    /**
     * Instantiates a new prob 1 find number of increased nums.
     */
    private Prob1FindNumberOfIncreasedNums() {

    }

    /**
     * Instance.
     *
     * @return the prob 1 find number of increased nums
     */
    private static Prob1FindNumberOfIncreasedNums instance() {

        return new Prob1FindNumberOfIncreasedNums();
    }

    /**
     * The main method.
     *
     * @param args the arguments
     */
    public static void main(final String[] args) {

        try {
            final List<Integer> nums = INSTANCE.nums();
            INSTANCE.info(INSTANCE.findNumberOfIncreasedNums(nums));
            INSTANCE.info(INSTANCE.findNumberOfIncreased3Nums(nums));
        } catch (final IOException exception) {
            INSTANCE.error(exception.getLocalizedMessage());
        }
    }

    /**
     * Nums.
     *
     * @return the list
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private List<Integer> nums() throws IOException {

        return lines().stream()
            .map(Integer::parseInt).collect(Collectors.toList());
    }

    /**
     * Finds the number of increased nums.
     *
     * @param nums the nums
     * @return the number of increased nums
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private int findNumberOfIncreasedNums(final List<Integer> nums) throws IOException {

        int count = 0;
        for (int i = 1; i < nums.size(); i++) {
            if (nums.get(i) > nums.get(i - 1)) {
                ++count;
            }
        }

        return count;
    }

    /**
     * Finds the number of increased 3 nums.
     *
     * @param nums the nums
     * @return the number of increased 3 nums
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private int findNumberOfIncreased3Nums(final List<Integer> nums) throws IOException {

        int count = 0;
        for (int i = 1; i < nums.size() - 2; i++) {
            if (nums.get(i + 2) > nums.get(i - 1)) {
                count++;
            }
        }

        return count;
    }
}
