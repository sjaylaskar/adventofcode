/*
 * Id: Prob2RockPaperScissors.java 02-Dec-2022 SubhajoyLaskar
 * Copyright (©) 2022 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */

package com.japps.adventofcode.probs2022;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.japps.adventofcode.util.AbstractSolvable;
import com.japps.adventofcode.util.Loggable;

/**
 * The prob 2 rock paper scissors.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public final class Prob2RockPaperScissors extends AbstractSolvable implements Loggable {

    /** The instance. */
    private static final Prob2RockPaperScissors INSTANCE = instance();


    /**
     * Instantiates a new prob 02.
     */
    private Prob2RockPaperScissors() {

    }


    /**
     * Instance.
     *
     * @return the prob 02
     */
    private static Prob2RockPaperScissors instance() {

        return new Prob2RockPaperScissors();
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

    	final Map<String, Integer> pointsMap = new HashMap<>();
    	pointsMap.put("AX", 4);
    	pointsMap.put("AY", 8);
    	pointsMap.put("AZ", 3);
    	pointsMap.put("BX", 1);
    	pointsMap.put("BY", 5);
    	pointsMap.put("BZ", 9);
    	pointsMap.put("CX", 7);
    	pointsMap.put("CY", 2);
    	pointsMap.put("CZ", 6);

    	final Map<String, Integer> pointsMapExtd = new HashMap<>();
    	pointsMapExtd.put("AX", 3);
    	pointsMapExtd.put("AY", 4);
    	pointsMapExtd.put("AZ", 8);
    	pointsMapExtd.put("BX", 1);
    	pointsMapExtd.put("BY", 5);
    	pointsMapExtd.put("BZ", 9);
    	pointsMapExtd.put("CX", 2);
    	pointsMapExtd.put("CY", 6);
    	pointsMapExtd.put("CZ", 7);

    	int totalScore = 0;
    	int extdTotalScore = 0;
    	System.out.println(lines.size());
    	for (final String line : lines) {
    		final String[] scoreChars = line.split(" ");
    		final String scoreKey = scoreChars[0] + scoreChars[1];
    		final int score = pointsMap.get(scoreKey);
    		final int extdScore = pointsMapExtd.get(scoreKey);
    		info(line + " scoreKey: "  + scoreKey + " score: " + score + " extdScore: " + extdScore);
			totalScore += score;
			extdTotalScore += extdScore;
    	}
    	info(totalScore);
    	info(extdTotalScore);
    }

}
