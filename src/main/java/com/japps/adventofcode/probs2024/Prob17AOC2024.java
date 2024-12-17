/*
 * Copyright (©) 2024 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.japps.adventofcode.probs2024;

import com.japps.adventofcode.util.AbstractSolvable;
import com.japps.adventofcode.util.Loggable;
import org.apache.commons.collections4.CollectionUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static com.japps.adventofcode.util.ProblemSolverUtil.COMMA;
import static com.japps.adventofcode.util.ProblemSolverUtil.commify;
import static java.util.Optional.ofNullable;

public final class Prob17AOC2024 extends AbstractSolvable implements Loggable {

    private static final Prob17AOC2024 INSTANCE = instance();

    private Prob17AOC2024() {

    }

    private static Prob17AOC2024 instance() {

        return new Prob17AOC2024();
    }

    public static void main(final String[] args) {

        try {
            INSTANCE.compute();
        } catch (final IOException exception) {
            INSTANCE.error(exception.getLocalizedMessage());
        }
    }

    private static final String INPUT_INSTRUCTION_PREFIX = ": ";

    private void compute() throws IOException {
		List<String> lines = lines();
        long registerA = Long.parseLong(lines.getFirst().substring(lines.getFirst().indexOf(INPUT_INSTRUCTION_PREFIX) + 2));
        long registerB = Long.parseLong(lines.get(1).substring(lines.get(1).indexOf(INPUT_INSTRUCTION_PREFIX) + 2));
        long registerC = Long.parseLong(lines.get(2).substring(lines.get(2).indexOf(INPUT_INSTRUCTION_PREFIX) + 2));
        List<Long> program = Arrays.stream(lines.get(4).substring(lines.get(4).indexOf(INPUT_INSTRUCTION_PREFIX) + 2).split(COMMA)).map(Long::parseLong).toList();
        println(commify(computeProgram(registerA, registerB, registerC, program)));
        long registerAValue = computeRegisterAValue(program);
        println("Output equals: " + Objects.equals(program, computeProgram(registerAValue, registerB, registerC, program)));
        println("Register A value: " + registerAValue);
    }

    private List<Long> computeProgram(long a, long b, long c, List<Long> program) {
        List<Long> output = new ArrayList<>();
        for(int instructionPointer = 0; instructionPointer < program.size();) {
            long opcode = program.get(instructionPointer);
            long operand = program.get(instructionPointer + 1);
            switch ((int) opcode) {
                case 0:
                    a >>= findActualOperand(a, b, c, operand);
                    break;
                case 1:
                    b ^= operand;
                    break;
                case 2:
                    b = actualOperandMod8(a, b, c, operand);
                    break;
                case 3:
                    if (a != 0) {
                        instructionPointer = (int) operand;
                        continue;
                    }
                    break;
                case 4:
                    b ^= c;
                    break;
                case 5:
                    output.add(actualOperandMod8(a, b, c, operand));
                    break;
                case 6:
                    b = a >> findActualOperand(a, b, c, operand);
                    break;
                case 7:
                    c = a >> findActualOperand(a, b, c, operand);
                    break;
                default:
                    throw new IllegalArgumentException("Invalid opcode: " + opcode);
            }
            instructionPointer += 2;
        }
        return output;
    }

    private static long computeRegisterAValue(List<Long> program) {
        List<Long> outputRegisterAValueList = computeRegisterAValue(program, program, 0);
        return (CollectionUtils.isNotEmpty(outputRegisterAValueList)) ? outputRegisterAValueList.getFirst() : -1;
    }

    private static List<Long> computeRegisterAValue(List<Long> program, List<Long> outputMatcher, long registerAValue) {
        if (CollectionUtils.isEmpty(outputMatcher)) {
            return new ArrayList<>();
        }
        for (long extender = 0; extender <= 7; extender++) {
            long a = (registerAValue << 3) | extender;
            long b = 0;
            long c = 0;
            long outputValue = -1;
            for (int instructionPointer = 0; instructionPointer < program.size() - 2; instructionPointer += 2) {
                long opcode = program.get(instructionPointer);
                long operand = program.get(instructionPointer + 1);
                switch ((int) opcode) {
                    case 0:
                        break;
                    case 1:
                        b ^= operand;
                        break;
                    case 2:
                        b = actualOperandMod8(a, b, c, operand);
                        break;
                    case 4:
                        b ^= c;
                        break;
                    case 5:
                        outputValue = actualOperandMod8(a, b, c, operand);
                        break;
                    case 7:
                        c = a >> findActualOperand(a, b, c, operand);
                        break;
                    default:
                        throw new IllegalArgumentException("Invalid opcode: " + opcode);
                }
                List<Long> outputValueList = outputValueList(program, outputMatcher, outputValue, a);
                if (outputValueList != null) {
                    return outputValueList;
                }
            }
        }
        return null;
    }

    private static List<Long> outputValueList(List<Long> program, List<Long> outputMatcher, long outputValue, long a) {
        if (outputValue != -1 && outputMatcher.getLast().equals(outputValue)) {
            List<Long> outputValueList = computeRegisterAValue(program, outputMatcher.subList(0, outputMatcher.size() - 1), a);
            ofNullable(outputValueList).ifPresent(list -> list.add(a));
            return outputValueList;
        }
        return null;
    }

    private static long findActualOperand(long a, long b, long c, long operand) {
        return switch ((int) operand) {
            case 4 -> a;
            case 5 -> b;
            case 6 -> c;
            default -> operand;
        };
    }

    private static long actualOperandMod8(long a, long b, long c, long operand) {
        return findActualOperand(a, b, c, operand) % 8;
    }
}
