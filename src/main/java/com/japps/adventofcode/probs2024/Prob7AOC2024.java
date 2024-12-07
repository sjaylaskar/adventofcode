/*
 * Copyright (©) 2024 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.japps.adventofcode.probs2024;

import com.japps.adventofcode.util.AbstractSolvable;
import com.japps.adventofcode.util.Loggable;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Stream;

public final class Prob7AOC2024 extends AbstractSolvable implements Loggable {

    private static final Prob7AOC2024 INSTANCE = instance();
    private static final String LINE_SEPARATOR = ":";
    private static final String SPACE = " ";

    enum OPERATION {
        SUM(Long::sum), MULTIPLY((Long x, Long y) -> x * y), CONCAT((Long x, Long y) -> Long.valueOf(String.valueOf(x) + y));

        private final BiFunction<Long, Long, Long> evaluation;

        OPERATION(BiFunction<Long, Long, Long> evaluation) {
            this.evaluation = evaluation;
        }

        BiFunction<Long, Long, Long> evaluation() {
            return evaluation;
        }

        Long apply(Long x, Long y) {
            return evaluation().apply(x, y);
        }
    }

    private Prob7AOC2024() {

    }

    private static Prob7AOC2024 instance() {

        return new Prob7AOC2024();
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
        long valueSum = 0;
        long valueSumWithConcat = 0;
		for (String line : lines) {
            String[] equation = line.split(LINE_SEPARATOR);
            long value = Long.parseLong(equation[0]);
            List<Long> equationValues = Stream.of(equation[1].trim().split(SPACE)).map(StringUtils::trim).map(Long::valueOf).toList();
            if (isComputeValue(value, equationValues, false)) {
                valueSum += value;
            }
            if (isComputeValue(value, equationValues, true)) {
                valueSumWithConcat += value;
            }
        }
        println(valueSum);
        println(valueSumWithConcat);
    }

    private boolean isComputeValue(long value, List<Long> equationValues, boolean isConcatAllowed) {
        return (equationValues.size() == 1)
               ? equationValues.getFirst().equals(value)
               : isSumComputation(value, equationValues, isConcatAllowed)
                || isMultiplyComputation(value, equationValues, isConcatAllowed)
                || isConcatComputation(value, equationValues, isConcatAllowed);
    }

    private boolean isSumComputation(long value, List<Long> equationValues, boolean isConcatAllowed) {
        return isComputeValue(value, computeOperatedList(equationValues, OPERATION.SUM), isConcatAllowed);
    }

    private boolean isMultiplyComputation(long value, List<Long> equationValues, boolean isConcatAllowed) {
        return isComputeValue(value, computeOperatedList(equationValues, OPERATION.MULTIPLY), isConcatAllowed);
    }

    private boolean isConcatComputation(long value, List<Long> equationValues, boolean isConcatAllowed) {
        return isConcatAllowed && isComputeValue(value, computeOperatedList(equationValues, OPERATION.CONCAT), true);
    }

    private List<Long> computeOperatedList(List<Long> equationValues, OPERATION operation) {
        List<Long> values = new ArrayList<>();
        values.add(operation.apply(equationValues.get(0), equationValues.get(1)));
        values.addAll(equationValues.subList(2, equationValues.size()));
        return values;
    }

}
