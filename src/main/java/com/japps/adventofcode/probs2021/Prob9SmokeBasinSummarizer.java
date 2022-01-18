/*
 * Id: Prob9SmokeBasinSummarizer.java 09-Dec-2021 10:30:34 am SubhajoyLaskar
 * Copyright (©) 2021 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.japps.adventofcode.probs2021;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.japps.adventofcode.util.AbstractSolvable;
import com.japps.adventofcode.util.Loggable;
import com.japps.adventofcode.util.Pair;


/**
 * The prob 9 smoke basin summarizer.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public final class Prob9SmokeBasinSummarizer extends AbstractSolvable implements Loggable {

    /** The instance. */
    private static final Prob9SmokeBasinSummarizer INSTANCE = instance();

    /**
     * Instantiates a new prob 9 smoke basin summarizer.
     */
    private Prob9SmokeBasinSummarizer() {

    }

    /**
     * Instance.
     *
     * @return the prob 9 smoke basin summarizer
     */
    private static Prob9SmokeBasinSummarizer instance() {

        return new Prob9SmokeBasinSummarizer();
    }

    /**
     * The main method.
     *
     * @param args the arguments
     */
    public static void main(final String[] args) {

        try {
            INSTANCE.compute();
        } catch (final IOException exception) {
            INSTANCE.error(exception.getLocalizedMessage());
        }
    }

    /**
     * Finds the.
     *
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private void compute() throws IOException {

        final List<String> lines = lines();

        final int numberOfRows = lines.size();
        final int numberOfColumns = lines.get(0).length();

        final int[][] points = new int[numberOfRows][numberOfColumns];

        for (int i = 0; i < numberOfRows; i++) {
            for (int j = 0; j < numberOfColumns; j++) {
                points[i][j] = Integer.parseInt(lines.get(i).charAt(j) + "");
            }
        }

        info(computeSumOfLowers(points, numberOfRows, numberOfColumns));

        info(computeBasinSizes(points, numberOfRows, numberOfColumns));
    }

    /**
     * Compute basin sizes.
     *
     * @param points the points
     * @param numberOfRows the number of rows
     * @param numberOfColumns the number of columns
     * @return the basin sizes
     */
    private int computeBasinSizes(final int[][] points, final int numberOfRows, final int numberOfColumns) {

        final List<Integer> basinSizes = new ArrayList<>();

        final Set<Pair> indexSet = new HashSet<>();

        final int[] rowDiffs = new int[] {-1, 0, 1, 0};
        final int[] colDiffs = new int[] {0, 1, 0, -1};

        for (int rowIndex = 0, row; rowIndex < numberOfRows; rowIndex++) {
            row = rowIndex;
            for (int colIndex = 0, col; colIndex < numberOfColumns; colIndex++) {
                col = colIndex;
                final Pair index = new Pair(row, col);
                if (!indexSet.contains(index)
                    && points[row][col] != 9) {
                    int size = 0;
                    final Deque<Pair> indexQueue = new ArrayDeque<>();
                    indexQueue.add(index);
                    while (!indexQueue.isEmpty()) {
                        final Pair indexedPair = indexQueue.pop();
                        row = indexedPair.getX();
                        col = indexedPair.getY();
                        if (indexSet.contains(indexedPair)) {
                            continue;
                        }
                        indexSet.add(indexedPair);
                        ++size;
                        for (int k = 0; k < 4; k++) {
                            final int checkRow = row + rowDiffs[k];
                            final int checkCol = col + colDiffs[k];
                            if (0 <= checkRow
                                && checkRow < numberOfRows
                                && 0 <= checkCol && checkCol < numberOfColumns
                                && points[checkRow][checkCol] != 9) {
                                indexQueue.add(new Pair(checkRow, checkCol));
                            }
                        }
                    }
                    basinSizes.add(size);
                }
            }
        }
        Collections.sort(basinSizes, (x, y) -> -Integer.compare(x, y));

        return basinSizes.get(0) * basinSizes.get(1) * basinSizes.get(2);
    }

    /**
     * Compute sum of lowers.
     *
     * @param points the points
     * @param numberOfRows the number of rows
     * @param numberOfColumns the number of columns
     * @return the long
     */
    private long computeSumOfLowers(final int[][] points, final int numberOfRows, final int numberOfColumns) {

        long sum = 0;
        for (int row = 0; row < numberOfRows; row++) {
            for (int col = 0; col < numberOfColumns; col++) {
                if (!isNotLower(points, numberOfRows, numberOfColumns, row, col)) {
                    sum += points[row][col] + 1;
                }
            }
        }

        return sum;
    }

    /**
     * Indicates if is not lower.
     *
     * @param points the points
     * @param numberOfRows the number of rows
     * @param numberOfColumns the number of columns
     * @param row the row
     * @param col the col
     * @return {@code true}, if is not lower
     */
    private boolean isNotLower(final int[][] points, final int numberOfRows, final int numberOfColumns, final int row,
            final int col) {

        return col - 1 >= 0 && points[row][col] >= points[row][col - 1]
            || col + 1 < numberOfColumns && points[row][col] >= points[row][col + 1]
            || row - 1 >= 0 && points[row][col] >= points[row - 1][col]
            || row + 1 < numberOfRows && points[row][col] >= points[row + 1][col];
    }
}