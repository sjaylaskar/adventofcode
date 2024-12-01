/*
 * Id: Prob18AOC2023.java 18-Dec-2023 SubhajoyLaskar
 * Copyright (©) 2023 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */

package com.japps.adventofcode.probs2023;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import com.japps.adventofcode.util.AbstractSolvable;
import com.japps.adventofcode.util.Loggable;

/**
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public final class Prob18AOC2023 extends AbstractSolvable implements Loggable {

	/** The instance. */
	private static final Prob18AOC2023 INSTANCE = instance();

	/**
	 */
	private Prob18AOC2023() {

	}

	/**
	 * Instance.
	 *
	 */
	private static Prob18AOC2023 instance() {

		return new Prob18AOC2023();
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
		process();
	}

    private void process() throws IOException {
    	final List<String> lines = lines();
        //String input = readInput(); //example;//readInput(); //example;//readInput();
        final var instructs = lines.stream().map(DigInstruction::fromString).toList();
        System.out.println(calcArea(instructs));
        final var decoded = instructs.stream().map(DigInstruction::convertColor).toList();
        System.out.println(calcArea(decoded));
    }

    private static long calcArea(final List<DigInstruction> instructions) {
        long line = 0L;
        long area = 0L;
        final List<Coord> coords = new ArrayList<>();
        // the start coord should be included as well
        coords.add(new Coord(0, 0));
        for (final var inst : instructions) {
            line += inst.length();
            coords.add(coords.get(coords.size() - 1).move(inst.dir(), inst.length()));
        }
        for (int i = 0; i < coords.size(); ++i) {
            final Coord ci = coords.get(i);
            // in case it is the last coord, the next coord is the first (start) coord.
            final Coord cn = coords.get((i + 1) % coords.size());
            // one of the arguments must be long, so I won't have overflow
            area += ((long)ci.y + cn.y) * (ci.x - cn.x);
        }
        return (Math.abs(area) + line) / 2 + 1;
    }

    private static String readInput() {
        try {
            return Files.readString(Path.of("day18.txt").toAbsolutePath());
        } catch (final Exception e) {
            throw new IllegalStateException(e);
        }
    }

    private record Coord(int x, int y) {
        Coord move(final char dir, final int length) {
            return switch(dir) {
            case 'L' -> new Coord(x-length, y);
            case 'R' -> new Coord(x+length, y);
            case 'U' -> new Coord(x, y-length);
            case 'D' -> new Coord(x, y+length);
            default -> throw new IllegalStateException("unknown dir: "+dir);
            };
        }
    }

    private record DigInstruction(char dir, int length, String color) {
        static DigInstruction fromString(final String line) {
            final var parts = line.split(" ");
            return new DigInstruction(parts[0].charAt(0), Integer.parseInt(parts[1]), parts[2]);
        }
        DigInstruction convertColor() {
            final String num = color.substring(2, color.length()-2);
            final char codedDir = color.charAt(color.length()-2);
            final int codedLength = Integer.parseInt(num, 16);
            return switch(codedDir) {
            case '0' -> new DigInstruction('R', codedLength, "");
            case '1' -> new DigInstruction('D', codedLength, "");
            case '2' -> new DigInstruction('L', codedLength, "");
            case '3' -> new DigInstruction('U', codedLength, "");
            default -> throw new IllegalStateException("unknown coded dir: "+codedDir);
            };
        }
    }
}
