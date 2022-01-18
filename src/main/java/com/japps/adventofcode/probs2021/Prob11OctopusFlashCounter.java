/*
 * Id: Prob11OctopusFlashCounter.java 11-Dec-2021 10:36:47 am SubhajoyLaskar
 * Copyright (©) 2021 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.japps.adventofcode.probs2021;

import java.io.IOException;
import java.util.List;

import com.japps.adventofcode.util.AbstractSolvable;
import com.japps.adventofcode.util.Loggable;
import com.japps.adventofcode.util.StringUtil;


/**
 * The prob 11 octopus flash counter.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public final class Prob11OctopusFlashCounter extends AbstractSolvable implements Loggable {

    /** The instance. */
    private static final Prob11OctopusFlashCounter INSTANCE = instance();

    /** The flash count. */
    private int flashCount;

    /** The moves. */
    private static final int[] MOVES = {-1, 0, 1};

    /**
     * Instantiates a new prob 11 octopus flash counter.
     */
    private Prob11OctopusFlashCounter() {

    }

    /**
     * Instance.
     *
     * @return the prob 11 octopus flash counter
     */
    private static Prob11OctopusFlashCounter instance() {

        return new Prob11OctopusFlashCounter();
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
     * Compute.
     *
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private void compute() throws IOException {

        final List<String> lines = lines();

        final int noOfRows = lines.size();
        final int noOfCols = lines.get(0).length();

        final int[][] matrix = new int[noOfRows][noOfCols];

        for (int row = 0; row < noOfRows; row++) {
            final String line = lines.get(row);
            for (int col = 0; col < noOfCols; col++) {
                matrix[row][col] = StringUtil.toStrictInt(line.charAt(col));
            }
        }

        performFlashComputations(matrix, noOfRows, noOfCols);
    }

    /**
     * Perform flash computations.
     *
     * @param matrix the matrix
     * @param noOfRows the no of rows
     * @param noOfCols the no of cols
     */
    private void performFlashComputations(final int[][] matrix, final int noOfRows, final int noOfCols) {

        int stepCount = 0;

        while (true) {
            stepCount++;

            flashIncrement(matrix, noOfRows, noOfCols);

            performFlash(matrix, noOfRows, noOfCols);

            final boolean isFlashedAll = isFlashedAll(matrix, noOfRows, noOfCols);

            if (stepCount == 100) {
                info("Number of flashes at 100th step: " + flashCount);
            }

            if (isFlashedAll) {
                info("First step where all flashed: " + stepCount);
                break;
            }
        }
    }

    /**
     * Checks if is flashed all.
     *
     * @param matrix the matrix
     * @param noOfRows the no of rows
     * @param noOfCols the no of cols
     * @return true, if is flashed all
     */
    private boolean isFlashedAll(final int[][] matrix, final int noOfRows, final int noOfCols) {

        boolean isFlashedAll = true;

        for (int row = 0; row < noOfRows; row++) {
            for (int col = 0; col < noOfCols; col++) {
                if (matrix[row][col] == -1) {
                    matrix[row][col] = 0;
                } else {
                    isFlashedAll = false;
                }
            }
        }
        return isFlashedAll;
    }

    /**
     * Perform flash.
     *
     * @param matrix the matrix
     * @param noOfRows the no of rows
     * @param noOfCols the no of cols
     */
    private void performFlash(final int[][] matrix, final int noOfRows, final int noOfCols) {

        for (int row = 0; row < noOfRows; row++) {
            for (int col = 0; col < noOfCols; col++) {
                if (matrix[row][col] == 10) {
                    performFlash(matrix, row, col, noOfRows, noOfCols);
                }
            }
        }
    }

    /**
     * Flash increment.
     *
     * @param matrix the matrix
     * @param noOfRows the no of rows
     * @param noOfCols the no of cols
     */
    private void flashIncrement(final int[][] matrix, final int noOfRows, final int noOfCols) {

        for (int row = 0; row < noOfRows; row++) {
            for (int col = 0; col < noOfCols; col++) {
                matrix[row][col]++;
            }
        }
    }

    /**
     * Perform flash.
     *
     * @param matrix the matrix
     * @param row the row
     * @param col the col
     * @param noOfRows the no of rows
     * @param noOfCols the no of cols
     */
    private void performFlash(final int[][] matrix, final int row, final int col, final int noOfRows, final int noOfCols) {

        flashCount++;

        matrix[row][col] = -1;

        for (final int moveRow : MOVES) {
            for (final int moveCol : MOVES) {
                final int checkRow = row + moveRow;
                final int checkCol = col + moveCol;
                if (isValidForIncrement(matrix, noOfRows, noOfCols, checkRow, checkCol)) {
                    matrix[checkRow][checkCol]++;
                    if (matrix[checkRow][checkCol] >= 10) {
                        performFlash(matrix, checkRow, checkCol, noOfRows, noOfCols);
                    }
                }
            }
        }
    }

    /**
     * Checks if is valid for increment.
     *
     * @param matrix the matrix
     * @param noOfRows the no of rows
     * @param noOfCols the no of cols
     * @param checkRow the check row
     * @param checkCol the check col
     * @return true, if is valid for increment
     */
    private boolean isValidForIncrement(final int[][] matrix, final int noOfRows, final int noOfCols, final int checkRow,
            final int checkCol) {

        return 0 <= checkRow
            && checkRow < noOfRows
            && 0 <= checkCol && checkCol < noOfCols
            && matrix[checkRow][checkCol] != -1;
    }
}
