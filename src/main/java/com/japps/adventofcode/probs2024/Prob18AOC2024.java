/*
 * Copyright (©) 2024 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.japps.adventofcode.probs2024;

import com.japps.adventofcode.util.*;

import java.io.IOException;
import java.util.List;

import static com.japps.adventofcode.util.ProblemSolverUtil.*;

public final class Prob18AOC2024 extends AbstractSolvable implements Loggable {

    private static final Prob18AOC2024 INSTANCE = instance();

    private Prob18AOC2024() {

    }

    private static Prob18AOC2024 instance() {

        return new Prob18AOC2024();
    }

    public static void main(final String[] args) {

        try {
            INSTANCE.compute();
        } catch (final IOException exception) {
            INSTANCE.error(exception.getLocalizedMessage());
        }
    }

    private static final char EMPTY_SPACE = '.';
    private static final char CORRUPT_SPACE = '#';
    private static final char START = 'S';
    private static final char END = 'E';
    private static final int SIZE = 71;
    private static final int BYTES_TO_PROCESS = 1024;

    private void compute() throws IOException {
		List<String> lines = lines();
		char[][] map = new char[SIZE][SIZE];
        ProblemSolverUtil.fill(map, EMPTY_SPACE);
        for (int i = 0; i < BYTES_TO_PROCESS; i++) {
            fillCorruptSpace(corruptCoordinates(lines.get(i)), map);
        }
        map[0][0] = START;
        map[SIZE - 1][SIZE - 1] = END;
        //ProblemSolverUtil.print(map);
        println(BFSShortestPathSquareMatrix.compute(toObjects(map), START, END, CORRUPT_SPACE));

        for (int i = BYTES_TO_PROCESS; i < lines.size(); i++) {
            IntPair corruptCoordinates = corruptCoordinates(lines.get(i));
            fillCorruptSpace(corruptCoordinates, map);
            if (BFSShortestPathSquareMatrix.compute(toObjects(map), START, END, CORRUPT_SPACE) == -1) {
                println(corruptCoordinates.flip());
                break;
            }
        }
    }

    private void fillCorruptSpace(IntPair corruptCoordinates, char[][] map) {
        setValue(map, corruptCoordinates, CORRUPT_SPACE);
    }

    private static IntPair corruptCoordinates(String line) {
        String[] corruptCoordinates = line.split(COMMA);
        return IntPair.of(Integer.parseInt(corruptCoordinates[1]), Integer.parseInt(corruptCoordinates[0]));
    }
}
