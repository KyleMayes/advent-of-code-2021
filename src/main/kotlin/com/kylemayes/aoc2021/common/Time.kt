package com.kylemayes.aoc2021.common

/** Prints the execution time of the supplied block. */
fun <T> time(name: String, block: () -> T): T {
    val start = System.nanoTime()
    val result = block()
    println("$name: ${(System.nanoTime() - start).toDouble() / 1000000.0}ms")
    return result
}
