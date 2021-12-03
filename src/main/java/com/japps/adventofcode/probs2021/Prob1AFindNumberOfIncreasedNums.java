/*
* Id: Prob1AFindNumberOfIncreasedNums.java 01-Dec-2021 10:33:11 am SubhajoyLaskar
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
 * The prob 1 A find number of increased nums.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public final class Prob1AFindNumberOfIncreasedNums extends AbstractSolvable implements Loggable {

    /** The instance. */
    private static final Prob1AFindNumberOfIncreasedNums INSTANCE = instance();

    /**
     * Instantiates a new prob 1 A find number of increased nums.
     */
    private Prob1AFindNumberOfIncreasedNums() {

    }

    /**
     * Instance.
     *
     * @return the prob 1 A find number of increased nums
     */
    private static Prob1AFindNumberOfIncreasedNums instance() {
        return new Prob1AFindNumberOfIncreasedNums();
    }

    /**
     * The main method.
     *
     * @param args the arguments
     */
    public static void main(final String[] args) {
        try {
            INSTANCE.info(INSTANCE.findNumberOfIncreasedNums());
        } catch (final IOException exception) {
            INSTANCE.error(exception.getLocalizedMessage());
        }
    }

    /**
     * Finds the number of increased nums.
     *
     * @return the number of increased nums
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private int findNumberOfIncreasedNums() throws IOException {

        final List<Integer> nums = lines().stream()
            .map(Integer::parseInt).collect(Collectors.toList());

        int count = 0;
        for (int i = 1; i < nums.size(); i++) {
            if (nums.get(i) > nums.get(i - 1)) {
                ++count;
            }
        }

        return count;
    }
}
