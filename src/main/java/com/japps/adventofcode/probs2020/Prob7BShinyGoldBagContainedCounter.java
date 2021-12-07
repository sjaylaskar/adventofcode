/*
 * Id: Prob7AShinyGoldBagContainerCounter.java 30-Nov-2021 1:52:54 am SubhajoyLaskar
 * Copyright (©) 2021 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.japps.adventofcode.probs2020;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import com.japps.adventofcode.util.AbstractSolvable;
import com.japps.adventofcode.util.Loggable;


/**
 * The prob 7 A shiny gold bag container counter.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public final class Prob7BShinyGoldBagContainedCounter extends AbstractSolvable implements Loggable {

    /** The contain. */
    private static final String CONTAIN = "contain";

    /** The bags. */
    private static final String BAGS = "bags";

    /** The bag. */
    private static final String BAG = "bag";

    /** The dot. */
    private static final String DOT = ".";

    /** The regex digit. */
    private static final String REGEX_DIGIT = "\\d";

    /** The comma. */
    private static final String COMMA = ",";

    /** The shiny gold. */
    private static final String SHINY_GOLD = "shiny gold";

    /** The instance. */
    private static final Prob7BShinyGoldBagContainedCounter INSTANCE = instance();

    /**
     * Instantiates a new prob 7 A shiny gold bag container counter.
     */
    private Prob7BShinyGoldBagContainedCounter() {

    }

    /**
     * Instance.
     *
     * @return the prob 7 A shiny gold bag container counter
     */
    private static Prob7BShinyGoldBagContainedCounter instance() {

        return new Prob7BShinyGoldBagContainedCounter();
    }

    /**
     * The main method.
     *
     * @param args the arguments
     */
    public static void main(final String[] args) {

        try {
            INSTANCE.countShinyGoldBagContainersAndContents();
        } catch (final IOException exception) {
            INSTANCE.error(exception.getLocalizedMessage());
        }
    }

    /**
     * Counts the shiny gold bag containers and contents.
     *
     * @return the count of shiny gold bag containers and contents
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private void countShinyGoldBagContainersAndContents() throws IOException {

        final List<String> lines = lines();
        final Map<String, Set<String>> containerMap = new HashMap<>();
        final Map<String, Map<String, Integer>> containedMap = new HashMap<>();

        fillContents(lines, containerMap, containedMap);

        info(countShinyGoldBagContainers(containerMap));

        info(countShinyGoldBagContents(SHINY_GOLD, containedMap) - 1);
    }

    /**
     * Fill contents.
     *
     * @param lines the lines
     * @param containerMap the container map
     * @param containedMap the contained map
     */
    private void fillContents(final List<String> lines, final Map<String, Set<String>> containerMap,
            final Map<String, Map<String, Integer>> containedMap) {

        lines
            .stream()
            .map(line -> line.replace(BAGS, StringUtils.EMPTY)
                .replace(BAG, StringUtils.EMPTY)
                .replace(DOT, StringUtils.EMPTY)
                .split(CONTAIN))
            .forEach(splitLine -> {
                final String container = splitLine[0].trim();
                final String[] containedBags = splitLine[1].split(COMMA);
                for (final String containedBag : containedBags) {
                    final String containedBagName = containedBag.replaceAll(REGEX_DIGIT, StringUtils.EMPTY).trim();

                    final String numberOfBagsString = containedBag.trim().split(StringUtils.SPACE)[0].trim();
                    containedMap.putIfAbsent(container, new HashMap<>());
                    containedMap.get(container).put(containedBagName, NumberUtils.isCreatable(numberOfBagsString) ? Integer
                        .parseInt(numberOfBagsString) : 0);

                    containerMap.putIfAbsent(containedBagName, new HashSet<>());
                    containerMap.get(containedBagName).add(container);
                }
            });
    }

    /**
     * Counts the shiny gold bag containers.
     *
     * @param containerMap the container map
     * @return the count of shiny gold bag containers
     */
    private long countShinyGoldBagContainers(final Map<String, Set<String>> containerMap) {

        final Set<String> shinyGoldContainerSet = new HashSet<>(containerMap.get(SHINY_GOLD));

        containerMap.get(SHINY_GOLD)
            .stream()
            .forEach(shinyGoldContainer -> addAllPossibleContainers(shinyGoldContainer, containerMap, shinyGoldContainerSet));

        return shinyGoldContainerSet.size();
    }

    /**
     * Counts the shiny gold bag contents.
     *
     * @param bagName the bag name
     * @param containedMap the contained map
     * @return the count of shiny gold bag contents
     */
    private long countShinyGoldBagContents(final String bagName, final Map<String, Map<String, Integer>> containedMap) {

        long result = 1;
        final Map<String, Integer> bagContent = containedMap.get(bagName);
        if (bagContent != null) {
            for (final Entry<String, Integer> entry : bagContent.entrySet()) {
                result += entry.getValue() * countShinyGoldBagContents(entry.getKey(), containedMap);
            }
        }

        return result;
    }

    /**
     * Adds the all possible containers.
     *
     * @param shinyGoldContainer the shiny gold container
     * @param containerMap the container map
     * @param shinyGoldContainers the shiny gold containers
     */
    private void addAllPossibleContainers(final String shinyGoldContainer, final Map<String, Set<String>> containerMap,
            final Set<String> shinyGoldContainers) {

        if (!containerMap.containsKey(shinyGoldContainer)) {
            return;
        }
        shinyGoldContainers.addAll(containerMap.get(shinyGoldContainer));
        containerMap.get(shinyGoldContainer)
            .forEach(sgc -> addAllPossibleContainers(sgc, containerMap, shinyGoldContainers));
    }
}
