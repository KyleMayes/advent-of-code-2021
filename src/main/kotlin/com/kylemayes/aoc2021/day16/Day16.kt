// SPDX-License-Identifier: Apache-2.0

package com.kylemayes.aoc2021.day16

import com.kylemayes.aoc2021.common.Input
import com.kylemayes.aoc2021.common.ResourceInput
import com.kylemayes.aoc2021.common.Solution
import com.kylemayes.aoc2021.common.StringIterator
import com.kylemayes.aoc2021.common.readLines
import com.kylemayes.aoc2021.common.solve

sealed class Packet {
    abstract fun evaluate(): Long
}

data class LiteralPacket(
    val version: Int,
    val type: Int,
    val value: Long,
) : Packet() {
    override fun evaluate(): Long {
        return value
    }
}

data class OperatorPacket(
    val version: Int,
    val type: Int,
    val children: List<Packet>,
) : Packet() {
    override fun evaluate(): Long {
        return when (type) {
            0 -> children.sumOf { it.evaluate() }
            1 -> children.fold(1L) { acc, p -> acc * p.evaluate() }
            2 -> children.minOf { it.evaluate() }
            3 -> children.maxOf { it.evaluate() }
            5 -> if (children[0].evaluate() > children[1].evaluate()) { 1L } else { 0L }
            6 -> if (children[0].evaluate() < children[1].evaluate()) { 1L } else { 0L }
            7 -> if (children[0].evaluate() == children[1].evaluate()) { 1L } else { 0L }
            else -> error("Unreachable.")
        }
    }
}

typealias ParsedInput = List<Char>

class Day16 : Solution<ParsedInput> {
    override fun parse(input: Input) = input
        .readLines()[0]
        .flatMap { it.toString().toInt(16).toString(2).padStart(4, '0').toList() }

    override fun solvePart1(input: ParsedInput): Int {
        var sum = 0

        val stack = ArrayDeque<Packet>()
        stack.addFirst(parsePacket(StringIterator(input)))
        while (stack.isNotEmpty()) {
            when (val next = stack.removeFirst()) {
                is LiteralPacket -> sum += next.version
                is OperatorPacket -> {
                    sum += next.version
                    stack.addAll(next.children)
                }
            }
        }

        return sum
    }

    override fun solvePart2(input: ParsedInput) =
        parsePacket(StringIterator(input)).evaluate()

    private fun parsePacket(bits: StringIterator): Packet {
        val version = bits.take(3).toInt(2)
        return when (val type = bits.take(3).toInt(2)) {
            4 -> LiteralPacket(version, type, parseValue(bits))
            else -> OperatorPacket(version, type, parseChildren(bits))
        }
    }

    private fun parseValue(bits: StringIterator): Long {
        var value = 0L
        while (true) {
            val last = bits.peek() == '0'
            bits.skip(1)
            value += (value * 15L) + bits.take(4).toInt(2)
            if (last) {
                return value
            }
        }
    }

    private fun parseChildren(bits: StringIterator): List<Packet> {
        return if (bits.next() == '0') {
            val packets = mutableListOf<Packet>()

            val length = bits.take(15).toInt(2)
            val start = bits.index
            while (bits.index - start < length) {
                packets.add(parsePacket(bits))
            }

            packets
        } else {
            val length = bits.take(11).toInt(2)
            (1..length).map { parsePacket(bits) }
        }
    }
}

fun main() = solve({ Day16() }, ResourceInput("day16.txt"))
