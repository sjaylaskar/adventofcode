/*
 *
 *  * Copyright (©) 2024 Subhajoy Laskar
 *  * https://www.linkedin.com/in/subhajoylaskar
 *
 */

package com.japps.adventofcode.util;

import org.apache.commons.lang3.ArrayUtils;

import java.math.BigDecimal;
import java.util.function.*;

public final class MathUtil {

    private MathUtil() {

    }

    public static boolean containsXYRoots(double[] roots) {
        return ArrayUtils.isNotEmpty(roots) && roots.length == 2;
    }

    public static boolean isWholeNumberRoots(double[] roots) {
        return Math.floor(roots[0]) == roots[0] && Math.floor(roots[1]) == roots[1];
    }

    public static boolean isInclusivelyUpperBounded(double[] roots, int bound) {
        return roots[0] <= bound && roots[1] <= bound;
    }

    public static boolean isNonNegativeRoots(double[] roots) {
        return roots[0] >= 0 && roots[1] >= 0;
    }

    public static double[] solveDoubleSimultaneousLinearEquationsInTwoVariables(double[] xCoefficients, double[] yCoefficients, double[] constants) throws ArithmeticException {
        return SimultaneousDoubleLinearEquationSolver.solve(xCoefficients, yCoefficients, constants);
    }

    public static BigDecimal toBigD(double value) {
        return BigDecimal.valueOf(value);
    }

    public static IntPredicate oddPredicate() {
        return num -> num % 2 != 0;
    }

    public static IntPredicate evenPredicate() {
        return oddPredicate().negate();
    }
}
