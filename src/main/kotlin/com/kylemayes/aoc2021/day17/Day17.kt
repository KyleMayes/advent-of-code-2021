// SPDX-License-Identifier: Apache-2.0

package com.kylemayes.aoc2021.day17

import com.kylemayes.aoc2021.common.Input
import com.kylemayes.aoc2021.common.ResourceInput
import com.kylemayes.aoc2021.common.Solution
import com.kylemayes.aoc2021.common.geometry.Point
import com.kylemayes.aoc2021.common.geometry.Rectangle
import com.kylemayes.aoc2021.common.product
import com.kylemayes.aoc2021.common.readLines
import com.kylemayes.aoc2021.common.solve
import kotlin.math.abs
import kotlin.math.max

typealias ParsedInput = Rectangle

class Day17 : Solution<ParsedInput> {
    override fun parse(input: Input): ParsedInput {
        val line = input.readLines()[0].drop(13)
        val match = Regex("x=(-?\\d+)..(-?\\d+), y=(-?\\d+)..(-?\\d+)").matchEntire(line)!!
        val coordinates = match.groupValues.drop(1).map { it.trim().toInt() }
        val topLeft = Point(coordinates[0], coordinates[2])
        val bottomRight = Point(coordinates[1], coordinates[3])
        return Rectangle(topLeft, bottomRight)
    }

    override fun solvePart1(input: ParsedInput): Int {
        val minX = (1..Int.MAX_VALUE).first { getEulerSum(it) >= input.topLeft.x }
        val maxX = (minX..Int.MAX_VALUE).first { getEulerSum(it + 1) > input.bottomRight.x }
        return listOf(minX..maxX, 1..2 * abs(input.bottomRight.y))
            .product()
            .map { Point(it[0], it[1]) }
            .filter { getIntersection(it, input) }
            .maxOf { getEulerSum(it.y) }
    }

    override fun solvePart2(input: ParsedInput): Int =
        listOf(1..input.bottomRight.x, -100..100)
            .product()
            .count { getIntersection(Point(it[0], it[1]), input) }

    private fun getEulerSum(n: Int): Int =
        (n * (n + 1)) / 2

    private fun getIntersection(velocity: Point, target: Rectangle): Boolean =
        getTrajectory(velocity)
            .takeWhile { it.x <= target.bottomRight.x && it.y >= 2 * target.bottomRight.y }
            .any { target.contains(it) }

    private fun getTrajectory(velocity: Point): Sequence<Point> {
        var point = Point(0, 0)
        var vx = velocity.x
        var vy = velocity.y
        return generateSequence {
            point = Point(point.x + vx, point.y + vy)
            vx = max(0, vx - 1)
            vy -= 1
            point
        }
    }
}
fun main() = solve({ Day17() }, ResourceInput("day17.txt"))
