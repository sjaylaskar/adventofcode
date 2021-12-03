/*
 * Id: Prob5BMissingSeatFinder.java 30-Nov-2021 1:52:54 am SubhajoyLaskar
 * Copyright (©) 2021 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.japps.adventofcode.probs2020;

import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import com.japps.adventofcode.util.AbstractSolvable;
import com.japps.adventofcode.util.Loggable;


/**
 * The prob 5 B missing seat finder.
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public final class Prob5BMissingSeatFinder extends AbstractSolvable implements Loggable {

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
    private static final Prob5BMissingSeatFinder INSTANCE = instance();

    /**
     * Instantiates a new prob 5 B missing seat finder.
     */
    private Prob5BMissingSeatFinder() {

    }

    /**
     * Instance.
     *
     * @return the prob 5 B missing seat finder
     */
    private static Prob5BMissingSeatFinder instance() {
        return new Prob5BMissingSeatFinder();
    }

    /**
     * The main method.
     *
     * @param args the arguments
     */
    public static void main(final String[] args) {
        try {
            INSTANCE.info(INSTANCE.findMissingSeatId());
        } catch (final IOException exception) {
            INSTANCE.error(exception.getLocalizedMessage());
        }
    }

    /**
     * Finds the missing seat id.
     *
     * @return the missing seat id
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private long findMissingSeatId() throws IOException {

        final Set<Long> seatIds = lines().stream().map(this::calculateSeatId).collect(Collectors.toSet());

        return LongStream.rangeClosed(0, 1023)
                .filter(seatId ->
                       !seatIds.contains(seatId)
                       && seatIds.contains(seatId + 1)
                       && seatIds.contains(seatId - 1))
                       .boxed().toList().get(0);
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
