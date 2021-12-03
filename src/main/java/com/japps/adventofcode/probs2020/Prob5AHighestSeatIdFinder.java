/*
 * Id: Prob5AHighestSeatIdFinder.java 30-Nov-2021 1:52:54 am SubhajoyLaskar
 * Copyright (©) 2021 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.japps.adventofcode.probs2020;

import java.io.IOException;

import com.japps.adventofcode.util.AbstractSolvable;
import com.japps.adventofcode.util.Loggable;


/**
 * The prob 5 A highest seat id finder.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public final class Prob5AHighestSeatIdFinder extends AbstractSolvable implements Loggable {

    /**
     * The seat number identifier.
     *
     * @author Subhajoy Laskar
     * @version 1.0
     */
    private static enum SeatNumberIdentifier {

        /** The row. */
        ROW,

        /** The column. */
        COLUMN
    }

    /** The instance. */
    private static final Prob5AHighestSeatIdFinder INSTANCE = instance();

    /**
     * Instantiates a new prob 5 A highest seat id finder.
     */
    private Prob5AHighestSeatIdFinder() {

    }

    /**
     * Instance.
     *
     * @return the prob 5 A highest seat id finder
     */
    private static Prob5AHighestSeatIdFinder instance() {
        return new Prob5AHighestSeatIdFinder();
    }

    /**
     * The main method.
     *
     * @param args the arguments
     */
    public static void main(final String[] args) {
        try {
            INSTANCE.info(INSTANCE.findHighestSeatId());
        } catch (final IOException exception) {
            INSTANCE.error(exception.getLocalizedMessage());
        }
    }

    /**
     * Finds the highest seat id.
     *
     * @return the highest seat id
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private long findHighestSeatId() throws IOException {

        return lines().stream().map(this::calculateSeatId).mapToLong(seatNumber -> seatNumber).max().orElse(0);
    }

    /**
     * Calculate seat id.
     *
     * @param seatNumber the seat number
     * @return the seat id
     */
    private long calculateSeatId(final String seatNumber) {
        final long seatRowNumber = calculateSeatRow(seatNumber);

        final long seatColumnNumber = calculateSeatColumn(seatNumber);

        final long seatId = (seatRowNumber * 8) + seatColumnNumber;

        return seatId;
    }

    /**
     * Calculates the seat row.
     *
     * @param seatNumber the seat number
     * @return the seat row
     */
    private long calculateSeatRow(final String seatNumber) {

        return Long.parseLong(binarizeSeatNumber(
            calculateSeatNumberPartToProcess(seatNumber, 0, 7), SeatNumberIdentifier.ROW), 2);
    }

    /**
     * Calculates the seat column.
     *
     * @param seatNumber the seat number
     * @return the seat column
     */
    private long calculateSeatColumn(final String seatNumber) {

        return Long.parseLong(binarizeSeatNumber(
            calculateSeatNumberPartToProcess(seatNumber, 7, 10), SeatNumberIdentifier.COLUMN), 2);
    }

    /**
     * Calculates the seat number part to process.
     *
     * @param seatNumber the seat number
     * @param startIndex the start index
     * @param endIndex the end index
     * @return the seat number part to process
     */
    private String calculateSeatNumberPartToProcess(final String seatNumber, final int startIndex, final int endIndex) {

        return seatNumber.substring(startIndex, endIndex);
    }

    /**
     * Binarize seat number.
     *
     * @param seatRowNumber the seat row number
     * @param seatNumberIdentifier the seat number identifier
     * @return the binarized seat number
     */
    private String binarizeSeatNumber(final String seatRowNumber, final SeatNumberIdentifier seatNumberIdentifier) {
        return (SeatNumberIdentifier.ROW.equals(seatNumberIdentifier))
               ? seatRowNumber.replace("F", "0").replace("B", "1")
               : seatRowNumber.replace("L", "0").replace("R", "1");
    }
}
