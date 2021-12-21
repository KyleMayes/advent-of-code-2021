// SPDX-License-Identifier: Apache-2.0

package com.kylemayes.aoc2021.day19

import com.kylemayes.aoc2021.common.Input
import com.kylemayes.aoc2021.common.ResourceInput
import com.kylemayes.aoc2021.common.Solution
import com.kylemayes.aoc2021.common.geometry.threed.Point3d
import com.kylemayes.aoc2021.common.geometry.threed.ROTATIONS
import com.kylemayes.aoc2021.common.product
import com.kylemayes.aoc2021.common.readGroups
import com.kylemayes.aoc2021.common.solve

data class Scanner(
    val id: Int,
    val beacons: List<Point3d>,
    val delta: Point3d = Point3d(0, 0, 0),
)

typealias ParsedInput = List<Scanner>

class Day19 : Solution<ParsedInput> {
    private var scanners = mutableListOf<Scanner>()

    override fun parse(input: Input) = input
        .readGroups()
        .map {
            Scanner(
                id = it[0].substring(12, it[0].length - 4).toInt(),
                beacons = it.drop(1).map { l ->
                    val coordinates = l.split(",").map { c -> c.toInt() }
                    Point3d(coordinates[0], coordinates[1], coordinates[2])
                },
            )
        }

    override fun solvePart1(input: ParsedInput): Int {
        scanners.add(input[0])

        val unmatched = input.drop(1).toMutableList()
        outer@while (unmatched.isNotEmpty()) {
            for (other in unmatched.toList()) {
                for (origin in scanners) {
                    val scanner = getRelativeScanner(origin, other)
                    if (scanner != null) {
                        scanners.add(scanner)
                        unmatched.remove(other)
                        continue@outer
                    }
                }
            }
        }

        return scanners
            .flatMap { it.beacons }
            .toSet()
            .size
    }

    override fun solvePart2(input: ParsedInput) = scanners
        .map { it.delta }
        .product(2)
        .maxOf { it[0].manhattanDistanceTo(it[1]) }

    private fun getRelativeScanner(
        origin: Scanner,
        other: Scanner,
    ): Scanner? {
        val deltas = mutableMapOf<Point3d, Int>()

        for (rotation in ROTATIONS) {
            deltas.clear()
            for (left in other.beacons.map { rotation.apply(it) }) {
                for (right in origin.beacons) {
                    val delta = right - left
                    val total = deltas.compute(delta) { _, v -> v?.let { it + 1 } ?: 1 }
                    if (total == 12) {
                        return other.copy(
                            beacons = other.beacons.map { rotation.apply(it) + delta },
                            delta = delta,
                        )
                    }
                }
            }
        }

        return null
    }
}

fun main() = solve({ Day19() }, ResourceInput("day19.txt"))
