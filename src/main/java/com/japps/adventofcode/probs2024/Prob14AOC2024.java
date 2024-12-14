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

    private void compute() throws IOException {
		List<String> lines = lines();
		List<Robot> robots = new ArrayList<>();
        for (String line : lines) {
            robots.add(robot(line));
        }
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
