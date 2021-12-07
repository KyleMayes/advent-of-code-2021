package com.kylemayes.aoc2021.common

/** Gets an item in this list with a wrapping index. */
fun <T> List<T>.getMod(index: Int): T {
    return this[Math.floorMod(index, size)]
}

/** Sets an item in this list with a wrapping index. */
fun <T> MutableList<T>.setMod(index: Int, item: T) {
    this[Math.floorMod(index, size)] = item
}
