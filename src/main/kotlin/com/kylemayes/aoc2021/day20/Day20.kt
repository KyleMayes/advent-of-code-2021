// SPDX-License-Identifier: Apache-2.0

package com.kylemayes.aoc2021.day20

import com.kylemayes.aoc2021.common.Input
import com.kylemayes.aoc2021.common.ResourceInput
import com.kylemayes.aoc2021.common.Solution
import com.kylemayes.aoc2021.common.geometry.Point
import com.kylemayes.aoc2021.common.geometry.Tile
import com.kylemayes.aoc2021.common.geometry.toTile
import com.kylemayes.aoc2021.common.readGroups
import com.kylemayes.aoc2021.common.solve

fun Point.square(): List<Point> = listOf(
    this + Point(-1, -1),
    this + Point(0, -1),
    this + Point(1, -1),
    this + Point(-1, 0),
    this,
    this + Point(1, 0),
    this + Point(-1, 1),
    this + Point(0, 1),
    this + Point(1, 1),
)

typealias ParsedInput = Pair<List<Char>, Tile<Char>>

class Day20 : Solution<ParsedInput> {
    override fun parse(input: Input): ParsedInput {
        val groups = input.readGroups()

        val program = groups[0][0].toList()

        val image = groups[1].toTile()
        val padding = 100
        val dimension = image.bounds.width + padding
        val padded = Tile(dimension, dimension) { '.' }
        padded.blit(Point(padding / 2, padding / 2), image)

        return program to padded
    }

    override fun solvePart1(input: ParsedInput) =
        enhance(input.first, input.second, 2)
            .entries()
            .count { it.second == '#' }

    override fun solvePart2(input: ParsedInput) =
        enhance(input.first, input.second, 50)
            .entries()
            .count { it.second == '#' }

    private fun enhance(program: List<Char>, image: Tile<Char>, times: Int): Tile<Char> {
        var enhanced = image

        for (i in 1..times) {
            val next = enhanced.clone()
            val default = if (i % 2 == 0) { program[0] } else { '.' }

            for (point in next.bounds.points()) {
                val index = point
                    .square()
                    .map { enhanced.getOrDefault(it, default) }
                    .map { if (it == '#') { 1 } else { 0 } }
                    .reduce { acc, d -> (2 * acc) + d }
                next[point] = program[index]
            }

            enhanced = next
        }

        return enhanced
    }
}

fun main() = solve({ Day20() }, ResourceInput("day20.txt"))
