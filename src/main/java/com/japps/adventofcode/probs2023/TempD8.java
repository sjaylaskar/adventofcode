/*
 * Id: TempD6.java 08-Dec-2023 SubhajoyLaskar
 * Copyright (©) 2023 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.japps.adventofcode.probs2023;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TempD8 {
    public static void main(final String[] args) {
        try {
            final String D = new String(Files.readAllBytes(Paths.get("D:\\Dev\\codespaces\\adventofcode\\data\\2023\\input8.txt"))).trim();
            final List<String> L = Arrays.asList(D.split("\\n"));

            final Map<String, String> GO_L = new HashMap<>();
            final Map<String, String> GO_R = new HashMap<>();

            final String[] parts = D.split("\\n\\n");
            final String steps = parts[0];
            final String rule = parts[1];

            for (final String line : rule.split("\\n")) {
                final String[] ruleParts = line.split("=");
                final String st = ruleParts[0].trim();
                final String lr = ruleParts[1].trim();
                final String[] lrParts = lr.split(",");
                final String left = lrParts[0].trim().substring(1).trim();
                final String right = lrParts[1].trim().substring(0, lrParts[1].length() - 1).trim();
                GO_L.put(st, left);
                GO_R.put(st, right);
            }

            System.out.println(solve(steps, GO_L, GO_R, false));
            System.out.println(solve(steps, GO_L, GO_R, true));
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    private static int gcd(final int a, final int b) {
        return b == 0 ? a : gcd(b, a % b);
    }

    private static int lcm(final int[] xs) {
        int ans = 1;
        for (final int x : xs) {
            ans = x * ans / gcd(x, ans);
        }
        return ans;
    }

    private static int solve(final String steps, final Map<String, String> goL, final Map<String, String> goR, final boolean part2) {
        String[] pos = new String[goL.size()];
        Arrays.fill(pos, "");  // Initialize the array with empty strings

        final int[] T = new int[goL.size()];

        int t = 0;
        while (true) {
            final String[] np = new String[pos.length];
            for (int i = 0; i < pos.length; i++) {
                final String p = goL.get(pos[i]);
                if (p.endsWith(part2 ? "A" : "AAA")) {
                    pos[i] = p;
                    return lcm(Arrays.copyOfRange(T, 0, i + 1));
                }
                np[i] = p;
            }
            pos = np;
            t++;
        }
    }
}


