package com.kylemayes.aoc2021

import com.kylemayes.aoc2021.common.ResourceInput
import com.kylemayes.aoc2021.common.Solution
import com.kylemayes.aoc2021.day1.Day1
import com.kylemayes.aoc2021.day10.Day10
import com.kylemayes.aoc2021.day11.Day11
import com.kylemayes.aoc2021.day12.Day12
import com.kylemayes.aoc2021.day13.Day13
import com.kylemayes.aoc2021.day14.Day14
import com.kylemayes.aoc2021.day15.Day15
import com.kylemayes.aoc2021.day16.Day16
import com.kylemayes.aoc2021.day17.Day17
import com.kylemayes.aoc2021.day18.Day18
import com.kylemayes.aoc2021.day19.Day19
import com.kylemayes.aoc2021.day2.Day2
import com.kylemayes.aoc2021.day20.Day20
import com.kylemayes.aoc2021.day21.Day21
import com.kylemayes.aoc2021.day3.Day3
import com.kylemayes.aoc2021.day4.Day4
import com.kylemayes.aoc2021.day5.Day5
import com.kylemayes.aoc2021.day6.Day6
import com.kylemayes.aoc2021.day7.Day7
import com.kylemayes.aoc2021.day8.Day8
import com.kylemayes.aoc2021.day9.Day9
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

private const val DAY13P2 =
    """
####..##...##..#..#.###..####.#..#.####.
#....#..#.#..#.#..#.#..#....#.#..#.#....
###..#..#.#....#..#.#..#...#..####.###..
#....####.#.##.#..#.###...#...#..#.#....
#....#..#.#..#.#..#.#.#..#....#..#.#....
#....#..#..###..##..#..#.####.#..#.####.
    """

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SolutionTest {
    private fun solutionsParameters(): Stream<Arguments> = Stream.of(
        Arguments.of("day1.txt", Day1(), 1688, 1728),
        Arguments.of("day2.txt", Day2(), 1936494L, 1997106066L),
        Arguments.of("day3.txt", Day3(), 741950, 903810),
        Arguments.of("day4.txt", Day4(), 39902, 26936),
        Arguments.of("day5.txt", Day5(), 7414, 19676),
        Arguments.of("day6.txt", Day6(), 360761L, 1632779838045L),
        Arguments.of("day7.txt", Day7(), 349812, 99763899),
        Arguments.of("day8.txt", Day8(), 421, 986163),
        Arguments.of("day9.txt", Day9(), 496, 902880),
        Arguments.of("day10.txt", Day10(), 345441L, 3235371166L),
        Arguments.of("day11.txt", Day11(), 1694, 346),
        Arguments.of("day12.txt", Day12(), 5756, 144603),
        Arguments.of("day13.txt", Day13(), 682, DAY13P2.trim()),
        Arguments.of("day14.txt", Day14(), 2874L, 5208377027195L),
        Arguments.of("day15.txt", Day15(), 745, 3002),
        Arguments.of("day16.txt", Day16(), 991L, 1264485568252L),
        Arguments.of("day17.txt", Day17(), 3655, 1447),
        Arguments.of("day18.txt", Day18(), 3816, 4819),
        Arguments.of("day19.txt", Day19(), 408, 13348),
        Arguments.of("day20.txt", Day20(), 5097, 17987),
        Arguments.of("day21.txt", Day21(), 900099L, 306719685234774L),
    )

    @ParameterizedTest
    @MethodSource("solutionsParameters")
    fun <I> solutions(resource: String, solution: Solution<I>, part1: Any, part2: Any) {
        val input = ResourceInput(resource)
        assertEquals(part1, solution.solvePart1(solution.parse(input)))
        assertEquals(part2, solution.solvePart2(solution.parse(input)))
    }
}
