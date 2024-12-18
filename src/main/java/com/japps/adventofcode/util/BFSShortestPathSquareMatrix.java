/*
 *
 *  * Copyright (©) 2024 Subhajoy Laskar
 *  * https://www.linkedin.com/in/subhajoylaskar
 *
 */

package com.japps.adventofcode.util;

import java.util.*;
import java.util.stream.Collectors;

import static com.japps.adventofcode.util.ProblemSolverUtil.*;

public final class BFSShortestPathSquareMatrix {

    private BFSShortestPathSquareMatrix() {}

    public static int compute(Object[][] arr, Object startIndicator, Object endIndicator, Object obstacleIndicator) {
        Point startPoint = Point.of(indicatedPoint(arr, startIndicator), 0);
        Point endPoint = Point.of(indicatedPoint(arr, endIndicator), -1);
        Queue<Point> pointsQueue = new LinkedList<>();
        pointsQueue.add(startPoint);
        Set<Point> visitedCoordinates = new HashSet<>();
        visitedCoordinates.add(startPoint);
        while(!pointsQueue.isEmpty()) {
            Point point = pointsQueue.poll();
            if (endPoint.equals(point)) {
                return point.sourceDistance();
            }
            pointsQueue.addAll(neighbors(point, arr, obstacleIndicator, visitedCoordinates));
        }
        return -1;
    }

    private static IntPair indicatedPoint(Object[][] arr, Object indicator) {
        for (int i = 0; i < rows(arr); i++) {
            for (int j = 0; j < cols(arr, i); j++) {
                if (Objects.equals(arr[i][j], indicator)) {
                    return IntPair.of(i, j);
                }
            }
        }
        // Default indicated position is top-left corner of the array.
        return IntPair.of(0, 0);
    }

    private static Set<Point> neighbors(Point point, Object[][] arr, Object obstacleIndicator, Set<Point> visitedCoordinates) {
        return horizontalVerticalNeighborCoordinates(point.coordinate())
                .filter(coordinate -> isInBounds(coordinate, rows(arr), cols(arr, 0)))
                .filter(coordinate -> !Objects.equals(value(arr, coordinate), obstacleIndicator))
                .map(coordinate -> Point.of(coordinate, point.sourceDistance() + 1))
                .filter(visitedCoordinates::add)
                .collect(Collectors.toSet());
    }

    private static final class Point {
        IntPair coordinate;
        int sourceDistance;

        private Point(IntPair coordinate, int sourceDistance) {
            this.coordinate = coordinate;
            this.sourceDistance = sourceDistance;
        }

        static Point of(IntPair coordinate, int sourceDistance) {
            return new Point(coordinate, sourceDistance);
        }

        IntPair coordinate() {
            return coordinate;
        }

        int sourceDistance() {
            return sourceDistance;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Point point = (Point) o;
            return Objects.equals(coordinate, point.coordinate);
        }

        @Override
        public int hashCode() {
            return coordinate.hashCode();
        }
    }
}

