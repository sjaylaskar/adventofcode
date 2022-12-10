/*
 * Id: Prob10CRTSignalComputer.java 10-Dec-2022 SubhajoyLaskar
 * Copyright (©) 2022 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */

package com.japps.adventofcode.probs2022;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.commons.lang3.StringUtils;

import com.japps.adventofcode.util.AbstractSolvable;
import com.japps.adventofcode.util.Loggable;



/**
 * The prob 10 CRT signal computer.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public final class Prob10CRTSignalComputer extends AbstractSolvable implements Loggable {

	/** The instance. */
	private static final Prob10CRTSignalComputer INSTANCE = instance();


	/**
	 * Instantiates a new prob 10 CRT signal computer.
	 */
	private Prob10CRTSignalComputer() {

	}


	/**
	 * Instance.
	 *
	 * @return the prob 10 CRT signal computer
	 */
	private static Prob10CRTSignalComputer instance() {

		return new Prob10CRTSignalComputer();
	}

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(final String[] args) {

		try {
			INSTANCE.compute();
		} catch (final IOException exception) {
			INSTANCE.error(exception.getLocalizedMessage());
		}
	}

	/**
	 * Compute.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private void compute() throws IOException {
		final List<String> lines = lines();
		final Set<Integer> cycleNumbers = new HashSet<>(Arrays.asList(20, 60, 100, 140, 180, 220));
		int cycle = 0;
		int signalStrengthSum = 0;
		int xValue = 1;
		final List<List<String>> crtImage = new ArrayList<>();
		IntStream.range(0, 6).forEach(index -> crtImage.add(new ArrayList<>()));

		for (final String line : lines) {
			if (StringUtils.equals(line, "noop")) {
				++cycle;
				signalStrengthSum = sumSignalStrength(cycleNumbers, cycle, signalStrengthSum, xValue);
				addCRTImageBits(cycle, xValue, crtImage);
			} else {
				final String[] addOps = line.split(" ");
				signalStrengthSum = sumSignalStrength(cycleNumbers, ++cycle, signalStrengthSum, xValue);
				addCRTImageBits(cycle, xValue, crtImage);
				signalStrengthSum = sumSignalStrength(cycleNumbers, ++cycle, signalStrengthSum, xValue);
				addCRTImageBits(cycle, xValue, crtImage);
				xValue += Integer.valueOf(addOps[1]);
			}
			info("Cycle: " + cycle + ", xVal: " + xValue + ", SignalStrength: " + signalStrengthSum);
		}
		info("Signal strength sum: " + signalStrengthSum);
		info("CRT Image: ");
		IntStream.range(0, 6).forEach(index -> {
			System.out.println(crtImage.get(index).stream().collect(Collectors.joining()));
		});
	}


	/**
	 * Sum signal strength.
	 *
	 * @param cycleNumbers the cycle numbers
	 * @param cycle the cycle
	 * @param signalStrengthSum the signal strength sum
	 * @param xValue the x value
	 * @return the int
	 */
	private int sumSignalStrength(final Set<Integer> cycleNumbers, final int cycle, final int signalStrengthSum, final int xValue) {
		return cycleNumbers.contains(cycle) ? signalStrengthSum + cycle * xValue : signalStrengthSum;
	}

	/**
	 * Adds the CRT image bits.
	 *
	 * @param cycle the cycle
	 * @param xValue the x value
	 * @param crtImage the crt image
	 */
	private void addCRTImageBits(final int cycle, final int xValue, final List<List<String>> crtImage) {
		final int cycIndex = cycle - 1;
		final int cycIndexRem = cycIndex % 40;
		crtImage.get(Math.floorDiv(cycIndex, 40)).add(cycIndexRem, Math.abs(xValue - cycIndexRem) <= 1 ? "#" : " ");
	}

}
