// SPDX-License-Identifier: Apache-2.0

package com.kylemayes.aoc2021.day15

import com.kylemayes.aoc2021.common.Input
import com.kylemayes.aoc2021.common.ResourceInput
import com.kylemayes.aoc2021.common.Solution
import com.kylemayes.aoc2021.common.findPath
import com.kylemayes.aoc2021.common.geometry.Point
import com.kylemayes.aoc2021.common.geometry.Tile
import com.kylemayes.aoc2021.common.geometry.toTile
import com.kylemayes.aoc2021.common.readLines
import com.kylemayes.aoc2021.common.solve

typealias ParsedInput = Tile<Int>

class Day15 : Solution<ParsedInput> {
    override fun parse(input: Input) = input
        .readLines()
        .toTile()
        .map { it.toString().toInt() }

    override fun solvePart1(input: ParsedInput) =
        solve(input)

    override fun solvePart2(input: ParsedInput): Int {
        var map = input

        var template = map.clone()
        for (x in 1..4) {
            map = map.mergeX(template.map { wrap(it + x) })
        }

        template = map.clone()
        for (y in 1..4) {
            map = map.mergeY(template.map { wrap(it + y) })
        }

        return solve(map)
    }

    private fun solve(map: Tile<Int>): Int {
        val path = findPath(map, Point(0, 0), map.bounds.bottomRight) { _, (_, r) -> r }
        return path!!.sumOf { map[it] } - map[Point(0, 0)]
    }

    private fun wrap(risk: Int) = if (risk >= 10) { risk - 9 } else { risk }
}

fun main() = solve({ Day15() }, ResourceInput("day15.txt"))
