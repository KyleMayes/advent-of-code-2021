// SPDX-License-Identifier: Apache-2.0

package com.kylemayes.aoc2021.day2

import com.kylemayes.aoc2021.common.Input
import com.kylemayes.aoc2021.common.ResourceInput
import com.kylemayes.aoc2021.common.Solution
import com.kylemayes.aoc2021.common.readLines
import com.kylemayes.aoc2021.common.solve

enum class Direction {
    FORWARD,
    DOWN,
    UP,
}

typealias ParsedInput = List<Pair<Direction, Int>>

class Day2 : Solution<ParsedInput> {
    override fun parse(input: Input) = input
        .readLines()
        .map { it.split(" ") }
        .map { Direction.valueOf(it[0].uppercase()) to it[1].toInt() }

    override fun solvePart1(input: ParsedInput): Long {
        var position = 0L
        var depth = 0L

        for ((direction, amount) in input) {
            when (direction) {
                Direction.FORWARD -> position += amount
                Direction.DOWN -> depth += amount
                Direction.UP -> depth -= amount
            }
        }

        return position * depth
    }

    override fun solvePart2(input: ParsedInput): Long {
        var aim = 0L
        var position = 0L
        var depth = 0L

        for ((direction, amount) in input) {
            when (direction) {
                Direction.FORWARD -> {
                    position += amount
                    depth += (aim * amount)
                }
                Direction.DOWN -> aim += amount
                Direction.UP -> aim -= amount
            }
        }

        return position * depth
    }
}

fun main() = solve({ Day2() }, ResourceInput("day2.txt"))
