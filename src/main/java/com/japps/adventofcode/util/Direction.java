/*
 *
 *  * Copyright (©) 2024 Subhajoy Laskar
 *  * https://www.linkedin.com/in/subhajoylaskar
 *
 */

package com.japps.adventofcode.util;

import com.japps.adventofcode.probs2024.Prob6AOC2024;

public enum Direction {
    NORTH, EAST, WEST, SOUTH;

    public static boolean isNorth(Direction direction) {
        return NORTH.equals(direction);
    }

    public static boolean isEast(Direction direction) {
        return EAST.equals(direction);
    }

    public static boolean isWest(Direction direction) {
        return WEST.equals(direction);
    }

    public static boolean isSouth(Direction direction) {
        return SOUTH.equals(direction);
    }

    public Direction nextDirection() {
        return switch (this) {
            case NORTH -> EAST;
            case EAST -> SOUTH;
            case SOUTH -> WEST;
            case WEST -> NORTH;
        };
    }
}
