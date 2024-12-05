/*
 * Copyright (©) 2024 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.japps.adventofcode.probs2024;

import com.japps.adventofcode.util.AbstractSolvable;
import com.japps.adventofcode.util.IntPair;
import com.japps.adventofcode.util.Loggable;

import java.io.IOException;
import java.util.*;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public final class Prob5AOC2024 extends AbstractSolvable implements Loggable {

    private static final Prob5AOC2024 INSTANCE = instance();

    private Prob5AOC2024() {

    }

    private static Prob5AOC2024 instance() {

        return new Prob5AOC2024();
    }

    public static void main(final String[] args) {

        try {
            INSTANCE.compute();
        } catch (final IOException exception) {
            INSTANCE.error(exception.getLocalizedMessage());
        }
    }

    private static final String PAGE_RULE_SPLITTER = Pattern.quote("|");
    private static final String PAGE_SPLITTER = ",";
    private static final int PAGE_RULE_LINE_END_ROW = 1176;

    private void compute() throws IOException {
		List<String> lines = lines();
        Set<IntPair> pageRules = lines.subList(0, PAGE_RULE_LINE_END_ROW).stream().map(this::toIntPair).collect(Collectors.toSet());
        Map<Integer, Set<Integer>> pagesAfterMap = new HashMap<>();
        pageRules.forEach(pair -> pagesAfterMap.computeIfAbsent(pair.getX(), _ -> new HashSet<>()).add(pair.getY()));
        computeMiddlePagesSum(lines, pageRules, pagesAfterMap, false);
        computeMiddlePagesSum(lines, pageRules, pagesAfterMap, true);
    }

    private void computeMiddlePagesSum(List<String> lines, Set<IntPair> pageRules, Map<Integer, Set<Integer>> pagesAfterMap, boolean fixIncorrectPagePairs) {
        List<Integer> middlePages = new ArrayList<>();
        for (String line : lines.subList(PAGE_RULE_LINE_END_ROW + 1, lines.size())) {
            List<Integer> pages = Stream.of(line.split(PAGE_SPLITTER)).map(Integer::valueOf).collect(Collectors.toList());
            if (!isInvalid(pages, pageRules) && !fixIncorrectPagePairs) {
                middlePages.add(pages.get(pages.size() / 2));
            } else if (isInvalid(pages, pageRules) && fixIncorrectPagePairs) {
                pages.sort(pagesComparator(pagesAfterMap));
                middlePages.add(pages.get(pages.size() / 2));
            }
        }
        println(middlePages.stream().mapToInt(Integer::valueOf).sum());
    }

    private static Comparator<Integer> pagesComparator(Map<Integer, Set<Integer>> pagesAfterMap) {
        return (x, y) -> pagesAfterMap.get(x).contains(y) ? -1 : pagesAfterMap.get(y).contains(x) ? 1 : 0;
    }

    private static boolean isInvalid(List<Integer> pages, Set<IntPair> pageRules) {
        return IntStream.range(0, pages.size() - 1).mapToObj(index -> IntPair.of(pages.get(index), pages.get(index + 1))).anyMatch(invalidPagePairPredicate(pageRules));
    }

    private static Predicate<IntPair> invalidPagePairPredicate(Set<IntPair> pageRules) {
        return pagePair -> !isValidPagePair(pageRules, pagePair);
    }

    private static boolean isValidPagePair(Set<IntPair> pageRules, IntPair pagePair) {
        return pageRules.contains(pagePair);
    }

    private IntPair toIntPair(String pageRuleLine) {
        String[] pages = pageRuleLine.split(PAGE_RULE_SPLITTER);
        return IntPair.of(Integer.parseInt(pages[0]), Integer.parseInt(pages[1]));
    }
}
