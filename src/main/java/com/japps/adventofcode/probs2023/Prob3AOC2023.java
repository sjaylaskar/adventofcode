/*
 * Id: Prob3AOC2023.java 03-Dec-2023 SubhajoyLaskar
 * Copyright (©) 2023 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.japps.adventofcode.probs2023;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.japps.adventofcode.util.AbstractSolvable;
import com.japps.adventofcode.util.IntPair;
import com.japps.adventofcode.util.Loggable;

/**
 * The prob 3 AOC 2023.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public final class Prob3AOC2023 extends AbstractSolvable implements Loggable {

	/** The instance. */
	private static final Prob3AOC2023 INSTANCE = instance();

	/**
	 * Instantiates a new prob 3 AOC 2023.
	 */
	private Prob3AOC2023() {

	}

	/**
	 * Instance.
	 *
	 * @return the prob 3 AOC 2023
	 */
	private static Prob3AOC2023 instance() {

		return new Prob3AOC2023();
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
		final List<String> lines = lines();

		processSum(lines);

	}


	/**
	 * Process sum.
	 *
	 * @param lines the lines
	 * @return the long
	 */
	private void processSum(final List<String> lines) {
		long sum = 0;
		final Map<IntPair, Long> gearRatioSumMapByIndex = new HashMap<>();
		for (int row = 0; row < lines.size(); row++) {
			final String line = lines.get(row);
			String num = "";
			for (int col = 0, numStartCol = col; col < line.length(); col++) {
				final char c = line.charAt(col);
				if (num == "" && c == '.') {
					continue;
				}
				if (Character.isDigit(c)) {
					if (num == "") {
						numStartCol = col;
					}
					num += c;
					if (col == line.length() - 1) {
						sum = processIfNotSymbol(lines, line, row, col, numStartCol, sum, num);
					}
				} else if (c != '.') {
					if (StringUtils.isNotBlank(num)) {
					    sum = addToSum(sum, num);
					    num = "";
					}
				} else if (StringUtils.isNotBlank(num)) {
					sum = processIfNotSymbol(lines, line, row, col, numStartCol, sum, num);
					num = "";
				}
				if (c == '*') {
			        processGearRatio(lines, line, IntPair.of(row, col), gearRatioSumMapByIndex);
				}
			}
		}
		println(sum);
		println(gearRatioSumMapByIndex.values().stream().mapToLong(Long::valueOf).sum());
	}

	/**
	 * Process if not symbol.
	 *
	 * @param lines the lines
	 * @param line the line
	 * @param row the row
	 * @param col the col
	 * @param numStartCol the num start col
	 * @param sum the sum
	 * @param num the num
	 * @return the long
	 */
	private long processIfNotSymbol(final List<String> lines, final String line, final int row, final int col,
			final int numStartCol, long sum, final String num) {
		if (numStartCol > 0) {
			final char sameLinePrevChar = line.charAt(numStartCol - 1);
			if (isSymbol(sameLinePrevChar)) {
				sum = addToSum(sum, num);
			}
		}
		if (row > 0) {
			final String prevLine = lines.get(row - 1);
			for (int prevCol = numStartCol - 1; prevCol <= col; prevCol++) {
				if (prevCol < 0) {
					continue;
				}
				final char prevChar = prevLine.charAt(prevCol);
				if (isSymbol(prevChar)) {
					sum = addToSum(sum, num);
				}
			}
		}
		if (row < lines.size() - 1) {
			final String nextLine = lines.get(row + 1);
			for (int prevCol = numStartCol - 1; prevCol <= col; prevCol++) {
				if (prevCol < 0) {
					continue;
				}
				final char nextChar = nextLine.charAt(prevCol);
				if (isSymbol(nextChar)) {
					sum = addToSum(sum, num);
				}
			}
		}
		return sum;
	}

	/**
	 * Process gear ratio.
	 *
	 * @param lines the lines
	 * @param line the line
	 * @param gearIndexPair the gear index pair
	 * @param gearRatioSumMapByIndex the gear ratio sum map by index
	 */
	private void processGearRatio(final List<String> lines, final String line, final IntPair gearIndexPair, final Map<IntPair, Long> gearRatioSumMapByIndex) {
		if (!gearRatioSumMapByIndex.containsKey(gearIndexPair)) {
			gearRatioSumMapByIndex.put(gearIndexPair, 1L);
			int gearNumCount = 0;
			// Process same line
			gearNumCount = processLine(gearIndexPair, gearRatioSumMapByIndex, gearNumCount, line);
			// Process previous line
			if (gearIndexPair.getX() > 0) {
				final String processLine = lines.get(gearIndexPair.getX() - 1);
				gearNumCount = processLine(gearIndexPair, gearRatioSumMapByIndex, gearNumCount, processLine);
			}
			// Process next line
			if (gearIndexPair.getX() < lines.size() - 1) {
				final String processLine = lines.get(gearIndexPair.getX() + 1);
				gearNumCount = processLine(gearIndexPair, gearRatioSumMapByIndex, gearNumCount, processLine);
			}
			if (gearNumCount != 2) {
				gearRatioSumMapByIndex.put(gearIndexPair, 0L);
			}
		}
	}

	/**
	 * Process part after gear col index.
	 *
	 * @param processLine the process line
	 * @param gearIndexPair the gear index pair
	 * @param gearRatioSumMapByIndex the gear ratio sum map by index
	 * @param gearNumCount the gear num count
	 * @param partNum the part num
	 * @return the int
	 */
	private int processPartAfterGearColIndex(final String processLine, final IntPair gearIndexPair,
			final Map<IntPair, Long> gearRatioSumMapByIndex, int gearNumCount, String partNum) {
		for (int index = gearIndexPair.getY() + 1; index < processLine.length(); index++) {
			final char c = processLine.charAt(index);
			if (Character.isDigit(c)) {
				partNum += c;
			} else {
				break;
			}
		}
		if (StringUtils.isNotBlank(partNum)) {
			gearNumCount = computeGear(gearIndexPair, gearRatioSumMapByIndex, gearNumCount, partNum);
			partNum = "";
		}
		return gearNumCount;
	}

	/**
	 * Compute gear.
	 *
	 * @param gearIndexPair the gear index pair
	 * @param gearRatioSumMapByIndex the gear ratio sum map by index
	 * @param gearNumCount the gear num count
	 * @param partNum the part num
	 * @return the int
	 */
	private int computeGear(final IntPair gearIndexPair, final Map<IntPair, Long> gearRatioSumMapByIndex,
			int gearNumCount, final String partNum) {
		gearNumCount++;
		gearRatioSumMapByIndex.put(gearIndexPair, gearRatioSumMapByIndex.get(gearIndexPair) * Long.valueOf(partNum));
		return gearNumCount;
	}

	/**
	 * Process line.
	 *
	 * @param gearIndexPair the gear index pair
	 * @param gearRatioSumMapByIndex the gear ratio sum map by index
	 * @param gearNumCount the gear num count
	 * @param processLine the process line
	 * @return the int
	 */
	private int processLine(final IntPair gearIndexPair, final Map<IntPair, Long> gearRatioSumMapByIndex, int gearNumCount,
			final String processLine) {
		String partNum = "";
		// Process index before.
		for (int index = gearIndexPair.getY() - 1; index >= 0; index--) {
			final char c = processLine.charAt(index);
			if (Character.isDigit(c)) {
				partNum += c;
			} else {
				break;
			}
		}
		partNum = new StringBuilder(partNum).reverse().toString();
		// Process on index.
		if (Character.isDigit(processLine.charAt(gearIndexPair.getY()))) {
			partNum += processLine.charAt(gearIndexPair.getY());
		} else if (StringUtils.isNotBlank(partNum)) {
			gearNumCount = computeGear(gearIndexPair, gearRatioSumMapByIndex, gearNumCount, partNum);
			partNum = "";
		}
		// Process index after.
		gearNumCount = processPartAfterGearColIndex(processLine, gearIndexPair, gearRatioSumMapByIndex, gearNumCount, partNum);
		return gearNumCount;
	}

	/**
	 * Adds the to sum.
	 *
	 * @param sum the sum
	 * @param num the num
	 * @return the long
	 */
	private long addToSum(long sum, final String num) {
		sum += Integer.valueOf(num);
		return sum;
	}

	/**
	 * Checks if is symbol.
	 *
	 * @param character the character
	 * @return true, if is symbol
	 */
	private boolean isSymbol(final char character) {
		return character != '.' && !Character.isDigit(character);
	}
}
