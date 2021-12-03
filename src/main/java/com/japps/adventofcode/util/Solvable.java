/*
* Id: Solvable.java 01-Dec-2021 12:26:02 pm SubhajoyLaskar
* Copyright (©) 2021 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.japps.adventofcode.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * The solvable.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public interface Solvable {

    default String determineInputFilePath() {
        return ProblemSolverUtil.determineInputFilePath(getClass());
    }

    default List<String> lines() throws IOException {

        try (final Stream<String> fileLinesStream = Files.lines(Paths.get(determineInputFilePath()))) {
            return fileLinesStream.collect(Collectors.toList());
        }
    }

    default long decimalize(final String binaryString) {

        return Long.parseLong(binaryString, 2);
    }
}
