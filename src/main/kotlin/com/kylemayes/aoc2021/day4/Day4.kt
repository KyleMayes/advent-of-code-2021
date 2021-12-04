package com.kylemayes.aoc2021.day4

import com.kylemayes.aoc2021.common.Input
import com.kylemayes.aoc2021.common.ResourceInput
import com.kylemayes.aoc2021.common.Solution
import com.kylemayes.aoc2021.common.geometry.Point
import com.kylemayes.aoc2021.common.geometry.Tile
import com.kylemayes.aoc2021.common.geometry.toTile
import com.kylemayes.aoc2021.common.readGroups
import com.kylemayes.aoc2021.common.solve

data class Board(
    val index: Int,
    val numbers: Map<Int, Point>,
    val marked: Tile<Boolean>,
) {
    constructor(index: Int, tile: Tile<Int>) : this(
        index,
        tile.entries().associate { e -> e.second to e.first },
        tile.map { false },
    )

    fun mark(number: Int) =
        numbers[number]?.let { marked[it] = true }

    fun complete(): Boolean =
        marked.rows().any { r -> r.all { it } } ||
            marked.columns().any { c -> c.all { it } }

    fun unmarked(): List<Int> = numbers
        .entries
        .filter { !marked[it.value] }
        .map { it.key }
}

data class ParsedInput(
    val numbers: List<Int>,
    val boards: List<Board>,
)

class Day4 : Solution<ParsedInput> {
    override fun parse(input: Input): ParsedInput {
        val groups = input.readGroups()

        val numbers = groups[0][0]
            .split(",")
            .map { it.toInt() }

        val boards = groups
            .subList(1, groups.size)
            .map { it.toTile { c -> c.toInt() } }
            .withIndex()
            .map { (i, t) -> Board(i, t) }

        return ParsedInput(numbers, boards)
    }

    override fun solvePart1(input: ParsedInput) =
        findBoard(input, 1)

    override fun solvePart2(input: ParsedInput) =
        findBoard(input, input.boards.size)

    private fun findBoard(input: ParsedInput, target: Int): Int {
        val complete = mutableSetOf<Int>()

        for (number in input.numbers) {
            for (board in input.boards) {
                if (complete.contains(board.index)) {
                    continue
                }

                board.mark(number)
                if (board.complete()) {
                    complete.add(board.index)
                    if (complete.size == target) {
                        return number * board.unmarked().sum()
                    }
                }
            }
        }

        throw Error("Unreachable.")
    }
}

fun main() = solve({ Day4() }, ResourceInput("day4.txt"))
