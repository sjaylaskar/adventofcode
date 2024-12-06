/*
 * Id: Pair.java 09-Dec-2021 2:05:15 pm SubhajoyLaskar
 * Copyright (©) 2021 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.japps.adventofcode.util;

import java.util.Objects;


/**
 * The Pair.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public class IntPair {

    /** The x. */
    private int x;

    /** The y. */
    private int y;

    public static IntPair of(final int x, final int y) {
    	return new IntPair(x, y);
    }

    public static IntPair of(final IntPair intPair) {
        return new IntPair(intPair.getX(), intPair.getY());
    }

    /**
     * Instantiates a new pair.
     *
     * @param x the x
     * @param y the y
     */
    public IntPair(final int x, final int y) {

        this.x = x;
        this.y = y;
    }

    public IntPair flip() {
        return IntPair.of(this.y, this.x);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {

        return Objects.hash(x, y);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object obj) {

        if (this == obj) {
			return true;
		}
        if (obj == null) {
			return false;
		}
        if (getClass() != obj.getClass()) {
			return false;
		}
        final IntPair other = (IntPair) obj;
        return x == other.x && y == other.y;
    }

    public boolean equals(int x, int y) {
        return (this.x == x && this.y == y);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {

        return getClass().getSimpleName() + " [x=" + x + ", y=" + y + "]";
    }

    /**
     * Gets the x.
     *
     * @return the x
     */
    public int getX() {

        return x;
    }

    /**
     * Gets the y.
     *
     * @return the y
     */
    public int getY() {

        return y;
    }

	/**
	 * @param x the x to set
	 */
	public void setX(final int x) {
		this.x = x;
	}

	/**
	 * @param y the y to set
	 */
	public void setY(final int y) {
		this.y = y;
	}
}
