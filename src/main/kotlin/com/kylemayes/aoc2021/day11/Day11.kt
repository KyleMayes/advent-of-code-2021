// SPDX-License-Identifier: Apache-2.0

package com.kylemayes.aoc2021.day11

import com.kylemayes.aoc2021.common.Input
import com.kylemayes.aoc2021.common.ResourceInput
import com.kylemayes.aoc2021.common.Solution
import com.kylemayes.aoc2021.common.geometry.Tile
import com.kylemayes.aoc2021.common.geometry.toTile
import com.kylemayes.aoc2021.common.readLines
import com.kylemayes.aoc2021.common.solve

typealias ParsedInput = Tile<Long>

class Day11 : Solution<ParsedInput> {
    override fun parse(input: Input) = input
        .readLines()
        .toTile()
        .map { it.toString().toLong() }

    override fun solvePart1(input: ParsedInput) =
        (1..100).sumOf { step(input) }

    override fun solvePart2(input: ParsedInput) =
        (1..Int.MAX_VALUE).first { step(input) == input.bounds.area }

    private fun step(input: ParsedInput): Int {
        for ((octopus, _) in input.entries()) {
            input[octopus] = input[octopus] + 1
        }

        val flashing = input
            .entries()
            .filter { it.second > 9L }
            .map { it.first }
            .toMutableList()

        val flashed = flashing
            .toMutableSet()

        while (flashing.isNotEmpty()) {
            val clone = ArrayList(flashing)
            flashing.clear()
            for (octopus in clone) {
                for (neighbor in input.neighbors(octopus)) {
                    input[neighbor.first] = input[neighbor.first] + 1
                    if (!flashed.contains(neighbor.first) && neighbor.second >= 9) {
                        flashing.add(neighbor.first)
                        flashed.add(neighbor.first)
                    }
                }
            }
        }

        for (octopus in flashed) {
            input[octopus] = 0
        }

        return flashed.size
    }
}

fun main() = solve({ Day11() }, ResourceInput("day11.txt"))
