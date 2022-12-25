/*
 * Id: Prob21MonkeyMath.java 24-Dec-2022 SubhajoyLaskar
 * Copyright (©) 2022 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */

package com.japps.adventofcode.probs2022;

import java.io.IOException;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.japps.adventofcode.util.AbstractSolvable;
import com.japps.adventofcode.util.Loggable;

/**
 * The prob 21 monkey math.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public final class Prob21MonkeyMath extends AbstractSolvable implements Loggable {

	/** The Constant INSTANCE. */
	private static final Prob21MonkeyMath INSTANCE = instance();

	/**
	 * Instantiates a new prob 21 monkey math.
	 */
	private Prob21MonkeyMath() {

	}

	/**
	 * Instance.
	 *
	 * @return the prob 21 monkey math
	 */
	private static Prob21MonkeyMath instance() {

		return new Prob21MonkeyMath();
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

	/** The operation map. */
	private final Map<String, Operation> operationMap = new HashMap<>();

	/**
	 * Compute.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private void compute() throws IOException {
		final List<String> lines = lines();

		lines.forEach(line -> {
			final String[] expression = line.split(":");
			final String key = expression[0].trim();
			final String[] operationExpression = expression[1].trim().split(" ");
			operationMap.put(key, operationExpression.length == 1
					? Operation.of(new BigInteger(operationExpression[0].trim()))
					: Operation.of(operationExpression[1].charAt(0), operationExpression[0], operationExpression[2]));

		});
		info("Root value: " + operate("root", BigInteger.valueOf(-1)));
		final Operation rootOperation = operationMap.get("root");
		info("Equality test pass value: " + findByBinarySearch(operate(rootOperation.operand2, BigInteger.ZERO), rootOperation.operand1));
	}

	/**
	 * Find by binary search.
	 *
	 * @param rootOperand2Value the root operand 2 value
	 * @param rootOperand1      the root operand 1
	 * @return the big integer
	 */
	private BigInteger findByBinarySearch(final BigInteger rootOperand2Value, final String rootOperand1) {
		BigInteger left = BigInteger.ZERO;
		BigInteger right = new BigInteger("100000000000000000000");
		while (left.compareTo(right) < 0) {
			final BigInteger rangeTotal = left.add(right);
			final BigInteger rangeMid = rangeTotal.divide(new BigInteger("2"));
			final BigInteger mid = rangeTotal.mod(BigInteger.valueOf(2)).equals(BigInteger.ZERO)
				   ? rangeMid
				   : rangeMid.subtract(BigInteger.ONE);
			final BigInteger equalityTestValue = rootOperand2Value.subtract(operate(rootOperand1, mid));
			if (equalityTestValue.compareTo(BigInteger.ZERO) < 0) {
				left = mid;
			} else if (equalityTestValue.equals(BigInteger.ZERO)) {
				return mid;
			} else {
				right = mid;
			}
		}
		return BigInteger.ONE.multiply(new BigInteger("-1"));

	}

	/**
	 * Operate.
	 *
	 * @param key   the key
	 * @param value the value
	 * @return the big integer
	 */
	private BigInteger operate(final String key, final BigInteger value) {
		if (StringUtils.equals(key, "humn") && value.compareTo(BigInteger.ZERO) >= 0) {
			return value;
		}
		final Operation operation = operationMap.get(key);

		if (operation.value != null) {
			return operation.value;
		} else {
			final BigInteger value1 = operate(operation.operand1, value);
			final BigInteger value2 = operate(operation.operand2, value);
			return operation.perform(value1, value2);
		}
	}

	/**
	 * The operation.
	 *
	 * @author Subhajoy Laskar
	 * @version 1.0
	 */
	private static final class Operation {

		/** The operator. */
		private final char operator;

		/** The operand 1. */
		private final String operand1;

		/** The operand 2. */
		private final String operand2;

		/** The value. */
		private BigInteger value;

		/**
		 * Instantiates a new operation.
		 *
		 * @param operator the operator
		 * @param operand1 the operand 1
		 * @param operand2 the operand 2
		 */
		private Operation(final char operator, final String operand1, final String operand2) {
			this.operator = operator;
			this.operand1 = operand1;
			this.operand2 = operand2;
			value = null;
		}

		/**
		 * Instantiates a new operation.
		 *
		 * @param value the value
		 */
		private Operation(final BigInteger value) {
			this('\0', null, null);
			this.value = value;
		}

		/**
		 * Of.
		 *
		 * @param operator the operator
		 * @param operand1 the operand 1
		 * @param operand2 the operand 2
		 * @return the operation
		 */
		private static Operation of(final char operator, final String operand1, final String operand2) {
			return new Operation(operator, operand1, operand2);
		}

		/**
		 * Of.
		 *
		 * @param value the value
		 * @return the operation
		 */
		private static Operation of(final BigInteger value) {
			return new Operation(value);
		}

		/**
		 * Perform.
		 *
		 * @param item1 the item 1
		 * @param item2 the item 2
		 * @return the big integer
		 */
		private BigInteger perform(final BigInteger item1, final BigInteger item2) {
			return operation(operator, item1, item2);
		}

		/**
		 * Operation.
		 *
		 * @param operator the operator
		 * @param operand1 the operand 1
		 * @param operand2 the operand 2
		 * @return the big integer
		 */
		private static BigInteger operation(final char operator, final BigInteger operand1, final BigInteger operand2) {

			return switch (operator) {
			case '+' -> operand1.add(operand2);
			case '-' -> operand1.subtract(operand2);
			case '*' -> operand1.multiply(operand2);
			case '/' -> operand1.divide(operand2);
			default -> throw new IllegalStateException("Unsupported operation.");
			};
		}

		/**
		 * To string.
		 *
		 * @return the string
		 */
		@Override
		public String toString() {
			return value != null ? "Operation[value = " + value + "]"
					: "Operation[" + operand1 + " " + operator + " " + operand2 + "]";
		}
	}
}
