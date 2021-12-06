/*
 * Id: Prob6FishCounter.java 30-Nov-2021 1:52:54 am SubhajoyLaskar
 * Copyright (©) 2021 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.japps.adventofcode.probs2021;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import com.japps.adventofcode.util.AbstractSolvable;
import com.japps.adventofcode.util.Loggable;


/**
 * The prob 6 fish counter.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public final class Prob6FishCounter extends AbstractSolvable implements Loggable {

    /** The instance. */
    private static final Prob6FishCounter INSTANCE = instance();

    /**
     * Instantiates a new prob 6 fish counter.
     */
    private Prob6FishCounter() {

    }

    /**
     * Instance.
     *
     * @return the prob 6 fish counter
     */
    private static Prob6FishCounter instance() {

        return new Prob6FishCounter();
    }

    /**
     * The main method.
     *
     * @param args the arguments
     */
    public static void main(final String[] args) {

        try {
            INSTANCE.info(INSTANCE.countFish());
        } catch (final IOException exception) {
            INSTANCE.error(exception.getLocalizedMessage());
        }
    }

    /**
     * Counts the of fish.
     *
     * @return the count of of fish
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private long countFish() throws IOException {

        final List<String> lines = lines();

        final Map<Long, Long> ageFrequencies = Arrays.asList(lines.get(0).split(","))
            .stream()
            .map(Long::parseLong)
            .collect(Collectors.groupingBy(value -> value, Collectors.counting()));

        //final long numberOfDays = 80;
        final long numberOfDays = 256;

        return calculateFishCount(ageFrequencies, numberOfDays);
    }

    /**
     * Calculates the fish count.
     *
     * @param ageFrequencies the age frequencies
     * @param numberOfDays the number of days
     * @return the fish count
     */
    private long calculateFishCount(final Map<Long, Long> ageFrequencies, final long numberOfDays) {

        Map<Long, Long> copyOfAgeFrequencies = new HashMap<Long, Long>(ageFrequencies);
        for (long i = 1; i <= numberOfDays; i++) {
            final Map<Long, Long> tempCopyOfAgeFrequencies = new HashMap<>();
            for(final Entry<Long, Long> ageFrequency : copyOfAgeFrequencies.entrySet()) {
                if (ageFrequency.getKey() == 0) {
                    tempCopyOfAgeFrequencies.putIfAbsent(6L, 0L);
                    tempCopyOfAgeFrequencies.put(6L, tempCopyOfAgeFrequencies.get(6L) + ageFrequency.getValue());

                    tempCopyOfAgeFrequencies.putIfAbsent(8L, 0L);
                    tempCopyOfAgeFrequencies.put(8L, tempCopyOfAgeFrequencies.get(8L) + ageFrequency.getValue());
                } else {
                    tempCopyOfAgeFrequencies.putIfAbsent(ageFrequency.getKey() - 1, 0L);
                    tempCopyOfAgeFrequencies.put(ageFrequency.getKey() - 1, tempCopyOfAgeFrequencies.get(ageFrequency.getKey() - 1) + ageFrequency.getValue());
                }
            }
            copyOfAgeFrequencies = new HashMap<Long, Long>(tempCopyOfAgeFrequencies);
        }

        return copyOfAgeFrequencies.values().stream().mapToLong(Long::longValue).sum();
    }

}
