/*
 * Id: Prob5CrateStackMovement.java 05-Dec-2022 SubhajoyLaskar
 * Copyright (©) 2022 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */


package com.japps.adventofcode.probs2022;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.commons.lang3.StringUtils;

import com.japps.adventofcode.util.AbstractSolvable;
import com.japps.adventofcode.util.Loggable;


/**
 * The prob 5 crate stack movement.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public final class Prob5CrateStackMovement extends AbstractSolvable implements Loggable {

	/** The instance. */
	private static final Prob5CrateStackMovement INSTANCE = instance();

	/**
	 * Instantiates a new prob 5 crate stack movement.
	 */
	private Prob5CrateStackMovement() {

	}

	/**
	 * Instance.
	 *
	 * @return the prob 5 crate stack movement
	 */
	private static Prob5CrateStackMovement instance() {

		return new Prob5CrateStackMovement();
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

		final List<Stack<Character>> stacks = buildStacks(lines);
		final List<Stack<Character>> copyStacks = copyOf(stacks);
		final List<String> moveLines = buildMoveConfigurationLines(lines);
		computeForSinglePop(stacks, moveLines);
		computeForMultiPop(copyStacks, moveLines);
	}

	/**
	 * Builds the stacks.
	 *
	 * @param lines the lines
	 * @return the list
	 */
	private List<Stack<Character>> buildStacks(final List<String> lines) {
		final Stack<String> stackOfStackLines = new Stack<>();
		lines.stream().filter(line -> StringUtils.startsWith(line, "[")).forEach(stackLine -> stackOfStackLines.add(stackLine));
		final String []stackIndices = lines.get(stackOfStackLines.size()).trim().split("   ");
		final List<Stack<Character>> stacks = new ArrayList<>();
		IntStream.range(0, Integer.valueOf(stackIndices[stackIndices.length - 1])).forEach(index -> stacks.add(new Stack<>()));
		while (!stackOfStackLines.isEmpty()) {
			final String stackLine = stackOfStackLines.pop();
			IntStream.range(0, stackIndices.length).forEach(stackIndex -> {
				final char stackLineElement = stackLine.charAt(4 * stackIndex + 1);
				if (StringUtils.isNotBlank(String.valueOf(stackLineElement).trim())) {
					stacks.get(stackIndex).add(stackLineElement);
				}
			});
		}
		info("Stacks: " + stacks);
		return stacks;
	}

	/**
	 * Copy of.
	 *
	 * @param stacks the stacks
	 * @return the list
	 */
	private List<Stack<Character>> copyOf(final List<Stack<Character>> stacks) {
		final List<Stack<Character>> copyStacks = new ArrayList<>();
		stacks.forEach(stack -> {
			final Stack<Character> copyStack = new Stack<>();
			copyStack.addAll(stack);
			copyStacks.add(copyStack);
		});
		return copyStacks;
	}

	/**
	 * Builds the move configuration lines.
	 *
	 * @param lines the lines
	 * @return the list
	 */
	private List<String> buildMoveConfigurationLines(final List<String> lines) {
		return lines.stream().filter(line -> StringUtils.startsWith(line, "move")).toList();
	}


	/**
	 * Compute for single pop.
	 *
	 * @param stacks the stacks
	 * @param moveLines the move lines
	 */
	private void computeForSinglePop(final List<Stack<Character>> stacks, final List<String> moveLines) {
		moveLines.forEach(moveLine -> {
			final RearrangementProcedure rearrangementProcedure = RearrangementProcedure.of(moveLine);
			IntStream.range(0, rearrangementProcedure.cratesToMove).forEach(x -> stacks.get(rearrangementProcedure.dest).add(stacks.get(rearrangementProcedure.src).pop()));
		});
		info("Single pop tops: " + stackTops(stacks));
	}

	/**
	 * Compute for multi pop.
	 *
	 * @param stacks the stacks
	 * @param moveLines the move lines
	 */
	private void computeForMultiPop(final List<Stack<Character>> stacks, final List<String> moveLines) {
		moveLines.forEach(moveLine -> {
			final RearrangementProcedure rearrangementProcedure = RearrangementProcedure.of(moveLine);
			final Stack<Character> crateStackToMove = new Stack<>();
			IntStream.range(0, rearrangementProcedure.cratesToMove).forEach(x -> crateStackToMove.add(stacks.get(rearrangementProcedure.src).pop()));
			while (!crateStackToMove.isEmpty()) {
				stacks.get(rearrangementProcedure.dest).add(crateStackToMove.pop());
			}
		});

		info("Multi pop tops: " + stackTops(stacks));
	}

	/**
	 * The rearrangement procedure.
	 *
	 * @author Subhajoy Laskar
	 * @version 1.0
	 */
	private static final class RearrangementProcedure {

		/**
		 * Instantiates a new rearrangement procedure.
		 */
		private RearrangementProcedure() {
		}

		/** The crates to move. */
		private int cratesToMove;

		/** The src. */
		private int src;

		/** The dest. */
		private int dest;

		/**
		 * Of.
		 *
		 * @param rearrangementProcedureMessage the rearrangement procedure message
		 * @return the rearrangement procedure
		 */
		private static final RearrangementProcedure of(final String rearrangementProcedureMessage) {
			final String[] move = rearrangementProcedureMessage.split(" ");
			return of(Integer.valueOf(move[1]), Integer.valueOf(move[3]) - 1, Integer.valueOf(move[5]) - 1);
		}

		/**
		 * Of.
		 *
		 * @param cratesToMove the crates to move
		 * @param src the src
		 * @param dest the dest
		 * @return the rearrangement procedure
		 */
		private static final RearrangementProcedure of(final int cratesToMove, final int src, final int dest) {
			final RearrangementProcedure rearrangementProcedure = new RearrangementProcedure();
			rearrangementProcedure.cratesToMove = cratesToMove;
			rearrangementProcedure.src = src;
			rearrangementProcedure.dest = dest;
			return rearrangementProcedure;
		}
	}

	/**
	 * Stack tops.
	 *
	 * @param stacks the stacks
	 * @return the string
	 */
	private String stackTops(final List<Stack<Character>> stacks) {
		return stacks.stream().map(Stack::pop).map(String::valueOf).collect(Collectors.joining(""));
	}
}
