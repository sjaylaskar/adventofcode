/*
 *
 *  * Copyright (©) 2024 Subhajoy Laskar
 *  * https://www.linkedin.com/in/subhajoylaskar
 *
 */

package com.japps.adventofcode.util;

public class ArrayIndexPair extends IntPair {

    public static ArrayIndexPair of(final int x, final int y) {
        return new ArrayIndexPair(x, y);
    }

    /**
     * Instantiates a new pair.
     *
     * @param x the x
     * @param y the y
     */
    public ArrayIndexPair(int x, int y) {
        super(x, y);
    }

    public int distance() {
        return Math.abs(getY() - getX() + 1);
    }
}
