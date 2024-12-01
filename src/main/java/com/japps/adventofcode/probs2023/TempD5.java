/*
 * Id: TempD5.java 05-Dec-2023 SubhajoyLaskar
 * Copyright (©) 2023 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.japps.adventofcode.probs2023;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TempD5 {

	public static void main(final String[] args) {
		final TempD5 tempD5 = new TempD5();
		System.out.println(tempD5.solve(true, new Scanner(TempD5.class.getResourceAsStream("/com/japps/adventofcode/probs2023/input5.txt"))));
		System.out.println(tempD5.solve(false, new Scanner(TempD5.class.getResourceAsStream("/com/japps/adventofcode/probs2023/input5.txt"))));
	}

    /**
     * Main solving method.
     *
     * @param part1 The solver will solve part 1 if param is set to true.
     *              The solver will solve part 2 if param is set to false.
     * @param in    The solver will read data from this Scanner.
     * @return Returns answer in string format.
     */
    public String solve(final boolean part1, final Scanner in) {
        long answer = 10000000000L;
        final List<RangeMap> rangeMaps = new ArrayList<>();
        List<Range> tmp = new ArrayList<>();
        final String[] stringSeeds = in.nextLine().split(" ");
        final long[] seeds = new long[stringSeeds.length - 1];
        for (int i = 1; i < stringSeeds.length; i++) {
            seeds[i - 1] = Long.parseLong(stringSeeds[i]);
        }
        while (in.hasNext()) {
            final String line = in.nextLine();
            if (line.equals("")) {
                continue;
            }
            if (line.contains("map")) {
                if (tmp.size() > 0) {
                    rangeMaps.add(new RangeMap(tmp));
                }
                tmp = new ArrayList<>();
            } else {
                tmp.add(new Range(line));
            }
        }
        rangeMaps.add(new RangeMap(tmp));
        if (part1) {
            for (final Long seed : seeds) {
                long val = seed;
                for (final RangeMap rangeMap : rangeMaps) {
                    val = rangeMap.convert(val);
                }
                if (val < answer) {
                    answer = val;
                }
            }
        } else {
            for (int i = 0; i < seeds.length; i += 2) {
                for (long j = seeds[i]; j < seeds[i] + seeds[i + 1]; j++) {
                    final long[] ret = returnValAndBound(j, rangeMaps);
                    if (ret[0] < answer) {
                        answer = ret[0];
                    }
                    j += ret[1];
                }
            }
        }
        return answer + "";
    }

    private long[] returnValAndBound(long val, final List<RangeMap> rangeMaps) {
        long bound = 10000000000L;
        for (final RangeMap rangeMap : rangeMaps) {
            bound = Math.min(bound, rangeMap.convert2(val)[1]);
            val = rangeMap.convert2(val)[0];
        }
        return new long[]{val, bound};
    }
}

class Range {
    long destination;
    long source;
    long range;

    public Range(final long des, final long src, final long r) {
        destination = des;
        source = src;
        range = r;
    }

    public Range(final String line) {
        final String[] pieces = line.split(" ");
        destination = Long.parseLong(pieces[0]);
        source = Long.parseLong(pieces[1]);
        range = Long.parseLong(pieces[2]);
    }
}

class RangeMap {
    List<Long> starts;
    List<Long> ends;
    List<Long> betweens;

    public RangeMap(final List<Range> ranges) {
        starts = new ArrayList<>();
        ends = new ArrayList<>();
        betweens = new ArrayList<>();
        for (final Range range : ranges) {
            starts.add(range.source);
            ends.add(range.destination);
            betweens.add(range.range);
        }
    }

    public long convert(final long val) {
        for (int i = 0; i < starts.size(); i++) {
            if (starts.get(i) <= val && starts.get(i) + betweens.get(i) > val) {
                return ends.get(i) + (val - starts.get(i));
            }
        }
        return val;
    }

    public long[] convert2(final long val) {
        long nextStart = 10000000000L;
        for (int i = 0; i < starts.size(); i++) {
            if (starts.get(i) > val) {
                nextStart = Math.min(nextStart, starts.get(i) - val - 1);
            }
            if (starts.get(i) <= val && starts.get(i) + betweens.get(i) > val) {
                return new long[]{ends.get(i) + (val - starts.get(i)), betweens.get(i) - (val - starts.get(i)) - 1};
            }
        }
        return new long[]{val, nextStart == 10000000000L ? 0 : nextStart};
    }
}