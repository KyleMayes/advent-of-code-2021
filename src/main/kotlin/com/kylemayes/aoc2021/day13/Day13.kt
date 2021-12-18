// SPDX-License-Identifier: Apache-2.0

package com.kylemayes.aoc2021.day13

import com.kylemayes.aoc2021.common.Input
import com.kylemayes.aoc2021.common.ResourceInput
import com.kylemayes.aoc2021.common.Solution
import com.kylemayes.aoc2021.common.geometry.Point
import com.kylemayes.aoc2021.common.geometry.Rectangle
import com.kylemayes.aoc2021.common.geometry.Tile
import com.kylemayes.aoc2021.common.geometry.toField
import com.kylemayes.aoc2021.common.geometry.toTile
import com.kylemayes.aoc2021.common.readGroups
import com.kylemayes.aoc2021.common.solve

data class ParsedInput(
    val sheet: Tile<Boolean>,
    val folds: List<Pair<String, Int>>,
)

class Day13 : Solution<ParsedInput> {
    override fun parse(input: Input): ParsedInput {
        val groups = input.readGroups()
        return ParsedInput(
            sheet = groups[0]
                .map {
                    val (x, y) = it.split(",")
                    Point(x.toInt(), y.toInt())
                }
                .associateWith { true }
                .toField()
                .toTile()!!
                .map { it ?: false },
            folds = groups[1]
                .map {
                    val match = Regex("fold along (\\w)=(\\d+)").matchEntire(it)!!
                    match.groupValues[1] to match.groupValues[2].toInt()
                },
        )
    }

    override fun solvePart1(input: ParsedInput) =
        fold(input.sheet, input.folds.take(1))
            .entries()
            .count { it.second }

    override fun solvePart2(input: ParsedInput) =
        fold(input.sheet, input.folds)
            .render { if (it) { '#' } else { '.' } }

    private fun fold(sheet: Tile<Boolean>, folds: List<Pair<String, Int>>): Tile<Boolean> {
        var folded = sheet

        for ((axis, n) in folds) {
            val (next, overlay) = if (axis == "x") {
                val middleBottomRight = Point(n - 1, folded.bounds.bottomRight.y)
                val left = folded.crop(Rectangle(folded.bounds.topLeft, middleBottomRight))
                val right = folded.crop(Rectangle(Point(n + 1, 0), folded.bounds.bottomRight))
                left to right.flipX()
            } else {
                val middleBottomRight = Point(folded.bounds.bottomRight.x, n - 1)
                val top = folded.crop(Rectangle(folded.bounds.topLeft, middleBottomRight))
                val bottom = folded.crop(Rectangle(Point(0, n + 1), folded.bounds.bottomRight))
                top to bottom.flipY()
            }

            for ((point, mark) in overlay.entries()) {
                next[point] = next[point] || mark
            }

            folded = next
        }

        return folded
    }
}

fun main() = solve({ Day13() }, ResourceInput("day13.txt"))
