/*
 * Id: Prob3LinePriorities.java 03-Dec-2022 SubhajoyLaskar
 * Copyright (©) 2022 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */

package com.japps.adventofcode.probs2022;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.japps.adventofcode.util.AbstractSolvable;
import com.japps.adventofcode.util.Loggable;

/**
 * The prob 3 line priorities.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public final class Prob3LinePriorities extends AbstractSolvable implements Loggable {

    /** The instance. */
    private static final Prob3LinePriorities INSTANCE = instance();


    /**
     * Instantiates a new prob 03.
     */
    private Prob3LinePriorities() {

    }


    /**
     * Instance.
     *
     * @return the prob 03
     */
    private static Prob3LinePriorities instance() {

        return new Prob3LinePriorities();
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

    /** The priority map. */
    private static final Map<Character, Integer> PRIORITY_MAP = new HashMap<>();

    static {
    	int priority = 1;
    	for (char c = 'a'; c <= 'z'; c++) {
    		PRIORITY_MAP.put(c, priority);
    		PRIORITY_MAP.put((char)(c - 32), priority + 26);
    		priority++;
    	}
    }

    /**
     * Compute.
     *
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private void compute() throws IOException {
    	final List<String> lines = lines();

    	info("Line priority sum: " + computeIndividualPrioritySum(lines));

    	info("Three grouped lines priority sum: " + computeGroupPrioritySum(lines));
    }


	/**
	 * Compute group priority sum.
	 *
	 * @param lines the lines
	 * @return the int
	 */
	private int computeGroupPrioritySum(final List<String> lines) {
		int groupPrioritySum = 0;
    	for (int lineIndex = 0; lineIndex < lines.size() - 2; lineIndex++) {
    		final Set<Integer> line1Chars = toCharSet(lines.get(lineIndex));
    		final Set<Integer> line2Chars = toCharSet(lines.get(++lineIndex));
    		final Set<Integer> line3Chars = toCharSet(lines.get(++lineIndex));
    		final Set<Integer> commonChars = line1Chars.stream().filter(c -> line2Chars.contains(c) && line3Chars.contains(c)).collect(Collectors.toSet());
    		groupPrioritySum += toPrioritySum(commonChars);
    	}
    	return groupPrioritySum;
	}

	/**
	 * Compute individual priority sum.
	 *
	 * @param lines the lines
	 * @return the int
	 */
	private int computeIndividualPrioritySum(final List<String> lines) {
		int prioritySum = 0;
		for (final String line : lines) {
			final int lineLength = line.length();
			final String linePart1 = line.substring(0, lineLength / 2);
			final String linePart2 = line.substring(lineLength / 2, lineLength);
			final Set<Integer> linePart1Chars = toCharSet(linePart1);
			final Set<Integer> linePart2Chars = toCharSet(linePart2);
			final Set<Integer> commonChars = linePart1Chars.stream().filter(c -> linePart2Chars.contains(c)).collect(Collectors.toSet());
			prioritySum += toPrioritySum(commonChars);
    	}
		return prioritySum;
	}

	/**
	 * To priority sum.
	 *
	 * @param commonChars the common chars
	 * @return the int
	 */
	private int toPrioritySum(final Set<Integer> commonChars) {
		return commonChars.stream().map(c -> PRIORITY_MAP.get((char)c.intValue())).mapToInt(Integer::intValue).sum();
	}

	/**
	 * To char set.
	 *
	 * @param line the line
	 * @return the sets the
	 */
	private Set<Integer> toCharSet(final String line) {
		return line.chars().boxed().collect(Collectors.toSet());
	}

}
