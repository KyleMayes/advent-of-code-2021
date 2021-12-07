// SPDX-License-Identifier: Apache-2.0

package com.kylemayes.aoc2021.day7

import com.kylemayes.aoc2021.common.Input
import com.kylemayes.aoc2021.common.ResourceInput
import com.kylemayes.aoc2021.common.Solution
import com.kylemayes.aoc2021.common.mean
import com.kylemayes.aoc2021.common.median
import com.kylemayes.aoc2021.common.readLines
import com.kylemayes.aoc2021.common.solve
import kotlin.math.absoluteValue

typealias ParsedInput = List<Int>

class Day7 : Solution<ParsedInput> {
    override fun parse(input: Input) = input
        .readLines()[0]
        .split(",")
        .map { it.toInt() }
        .sorted()

    override fun solvePart1(input: ParsedInput) =
        solve(input, input.median().toInt()) { it }

    override fun solvePart2(input: ParsedInput) =
        solve(input, input.mean().toInt()) { (it * (it + 1)) / 2 }

    private fun solve(input: ParsedInput, target: Int, consumption: (Int) -> Int) =
        input.sumOf { consumption((it - target).absoluteValue) }
}

fun main() = solve({ Day7() }, ResourceInput("day7.txt"))
