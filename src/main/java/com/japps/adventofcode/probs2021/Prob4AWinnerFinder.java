/*
 * Id: Prob4AWinnerFinder.java 30-Nov-2021 1:52:54 am SubhajoyLaskar
 * Copyright (©) 2021 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.japps.adventofcode.probs2021;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import com.japps.adventofcode.util.AbstractSolvable;
import com.japps.adventofcode.util.Loggable;


/**
 * The prob 4 A winner finder.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public final class Prob4AWinnerFinder extends AbstractSolvable implements Loggable {

    private static final String REGEX_COMMA = ",";

    /** The regex new line. */
    private static final String REGEX_NEW_LINE = "\\n";

    /** The regex blank line. */
    private static final String REGEX_BLANK_LINE = "\\n\\n";

    /** The instance. */
    private static final Prob4AWinnerFinder INSTANCE = instance();

    /**
     * Instantiates a new prob 4 A winner finder.
     */
    private Prob4AWinnerFinder() {

    }

    /**
     * Instance.
     *
     * @return the prob 4 A winner finder
     */
    private static Prob4AWinnerFinder instance() {

        return new Prob4AWinnerFinder();
    }

    /**
     * The main method.
     *
     * @param args the arguments
     */
    public static void main(final String[] args) {

        try {
            INSTANCE.info(INSTANCE.findWinner());
        } catch (final IOException exception) {
            INSTANCE.error(exception.getLocalizedMessage());
        }
    }

    /**
     * Finds the winner.
     *
     * @return the winner
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private long findWinner() throws IOException {

        final String fileString = new String(Files.readAllBytes(Paths.get(determineInputFilePath())));
        final String[] linesSplit = fileString.split(REGEX_BLANK_LINE);

        final String[] choose = linesSplit[0].split(REGEX_COMMA);

        final List<String[]> matrices = new ArrayList<>();
        final Map<Integer, List<int[]>> markedPositions = new HashMap<>();
        for (int i = 1; i < linesSplit.length; i++) {
            final String[] matrix = linesSplit[i].split(REGEX_NEW_LINE);
            final String[] finalMat = new String[25];
            int index = -1;
            for (final String mat : matrix) {
                final String[] mats = mat.split(StringUtils.SPACE);
                for (final String m : mats) {
                    if (NumberUtils.isCreatable(m)) {
                        finalMat[++index] = m.trim();
                    }
                }
            }
            matrices.add(finalMat);
        }

        final List<int[][]> intMatrices = new ArrayList<>();
        for (int i = 0; i < matrices.size(); i++) {
            final String[] matrix = matrices.get(i);
            final int[][] intMatrix = new int[5][5];
            int index = 0;
            for (int j = 0; j < 5; j++) {
                for (int k = 0; k < 5; k++) {
                    intMatrix[j][k] = Integer.parseInt(matrix[index++]);
                }
            }
            intMatrices.add(intMatrix);
        }

        int winMatrixIndex = -1;
        int winNumber = -1;
        boolean winnerFound = false;
        for (int i = 0; i < choose.length; i++) {
            if (winnerFound) {
                break;
            }
            for (int j = 0; j < intMatrices.size(); j++) {
                if (winnerFound) {
                    break;
                }
                final int[][] intMatrix = intMatrices.get(j);
                for (int k = 0; k < 5; k++) {
                    if (winnerFound) {
                        break;
                    }
                    for (int p = 0; p < 5; p++) {
                        if (intMatrix[k][p] == Integer.parseInt(choose[i])) {
                            markedPositions.putIfAbsent(j, new ArrayList<>());
                            markedPositions.get(j).add(new int[] {k, p});
                        }
                        if (markedPositions.containsKey(j)) {
                            if (isRowOrColumnMarked(intMatrix, markedPositions.get(j))) {
                                winMatrixIndex = j;
                                winNumber = Integer.parseInt(choose[i]);
                                winnerFound = true;
                                break;
                            }
                        }
                    }
                }
            }
        }

        final int[][] winMatrix = intMatrices.get(winMatrixIndex);
        long sum = 0;
        final List<int[]> markedWinMatrixPositions = markedPositions.get(winMatrixIndex);
        final List<int[]> unMarkedWinMatrixPositions = findUnMarkedWinMatrixPositions(markedWinMatrixPositions);

        for (final int[] unmarkedPos : unMarkedWinMatrixPositions) {
            sum += winMatrix[unmarkedPos[0]][unmarkedPos[1]];
        }

        return sum * winNumber;
    }

    /**
     * Finds the un marked win matrix positions.
     *
     * @param markedWinMatrixPositions the marked win matrix positions
     * @return the un marked win matrix positions
     */
    private List<int[]> findUnMarkedWinMatrixPositions(final List<int[]> markedWinMatrixPositions) {

        final List<int[]> unMarkedPos = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                boolean isMarked = false;
                for (final int[] markedPos : markedWinMatrixPositions) {
                    if (markedPos[0] == i && markedPos[1] == j) {
                        isMarked = true;
                        break;
                    }
                }
                if (!isMarked) {
                    unMarkedPos.add(new int[] {i, j});
                }
            }
        }
        return unMarkedPos;
    }

    /**
     * Indicates if is row or column marked.
     *
     * @param intMatrix the int matrix
     * @param markedPositions the marked positions
     * @return {@code true}, if is row or column marked
     */
    private boolean isRowOrColumnMarked(final int[][] intMatrix, final List<int[]> markedPositions) {

        for (int i = 0; i < 5; i++) {
            int rowMarkCount = 0;
            int colMarkCount = 0;
            for (int j = 0; j < 5; j++) {
                for (int k = 0; k < markedPositions.size(); k++) {
                    final int[] markedPos = markedPositions.get(k);
                    if (markedPos[0] == i && markedPos[1] == j) {
                        rowMarkCount++;
                    }
                    if (markedPos[1] == i && markedPos[0] == j) {
                        colMarkCount++;
                    }
                }
            }
            if (rowMarkCount == 5) {
                return true;
            }
            if (colMarkCount == 5) {
                return true;
            }
        }

        return false;
    }
}
