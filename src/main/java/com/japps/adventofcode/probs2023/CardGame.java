/*
 * Id: CardGame.java 07-Dec-2023 SubhajoyLaskar
 * Copyright (©) 2023 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.japps.adventofcode.probs2023;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CardGame {
    public static void main(final String[] args) {
        try {
            final String fileName = "D:\\Dev\\codespaces\\adventofcode\\src\\main\\resources\\com\\japps\\adventofcode\\probs2023\\input7.txt";
            final String fileContent = readFile(fileName);

            final String[] lines = fileContent.trim().split("\n");
            final List<String[]> handsAndBids = new ArrayList<>();

            for (final String line : lines) {
                final String[] parts = line.split(" ");
                handsAndBids.add(parts);
            }

            for (final boolean part2 : new boolean[]{false, true}) {
                final List<String[]> sortedHandsAndBids = sortByStrength(handsAndBids, part2);
                final int ans = calculateScore(sortedHandsAndBids);
                System.out.println(ans);
            }

        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    private static String readFile(final String fileName) throws IOException {
        final StringBuilder content = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                content.append(line).append("\n");
            }
        }
        return content.toString();
    }

    private static List<String[]> sortByStrength(final List<String[]> handsAndBids, final boolean part2) {
        return handsAndBids.stream()
                .sorted((hb1, hb2) -> Integer.compare(strength(hb1[0], part2), strength(hb2[0], part2)))
                .toList();
    }

    private static int strength(String hand, final boolean part2) {
        hand = hand.replace("T", String.valueOf((char) (Character.getNumericValue('9') + 1)));
        hand = hand.replace("J", String.valueOf(part2 ? (char) (Character.getNumericValue('2') - 1) : (char) (Character.getNumericValue('9') + 2)));
        hand = hand.replace("Q", String.valueOf((char) (Character.getNumericValue('9') + 3)));
        hand = hand.replace("K", String.valueOf((char) (Character.getNumericValue('9') + 4)));
        hand = hand.replace("A", String.valueOf((char) (Character.getNumericValue('9') + 5)));

        final Map<Character, Integer> counterMap = new HashMap<>();
        for (final char c : hand.toCharArray()) {
            counterMap.put(c, counterMap.getOrDefault(c, 0) + 1);
        }

        if (part2) {
            char target = counterMap.keySet().iterator().next();
            for (final char k : counterMap.keySet()) {
                if (k != '1') {
                    if (counterMap.get(k) > counterMap.get(target) || target == '1') {
                        target = k;
                    }
                }
            }

            assert target != '1' || counterMap.keySet().equals(Set.of('1'));

            if (counterMap.containsKey('1') && target != '1') {
                counterMap.put(target, counterMap.get(target) + counterMap.get('1'));
                counterMap.remove('1');
            }

            assert !counterMap.containsKey('1') || counterMap.keySet().equals(Set.of('1')) : counterMap.toString() + " " + hand;
        }

        final List<Integer> sortedValues = counterMap.values().stream().sorted().toList();

        if (sortedValues.equals(List.of(5))) {
            return 10;
        } else if (sortedValues.equals(List.of(1, 4))) {
            return 9;
        } else if (sortedValues.equals(List.of(2, 3))) {
            return 8;
        } else if (sortedValues.equals(List.of(1, 1, 3))) {
            return 7;
        } else if (sortedValues.equals(List.of(1, 2, 2))) {
            return 6;
        } else if (sortedValues.equals(List.of(1, 1, 1, 2))) {
            return 5;
        } else if (sortedValues.equals(List.of(1, 1, 1, 1, 1))) {
            return 4;
        } else {
            throw new AssertionError(counterMap.toString() + " " + hand + " " + sortedValues);
        }
    }

    private static int calculateScore(final List<String[]> sortedHandsAndBids) {
        int ans = 0;
        for (int i = 0; i < sortedHandsAndBids.size(); i++) {
            final String[] handAndBid = sortedHandsAndBids.get(i);
            ans += (i + 1) * Integer.parseInt(handAndBid[1]);
        }
        return ans;
    }
}

