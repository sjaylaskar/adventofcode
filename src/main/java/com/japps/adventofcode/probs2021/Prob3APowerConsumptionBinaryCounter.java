/*
 * Id: Prob3APowerConsumptionBinaryCounter.java 01-Dec-2021 10:33:11 am SubhajoyLaskar
 * Copyright (©) 2021 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.japps.adventofcode.probs2021;

import java.io.IOException;
import java.util.List;

import com.japps.adventofcode.util.AbstractSolvable;
import com.japps.adventofcode.util.Loggable;


/**
 * The prob 3 A life support counter.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public final class Prob3APowerConsumptionBinaryCounter extends AbstractSolvable implements Loggable {

    /** The instance. */
    private static final Prob3APowerConsumptionBinaryCounter INSTANCE = instance();

    /**
     * Instantiates a new prob 3 A power consumption binary counter.
     */
    private Prob3APowerConsumptionBinaryCounter() {

    }

    /**
     * Instance.
     *
     * @return the prob 3 A power consumption binary counter
     */
    private static Prob3APowerConsumptionBinaryCounter instance() {

        return new Prob3APowerConsumptionBinaryCounter();
    }

    /**
     * The main method.
     *
     * @param args the arguments
     */
    public static void main(final String[] args) {

        try {
            INSTANCE.info(INSTANCE.findBinaryPowerConsumption());
        } catch (final IOException exception) {
            INSTANCE.error(exception.getLocalizedMessage());
        }
    }

    /**
     * Finds the binary power consumption.
     *
     * @return the binary power consumption
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private long findBinaryPowerConsumption() throws IOException {

        final StringBuilder gammaStringBuilder = new StringBuilder();

        final List<String> lines = lines();

        final int perLineLength = lines.get(0).length();
        for (int i = 0; i < perLineLength; i++) {
            int zeroCount = 0;
            int oneCount = 0;

            for (final String line : lines) {
                if (line.charAt(i) == '0') {
                    zeroCount++;
                } else {
                    oneCount++;
                }
            }
            gammaStringBuilder.append((zeroCount > oneCount) ? 0 : 1);
        }
        final long gamma = decimalize(gammaStringBuilder.toString());
        final long epsilon = ((long) (Math.pow(2, perLineLength) - 1) - gamma);

        return gamma * epsilon;
    }
}
