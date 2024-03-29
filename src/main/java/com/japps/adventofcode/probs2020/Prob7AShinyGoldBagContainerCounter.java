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
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.japps.adventofcode.util.AbstractSolvable;
import com.japps.adventofcode.util.Loggable;


/**
 * The prob 7 A shiny gold bag container counter.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public final class Prob7AShinyGoldBagContainerCounter extends AbstractSolvable implements Loggable {

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
    private static final Prob7AShinyGoldBagContainerCounter INSTANCE = instance();

    /**
     * Instantiates a new prob 7 A shiny gold bag container counter.
     */
    private Prob7AShinyGoldBagContainerCounter() {

    }

    /**
     * Instance.
     *
     * @return the prob 7 A shiny gold bag container counter
     */
    private static Prob7AShinyGoldBagContainerCounter instance() {
        return new Prob7AShinyGoldBagContainerCounter();
    }

    /**
     * The main method.
     *
     * @param args the arguments
     */
    public static void main(final String[] args) {
        try {
            INSTANCE.info(INSTANCE.countShinyGoldBagContainers());
        } catch (final IOException exception) {
            INSTANCE.error(exception.getLocalizedMessage());
        }
    }

    /**
     * Counts the shiny gold bag containers.
     *
     * @return the count of shiny gold bag containers
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private long countShinyGoldBagContainers() throws IOException {
        final List<String> lines = lines();
        final Map<String, Set<String>> containerMap = new HashMap<>();

        lines
        .stream()
        .map(line -> line.replace(BAGS, StringUtils.EMPTY)
                         .replace(BAG, StringUtils.EMPTY)
                         .replace(DOT, StringUtils.EMPTY)
                         .replaceAll(REGEX_DIGIT, StringUtils.EMPTY)
                         .split(CONTAIN))
        .forEach(splitLine -> {
            final String container = splitLine[0].trim();
            final String[] containedBags = splitLine[1].split(COMMA);
            for (final String containedBag : containedBags) {
                containerMap.putIfAbsent(containedBag.trim(), new HashSet<>());
                containerMap.get(containedBag.trim()).add(container);
            }
        });

        final Set<String> shinyGoldContainerSet = new HashSet<>(containerMap.get(SHINY_GOLD));

        containerMap.get(SHINY_GOLD)
        .stream()
        .forEach(shinyGoldContainer -> addAllPossibleContainers(shinyGoldContainer, containerMap, shinyGoldContainerSet));

        return shinyGoldContainerSet.size();
    }

    /**
     * Adds the all possible containers.
     *
     * @param shinyGoldContainer the shiny gold container
     * @param containerMap the container map
     * @param shinyGoldContainers the shiny gold containers
     */
    private void addAllPossibleContainers(final String shinyGoldContainer, final Map<String, Set<String>> containerMap, final Set<String> shinyGoldContainers) {
        if (!containerMap.containsKey(shinyGoldContainer)) {
            return;
        }
        shinyGoldContainers.addAll(containerMap.get(shinyGoldContainer));
        containerMap.get(shinyGoldContainer)
        .forEach(sgc -> addAllPossibleContainers(sgc, containerMap, shinyGoldContainers));
    }
}
