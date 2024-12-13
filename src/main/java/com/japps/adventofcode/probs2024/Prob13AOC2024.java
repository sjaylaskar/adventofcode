/*
 * Copyright (©) 2024 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.japps.adventofcode.probs2024;

import com.japps.adventofcode.util.AbstractSolvable;
import com.japps.adventofcode.util.Loggable;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static com.japps.adventofcode.util.MathUtil.*;

public final class Prob13AOC2024 extends AbstractSolvable implements Loggable {

    private static final Prob13AOC2024 INSTANCE = instance();

    private Prob13AOC2024() {

    }

    private static Prob13AOC2024 instance() {

        return new Prob13AOC2024();
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
        println("Cost: " + toBigD(compute(lines, true)));
        println("Cost: " + toBigD(compute(lines, false)));
    }

    private double compute(List<String> lines, boolean isBounded) {
        double costSum = 0;
        for (int i = 0; i < lines.size(); ++i) {
            String buttonALine = lines.get(i);
            String buttonBLine = lines.get(++i);
            String prizeLine = lines.get(++i);
            ++i;
            costSum += computeCost(buttonALine, buttonBLine, prizeLine, isBounded);
        }
        return costSum;
    }

    private double computeCost(String buttonALine, String buttonBLine, String prizeLine, boolean isBounded) {
        EquationParam equationParam = equationParam(buttonALine, buttonBLine, prizeLine, isBounded);

        double[] roots = solve(equationParam);

        if (isValidRoots(roots, isBounded)) {
            return ((3 * roots[0]) + roots[1]);
        }
        return 0;
    }

    private double[] solve(EquationParam equationParam) {
        double[] roots = null;
        try {
             roots = solveDoubleSimultaneousLinearEquationsInTwoVariables(
                     new double[]{equationParam.buttonACoeffs()[0], equationParam.buttonACoeffs()[1]},
                     new double[]{equationParam.buttonBCoeffs()[0], equationParam.buttonBCoeffs()[1]},
                     new double[]{equationParam.prizeCoeffs()[0], equationParam.prizeCoeffs()[1]});
        } catch (Exception exception) {
            println(exception);
        }
        return roots;
    }

    private static EquationParam equationParam(String buttonALine, String buttonBLine, String prizeLine, boolean isBounded) {
        double prizeX = Double.parseDouble(prizeLine.substring(prizeLine.indexOf("X=") + 2, prizeLine.indexOf(',')));
        double prizeY = Double.parseDouble(prizeLine.substring(prizeLine.indexOf("Y=") + 2));
        return new EquationParam(
                new double[] {Double.parseDouble(buttonALine.substring(buttonALine.indexOf("X+") + 2, buttonALine.indexOf(','))), Double.parseDouble(buttonALine.substring(buttonALine.indexOf("Y+") + 2))},
                new double[] {Double.parseDouble(buttonBLine.substring(buttonBLine.indexOf("X+") + 2, buttonBLine.indexOf(','))), Double.parseDouble(buttonBLine.substring(buttonBLine.indexOf("Y+") + 2))},
                new double[] {isBounded ? prizeX : (10000000000000d + prizeX), isBounded ? prizeY : (10000000000000d + prizeY)}
        );
    }

    private static boolean isValidRoots(double[] roots, boolean isBounded) {
        return containsXYRoots(roots) && isNonNegativeRoots(roots) && (!isBounded || isInclusivelyUpperBounded(roots, 100)) && isWholeNumberRoots(roots);
    }

    private record EquationParam(double[] buttonACoeffs, double[] buttonBCoeffs, double[] prizeCoeffs){
        @Override
        public String toString() {
            return "EquationParam{" +
                    "buttonACoeffs=" + Arrays.toString(buttonACoeffs) +
                    ", buttonBCoeffs=" + Arrays.toString(buttonBCoeffs) +
                    ", prizeCoeffs=" + Arrays.toString(prizeCoeffs) +
                    '}';
        }
    }
}
