/*
* Id: ProblemSolverUtil.java 01-Dec-2021 11:51:25 am SubhajoyLaskar
* Copyright (©) 2021 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.japps.adventofcode.util;

import org.apache.commons.lang3.math.NumberUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * The problem solver util.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public final class ProblemSolverUtil implements Loggable {

    /** The txt file extension. */
    private static final String TXT_FILE_EXTENSION = ".txt";

    /** The input. */
    private static final String INPUT = "input";

    /** The src main resources. */
    private static final String SRC_MAIN_RESOURCES = "src/main/resources/";

    /**
     * Instantiates a new problem solver util.
     */
    private ProblemSolverUtil() {

    }

    /**
     * Determines the input file path.
     *
     * @param clazz the clazz
     * @return the input file path
     */
    public static String determineInputFilePath(final Class<?> clazz) {
        final String inputFileSuffix = clazz.getSimpleName().substring(4, 6);

        final String inputFileName = determineInputFileDirectoryPath(clazz) + "/" + (INPUT + (NumberUtils.isCreatable(inputFileSuffix.substring(1)) ? inputFileSuffix : inputFileSuffix.substring(0, 1)) + TXT_FILE_EXTENSION);
        Loggable.INFO(ProblemSolverUtil.class, "Input file: " + inputFileName);
        return inputFileName;
    }

    /**
     * Determines the input file directory path.
     *
     * @param clazz the clazz
     * @return the input file directory path
     */
    private static String determineInputFileDirectoryPath(final Class<?> clazz) {

        return SRC_MAIN_RESOURCES + clazz.getPackageName().replace(".", "/");
    }

    public static char[][] linesAsArray(List<String> lines) {
        List<char[]> linesArrayList = lines.stream().map(String::toCharArray).toList();
        return linesArrayList.toArray(new char[0][0]);
    }

    public static int[][] linesAsIntArray(List<String> lines) {
        return Stream.of(linesAsArray(lines)).map(ProblemSolverUtil::convertToIntArray).toList().toArray(new int[0][0]);
    }

    private static int[] convertToIntArray(char[] line) {
        return IntStream.range(0, line.length).map(index -> Integer.parseInt(String.valueOf(line[index]))).toArray();
    }

    public static int cols(char[][] arr, int row) {
        return arr[row].length;
    }

    public static int rows(char[][] arr) {
        return arr.length;
    }

    public static int cols(int[][] arr, int row) {
        return arr[row].length;
    }

    public static int rows(int[][] arr) {
        return arr.length;
    }

    public static boolean isInBounds(IntPair coord, int rows, int cols) {
        return coord.getX() >= 0 && coord.getX() < rows && coord.getY() >= 0 && coord.getY() < cols;
    }

    public static Stream<IntPair> horizontalVerticalNeighborCoordinates(IntPair coordinate) {
        return Stream.of(
                IntPair.of(coordinate.getX() - 1, coordinate.getY()),
                IntPair.of(coordinate.getX(), coordinate.getY() + 1),
                IntPair.of(coordinate.getX() + 1, coordinate.getY()),
                IntPair.of(coordinate.getX(), coordinate.getY() - 1));
    }

    public static char value(char[][] arr, IntPair coordinate) {
        return arr[coordinate.getX()][coordinate.getY()];
    }

    public static int mid(int edge) {
        return (edge - 1) / 2;
    }

    public static void print(char[][] arr) {
        IntStream.range(0, rows(arr)).forEach(row -> System.out.println(Arrays.toString(arr[row])));
    }
}
