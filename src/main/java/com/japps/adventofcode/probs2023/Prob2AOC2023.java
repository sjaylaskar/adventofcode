/*
 * Id: Prob2AOC2023.java 02-Dec-2023 SubhajoyLaskar
 * Copyright (©) 2023 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.japps.adventofcode.probs2023;

import java.io.IOException;
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
public final class Prob2AOC2023 extends AbstractSolvable implements Loggable {

    /** The instance. */
    private static final Prob2AOC2023 INSTANCE = instance();


    /**
     * Instantiates a new prob 02.
     */
    private Prob2AOC2023() {

    }


    /**
     * Instance.
     *
     * @return the prob 02
     */
    private static Prob2AOC2023 instance() {

        return new Prob2AOC2023();
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

    /** The Constant COLOR_MAX_MAP. */
    private static final Map<String, Integer> COLOR_MAX_MAP = new HashMap<>();
    static {
    	COLOR_MAX_MAP.put("red", 12);
    	COLOR_MAX_MAP.put("green", 13);
    	COLOR_MAX_MAP.put("blue", 14);
    }

    /**
     * Compute.
     *
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private void compute() throws IOException {
    	final List<String> lines = lines();
    	long idSum = 0;
    	long gameSum = 0;
    	for (final String line : lines) {
    		 final Map<String, Integer> colorBallsCountMap = new HashMap<>();
    		 final String[] parts = line.split(":");
    		 final String[] gameBalls = parts[1].trim().split(";");
    		 boolean isGamePossible = true;
    		 for (final String gameSet : gameBalls) {
    			 final String[] individualSets = gameSet.trim().split(",");
    			 for (final String ballCountSet : individualSets) {
    				  final String[] ballCounts = ballCountSet.trim().split(" ");
    				  final String color = ballCounts[1].trim();
    				  final int ballCount = Integer.valueOf(ballCounts[0].trim());
					  colorBallsCountMap.putIfAbsent(color, 0);
    				  colorBallsCountMap.put(color, Math.max(colorBallsCountMap.get(color), ballCount));
    				  if (ballCount > COLOR_MAX_MAP.get(color)) {
    					  isGamePossible = false;
    				  }
    			 }
    		 }
    		 gameSum += colorBallsCountMap.values().stream().reduce(1, (a, b) -> a * b);
    		 if (isGamePossible) {
    			 idSum += Integer.valueOf(parts[0].trim().split(" ")[1].trim());
    		 }
    	}
    	println(idSum);
    	println(gameSum);
    }
}
