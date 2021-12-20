// SPDX-License-Identifier: Apache-2.0

package com.kylemayes.aoc2021.common

import com.kylemayes.aoc2021.common.geometry.Point
import com.kylemayes.aoc2021.common.geometry.Tile
import java.util.PriorityQueue

/** Finds the shortest path using `A*`. */
fun <T> findPath(
    start: T,
    goal: T,
    neighbors: (node: T) -> List<T>,
    heuristic: (node: T) -> Int,
    weight: (start: T, end: T) -> Int,
): List<T>? {
    val open = PriorityQueue(Comparator.comparing(heuristic).reversed())
    open.add(start)

    val from = mutableMapOf<T, T>()

    val gscores = mutableMapOf<T, Int>()
    gscores[start] = 0

    val fscores = mutableMapOf<T, Int>()
    fscores[start] = heuristic(start)

    while (open.isNotEmpty()) {
        val current = open.poll()
        if (current == goal) {
            val path = mutableListOf(current)

            var node = current
            while (from.containsKey(node)) {
                node = from[node]
                path.add(0, node)
            }

            return path
        }

        for (neighbor in neighbors(current)) {
            val gscore = gscores[current]!! + weight(current, neighbor)
            if (gscore < gscores.getOrDefault(neighbor, Int.MAX_VALUE)) {
                from[neighbor] = current
                gscores[neighbor] = gscore
                fscores[neighbor] = gscore + heuristic(neighbor)
                if (!open.contains(neighbor)) {
                    open.add(neighbor)
                }
            }
        }
    }

    return null
}

/** Finds the shortest path in a tile using `A*`. */
fun <T> findPath(
    tile: Tile<T>,
    start: Point,
    end: Point,
    weight: (start: Pair<Point, T>, end: Pair<Point, T>) -> Int,
): List<Point>? {
    val heuristic = { p: Point -> end.manhattanDistanceTo(p) }

    val open = PriorityQueue(Comparator.comparing(heuristic).reversed())
    open.add(start)

    val from = tile.map<Point?> { null }

    val gscores = tile.map { Int.MAX_VALUE }
    gscores[start] = 0

    val fscores = tile.map { Int.MAX_VALUE }
    gscores[start] = heuristic(start)

    while (!open.isEmpty()) {
        val current = open.poll()
        if (current == end) {
            val path = mutableListOf(current)

            var node = current
            while (from[node] != null) {
                node = from[node]
                path.add(0, node)
            }

            return path
        }

        for ((neighbor, value) in tile.neighbors(current, false)) {
            val gscore = gscores[current] + weight(current to tile[current], neighbor to value)
            if (gscore < gscores[neighbor]) {
                from[neighbor] = current
                gscores[neighbor] = gscore
                fscores[neighbor] = gscore + heuristic(neighbor)
                if (!open.contains(neighbor)) {
                    open.add(neighbor)
                }
            }
        }
    }

    return null
}
