// SPDX-License-Identifier: Apache-2.0

package com.kylemayes.aoc2021.day6

import com.kylemayes.aoc2021.common.Input
import com.kylemayes.aoc2021.common.ResourceInput
import com.kylemayes.aoc2021.common.Solution
import com.kylemayes.aoc2021.common.cycle
import com.kylemayes.aoc2021.common.getMod
import com.kylemayes.aoc2021.common.readLines
import com.kylemayes.aoc2021.common.setMod
import com.kylemayes.aoc2021.common.solve

typealias ParsedInput = List<Long>

class Day6 : Solution<ParsedInput> {
    override fun parse(input: Input) = input
        .readLines()[0]
        .split(",")
        .map { it.toLong() }

    override fun solvePart1(input: ParsedInput) = solve(input, 80)

    override fun solvePart2(input: ParsedInput) = solve(input, 256)

    private fun solve(input: ParsedInput, target: Int): Long {
        val days = MutableList(18) { 0L }
        input.forEach { days[it.toInt()] += 1L }

        for (day in (0 until days.size).cycle().take(target)) {
            days.setMod(day + 7, days.getMod(day + 7) + days[day])
            days.setMod(day + 9, days.getMod(day + 9) + days[day])
            days[day] = 0
        }

        return days.sum()
    }
}

fun main() = solve({ Day6() }, ResourceInput("day6.txt"))
