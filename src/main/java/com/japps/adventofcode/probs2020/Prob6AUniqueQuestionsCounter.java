/*
 * Id: Prob6AUniqueQuestionsCounter.java 30-Nov-2021 1:52:54 am SubhajoyLaskar
 * Copyright (©) 2021 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.japps.adventofcode.probs2020;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;

import com.japps.adventofcode.util.AbstractSolvable;
import com.japps.adventofcode.util.Loggable;


/**
 * The prob 6 A unique questions counter.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public final class Prob6AUniqueQuestionsCounter extends AbstractSolvable implements Loggable {

    /** The regex blank line. */
    private static final String REGEX_BLANK_LINE = "\\n\\n";

    /** The instance. */
    private static final Prob6AUniqueQuestionsCounter INSTANCE = instance();

    /**
     * Instantiates a new prob 6 A unique questions counter.
     */
    private Prob6AUniqueQuestionsCounter() {

    }

    /**
     * Instance.
     *
     * @return the prob 6 A unique questions counter
     */
    private static Prob6AUniqueQuestionsCounter instance() {

        return new Prob6AUniqueQuestionsCounter();
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

        return countUniqueQuestions(fileString.split(REGEX_BLANK_LINE));
    }

    /**
     * Counts the unique questions.
     *
     * @param answeredQuestionsGroups the answered questions groups
     * @return the count of unique questions
     */
    private long countUniqueQuestions(final String[] answeredQuestionsGroups) {

        return Arrays.asList(answeredQuestionsGroups)
        .stream()
        .map(answeredQuestionsGroup -> StringUtils.normalizeSpace(answeredQuestionsGroup)
            .replace(StringUtils.SPACE, StringUtils.EMPTY)
            .chars().distinct().count())
        .mapToLong(Long::longValue)
        .sum();
    }
}
