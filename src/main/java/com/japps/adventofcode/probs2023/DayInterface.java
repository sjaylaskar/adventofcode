/*
 * Id: DayInterface.java 26-Dec-2023 SubhajoyLaskar
 * Copyright (©) 2023 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.japps.adventofcode.probs2023;

import java.util.Map;

public interface DayInterface {
    public Map<String, String> part1Tests();
    public Map<String, String> part2Tests();
    public String part1Solution(String input);
    public String part2Solution(String input);
}