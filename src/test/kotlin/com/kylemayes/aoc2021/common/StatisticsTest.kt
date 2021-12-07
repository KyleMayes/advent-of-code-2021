package com.kylemayes.aoc2021.common

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class StatisticsTest {
    @Test
    fun mean() {
        assertEquals(1.0, listOf(1).mean())
        assertEquals(2.5, listOf(1, 4).mean())
        assertEquals(12.0 / 3.0, listOf(1, 4, 7).mean())
    }

    @Test
    fun median() {
        assertEquals(1.0, listOf(1).median())
        assertEquals(2.5, listOf(1, 4).median())
        assertEquals(4.0, listOf(1, 4, 7).median())
    }
}
