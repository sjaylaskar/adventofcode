/*
* Id: Prob2BSubmarineDepthFinderWithAim.java 01-Dec-2021 10:33:11 am SubhajoyLaskar
* Copyright (©) 2021 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.japps.adventofcode.probs2021;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import com.japps.adventofcode.util.AbstractSolvable;
import com.japps.adventofcode.util.Loggable;

/**
 * The prob 2 B submarine depth finder with aim.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public final class Prob2BSubmarineDepthFinderWithAim extends AbstractSolvable implements Loggable {

    /** The space. */
    private static final String SPACE = " ";

    /** The up. */
    private static final String UP = "up";

    /** The down. */
    private static final String DOWN = "down";

    /** The forward. */
    private static final String FORWARD = "forward";
    /** The instance. */
    private static final Prob2BSubmarineDepthFinderWithAim INSTANCE = instance();

    /**
     * Instantiates a new prob 2 B submarine depth finder with aim.
     */
    private Prob2BSubmarineDepthFinderWithAim() {

    }

    /**
     * Instance.
     *
     * @return the prob 2 B submarine depth finder with aim
     */
    private static Prob2BSubmarineDepthFinderWithAim instance() {
        return new Prob2BSubmarineDepthFinderWithAim();
    }

    /**
     * The main method.
     *
     * @param args the arguments
     */
    public static void main(final String[] args) {
        try {
            INSTANCE.info(INSTANCE.findProductOfHorizontalAndVerticalDepth());
        } catch (final IOException exception) {
            INSTANCE.error(exception.getLocalizedMessage());
        }
    }

    /**
     * Finds the product of horizontal and vertical depth.
     *
     * @return the product of horizontal and vertical depth
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private long findProductOfHorizontalAndVerticalDepth() throws IOException {

        final List<String> lines = Files.lines(Paths.get(determineInputFilePath()))
            .collect(Collectors.toList());

        long horizontal = 0;
        long depth = 0;
        long aim = 0;
        for (final String line : lines) {
            final String[] actions = line.split(SPACE);
            final String action = actions[0];
            final long actionMove = Long.parseLong(actions[1]);
            if (FORWARD.equals(action)) {
                horizontal += actionMove;
                depth += (actionMove * aim);
            } else if (DOWN.equals(action)) {
                aim += actionMove;
            } else if (UP.equals(action)){
                aim -= actionMove;
            }
        }

        return horizontal * depth;
    }
}
