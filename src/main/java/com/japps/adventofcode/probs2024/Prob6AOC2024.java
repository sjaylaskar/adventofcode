/*
 * Copyright (©) 2024 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.japps.adventofcode.probs2024;

import com.japps.adventofcode.util.*;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public final class Prob6AOC2024 extends AbstractSolvable implements Loggable {

    private static final Prob6AOC2024 INSTANCE = instance();

    private Prob6AOC2024() {

    }

    private static Prob6AOC2024 instance() {

        return new Prob6AOC2024();
    }

    public static void main(final String[] args) {

        try {
            INSTANCE.compute();
        } catch (final IOException exception) {
            INSTANCE.error(exception.getLocalizedMessage());
        }
    }

    private static final char GUARD = '^';
    private static final char OBSTACLE = '#';
    private static final char EMPTY = '.';

    private void compute() throws IOException {
        char[][] labMap = linesAsArray();

        Direction startingGuardDirection = Direction.NORTH;
        IntPair startingGuardPosition = startingGuardPosition(labMap);

        Set<IntPair> guardPositionsTraversed = new HashSet<>();
        guardPositionsTraversed.add(startingGuardPosition);
        guardTraversalPositions(IntPair.of(startingGuardPosition), startingGuardDirection, labMap, guardPositionsTraversed);

        guardTraversalLoopObstaclePositions(PositionDirection.of(startingGuardPosition, startingGuardDirection), labMap, guardPositionsTraversed);
    }

    private static IntPair startingGuardPosition(char[][] labMap) {
        IntPair startingGuardPosition = IntPair.of(-1, -1);
        outer:
        for (int i = 0; i < ProblemSolverUtil.rows(labMap); i++) {
            for (int j = 0; j < labMap[i].length; j++) {
                if (isGuard(labMap, i, j)) {
                    startingGuardPosition = IntPair.of(i, j);
                    break outer;
                }
            }
        }
        return startingGuardPosition;
    }

    private static boolean isGuard(char[][] labMap, int i, int j) {
        return labMap[i][j] == GUARD;
    }

    private void guardTraversalPositions(IntPair guardPosition, Direction guardDirection, char[][] labMap, Set<IntPair> guardPositions) {
        while (true) {
            guardPosition =
                    switch (guardDirection) {
                        case NORTH -> IntPair.of(guardPosition.getX() - 1, guardPosition.getY());
                        case EAST -> IntPair.of(guardPosition.getX(), guardPosition.getY() + 1);
                        case WEST -> IntPair.of(guardPosition.getX(), guardPosition.getY() - 1);
                        case SOUTH -> IntPair.of(guardPosition.getX() + 1, guardPosition.getY());
                    };
            if (isObstacle(labMap, guardPosition, ProblemSolverUtil.rows(labMap), ProblemSolverUtil.cols(labMap, 0))) {
                guardPosition = revertToNonObstaclePosition(guardDirection, guardPosition);
                guardDirection = guardDirection.nextDirection();
            } else {
                guardPositions.add(guardPosition);
            }
            if (isGuardTraversalDone(guardDirection, guardPosition, ProblemSolverUtil.rows(labMap), ProblemSolverUtil.cols(labMap, 0))) {
                guardPositions.add(guardPosition);
                break;
            }
        }
        println(guardPositions.size());
    }

    private void guardTraversalLoopObstaclePositions(PositionDirection startingPositionDirection, char[][] labMap, Set<IntPair> guardPositions) {
        int countLoopingObstaclePositions = 0;
        Set<IntPair> newObstaclePositions = newObstaclePositions(startingPositionDirection.position(), labMap, guardPositions);
        for (IntPair newObstaclePosition : newObstaclePositions) {
            labMap[newObstaclePosition.getX()][newObstaclePosition.getY()] = OBSTACLE;
            IntPair guardPosition = IntPair.of(startingPositionDirection.position());
            Direction guardDirection = Direction.NORTH;
            Set<PositionDirection> positionDirections = new HashSet<>();
            positionDirections.add(startingPositionDirection);
            while (true) {
                guardPosition =
                        switch (guardDirection) {
                            case NORTH -> IntPair.of(guardPosition.getX() - 1, guardPosition.getY());
                            case EAST -> IntPair.of(guardPosition.getX(), guardPosition.getY() + 1);
                            case WEST -> IntPair.of(guardPosition.getX(), guardPosition.getY() - 1);
                            case SOUTH -> IntPair.of(guardPosition.getX() + 1, guardPosition.getY());
                        };
                if (isObstacle(labMap, guardPosition, ProblemSolverUtil.rows(labMap), ProblemSolverUtil.cols(labMap, 0))) {
                    guardPosition = revertToNonObstaclePosition(guardDirection, guardPosition);
                    guardDirection = guardDirection.nextDirection();
                } else {
                    if (!positionDirections.add(PositionDirection.of(guardPosition, guardDirection))) {
                        countLoopingObstaclePositions++;
                        break;
                    }
                }
                if (isGuardTraversalDone(guardDirection, guardPosition, ProblemSolverUtil.rows(labMap), ProblemSolverUtil.cols(labMap, 0))) {
                    break;
                }
            }
            labMap[newObstaclePosition.getX()][newObstaclePosition.getY()] = EMPTY;
        }
        println(countLoopingObstaclePositions);
    }

    private static Set<IntPair> newObstaclePositions(IntPair startingGuardPosition, char[][] labMap, Set<IntPair> guardPositions) {
        Set<IntPair> newObstacleOptions = new HashSet<>();
        for (IntPair pos : guardPositions) {
            if (!isObstacle(labMap, pos.getX(), pos.getY()) && !startingGuardPosition.equals(pos.getX(), pos.getY())) {
                newObstacleOptions.add(IntPair.of(pos.getX(), pos.getY()));
            }
        }
        return newObstacleOptions;
    }

    private IntPair revertToNonObstaclePosition(Direction guardDirection, IntPair guardPosition) {
        return switch (guardDirection) {
            case NORTH -> IntPair.of(guardPosition.getX() + 1, guardPosition.getY());
            case EAST -> IntPair.of(guardPosition.getX(), guardPosition.getY() - 1);
            case WEST -> IntPair.of(guardPosition.getX(), guardPosition.getY() + 1);
            case SOUTH -> IntPair.of(guardPosition.getX() - 1, guardPosition.getY());

        };
    }

    private boolean isObstacle(char[][] labMap, IntPair guardPosition, int rows, int cols) {
        return (guardPosition.getX() >= 0 && guardPosition.getX() < rows
                && guardPosition.getY() >= 0 && guardPosition.getY() < cols)
                && isObstacle(labMap, guardPosition.getX(), guardPosition.getY());
    }

    private static boolean isObstacle(char[][] labMap, int x, int y) {
        return labMap[x][y] == OBSTACLE;
    }

    private static boolean isGuardTraversalDone(Direction guardDirection, IntPair guardPosition, int rows, int cols) {
        return (Direction.isNorth(guardDirection) && guardPosition.getX() <= 0)
                || (Direction.isEast(guardDirection) && guardPosition.getY() >= cols - 1)
                || (Direction.isSouth(guardDirection) && guardPosition.getX() >= rows - 1)
                || (Direction.isWest(guardDirection) && guardPosition.getY() <= 0);
    }

}
