package com.kylemayes.aoc2021.common.geometry.threed

import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sqrt

/** A 3D point. */
data class Point3d(val x: Int, val y: Int, val z: Int) : Comparable<Point3d> {
    /** Returns the Euclidean distance between this point and the supplied point. */
    fun distanceTo(other: Point3d): Double =
        sqrt(
            (other.x - x).toDouble().pow(2) +
                (other.y - y).toDouble().pow(2) +
                (other.z - z).toDouble().pow(2)
        )

    /** Returns the Manhattan distance between this point and the supplied point. */
    fun manhattanDistanceTo(other: Point3d): Int =
        abs(other.x - x) + abs(other.y - y) + abs(other.z - z)

    operator fun plus(other: Point3d): Point3d =
        Point3d(x + other.x, y + other.y, z + other.z)
    operator fun minus(other: Point3d): Point3d =
        Point3d(x - other.x, y - other.y, z - other.z)

    override fun compareTo(other: Point3d) =
        compareValuesBy(this, other, { it.x }, { it.y }, { it.z })

    override fun toString() = "($x, $y, $z)"
}
