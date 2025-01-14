/*
 * Copyright (©) 2024 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.japps.adventofcode.probs2024;

import static com.japps.adventofcode.util.ProblemSolverUtil.*;

import java.io.*;
import java.util.*;
import java.util.stream.*;

import com.japps.adventofcode.util.*;

public final class Prob22AOC2024 extends AbstractSolvable implements Loggable {

    private static final Prob22AOC2024 INSTANCE = instance();
    private static final long PRUNER_VALUE = 16777216L;
    private static final int NUMBER_OF_ROTATIONS = 2000;

    private Prob22AOC2024() {

    }

    private static Prob22AOC2024 instance() {

        return new Prob22AOC2024();
    }

    public static void main(final String[] args) {

        try {
            INSTANCE.compute();
        } catch (final IOException exception) {
            INSTANCE.error(exception.getLocalizedMessage());
        }
    }

    private void compute() throws IOException {
		List<String> lines = lines();
		println(lines.stream().mapToLong(Long::valueOf).map(secretNum -> nthSecretNum(secretNum, NUMBER_OF_ROTATIONS)).sum());
        println(maxBananas(lines.stream().mapToLong(Long::valueOf).mapToObj(secretNum -> fourPriceDiffSequenceMap(secretNum, NUMBER_OF_ROTATIONS)).toList()));
    }

    private static long maxBananas(List<Map<String, Long>> fourPriceDiffSequenceMaps) {
        Set<String> processedSequences = new HashSet<>();
        return fourPriceDiffSequenceMaps.stream().map(fourPriceDiffSequenceMap -> fourPriceDiffSequenceMap.keySet().stream()
                .filter(sequence -> !processedSequences.add(sequence))
                .mapToLong(aLong -> fourPriceDiffSequenceMaps.stream().filter(fpdsm -> fpdsm.containsKey(aLong)).mapToLong(fpdsm -> fpdsm.get(aLong)).sum())
                .max().orElse(0L)).mapToLong(Long::valueOf).max().orElse(0);
    }

    private static Map<String, Long> fourPriceDiffSequenceMap(long secretNum, long n) {
        Map<String, Long> fourPriceDiffSequenceMap = new HashMap<>();
        List<Long> nPrices = nPrices(secretNum, n);
        IntStream.rangeClosed(0, nPrices.size() - 5)
                .forEach(index -> fillFourPriceDiffSequences(fourPriceDiffSequenceMap, nPrices, index));
        return fourPriceDiffSequenceMap;
    }

    private static void fillFourPriceDiffSequences(Map<String, Long> fourPriceDiffSequenceMap, List<Long> nPrices, int i) {
        fourPriceDiffSequenceMap.putIfAbsent(
                StringUtil.stringify(
                        ProblemSolverUtil.toObjects(
                                List.of(
                                        nPrices.get(i + 1) - nPrices.get(i),
                                        nPrices.get(i + 2) - nPrices.get(i + 1),
                                        nPrices.get(i + 3) - nPrices.get(i + 2),
                                        nPrices.get(i + 4) - nPrices.get(i + 3))), COMMA),
                nPrices.get(i + 4));
    }

    private static List<Long> nPrices(long initialSecretNum, long n) {
        List<Long> nPrices = new ArrayList<>();
        long secretNum = initialSecretNum;
        nPrices.add(price(secretNum));
        for (int i = 1; i <= n; i++) {
            secretNum = nextSecretNum(secretNum);
            nPrices.add(price(secretNum));
        }
        return nPrices;
    }

    private static long price(long secretNum) {
        return secretNum % 10;
    }

    private static long nthSecretNum(long initialSecretNum, long n) {
        long secretNum = initialSecretNum;
        for (int i = 1; i <= n; i++) {
            secretNum = nextSecretNum(secretNum);
        }
        return secretNum;
    }

    private static long nextSecretNum(long secretNum) {
        return step3(step2(step1(secretNum)));
    }

    private static long step1(long secretNum) {
       return prune(mix(secretNum, secretNum * 64L));
    }

    private static long step2(long secretNum) {
        return prune(mix(secretNum, secretNum / 32L));
    }

    private static long step3(long secretNum) {
        return prune(mix(secretNum, secretNum * 2048L));
    }

    private static long mix(long secretNum, long value) {
        return secretNum ^ value;
    }

    private static long prune(long secretNum) {
        return secretNum % PRUNER_VALUE;
    }
}
