/*
 * Id: StringUtil.java 01-Jan-2014 01:11:56 am SubhajoyLaskar
 * Copyright (©) 2014 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.japps.adventofcode.util;

import java.util.*;
import java.util.stream.*;

import org.apache.commons.lang3.math.*;


/**
 * The string util.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public final class StringUtil {

    /**
     * Instantiates a new string util.
     */
    private StringUtil() {

    }

    /**
     * Sort.
     *
     * @param str the string
     * @return the sorted string
     */
    public static String sort(final String str) {

        final char[] strChars = str.toCharArray();
        Arrays.sort(strChars);
        return String.valueOf(strChars);
    }


    /**
     * To optional integer.
     *
     * @param c the c
     * @return the optional
     */
    public static Optional<Integer> toOptionalInteger(final char c) {

        final String str = String.valueOf(c);
        return (NumberUtils.isCreatable(str))
                ? Optional.of(Integer.parseInt(str)) : Optional.empty();
    }

    /**
     * To strict int.
     * Assumes that the character c is String-parsable to int.
     *
     * @param c the c
     * @return the int value (not ASCII)
     * @throws NumberFormatException if the character c is not String-parsable to int.
     */
    public static int toStrictInt(final char c) throws NumberFormatException {
        return Integer.parseInt(String.valueOf(c));
    }

    /**
     * Indicates if the {@code stringLine} can be constructed from the provided {@code tokens}.
     *
     * @param stringLine The string line.
     * @param tokens The tokens.
     * @return {@code true}, if the {@code stringLine} can be constructed from the provided {@code tokens}.
     */
    public static boolean isConstructable(String stringLine, Set<String> tokens) {
        boolean[] memo = new boolean[stringLine.length() + 1];
        memo[0] = true;
        IntStream.rangeClosed(1, stringLine.length())
                .filter(index -> tokens.stream().anyMatch(token -> index >= token.length() && memo[index - token.length()] && stringLine.startsWith(token, index - token.length())))
                .forEach(index -> memo[index] = true);
        return memo[stringLine.length()];
    }

    /**
     * Counts the number of ways that the {@code stringLine} can be constructed from the provided {@code tokens}.
     *
     * @param stringLine The string line.
     * @param tokens The tokens.
     * @return The number of ways that the {@code stringLine} can be constructed from the provided {@code tokens}. <i>0, if its not possible.</i>
     */
    public static long constructableWays(String stringLine, Set<String> tokens) {
        long[] memo = new long[stringLine.length() + 1];
        memo[0] = 1;
        IntStream.rangeClosed(1, stringLine.length())
                .forEach(index -> tokens.stream().filter(token -> index >= token.length() && memo[index - token.length()] > 0 && stringLine.startsWith(token, index - token.length()))
                        .forEach(token -> memo[index] += memo[index - token.length()]));
        return memo[stringLine.length()];
    }

    public static String sortedDelimited(List<String> strings, String delimiter) {
        return String.join(delimiter, strings.stream().sorted().toList());
    }
}
