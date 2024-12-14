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

import static com.japps.adventofcode.util.ProblemSolverUtil.*;

public final class Prob10AOC2024 extends AbstractSolvable implements Loggable {

    private static final Prob10AOC2024 INSTANCE = instance();

    private Prob10AOC2024() {

    }

    private static Prob10AOC2024 instance() {

        return new Prob10AOC2024();
    }

    public static void main(final String[] args) {

        try {
            INSTANCE.compute();
        } catch (final IOException exception) {
            INSTANCE.error(exception.getLocalizedMessage());
        }
    }

    private void compute() throws IOException {
        int[][] trailMap = linesAsIntArray();
        trailHeadRatingsByCoordinates(trailMap);
    }

    private void trailHeadRatingsByCoordinates(int[][] trailMap) {
        Map<IntPair, Set<IntPair>> trailHeadScoreByCoordinates = new HashMap<>();
        Map<IntPair, Long> trailHeadRatingsByCoordinates = new HashMap<>();
        Set<IntPair> validTrailHeadCoordinates = new HashSet<>();
        for (int i = 0; i < rows(trailMap); i++) {
            for (int j = 0; j < cols(trailMap, i); j++) {
                if (trailMap[i][j] == 0) {
                    IntPair coordinate = IntPair.of(i, j);
                    trailHeadRatingsByCoordinates.computeIfAbsent(
                            coordinate, _ -> computeScore(new TrailHeadScoreParam(1, null, computeValidNeighbors(0, IntPair.of(coordinate), trailMap), trailMap, validTrailHeadCoordinates, IntPair.of(coordinate), trailHeadScoreByCoordinates)));
                }
            }
        }
        println(trailHeadScoreByCoordinates.values().stream().map(Set::size).mapToLong(Long::valueOf).sum());
        println(trailHeadRatingsByCoordinates.values().stream().mapToLong(Long::valueOf).sum());
    }

    private List<IntPair> computeValidNeighbors(int value, IntPair coordinate, int[][] trailMap) {
        return horizontalVerticalNeighborCoordinates(coordinate).filter(coord -> isValidCoordinate(coord, value, trailMap)).toList();
    }

    private boolean isValidCoordinate(IntPair coord, int value, int[][] trailMap) {
        return isInBounds(coord, rows(trailMap), cols(trailMap, 0))
                && (trailMap[coord.getX()][coord.getY()] == value + 1);
    }

    private long computeScore(TrailHeadScoreParam trailHeadScoreParam) {
        return trailHeadScoreParam.coordinatesList().stream().mapToLong(coordinate -> computeScoreAndRank(new TrailHeadScoreParam(trailHeadScoreParam.value(), coordinate, trailHeadScoreParam.coordinatesList(), trailHeadScoreParam.trailMap(), trailHeadScoreParam.validTrailHeadCoordinates(), trailHeadScoreParam.startCoordinate(), trailHeadScoreParam.trailHeadScoreByCoordinates()))).sum();
    }

    private long computeScoreAndRank(TrailHeadScoreParam trailHeadScoreParam) {
        if (trailHeadScoreParam.validTrailHeadCoordinates().contains(trailHeadScoreParam.coordinate())) {
            if (trailHeadScoreParam.trailMap()[trailHeadScoreParam.startCoordinate().getX()][trailHeadScoreParam.startCoordinate().getY()] == 0) {
                trailHeadScoreParam.trailHeadScoreByCoordinates().computeIfAbsent(trailHeadScoreParam.startCoordinate(), _ -> new HashSet<>()).add(trailHeadScoreParam.coordinate());
            }
            return 1;
        }
        if (trailHeadScoreParam.value() == 9) {
            trailHeadScoreParam.validTrailHeadCoordinates().add(trailHeadScoreParam.coordinate());
            if (trailHeadScoreParam.trailMap()[trailHeadScoreParam.startCoordinate().getX()][trailHeadScoreParam.startCoordinate().getY()] == 0) {
                trailHeadScoreParam.trailHeadScoreByCoordinates().computeIfAbsent(trailHeadScoreParam.startCoordinate(), _ -> new HashSet<>()).add(trailHeadScoreParam.coordinate());
            }
            return 1;
        }
        return computeScore(new TrailHeadScoreParam(trailHeadScoreParam.value() + 1, null, computeValidNeighbors(trailHeadScoreParam.value(), trailHeadScoreParam.coordinate(), trailHeadScoreParam.trailMap()), trailHeadScoreParam.trailMap(), trailHeadScoreParam.validTrailHeadCoordinates(), trailHeadScoreParam.startCoordinate(), trailHeadScoreParam.trailHeadScoreByCoordinates()));
    }

    record TrailHeadScoreParam(int value, IntPair coordinate, List<IntPair> coordinatesList, int[][] trailMap, Set<IntPair> validTrailHeadCoordinates, IntPair startCoordinate, Map<IntPair, Set<IntPair>> trailHeadScoreByCoordinates) {}
}
