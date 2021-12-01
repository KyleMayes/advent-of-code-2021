// SPDX-License-Identifier: Apache-2.0

package com.kylemayes.aoc2021.day1

import com.kylemayes.aoc2021.common.Input
import com.kylemayes.aoc2021.common.ResourceInput
import com.kylemayes.aoc2021.common.Solution
import com.kylemayes.aoc2021.common.readLines
import com.kylemayes.aoc2021.common.solve

typealias ParsedInput = List<Int>

class Day1 : Solution<ParsedInput> {
    override fun parse(input: Input) = input
        .readLines()
        .map { it.toInt() }

    override fun solvePart1(input: ParsedInput) = input
        .windowed(2)
        .count { it[1] > it[0] }

    override fun solvePart2(input: ParsedInput) = input
        .windowed(3)
        .map { it.sum() }
        .windowed(2)
        .count { it[1] > it[0] }
}

fun main() = solve({ Day1() }, ResourceInput("day1.txt"))
