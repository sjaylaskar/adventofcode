/*
 * Id: Prob3ALifeSupportCounter.java 01-Dec-2021 10:33:11 am SubhajoyLaskar
 * Copyright (©) 2021 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.japps.adventofcode.probs2021;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.japps.adventofcode.util.AbstractSolvable;
import com.japps.adventofcode.util.Loggable;


/**
 * The prob 3 A life support counter.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public final class Prob3BLifeSupportCounterByBits extends AbstractSolvable implements Loggable {

    /** The instance. */
    private static final Prob3BLifeSupportCounterByBits INSTANCE = instance();

    /**
     * Instantiates a new prob 3 A life support counter.
     */
    private Prob3BLifeSupportCounterByBits() {

    }

    /**
     * Instance.
     *
     * @return the prob 3 A life support counter
     */
    private static Prob3BLifeSupportCounterByBits instance() {

        return new Prob3BLifeSupportCounterByBits();
    }

    /**
     * The main method.
     *
     * @param args the arguments
     */
    public static void main(final String[] args) {

        try {
            INSTANCE.info(INSTANCE.findBinaryLifeSupportRating());
        } catch (final IOException exception) {
            INSTANCE.error(exception.getLocalizedMessage());
        }
    }

    /**
     * Finds the binary life support rating.
     *
     * @return the binary life support rating
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private long findBinaryLifeSupportRating() throws IOException {

        final List<String> lines = lines();

        List<String> oxygenGenCountLines = new ArrayList<>(lines);
        List<String> co2ScrubberCountLines = new ArrayList<>(lines);

        final int perLineLength = lines.get(0).length();

        for (int i = 0; i < perLineLength; i++) {
            oxygenGenCountLines = calculateO2GenCount(oxygenGenCountLines, i);

            co2ScrubberCountLines = calculateCO2ScrubberCount(co2ScrubberCountLines, i);
        }

        return decimalize(oxygenGenCountLines.get(0))
               * decimalize(co2ScrubberCountLines.get(0));
    }

    /**
     * Calculates the CO 2 scrubber count.
     *
     * @param co2ScrubberCountLines the co 2 scrubber count lines
     * @param lineIndex the line index
     * @return the CO 2 scrubber count
     */
    private List<String> calculateCO2ScrubberCount(final List<String> co2ScrubberCountLines, final int lineIndex) {

        if (co2ScrubberCountLines.size() != 1) {
            int zeroCount = 0;
            int oneCount = 0;

            for (final String line : co2ScrubberCountLines) {
                if (line.charAt(lineIndex) == '0') {
                    zeroCount++;
                } else {
                    oneCount++;
                }
            }
            final char leastBit = (zeroCount > oneCount) ? '1' : '0';
            final int index = lineIndex;
            return co2ScrubberCountLines
                    .stream()
                    .filter(line -> line.charAt(index) == leastBit)
                    .collect(Collectors.toList());
        }

        return co2ScrubberCountLines;
    }

    /**
     * Calculates the o 2 gen count.
     *
     * @param oxygenGenCountLines the oxygen gen count lines
     * @param lineIndex the line index
     * @return the o 2 gen count
     */
    private List<String> calculateO2GenCount(final List<String> oxygenGenCountLines, final int lineIndex) {

        if (oxygenGenCountLines.size() != 1) {
            int zeroCount = 0;
            int oneCount = 0;

            for (final String line : oxygenGenCountLines) {
                if (line.charAt(lineIndex) == '0') {
                    zeroCount++;
                } else {
                    oneCount++;
                }
            }
            final char mostBit = (zeroCount > oneCount) ? '0' : '1';
            final int index = lineIndex;
            return oxygenGenCountLines
                .stream()
                .filter(line -> line.charAt(index) == mostBit)
                .collect(Collectors.toList());
        }

        return oxygenGenCountLines;
    }
}
