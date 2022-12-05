/*
 * Id: Prob5CrateStackMovement.java 05-Dec-2022 11:19:11 am SubhajoyLaskar
 * Copyright (©) 2022 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */

package com.japps.adventofcode.probs2022;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

		computeForSinglePop(lines);
		computeForMultiPop(lines);
	}

	/**
	 * Compute for single pop.
	 *
	 * @param lines the lines
	 */
	private void computeForSinglePop(final List<String> lines) {
		final List<Stack<Character>> stacks = buildStacks();

		IntStream.range(10, 512).forEach(lineNumber -> {
			final RearrangementProcedure rearrangementProcedure = RearrangementProcedure.of(lines.get(lineNumber));
			IntStream.range(0, rearrangementProcedure.cratesToMove).forEach(x -> stacks.get(rearrangementProcedure.dest).add(stacks.get(rearrangementProcedure.src).pop()));
		});
		info("Single pop tops: " + stackTops(stacks));
	}

	/**
	 * Compute for multi pop.
	 *
	 * @param lines the lines
	 */
	private void computeForMultiPop(final List<String> lines) {
		final List<Stack<Character>> stacks = buildStacks();

		IntStream.range(10, 512).forEach(lineNumber -> {
			final RearrangementProcedure rearrangementProcedure = RearrangementProcedure.of(lines.get(lineNumber));
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

	/**
	 * Builds the stacks.
	 *
	 * @return the list
	 */
	private List<Stack<Character>> buildStacks() {
		final Stack<Character> stack1 = new Stack<>();
		stack1.addAll(Arrays.asList('B', 'P', 'N', 'Q', 'H', 'D', 'R', 'T'));
		final Stack<Character> stack2 = new Stack<>();
		stack2.addAll(Arrays.asList('W', 'G', 'B', 'J', 'T', 'V'));
		final Stack<Character> stack3 = new Stack<>();
		stack3.addAll(Arrays.asList('N', 'R', 'H', 'D', 'S', 'V', 'M', 'Q'));
		final Stack<Character> stack4 = new Stack<>();
		stack4.addAll(Arrays.asList('P', 'Z', 'N', 'M', 'C'));
		final Stack<Character> stack5 = new Stack<>();
		stack5.addAll(Arrays.asList('D', 'Z', 'B'));
		final Stack<Character> stack6 = new Stack<>();
		stack6.addAll(Arrays.asList('V', 'C', 'W', 'Z'));
		final Stack<Character> stack7 = new Stack<>();
		stack7.addAll(Arrays.asList('G', 'Z', 'N', 'C', 'V', 'Q', 'L', 'S'));
		final Stack<Character> stack8 = new Stack<>();
		stack8.addAll(Arrays.asList('L', 'G', 'J', 'M', 'D', 'N', 'V'));
		final Stack<Character> stack9 = new Stack<>();
		stack9.addAll(Arrays.asList('T', 'P', 'M', 'F', 'Z', 'C', 'J'));

		return new ArrayList<>(Arrays.asList(stack1, stack2, stack3, stack4, stack5, stack6, stack7, stack8, stack9));
	}
}
