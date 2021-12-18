// SPDX-License-Identifier: Apache-2.0

package com.kylemayes.aoc2021.day14

import com.kylemayes.aoc2021.common.Input
import com.kylemayes.aoc2021.common.ResourceInput
import com.kylemayes.aoc2021.common.Solution
import com.kylemayes.aoc2021.common.readGroups
import com.kylemayes.aoc2021.common.solve

data class ParsedInput(
    val template: List<Char>,
    val rules: Map<Pair<Char, Char>, Char>,
)

class Day14 : Solution<ParsedInput> {
    override fun parse(input: Input): ParsedInput {
        val groups = input.readGroups()
        return ParsedInput(
            template = groups[0][0].toList(),
            rules = groups[1].associate {
                val (pattern, middle) = it.split(" -> ")
                (pattern[0] to pattern[1]) to middle[0]
            }
        )
    }

    override fun solvePart1(input: ParsedInput) = solve(input, 10)

    override fun solvePart2(input: ParsedInput) = solve(input, 40)

    private fun solve(input: ParsedInput, steps: Int): Long {
        val counts = input
            .template
            .groupingBy { it }
            .eachCount()
            .mapValues { it.value.toLong() }
            .toMutableMap()

        val pairs = input
            .template
            .windowed(2)
            .map { it[0] to it[1] }
            .groupingBy { it }
            .eachCount()
            .mapValues { it.value.toLong() }
            .toMutableMap()

        for (step in 1..steps) {
            for ((pair, count) in pairs.toList()) {
                val middle = input.rules[pair]
                if (middle != null) {
                    counts.compute(middle) { _, v -> v?.let { it + count } ?: count }
                    pairs[pair] = pairs[pair]!! - count
                    for (add in listOf(pair.first to middle, middle to pair.second)) {
                        pairs.compute(add) { _, v -> v?.let { it + count } ?: count }
                    }
                }
            }
        }

        val entries = counts
            .entries
            .toList()
            .sortedBy { it.value }

        return entries.last().value - entries.first().value
    }
}

fun main() = solve({ Day14() }, ResourceInput("day14.txt"))
