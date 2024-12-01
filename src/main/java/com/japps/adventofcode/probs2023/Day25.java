/*
 * Id: Day25.java 26-Dec-2023 SubhajoyLaskar
 * Copyright (©) 2023 Subhajoy Laskar
 * https://www.linkedin.com/in/subhajoylaskar
 */
package com.japps.adventofcode.probs2023;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jgrapht.alg.StoerWagnerMinimumCut;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultUndirectedGraph;

@SuppressWarnings("unused")
public class Day25 implements DayInterface {


    @Override
    public Map<String, String> part1Tests() {
        return Map.of();
    }

    @Override
    public Map<String, String> part2Tests() {
        return Map.of();
    }

    //@Override
    public String part1Solution(final List<String> lines) {
        // final var lines = input.trim().split("\n");
        final var graph = new DefaultUndirectedGraph<String, DefaultEdge>(DefaultEdge.class);

        for (final var line: lines) {
            Regex.matchAll("\\w+", line).stream().filter(s -> !graph.containsVertex(s)).forEach(graph::addVertex);
        }

        for (final var line: lines) {
            final var connections = new LinkedList<>(Regex.matchAll("\\w+", line));
            final String name = connections.removeFirst();

            connections.stream().filter(s -> !graph.containsEdge(name, s)).forEach(s -> graph.addEdge(name, s));
        }

        final var stoerWagnerMinimumCut = new StoerWagnerMinimumCut<>(graph);
        final var minimumCutSize = stoerWagnerMinimumCut.minCut().size();

        return String.valueOf(minimumCutSize * (graph.vertexSet().size() - minimumCutSize));
    }

    @Override
    public String part2Solution(final String input) {
        return "Merry Christmas!";
    }

	@Override
	public String part1Solution(final String input) {
		// TODO Auto-generated method stub
		return null;
	}
}