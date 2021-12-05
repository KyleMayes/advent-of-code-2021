// SPDX-License-Identifier: Apache-2.0

package com.kylemayes.aoc2021.day5

import com.kylemayes.aoc2021.common.Input
import com.kylemayes.aoc2021.common.ResourceInput
import com.kylemayes.aoc2021.common.Solution
import com.kylemayes.aoc2021.common.geometry.Field
import com.kylemayes.aoc2021.common.geometry.Line
import com.kylemayes.aoc2021.common.geometry.Point
import com.kylemayes.aoc2021.common.readLines
import com.kylemayes.aoc2021.common.solve

typealias ParsedInput = List<Line>

class Day5 : Solution<ParsedInput> {
    override fun parse(input: Input) = input
        .readLines()
        .map {
            val points = it.split(" -> ")
            val (ax, ay) = points[0].split(",").map { c -> c.toInt() }
            val (bx, by) = points[1].split(",").map { c -> c.toInt() }
            Line(Point(ax, ay), Point(bx, by))
        }

    override fun solvePart1(input: ParsedInput) = solve(input, false)

    override fun solvePart2(input: ParsedInput) = solve(input, true)

    private fun solve(input: ParsedInput, diagonal: Boolean): Int {
        val field = Field<Int>()

        for (line in input.filter { diagonal || (it.horizontal || it.vertical) }) {
            for (point in line.points()) {
                field.compute(point) { _, v -> v?.let { it + 1 } ?: 1 }
            }
        }

        return field.values.count { it > 1 }
    }
}

fun main() = solve({ Day5() }, ResourceInput("day5.txt"))
