/*
 * Id: Regex.java 26-Dec-2023 SubhajoyLaskar
 * Copyright (©) 2023 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.japps.adventofcode.probs2023;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;


public class Regex {
    public static String match(final String regex, final String input) {
        final var m = Pattern.compile(regex).matcher(input);

        return m.find() ? m.group() : null;
    }

    public static List<String> matchAll(final String regex, final String input) {
        final var m = Pattern.compile(regex).matcher(input);
        final var list = new ArrayList<String>();

        while (m.find()) {
            list.add(m.group());
        }

        return list;
    }

    public static boolean matches(final String regex, final String input) {
        return Pattern.compile(regex).matcher(input).find();
    }

    public static List<String> matchGroups(final String regex, final String input) {
        final var m = Pattern.compile(regex).matcher(input);
        final var l = new ArrayList<String>();

        if (!m.find()) {
            return null;
        }

        for (var i = 1; i <= m.groupCount(); i++) {
            final var val = m.group(i);

            if (val != null) {
                l.add(val);
            }
        }

        return l;
    }

	/*
	 * public static HashMap<String, String> matchNamedGroups(final String regex,
	 * final String input) { final var m = Pattern.compile(regex).matcher(input);
	 * final var map = new HashMap<String, String>();
	 *
	 * if (!m.find()) { throw new RuntimeException("No matches found"); }
	 *
	 * for (final var es: m.namedGroups().entrySet()) { map.put(es.getKey(),
	 * m.group(es.getKey())); }
	 *
	 * return map; }
	 */
}