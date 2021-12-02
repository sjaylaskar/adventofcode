/*
* Id: Prob1BFindNumberOfIncreased3Nums.java 01-Dec-2021 10:33:11 am SubhajoyLaskar
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
 * The prob 1 B find number of increased 3 nums.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public final class Prob1BFindNumberOfIncreased3Nums extends AbstractSolvable implements Loggable {

    /** The instance. */
    private static final Prob1BFindNumberOfIncreased3Nums INSTANCE = instance();

    /**
     * Instantiates a new prob 1 B find number of increased 3 nums.
     */
    private Prob1BFindNumberOfIncreased3Nums() {

    }

    /**
     * Instance.
     *
     */
    private static Prob1BFindNumberOfIncreased3Nums instance() {
        return new Prob1BFindNumberOfIncreased3Nums();
    }

    /**
     * The main method.
     *
     * @param args the arguments
     */
    public static void main(final String[] args) {
        try {
            INSTANCE.info(INSTANCE.findNumberOfIncreased3Nums());
        } catch (final IOException exception) {
            INSTANCE.error(exception.getLocalizedMessage());
        }
    }

    /**
     * Finds the number of increased 3 nums.
     *
     * @return the number of increased 3 nums
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private int findNumberOfIncreased3Nums() throws IOException {

        final List<Integer> nums = lines().stream()
            .map(Integer::parseInt).collect(Collectors.toList());

        int count = 0;
        for (int i = 1; i < nums.size() - 2; i++) {
            final int prevSum = nums.get(i - 1) + nums.get(i) + nums.get(i + 1);
            final int currSum = nums.get(i) + nums.get(i + 1) + nums.get(i + 2);
            if (currSum > prevSum) {
                count++;
            }
        }

        return count;
    }
}
