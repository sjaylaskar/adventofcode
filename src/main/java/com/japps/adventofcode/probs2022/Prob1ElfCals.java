/*
 * Id: Prob1ElfCals.java 03-Dec-2022 11:31:50 am SubhajoyLaskar
 * Copyright (©) 2022 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.japps.adventofcode.probs2022;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.japps.adventofcode.util.AbstractSolvable;
import com.japps.adventofcode.util.Loggable;

/**
 * The prob 1 elf cals.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public final class Prob1ElfCals extends AbstractSolvable implements Loggable {

    /** The instance. */
    private static final Prob1ElfCals INSTANCE = instance();


    /**
     * Instantiates a new prob 01.
     */
    private Prob1ElfCals() {

    }


    /**
     * Instance.
     *
     * @return the prob 01
     */
    private static Prob1ElfCals instance() {

        return new Prob1ElfCals();
    }


    /**
     * The main method.
     *
     * @param args the arguments
     */
    public static void main(final String[] args) {

        try {
            INSTANCE.compute();
        } catch (final IOException exception) {
            INSTANCE.error(exception.getLocalizedMessage());
        }
    }

    /**
     * Compute.
     *
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private void compute() throws IOException {
    	final List<String> lines = lines();
    	long sum = 0;
    	final List<Long> cals = new ArrayList<>();
    	for (final String line : lines) {
    		if (StringUtils.isBlank(line)) {
    			cals.add(sum);
    			sum = 0;
    			continue;
    		}
    		sum += Long.valueOf(StringUtils.trim(line));
    	}
    	Collections.sort(cals);
    	System.out.println(cals.get(cals.size() - 1));
    	System.out.println(cals.get(cals.size() - 1) + cals.get(cals.size() - 2) + cals.get(cals.size() - 3));
    }

}
