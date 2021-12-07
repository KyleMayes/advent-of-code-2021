// SPDX-License-Identifier: Apache-2.0

package com.kylemayes.aoc2021.common

@JvmName("meanOfInt")
fun List<Int>.mean(): Double =
    sum().toDouble() / size.toDouble()

@JvmName("meanOfLong")
fun List<Long>.mean(): Double =
    sum().toDouble() / size.toDouble()

@JvmName("medianOfInt")
fun List<Int>.median(): Double =
    if (size % 2 == 0) {
        val midpoint = size / 2
        val a = this[midpoint - 1].toDouble()
        val b = this[midpoint].toDouble()
        (a + b) / 2.0
    } else {
        this[size / 2].toDouble()
    }

@JvmName("medianOfLong")
fun List<Long>.median(): Double =
    if (size % 2 == 0) {
        val midpoint = size / 2
        val a = this[midpoint].toDouble()
        val b = this[midpoint + 1].toDouble()
        (a + b) / 2.0
    } else {
        this[size / 2].toDouble()
    }
