/*
* Id: StringUtil.java 01-Jan-2014 01:11:56 am SubhajoyLaskar
* Copyright (©) 2014 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.japps.adventofcode.util;

import java.util.Arrays;

/**
 * The string util.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public final class StringUtil {

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
        return new String(strChars);
    }

}
