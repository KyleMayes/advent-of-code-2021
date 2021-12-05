package com.kylemayes.aoc2021.common.geometry

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class LineTest {
    @Test
    fun points() {
        val get = { a: Point, b: Point -> Line(a, b).points() }

        assertEquals(
            listOf(Point(0, 0), Point(1, 0), Point(2, 0)),
            get(Point(0, 0), Point(2, 0)),
        )

        assertEquals(
            listOf(Point(0, 0), Point(-1, 0), Point(-2, 0)),
            get(Point(0, 0), Point(-2, 0)),
        )

        assertEquals(
            listOf(Point(0, 0), Point(0, 1), Point(0, 2)),
            get(Point(0, 0), Point(0, 2)),
        )

        assertEquals(
            listOf(Point(0, 0), Point(0, -1), Point(0, -2)),
            get(Point(0, 0), Point(0, -2)),
        )

        assertEquals(
            listOf(Point(0, 0), Point(1, 1), Point(2, 2)),
            get(Point(0, 0), Point(2, 2)),
        )

        assertEquals(
            listOf(Point(0, 0), Point(3, 1), Point(6, 2)),
            get(Point(0, 0), Point(6, 2)),
        )

        assertEquals(
            listOf(Point(0, 0), Point(-3, 1), Point(-6, 2)),
            get(Point(0, 0), Point(-6, 2)),
        )

        assertEquals(
            listOf(Point(0, 0), Point(3, -1), Point(6, -2)),
            get(Point(0, 0), Point(6, -2)),
        )

        assertEquals(
            listOf(Point(0, 0), Point(-3, -1), Point(-6, -2)),
            get(Point(0, 0), Point(-6, -2)),
        )
    }

    @Test
    fun stringify() {
        val line = Line(Point(0, 0), Point(2, 2))
        assertEquals("Line((0, 0) → (2, 2))", line.toString())
    }
}
