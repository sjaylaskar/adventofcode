/*
* Id: Point.java 09-Dec-2021 2:09:27 pm SubhajoyLaskar
* Copyright (©) 2021 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.japps.adventofcode.util;


/**
 * The point.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public class Point extends IntPair {

    /**
     * Instantiates a new point.
     *
     * @param x the x
     * @param y the y
     */
    public Point(final int x, final int y) {

        super(x, y);
    }

    public static Point of(final int x, final int y) {
    	return new Point(x, y);
    }

}
