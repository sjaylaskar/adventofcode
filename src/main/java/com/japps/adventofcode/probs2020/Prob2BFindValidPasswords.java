/*
 * Id: Prob2BFindValidPasswords.java 30-Nov-2021 1:52:54 am SubhajoyLaskar
 * Copyright (©) 2021 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.japps.adventofcode.probs2020;

import java.io.IOException;

import com.japps.adventofcode.util.AbstractSolvable;
import com.japps.adventofcode.util.Loggable;


/**
 * The prob 2 B find valid passwords.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public final class Prob2BFindValidPasswords extends AbstractSolvable implements Loggable {

    /** The instance. */
    private static final Prob2BFindValidPasswords INSTANCE = instance();

    /**
     * Instantiates a new prob 2 B find valid passwords.
     */
    private Prob2BFindValidPasswords() {

    }

    /**
     * Instance.
     *
     * @return the prob 2 B find valid passwords
     */
    private static Prob2BFindValidPasswords instance() {
        return new Prob2BFindValidPasswords();
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

        return lines().stream().filter(this::isValid).count();
    }

    /**
     * Indicates if is valid.
     *
     * @param password the password
     * @return {@code true}, if is valid
     */
    private boolean isValid(final String password) {
        final int indexOfHyphen = password.indexOf("-");
        final int occurenceIndex1 = Integer.parseInt(password.substring(0, indexOfHyphen)) - 1;
        final int indexOfSpace = password.indexOf(" ");
        final int occurenceIndex2 = Integer.parseInt(password.substring(indexOfHyphen + 1, indexOfSpace)) - 1;
        final int indexOfColon = password.indexOf(":");
        final char validationChar = password.charAt(indexOfSpace + 1);
        final String passwordText = password.substring(indexOfColon + 2);
        final char charAtOccurenceIndex1 = passwordText.charAt(occurenceIndex1);
        final char charAtOccurenceIndex2 = passwordText.charAt(occurenceIndex2);
        return (charAtOccurenceIndex1 == validationChar) ^ (charAtOccurenceIndex2 == validationChar);
    }
}
