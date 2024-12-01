/*
 * Id: Temp2D5.java 06-Dec-2023 SubhajoyLaskar
 * Copyright (©) 2023 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.japps.adventofcode.probs2023;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Function {
    List<long[]> tuples;

    public Function(final String S) {
        final String[] lines = S.split("\n");
        tuples = new ArrayList<>();
        for (int i = 1; i < lines.length; i++) {
            final String[] values = lines[i].split(" ");
            final long[] tuple = Arrays.stream(values).mapToLong(Long::parseLong).toArray();
            tuples.add(tuple);
        }
    }

    public long applyOne(final long x) {
        for (final long[] tuple : tuples) {
            final long dst = tuple[0];
            final long src = tuple[1];
            final long sz = tuple[2];
            if (src <= x && x < src + sz) {
                return x + dst - src;
            }
        }
        return x;
    }

    public List<long[]> applyRange(List<long[]> R) {
        final List<long[]> A = new ArrayList<>();
        final List<long[]> NR = new ArrayList<>();
        for (final long[] tuple : tuples) {
            final long dest = tuple[0];
            final long src = tuple[1];
            final long sz = tuple[2];
            final long srcEnd = src + sz;
            while (!R.isEmpty()) {
                final long[] range = R.remove(0);
                final long st = range[0];
                final long ed = range[1];
                final long[] before = {st, Math.min(ed, src)};
                final long[] inter = {Math.max(st, src), Math.min(srcEnd, ed)};
                final long[] after = {Math.max(srcEnd, st), ed};
                if (before[1] > before[0]) {
                    NR.add(before);
                }
                if (inter[1] > inter[0]) {
                    A.add(new long[]{inter[0] - src + dest, inter[1] - src + dest});
                }
                if (after[1] > after[0]) {
                    NR.add(after);
                }
            }
            R = NR;
        }
        A.addAll(R);
        return A;
    }
}

public class Temp2D5 {
    public static void main(final String[] args) {
        try {
            final List<String> lines = Files.readAllLines(Paths.get("D:\\Dev\\codespaces\\adventofcode\\src\\main\\resources\\com\\japps\\adventofcode\\probs2023\\input5.txt"));

            final String seedStr = lines.get(0).split(":")[1].trim();
            final String[] seedValues = seedStr.split(" ");
            final List<Long> seed = new ArrayList<>();
            for (final String value : seedValues) {
                seed.add(Long.parseLong(value));
            }

            final List<Function> Fs = new ArrayList<>();
            for (int i = 1; i < lines.size(); i++) {
                Fs.add(new Function(lines.get(i)));
            }

            final List<Long> P1 = new ArrayList<>();
            for (long x : seed) {
                for (final Function f : Fs) {
                    x = f.applyOne(x);
                }
                P1.add(x);
            }
            System.out.println(P1.stream().mapToLong(Long::longValue).min().orElse(-1));

            final List<Long> P2 = new ArrayList<>();
            for (int i = 0; i < seed.size(); i += 2) {
                final long st = seed.get(i);
                final long sz = seed.get(i + 1);
                List<long[]> R = new ArrayList<>(List.of(new long[]{st, st + sz}));
                for (final Function f : Fs) {
                    R = f.applyRange(R);
                }
                P2.add(R.stream().mapToLong(range -> range[0]).min().orElse(-1));
            }
            System.out.println(P2.stream().mapToLong(Long::longValue).min().orElse(-1));
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }
}
