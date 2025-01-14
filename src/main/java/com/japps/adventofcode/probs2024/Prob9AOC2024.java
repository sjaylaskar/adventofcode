/*
 * Copyright (©) 2024 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.japps.adventofcode.probs2024;

import static com.japps.adventofcode.util.MathUtil.*;

import java.io.*;
import java.util.*;
import java.util.stream.*;

import org.apache.commons.collections4.*;

import com.japps.adventofcode.util.*;

public final class Prob9AOC2024 extends AbstractSolvable implements Loggable {

	private static final Prob9AOC2024 INSTANCE = instance();

	private Prob9AOC2024() {

	}

	private static Prob9AOC2024 instance() {

		return new Prob9AOC2024();
	}

	public static void main(final String[] args) {

		try {
			INSTANCE.compute();
		} catch (final IOException exception) {
			INSTANCE.error(exception.getLocalizedMessage());
		}
	}

	private static final String EMPTY_SPACE = ".";

	private void compute() throws IOException {
		List<String> lines = lines();
		String line = lines.getFirst();
		//line = "2333133121414131402";
		computeCheckSumByBlockCompaction(line);
		computeCheckSumByFileCompaction(line);
	}

	private void computeCheckSumByBlockCompaction(String line) {
		LinkedHashMap<Integer, Integer> positionFileIdMap = new LinkedHashMap<>();
		LinkedList<Integer> emptySpacePositionList = new LinkedList<>();
		arrangement(line, positionFileIdMap, emptySpacePositionList);
		LinkedHashMap<Integer, Integer> replacedPositionFileIdMap = compaction(emptySpacePositionList, positionFileIdMap);
		println(positionFileIdProductSum(positionFileIdMap) + positionFileIdProductSum(replacedPositionFileIdMap));
	}

	private static void arrangement(String line, LinkedHashMap<Integer, Integer> positionFileIdMap, LinkedList<Integer> emptySpacePositionList) {
		int fileId = 0;
		int startPosition = 0;
		for (int i = 0; i < line.length(); i++) {
			int countOfFileLengthOrSpace = Integer.parseInt(String.valueOf(line.charAt(i)));
			int nextPosition = startPosition + countOfFileLengthOrSpace;
			if (evenPredicate().test(i)) {
				int positionFileId = fileId;
				IntStream.range(startPosition, nextPosition).forEach(position -> positionFileIdMap.put(position, positionFileId));
				fileId++;
			} else {
				IntStream.range(startPosition, nextPosition).forEach(emptySpacePositionList::add);
			}
			startPosition = nextPosition;
		}
	}

	private static LinkedHashMap<Integer, Integer> compaction(LinkedList<Integer> emptySpacePositionList, LinkedHashMap<Integer, Integer> positionFileIdMap) {
		LinkedHashMap<Integer, Integer> replacedPositionFileIdMap = new LinkedHashMap<>();
		while (CollectionUtils.isNotEmpty(emptySpacePositionList)) {
			Integer emptySpacePosition = emptySpacePositionList.pollFirst();
			Map.Entry<Integer, Integer> positionFileIdEntry = positionFileIdMap.lastEntry();
			if (emptySpacePosition > positionFileIdEntry.getKey()) {
				break;
			}
			replacedPositionFileIdMap.put(emptySpacePosition, positionFileIdMap.pollLastEntry().getValue());
		}
		return replacedPositionFileIdMap;
	}

	private static long positionFileIdProductSum(LinkedHashMap<Integer, Integer> positionFileIdMap) {
		return positionFileIdMap.entrySet().stream().mapToLong(positionFileId -> (long) positionFileId.getKey() * positionFileId.getValue()).sum();
	}

	private void computeCheckSumByFileCompaction(String line) {
		LinkedHashMap<Integer, IntPair> fileIdPositionMap = new LinkedHashMap<>();
		List<IntPair> emptySpacePositionList = new ArrayList<>();
		arrangement(line, fileIdPositionMap, emptySpacePositionList);
		LinkedHashMap<Integer, IntPair> replacedFileIdPositionMap = compaction(fileIdPositionMap, emptySpacePositionList);
		println(fileIdPositionProductSum(replacedFileIdPositionMap));
	}

	private static void arrangement(String line, LinkedHashMap<Integer, IntPair> fileIdPositionMap, List<IntPair> emptySpacePositionList) {
		int fileId = 0;
		int startPosition = 0;
		for (int i = 0; i < line.length(); i++) {
			int countOfFileLengthOrSpace = Integer.parseInt(String.valueOf(line.charAt(i)));
			int nextPosition = startPosition + countOfFileLengthOrSpace;
			if (countOfFileLengthOrSpace != 0) {
				if (evenPredicate().test(i)) {
					fileIdPositionMap.put(fileId++, IntPair.of(startPosition, nextPosition - 1));
				} else {
					emptySpacePositionList.add(IntPair.of(startPosition, nextPosition - 1));
				}
			}
			startPosition = nextPosition;
		}
	}

	private static LinkedHashMap<Integer, IntPair> compaction(LinkedHashMap<Integer, IntPair> fileIdPositionMap, List<IntPair> emptySpacePositionList) {
		LinkedHashMap<Integer, IntPair> replacedFileIdPositionMap = new LinkedHashMap<>();
		while (MapUtils.isNotEmpty(fileIdPositionMap)) {
			Map.Entry<Integer, IntPair> fileIdPositionEntry = fileIdPositionMap.pollLastEntry();
				emptySpacePositionList.stream().filter(emptySpaceAvailablePredicate(fileIdPositionEntry)).findFirst()
					.ifPresentOrElse(
							emptySpacePos -> processForAvailableEmptySpace(emptySpacePos, emptySpacePositionList, fileIdPositionEntry, replacedFileIdPositionMap),
							() -> replacedFileIdPositionMap.put(fileIdPositionEntry.getKey(), fileIdPositionEntry.getValue()));
		}
		return replacedFileIdPositionMap;
	}

	private static void processForAvailableEmptySpace(IntPair emptySpacePos, List<IntPair> emptySpacePositionList, Map.Entry<Integer, IntPair> fileIdPositionEntry, LinkedHashMap<Integer, IntPair> replacedFileIdPositionMap) {
		emptySpacePositionList.remove(emptySpacePos);
		int endCoordinate = emptySpacePos.getX() + fileIdPositionEntry.getValue().distance();
		replacedFileIdPositionMap.put(fileIdPositionEntry.getKey(), IntPair.of(emptySpacePos.getX(), endCoordinate));
		if (emptySpacePos.distance() > fileIdPositionEntry.getValue().distance()) {
			emptySpacePositionList.add(IntPair.of(endCoordinate + 1, emptySpacePos.getY()));
		}
	}

	private static java.util.function.Predicate<IntPair> emptySpaceAvailablePredicate(Map.Entry<Integer, IntPair> fileIdPositionEntry) {
		return emptySpacePos -> emptySpacePos.distance() >= fileIdPositionEntry.getValue().distance() && emptySpacePos.lessThan(fileIdPositionEntry.getValue());
	}

	private static long fileIdPositionProductSum(LinkedHashMap<Integer, IntPair> fileIdPositionMap) {
		return fileIdPositionMap.entrySet().stream().mapToLong(Prob9AOC2024::fileIdPositionProductSum).sum();
	}

	private static long fileIdPositionProductSum(Map.Entry<Integer, IntPair> fileIdPositionEntry) {
		return LongStream.rangeClosed(fileIdPositionEntry.getValue().getX(), fileIdPositionEntry.getValue().getY()).sum() * fileIdPositionEntry.getKey();
	}
}
