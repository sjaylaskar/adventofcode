/*
 * Id: Prob3AFindNumberOfTrees.java 30-Nov-2021 1:52:54 am SubhajoyLaskar
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
 * The prob 3 A find number of trees.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public final class Prob3AFindNumberOfTrees extends AbstractSolvable implements Loggable {

    /** The instance. */
    private static final Prob3AFindNumberOfTrees INSTANCE = instance();

    /**
     * Instantiates a new prob 3 A find number of trees.
     */
    private Prob3AFindNumberOfTrees() {

    }

    /**
     * Instance.
     *
     * @return the prob 3 A find number of trees
     */
    private static Prob3AFindNumberOfTrees instance() {
        return new Prob3AFindNumberOfTrees();
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

        return countTrees(Files.lines(Paths.get(determineInputFilePath())).collect(Collectors.toList()));
    }

    /**
     * Count trees.
     *
     * @param lines the lines
     * @return the count of trees
     */
    private long countTrees(final List<String> lines) {
        long treeCount = 0;
        int indexToCheckForTree = 0;
        for (int i = 1; i < lines.size(); i++) {
            indexToCheckForTree = (i * 3) % lines.get(i).length();
            if (lines.get(i).charAt(indexToCheckForTree) == '#') {
                ++treeCount;
            }
        }
        return treeCount;
    }
}
