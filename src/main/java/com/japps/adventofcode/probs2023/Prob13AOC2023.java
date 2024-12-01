/*
 * Id: Prob13AOC2023.java 03-Jan-2024 SubhajoyLaskar
 * Copyright (©) 2024 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */

package com.japps.adventofcode.probs2023;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.japps.adventofcode.util.AbstractSolvable;
import com.japps.adventofcode.util.Loggable;

/**
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public final class Prob13AOC2023 extends AbstractSolvable implements Loggable {

	/** The instance. *//*
	private static final Prob13AOC2023 INSTANCE = instance();

	*//**
	 *//*
	private Prob13AOC2023() {

	}

	*//**
	 * Instance.
	 *
	 *//*
	private static Prob13AOC2023 instance() {

		return new Prob13AOC2023();
	}

	*//**
	 * The main method.
	 *
	 * @param args the arguments
	 *//*
	public static void main(final String[] args) {

		try {
			INSTANCE.compute();
		} catch (final IOException exception) {
			INSTANCE.error(exception.getLocalizedMessage());
		}
	}

	List<String> allLines;
    List<Pattern> patterns = new ArrayList<>();

    public class Pattern {
        List<List<Character>> grid;
        List<List<Character>> rotatedGrid;

        private Pattern() {
            grid = new ArrayList<>();
            rotatedGrid = new ArrayList<>();
        }

        private void getCols() {
            for (int i = 0; i < grid.get(0).size(); i++) {
                final List<Character> col = new ArrayList<>();
                for (int j = 0; j < grid.size(); j++) {
                    col.add(grid.get(j).get(i));
                }
                rotatedGrid.add(col);
            }
        }
    }

    void fillPatterns() {
        Pattern pattern = new Pattern();
        for (final String line : allLines) {
            if (line.isEmpty()) {
                patterns.add(pattern);
                pattern = new Pattern();
                continue;
            }
            final List<Character> row = new ArrayList<>();
            for (final char c : line.toCharArray()) {
                row.add(c);
            }
            pattern.grid.add(row);
        }
        patterns.add(pattern);
    }

    boolean isValidMiddle(final List<List<Character>> grid, final int leftPos, final int rightPos, final int part) {
        int numSmudges = 0;
        for (final List<Character> row : grid) {
            int l = leftPos, r = rightPos;
            while (l < r) {
                if (row.get(l) != row.get(r)) {
                    if (part == 1) {
                        return false;
                    }
                    numSmudges++;
                }
                l++;
                r--;
            }
        }
        if (part == 1) {
            return true;
        } else {
            return numSmudges == 1;
        }
    }

    boolean checkSymmetry(final String s1, final String s2, final int part) {
        int numSmudges = 0;
        final int len1 = s1.length(), len2 = s2.length();
        if (len1 != len2) {
			return false;
		}
        for (int i = 0; i < len1; i++) {
            if (s1.charAt(i) != s2.charAt(i)) {
                if (part == 1) {
                    return false;
                }
                numSmudges++;
                if (numSmudges > 2) {
                    return false;
                }
            }
        }
        return true;
    }

    int findMiddle(final Pattern pattern, final boolean checkRow, final int part) {
        List<List<Character>> grid;
        if (checkRow) {
            grid = pattern.grid;
        } else {
            pattern.getCols();
            grid = pattern.rotatedGrid;
        }

        final List<Character> row = grid.get(0);
        final StringBuilder forward = new StringBuilder(row.size()).append(row.get(0));
        int lPtr = 0, rPtr = 0;
		final int n = row.size() - 1;
        while (lPtr < n - 1) {
            if (rPtr == n) {
                lPtr++;
                forward.deleteCharAt(0);
            } else {
                rPtr++;
                forward.append(row.get(rPtr));
            }
            if ((rPtr - lPtr) % 2 == 1) {
                if (checkSymmetry(forward.toString(), forward.reverse().toString(), part)
                        && isValidMiddle(grid, lPtr, rPtr, part)) {
                    return lPtr + (rPtr - lPtr) / 2 + 1;
                }
                forward.reverse();
            }
        }

        return 0;
    }

    void part1() {
        int totalSummary = 0;
        for (final Pattern pattern : patterns) {
            int lineOfReflection = findMiddle(pattern, true, 1);
            if (lineOfReflection == 0) {
                lineOfReflection = findMiddle(pattern, false, 1) * 100;
            }
            totalSummary += lineOfReflection;
        }
        System.out.printf("Part 1: %d\n", totalSummary);

    }

    void part2() {
        int totalSummary = 0;
        for (final Pattern pattern : patterns) {
            int lineOfReflection = findMiddle(pattern, true, 2);
            if (lineOfReflection == 0) {
                lineOfReflection = findMiddle(pattern, false, 2) * 100;
            }
            totalSummary += lineOfReflection;
        }
        System.out.printf("Part 2: %d\n", totalSummary);
    }

    public static void main(final String... args) throws Exception {
        final Day13 day13 = new Day13();

        day13.allLines = Files.readAllLines(Paths.get("./day13.txt"));
        day13.fillPatterns();
        day13.part2();
        day13.part1();
    }*/
}
