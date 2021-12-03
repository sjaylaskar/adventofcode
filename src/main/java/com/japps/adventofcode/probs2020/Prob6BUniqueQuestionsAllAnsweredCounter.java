/*
 * Id: Prob6BUniqueQuestionsAllAnsweredCounter.java 30-Nov-2021 1:52:54 am SubhajoyLaskar
 * Copyright (©) 2021 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.japps.adventofcode.probs2020;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import com.japps.adventofcode.util.AbstractSolvable;
import com.japps.adventofcode.util.Loggable;


/**
 * The prob 6 B unique questions all answered counter.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public final class Prob6BUniqueQuestionsAllAnsweredCounter extends AbstractSolvable implements Loggable {

    /** The regex blank line. */
    private static final String REGEX_BLANK_LINE = "\\n\\n";

    /** The instance. */
    private static final Prob6BUniqueQuestionsAllAnsweredCounter INSTANCE = instance();

    /**
     * Instantiates a new prob 6 B unique questions all answered counter.
     */
    private Prob6BUniqueQuestionsAllAnsweredCounter() {

    }

    /**
     * Instance.
     *
     * @return the prob 6 B unique questions all answered counter
     */
    private static Prob6BUniqueQuestionsAllAnsweredCounter instance() {

        return new Prob6BUniqueQuestionsAllAnsweredCounter();
    }

    /**
     * The main method.
     *
     * @param args the arguments
     */
    public static void main(final String[] args) {

        try {
            INSTANCE.info(INSTANCE.countUniqueQuestions());
        } catch (final IOException exception) {
            INSTANCE.error(exception.getLocalizedMessage());
        }
    }

    /**
     * Counts the unique questions.
     *
     * @return the count of unique questions
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private long countUniqueQuestions() throws IOException {

        final String fileString = new String(Files.readAllBytes(Paths.get(determineInputFilePath())));

        return countUniqueQuestionsAnsweredByAll(fileString.split(REGEX_BLANK_LINE));
    }

    /**
     * Counts the unique questions answered by all.
     *
     * @param answeredQuestionsGroups the answered questions groups
     * @return the count of unique questions answered by all
     */
    private long countUniqueQuestionsAnsweredByAll(final String[] answeredQuestionsGroups) {

        return Arrays.asList(answeredQuestionsGroups)
        .stream()
        .map(answeredQuestionsGroup -> StringUtils.normalizeSpace(answeredQuestionsGroup)
            .replace(StringUtils.SPACE, StringUtils.EMPTY)
            .codePoints()
            .mapToObj(Character::toString)
            .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
            .values()
            .stream()
            .filter(value -> value == answeredQuestionsGroup.split("\n").length)
            .count()
            )
        .mapToLong(Long::longValue)
        .sum();
    }
}
