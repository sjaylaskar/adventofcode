/*
 * Id: AnagramChecker2.java 26-Mar-2023 SubhajoyLaskar
 * Copyright (©) 2023 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.japps.adventofcode.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;


/**
 * The english alphabet anagram checker.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public final class EnglishAlphabetAnagramChecker implements Loggable {

	/**
	 * Instantiates a new anagram checker 2.
	 */
	private EnglishAlphabetAnagramChecker() {

	}

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(final String[] args) {
		test();
	}

	/**
	 * Test.
	 */
	private static void test() {
		final List<String[]> testStringsList = new ArrayList<>();
		testStringsList.add(new String[] {"BBDcd", "cbbd"});
		testStringsList.add(new String[] {"abcd", "cabd"});
		testStringsList.add(new String[] {"BBDc", "cbbd"});
		testStringsList.add(new String[] {"BBce", "cbbd"});
		testStringsList.add(new String[] {"BBD1", "cbbd"});
		testStringsList.add(new String[] {"XyXzZ", "ZyXXZ"});
		testStringsList.add(new String[] {"XyXasasasasasasasasaasasasasasasaasasasasasaszZ", "ZasasasasasasasasaasasasasasasaasasasasasasyXXZ"});
		testStringsList.add(new String[] {
				"wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwiiiiiiiiiooooooooooooppppppppppppppllllllllllkkkkkkkkkkkkkkkkjjjjjjjjuuuuuuuuuhjhhhhhhhhhhhhhhhhhhhjjyyyyyyyyyyyyyyyyyyyyyyyyyyyyydddddddddddddddddddddddddddddddddddddddddddddddddddXyXasasasasasasasasaasasasasasasaasasasasasaszbhbhbhsssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssshbhbhbZ",
				"ZasasbhbhbhssssssssssssssssssssssssssssssssssssssssswwwwwwwwwwwwwwwwwwwwwwwwwwwwwwiiiiiiiiiooooooooooooppppppppppppppllllllllllkkkkkkkkkkkkkkkkjjjjjjjjuuuuuuuuuhjhhhhhhhhhhhhhhhhhhhjjwwwwwwwwwwwwwwwwwwwwwwwwwwwyyyyyyyyyyyyyyyyyyyyyyyyyyyyydddddddddddddddddddddddddddddddddddddddddddddddddddssssssssssssssssssssssssssssssssssssssssssssssssshbhbhbasasasasasasaasasasasasasaasasasasasasyXXZ"});

		testStringsList
		.forEach(stringArr -> {
			try {
				System.out.println(areAnagrams(stringArr[0], stringArr[1]));
			} catch (final Exception exception) {
				LOG.error(exception.getMessage());
			}
		});
	}

	/** The Constant LOG. */
	private static final LogUtil LOG = LogUtil.newInstance(EnglishAlphabetAnagramChecker.class);

	/** The Constant ENGLISH_UPPER_LOWER_ASCII_DIFF. */
	private static final int ENGLISH_UPPER_LOWER_ASCII_DIFF = 32;

	/** The Constant ENGLISH_ALPHABET_SIZE. */
	private static final int ENGLISH_ALPHABET_SIZE = 26;

	/** The Constant ENGLISH_LOWERCASE_ASCII_START. */
	private static final int ENGLISH_LOWERCASE_ASCII_START = 97;

	/** The Constant ENGLISH_LOWERCASE_ASCII_END. */
	private static final int ENGLISH_LOWERCASE_ASCII_END = ENGLISH_LOWERCASE_ASCII_START + ENGLISH_ALPHABET_SIZE - 1;

	/** The Constant CHAR_SET. */
	private static final Set<Character> CHAR_SET = new HashSet<>();

	static {
		IntStream
		.rangeClosed(ENGLISH_LOWERCASE_ASCII_START, ENGLISH_LOWERCASE_ASCII_END)
		.forEach(index -> {
			 CHAR_SET.add((char) index);
			 CHAR_SET.add((char) (index - ENGLISH_UPPER_LOWER_ASCII_DIFF));
		});
	}

	/**
	 * Are anagrams.
	 *
	 * @param s1 the s 1
	 * @param s2 the s 2
	 * @return true, if successful
	 */
	public static boolean areAnagrams(final String s1, final String s2) {
		return areAnagrams(AnagramData.of(s1, s2));
	}

	/**
	 * Are anagrams.
	 *
	 * @param anagramData the anagram data
	 * @return true, if successful
	 */
	private static boolean areAnagrams(final AnagramData anagramData) {

		if (anagramData.s1 == null && anagramData.s2 == null) {
			return true;
		}

		if (anagramData.s1 == null && anagramData.s2 != null
		    || anagramData.s1 != null && anagramData.s2 == null) {
			return false;
		}

		if (anagramData.s1.length() != anagramData.s2.length()) {
			return false;
		}

		final int[] chars = new int[ENGLISH_ALPHABET_SIZE];

		IntStream
		.range(0, anagramData.s1.length())
		.forEach(index -> {
			chars[anagramData.s1.charAt(index) - ENGLISH_LOWERCASE_ASCII_START]++;
			chars[anagramData.s2.charAt(index) - ENGLISH_LOWERCASE_ASCII_START]--;
		});

		return IntStream.range(0, ENGLISH_ALPHABET_SIZE).noneMatch(index -> chars[index] != 0);
	}

	/**
	 * The anagram data.
	 *
	 * @author Subhajoy Laskar
	 * @version 1.0
	 */
	private static final class AnagramData {

		/** The s 1. */
		private String s1;

		/** The s 2. */
		private String s2;

		/**
		 * Instantiates a new anagram data.
		 */
		private AnagramData() {

		}

		/**
		 * Of.
		 *
		 * @param s1 the s 1
		 * @param s2 the s 2
		 * @return the anagram data
		 */
		private static final AnagramData of(final String s1, final String s2) {
			final AnagramData anagramData = new AnagramData();
			if (s1 != null) {
				validate(s1);
				anagramData.s1 = s1.toLowerCase();
			}
			if (s2 != null) {
				validate(s2);
				anagramData.s2 = s2.toLowerCase();
			}
			return anagramData;
		}

		/**
		 * Validate.
		 *
		 * @param s the s
		 */
		private static void validate(final String s) {
			if (!validateContainsEnglishChars(s)) {
				throw new IllegalArgumentException("The string: " + s + " contains characters not present in English alphabet.");
			}
		}

		/**
		 * Validate contains english chars.
		 *
		 * @param s the s
		 * @return true, if successful
		 */
		private static boolean validateContainsEnglishChars(final String s) {
			return s.chars().allMatch(character -> CHAR_SET.contains((char)character));
		}
	}
}
