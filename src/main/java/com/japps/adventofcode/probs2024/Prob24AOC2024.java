/*
 * Copyright (©) 2024 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.japps.adventofcode.probs2024;

import java.io.*;
import java.math.*;
import java.util.*;
import java.util.function.*;
import java.util.stream.*;

import org.apache.commons.lang3.*;

import com.japps.adventofcode.util.*;

public final class Prob24AOC2024 extends AbstractSolvable implements Loggable {

	private static final Prob24AOC2024 INSTANCE = instance();
	private static final String COLON_SPACE = ": ";
	private static final String WIRE_PREFIX_X = "x";
	private static final String WIRE_PREFIX_Y = "y";
	private static final String WIRE_PREFIX_Z = "z";
	private static final String NUMBER_ZERO = "0";

	private Prob24AOC2024() {

	}

	private static Prob24AOC2024 instance() {

		return new Prob24AOC2024();
	}

	public static void main(final String[] args) {

		try {
			INSTANCE.compute();
		} catch (final IOException exception) {
			INSTANCE.error(exception.getLocalizedMessage());
		}
	}

	private void compute() throws IOException {
		List<String> lines = lines();
		Map<String, Integer> wireValues = new HashMap<>();
		List<WireGateFunction> wireGateFunctions = new ArrayList<>();
		computeZWiresNumValue(lines, wireValues, wireGateFunctions);
		computeSwappedWires(wireValues);
	}

	private void computeSwappedWires(Map<String, Integer> wireValues) {
		BigInteger xWiresNum = new BigInteger(wirePrefixBinaryNumber(wireValues, WIRE_PREFIX_X), 2);
		println(xWiresNum);
		BigInteger yWiresNum = new BigInteger(wirePrefixBinaryNumber(wireValues, WIRE_PREFIX_Y), 2);
		println(yWiresNum);
		BigInteger proposedZWireNum = xWiresNum.add(yWiresNum);
		String zWireNumBinary = proposedZWireNum.toString(2);
		println("z-wire num should be: " + proposedZWireNum + "; binary: " + zWireNumBinary);
		Map<String, Integer> zWireActualBinaryValues = new HashMap<>();
		for (int j = 0; j < zWireNumBinary.length(); j++) {
			zWireActualBinaryValues.put(WIRE_PREFIX_Z + determineZWireSuffix(zWireNumBinary.length() - j - 1), Integer.parseInt(String.valueOf(zWireNumBinary.charAt(j))));
		}
		println(zWireActualBinaryValues);
		println(wireValues.entrySet().stream().filter(entry -> entry.getKey().startsWith(WIRE_PREFIX_Z))
				.filter(entry -> !Objects.equals(zWireActualBinaryValues.get(entry.getKey()), entry.getValue()))
				.sorted(Map.Entry.comparingByKey(Comparator.reverseOrder()))
				.map(Map.Entry::getKey)
				.toList());
	}

	private void computeZWiresNumValue(List<String> lines, Map<String, Integer> wireValues, List<WireGateFunction> wireGateFunctions) {
		int i = 0;
		for (; i < lines.size(); i++) {
			String line = lines.get(i);
			if (StringUtils.EMPTY.equals(line)) {
				break;
			}
			String[] wireValue = line.split(COLON_SPACE);
			wireValues.put(wireValue[0], Integer.parseInt(wireValue[1]));
		}
		for (i++; i < lines.size(); i++) {
			String line = lines.get(i);
			String[] wireGateFunction = line.split(StringUtils.SPACE);
			wireGateFunctions.add(WireGateFunction.of(wireGateFunction[0], wireGateFunction[2], wireGateFunction[4], Gate.of(wireGateFunction[1])));
		}
		while (wireGateFunctions.stream().anyMatch(Predicate.not(WireGateFunction::isResolvedWireValues))) {
			wireGateFunctions.stream().filter(Predicate.not(WireGateFunction::isResolvedWireValues)).forEach(wireGateFunction -> resolveValues(wireGateFunction, wireValues));
		}
		println(wireValues);
		println(new BigInteger(wirePrefixBinaryNumber(wireValues, WIRE_PREFIX_Z), 2));
	}

	private String determineZWireSuffix(int zWireSuffix) {
		return String.valueOf(((zWireSuffix > 9) ? zWireSuffix : NUMBER_ZERO + zWireSuffix));
	}

	private static String wirePrefixBinaryNumber(Map<String, Integer> wireValues, String wirePrefix) {
		return wireValues.entrySet().stream().filter(entry -> entry.getKey().startsWith(wirePrefix)).sorted(Map.Entry.comparingByKey(Comparator.reverseOrder())).map(entry -> String.valueOf(entry.getValue())).collect(Collectors.joining());
	}

	private void resolveValues(WireGateFunction wireGateFunction, Map<String, Integer> wireValues) {
		switch (wireGateFunction.gate()) {
		case XOR -> resolveXORValues(wireGateFunction, wireValues);
		case AND -> resolveANDValues(wireGateFunction, wireValues);
		case OR -> resolveORValues(wireGateFunction, wireValues);
		}
	}

	private static void resolveXORValues(WireGateFunction wireGateFunction, Map<String, Integer> wireValues) {
		if (wireValues.containsKey(wireGateFunction.inputWire1()) && wireValues.containsKey(wireGateFunction.inputWire2())) {
			wireValues.put(wireGateFunction.outputWire(), wireValues.get(wireGateFunction.inputWire1()) ^ wireValues.get(wireGateFunction.inputWire2()));
			wireGateFunction.isResolvedWireValues(true);
		} else if (wireValues.containsKey(wireGateFunction.outputWire())) {
			if (wireValues.containsKey(wireGateFunction.inputWire1())) {
				resolveXORValues(wireGateFunction, wireValues, wireGateFunction.inputWire1(), wireGateFunction.inputWire2());
			} else if (wireValues.containsKey(wireGateFunction.inputWire2())) {
				resolveXORValues(wireGateFunction, wireValues, wireGateFunction.inputWire2(), wireGateFunction.inputWire1());
			}
		}
	}

	private static void resolveXORValues(WireGateFunction wireGateFunction, Map<String, Integer> wireValues, String knownWire, String unknownWire) {
		int outputWireValue = wireValues.get(wireGateFunction.outputWire());
		if (outputWireValue == 0) {
			wireValues.put(unknownWire, wireValues.get(knownWire));
		} else {
			wireValues.put(unknownWire, ~wireValues.get(knownWire));
		}
		wireGateFunction.isResolvedWireValues(true);
	}

	private static void resolveANDValues(WireGateFunction wireGateFunction, Map<String, Integer> wireValues) {
		if (wireValues.containsKey(wireGateFunction.inputWire1()) && wireValues.containsKey(wireGateFunction.inputWire2())) {
			wireValues.put(wireGateFunction.outputWire(), wireValues.get(wireGateFunction.inputWire1()) & wireValues.get(wireGateFunction.inputWire2()));
			wireGateFunction.isResolvedWireValues(true);
		} else if (wireValues.containsKey(wireGateFunction.outputWire())) {
			if (wireValues.get(wireGateFunction.outputWire()) == 1) {
				wireValues.putIfAbsent(wireGateFunction.inputWire1(), 1);
				wireValues.putIfAbsent(wireGateFunction.inputWire2(), 1);
				wireGateFunction.isResolvedWireValues(true);
			} else if (wireValues.containsKey(wireGateFunction.inputWire1())) {
				resolveANDValues(wireGateFunction, wireValues, wireGateFunction.inputWire1(), wireGateFunction.inputWire2());
			} else if (wireValues.containsKey(wireGateFunction.inputWire2())) {
				resolveANDValues(wireGateFunction, wireValues, wireGateFunction.inputWire2(), wireGateFunction.inputWire1());
			}
		} else if ((wireValues.containsKey(wireGateFunction.inputWire1()) && wireValues.get(wireGateFunction.inputWire1()) == 0) || (wireValues.containsKey(wireGateFunction.inputWire2()) && wireValues.get(wireGateFunction.inputWire2()) == 0)) {
			wireValues.put(wireGateFunction.outputWire(), 0);
		}
	}

	private static void resolveANDValues(WireGateFunction wireGateFunction, Map<String, Integer> wireValues, String knownWire, String unknownWire) {
		if (wireValues.get(knownWire) == 1) {
			wireValues.put(unknownWire, 0);
			wireGateFunction.isResolvedWireValues(true);
		}
	}

	private static void resolveORValues(WireGateFunction wireGateFunction, Map<String, Integer> wireValues) {
		if (wireValues.containsKey(wireGateFunction.inputWire1()) && wireValues.containsKey(wireGateFunction.inputWire2())) {
			wireValues.put(wireGateFunction.outputWire(), wireValues.get(wireGateFunction.inputWire1()) | wireValues.get(wireGateFunction.inputWire2()));
			wireGateFunction.isResolvedWireValues(true);
		} else if (wireValues.containsKey(wireGateFunction.outputWire())) {
			if (wireValues.get(wireGateFunction.outputWire()) == 0) {
				wireValues.putIfAbsent(wireGateFunction.inputWire1(), 0);
				wireValues.putIfAbsent(wireGateFunction.inputWire2(), 0);
				wireGateFunction.isResolvedWireValues(true);
			} else if (wireValues.containsKey(wireGateFunction.inputWire1())) {
				resolveORValues(wireGateFunction, wireValues, wireGateFunction.inputWire1(), wireGateFunction.inputWire2());
			} else if (wireValues.containsKey(wireGateFunction.inputWire2())) {
				resolveORValues(wireGateFunction, wireValues, wireGateFunction.inputWire2(), wireGateFunction.inputWire1());
			}
		}
	}

	private static void resolveORValues(WireGateFunction wireGateFunction, Map<String, Integer> wireValues, String knownWire, String unknownWire) {
		if (wireValues.get(knownWire) == 0) {
			wireValues.put(unknownWire, 1);
			wireGateFunction.isResolvedWireValues(true);
		}
	}

	private static final class WireGateFunction {
		private final String inputWire1;
		private final String inputWire2;
		private final String outputWire;
		private final Gate gate;
		private boolean isResolvedWireValues;

		private WireGateFunction(String inputWire1, String inputWire2, String outputWire, Gate gate) {
			this.inputWire1 = inputWire1;
			this.inputWire2 = inputWire2;
			this.outputWire = outputWire;
			this.gate = gate;
		}

		static WireGateFunction of(String inputWire1, String inputWire2, String outputWire, Gate gate) {
			return new WireGateFunction(inputWire1, inputWire2, outputWire, gate);
		}

		String inputWire1() {
			return inputWire1;
		}

		String inputWire2() {
			return inputWire2;
		}

		String outputWire() {
			return outputWire;
		}

		Gate gate() {
			return gate;
		}

		WireGateFunction isResolvedWireValues(boolean isResolvedWireValues) {
			this.isResolvedWireValues = isResolvedWireValues;
			return this;
		}

		boolean isResolvedWireValues() {
			return this.isResolvedWireValues;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o)
				return true;
			if (o == null || getClass() != o.getClass())
				return false;
			WireGateFunction that = (WireGateFunction) o;
			return Objects.equals(inputWire1, that.inputWire1) && Objects.equals(inputWire2, that.inputWire2) && Objects.equals(outputWire, that.outputWire) && gate == that.gate;
		}

		@Override
		public int hashCode() {
			return Objects.hash(inputWire1, inputWire2, outputWire, gate);
		}
	}

	private enum Gate {
		XOR, AND, OR;

		static Gate of(String gate) {
			return switch (gate) {
				case "XOR" -> XOR;
				case "AND" -> AND;
				case "OR" -> OR;
				default -> throw new IllegalArgumentException("Invalid gate.");
			};
		}
	}
}
