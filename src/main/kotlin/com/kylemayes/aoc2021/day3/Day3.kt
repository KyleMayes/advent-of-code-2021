// SPDX-License-Identifier: Apache-2.0

package com.kylemayes.aoc2021.day3

import com.kylemayes.aoc2021.common.Input
import com.kylemayes.aoc2021.common.ResourceInput
import com.kylemayes.aoc2021.common.Solution
import com.kylemayes.aoc2021.common.readLines
import com.kylemayes.aoc2021.common.solve

typealias ParsedInput = List<String>

class Day3 : Solution<ParsedInput> {
    override fun parse(input: Input) = input
        .readLines()

    override fun solvePart1(input: ParsedInput): Int {
        var gamma = 0
        var epsilon = 0

        for (i in 0 until input[0].length) {
            gamma *= 2
            epsilon *= 2
            if (input.count { it[i] == '0' } > input.size / 2) {
                gamma += 1
            } else {
                epsilon += 1
            }
        }

        return gamma * epsilon
    }

    override fun solvePart2(input: ParsedInput): Int {
        val o2 = getRating(input.toMutableList(), '0', '1')
        val co2 = getRating(input.toMutableList(), '1', '0')
        return o2 * co2
    }

    private fun getRating(numbers: MutableList<String>, top: Char, bottom: Char): Int {
        var index = 0

        while (numbers.size > 1) {
            val zeros = numbers.count { it[index] == '0' }
            val ones = numbers.size - zeros

            val target = if (zeros > ones) { top } else { bottom }
            numbers.removeIf { it[index] == target }

            index += 1
        }

        return numbers[0].toInt(2)
    }
}

fun main() = solve({ Day3() }, ResourceInput("day3.txt"))
