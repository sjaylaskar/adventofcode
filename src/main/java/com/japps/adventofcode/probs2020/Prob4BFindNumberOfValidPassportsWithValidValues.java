/*
 * Id: Prob4BFindNumberOfValidPassportsWithValidValues.java 30-Nov-2021 1:52:54 am SubhajoyLaskar
 * Copyright (©) 2021 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.japps.adventofcode.probs2020;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.japps.adventofcode.util.AbstractSolvable;
import com.japps.adventofcode.util.Loggable;


/**
 * The prob 4 B find number of valid passports with valid values.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public final class Prob4BFindNumberOfValidPassportsWithValidValues extends AbstractSolvable implements Loggable {

    /** The regex space. */
    private static final String REGEX_SPACE = " ";

    /** The regex new line. */
    private static final String REGEX_NEW_LINE = "\\n";

    /** The regex blank line. */
    private static final String REGEX_BLANK_LINE = "\\n\\n";

    /** The instance. */
    private static final Prob4BFindNumberOfValidPassportsWithValidValues INSTANCE = instance();

    /** The regex colon. */
    private static final String REGEX_COLON = ":";

    /**
     * The passport field.
     *
     * @author Subhajoy Laskar
     * @version 1.0
     */
    private static enum PassportField {

        /** The byr. */
        BYR("byr"),

        /** The iyr. */
        IYR("iyr"),

        /** The eyr. */
        EYR("eyr"),

        /** The hgt. */
        HGT("hgt"),

        /** The hcl. */
        HCL("hcl"),

        /** The ecl. */
        ECL("ecl"),

        /** The pid. */
        PID("pid"),

        /** The cid. */
        CID("cid");

        /** The code. */
        private String code;

        /**
         * Instantiates a new passport field.
         *
         * @param code the code
         */
        private PassportField(final String code) {
            this.code = code;
        }

        /**
         * By code.
         *
         * @param code the code
         * @return the passport field
         */
        private static PassportField byCode(final String code) {
            switch(code) {
            case "byr" -> {return BYR;}
            case "iyr" -> {return IYR;}
            case "eyr" -> {return EYR;}
            case "hgt" -> {return HGT;}
            case "hcl" -> {return HCL;}
            case "ecl" -> {return ECL;}
            case "pid" -> {return PID;}
            case "cid" -> {return CID;}
            default -> {return null;}
            }
        }
    }

    /**
     * Instantiates a new prob 4 B find number of valid passports with valid values.
     */
    private Prob4BFindNumberOfValidPassportsWithValidValues() {

    }

    /**
     * Instance.
     *
     * @return the prob 4 B find number of valid passports with valid values
     */
    private static Prob4BFindNumberOfValidPassportsWithValidValues instance() {

        return new Prob4BFindNumberOfValidPassportsWithValidValues();
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

        return countValidPassports(fileString.split(REGEX_BLANK_LINE));
    }

    /**
     * Counts the valid passports.
     *
     * @param passportStrings the passports
     * @return the count of valid passports
     */
    private long countValidPassports(final String[] passportStrings) {

        long count = 0;
        for (final String passportString : passportStrings) {
            final String[] passportLines = passportString.split(REGEX_NEW_LINE);
            final List<String> passportFields = new ArrayList<>(8);
            for (final String passportLine : passportLines) {
                passportFields.addAll(Arrays.asList(passportLine.split(REGEX_SPACE)));
            }
            final Passport passport = new Passport();
            passportFields
            .forEach(passportField -> {
                buildPassport(passportField, passport);
            });
            if (passport.isValid()) {
                info("Valid passport: " + passport.getPid());
                ++count;
            } else {
                info("Invalid passport: " + passport.getPid());
            }
        }

        return count;
    }

    /**
     * Builds the passport.
     *
     * @param passportField the passport field
     * @param passport the passport
     */
    private void buildPassport(final String passportField, final Passport passport) {
        final String[] passportFieldValues = passportField.split(REGEX_COLON);
        switch (PassportField.byCode(passportFieldValues[0])) {
        case BYR -> {passport.setByr(passportFieldValues[1]);}
        case IYR -> {passport.setIyr(passportFieldValues[1]);}
        case EYR -> {passport.setEyr(passportFieldValues[1]);}
        case HGT -> {passport.setHgt(passportFieldValues[1]);}
        case HCL -> {passport.setHcl(passportFieldValues[1]);}
        case ECL -> {passport.setEcl(passportFieldValues[1]);}
        case PID -> {passport.setPid(passportFieldValues[1]);}
        case CID -> {passport.setCid(passportFieldValues[1]);}
        }
    }

    /**
     * The passport.
     *
     * @author Subhajoy Laskar
     * @version 1.0
     */
    private static final class Passport {

        /** The byr. */
        private String byr;

        /** The iyr. */
        private String iyr;

        /** The eyr. */
        private String eyr;

        /** The hgt. */
        private String hgt;

        /** The hcl. */
        private String hcl;

        /** The ecl. */
        private String ecl;

        /** The pid. */
        private String pid;

        /** The cid. */
        private String cid;

        /**
         * Gets the byr.
         *
         * @return the byr
         */
        String getByr() {

            return byr;
        }

        /**
         * Sets the byr.
         *
         * @param byr the new byr
         */
        void setByr(final String byr) {

            this.byr = byr;
        }

        /**
         * Gets the iyr.
         *
         * @return the iyr
         */
        String getIyr() {

            return iyr;
        }

        /**
         * Sets the iyr.
         *
         * @param iyr the new iyr
         */
        void setIyr(final String iyr) {

            this.iyr = iyr;
        }

        /**
         * Gets the eyr.
         *
         * @return the eyr
         */
        String getEyr() {

            return eyr;
        }

        /**
         * Sets the eyr.
         *
         * @param eyr the new eyr
         */
        void setEyr(final String eyr) {

            this.eyr = eyr;
        }

        /**
         * Gets the hgt.
         *
         * @return the hgt
         */
        String getHgt() {

            return hgt;
        }

        /**
         * Sets the hgt.
         *
         * @param hgt the new hgt
         */
        void setHgt(final String hgt) {

            this.hgt = hgt;
        }

        /**
         * Gets the hcl.
         *
         * @return the hcl
         */
        String getHcl() {

            return hcl;
        }

        /**
         * Sets the hcl.
         *
         * @param hcl the new hcl
         */
        void setHcl(final String hcl) {

            this.hcl = hcl;
        }

        /**
         * Gets the ecl.
         *
         * @return the ecl
         */
        String getEcl() {

            return ecl;
        }

        /**
         * Sets the ecl.
         *
         * @param ecl the new ecl
         */
        void setEcl(final String ecl) {

            this.ecl = ecl;
        }

        /**
         * Gets the pid.
         *
         * @return the pid
         */
        String getPid() {

            return pid;
        }

        /**
         * Sets the pid.
         *
         * @param pid the new pid
         */
        void setPid(final String pid) {

            this.pid = pid;
        }

        /**
         * Gets the cid.
         *
         * @return the cid
         */
        String getCid() {

            return cid;
        }

        /**
         * Sets the cid.
         *
         * @param cid the new cid
         */
        void setCid(final String cid) {

            this.cid = cid;
        }

        /**
         * Indicates if is valid.
         *
         * @return {@code true}, if is valid
         */
        boolean isValid() {
            return PassportValidator.validate(this);
        }
    }

    /**
     * The passport validator.
     *
     * @author Subhajoy Laskar
     * @version 1.0
     */
    private static final class PassportValidator {

        /** The regex year. */
        private static final String REGEX_YEAR = "[0-9]{4}";

        /** The boundaries birth year. */
        private static final int BOUNDARIES_BIRTH_YEAR[] = {1920, 2002};

        /** The boundaries issue year. */
        private static final int BOUNDARIES_ISSUE_YEAR[] = {2010, 2020};

        /** The boundaries expiration year. */
        private static final int BOUNDARIES_EXPIRATION_YEAR[] = {2020, 2030};

        /** The regex height. */
        private static final String REGEX_HEIGHT = "([0-9]+)(cm|in)";

        /** The boundaries height cm. */
        private static final int BOUNDARIES_HEIGHT_CM[] = {150, 193};

        /** The boundaries height in. */
        private static final int BOUNDARIES_HEIGHT_IN[] = {59, 76};

        /** The regex hair color. */
        private static final String REGEX_HAIR_COLOR = "#[0-9a-f]{6}";

        /** The eye colors. */
        private static final Set<String> EYE_COLORS = new HashSet<>(Arrays.asList("amb", "blu", "brn", "gry", "grn", "hzl", "oth"));

        /** The regex passport id. */
        private static final String REGEX_PASSPORT_ID = "[0-9]{9}";

        /**
         * Instantiates a new passport validator.
         */
        private PassportValidator() {

        }

        /**
         * Validate.
         *
         * @param passport the passport
         * @return {@code true}, if successful
         */
        static boolean validate(final Passport passport) {

            return StringUtils.isNotBlank(passport.getByr())
                    && passport.getByr().matches(REGEX_YEAR)
                    && isInRange(passport.getByr(), BOUNDARIES_BIRTH_YEAR)

                    && StringUtils.isNotBlank(passport.getIyr())
                    && passport.getIyr().matches(REGEX_YEAR)
                    && isInRange(passport.getIyr(), BOUNDARIES_ISSUE_YEAR)

                    && StringUtils.isNotBlank(passport.getEyr())
                    && passport.getEyr().matches(REGEX_YEAR)
                    && isInRange(passport.getEyr(), BOUNDARIES_EXPIRATION_YEAR)

                    && StringUtils.isNotBlank(passport.getHgt())
                    && passport.getHgt().matches(REGEX_HEIGHT)
                    && isValidHeight(passport.getHgt())

                    && StringUtils.isNotBlank(passport.getHcl())
                    && passport.getHcl().matches(REGEX_HAIR_COLOR)

                    && StringUtils.isNotBlank(passport.getEcl())
                    && EYE_COLORS.contains(passport.getEcl())

                    && StringUtils.isNotBlank(passport.getPid())
                    && passport.getPid().matches(REGEX_PASSPORT_ID);
        }

        /**
         * Indicates if is in range.
         *
         * @param intAsString the int as string
         * @param boundaries the boundaries
         * @return {@code true}, if is in range
         */
        private static boolean isInRange(final String intAsString, final int[] boundaries) {
            final int num = Integer.parseInt(intAsString);
            return boundaries[0] <= num && num <= boundaries[1];
        }

        /**
         * Indicates if is valid height.
         *
         * @param heightAsString the height as string
         * @return {@code true}, if is valid height
         */
        private static boolean isValidHeight(final String heightAsString) {
            final String heightValueAsString = heightAsString.substring(0, heightAsString.length() - 2);
            final char charAtSecondLastPos = heightAsString.charAt(heightAsString.length() - 2);
            return (charAtSecondLastPos == 'c')
                   ? isInRange(heightValueAsString, BOUNDARIES_HEIGHT_CM)
                   :  (charAtSecondLastPos == 'i')
                      ? isInRange(heightValueAsString, BOUNDARIES_HEIGHT_IN)
                      : false;
        }
    }
}
