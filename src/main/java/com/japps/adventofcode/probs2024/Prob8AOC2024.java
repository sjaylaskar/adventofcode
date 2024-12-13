/*
 * Copyright (©) 2024 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.japps.adventofcode.probs2024;

import com.japps.adventofcode.util.AbstractSolvable;
import com.japps.adventofcode.util.IntPair;
import com.japps.adventofcode.util.Loggable;

import java.io.IOException;
import java.util.*;
import java.util.stream.IntStream;

import static com.japps.adventofcode.util.ProblemSolverUtil.cols;
import static com.japps.adventofcode.util.ProblemSolverUtil.rows;

public final class Prob8AOC2024 extends AbstractSolvable implements Loggable {

    private static final Prob8AOC2024 INSTANCE = instance();
    public static final char EMPTY_POS = '.';

    private Prob8AOC2024() {

    }

    private static Prob8AOC2024 instance() {

        return new Prob8AOC2024();
    }

    public static void main(final String[] args) {

        try {
            INSTANCE.compute();
        } catch (final IOException exception) {
            INSTANCE.error(exception.getLocalizedMessage());
        }
    }

    private void compute() throws IOException {
        char[][] positions = linesAsArray();

        int rows = rows(positions);
        int cols = cols(positions, 0);
        Map<Character, List<IntPair>> coordinatesPerCharacter = new HashMap<>();
        IntStream.range(0, rows)
                .forEach(row -> IntStream.range(0, cols)
                .filter(col -> positions[row][col] != EMPTY_POS)
                .forEach(col -> coordinatesPerCharacter.computeIfAbsent(positions[row][col], _ -> new ArrayList<>()).add(IntPair.of(row, col))));

        Set<IntPair> antiNodeCoordinates = new HashSet<>();
        Set<IntPair> updatedAntiNodeCoordinates = new HashSet<>();
        IntStream.range(0, rows).forEach(row -> IntStream.range(0, cols).forEach(col -> coordinatesPerCharacter.values().forEach(charCoordinates ->
                charCoordinates.forEach(charCoordinate1 -> charCoordinates.stream().filter(charCoordinate2 -> !charCoordinate1.equals(charCoordinate2))
                .forEach(charCoordinate2 -> addAntiNodeCoordinates(row, col, charCoordinate1, charCoordinate2, antiNodeCoordinates, updatedAntiNodeCoordinates))))));
        println(antiNodeCoordinates.size());
        println(updatedAntiNodeCoordinates.size());
    }

    private static void addAntiNodeCoordinates(
            int row, int col, IntPair charCoordinate1, IntPair charCoordinate2, Set<IntPair> antiNodeCoordinates, Set<IntPair> updatedAntiNodeCoordinates) {
        if (isValidLine(IntPair.of(row - charCoordinate1.getX(),  col - charCoordinate1.getY()), IntPair.of(row - charCoordinate2.getX(), col - charCoordinate2.getY()))) {
            if (isValidAntennaDistance(antennaDistance(row, col, charCoordinate1), antennaDistance(row, col, charCoordinate2))) {
                antiNodeCoordinates.add(IntPair.of(row, col));
            }
            updatedAntiNodeCoordinates.add(IntPair.of(row, col));
        }
    }

    private static int antennaDistance(int row, int col, IntPair charCoordinate) {
        return Math.abs(row - charCoordinate.getX()) + Math.abs(col - charCoordinate.getY());
    }

    private static boolean isValidAntennaDistance(int antennaDistance1, int antennaDistance2) {
        return antennaDistance1 == 2 * antennaDistance2 || antennaDistance2 == 2 * antennaDistance1;
    }

    private static boolean isValidLine(IntPair antennaCoordinate1, IntPair antennaCoordinate2) {
        return antennaCoordinate1.getX() * antennaCoordinate2.getY() == antennaCoordinate1.getY() * antennaCoordinate2.getX();
    }
}
