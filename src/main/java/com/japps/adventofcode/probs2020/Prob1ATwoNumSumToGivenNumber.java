/*
 * Id: Prob1ATwoNumSumToGivenNumber.java 30-Nov-2021 1:52:54 am SubhajoyLaskar
 * Copyright (©) 2021 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.japps.adventofcode.probs2020;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import com.japps.adventofcode.util.AbstractSolvable;
import com.japps.adventofcode.util.Loggable;


/**
 * The prob 1 A two num sum to given number.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public final class Prob1ATwoNumSumToGivenNumber extends AbstractSolvable implements Loggable {

    /** The instance. */
    private static final Prob1ATwoNumSumToGivenNumber INSTANCE = instance();

    /**
     * Instantiates a new prob 1 A two num sum to given number.
     */
    private Prob1ATwoNumSumToGivenNumber() {

    }

    /**
     * Instance.
     *
     * @return the prob 1 A two num sum to given number
     */
    private static Prob1ATwoNumSumToGivenNumber instance() {
        return new Prob1ATwoNumSumToGivenNumber();
    }

    /**
     * The main method.
     *
     * @param args the arguments
     */
    public static void main(final String[] args) {
        try {
            INSTANCE.info(INSTANCE.findTwoNumProductThatSumToGivenNum(2020));
        } catch (final IOException exception) {
            INSTANCE.error(exception.getLocalizedMessage());
        }
    }

    /**
     * Finds the two num product that sum to given num.
     *
     * @param givenSumNum the given sum num
     * @return the two num product that sum to given num
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private int findTwoNumProductThatSumToGivenNum(final int givenSumNum) throws IOException {
        final List<Integer> nums = Files.lines(Paths.get(determineInputFilePath())).filter(line -> Integer.parseInt(line) <= givenSumNum)
            .map(Integer::parseInt).collect(Collectors.toList());

        final Integer starNum = nums.stream().filter(num -> nums.contains(givenSumNum - num)).findFirst().orElse(-1);

        Loggable.INFO(Prob1ATwoNumSumToGivenNumber.class, starNum);
        return (starNum != -1) ? starNum * (givenSumNum - starNum) : -1;
    }

}
