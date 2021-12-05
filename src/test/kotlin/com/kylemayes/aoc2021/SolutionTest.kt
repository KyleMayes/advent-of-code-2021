package com.kylemayes.aoc2021

import com.kylemayes.aoc2021.common.ResourceInput
import com.kylemayes.aoc2021.common.Solution
import com.kylemayes.aoc2021.day1.Day1
import com.kylemayes.aoc2021.day2.Day2
import com.kylemayes.aoc2021.day3.Day3
import com.kylemayes.aoc2021.day4.Day4
import com.kylemayes.aoc2021.day5.Day5
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SolutionTest {
    private fun solutionsParameters(): Stream<Arguments> = Stream.of(
        Arguments.of("day1.txt", Day1(), 1688, 1728),
        Arguments.of("day2.txt", Day2(), 1936494L, 1997106066L),
        Arguments.of("day3.txt", Day3(), 741950, 903810),
        Arguments.of("day4.txt", Day4(), 39902, 26936),
        Arguments.of("day5.txt", Day5(), 7414, 19676)
    )

    @ParameterizedTest
    @MethodSource("solutionsParameters")
    fun <I> solutions(resource: String, solution: Solution<I>, part1: Any, part2: Any) {
        val input = ResourceInput(resource)
        assertEquals(part1, solution.solvePart1(solution.parse(input)))
        assertEquals(part2, solution.solvePart2(solution.parse(input)))
    }
}
