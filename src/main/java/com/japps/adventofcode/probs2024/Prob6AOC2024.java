/*
 * Copyright (©) 2024 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.japps.adventofcode.probs2024;

import com.japps.adventofcode.util.AbstractSolvable;
import com.japps.adventofcode.util.IntPair;
import com.japps.adventofcode.util.Loggable;

import java.io.IOException;
import java.util.HashSet;
import java.util.Objects;
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

    private enum DIRECTION {
        NORTH, EAST, WEST, SOUTH;

        static boolean isNorth(DIRECTION direction) {
            return NORTH.equals(direction);
        }

        static boolean isEast(DIRECTION direction) {
            return EAST.equals(direction);
        }

        static boolean isWest(DIRECTION direction) {
            return WEST.equals(direction);
        }

        static boolean isSouth(DIRECTION direction) {
            return SOUTH.equals(direction);
        }

        DIRECTION nextDirection() {
            return switch (this) {
                case NORTH -> EAST;
                case EAST -> SOUTH;
                case SOUTH -> WEST;
                case WEST -> NORTH;
            };
        }
    }

    private void compute() throws IOException {
        char[][] labMap = linesAsArray();

        DIRECTION startingGuardDirection = DIRECTION.NORTH;
        IntPair startingGuardPosition = startingGuardPosition(labMap);

        Set<IntPair> guardPositionsTraversed = new HashSet<>();
        guardPositionsTraversed.add(startingGuardPosition);
        guardTraversalPositions(IntPair.of(startingGuardPosition), startingGuardDirection, labMap, guardPositionsTraversed);

        guardTraversalLoopObstaclePositions(GuardPositionDirection.of(startingGuardPosition, startingGuardDirection), labMap, guardPositionsTraversed);
    }

    private static int cols(char[][] labMap) {
        return labMap[0].length;
    }

    private static int rows(char[][] labMap) {
        return labMap.length;
    }

    private static IntPair startingGuardPosition(char[][] labMap) {
        IntPair startingGuardPosition = IntPair.of(-1, -1);
        outer:
        for (int i = 0; i < rows(labMap); i++) {
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

    private void guardTraversalPositions(IntPair guardPosition, DIRECTION guardDirection, char[][] labMap, Set<IntPair> guardPositions) {
        while (true) {
            guardPosition =
                    switch (guardDirection) {
                        case NORTH -> IntPair.of(guardPosition.getX() - 1, guardPosition.getY());
                        case EAST -> IntPair.of(guardPosition.getX(), guardPosition.getY() + 1);
                        case WEST -> IntPair.of(guardPosition.getX(), guardPosition.getY() - 1);
                        case SOUTH -> IntPair.of(guardPosition.getX() + 1, guardPosition.getY());
                    };
            if (isObstacle(labMap, guardPosition, rows(labMap), cols(labMap))) {
                guardPosition = revertToNonObstaclePosition(guardDirection, guardPosition);
                guardDirection = guardDirection.nextDirection();
            } else {
                guardPositions.add(guardPosition);
            }
            if (isGuardTraversalDone(guardDirection, guardPosition, rows(labMap), cols(labMap))) {
                guardPositions.add(guardPosition);
                break;
            }
        }
        println(guardPositions.size());
    }

    private void guardTraversalLoopObstaclePositions(GuardPositionDirection startingGuardPositionDirection, char[][] labMap, Set<IntPair> guardPositions) {
        int countLoopingObstaclePositions = 0;
        Set<IntPair> newObstaclePositions = newObstaclePositions(startingGuardPositionDirection.position(), labMap, guardPositions);
        for (IntPair newObstaclePosition : newObstaclePositions) {
            labMap[newObstaclePosition.getX()][newObstaclePosition.getY()] = OBSTACLE;
            IntPair guardPosition = IntPair.of(startingGuardPositionDirection.position());
            DIRECTION guardDirection = DIRECTION.NORTH;
            Set<GuardPositionDirection> guardPositionDirections = new HashSet<>();
            guardPositionDirections.add(startingGuardPositionDirection);
            while (true) {
                guardPosition =
                        switch (guardDirection) {
                            case NORTH -> IntPair.of(guardPosition.getX() - 1, guardPosition.getY());
                            case EAST -> IntPair.of(guardPosition.getX(), guardPosition.getY() + 1);
                            case WEST -> IntPair.of(guardPosition.getX(), guardPosition.getY() - 1);
                            case SOUTH -> IntPair.of(guardPosition.getX() + 1, guardPosition.getY());
                        };
                if (isObstacle(labMap, guardPosition, rows(labMap), cols(labMap))) {
                    guardPosition = revertToNonObstaclePosition(guardDirection, guardPosition);
                    guardDirection = guardDirection.nextDirection();
                } else {
                    if (!guardPositionDirections.add(GuardPositionDirection.of(guardPosition, guardDirection))) {
                        countLoopingObstaclePositions++;
                        break;
                    }
                }
                if (isGuardTraversalDone(guardDirection, guardPosition, rows(labMap), cols(labMap))) {
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

    private IntPair revertToNonObstaclePosition(DIRECTION guardDirection, IntPair guardPosition) {
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

    private static boolean isGuardTraversalDone(DIRECTION guardDirection, IntPair guardPosition, int rows, int cols) {
        return (DIRECTION.isNorth(guardDirection) && guardPosition.getX() <= 0)
                || (DIRECTION.isEast(guardDirection) && guardPosition.getY() >= cols - 1)
                || (DIRECTION.isSouth(guardDirection) && guardPosition.getX() >= rows - 1)
                || (DIRECTION.isWest(guardDirection) && guardPosition.getY() <= 0);
    }

    private record GuardPositionDirection(IntPair position, DIRECTION direction) {
            private GuardPositionDirection(IntPair position, DIRECTION direction) {
                this.position = IntPair.of(position);
                this.direction = direction;
            }

            static GuardPositionDirection of(IntPair position, DIRECTION direction) {
                return new GuardPositionDirection(position, direction);
            }

            @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                GuardPositionDirection that = (GuardPositionDirection) o;
                return Objects.equals(position, that.position) && direction == that.direction;
            }

    }
}
