/*
 * Id: Prob2AFindValidPasswords.java 30-Nov-2021 1:52:54 am SubhajoyLaskar
 * Copyright (©) 2021 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.japps.adventofcode.probs2020;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.japps.adventofcode.util.AbstractSolvable;
import com.japps.adventofcode.util.Loggable;


/**
 * The prob 2 A find valid passwords.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public final class Prob2AFindValidPasswords extends AbstractSolvable implements Loggable {

    /** The instance. */
    private static final Prob2AFindValidPasswords INSTANCE = instance();

    /**
     * Instantiates a new prob 2 A find valid passwords.
     */
    private Prob2AFindValidPasswords() {

    }

    /**
     * Instance.
     *
     * @return the prob 2 A find valid passwords
     */
    private static Prob2AFindValidPasswords instance() {
        return new Prob2AFindValidPasswords();
    }

    /**
     * The main method.
     *
     * @param args the arguments
     */
    public static void main(final String[] args) {
        try {
            INSTANCE.info(INSTANCE.findValidPasswords());
        } catch (final IOException exception) {
            INSTANCE.error(exception.getLocalizedMessage());
        }
    }

    /**
     * Finds the valid passwords.
     *
     * @return the valid passwords
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private long findValidPasswords() throws IOException {

        return Files.lines(Paths.get(determineInputFilePath())).filter(this::isValid).count();
    }

    /**
     * Indicates if is valid.
     *
     * @param password the password
     * @return {@code true}, if is valid
     */
    private boolean isValid(final String password) {
        final int indexOfHyphen = password.indexOf("-");
        final int minOccurence = Integer.parseInt(password.substring(0, indexOfHyphen));
        final int indexOfSpace = password.indexOf(" ");
        final int maxOccurence = Integer.parseInt(password.substring(indexOfHyphen + 1, indexOfSpace));
        final int indexOfColon = password.indexOf(":");
        final char validationChar = password.charAt(indexOfSpace + 1);
        final String passwordText = password.substring(indexOfColon + 2);
        final long validationCharCount = passwordText.chars().filter(character -> character == validationChar).count();
        return minOccurence <= validationCharCount &&
                passwordText.chars().filter(character -> character == validationChar).count() <= maxOccurence;
    }

}
