/*
 * Id: Prob11AOC2023.java 11-Dec-2023 SubhajoyLaskar
 * Copyright (©) 2023 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */

package com.japps.adventofcode.probs2023;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.japps.adventofcode.util.AbstractSolvable;
import com.japps.adventofcode.util.Loggable;
import com.japps.adventofcode.util.Point;


/**
 * The prob 11 AOC 2023.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public final class Prob11AOC2023 extends AbstractSolvable implements Loggable {

	/** The instance. */
	private static final Prob11AOC2023 INSTANCE = instance();

	/**
	 * Instantiates a new prob 11 AOC 2023.
	 */
	private Prob11AOC2023() {

	}

	/**
	 * Instance.
	 *
	 * @return the prob 11 AOC 2023
	 */
	private static Prob11AOC2023 instance() {

		return new Prob11AOC2023();
	}

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(final String[] args) {

		try {
			INSTANCE.compute();
		} catch (final IOException exception) {
			INSTANCE.error(exception.getLocalizedMessage());
		}
	}


	/**
	 * Compute.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private void compute() throws IOException {

		final List<Point> pointsPart1 = new ArrayList<>();
		final List<Point> pointsPart2 = new ArrayList<>();
		processGalaxy(pointsPart1, pointsPart2);

		println(shortestPathSum(pointsPart1));
		println(shortestPathSum(pointsPart2));
	}

	/**
	 * Process galaxy.
	 *
	 * @param pointsPart1 the points part 1
	 * @param pointsPart2 the points part 2
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private void processGalaxy(final List<Point> pointsPart1, final List<Point> pointsPart2) throws IOException {
		final int expansionFactorPart1 = 2;
	    final int expansionFactorPart2 = 1000000;
	    final List<String> lines = lines();
	    final int rows = lines.size();
        final int cols = lines.get(0).length();
		final boolean[] isPresentInColumn = new boolean[cols];
		int rowIndexPart1 = 0;
		int rowIndexPart2 = 0;
		int count = 1;

		for (int row = 0; row < rows; row++) {
			boolean isPresentInRow = false;
			for (int col = 0; col < cols; col++) {
				final int contained = lines.get(row).charAt(col) == '#' ? count++ : 0;
				if (contained > 0) {
					pointsPart1.add(Point.of(rowIndexPart1, col));
					pointsPart2.add(Point.of(rowIndexPart2, col));
					isPresentInColumn[col] = true;
					isPresentInRow = true;
				}
			}
			if (!isPresentInRow) {
				rowIndexPart1 += expansionFactorPart1 - 1;
				rowIndexPart2 += expansionFactorPart2 - 1;
			}
			rowIndexPart1++;
			rowIndexPart2++;
		}

		for (int row = 0; row < count - 1; row++) {
            final int columnPosition = pointsPart1.get(row).getY();
            for (int col = 0; col < columnPosition; col++) {
                if (!isPresentInColumn[col]) {
                	pointsPart1.get(row).setY(pointsPart1.get(row).getY() + expansionFactorPart1 - 1);
                    pointsPart2.get(row).setY(pointsPart2.get(row).getY() + expansionFactorPart2 - 1);
                }
            }
        }
	}

    /**
     * Shortest path.
     *
     * @param point1 the point 1
     * @param point2 the point 2
     * @return the long
     */
    public long shortestPath(final Point point1, final Point point2) {
        return Math.abs(point1.getX() - point2.getX()) + Math.abs(point1.getY() - point2.getY());
    }

    /**
     * Shortest path sum.
     *
     * @param points the points
     * @return the long
     */
    public long shortestPathSum(final List<Point> points) {
    	long sum = 0;
        for (int i = 0; i < points.size(); i++) {
            for (int j = i + 1; j < points.size(); j++) {
                sum += shortestPath(points.get(i), points.get(j));
            }
        }
        return sum;
    }
}
