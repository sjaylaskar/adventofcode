/*
 * Id: EnglishAlphabetAnagramCheckerTest.java 27-Mar-2023 SubhajoyLaskar
 * Copyright (©) 2023 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.japps.adventofcode.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * The test for: {@link EnglishAlphabetAnagramChecker}.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public class EnglishAlphabetAnagramCheckerTest {

	/**
	 * Test are anagrams.
	 */
	@Test
	public void testAreAnagrams() {

		assertTrue(EnglishAlphabetAnagramChecker.areAnagrams("xyz", "XYZ"));
		assertFalse(EnglishAlphabetAnagramChecker.areAnagrams("xsyz", "XYZ"));
		assertFalse(EnglishAlphabetAnagramChecker.areAnagrams("xrz", "xyz"));
		assertTrue(EnglishAlphabetAnagramChecker.areAnagrams("tty", "ytt"));
		assertTrue(EnglishAlphabetAnagramChecker.areAnagrams("z", "Z"));
		assertTrue(EnglishAlphabetAnagramChecker.areAnagrams("", ""));
		assertFalse(EnglishAlphabetAnagramChecker.areAnagrams("x", ""));
		assertFalse(EnglishAlphabetAnagramChecker.areAnagrams(null, ""));
		assertTrue(EnglishAlphabetAnagramChecker.areAnagrams(null, null));
		assertThrows("The string: bb1c contains characters not present in English alphabet.",
				     IllegalArgumentException.class,
				     () -> EnglishAlphabetAnagramChecker.areAnagrams("BBLC", "bb1c"));
		assertThrows("The string: BB2C contains characters not present in English alphabet.",
			     IllegalArgumentException.class,
			     () -> EnglishAlphabetAnagramChecker.areAnagrams("BB2C", "bb1c"));
		assertThrows("The string: xyz1 contains characters not present in English alphabet.",
			     IllegalArgumentException.class,
			     () -> EnglishAlphabetAnagramChecker.areAnagrams("xyz1", "xyz2"));
		assertThrows("The string: xyz1 contains characters not present in English alphabet.",
			     IllegalArgumentException.class,
			     () -> EnglishAlphabetAnagramChecker.areAnagrams("xyz1", "xyz1"));

	}

}
