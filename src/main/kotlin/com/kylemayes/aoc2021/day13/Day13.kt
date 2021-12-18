// SPDX-License-Identifier: Apache-2.0

package com.kylemayes.aoc2021.day13

import com.kylemayes.aoc2021.common.Input
import com.kylemayes.aoc2021.common.ResourceInput
import com.kylemayes.aoc2021.common.Solution
import com.kylemayes.aoc2021.common.readLines
import com.kylemayes.aoc2021.common.solve

typealias ParsedInput = List<String>

class Day13 : Solution<ParsedInput> {
    override fun parse(input: Input) = input
        .readLines()

    override fun solvePart1(input: ParsedInput) = 0

    override fun solvePart2(input: ParsedInput) = 0
}

fun main() = solve({ Day13() }, ResourceInput("day13.txt"))
