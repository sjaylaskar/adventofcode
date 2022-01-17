/*
* Id: Prob15MinimumCostPathCalculatorDijkstras.java 15-Dec-2021 10:30:09 am SubhajoyLaskar
* Copyright (©) 2021 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.japps.adventofcode.probs2021;

import java.io.IOException;
import java.util.List;
import java.util.PriorityQueue;

import com.japps.adventofcode.util.AbstractSolvable;
import com.japps.adventofcode.util.Loggable;


/**
 * The prob 15 minimum cost path calculator dijkstras.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public final class Prob15MinimumCostPathCalculatorDijkstras extends AbstractSolvable implements Loggable {

    /** The instance. */
    private static final Prob15MinimumCostPathCalculatorDijkstras INSTANCE = instance();

    /**
     * Instantiates a new prob 15 minimum cost path calculator dijkstras.
     */
    private Prob15MinimumCostPathCalculatorDijkstras() {

    }

    /**
     * Instance.
     *
     * @return the prob 15 minimum cost path calculator dijkstras
     */
    private static Prob15MinimumCostPathCalculatorDijkstras instance() {

        return new Prob15MinimumCostPathCalculatorDijkstras();
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

        final int rows = lines.size();
        final int cols = lines.get(0).length();

        final int[][] cost = new int[rows][cols];
        for (int i = 0; i < rows; i++) {
            final String line = lines.get(i);
            for (int j = 0; j < cols; j++) {
                cost[i][j] = Integer.parseInt(String.valueOf(line.charAt(j)));
            }
        }

        info(DijkstrasMinCostCalculator.calculateMinCost(cost, rows, cols, rows, cols));

        final int[][] repeatedCost = repeatMatrix(cost, 5, 5);
        final int repeatRows = repeatedCost.length;
        final int repeatCols = repeatedCost[0].length;

        info(DijkstrasMinCostCalculator.calculateMinCost(repeatedCost, repeatRows, repeatCols, repeatRows, repeatCols));

    }

    /**
     * Repeat matrix.
     *
     * @param matrix the matrix
     * @param rowRepeatTimes the row repeat times
     * @param colRepeatTimes the col repeat times
     * @return the repeated matrix
     */
    private int[][] repeatMatrix(final int[][] matrix, final int rowRepeatTimes, final int colRepeatTimes) {
        final int rows = matrix.length;
        final int cols = matrix[0].length;
        final int repeatRows = rows * rowRepeatTimes;
        final int repeatCols = cols * colRepeatTimes;
        final int[][] repeatedMatrix = new int[repeatRows][repeatCols];

        for (int i = 0; i < repeatRows; i++) {
            for (int j = 0; j < repeatCols; j++) {
                if (i < rows && j < cols) {
                    repeatedMatrix[i][j] = matrix[i][j];
                } else if (j >= cols) {
                    repeatedMatrix[i][j] = (repeatedMatrix[i][j - cols] == 9)
                        ? 1
                        : repeatedMatrix[i][j - cols] + 1;
                } else if (i >= rows) {
                    repeatedMatrix[i][j] = (repeatedMatrix[i - rows][j] == 9)
                        ? 1
                        : repeatedMatrix[i - rows][j] + 1;
                }
            }
        }
        return repeatedMatrix;
    }

    /**
     * The dijkstras min cost calculator.
     *
     * @author Subhajoy Laskar
     * @version 1.0
     */
    private static final class DijkstrasMinCostCalculator {

        /** The dx = moves in x axis. */
        private static final int dx[] = {-1, 0, 1, 0};

        /** The dy = moves in y axis. */
        private static final int dy[] = {0, 1, 0, -1};

        /**
         * The cell.
         *
         * @author Subhajoy Laskar
         * @version 1.0
         */
        private static class Cell {

            /** The x coordinate. */
            private final int x;

            /** The y coordinate. */
            private final int y;

            /** The cost. */
            private final int cost;

            /**
             * Instantiates a new cell.
             *
             * @param x the x
             * @param y the y
             * @param cost the cost
             */
            Cell(final int x, final int y, final int cost) {

                this.x = x;
                this.y = y;
                this.cost = cost;
            }
        }

        /**
         * Indicates if is in safe range.
         *
         * @param x the x
         * @param y the y
         * @param rows the rows
         * @param cols the cols
         * @return {@code true}, if is in safe range
         */
        private static boolean isInSafeRange(final int x, final int y, final int rows, final int cols) {

            return x >= 0 && x < rows && y >= 0 && y < cols;
        }

        /**
         * Calculates the min cost.
         *
         * @param cost the cost
         * @param rows the rows
         * @param cols the cols
         * @param calcCellXCoord the calc cell X coord
         * @param calcCellYCoord the calc cell Y coord
         * @return the min cost
         */
        private static int calculateMinCost(final int[][] cost, final int rows, final int cols, final int calcCellXCoord, final int calcCellYCoord) {

            final int[][] cacheTable = new int[rows][cols];

            final boolean isVisited[][] = new boolean[rows][cols];

            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    cacheTable[i][j] = Integer.MAX_VALUE;
                }
            }

            final PriorityQueue<Cell> priorityQueue = new PriorityQueue<Cell>((c1, c2) -> Integer.compare(c1.cost, c2.cost));

            cacheTable[0][0] = cost[0][0];
            priorityQueue.add(new Cell(0, 0, cost[0][0]));

            while (!priorityQueue.isEmpty()) {

                final Cell cell = priorityQueue.poll();
                final int x = cell.x;
                final int y = cell.y;
                if (isVisited[x][y]) {
                    continue;
                }

                isVisited[x][y] = true;

                for (int i = 0; i < 4; i++) {
                    final int movedX = x + dx[i];
                    final int movedY = y + dy[i];
                    if (isInSafeRange(movedX, movedY, rows, cols) && !isVisited[movedX][movedY]) {
                        cacheTable[movedX][movedY] = Math.min(cacheTable[movedX][movedY],
                            cacheTable[x][y] + cost[movedX][movedY]);
                        priorityQueue.add(new Cell(movedX, movedY, cacheTable[movedX][movedY]));
                    }
                }
            }

            return cacheTable[calcCellXCoord - 1][calcCellYCoord - 1] - cost[0][0];
        }
    }
}