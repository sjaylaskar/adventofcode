/*
 * Id: Prob2SubmarineDepthFinder.java 02-Dec-2021 10:31:11 am SubhajoyLaskar
 * Copyright (©) 2021 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.japps.adventofcode.probs2021;

import java.io.IOException;
import java.util.List;

import com.japps.adventofcode.util.AbstractSolvable;
import com.japps.adventofcode.util.Loggable;


/**
 * The prob 2 submarine depth finder.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public final class Prob2SubmarineDepthFinder extends AbstractSolvable implements Loggable {

    /** The space. */
    private static final String SPACE = " ";

    /** The up. */
    private static final String UP = "up";

    /** The down. */
    private static final String DOWN = "down";

    /** The forward. */
    private static final String FORWARD = "forward";

    /** The instance. */
    private static final Prob2SubmarineDepthFinder INSTANCE = instance();

    /**
     * Instantiates a new prob 2 submarine depth finder.
     */
    private Prob2SubmarineDepthFinder() {

    }

    /**
     * Instance.
     *
     * @return the prob 2 submarine depth finder
     */
    private static Prob2SubmarineDepthFinder instance() {

        return new Prob2SubmarineDepthFinder();
    }

    /**
     * The main method.
     *
     * @param args the arguments
     */
    public static void main(final String[] args) {

        try {
            final List<String> lines = INSTANCE.lines();
            INSTANCE.info(INSTANCE.findProductOfHorizontalAndVerticalDepth(lines));
            INSTANCE.info(INSTANCE.findProductOfHorizontalAndVerticalDepthWithAim(lines));
        } catch (final IOException exception) {
            INSTANCE.error(exception.getLocalizedMessage());
        }
    }

    /**
     * Finds the product of horizontal and vertical depth.
     *
     * @param lines the lines
     * @return the product of horizontal and vertical depth
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private long findProductOfHorizontalAndVerticalDepth(final List<String> lines) throws IOException {

        long horizontal = 0;
        long depth = 0;
        for (final String line : lines) {
            final String[] actions = line.split(SPACE);
            final String action = actions[0];
            final long actionMove = Long.parseLong(actions[1]);
            if (FORWARD.equals(action)) {
                horizontal += actionMove;
            } else if (DOWN.equals(action)) {
                depth += actionMove;
            } else if (UP.equals(action)) {
                depth -= actionMove;
            }
        }

        return horizontal * depth;
    }

    /**
     * Finds the product of horizontal and vertical depth with aim.
     *
     * @param lines the lines
     * @return the product of horizontal and vertical depth with aim.
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private long findProductOfHorizontalAndVerticalDepthWithAim(final List<String> lines) throws IOException {

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
            } else if (UP.equals(action)) {
                aim -= actionMove;
            }
        }

        return horizontal * depth;
    }
}
