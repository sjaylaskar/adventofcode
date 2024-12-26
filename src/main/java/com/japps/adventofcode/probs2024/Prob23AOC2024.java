/*
 * Copyright (©) 2024 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.japps.adventofcode.probs2024;

import static org.apache.commons.collections4.CollectionUtils.*;
import static com.japps.adventofcode.util.ProblemSolverUtil.*;
import static com.japps.adventofcode.util.StringUtil.*;

import java.io.*;
import java.util.*;
import java.util.function.*;
import java.util.stream.*;

import org.apache.commons.lang3.*;

import com.japps.adventofcode.util.*;

public final class Prob23AOC2024 extends AbstractSolvable implements Loggable {

    private static final Prob23AOC2024 INSTANCE = instance();

    private Prob23AOC2024() {

    }

    private static Prob23AOC2024 instance() {

        return new Prob23AOC2024();
    }

    public static void main(final String[] args) {

        try {
            INSTANCE.compute();
        } catch (final IOException exception) {
            INSTANCE.error(exception.getLocalizedMessage());
        }
    }

    private static final String PREFIX_T = "t";
    private static final String HYPHEN = "-";

    private void compute() throws IOException {
		List<String> lines = lines();
        Map<String, Set<String>> deviceConnections = new HashMap<>();
        lines.stream().map(line -> line.split(HYPHEN)).forEach(deviceNames -> {
            deviceConnections.computeIfAbsent(deviceNames[0], _ -> new HashSet<>()).add(deviceNames[1]);
            deviceConnections.computeIfAbsent(deviceNames[1], _ -> new HashSet<>()).add(deviceNames[0]);
        });
        List<String> devices = List.copyOf(deviceConnections.keySet());
        println(computePrefixStartingDeviceTriplet(devices, deviceConnections));
        println(largestConnection(devices, deviceConnections));
    }

    private static long computePrefixStartingDeviceTriplet(List<String> devices, Map<String, Set<String>> deviceConnections) {
        return IntStream.range(0, devices.size()).boxed().flatMap(position1 -> IntStream.range(position1 + 1, devices.size()).boxed().flatMap(position2 -> IntStream.range(position2 + 1, devices.size()).mapToObj(position3 -> List.of(position1, position2, position3)))).filter(validTripletConnectionPredicate(devices, deviceConnections)).count();
    }

    private static Predicate<List<Integer>> validTripletConnectionPredicate(List<String> devices, Map<String, Set<String>> deviceConnections) {
        return devicePositions -> connectionContains(deviceConnections, devices, devicePositions, 0, List.of(1, 2)) && connectionContains(deviceConnections, devices, devicePositions, 1, List.of(0, 2)) && connectionContains(deviceConnections, devices, devicePositions, 2, List.of(0, 1)) && startsWithPrefix(devicePositions, devices);
    }

    private static boolean connectionContains(Map<String, Set<String>> deviceConnections, List<String> devices, List<Integer> devicePositions, int containerPosition, List<Integer> containedPositions) {
        return deviceConnections.get(devices.get(devicePositions.get(containerPosition))).contains(devices.get(devicePositions.get(containedPositions.get(0))))
                && deviceConnections.get(devices.get(devicePositions.get(containerPosition))).contains(devices.get(devicePositions.get(containedPositions.get(1))));
    }

    private static boolean startsWithPrefix(List<Integer> devicePositions, List<String> devices) {
        return startsWithPrefix(devicePositions, 0, devices)
                || startsWithPrefix(devicePositions, 1, devices)
                || startsWithPrefix(devicePositions, 2, devices);
    }

    private static boolean startsWithPrefix(List<Integer> devicePositions, int searchPosition, List<String> devices) {
        return devices.get(devicePositions.get(searchPosition)).startsWith(PREFIX_T);
    }

    private static String largestConnection(List<String> devices, Map<String, Set<String>> deviceConnections) {
        List<List<String>> connectedDeviceLinks = devices.stream().map(device -> new ArrayList<>(List.of(device))).collect(Collectors.toList());
        while (isNotEmpty(connectedDeviceLinks)) {
            List<List<String>> validConnectedDeviceLinks = validConnectedDevicesLinks(devices, deviceConnections, connectedDeviceLinks);
            if (isEmpty(validConnectedDeviceLinks)) {
                return sortedDelimited(connectedDeviceLinks.getFirst(), COMMA);
            }
            connectedDeviceLinks = validConnectedDeviceLinks;
        }
        return StringUtils.EMPTY;
    }

    private static List<List<String>> validConnectedDevicesLinks(List<String> devices, Map<String, Set<String>> deviceConnections, List<List<String>> connectedDeviceLinks) {
        Set<String> sortedCommaDelimitedConnectedDevices = new HashSet<>();
        return connectedDeviceLinks.stream()
                .flatMap(connectedDevices -> devices.stream().filter(device -> deviceConnections.get(device).containsAll(connectedDevices))
                        .map(device -> validConnectedDeviceLink(connectedDevices, device))
                        .filter(validConnectedDeviceLink -> sortedCommaDelimitedConnectedDevices.add(sortedDelimited(validConnectedDeviceLink, COMMA))))
                .toList();
    }

    private static List<String> validConnectedDeviceLink(List<String> connectedDevices, String device) {
        List<String> validConnectedDeviceLink = new ArrayList<>(connectedDevices);
        validConnectedDeviceLink.add(device);
        return validConnectedDeviceLink;
    }
}
