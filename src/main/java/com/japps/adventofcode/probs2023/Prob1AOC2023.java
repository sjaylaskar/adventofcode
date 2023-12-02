/*
 * Id: Prob1AOC2023.java 02-Dec-2023 SubhajoyLaskar
 * Copyright (©) 2023 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.japps.adventofcode.probs2023;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.japps.adventofcode.util.AbstractSolvable;
import com.japps.adventofcode.util.Loggable;

/**
 * The prob 1 AOC 2023.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public final class Prob1AOC2023 extends AbstractSolvable implements Loggable {

    /** The instance. */
    private static final Prob1AOC2023 INSTANCE = instance();


    /**
     * Instantiates a new prob 01.
     */
    private Prob1AOC2023() {

    }


    /**
     * Instance.
     *
     * @return the prob 01
     */
    private static Prob1AOC2023 instance() {

        return new Prob1AOC2023();
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

    /** The Constant ALPHA_NUM_MAP. */
    private static final Map<String, Integer> ALPHA_NUM_MAP = new HashMap<>();

    static {
    	ALPHA_NUM_MAP.put("one", 1);
    	ALPHA_NUM_MAP.put("two", 2);
    	ALPHA_NUM_MAP.put("three", 3);
    	ALPHA_NUM_MAP.put("four", 4);
    	ALPHA_NUM_MAP.put("five", 5);
    	ALPHA_NUM_MAP.put("six", 6);
    	ALPHA_NUM_MAP.put("seven", 7);
    	ALPHA_NUM_MAP.put("eight", 8);
    	ALPHA_NUM_MAP.put("nine", 9);
    }

    /**
     * Compute.
     *
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private void compute() throws IOException {
    	long sum1 = 0;
    	long sum2 = 0;
    	final List<String> lines = lines();
    	for (final String line : lines) {
    		 final List<Integer> nums1 = new ArrayList<>();
        	 final List<Integer> nums2 = new ArrayList<>();
    		 for (int i = 0; i < line.length(); i++) {
    			  final char c = line.charAt(i);
    			  if (Character.isDigit(c)) {
    				  final Integer digit = Integer.valueOf(String.valueOf(c));
					  nums1.add(digit);
    				  nums2.add(digit);
    			  }
    			  for (final String alphaNum : ALPHA_NUM_MAP.keySet()) {
    				  if (line.substring(i).startsWith(alphaNum)) {
    					  nums2.add(ALPHA_NUM_MAP.get(alphaNum));
    				  }
    			  }
    		 }
    		 sum1 += 10 * nums1.get(0) + nums1.get(nums1.size() - 1);
    		 sum2 += 10 * nums2.get(0) + nums2.get(nums2.size() - 1);
    	}
    	println(sum1);
    	println(sum2);
    }
}
