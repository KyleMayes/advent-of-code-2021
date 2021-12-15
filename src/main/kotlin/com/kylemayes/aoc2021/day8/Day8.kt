// SPDX-License-Identifier: Apache-2.0

package com.kylemayes.aoc2021.day8

import com.kylemayes.aoc2021.common.Input
import com.kylemayes.aoc2021.common.ResourceInput
import com.kylemayes.aoc2021.common.Solution
import com.kylemayes.aoc2021.common.readLines
import com.kylemayes.aoc2021.common.solve

private val unique = setOf(2, 3, 4, 7)

private val digits = mapOf(
    setOf('a', 'b', 'c', 'e', 'f', 'g') to 0,
    setOf('c', 'f') to 1,
    setOf('a', 'c', 'd', 'e', 'g') to 2,
    setOf('a', 'c', 'd', 'f', 'g') to 3,
    setOf('b', 'c', 'd', 'f') to 4,
    setOf('a', 'b', 'd', 'f', 'g') to 5,
    setOf('a', 'b', 'd', 'e', 'f', 'g') to 6,
    setOf('a', 'c', 'f') to 7,
    setOf('a', 'b', 'c', 'd', 'e', 'f', 'g') to 8,
    setOf('a', 'b', 'c', 'd', 'f', 'g') to 9,
)

typealias ParsedInput = List<Pair<List<Set<Char>>, List<Set<Char>>>>

class Day8 : Solution<ParsedInput> {
    override fun parse(input: Input) = input
        .readLines()
        .map {
            val (left, right) = it.split(" | ")
            val signals = left.split(" ").map { s -> s.toCharArray().toSet() }
            val digits = right.split(" ").map { s -> s.toCharArray().toSet() }
            signals to digits
        }

    override fun solvePart1(input: ParsedInput) = input
        .sumOf { it.second.count { d -> unique.contains(d.size) } }

    override fun solvePart2(input: ParsedInput) = input
        .sumOf {
            val mappings = getCharMappings(it.first)

            var value = 0
            for (display in it.second) {
                val mapped = display.map { c -> mappings[c] }.toSet()
                val digit = digits[mapped]!!
                value = (value * 10) + digit
            }

            value
        }

    private fun getCharMappings(signals: List<Set<Char>>): Map<Char, Char> {
        val allCounts = getCharCounts(signals)
        val uniqueCounts = getCharCounts(signals.filter { unique.contains(it.size) })

        val getCharMapping = { allOccurrences: Int, uniqueOccurrences: Int ->
            val allOptions = getCharOptions(allCounts, allOccurrences)
            val uniqueOptions = getCharOptions(uniqueCounts, uniqueOccurrences)
            allOptions.intersect(uniqueOptions).first()
        }

        return mapOf(
            getCharMapping(8, 2) to 'a',
            getCharMapping(6, 2) to 'b',
            getCharMapping(8, 4) to 'c',
            getCharMapping(7, 2) to 'd',
            getCharMapping(4, 1) to 'e',
            getCharMapping(9, 4) to 'f',
            getCharMapping(7, 1) to 'g',
        )
    }

    private fun getCharCounts(entries: List<Set<Char>>): Map<Char, Int> = entries
        .flatten()
        .groupingBy { it }
        .eachCount()

    private fun getCharOptions(counts: Map<Char, Int>, occurrences: Int): Set<Char> = counts
        .filter { it.value == occurrences }
        .map { it.key }
        .toSet()
}

fun main() = solve({ Day8() }, ResourceInput("day8.txt"))
