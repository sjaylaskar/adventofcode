/*
 * Copyright (©) 2024 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.japps.adventofcode.probs2024;

import com.japps.adventofcode.util.AbstractSolvable;
import com.japps.adventofcode.util.IntPair;
import com.japps.adventofcode.util.Loggable;
import com.japps.adventofcode.util.ProblemSolverUtil;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.List;
import java.util.stream.IntStream;

import static com.japps.adventofcode.util.ProblemSolverUtil.*;

public final class Prob15AOC2024 extends AbstractSolvable implements Loggable {

    private static final Prob15AOC2024 INSTANCE = instance();

    private Prob15AOC2024() {

    }

    private static Prob15AOC2024 instance() {

        return new Prob15AOC2024();
    }

    public static void main(final String[] args) {

        try {
            INSTANCE.compute();
        } catch (final IOException exception) {
            INSTANCE.error(exception.getLocalizedMessage());
        }
    }

    private static final char ROBOT = '@';
    private static final char BOX = 'O';
    private static final char WALL = '#';
    private static final char EMPTY = '.';
    private static final char MOVE_UP = '^';
    private static final char MOVE_DOWN = 'v';
    private static final char MOVE_LEFT = '<';
    private static final char MOVE_RIGHT = '>';

    private void compute() throws IOException {
        List<String> lines = lines();
        char[][] map = ProblemSolverUtil.linesAsArray(lines.subList(0, 50));
        List<String> robotMovementsList = lines.subList(51, 71);
        String robotMovements = String.join(StringUtils.EMPTY, robotMovementsList);
        //println("Robot movements: " + robotMovements);
        IntPair robotCoordinates = computeRobotCoordinates(map);
        //println("Initial map: ");
        //print(map);
        moveRobot(robotMovements, robotCoordinates, map);
        print(map);
        println(gpsSum(map));
    }

    private static long gpsSum(char[][] map) {
        long gpsSum = 0;
        for (int i = 1; i < rows(map); i++) {
            for (int j = 1; j < cols(map, i); j++) {
                if (map[i][j] == BOX) {
                    gpsSum += (100L * i) + j;
                }
            }
        }
        return gpsSum;
    }

    private void moveRobot(String robotMovements, IntPair robotCoordinates, char[][] map) {
        for (int i = 0; i < robotMovements.length(); i++) {
            char move = robotMovements.charAt(i);
            IntPair nextPosition = nextPosition(move, robotCoordinates);
            if (value(map, nextPosition) == WALL) {
                //printMovedMap(map, move);
                continue;
            }
            if (value(map, nextPosition) == EMPTY) {
                map[nextPosition.getX()][nextPosition.getY()] = ROBOT;
                map[robotCoordinates.getX()][robotCoordinates.getY()] = EMPTY;
                robotCoordinates = IntPair.of(nextPosition.getX(), nextPosition.getY());
            }
            if (value(map, nextPosition) == BOX) {
                robotCoordinates = switch (move) {
                    case MOVE_UP -> moveUp(nextPosition, map, robotCoordinates);
                    case MOVE_DOWN -> moveDown(nextPosition, map, robotCoordinates);
                    case MOVE_LEFT -> moveLeft(nextPosition, map, robotCoordinates);
                    case MOVE_RIGHT -> moveRight(nextPosition, map, robotCoordinates);
                    default -> robotCoordinates;
                };
            }
            //printMovedMap(map, move);
        }
    }

    private void printMovedMap(char[][] map, char move) {
        println("Map at move: " + move);
        print(map);
    }

    private static IntPair moveUp(IntPair nextPosition, char[][] map, IntPair robotCoordinates) {
        // MOVE UP: COL is SAME.
        for (int r = nextPosition.getX() - 1; r >= 1; r--) {
            if (map[r][nextPosition.getY()] == EMPTY) {
                return sameColumnMovement(robotCoordinates, IntStream.range(r, nextPosition.getX()), map, nextPosition);
            }
            if (map[r][nextPosition.getY()] == BOX) {
                continue;
            }
            if (map[r][nextPosition.getY()] == WALL) {
                break;
            }
        }
        return robotCoordinates;
    }

    private static IntPair moveDown(IntPair nextPosition, char[][] map, IntPair robotCoordinates) {
        // MOVE DOWN: COL is SAME.
        for (int r = nextPosition.getX() + 1; r <= rows(map); r++) {
            if (map[r][nextPosition.getY()] == EMPTY) {
                return sameColumnMovement(robotCoordinates, IntStream.rangeClosed(nextPosition.getX() + 1, r), map, nextPosition);
            }
            if (map[r][nextPosition.getY()] == BOX) {
                continue;
            }
            if (map[r][nextPosition.getY()] == WALL) {
                break;
            }
        }
        return robotCoordinates;
    }

    private static IntPair sameColumnMovement(IntPair robotCoordinates, IntStream rowStream, char[][] map, IntPair nextPosition) {
        int robotColumn = robotCoordinates.getY();
        rowStream.forEach(row -> map[row][robotColumn] = BOX);
        map[nextPosition.getX()][nextPosition.getY()] = ROBOT;
        map[robotCoordinates.getX()][robotCoordinates.getY()] = EMPTY;
        return IntPair.of(nextPosition.getX(), nextPosition.getY());
    }

    private static IntPair moveLeft(IntPair nextPosition, char[][] map, IntPair robotCoordinates) {
        // MOVE LEFT: ROW is SAME.
        for (int c = nextPosition.getY() - 1; c >= 1; c--) {
            if (map[nextPosition.getX()][c] == EMPTY) {
                return sameRowMovement(robotCoordinates, IntStream.range(c, nextPosition.getY()), map, nextPosition);
            }
            if (map[nextPosition.getX()][c] == BOX) {
                continue;
            }
            if (map[nextPosition.getX()][c] == WALL) {
                break;
            }
        }
        return robotCoordinates;
    }

    private static IntPair moveRight(IntPair nextPosition, char[][] map, IntPair robotCoordinates) {
        // MOVE RIGHT: ROW is SAME.
        for (int c = nextPosition.getY() + 1; c <= cols(map, robotCoordinates.getX()); c++) {
            if (map[nextPosition.getX()][c] == EMPTY) {
                return sameRowMovement(robotCoordinates, IntStream.rangeClosed(nextPosition.getY() + 1, c), map, nextPosition);
            }
            if (map[nextPosition.getX()][c] == BOX) {
                continue;
            }
            if (map[nextPosition.getX()][c] == WALL) {
                break;
            }
        }
        return robotCoordinates;
    }

    private static IntPair sameRowMovement(IntPair robotCoordinates, IntStream columnStream, char[][] map, IntPair nextPosition) {
        int robotRow = robotCoordinates.getX();
        columnStream.forEach(col -> map[robotRow][col] = BOX);
        map[nextPosition.getX()][nextPosition.getY()] = ROBOT;
        map[robotCoordinates.getX()][robotCoordinates.getY()] = EMPTY;
        return IntPair.of(nextPosition.getX(), nextPosition.getY());
    }

    private static IntPair nextPosition(char move, IntPair robotCoordinates) {
        return switch (move) {
            case MOVE_UP -> IntPair.of(robotCoordinates.getX() - 1, robotCoordinates.getY());
            case MOVE_DOWN -> IntPair.of(robotCoordinates.getX() + 1, robotCoordinates.getY());
            case MOVE_LEFT -> IntPair.of(robotCoordinates.getX(), robotCoordinates.getY() - 1);
            case MOVE_RIGHT -> IntPair.of(robotCoordinates.getX(), robotCoordinates.getY() + 1);
            default -> robotCoordinates;
        };
    }

    private static IntPair computeRobotCoordinates(char[][] map) {
        for (int i = 0; i < rows(map); i++) {
            for (int j = 0; j < cols(map, i); j++) {
                if (map[i][j] == ROBOT) {
                    return IntPair.of(i, j);
                }
            }
        }
        return null;
    }
}
