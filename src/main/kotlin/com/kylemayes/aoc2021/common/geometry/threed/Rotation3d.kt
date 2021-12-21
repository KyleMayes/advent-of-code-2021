package com.kylemayes.aoc2021.common.geometry.threed

import com.kylemayes.aoc2021.common.permutations
import com.kylemayes.aoc2021.common.product

/** An axis-aligned 3D rotation of a 3D point around the origin. */
data class Rotation3d(
    val negateX: Boolean,
    val negateY: Boolean,
    val negateZ: Boolean,
    val indices: List<Int>,
) {
    fun apply(point: Point3d): Point3d {
        val x = if (negateX) { -point.x } else { point.x }
        val y = if (negateY) { -point.y } else { point.y }
        val z = if (negateZ) { -point.z } else { point.z }
        val coordinates = listOf(x, y, z)
        return Point3d(
            coordinates[indices[0]],
            coordinates[indices[1]],
            coordinates[indices[2]],
        )
    }
}

/** The axis-aligned 3D rotations of a 3D point around the origin. */
val ROTATIONS = run {
    val rotations = mutableListOf<Rotation3d>()

    for (negate in listOf(true, false).product(3)) {
        for (indices in listOf(0, 1, 2).permutations(3)) {
            rotations.add(
                Rotation3d(
                    negateX = negate[0],
                    negateY = negate[1],
                    negateZ = negate[2],
                    indices = indices,
                )
            )
        }
    }

    rotations
}
