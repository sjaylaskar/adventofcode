/*
 * Copyright (©) 2024 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.japps.adventofcode.util;

public final class SimultaneousDoubleLinearEquationSolver implements Loggable {

    /**
     * Solves 2 linear equations in 2 variables.
     *
     * @param xCoefficients The x - coefficients.
     * @param yCoefficients The y - coefficients.
     * @param constants The constants.
     * @return The roots of the equation as a 1-D 2-length array of [root for x, root for y].
     * @throws ArithmeticException If the equation solving leads to any calculations causing exceptions.
     */
    public static double[] solve(double[] xCoefficients, double[] yCoefficients, double[] constants) throws ArithmeticException {
        double[][] solvables = new double[2][2];
        solvables[0][0] = yCoefficients[1] * xCoefficients[0];
        solvables[0][1] = yCoefficients[1] * constants[0];
        solvables[1][0] = yCoefficients[0] * xCoefficients[1];
        solvables[1][1] = yCoefficients[0] * constants[1];
        try {
            double rootX = (solvables[0][1] - solvables[1][1]) / (solvables[0][0] - solvables[1][0]);
            double rootY = (constants[0] - xCoefficients[0] * rootX) / yCoefficients[0];
            return new double[] {rootX, rootY};
        } catch (ArithmeticException exception) {
            LogUtil.newInstance(SimultaneousDoubleLinearEquationSolver.class).println(exception);
            throw exception;
        }
    }
}
