package com.kylemayes.aoc2021.common.geometry

import org.apache.commons.lang3.builder.EqualsBuilder
import org.apache.commons.lang3.builder.HashCodeBuilder
import kotlin.math.absoluteValue

/** A 2D ray. */
class Ray(val start: Point, next: Point) {
    val slope = start.slopeTo(next)
    val angle = start.angleTo(next)

    val horizontal: Boolean get() = slope.y == 0
    val vertical: Boolean get() = slope.x == 0
    val diagonal: Boolean get() = slope.x.absoluteValue == slope.y.absoluteValue

    /** Returns the points along this ray. */
    fun points(): Sequence<Point> =
        generateSequence(start) { it + slope }

    override fun equals(other: Any?) = other is Ray && EqualsBuilder()
        .append(start, other.start)
        .append(slope, other.slope)
        .isEquals

    override fun hashCode() = HashCodeBuilder()
        .append(start)
        .append(slope)
        .toHashCode()

    override fun toString() = "Ray($start → $slope [$angle°])"
}
