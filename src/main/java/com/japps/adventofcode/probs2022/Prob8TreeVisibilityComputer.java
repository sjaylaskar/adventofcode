/*
 * Id: Prob8TreeVisibilityComputer.java 08-Dec-2022 SubhajoyLaskar
 * Copyright (©) 2022 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.japps.adventofcode.probs2022;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.IntPredicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import com.japps.adventofcode.util.AbstractSolvable;
import com.japps.adventofcode.util.IntPair;
import com.japps.adventofcode.util.Loggable;

/**
 * The prob 8 tree visibility computer.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public final class Prob8TreeVisibilityComputer extends AbstractSolvable implements Loggable {

	/** The instance. */
	private static final Prob8TreeVisibilityComputer INSTANCE = instance();

	/**
	 * Instantiates a new prob 8 tree visibility computer.
	 */
	private Prob8TreeVisibilityComputer() {

	}

	/**
	 * Instance.
	 *
	 * @return the prob 8 tree visibility computer
	 */
	private static Prob8TreeVisibilityComputer instance() {

		return new Prob8TreeVisibilityComputer();
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

	private static final Map<String, Integer> TREE_DIRECTION_INDICES = new HashMap<>();
	static {
		TREE_DIRECTION_INDICES.put("LEFT", 0);
		TREE_DIRECTION_INDICES.put("RIGHT", 1);
		TREE_DIRECTION_INDICES.put("UP", 2);
		TREE_DIRECTION_INDICES.put("DOWN", 3);
	}

	/**
	 * Compute.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private void compute() throws IOException {
		final List<String> lines = lines();
		final List<String> verticalLines = buildVerticalLines(lines);
		countVisibleTrees(lines, verticalLines);
		countHighestScenicScore(lines, verticalLines);
	}

	/**
	 * Count visible trees.
	 *
	 * @param lines the lines
	 */
	private void countVisibleTrees(final List<String> lines, final List<String> verticalLines) {
		int visibleTreesCount = 0;
		for (int i = 1; i <= lines.size() - 2; i++) {
			for (int j = 1; j <= lines.get(0).length() - 2; j++) {
				final char c = lines.get(i).charAt(j);
				final List<IntPair> treeDirectionIndexPairs = buildTreeDirectionIndexPairs(j, i,
						lines.get(0).length(), lines.size());
				final Map<String, String> treeLinesByDirectionNameMap =
						buildTreeLinesByDirectionNameMap(j, i, lines, verticalLines, treeDirectionIndexPairs);
				if (isNotBlocked(c, treeLinesByDirectionNameMap)) {
					visibleTreesCount++;
				}
			}
		}
		visibleTreesCount += 4 * (lines.get(0).length() - 1);
		info("Visible trees: " + visibleTreesCount);
	}

	/**
	 * Count highest scenic score.
	 *
	 * @param lines the lines
	 */
	/*
	 * private void countHighestScenicScore(final List<String> lines, final
	 * List<String> verticalLines) {
	 *
	 * int maxScenicScore = Integer.MIN_VALUE; for (int i = 1; i <= lines.size() -
	 * 2; i++) { for (int j = 1; j <= lines.get(0).length() - 2; j++) { final char c
	 * = lines.get(i).charAt(j); final List<IntPair> treeDirectionIndexPairs =
	 * buildTreeDirectionIndexPairs(j, i, lines.get(0).length(), lines.size());
	 * final Map<String, String> treeLinesByDirectionNameMap =
	 * buildTreeLinesByDirectionNameMap(j, i, lines, verticalLines,
	 * treeDirectionIndexPairs); final int scenicScore =
	 * treeLinesByDirectionNameMap.entrySet().stream() .mapToInt(treeLinesEntry ->
	 * scenicScore(c, treeLinesEntry.getValue(), treeLinesEntry.getKey(),
	 * treeDirIndexPair(treeDirectionIndexPairs, treeLinesEntry.getKey())))
	 * .reduce(1, Math::multiplyExact); maxScenicScore = Math.max(maxScenicScore,
	 * scenicScore);
	 *
	 * } } info("Max scenic score: " + maxScenicScore); }
	 */

	private void countHighestScenicScore(final List<String> lines, final List<String> verticalLines) {

		int maxScenicScore = Integer.MIN_VALUE;
		final int []maxScenicSpotIndices = new int[2];
		for (int i = 1; i <= lines.size() - 2; i++) {
			for (int j = 1; j <= lines.get(0).length() - 2; j++) {
				final char c = lines.get(i).charAt(j);
				final List<IntPair> treeDirectionIndexPairs = buildTreeDirectionIndexPairs(j, i,
						lines.get(0).length(), lines.size());
				final Map<String, String> treeLinesByDirectionNameMap
				= buildTreeLinesByDirectionNameMap(j, i, lines, verticalLines, treeDirectionIndexPairs);
				info("Tree at: (" + i + ", " + j + "): " + c);
				for (final Map.Entry<String, String> directionTreeLineEntry : treeLinesByDirectionNameMap.entrySet()) {
					info("Direction: " + directionTreeLineEntry.getKey() + ", Tree line: " + directionTreeLineEntry.getValue());
				}
				/*
				 * final int scenicScore = treeLinesByDirectionNameMap.entrySet().stream()
				 * .mapToInt(treeLinesEntry -> scenicScore(c, treeLinesEntry.getValue(),
				 * treeLinesEntry.getKey(), treeDirIndexPair(treeDirectionIndexPairs,
				 * treeLinesEntry.getKey()))) .reduce(1, Math::multiplyExact);
				 */
				final int scenicScore = 0;
				if (maxScenicScore < scenicScore) {
					maxScenicSpotIndices[0] = i;
					maxScenicSpotIndices[1] = j;
					maxScenicScore = scenicScore;
				}
			}
		}
		info("Max scenic score: " + maxScenicScore);
		info("Max scenic indices: " + ArrayUtils.toString(maxScenicSpotIndices));
	}

	/**
	 * Builds the vertical lines.
	 *
	 * @param lines the lines
	 * @return the list
	 */
	private List<String> buildVerticalLines(final List<String> lines) {
		final List<String> verticalLines = new ArrayList<>();
		IntStream.range(0, lines.get(0).length()).forEach(index -> verticalLines
				.add(lines.stream().map(line -> String.valueOf(line.charAt(index))).collect(Collectors.joining())));
		return verticalLines;
	}

	/**
	 * Tree dir index pair.
	 *
	 * @param treeDirectionIndexPairs the tree direction index pairs
	 * @param treeDirectionName the tree direction name
	 * @return the int pair
	 */
	private IntPair treeDirIndexPair(final List<IntPair> treeDirectionIndexPairs, final String treeDirectionName) {
		return treeDirectionIndexPairs.get(TREE_DIRECTION_INDICES.get(treeDirectionName));
	}

	/**
	 * Builds the tree lines by direction name map.
	 *
	 * @param horizontalIndex the horizontal index
	 * @param verticalIndex the vertical index
	 * @param lines the lines
	 * @param treeDirectionIndexPairs the tree direction index pairs
	 * @return the map
	 */
	private Map<String, String> buildTreeLinesByDirectionNameMap(final int horizontalIndex, final int verticalIndex,
		final List<String> lines, final List<String> verticalLines, final List<IntPair> treeDirectionIndexPairs) {
		return Map.ofEntries(
				Map.entry("LEFT", buildTreeLine("LEFT", verticalIndex, lines, treeDirectionIndexPairs)),
				Map.entry("RIGHT", buildTreeLine("RIGHT", verticalIndex, lines, treeDirectionIndexPairs)),
				Map.entry("UP", buildTreeLine("UP", horizontalIndex, verticalLines, treeDirectionIndexPairs)),
				Map.entry("DOWN", buildTreeLine("DOWN", horizontalIndex, verticalLines, treeDirectionIndexPairs)));
	}

	/**
	 * Builds the tree line.
	 *
	 * @param treeDirectionName the tree direction name
	 * @param index the index
	 * @param lines the lines
	 * @param treeDirectionIndexPairs the tree direction index pairs
	 * @return the string
	 */
	private String buildTreeLine(final String treeDirectionName, final int index, final List<String> lines,
			final List<IntPair> treeDirectionIndexPairs) {
		final IntPair treeDirIndexPair = treeDirIndexPair(treeDirectionIndexPairs, treeDirectionName);
		return lines.get(index).substring(treeDirIndexPair.getX(), treeDirIndexPair.getY());
	}

	/**
	 * Scenic score.
	 *
	 * @param c the c
	 * @param treeDirection the tree direction
	 * @param treeDirectionName the tree direction name
	 * @param treeDirectionIndexPair the tree direction index pair
	 * @return the int
	 */
	private int scenicScore(final char c, final String treeDirection, final String treeDirectionName,
			final IntPair treeDirectionIndexPair) {
		return isNotBlocked(treeDirection, c) ? treeDirection.length()
						: StringUtils.equals("RIGHT", treeDirectionName) || StringUtils.equals("DOWN", treeDirectionName)
						  ? determineBlockedTreeIndex(treeDirection, c) + 1
						  : treeDirection.length() - determineBlockedTreeIndex(treeDirection, c);
	}

	/**
	 * Determine tree direction index pairs.
	 *
	 * @param horizontalIndex the horizontal index
	 * @param verticalIndex the vertical index
	 * @param horizontalNumberOfTrees the horizontal number of trees
	 * @param verticalNumberOfTrees the vertical number of trees
	 * @return the list
	 */
	private List<IntPair> buildTreeDirectionIndexPairs(final int horizontalIndex, final int verticalIndex,
			final int horizontalNumberOfTrees, final int verticalNumberOfTrees) {
		return List.of(IntPair.of(0, horizontalIndex), IntPair.of(horizontalIndex + 1, horizontalNumberOfTrees),
				IntPair.of(0, verticalIndex), IntPair.of(verticalIndex + 1, verticalNumberOfTrees));
	}

	/**
	 * Determine blocked tree index.
	 *
	 * @param treeDirection the tree direction
	 * @param c the c
	 * @return the int
	 */
	private int determineBlockedTreeIndex(final String treeDirection, final char c) {
		return treeDirection.indexOf((char) treeDirection.chars().filter(isBlocked(c)).findFirst().getAsInt());
	}

	/**
	 * Checks if is not blocked.
	 *
	 * @param c the c
	 * @param treeLinesByDirectionNameMap the tree lines by direction name map
	 * @return true, if is not blocked
	 */
	private boolean isNotBlocked(final char c, final Map<String, String> treeLinesByDirectionNameMap) {
		return treeLinesByDirectionNameMap.entrySet().stream()
				.anyMatch(treeLineEntry -> isNotBlocked(treeLineEntry.getValue(), c));
	}

	/**
	 * Checks if is not blocked.
	 *
	 * @param treesDirection the trees direction
	 * @param c the c
	 * @return true, if is not blocked
	 */
	private boolean isNotBlocked(final String treesDirection, final char c) {
		return treesDirection.chars().noneMatch(isBlocked(c));
	}

	/**
	 * Checks if is blocked.
	 *
	 * @param c the c
	 * @return the int predicate
	 */
	private IntPredicate isBlocked(final char c) {
		return x -> (char) x >= c;
	}

}
