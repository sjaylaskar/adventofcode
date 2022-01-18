/*
 * Id: Prob10SyntaxScoreComputer.java 10-Dec-2021 10:34:12 am SubhajoyLaskar
 * Copyright (©) 2021 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.japps.adventofcode.probs2021;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.japps.adventofcode.util.AbstractSolvable;
import com.japps.adventofcode.util.Loggable;


/**
 * The prob 10 syntax score computer.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public final class Prob10SyntaxScoreComputer extends AbstractSolvable implements Loggable {

    /** The instance. */
    private static final Prob10SyntaxScoreComputer INSTANCE = instance();

    /**
     * Instantiates a new prob 10 syntax score computer.
     */
    private Prob10SyntaxScoreComputer() {

    }

    /**
     * Instance.
     *
     * @return the prob 10 syntax score computer
     */
    private static Prob10SyntaxScoreComputer instance() {

        return new Prob10SyntaxScoreComputer();
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

    /** The opening brackets scores. */
    private static final Map<Character, Integer> OPENING_BRACKETS_SCORES = new HashMap<>(4);
    static {
        OPENING_BRACKETS_SCORES.put('(', 1);
        OPENING_BRACKETS_SCORES.put('[', 2);
        OPENING_BRACKETS_SCORES.put('{', 3);
        OPENING_BRACKETS_SCORES.put('<', 4);
    }

    /** The opening brackets sum scores. */
    private static final Map<Character, Integer> OPENING_BRACKETS_SUM_SCORES = new HashMap<>(4);
    static {
        OPENING_BRACKETS_SUM_SCORES.put('(', 3);
        OPENING_BRACKETS_SUM_SCORES.put('[', 57);
        OPENING_BRACKETS_SUM_SCORES.put('{', 1197);
        OPENING_BRACKETS_SUM_SCORES.put('<', 25137);
    }

    /** The close open brackets. */
    private static final Map<Character, Character> CLOSE_OPEN_BRACKETS = new HashMap<>(4);
    static {
        CLOSE_OPEN_BRACKETS.put(')', '(');
        CLOSE_OPEN_BRACKETS.put(']', '[');
        CLOSE_OPEN_BRACKETS.put('}', '{');
        CLOSE_OPEN_BRACKETS.put('>', '<');
    }

    /**
     * Compute.
     *
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private void compute() throws IOException {

        final List<String> lines = lines();

        long sum = 0;
        final List<Long> scores = new ArrayList<>();

        for (final String line : lines) {
            boolean illegalCharFound = false;

            final LinkedList<Character> charList = new LinkedList<>();

            for (final Character character : line.toCharArray()) {
                if (OPENING_BRACKETS_SCORES.containsKey(character)) {
                    charList.add(character);
                } else {
                    final IllegalCharFoundScoreTuple illegalCharFoundScoreTuple = illegalCharFoundScoreTuple(charList,
                        CLOSE_OPEN_BRACKETS.get(character));
                    if (illegalCharFound = illegalCharFoundScoreTuple.illegalCharFound) {
                        sum += illegalCharFoundScoreTuple.score;
                        break;
                    }
                }
            }
            computeScores(charList, scores, illegalCharFound);
        }

        info("Illegal score: " + sum);
        Collections.sort(scores);
        info("Fix score: " + scores.get(scores.size() / 2));
    }

    /**
     * Illegal char found score tuple.
     *
     * @param charList the char list
     * @param checkChar the check char
     * @return the illegal char found score tuple
     */
    private IllegalCharFoundScoreTuple illegalCharFoundScoreTuple(final LinkedList<Character> charList, final char checkChar) {

        boolean illegalCharFound = false;
        long score = 0;
        if (illegalCharFound = charList.peekLast() != checkChar) {
            score = OPENING_BRACKETS_SUM_SCORES.get(checkChar);
        } else {
            charList.pollLast();
        }

        return new IllegalCharFoundScoreTuple(illegalCharFound, score);
    }

    /**
     * Compute scores.
     *
     * @param charList the char list
     * @param scores the scores
     * @param illegalCharFound the illegal char found
     */
    private void computeScores(final LinkedList<Character> charList, final List<Long> scores, final boolean illegalCharFound) {

        if (!illegalCharFound) {
            long score = 0;
            Collections.reverse(charList);
            for (final Character c : charList) {
                score = 5 * score + OPENING_BRACKETS_SCORES.get(c);
            }
            scores.add(score);
        }
    }

    /**
     * The illegal char found score tuple.
     *
     * @author Subhajoy Laskar
     * @version 1.0
     */
    private static final class IllegalCharFoundScoreTuple {

        /** The illegal char found. */
        private final boolean illegalCharFound;

        /** The score. */
        private final long score;

        /**
         * Instantiates a new illegal char found score tuple.
         *
         * @param illegalCharFound the illegal char found
         * @param score the score
         */
        public IllegalCharFoundScoreTuple(final boolean illegalCharFound, final long score) {

            this.illegalCharFound = illegalCharFound;
            this.score = score;
        }
    }
}
