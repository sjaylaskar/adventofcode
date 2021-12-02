/*
* Id: Prob2ASubmarineDepthFinder.java 01-Dec-2021 10:33:11 am SubhajoyLaskar
* Copyright (©) 2021 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.japps.adventofcode.probs2021;

import java.io.IOException;

import com.japps.adventofcode.util.AbstractSolvable;
import com.japps.adventofcode.util.Loggable;

/**
 * The prob 2 A submarine depth finder.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public final class Prob2ASubmarineDepthFinder extends AbstractSolvable implements Loggable {

    /** The space. */
    private static final String SPACE = " ";

    /** The up. */
    private static final String UP = "up";

    /** The down. */
    private static final String DOWN = "down";

    /** The forward. */
    private static final String FORWARD = "forward";
    /** The instance. */
    private static final Prob2ASubmarineDepthFinder INSTANCE = instance();

    /**
     * Instantiates a new prob 2 A submarine depth finder.
     */
    private Prob2ASubmarineDepthFinder() {

    }

    /**
     * Instance.
     *
     * @return the prob 2 A submarine depth finder
     */
    private static Prob2ASubmarineDepthFinder instance() {
        return new Prob2ASubmarineDepthFinder();
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

        long horizontal = 0;
        long depth = 0;
        for (final String line : lines()) {
            final String[] actions = line.split(SPACE);
            final String action = actions[0];
            final long actionMove = Long.parseLong(actions[1]);
            if (FORWARD.equals(action)) {
                horizontal += actionMove;
            } else if (DOWN.equals(action)) {
                depth += actionMove;
            } else if (UP.equals(action)){
                depth -= actionMove;
            }
        }

        return horizontal * depth;
    }
}
