package com.kylemayes.aoc2021.common.geometry

import kotlin.math.max
import kotlin.math.min

/** A 2D line. */
data class Line(
    val start: Point,
    val end: Point,
) {
    val ray: Ray = Ray(start, end)

    val slope: Point get() = ray.slope
    val angle: Double get() = ray.angle

    val horizontal: Boolean get() = ray.horizontal
    val vertical: Boolean get() = ray.vertical
    val diagonal: Boolean get() = ray.diagonal

    /** Returns the points along this line. */
    fun points(): List<Point> {
        val xs = min(start.x, end.x)..max(start.x, end.x)
        val ys = min(start.y, end.y)..max(start.y, end.y)
        return ray
            .points()
            .takeWhile { xs.contains(it.x) && ys.contains(it.y) }
            .toList()
    }

    override fun toString() = "Line($start → $end)"
}
