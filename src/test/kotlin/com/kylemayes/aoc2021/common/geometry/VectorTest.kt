package com.kylemayes.aoc2021.common.geometry

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class VectorTest {
    @Test
    fun stringify() {
        val vector = Vector(Point(0, 0), Point(1, 1))
        assertEquals("Vector((0, 0) → (1, 1) [1.41 @ 45.0°])", vector.toString())
    }
}
