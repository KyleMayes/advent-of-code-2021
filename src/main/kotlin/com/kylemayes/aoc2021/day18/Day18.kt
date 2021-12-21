// SPDX-License-Identifier: Apache-2.0

package com.kylemayes.aoc2021.day18

import com.kylemayes.aoc2021.common.Input
import com.kylemayes.aoc2021.common.ResourceInput
import com.kylemayes.aoc2021.common.Solution
import com.kylemayes.aoc2021.common.StringIterator
import com.kylemayes.aoc2021.common.product
import com.kylemayes.aoc2021.common.readLines
import com.kylemayes.aoc2021.common.solve
import java.util.IdentityHashMap
import kotlin.math.ceil
import kotlin.math.floor

sealed class Element {
    abstract fun clone(): Element
    abstract fun getMagnitude(): Int
}

data class PairElement(
    var left: Element,
    var right: Element,
) : Element() {
    override fun clone() = PairElement(left.clone(), right.clone())
    override fun getMagnitude() = (3 * left.getMagnitude()) + (2 * right.getMagnitude())
    override fun toString() = "[$left,$right]"
}

data class ValueElement(
    var value: Int
) : Element() {
    override fun clone() = ValueElement(value)
    override fun getMagnitude() = value
    override fun toString() = value.toString()
}

typealias ParsedInput = List<Element>

class Day18 : Solution<ParsedInput> {
    override fun parse(input: Input) = input
        .readLines()
        .map { parseElement(StringIterator(it.toList())) }

    private fun parseElement(chars: StringIterator): Element {
        return if (chars.peek() == '[') {
            chars.next()
            val left = parseElement(chars)
            chars.next()
            val right = parseElement(chars)
            chars.next()
            PairElement(left, right)
        } else {
            ValueElement(chars.takeWhile { it.isDigit() }.toInt())
        }
    }

    override fun solvePart1(input: ParsedInput) =
        input
            .reduce { a, b -> reduce(PairElement(a, b)) }
            .getMagnitude()

    override fun solvePart2(input: ParsedInput) =
        listOf(input, input)
            .product()
            .flatMap {
                val left = PairElement(it[0].clone(), it[1].clone())
                val right = PairElement(it[1].clone(), it[0].clone())
                listOf(left, right)
            }
            .maxOf { reduce(it).getMagnitude() }

    private fun reduce(element: Element): Element {
        while (true) {
            val skip = IdentityHashMap<Element, Unit>()
            if (explode(element, skip) != null) continue
            if (split(element)) continue
            return element
        }
    }

    private fun explode(
        element: Element,
        skip: IdentityHashMap<Element, Unit>,
        depth: Int = 0,
    ): Pair<Int?, Int?>? {
        val summands = if (depth == 3 && element is PairElement) {
            if (element.left is PairElement) {
                val left = ((element.left as PairElement).left as ValueElement).value
                val right = ((element.left as PairElement).right as ValueElement).value
                element.left = ValueElement(0)
                skip[element.left] = Unit
                left to right
            } else if (element.right is PairElement) {
                val left = ((element.right as PairElement).left as ValueElement).value
                val right = ((element.right as PairElement).right as ValueElement).value
                element.right = ValueElement(0)
                skip[element.right] = Unit
                left to right
            } else {
                null
            }
        } else if (element is PairElement) {
            explode(element.left, skip, depth + 1) ?: explode(element.right, skip, depth + 1)
        } else {
            return null
        }

        return if (summands != null) {
            var (left, right) = summands

            if (left != null && !skip.containsKey(element.left)) {
                explodeSum(element.left, true, left)
                left = null
            }

            if (right != null && !skip.containsKey(element.right)) {
                explodeSum(element.right, false, right)
                right = null
            }

            skip[element] = Unit
            left to right
        } else {
            summands
        }
    }

    private fun explodeSum(element: Element, left: Boolean, summand: Int) {
        if (element is ValueElement) {
            element.value += summand
        } else if (element is PairElement) {
            val next = if (left) { element.right } else { element.left }
            explodeSum(next, left, summand)
        }
    }

    private fun split(element: Element): Boolean {
        val pair = element as PairElement

        if (pair.left is ValueElement && (pair.left as ValueElement).value >= 10) {
            pair.left = splitDivide((pair.left as ValueElement).value)
            return true
        } else if (pair.left is PairElement) {
            if (split(pair.left)) return true
        }

        if (pair.right is ValueElement && (pair.right as ValueElement).value >= 10) {
            pair.right = splitDivide((pair.right as ValueElement).value)
            return true
        } else if (pair.right is PairElement) {
            if (split(pair.right)) return true
        }

        return false
    }

    private fun splitDivide(value: Int): PairElement {
        val half = value.toDouble() / 2.0
        val left = floor(half).toInt()
        val right = ceil(half).toInt()
        return PairElement(ValueElement(left), ValueElement(right))
    }
}

fun main() = solve({ Day18() }, ResourceInput("day18.txt"))
