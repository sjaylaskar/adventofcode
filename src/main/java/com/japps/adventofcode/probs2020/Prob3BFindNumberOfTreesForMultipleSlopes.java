/*
 * Id: Prob3BFindNumberOfTreesForMultipleSlopes.java 30-Nov-2021 1:52:54 am SubhajoyLaskar
 * Copyright (©) 2021 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.japps.adventofcode.probs2020;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.japps.adventofcode.util.AbstractSolvable;
import com.japps.adventofcode.util.Loggable;


/**
 * The prob 3 B find number of trees for multiple slopes.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public final class Prob3BFindNumberOfTreesForMultipleSlopes extends AbstractSolvable implements Loggable {

    /** The instance. */
    private static final Prob3BFindNumberOfTreesForMultipleSlopes INSTANCE = instance();

    /**
     * Instantiates a new prob 3 B find number of trees for multiple slopes.
     */
    private Prob3BFindNumberOfTreesForMultipleSlopes() {

    }

    /**
     * Instance.
     *
     * @return the prob 3 B find number of trees for multiple slopes
     */
    private static Prob3BFindNumberOfTreesForMultipleSlopes instance() {
        return new Prob3BFindNumberOfTreesForMultipleSlopes();
    }

    /**
     * The main method.
     *
     * @param args the arguments
     */
    public static void main(final String[] args) {
        try {
            INSTANCE.info(INSTANCE.findNumberOfTrees());
        } catch (final IOException exception) {
            INSTANCE.error(exception.getLocalizedMessage());
        }
    }

    /**
     * Finds the number of trees.
     *
     * @return the number of trees
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private long findNumberOfTrees() throws IOException {

        return countTrees(lines());
    }

    /**
     * Counts the trees.
     *
     * @param lines the lines
     * @return the count of trees
     */
    private long countTrees(final List<String> lines) {
        final List<Slope> slopes = new ArrayList<>(5);
        slopes.add(new Slope(1, 1));
        slopes.add(new Slope(3, 1));
        slopes.add(new Slope(5, 1));
        slopes.add(new Slope(7, 1));
        slopes.add(new Slope(1, 2));
        long productOfTreeCounts = 1;
        for (final Slope slope : slopes) {
            long treeCount = 0;
            for (int i = slope.down; i < lines.size(); i += slope.down) {
                final int indexToCheckForTree = ((i / slope.down) * slope.right) % lines.get(i).length();
                if (lines.get(i).charAt(indexToCheckForTree) == '#') {
                    ++treeCount;
                }
            }
            productOfTreeCounts *= treeCount;
        }
        return productOfTreeCounts;
    }

    /**
     * The slope.
     *
     * @author Subhajoy Laskar
     * @version 1.0
     */
    private static final class Slope {

        /** The right. */
        private final int right;

        /** The down. */
        private final int down;

        /**
         * {@inheritDoc}
         */
        @Override
        public int hashCode() {

            return Objects.hash(down, right);
        }



        /**
         * {@inheritDoc}
         */
        @Override
        public boolean equals(final Object obj) {

            if (this == obj) return true;
            if (obj == null) return false;
            if (getClass() != obj.getClass()) return false;
            final Slope other = (Slope) obj;
            return down == other.down && right == other.right;
        }



        /**
         * Instantiates a new slope.
         *
         * @param right the right
         * @param down the down
         */
        Slope(final int right, final int down) {
            this.right = right;
            this.down = down;
        }
    }
}
