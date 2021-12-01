/*
* Id: Solvable.java 01-Dec-2021 12:26:02 pm SubhajoyLaskar
* Copyright (©) 2021 Subhajoy Laskar
* https://www.linkedin.com/in/subhajoylaskar
*/
package com.japps.adventofcode.util;


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
}
