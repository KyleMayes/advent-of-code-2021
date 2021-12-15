// SPDX-License-Identifier: Apache-2.0

package com.kylemayes.aoc2021.day9

import com.kylemayes.aoc2021.common.Input
import com.kylemayes.aoc2021.common.ResourceInput
import com.kylemayes.aoc2021.common.Solution
import com.kylemayes.aoc2021.common.geometry.Point
import com.kylemayes.aoc2021.common.geometry.Tile
import com.kylemayes.aoc2021.common.geometry.toTile
import com.kylemayes.aoc2021.common.readLines
import com.kylemayes.aoc2021.common.solve
import java.util.Comparator

typealias ParsedInput = Tile<Int>

class Day9 : Solution<ParsedInput> {
    override fun parse(input: Input) = input
        .readLines()
        .toTile()
        .map { it.toString().toInt() }

    override fun solvePart1(input: ParsedInput) = input
        .entries()
        .filter { (p, h) -> input.neighbors(p, false).all { (_, nh) -> nh > h } }
        .sumOf { 1 + it.second }

    override fun solvePart2(input: ParsedInput): Int {
        val visited = mutableSetOf<Point>()
        val basins = mutableListOf<Int>()

        for ((point, height) in input.entries()) {
            if (height != 9 && !visited.contains(point)) {
                val start = visited.size
                visit(input, point, visited)
                basins.add(visited.size - start)
            }
        }

        basins.sortWith(Comparator.reverseOrder())
        println(basins.take(3))
        return basins.take(3).reduce { a, e -> a * e }
    }

    private fun visit(input: ParsedInput, point: Point, visited: MutableSet<Point>) {
        visited.add(point)
        for ((neighbor, height) in input.neighbors(point, false)) {
            if (height != 9 && !visited.contains(neighbor)) {
                visit(input, neighbor, visited)
            }
        }
    }
}

fun main() = solve({ Day9() }, ResourceInput("day9.txt"))
