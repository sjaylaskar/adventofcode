/*
 * Id: Prob7CrabSubmarineFuelCostCalculator.java 30-Nov-2021 1:52:54 am SubhajoyLaskar
 * Copyright (©) 2021 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.japps.adventofcode.probs2021;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.japps.adventofcode.util.AbstractSolvable;
import com.japps.adventofcode.util.Loggable;


/**
 * The prob 7 crab submarine fuel cost calculator.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public final class Prob7CrabSubmarineFuelCostCalculator extends AbstractSolvable implements Loggable {

    /** The instance. */
    private static final Prob7CrabSubmarineFuelCostCalculator INSTANCE = instance();

    /** The comma. */
    private static final String COMMA = ",";

    /**
     * Instantiates a new prob 7 crab submarine fuel cost calculator.
     */
    private Prob7CrabSubmarineFuelCostCalculator() {

    }

    /**
     * Instance.
     *
     * @return the prob 7 crab submarine fuel cost calculator
     */
    private static Prob7CrabSubmarineFuelCostCalculator instance() {

        return new Prob7CrabSubmarineFuelCostCalculator();
    }

    /**
     * The main method.
     *
     * @param args the arguments
     */
    public static void main(final String[] args) {

        try {
            final List<Integer> positions = INSTANCE.determinePositions();
            INSTANCE.info(INSTANCE.calculateFuelCost(positions));
            INSTANCE.info(INSTANCE.calculateFuelCostBinomially(positions));
        } catch (final IOException exception) {
            INSTANCE.error(exception.getLocalizedMessage());
        }
    }

    /**
     * Determines the positions.
     *
     * @return the positions
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private List<Integer> determinePositions() throws IOException {

        final List<String> lines = lines();

        return Arrays.asList(lines.get(0).split(COMMA))
        .stream()
        .map(Integer::parseInt)
        .collect(Collectors.toList());
    }

    /**
     * Calculates the fuel cost.
     *
     * @param positions the positions
     * @return the fuel cost
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private long calculateFuelCost(final List<Integer> positions) throws IOException {

        long sumOfFuel = Long.MAX_VALUE;
        for (int i = 0; i < positions.size(); i++) {
            long sum = 0;
            for (int j = 0; j < positions.size(); j++) {
                sum += Math.abs(positions.get(j) - positions.get(i));
            }
            sumOfFuel = Math.min(sum, sumOfFuel);
        }

        return sumOfFuel;
    }

    /**
     * Calculates the fuel cost binomially.
     *
     * @param positions the positions
     * @return the fuel cost binomially
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private long calculateFuelCostBinomially(final List<Integer> positions) throws IOException {

        long sumOfFuel = Long.MAX_VALUE;
        for (int i = 0; i < positions.size(); i++) {
            long sum = 0;
            for (int j = 0; j < positions.size(); j++) {
                sum += binomialSum(Math.abs(positions.get(j) - positions.get(i)));
            }
            sumOfFuel = Math.min(sum, sumOfFuel);
        }

        return sumOfFuel;
    }

    /**
     * Binomial sum.
     *
     * @param n the n
     * @return the sum of 1 + 2 + 3 + .... + n
     */
    private long binomialSum(final long n) {
        return (n * (n + 1)) / 2;
    }
}
