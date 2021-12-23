// SPDX-License-Identifier: Apache-2.0

package com.kylemayes.aoc2021.day21

import com.kylemayes.aoc2021.common.Input
import com.kylemayes.aoc2021.common.ResourceInput
import com.kylemayes.aoc2021.common.Solution
import com.kylemayes.aoc2021.common.product
import com.kylemayes.aoc2021.common.readLines
import com.kylemayes.aoc2021.common.solve
import kotlin.math.max
import kotlin.math.min

val DIRAC = listOf(1L, 2L, 3L)
    .product(3)
    .groupingBy { it.sum() }
    .eachCount()

data class GameState(
    val p1Turn: Boolean,
    val p1Index: Long,
    val p1Score: Long,
    val p2Index: Long,
    val p2Score: Long,
)

typealias ParsedInput = Pair<Long, Long>

class Day21 : Solution<ParsedInput> {
    override fun parse(input: Input): ParsedInput {
        val lines = input.readLines()
        val p1 = lines[0].split(": ")[1].toLong()
        val p2 = lines[1].split(": ")[1].toLong()
        return p1 to p2
    }

    override fun solvePart1(input: ParsedInput): Long {
        var p1Score = 0L
        var p1Index = input.first - 1

        var p2Score = 0L
        var p2Index = input.second - 1

        var rolls = 0L
        var next = 0L
        val roll = { rolls += 1; (next++ % 100) + 1 }

        while (true) {
            val p1Roll = roll() + roll() + roll()
            p1Index = (p1Index + p1Roll) % 10
            p1Score += p1Index + 1
            if (p1Score >= 1000) {
                break
            }

            val p2Roll = roll() + roll() + roll()
            p2Index = (p2Index + p2Roll) % 10
            p2Score += p2Index + 1
            if (p2Score >= 1000) {
                break
            }
        }

        return rolls * min(p1Score, p2Score)
    }

    override fun solvePart2(input: ParsedInput): Long {
        val initial = GameState(true, input.first - 1, 0, input.second - 1, 0)
        val wins = mutableMapOf<GameState, Pair<Long, Long>>()
        val (p1, p2) = getWins(initial, wins)
        return max(p1, p2)
    }

    private fun getWins(
        state: GameState,
        wins: MutableMap<GameState, Pair<Long, Long>>,
    ): Pair<Long, Long> {
        if (wins.contains(state)) {
            return wins[state]!!
        }

        if (state.p1Score >= 21) {
            return 1L to 0L
        }

        if (state.p2Score >= 21) {
            return 0L to 1L
        }

        var p1Wins = 0L
        var p2Wins = 0L

        for ((roll, n) in DIRAC) {
            val (l, r) = if (state.p1Turn) {
                val p1Index = (state.p1Index + roll) % 10
                val p1Score = state.p1Score + p1Index + 1
                getWins(state.copy(p1Turn = false, p1Index = p1Index, p1Score = p1Score), wins)
            } else {
                val p2Index = (state.p2Index + roll) % 10
                val p2Score = state.p2Score + p2Index + 1
                getWins(state.copy(p1Turn = true, p2Index = p2Index, p2Score = p2Score), wins)
            }

            p1Wins += (l * n.toLong())
            p2Wins += (r * n.toLong())
        }

        wins[state] = p1Wins to p2Wins
        return p1Wins to p2Wins
    }
}

fun main() = solve({ Day21() }, ResourceInput("day21.txt"))
