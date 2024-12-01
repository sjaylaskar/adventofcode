/*
 * Id: Prob18AOC2023.java 18-Dec-2023 SubhajoyLaskar
 * Copyright (©) 2023 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */

package com.japps.adventofcode.probs2023;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import com.japps.adventofcode.util.AbstractSolvable;
import com.japps.adventofcode.util.Loggable;

/**
 * The prob 19 AOC 2023.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public final class Prob19AOC2023 extends AbstractSolvable implements Loggable {

	/** The instance. */
	private static final Prob19AOC2023 INSTANCE = instance();

	/**
	 * Instantiates a new prob 19 AOC 2023.
	 */
	private Prob19AOC2023() {

	}

	/**
	 * Instance.
	 *
	 * @return the prob 19 AOC 2023
	 */
	private static Prob19AOC2023 instance() {

		return new Prob19AOC2023();
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

		final Map<String, List<String>> workflowMap = new HashMap<>();

		final List<Part> acceptedParts = new ArrayList<>();

		boolean workflowsProcessed = false;
		for (final String line : lines) {
			if (StringUtils.isBlank(line)) {
				workflowsProcessed = true;
				// println(workflowMap);
				continue;
			}
			if (!workflowsProcessed) {
				final String workflowName = line.substring(0, line.indexOf('{'));
				final List<String> workflowInstructions = Arrays
						.asList(line.substring(line.indexOf('{') + 1, line.lastIndexOf('}')).split(","));
				workflowMap.put(workflowName, workflowInstructions);
			} else {
				final Map<String, Long> partValuesMap = Arrays
						.asList(line.substring(1, line.lastIndexOf('}')).split(",")).stream().collect(Collectors
								.toMap(value -> value.split("=")[0], value -> Long.valueOf(value.split("=")[1])));
				final Part part = Part.of(partValuesMap);
				// println(part);
				if (Status.A.equals(process(part, workflowMap))) {
					acceptedParts.add(part);
				}
			}
		}

		println(acceptedParts.stream().mapToLong(Part::sum).sum());

	}

	/**
	 * Process.
	 *
	 * @param part        the part
	 * @param workflowMap the workflow map
	 * @return the status
	 */
	private Status process(final Part part, final Map<String, List<String>> workflowMap) {

		boolean finished = false;

		final List<String> firstInstructions = workflowMap.get("in");

		String nextStepName = process(part, firstInstructions);

		finished = isFinished(nextStepName);

		while (!finished) {
			nextStepName = process(part, workflowMap.get(nextStepName));
			finished = isFinished(nextStepName);
		}

		return Status.getByName(nextStepName);
	}

	/**
	 * Checks if is finished.
	 *
	 * @param nextStepName the next step name
	 * @return true, if is finished
	 */
	private boolean isFinished(final String nextStepName) {
		return Status.A.name().equals(nextStepName) || Status.R.name().equals(nextStepName);
	}

	/**
	 * Process.
	 *
	 * @param part         the part
	 * @param instructions the instructions
	 * @return the string
	 */
	private String process(final Part part, final List<String> instructions) {
		for (final String instruction : instructions) {
			if (Status.A.name().equals(instruction) || Status.R.name().equals(instruction)
					|| !instruction.contains(":")) {
				return instruction;
			}
			final String[] instructionParts = instruction.split(":");
			final String nextWorkflowName = instructionParts[1];
			final String condition = instructionParts[0];
			final Conditional conditional = Conditional.getBySymbol(condition.contains("<") ? "<" : ">");
			final String[] conditionParts = condition.split(conditional.symbol);
			if (isSatisfied(part, conditionParts, conditional)) {
				return nextWorkflowName;
			}
		}
		return Status.R.name();
	}

	/**
	 * Checks if is satisfied.
	 *
	 * @param part           the part
	 * @param conditionParts the condition parts
	 * @param conditional    the conditional
	 * @return true, if is satisfied
	 */
	private boolean isSatisfied(final Part part, final String[] conditionParts, final Conditional conditional) {
		return switch (conditionParts[0]) {
		case "x" -> conditional.operate(part.getX(), Long.valueOf(conditionParts[1]));
		case "m" -> conditional.operate(part.getM(), Long.valueOf(conditionParts[1]));
		case "a" -> conditional.operate(part.getA(), Long.valueOf(conditionParts[1]));
		case "s" -> conditional.operate(part.getS(), Long.valueOf(conditionParts[1]));
		default -> false;
		};
	}

	/**
	 * The conditional.
	 *
	 * @author Subhajoy Laskar
	 * @version 1.0
	 */
	enum Conditional {

		/** The less. */
		LESS("<"),
		/** The greater. */
		GREATER(">");

		/** The symbol. */
		private String symbol;

		/**
		 * Instantiates a new conditional.
		 *
		 * @param symbol the symbol
		 */
		private Conditional(final String symbol) {
			this.symbol = symbol;
		}

		/**
		 * Symbol.
		 *
		 * @return the symbol
		 */
		public String symbol() {
			return symbol;
		}

		/**
		 * Gets the by symbol.
		 *
		 * @param symbol the symbol
		 * @return the by symbol
		 */
		static Conditional getBySymbol(final String symbol) {
			return "<".equals(symbol) ? LESS : GREATER;
		}

		/**
		 * Operate.
		 *
		 * @param value1 the value 1
		 * @param value2 the value 2
		 * @return true, if successful
		 */
		boolean operate(final long value1, final long value2) {
			return LESS == this ? value1 < value2 : value1 > value2;
		}
	}

	/**
	 * The status.
	 *
	 * @author Subhajoy Laskar
	 * @version 1.0
	 */
	enum Status {

		/** The a. */
		A,
		/** The r. */
		R;

		/**
		 * Gets the by name.
		 *
		 * @param name the name
		 * @return the by name
		 */
		static Status getByName(final String name) {
			return A.name().equals(name) ? A : R;
		}
	}

	/**
	 * The part.
	 *
	 * @author Subhajoy Laskar
	 * @version 1.0
	 */
	private static final class Part {

		/** The x. */
		private long x;

		/** The m. */
		private long m;

		/** The a. */
		private long a;

		/** The s. */
		private long s;

		/**
		 * Of.
		 *
		 * @param partValuesMap the part values map
		 * @return the part
		 */
		static Part of(final Map<String, Long> partValuesMap) {
			return new Part(partValuesMap.get("x"), partValuesMap.get("m"), partValuesMap.get("a"),
					partValuesMap.get("s"));
		}

		/**
		 * Instantiates a new part.
		 *
		 * @param x the x
		 * @param m the m
		 * @param a the a
		 * @param s the s
		 */
		public Part(final long x, final long m, final long a, final long s) {
			super();
			this.x = x;
			this.m = m;
			this.a = a;
			this.s = s;
		}

		/**
		 * Gets the x.
		 *
		 * @return the x
		 */
		public long getX() {
			return x;
		}

		/**
		 * Sets the x.
		 *
		 * @param x the x to set
		 */
		public void setX(final long x) {
			this.x = x;
		}

		/**
		 * Gets the m.
		 *
		 * @return the m
		 */
		public long getM() {
			return m;
		}

		/**
		 * Sets the m.
		 *
		 * @param m the m to set
		 */
		public void setM(final long m) {
			this.m = m;
		}

		/**
		 * Gets the a.
		 *
		 * @return the a
		 */
		public long getA() {
			return a;
		}

		/**
		 * Sets the a.
		 *
		 * @param a the a to set
		 */
		public void setA(final long a) {
			this.a = a;
		}

		/**
		 * Gets the s.
		 *
		 * @return the s
		 */
		public long getS() {
			return s;
		}

		/**
		 * Sets the s.
		 *
		 * @param s the s to set
		 */
		public void setS(final long s) {
			this.s = s;
		}

		/**
		 * Sum.
		 *
		 * @return the long
		 */
		public long sum() {
			return x + m + a + s;

		}

		/**
		 * To string.
		 *
		 * @return the string
		 */
		@Override
		public String toString() {
			return "Part [x=" + x + ", m=" + m + ", a=" + a + ", s=" + s + "]";
		}

	}
}
