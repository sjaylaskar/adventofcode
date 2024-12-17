/*
 * Copyright (©) 2024 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.japps.adventofcode.probs2024;

import com.japps.adventofcode.util.AbstractSolvable;
import com.japps.adventofcode.util.Loggable;

import java.io.IOException;
import java.util.*;

import static com.japps.adventofcode.util.ProblemSolverUtil.COMMA;
import static com.japps.adventofcode.util.ProblemSolverUtil.commify;

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
    private static final String REGISTER_A = "A";
    private static final String REGISTER_B = "B";
    private static final String REGISTER_C = "C";
    private static final long MIN_RANGE = 35184372088832L;
    private static final long MAX_VALUE = Long.MAX_VALUE;
    private static final long MAX_RANGE = MAX_VALUE / 32767;

    private void compute() throws IOException {
		List<String> lines = lines();
        long registerA = Integer.parseInt(lines.getFirst().substring(lines.getFirst().indexOf(INPUT_INSTRUCTION_PREFIX) + 2));
        long registerB = Integer.parseInt(lines.get(1).substring(lines.get(1).indexOf(INPUT_INSTRUCTION_PREFIX) + 2));
        long registerC = Integer.parseInt(lines.get(2).substring(lines.get(2).indexOf(INPUT_INSTRUCTION_PREFIX) + 2));
        List<Long> program = Arrays.stream(lines.get(4).substring(lines.get(4).indexOf(INPUT_INSTRUCTION_PREFIX) + 2).split(COMMA)).map(Long::parseLong).toList();
        println(computeProgram(registerA, registerB, registerC, program));
        println(computeRegisterAValue(program));
    }

    private long computeRegisterAValue(List<Long> program) {
        for (long i = MIN_RANGE; i <= MAX_RANGE; i++) {
            String commifiedOutput = computeProgram(i, 0, 0, program);
            println("i: " + i + ", output: " + commifiedOutput);
            println("Length of output: " + commifiedOutput.length());
            if (commify(program).equals(commifiedOutput)) {
                return i;
            }
        }
        return -1;
    }

    private String computeProgram(long registerA, long registerB, long registerC, List<Long> program) {
        Map<String, Long> registerMap = new HashMap<>();
        registerMap.put(REGISTER_A, registerA);
        registerMap.put(REGISTER_B, registerB);
        registerMap.put(REGISTER_C, registerC);
        List<Long> output = executeProgram(program, registerMap);
        return commify(output);
    }

    private List<Long> executeProgram(List<Long> program, Map<String, Long> registerMap) {
        List<Long> output = new ArrayList<>();
        int instructionPointer = 0;
        while (instructionPointer < program.size()) {
            long opcode = program.get(instructionPointer);
            long operand = program.get(instructionPointer + 1);
            switch ((int) opcode) {
                case 0: // adv
                    registerMap.put(REGISTER_A, advValue(registerMap, operand));
                    break;
                case 1: // bxl
                    registerMap.put(REGISTER_B, registerMap.get(REGISTER_B) ^ operand);
                    break;
                case 2: // bst
                    registerMap.put(REGISTER_B, comboOpMod8(registerMap, operand));
                    break;
                case 3: // jnz
                    if (registerMap.get(REGISTER_A) != 0) {
                        instructionPointer = (int) operand;
                        continue;
                    }
                    break;
                case 4: // bxc
                    registerMap.put(REGISTER_B, registerMap.get(REGISTER_B) ^ registerMap.get(REGISTER_C));
                    break;
                case 5: // out
                    output.add(comboOpMod8(registerMap, operand));
                    break;
                case 6: // bdv
                    registerMap.put(REGISTER_B, advValue(registerMap, operand));
                    break;
                case 7: // cdv
                    registerMap.put(REGISTER_C, advValue(registerMap, operand));
                    break;
                default:
                    throw new IllegalArgumentException("Invalid opcode: " + opcode);
            }
            instructionPointer += 2;
        }
        return output;
    }

    private long comboOpMod8(Map<String, Long> registerMap, long operand) {
        return findActualOperand(registerMap, operand) % 8;
    }

    private long advValue(Map<String, Long> registerMap, long operand) {
        return registerMap.get(REGISTER_A) / (long) (Math.pow(2, findActualOperand(registerMap, operand)));
    }

    private long findActualOperand(Map<String, Long> registerMap, long operand) {
        return switch ((int) operand) {
            case 0, 1, 2, 3 -> operand;
            case 4 -> registerMap.get(REGISTER_A);
            case 5 -> registerMap.get(REGISTER_B);
            case 6 -> registerMap.get(REGISTER_C);
            default -> -1;
        };
    }
}
