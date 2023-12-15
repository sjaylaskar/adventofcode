/*
 * Id: Prob14AOC2023.java 14-Dec-2023 SubhajoyLaskar
 * Copyright (©) 2023 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */

package com.japps.adventofcode.probs2023;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import com.japps.adventofcode.util.AbstractSolvable;
import com.japps.adventofcode.util.Loggable;

/**
 * The prob 14 AOC 2023.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public final class Prob15AOC2023 extends AbstractSolvable implements Loggable {

	/** The instance. */
	private static final Prob15AOC2023 INSTANCE = instance();

	/**
	 * Instantiates a new prob 14 AOC 2023.
	 */
	private Prob15AOC2023() {

	}

	/**
	 * Instance.
	 *
	 * @return the prob 14 AOC 2023
	 */
	private static Prob15AOC2023 instance() {

		return new Prob15AOC2023();
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

		final String[] steps = lines.get(0).split(",");

		println(Arrays.stream(steps).mapToInt(this::hash).sum());

		final Map<Integer, List<LabelValue>> boxes = new HashMap<>();
		for (final String step : steps) {
			if (step.charAt(step.length() - 1) == '-') {
				final String label = step.substring(0, step.length() - 1);
				final int hash = hash(label);
				if (boxes.get(hash) != null) {
					boxes.put(hash, boxes.get(hash).stream().filter(labelValue -> !StringUtils.equals(labelValue.label, label)).collect(Collectors.toList()));
				}
			} else if (step.charAt(step.length() - 2) == '=') {
				final String label = step.substring(0, step.length() - 2);
				final int value = Integer.valueOf(step.substring(step.length() - 1));
				final int hash = hash(label);
				if (boxes.get(hash) != null) {
					if (boxes.get(hash).contains(LabelValue.of(label))) {
						for (int i = 0; i < boxes.get(hash).size(); i++) {
							 if (StringUtils.equals(boxes.get(hash).get(i).label, label)) {
								 boxes.get(hash).get(i).value = value;
							 }
						}
					} else {
						boxes.get(hash).add(LabelValue.of(label, value));
					}
				} else {
					boxes.put(hash, new ArrayList<>());
					boxes.get(hash).add(LabelValue.of(label, value));
				}
			}
		}

		// println(boxes);
		int sum = 0;
		for (final Map.Entry<Integer, List<LabelValue>> box : boxes.entrySet()) {
			 int boxSum = 0;
			 if (CollectionUtils.isNotEmpty(box.getValue())) {
				 for (int i = 1; i <= box.getValue().size(); i++) {
					  boxSum += (box.getKey() + 1) * i * box.getValue().get(i - 1).value;
				 }
			 }
			 sum += boxSum;
		}
		println(sum);
	}

	private int hash(final String step) {
		int stepHash = 0;
		for (int i = 0; i < step.length(); i++) {
			stepHash = (stepHash + step.charAt(i)) * 17 % 256;
		}
		return stepHash;
	}

	private static final class LabelValue {
		private final String label;
		private int value;

		private LabelValue(final String label) {
			this.label = label;
		}

		/**
		 *
		 */
		public LabelValue(final String label, final int value) {
			this.label = label;
			this.value = value;
		}

		/**
		 *
		 */
		private static LabelValue of(final String label, final int value) {
			return new LabelValue(label, value);
		}

		private static LabelValue of(final String label) {
			return new LabelValue(label);
		}

		@Override
		public int hashCode() {
			return Objects.hash(label);
		}

		@Override
		public boolean equals(final Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (getClass() != obj.getClass()) {
				return false;
			}
			final LabelValue other = (LabelValue) obj;
			return Objects.equals(label, other.label);
		}

		@Override
		public String toString() {
			return "label: " + label + ", value: " + value;
		}



	}
}
