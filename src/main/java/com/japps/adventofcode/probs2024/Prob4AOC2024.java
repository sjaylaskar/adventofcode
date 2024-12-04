/*
 * Copyright (©) 2024 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.japps.adventofcode.probs2024;

import java.io.*;
import java.util.*;

import com.japps.adventofcode.util.*;

public final class Prob4AOC2024 extends AbstractSolvable implements Loggable {

    private static final Prob4AOC2024 INSTANCE = instance();

    private Prob4AOC2024() {

    }

    private static Prob4AOC2024 instance() {

        return new Prob4AOC2024();
    }

    public static void main(final String[] args) {

        try {
            INSTANCE.compute();
        } catch (final IOException exception) {
            INSTANCE.error(exception.getLocalizedMessage());
        }
    }

    private static final List<List<IntPair>> XMAS_COORDINATE_DIFFS =
            List.of(
                    List.of(IntPair.of(0, 1), IntPair.of(0, 2), IntPair.of(0, 3)),
                    List.of(IntPair.of(0, -1), IntPair.of(0, -2), IntPair.of(0, -3)),
                    List.of(IntPair.of(1, 0), IntPair.of(2, 0), IntPair.of(3, 0)),
                    List.of(IntPair.of(-1, 0), IntPair.of(-2, 0), IntPair.of(-3, 0)),
                    List.of(IntPair.of(1, 1), IntPair.of(2, 2), IntPair.of(3, 3)),
                    List.of(IntPair.of(1, -1), IntPair.of(2, -2), IntPair.of(3, -3)),
                    List.of(IntPair.of(-1, 1), IntPair.of(-2, 2), IntPair.of(-3, 3)),
                    List.of(IntPair.of(-1, -1), IntPair.of(-2, -2), IntPair.of(-3, -3)));

    private static final List<IntPair> MS_COORDINATE_DIFFS =
            List.of(IntPair.of(-1, -1), IntPair.of(1, 1), IntPair.of(-1, 1), IntPair.of(1, -1));

    private void compute() throws IOException {
		List<String> lines = lines();
        List<char[]> linesArrayList = lines.stream().map(String::toCharArray).toList();
        char[][] linesArray = linesArrayList.toArray(new char[0][0]);
        computeXmasCount(linesArray);
        computeMasAsXCount(linesArray);
    }

    private void computeXmasCount(char[][] linesArray) {
        long xmasCount = 0;
        for (int i = 0; i < linesArray.length; i++) {
            for (int j = 0; j < linesArray[i].length; j++) {
                if (linesArray[i][j] == 'X') {
                    int xCoord = i;
                    int yCoord = j;
                    xmasCount += XMAS_COORDINATE_DIFFS.stream().filter(xcd -> isValidXMAS(linesArray, coordinatesFromDiffs(xCoord, yCoord, xcd))).count();
                }
            }
        }
        println(xmasCount);
    }

    private boolean isValidXMAS(char[][] linesArray, List<IntPair> masCoordinates) {
        if (masCoordinates.stream().anyMatch(c -> c.getX() < 0 || c.getX() > linesArray.length - 1 || c.getY() < 0 || c.getY() > linesArray[0].length - 1)) {
            return false;
        }
        return (linesArray[masCoordinates.get(0).getX()][masCoordinates.get(0).getY()] == 'M'
                && linesArray[masCoordinates.get(1).getX()][masCoordinates.get(1).getY()] == 'A'
                && linesArray[masCoordinates.get(2).getX()][masCoordinates.get(2).getY()] == 'S');
    }

    private void computeMasAsXCount(char[][] linesArray) {
        long masAsXCount = 0;
        for (int i = 0; i < linesArray.length; i++) {
            for (int j = 0; j < linesArray[i].length; j++) {
                if (linesArray[i][j] == 'A') {
                    if (isValidMASAsX(linesArray, coordinatesFromDiffs(i, j, MS_COORDINATE_DIFFS))) {
                        ++masAsXCount;
                    }
                }
            }
        }
        println(masAsXCount);
    }

    private boolean isValidMASAsX(char[][] linesArray, List<IntPair> msCoordinates) {
        if (msCoordinates.stream().anyMatch(c -> c.getX() < 0 || c.getX() > linesArray.length - 1 || c.getY() < 0 || c.getY() > linesArray[0].length - 1)) {
            return false;
        }
        return ((linesArray[msCoordinates.get(0).getX()][msCoordinates.get(0).getY()] == 'M' && linesArray[msCoordinates.get(1).getX()][msCoordinates.get(1).getY()] == 'S')
                || (linesArray[msCoordinates.get(0).getX()][msCoordinates.get(0).getY()] == 'S' && linesArray[msCoordinates.get(1).getX()][msCoordinates.get(1).getY()] == 'M'))
                && ((linesArray[msCoordinates.get(2).getX()][msCoordinates.get(2).getY()] == 'M' && linesArray[msCoordinates.get(3).getX()][msCoordinates.get(3).getY()] == 'S')
                || (linesArray[msCoordinates.get(2).getX()][msCoordinates.get(2).getY()] == 'S' && linesArray[msCoordinates.get(3).getX()][msCoordinates.get(3).getY()] == 'M'));
    }

    private List<IntPair> coordinatesFromDiffs(int xCoord, int yCoord, List<IntPair> xcd) {
        return xcd.stream().map(coordDiff -> IntPair.of(xCoord + coordDiff.getX(), yCoord + coordDiff.getY())).toList();
    }
}
