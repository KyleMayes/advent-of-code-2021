// SPDX-License-Identifier: Apache-2.0

package com.kylemayes.aoc2021.day12

import com.kylemayes.aoc2021.common.Input
import com.kylemayes.aoc2021.common.ResourceInput
import com.kylemayes.aoc2021.common.Solution
import com.kylemayes.aoc2021.common.readLines
import com.kylemayes.aoc2021.common.solve

typealias ParsedInput = Map<String, Set<String>>

class Day12 : Solution<ParsedInput> {
    override fun parse(input: Input): ParsedInput {
        val paths = input
            .readLines()
            .map { it.split("-") }
            .groupBy({ it[0] }) { it[1] }
            .mapValues { it.value.toMutableSet() }
            .toMutableMap()

        for ((cave, neighbors) in HashMap(paths)) {
            for (neighbor in neighbors) {
                paths.computeIfAbsent(neighbor) { mutableSetOf() }.add(cave)
            }
        }

        return paths
    }

    override fun solvePart1(input: ParsedInput) =
        visit(input, "start", mutableMapOf(), false)

    override fun solvePart2(input: ParsedInput) =
        visit(input, "start", mutableMapOf(), true)

    private fun visit(
        connections: Map<String, Set<String>>,
        cave: String,
        visited: MutableMap<String, Int>,
        double: Boolean,
        doubled: Boolean = false,
    ): Int {
        if (cave == "end") {
            return 1
        }

        var paths = 0
        visited.compute(cave) { _, v -> v?.let { it + 1 } ?: 1 }

        for (neighbor in connections.getOrDefault(cave, setOf())) {
            val (visitable, newDoubled) = check(visited, double, doubled, neighbor)
            if (visitable) {
                paths += visit(connections, neighbor, visited, double, newDoubled)
            }
        }

        visited[cave] = visited[cave]!! - 1
        return paths
    }

    private fun check(
        visited: MutableMap<String, Int>,
        double: Boolean,
        doubled: Boolean,
        neighbor: String,
    ): Pair<Boolean, Boolean> {
        if (neighbor == "start") {
            return false to doubled
        }

        if (neighbor[0].isUpperCase()) {
            return true to doubled
        }

        val visits = visited.getOrDefault(neighbor, 0)
        return if (visits < 1) {
            true to doubled
        } else if (double && !doubled && visits < 2) {
            true to true
        } else {
            false to doubled
        }
    }
}

fun main() = solve({ Day12() }, ResourceInput("day12.txt"))
