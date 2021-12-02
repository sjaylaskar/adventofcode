/*
 * Id: Prob4AFindNumberOfValidPassports.java 30-Nov-2021 1:52:54 am SubhajoyLaskar
 * Copyright (©) 2021 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.japps.adventofcode.probs2020;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.japps.adventofcode.util.AbstractSolvable;
import com.japps.adventofcode.util.Loggable;


/**
 * The prob 4 A find number of valid passports.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public final class Prob4AFindNumberOfValidPassports extends AbstractSolvable implements Loggable {

    /** The instance. */
    private static final Prob4AFindNumberOfValidPassports INSTANCE = instance();

    private static final Set<String> PASSPORT_VALID_PREFIXES = new HashSet<>(7);

    static {
        PASSPORT_VALID_PREFIXES.addAll(Arrays.asList("byr",
            "iyr",
            "eyr",
            "hgt",
            "hcl",
            "ecl",
            "pid"));
    }

    /**
     * Instantiates a new prob 4 A find number of valid passports.
     */
    private Prob4AFindNumberOfValidPassports() {

    }

    /**
     * Instance.
     *
     * @return the prob 4 A find number of valid passports
     */
    private static Prob4AFindNumberOfValidPassports instance() {
        return new Prob4AFindNumberOfValidPassports();
    }

    /**
     * The main method.
     *
     * @param args the arguments
     */
    public static void main(final String[] args) {
        try {
            INSTANCE.info(INSTANCE.findNumberOfValidPassports());
        } catch (final IOException exception) {
            INSTANCE.error(exception.getLocalizedMessage());
        }
    }

    /**
     * Finds the number of valid passports.
     *
     * @return the number of valid passports
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private long findNumberOfValidPassports() throws IOException {

        final String fileString = new String(Files.readAllBytes(Paths.get(determineInputFilePath())));

        return countValidPassports(fileString.split("\\n\\n"));
    }

    private long countValidPassports(final String[] passports) {
        long count = 0;
        for (final String passport : passports) {
            final String[] passportTextsSplitByColon = passport.split(":");
            final String[] passportTexts = Arrays.copyOf(passportTextsSplitByColon, passportTextsSplitByColon.length - 1);
            final Set<String> passportItemPrefixes = new HashSet<>();
            for (final String passportText : passportTexts) {
                passportItemPrefixes.add(
                    passportText.substring(passportText.length() - 3, passportText.length()));
            }
            boolean isValid = true;
            for (final String passportValidPrefix : PASSPORT_VALID_PREFIXES) {
                if (!passportItemPrefixes.contains(passportValidPrefix)) {
                    isValid = false;
                    break;
                }
            }
            if (isValid) {
                ++count;
            }
        }

        return count;
    }
}
