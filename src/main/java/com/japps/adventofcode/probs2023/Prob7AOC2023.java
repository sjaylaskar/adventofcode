/*
 * Id: Prob7AOC2023.java 07-Dec-2023 SubhajoyLaskar
 * Copyright (©) 2023 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.japps.adventofcode.probs2023;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.commons.collections4.CollectionUtils;

import com.japps.adventofcode.util.AbstractSolvable;
import com.japps.adventofcode.util.Loggable;

/**
 *
 * @author Subhajoy Laskar
 * @version 1.0
 */
public final class Prob7AOC2023 extends AbstractSolvable implements Loggable {

	/** The instance. */
	private static final Prob7AOC2023 INSTANCE = instance();

	/**
	 */
	private Prob7AOC2023() {

	}

	/**
	 * Instance.
	 *
	 */
	private static Prob7AOC2023 instance() {

		return new Prob7AOC2023();
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

	private static final Map<String, Integer> CARD_RANKS = new HashMap<>();

	private static final List<String> CARD_SYMBOLS = List.of("A", "K", "Q", "J", "T", "9", "8", "7", "6", "5", "4", "3", "2");

	private static final List<String> CARD_SYMBOLS_PART2 = List.of("A", "K", "Q", "T", "9", "8", "7", "6", "5", "4", "3", "2", "J");
	private static final Map<String, Integer> CARD_RANKS_PART2 = new HashMap<>();

	static {
		IntStream.range(0, 13).forEach(rank -> CARD_RANKS.put(CARD_SYMBOLS.get(rank), 13 - rank));
		IntStream.range(0, 13).forEach(rank -> CARD_RANKS_PART2.put(CARD_SYMBOLS_PART2.get(rank), 13 - rank));
	}

	/**
	 * Compute.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private void compute() throws IOException {
		final List<String> lines = lines();

		processPartOne(lines);

		processPartTwo(lines);

	}

	/**
	 * @param lines
	 */
	private void processPartTwo(final List<String> lines) {
		final List<Hand> fiveOfAKindHandTypeList = new ArrayList<>();
		final List<Hand> fourOfAKindHandTypeList = new ArrayList<>();
		final List<Hand> fullHouseHandTypeList = new ArrayList<>();
		final List<Hand> threeOfAKindHandTypeList = new ArrayList<>();
		final List<Hand> twoPairHandTypeList = new ArrayList<>();
		final List<Hand> onePairHandTypeList = new ArrayList<>();
		final List<Hand> highCardHandTypeList = new ArrayList<>();

		for (final String line : lines) {
			 final String[] handBidPair = line.split(" ");
			 final Hand hand = new Hand(handBidPair[0], Long.valueOf(handBidPair[1]));
			 final Map<Character, Long> handCharFrequencyMap
			    = hand.value.chars().mapToObj(c -> (char)c).collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
			 final Map<Character, Long> convertedCharFrequencyMap = convertJCardForHighestStrength(handCharFrequencyMap);
			 if (isFiveOfAKind(convertedCharFrequencyMap)) {
				 fiveOfAKindHandTypeList.add(hand);
			 } else if (isFourOfAKind(convertedCharFrequencyMap)) {
				 fourOfAKindHandTypeList.add(hand);
			 } else if (isFullHouse(convertedCharFrequencyMap)) {
				 fullHouseHandTypeList.add(hand);
			 } else if (isThreeOfAKind(convertedCharFrequencyMap)) {
				 threeOfAKindHandTypeList.add(hand);
			 } else if (isTwoPair(convertedCharFrequencyMap)) {
				 twoPairHandTypeList.add(hand);
			 } else if (isOnePair(convertedCharFrequencyMap)) {
				 onePairHandTypeList.add(hand);
			 } else if (isHighCard(convertedCharFrequencyMap)) {
				 highCardHandTypeList.add(hand);
			 }
		}
		final Map<Hand, Integer> handRanks = new HashMap<>();
		int highestRankYet = 0;
		if (CollectionUtils.isNotEmpty(highCardHandTypeList)) {
			Collections.sort(highCardHandTypeList, Hand::partTwoCompare);
			highestRankYet = processMap(highCardHandTypeList, handRanks, highestRankYet);
		}
		if (CollectionUtils.isNotEmpty(onePairHandTypeList)) {
			Collections.sort(onePairHandTypeList, Hand::partTwoCompare);
			highestRankYet = processMap(onePairHandTypeList, handRanks, highestRankYet);
		}
		if (CollectionUtils.isNotEmpty(twoPairHandTypeList)) {
			Collections.sort(twoPairHandTypeList, Hand::partTwoCompare);
			highestRankYet = processMap(twoPairHandTypeList, handRanks, highestRankYet);
		}
		if (CollectionUtils.isNotEmpty(threeOfAKindHandTypeList)) {
			Collections.sort(threeOfAKindHandTypeList, Hand::partTwoCompare);
			highestRankYet = processMap(threeOfAKindHandTypeList, handRanks, highestRankYet);
		}
		if (CollectionUtils.isNotEmpty(fullHouseHandTypeList)) {
			Collections.sort(fullHouseHandTypeList, Hand::partTwoCompare);
			highestRankYet = processMap(fullHouseHandTypeList, handRanks, highestRankYet);
		}
		if (CollectionUtils.isNotEmpty(fourOfAKindHandTypeList)) {
			Collections.sort(fourOfAKindHandTypeList, Hand::partTwoCompare);
			highestRankYet = processMap(fourOfAKindHandTypeList, handRanks, highestRankYet);
		}
		if (CollectionUtils.isNotEmpty(fiveOfAKindHandTypeList)) {
			Collections.sort(fiveOfAKindHandTypeList, Hand::partTwoCompare);
			highestRankYet = processMap(fiveOfAKindHandTypeList, handRanks, highestRankYet);
		}

		println(handRanks.keySet().stream().mapToLong(hand -> hand.bid * handRanks.get(hand)).sum());

	}

	/**
	 * @param handCharFrequencyMap
	 * @return
	 */
	private Map<Character, Long> convertJCardForHighestStrength(final Map<Character, Long> handCharFrequencyMap) {

		if (handCharFrequencyMap.containsKey(Character.valueOf('J'))) {
			final long jCount = handCharFrequencyMap.get(Character.valueOf('J'));
			handCharFrequencyMap.keySet()
			.stream()
			.filter(key -> !key.equals(Character.valueOf('J')))
			.max(freqComparator(handCharFrequencyMap))
			.ifPresent(key -> handCharFrequencyMap.put(key, handCharFrequencyMap.get(key) + jCount));
			handCharFrequencyMap.remove(Character.valueOf('J'));
		}

		return handCharFrequencyMap;
	}

	private Comparator<? super Character> freqComparator(final Map<Character, Long> handCharFrequencyMap) {
		return (a, b) -> {
			final int result = Long.compare(handCharFrequencyMap.get(a), handCharFrequencyMap.get(b));
			return result != 0
					? result
					: Integer.compare(CARD_RANKS_PART2.get(String.valueOf(a)), CARD_RANKS_PART2.get(String.valueOf(b)));
		};
	}

	private void processPartOne(final List<String> lines) {
		final List<Hand> fiveOfAKindHandTypeList = new ArrayList<>();
		final List<Hand> fourOfAKindHandTypeList = new ArrayList<>();
		final List<Hand> fullHouseHandTypeList = new ArrayList<>();
		final List<Hand> threeOfAKindHandTypeList = new ArrayList<>();
		final List<Hand> twoPairHandTypeList = new ArrayList<>();
		final List<Hand> onePairHandTypeList = new ArrayList<>();
		final List<Hand> highCardHandTypeList = new ArrayList<>();

		for (final String line : lines) {
			 final String[] handBidPair = line.split(" ");
			 final Hand hand = new Hand(handBidPair[0], Long.valueOf(handBidPair[1]));
			 final Map<Character, Long> handCharFrequencyMap
			    = hand.value.chars().mapToObj(c -> (char)c).collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
			 if (isFiveOfAKind(handCharFrequencyMap)) {
				 fiveOfAKindHandTypeList.add(hand);
			 } else if (isFourOfAKind(handCharFrequencyMap)) {
				 fourOfAKindHandTypeList.add(hand);
			 } else if (isFullHouse(handCharFrequencyMap)) {
				 fullHouseHandTypeList.add(hand);
			 } else if (isThreeOfAKind(handCharFrequencyMap)) {
				 threeOfAKindHandTypeList.add(hand);
			 } else if (isTwoPair(handCharFrequencyMap)) {
				 twoPairHandTypeList.add(hand);
			 } else if (isOnePair(handCharFrequencyMap)) {
				 onePairHandTypeList.add(hand);
			 } else if (isHighCard(handCharFrequencyMap)) {
				 highCardHandTypeList.add(hand);
			 }
		}
		final Map<Hand, Integer> handRanks = new HashMap<>();
		int highestRankYet = 0;
		if (CollectionUtils.isNotEmpty(highCardHandTypeList)) {
			Collections.sort(highCardHandTypeList);
			highestRankYet = processMap(highCardHandTypeList, handRanks, highestRankYet);
		}
		if (CollectionUtils.isNotEmpty(onePairHandTypeList)) {
			Collections.sort(onePairHandTypeList);
			highestRankYet = processMap(onePairHandTypeList, handRanks, highestRankYet);
		}
		if (CollectionUtils.isNotEmpty(twoPairHandTypeList)) {
			Collections.sort(twoPairHandTypeList);
			highestRankYet = processMap(twoPairHandTypeList, handRanks, highestRankYet);
		}
		if (CollectionUtils.isNotEmpty(threeOfAKindHandTypeList)) {
			Collections.sort(threeOfAKindHandTypeList);
			highestRankYet = processMap(threeOfAKindHandTypeList, handRanks, highestRankYet);
		}
		if (CollectionUtils.isNotEmpty(fullHouseHandTypeList)) {
			Collections.sort(fullHouseHandTypeList);
			highestRankYet = processMap(fullHouseHandTypeList, handRanks, highestRankYet);
		}
		if (CollectionUtils.isNotEmpty(fourOfAKindHandTypeList)) {
			Collections.sort(fourOfAKindHandTypeList);
			highestRankYet = processMap(fourOfAKindHandTypeList, handRanks, highestRankYet);
		}
		if (CollectionUtils.isNotEmpty(fiveOfAKindHandTypeList)) {
			Collections.sort(fiveOfAKindHandTypeList);
			highestRankYet = processMap(fiveOfAKindHandTypeList, handRanks, highestRankYet);
		}

		println(handRanks.keySet().stream().mapToLong(hand -> hand.bid * handRanks.get(hand)).sum());
	}

	private int processMap(final List<Hand> handList, final Map<Hand, Integer> handRanks, final int highestRankYet) {
		for (int i = 0; i < handList.size(); i++) {
			handRanks.put(handList.get(i), highestRankYet + i + 1);
		}
		return highestRankYet + handList.size();
	}

	/**
	 * @param handCharFrequencyMap
	 * @return
	 */
	private boolean isFiveOfAKind(final Map<Character, Long> handCharFrequencyMap) {
		return handCharFrequencyMap.size() == 1
				&& handCharFrequencyMap.containsValue(5L);
	}

	/**
	 * @param handCharFrequencyMap
	 * @return
	 */
	private boolean isFourOfAKind(final Map<Character, Long> handCharFrequencyMap) {
		return handCharFrequencyMap.size() == 2
				&& handCharFrequencyMap.containsValue(4L)
				&& handCharFrequencyMap.containsValue(1L);
	}

	/**
	 * @param handCharFrequencyMap
	 * @return
	 */
	private boolean isFullHouse(final Map<Character, Long> handCharFrequencyMap) {
		return handCharFrequencyMap.size() == 2
				&& handCharFrequencyMap.containsValue(3L)
				&& handCharFrequencyMap.containsValue(2L);
	}

	/**
	 * @param handCharFrequencyMap
	 * @return
	 */
	private boolean isThreeOfAKind(final Map<Character, Long> handCharFrequencyMap) {
		return handCharFrequencyMap.size() == 3
				&& handCharFrequencyMap.containsValue(3L)
				&& handCharFrequencyMap.containsValue(1L);
	}

	/**
	 * @param handCharFrequencyMap
	 * @return
	 */
	private boolean isTwoPair(final Map<Character, Long> handCharFrequencyMap) {
		return handCharFrequencyMap.size() == 3
				&& handCharFrequencyMap.containsValue(2L)
				&& handCharFrequencyMap.containsValue(1L);
	}

	/**
	 * @param handCharFrequencyMap
	 * @return
	 */
	private boolean isOnePair(final Map<Character, Long> handCharFrequencyMap) {
		return handCharFrequencyMap.size() == 4
				&& handCharFrequencyMap.containsValue(2L)
				&& handCharFrequencyMap.containsValue(1L);
	}

	/**
	 * @param handCharFrequencyMap
	 * @return
	 */
	private boolean isHighCard(final Map<Character, Long> handCharFrequencyMap) {
		return handCharFrequencyMap.size() == 5
				&& handCharFrequencyMap.containsValue(1L);
	}

	private static final class Hand implements Comparable<Hand> {
		private final String value;

		private final long bid;

		/**
		 * @param value
		 * @param bid
		 */
		public Hand(final String value, final long bid) {
			this.value = value;
			this.bid = bid;
		}



		@Override
		public int hashCode() {
			return Objects.hash(value);
		}



		@Override
		public boolean equals(final Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (getClass() != obj.getClass()) {
				return false;
			}
			final Hand other = (Hand) obj;
			return Objects.equals(value, other.value);
		}



		@Override
		public int compareTo(final Hand other) {
			int result = Integer.compare(
						 CARD_RANKS.get(String.valueOf(value.charAt(0))),
						 CARD_RANKS.get(String.valueOf(other.value.charAt(0))));
			if (result == 0) {
				result = Integer.compare(
						 CARD_RANKS.get(String.valueOf(value.charAt(1))),
						 CARD_RANKS.get(String.valueOf(other.value.charAt(1))));
			}
			if (result == 0) {
				result = Integer.compare(
						 CARD_RANKS.get(String.valueOf(value.charAt(2))),
						 CARD_RANKS.get(String.valueOf(other.value.charAt(2))));
			}
			if (result == 0) {
				result = Integer.compare(
						 CARD_RANKS.get(String.valueOf(value.charAt(3))),
						 CARD_RANKS.get(String.valueOf(other.value.charAt(3))));
			}
			if (result == 0) {
				result = Integer.compare(
						 CARD_RANKS.get(String.valueOf(value.charAt(4))),
						 CARD_RANKS.get(String.valueOf(other.value.charAt(4))));
			}
			return result;
		}

		public int partTwoCompare(final Hand other) {
			int result = Integer.compare(
						 CARD_RANKS_PART2.get(String.valueOf(value.charAt(0))),
						 CARD_RANKS_PART2.get(String.valueOf(other.value.charAt(0))));
			if (result == 0) {
				result = Integer.compare(
						CARD_RANKS_PART2.get(String.valueOf(value.charAt(1))),
						CARD_RANKS_PART2.get(String.valueOf(other.value.charAt(1))));
			}
			if (result == 0) {
				result = Integer.compare(
						CARD_RANKS_PART2.get(String.valueOf(value.charAt(2))),
						CARD_RANKS_PART2.get(String.valueOf(other.value.charAt(2))));
			}
			if (result == 0) {
				result = Integer.compare(
						CARD_RANKS_PART2.get(String.valueOf(value.charAt(3))),
						CARD_RANKS_PART2.get(String.valueOf(other.value.charAt(3))));
			}
			if (result == 0) {
				result = Integer.compare(
						CARD_RANKS_PART2.get(String.valueOf(value.charAt(4))),
						CARD_RANKS_PART2.get(String.valueOf(other.value.charAt(4))));
			}
			return result;
		}



		@Override
		public String toString() {
			return "Hand [value=" + value + ", bid=" + bid + "]";
		}


	}
}
