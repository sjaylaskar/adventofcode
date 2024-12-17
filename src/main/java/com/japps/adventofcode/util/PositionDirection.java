/*
 *
 *  * Copyright (©) 2024 Subhajoy Laskar
 *  * https://www.linkedin.com/in/subhajoylaskar
 *
 */

package com.japps.adventofcode.util;

import java.util.Objects;

public record PositionDirection(IntPair position, Direction direction) {
    public PositionDirection(IntPair position, Direction direction) {
        this.position = IntPair.of(position);
        this.direction = direction;
    }

    public static PositionDirection of(IntPair position, Direction direction) {
        return new PositionDirection(position, direction);
    }

    public static PositionDirection of(PositionDirection positionDirection) {
        return PositionDirection.of(positionDirection.position(), positionDirection.direction());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PositionDirection that = (PositionDirection) o;
        return Objects.equals(position, that.position) && direction == that.direction;
    }

    @Override
    public int hashCode() {
        return Objects.hash(position, direction);
    }
}
