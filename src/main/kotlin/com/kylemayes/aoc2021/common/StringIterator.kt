package com.kylemayes.aoc2021.common

class StringIterator(val chars: List<Char>) : Iterator<Char> {
    var index = 0
        private set

    override fun hasNext() = index < chars.size
    override fun next() = chars[index++]

    fun peek(): Char? = chars.getOrNull(index)

    fun skip(n: Int) {
        index += n
    }

    fun skipWhile(predicate: (next: Char) -> Boolean) {
        while (peek()?.let { predicate(it) } == true) {
            next()
        }
    }

    fun take(n: Int): String {
        val string = chars.subList(index, index + n).joinToString("")
        index += n
        return string
    }

    fun takeWhile(predicate: (next: Char) -> Boolean): String {
        val start = index
        skipWhile(predicate)
        return chars.subList(start, index).joinToString("")
    }
}
