/*
 * Copyright (©) 2024 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.japps.adventofcode.probs2024;

import com.japps.adventofcode.util.AbstractSolvable;
import com.japps.adventofcode.util.IntPair;
import com.japps.adventofcode.util.Loggable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static com.japps.adventofcode.util.ProblemSolverUtil.mid;

public final class Prob14AOC2024 extends AbstractSolvable implements Loggable {

    private static final Prob14AOC2024 INSTANCE = instance();

    private Prob14AOC2024() {

    }

    private static Prob14AOC2024 instance() {

        return new Prob14AOC2024();
    }

    public static void main(final String[] args) {

        try {
            INSTANCE.compute();
        } catch (final IOException exception) {
            INSTANCE.error(exception.getLocalizedMessage());
        }
    }

    private static final int ROWS = 103;
    private static final int COLS = 101;
    private static final int COUNT_OF_MOVES = 100;
    private static final String PATTERN_ROBOT = "O";
    private static final String PATTERN_DOT = ".";
    private static final String PATTERN_BASE_MINUS_1_CHRISTMAS_TREE = "OOOOOOOOOO";
    private static final String PATTERN_BASE_CHRISTMAS_TREE = "OOOOOOOOOOO";

    private void compute() throws IOException {
		List<String> lines = lines();
        computeSafetyFactor(robots(lines));
        computeChristmasTree(robots(lines));
    }

    private List<Robot> robots(List<String> lines) {
        List<Robot> robots = new ArrayList<>();
        for (String line : lines) {
            robots.add(robot(line));
        }
        return robots;
    }

    private void computeChristmasTree(List<Robot> robots) {
        outer: for (int t = 0; t < ROWS * COLS; t++) {
            List<StringBuilder> rowBuilders = new ArrayList<>();
            for (int i = 0; i < ROWS; i++) {
                StringBuilder rowBuilder = new StringBuilder();
                for (int j = 0; j < COLS; j++) {
                    IntPair position = IntPair.of(i, j);
                    if (robots.stream().anyMatch(robot -> robot.isInPosition(position))) {
                        rowBuilder.append(PATTERN_ROBOT);
                    } else {
                        rowBuilder.append(PATTERN_DOT);
                    }
                }
                rowBuilders.add(rowBuilder);
            }
            for (int r = 0; r < rowBuilders.size() - 1; r++) {
                if (rowBuilders.get(r).toString().contains(PATTERN_BASE_MINUS_1_CHRISTMAS_TREE)
                    && rowBuilders.get(r + 1).toString().contains(PATTERN_BASE_CHRISTMAS_TREE)) {
                    System.out.println("************************************************************************************\n");
                    rowBuilders.forEach(System.out::println);
                    System.out.println("************************************************************************************\n");
                    println("\nTIME: t = " + t);
                    break outer;
                }
            }
            robots.forEach(robot -> robot.move(ROWS, COLS));
        }
    }

    private void computeSafetyFactor(List<Robot> robots) {
        robots.forEach(this::move);
        long firstQuadrantCount = 0;
        long secondQuadrantCount = 0;
        long thirdQuadrantCount = 0;
        long fourthQuadrantCount = 0;
        for (Robot robot : robots) {
            if (isInQuadrant(robot)) {
                if (Quadrant.isFirst(robot.quadrant(ROWS, COLS))) {
                    ++firstQuadrantCount;
                }
                if (Quadrant.isSecond(robot.quadrant(ROWS, COLS))) {
                    ++secondQuadrantCount;
                }
                if (Quadrant.isThird(robot.quadrant(ROWS, COLS))) {
                    ++thirdQuadrantCount;
                }
                if (Quadrant.isFourth(robot.quadrant(ROWS, COLS))) {
                    ++fourthQuadrantCount;
                }
            }
        }
        println(firstQuadrantCount * secondQuadrantCount * thirdQuadrantCount * fourthQuadrantCount);
    }

    private boolean isInQuadrant(Robot robot) {
        return (robot.position().getX() != mid(ROWS) && robot.position().getY() != mid(COLS));
    }

    private void move(Robot robot) {
        IntStream.rangeClosed(1, COUNT_OF_MOVES).forEach(_ -> robot.move(ROWS, COLS));
    }

    private Robot robot(String line) {
        String[] startPosition = line.substring(line.indexOf("p=") + 2, line.indexOf(" ")).split(",");
        String[] velocities = line.substring(line.indexOf("v=") + 2).split(",");
        return Robot.of()
                .position(IntPair.of(Integer.parseInt(startPosition[1]), Integer.parseInt(startPosition[0])))
                .rowVelocity(Integer.parseInt(velocities[1]))
                .columnVelocity(Integer.parseInt(velocities[0]));
    }

    private static class Robot {
        private IntPair position;
        private int rowVelocity;
        private int columnVelocity;

        private Robot() {}

        static Robot of() {
            return new Robot();
        }

        Robot position(IntPair position) {
            this.position = position;
            return this;
        }

        Robot rowVelocity(int rowVelocity) {
            this.rowVelocity = rowVelocity;
            return this;
        }

        Robot columnVelocity(int columnVelocity) {
            this.columnVelocity = columnVelocity;
            return this;
        }

        IntPair position() {
            return position;
        }

        int rowVelocity() {
            return rowVelocity;
        }

        int columnVelocity() {
            return columnVelocity;
        }

        boolean isInPosition(IntPair position) {
            return this.position().equals(position);
        }

        void move(int rowEdge, int colEdge) {
            position(IntPair.of(
                    movement(position().getX(), rowEdge, rowVelocity()),
                    movement(position().getY(), colEdge, columnVelocity())));
        }

        private int movement(int axisPosition, int axisEdge, int axisVelocity) {
            int newAxisPosition = axisPosition + axisVelocity;
            if (newAxisPosition < 0) {
                newAxisPosition += axisEdge;
            }
            if (newAxisPosition >= axisEdge) {
                newAxisPosition -= axisEdge;
            }
            return newAxisPosition;
        }

        private Quadrant quadrant(int rowEdge, int colEdge) {
            return (position().getX() >= 0 && position().getX() < mid(rowEdge)
                    && position().getY() >= 0 && position().getY() < mid(colEdge))
                    ? Quadrant.FIRST
                    : (position().getX() >= 0 && position().getX() < mid(rowEdge)
                    && position().getY() > mid(colEdge) && position().getY() < colEdge)
                    ? Quadrant.SECOND
                    : (position().getX() > mid(rowEdge) && position().getX() < rowEdge
                    && position().getY() >= 0 && position().getY() < mid(colEdge))
                    ? Quadrant.THIRD
                    : (position().getX() > mid(rowEdge) && position().getX() < rowEdge
                    && position().getY() > mid(colEdge) && position().getY() < colEdge)
                    ? Quadrant.FOURTH
                    : Quadrant.MIDDLE;
        }
    }

    enum Quadrant {
        FIRST, SECOND, THIRD, FOURTH, MIDDLE;

        static Quadrant quadrant(Quadrant quadrant) {
            return switch (quadrant) {
                case FIRST -> FIRST;
                case SECOND -> SECOND;
                case THIRD -> THIRD;
                case FOURTH -> FOURTH;
                case MIDDLE -> MIDDLE;
            };
        }

        static boolean isFirst(Quadrant quadrant) {
            return FIRST.equals(quadrant(quadrant));
        }

        static boolean isSecond(Quadrant quadrant) {
            return SECOND.equals(quadrant(quadrant));
        }

        static boolean isThird(Quadrant quadrant) {
            return THIRD.equals(quadrant(quadrant));
        }

        static boolean isFourth(Quadrant quadrant) {
            return FOURTH.equals(quadrant(quadrant));
        }
    }
}
