// SPDX-License-Identifier: Apache-2.0

package com.kylemayes.aoc2021.day10

import com.kylemayes.aoc2021.common.Input
import com.kylemayes.aoc2021.common.ResourceInput
import com.kylemayes.aoc2021.common.Solution
import com.kylemayes.aoc2021.common.readLines
import com.kylemayes.aoc2021.common.solve

typealias ParsedInput = List<String>

val P1S = mapOf(')' to 3L, ']' to 57L, '}' to 1197L, '>' to 25137L)
val P2S = mapOf(')' to 1L, ']' to 2L, '}' to 3L, '>' to 4L)

class Day10 : Solution<ParsedInput> {
    override fun parse(input: Input) = input
        .readLines()

    override fun solvePart1(input: ParsedInput) = input
        .mapNotNull { process(it).first }
        .sumOf { P1S[it]!! }

    override fun solvePart2(input: ParsedInput): Long {
        val scores = mutableListOf<Long>()

        outer@ for (line in input) {
            val (different, stack) = process(line)
            if (different == null) {
                scores.add(
                    stack
                        .map { P2S[it]!! }
                        .reduce { acc, i -> (acc * 5L) + i }
                )
            }
        }

        scores.sort()
        return scores[scores.size / 2]
    }

    private fun process(line: String): Pair<Char?, List<Char>> {
        val stack = ArrayDeque<Char>()

        for (actual in line) {
            val expected = when (actual) {
                '(' -> { stack.addFirst(')'); continue }
                '{' -> { stack.addFirst('}'); continue }
                '[' -> { stack.addFirst(']'); continue }
                '<' -> { stack.addFirst('>'); continue }
                else -> stack.removeFirst()
            }

            if (expected != actual) {
                return actual to stack
            }
        }

        return null to stack
    }
}

fun main() = solve({ Day10() }, ResourceInput("day10.txt"))
