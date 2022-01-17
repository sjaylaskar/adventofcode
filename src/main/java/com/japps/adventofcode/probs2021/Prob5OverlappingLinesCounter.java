/*
 * Id: Prob5OverlappingLinesCounter.java 05-Dec-2021 10:32:39 am SubhajoyLaskar
 * Copyright (©) 2021 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.japps.adventofcode.probs2021;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.japps.adventofcode.util.AbstractSolvable;
import com.japps.adventofcode.util.Loggable;
import com.japps.adventofcode.util.Point;


/**
 * The prob 5 overlapping lines counter.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public final class Prob5OverlappingLinesCounter extends AbstractSolvable implements Loggable {

    /** The instance. */
    private static final Prob5OverlappingLinesCounter INSTANCE = instance();

    /**
     * Instantiates a new prob 5 overlapping lines counter.
     */
    private Prob5OverlappingLinesCounter() {

    }

    /**
     * Instance.
     *
     * @return the prob 5 overlapping lines counter
     */
    private static Prob5OverlappingLinesCounter instance() {

        return new Prob5OverlappingLinesCounter();
    }

    /**
     * The main method.
     *
     * @param args the arguments
     */
    public static void main(final String[] args) {

        try {
            final List<String> lines = INSTANCE.lines();
            INSTANCE.info(INSTANCE.countAllOverlappingLines(lines, false));
            INSTANCE.info(INSTANCE.countAllOverlappingLines(lines, true));
        } catch (final IOException exception) {
            INSTANCE.error(exception.getLocalizedMessage());
        }
    }

    /**
     * Counts the all overlapping lines.
     *
     * @param lines the lines
     * @param isCountAllOverlappingLines the is count all overlapping lines
     * @return the count of all overlapping lines
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private long countAllOverlappingLines(final List<String> lines, final boolean isCountAllOverlappingLines) throws IOException {

        final List<LineSegment> lineSegments = buildLineSegments(lines);

        final Map<Point, Integer> pointFrequencyMap = new LinkedHashMap<>();
        for (final LineSegment lineSegment : lineSegments) {
            if (lineSegment.x1 == lineSegment.x2) {
                addPointFrequencyForX1EqualToX2(lineSegment, pointFrequencyMap);
            } else if (lineSegment.y1 == lineSegment.y2) {
                addPointFrequencyForY1EqualToY2(lineSegment, pointFrequencyMap);
            } else if (isCountAllOverlappingLines){
                addPointFrequencyForOtherLines(lineSegment, pointFrequencyMap);
            }
        }

        return pointFrequencyMap.values().stream().filter(value -> value >= 2).count();
    }

    /**
     * Builds the line segments.
     *
     * @param lines the lines
     * @return the list
     */
    private List<LineSegment> buildLineSegments(final List<String> lines) {

        final List<LineSegment> lineSegments = new ArrayList<>();

        for (final String line : lines) {
            final String[] splitLine = line.split(" -> ");
            final String[] x1y1 = splitLine[0].split(",");
            final String[] x2y2 = splitLine[1].split(",");
            final LineSegment lineSegment = new LineSegment(Integer.parseInt(x1y1[0]), Integer.parseInt(x1y1[1]), Integer.parseInt(
                x2y2[0]), Integer.parseInt(x2y2[1]));
            lineSegments.add(lineSegment);
        }
        return lineSegments;
    }

    /**
     * Adds the point frequency for other lines.
     *
     * @param lineSegment the line segment
     * @param pointFrequencyMap the point frequency map
     */
    private void addPointFrequencyForOtherLines(final LineSegment lineSegment, final Map<Point, Integer> pointFrequencyMap) {

        final double slope = (lineSegment.y2 - lineSegment.y1) / (lineSegment.x2 - lineSegment.x1);
        final double intercept = lineSegment.y1 - slope * lineSegment.x1;
        final int minX = Math.min(lineSegment.x1, lineSegment.x2);
        final int maxX = Math.max(lineSegment.x1, lineSegment.x2);
        for (int i = minX; i <= maxX; i++) {
            final Point point = new Point(i, (int) (slope * i + intercept));
            if (!pointFrequencyMap.containsKey(point)) {
                pointFrequencyMap.put(point, 0);
            }
            pointFrequencyMap.put(point, pointFrequencyMap.get(point) + 1);
        }
    }

    /**
     * Adds the point frequency for X1 equal to X2.
     *
     * @param lineSegment the line segment
     * @param pointFrequencyMap the point frequency map
     */
    private void addPointFrequencyForX1EqualToX2(final LineSegment lineSegment, final Map<Point, Integer> pointFrequencyMap) {

        final int first = Math.min(lineSegment.y1, lineSegment.y2);
        final int last = Math.max(lineSegment.y1, lineSegment.y2);
        for (int i = first; i <= last; i++) {
            final Point point = new Point(lineSegment.x1, i);
            if (!pointFrequencyMap.containsKey(point)) {
                pointFrequencyMap.put(point, 0);
            }
            pointFrequencyMap.put(point, pointFrequencyMap.get(point) + 1);
        }
    }

    /**
     * Adds the point frequency for Y1 equal to Y2.
     *
     * @param lineSegment the line segment
     * @param pointFrequencyMap the point frequency map
     */
    private void addPointFrequencyForY1EqualToY2(final LineSegment lineSegment, final Map<Point, Integer> pointFrequencyMap) {

        final int first = Math.min(lineSegment.x1, lineSegment.x2);
        final int last = Math.max(lineSegment.x1, lineSegment.x2);
        for (int i = first; i <= last; i++) {
            final Point point = new Point(i, lineSegment.y1);
            if (!pointFrequencyMap.containsKey(point)) {
                pointFrequencyMap.put(point, 0);
            }
            pointFrequencyMap.put(point, pointFrequencyMap.get(point) + 1);
        }
    }

    /**
     * The line segment.
     *
     * @author Subhajoy Laskar
     * @version 1.0
     */
    private static final class LineSegment {

        /** The x 1. */
        private final int x1;

        /** The y 1. */
        private final int y1;

        /** The x 2. */
        private final int x2;

        /** The y 2. */
        private final int y2;

        /**
         * Instantiates a new line segment.
         *
         * @param x1 the x 1
         * @param y1 the y 1
         * @param x2 the x 2
         * @param y2 the y 2
         */
        public LineSegment(final int x1, final int y1, final int x2, final int y2) {

            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {

            return "LineSegment [x1=" + x1 + ", y1=" + y1 + ", x2=" + x2 + ", y2=" + y2 + "]";
        }
    }
}
