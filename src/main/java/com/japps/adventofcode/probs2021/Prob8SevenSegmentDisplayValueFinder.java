/*
 * Id: Prob8SevenSegmentDisplayValueFinder.java 08-Dec-2021 10:31:19 am SubhajoyLaskar
 * Copyright (©) 2021 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.japps.adventofcode.probs2021;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.collections4.BidiMap;
import org.apache.commons.collections4.bidimap.DualHashBidiMap;
import org.apache.commons.lang3.StringUtils;

import com.japps.adventofcode.util.AbstractSolvable;
import com.japps.adventofcode.util.Loggable;
import com.japps.adventofcode.util.StringUtil;


/**
 * The prob 8 seven segment display value finder.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public final class Prob8SevenSegmentDisplayValueFinder extends AbstractSolvable implements Loggable {

    /** The pipe. */
    private static final String PIPE = "|";

    /** The instance. */
    private static final Prob8SevenSegmentDisplayValueFinder INSTANCE = instance();

    /**
     * Instantiates a new prob 8 seven segment display value finder.
     */
    private Prob8SevenSegmentDisplayValueFinder() {

    }

    /**
     * Instance.
     *
     * @return the prob 8 seven segment display value finder
     */
    private static Prob8SevenSegmentDisplayValueFinder instance() {

        return new Prob8SevenSegmentDisplayValueFinder();
    }

    /**
     * The main method.
     *
     * @param args the arguments
     */
    public static void main(final String[] args) {

        try {
            INSTANCE.findDisplayValue();
        } catch (final IOException exception) {
            INSTANCE.error(exception.getLocalizedMessage());
        }
    }

    /** The Constant signalAlphabet. */
    private static final Set<Character> signalAlphabet = new HashSet<>(Arrays.asList('a', 'b', 'c', 'd', 'e', 'f', 'g'));

    /** The Constant uniqueDigitLengthMap. */
    private static final Map<Integer, Integer> uniqueDigitLengthMap = new HashMap<>(4);
    static {
        uniqueDigitLengthMap.put(2, 1);
        uniqueDigitLengthMap.put(3, 7);
        uniqueDigitLengthMap.put(4, 4);
        uniqueDigitLengthMap.put(7, 8);
    }

    /** The Constant combMap. */
    private static final Map<String, Integer> combMap = new HashMap<>(10);
    static {
        combMap.put("123567", 0);
        combMap.put("36", 1);
        combMap.put("13457", 2);
        combMap.put("13467", 3);
        combMap.put("2346", 4);
        combMap.put("12467", 5);
        combMap.put("124567", 6);
        combMap.put("136", 7);
        combMap.put("1234567", 8);
        combMap.put("123467", 9);
    }

    /**
     * Finds the display value.
     *
     * @return the display value
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private void findDisplayValue() throws IOException {

        final List<String> lines = lines();

        long digitInstances = 0;
        long digitSum = 0;

        for (final String line : lines) {
            final List<String> outputDigits =
                Arrays.asList(line.substring(line.indexOf(PIPE) + 2).trim().split(StringUtils.SPACE));
            digitInstances += countUniqueOutputDigitInstances(outputDigits);

            final List<Set<Character>> inputDigits =
                Arrays.asList(line.substring(0, line.indexOf(PIPE)).trim().split(StringUtils.SPACE))
                    .stream()
                    .map(this::convertToCharacterSet)
                    .collect(Collectors.toList());

            final BidiMap<Character, Integer> signalMap = determineSignalMap(inputDigits);

            digitSum += calculateDigitSum(digitSum, outputDigits, signalMap);
        }
        info("Digit instances: " + digitInstances);

        info("Sum of all output digits: " + digitSum);

    }

    /**
     * Calculate digit sum.
     *
     * @param digitSum the digit sum
     * @param outputDigits the output digits
     * @param signalMap the signal map
     * @return the long
     */
    private long calculateDigitSum(final long digitSum, final List<String> outputDigits,
            final BidiMap<Character, Integer> signalMap) {

        final StringBuilder digitString = new StringBuilder();
        for (final String digit : outputDigits) {
            final int uniqueOutputDigitOrMinusOne = determineUniqueOutputDigit(digit);
            if (uniqueOutputDigitOrMinusOne == -1) {
                final String digitSignal = determineDigitSignal(digit, signalMap);
                digitString.append(combMap.get(digitSignal));
            } else {
                digitString.append(uniqueOutputDigitOrMinusOne);
            }
        }
        return Long.parseLong(digitString.toString());
    }

    /**
     * Convert to character set.
     *
     * @param value the value
     * @return the sets the
     */
    private Set<Character> convertToCharacterSet(final String value) {

        final char[] valueCharArray = value.toCharArray();
        final Set<Character> valueCharSet = new HashSet<>(value.length());
        for (final char valueChar : valueCharArray) {
            valueCharSet.add(valueChar);
        }
        return valueCharSet;
    }

    /**
     * Determines the digit signal.
     *
     * @param digit the digit
     * @param signalMap the signal map
     * @return the string
     */
    private String determineDigitSignal(final String digit, final Map<Character, Integer> signalMap) {

        final StringBuilder digitSignal = new StringBuilder();
        for (final Character character : digit.toCharArray()) {
            digitSignal.append(signalMap.get(character));
        }
        return StringUtil.sort(digitSignal.toString());
    }

    /**
     * Determines the unique output digit.
     *
     * @param digit the digit
     * @return the int
     */
    private int determineUniqueOutputDigit(final String digit) {

        return uniqueDigitLengthMap.getOrDefault(digit.length(), -1);
    }

    /**
     * Determines the signal map.
     *
     * @param inputDigits the input digits
     * @return the bidi map
     */
    private BidiMap<Character, Integer> determineSignalMap(final List<Set<Character>> inputDigits) {

        final BidiMap<Character, Integer> signalMap = new DualHashBidiMap<>();

        final Map<Integer, Set<Character>> uniqueLengthInputSignalsMap = inputDigits
            .stream()
            .filter(value -> value.size() == 2 || value.size() == 4 || value.size() == 3 || value.size() == 7)
            .collect(Collectors.toMap(value -> uniqueDigitLengthMap.get(value.size()), value -> value));

        final List<Set<Character>> zeroSixNineList =
            inputDigits
                .stream()
                .filter(value -> value.size() == 6)
                .collect(Collectors.toList());

        signalMap.put(uniqueLengthInputSignalsMap.get(7).stream().filter(val -> !uniqueLengthInputSignalsMap.get(1).contains(val))
            .findFirst().orElse('z'), 1);

        filterOutSix(signalMap, uniqueLengthInputSignalsMap, zeroSixNineList);

        final Set<Character> nine = filterOutNine(uniqueLengthInputSignalsMap, zeroSixNineList);

        signalMap.put(signalAlphabet.stream().filter(alphabetChar -> !nine.contains(alphabetChar)).findFirst().orElse('z'), 5);

        signalMap.put(signalAlphabet.stream().filter(alphabetChar -> !zeroSixNineList.get(0).contains(alphabetChar)).findFirst()
            .orElse('z'), 4);

        signalMap.put(uniqueLengthInputSignalsMap.get(4).stream().filter(digitChar -> digitChar != signalMap.getKey(3)
            && digitChar != signalMap.getKey(4)
            && digitChar != signalMap.getKey(6)).findFirst().orElse('z'), 2);

        signalMap.put(signalAlphabet.stream().filter(alphabetChar -> !signalMap.containsKey(alphabetChar)).findFirst().orElse('z'),
            7);

        return signalMap;
    }

    /**
     * Filter out nine.
     *
     * @param uniqueLengthInputSignalsMap the unique length input signals map
     * @param zeroSixNineList the zero six nine list
     * @return the sets the
     */
    private Set<Character> filterOutNine(final Map<Integer, Set<Character>> uniqueLengthInputSignalsMap,
            final List<Set<Character>> zeroSixNineList) {

        int removalIndex = -1;
        final Set<Character> nine = new HashSet<>();
        for (int index = 0; index < 2; index++) {
            final Set<Character> value = zeroSixNineList.get(index);
            boolean isNine = true;
            for (final Character digitChar : uniqueLengthInputSignalsMap.get(4)) {
                if (!value.contains(digitChar)) {
                    isNine = false;
                    break;
                }
            }
            if (isNine) {
                nine.addAll(value);
                removalIndex = index;
                break;
            }
        }
        zeroSixNineList.remove(removalIndex);
        return nine;
    }

    /**
     * Filter out six.
     *
     * @param signalMap the signal map
     * @param uniqueLengthInputSignalsMap the unique length input signals map
     * @param zeroSixNineList the zero six nine list
     */
    private void filterOutSix(final BidiMap<Character, Integer> signalMap,
            final Map<Integer, Set<Character>> uniqueLengthInputSignalsMap, final List<Set<Character>> zeroSixNineList) {

        int removalIndex = -1;
        for (int index = 0; index < 3; index++) {
            final Set<Character> value = zeroSixNineList.get(index);
            for (final Character digitChar : uniqueLengthInputSignalsMap.get(1)) {
                if (!value.contains(digitChar)) {
                    signalMap.put(digitChar, 3);
                    signalMap.put(uniqueLengthInputSignalsMap.get(1).stream().filter(val -> val != digitChar).findFirst().orElse(
                        'z'), 6);
                    removalIndex = index;
                    break;
                }
            }
        }

        zeroSixNineList.remove(removalIndex);
    }

    /**
     * Count of the unique output digit instances.
     *
     * @param outputDigits the output digits
     * @return the unique output digit instances count
     */
    private long countUniqueOutputDigitInstances(final List<String> outputDigits) {

        return outputDigits
            .stream()
            .filter(value -> value.length() == 2 || value.length() == 4 || value.length() == 3 || value.length() == 7)
            .count();
    }

}
