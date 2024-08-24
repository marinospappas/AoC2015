package mpdev.springboot.aoc2015.utils

import kotlin.math.abs
import kotlin.math.min

fun String.splitRepeatedChars(): List<String> {
    if (isEmpty())
        return emptyList()
    val s = StringBuilder(this)
    var index = 0
    val delimiter = '_'
    var previous = s.first()
    while (index < s.length) {
        if (s[index] != previous)
            s.insert(index++, delimiter)
        previous = s[index]
        ++index
    }
    return s.split(delimiter)
}

fun String.toFrequency(): String =
    this.toList().associateWith { c -> this.count { it == c } }
        .entries
        .sortedWith { e1, e2 ->
            if (e1.value == e2.value)
                e1.key.compareTo(e2.key)
            else
                e2.value.compareTo(e1.value)
        }
        .map { it.key }
        .joinToString("")

fun Int.lastDigit() = this % 10

fun Int.numOfDigits() = abs(this).toString().length

operator fun IntArray.plus(other: IntArray) = Array(size) { this[it] + other[it] }.toIntArray()

fun Array<IntRange>.allValues(): Set<MutableList<Int>> {
    return if (size == 1)
        (0 .. this[0].last - this[0].first).map { mutableListOf(this[0].first + it) }.toSet()
    else {
        val result = mutableSetOf<MutableList<Int>>()
        this.last().forEach { value ->
            this.sliceArray(0 .. size - 2).allValues().forEach { combo ->
                result.add(combo.also { combo.add(value) })
            }
        }
        result
    }
}

fun min(a1: Int, a2: Int, a3: Int, a4: Int): Int {
    return min(min(a1,a2), min(a3,a4))
}

fun min(a1: Long, a2: Long, a3: Long, a4: Long): Long {
    return min(min(a1,a2), min(a3,a4))
}

fun String.isAnagram(other: String): Boolean =
    this.toCharArray().toList().sorted() == other.toCharArray().toList().sorted()

fun Any?.println() = println(this)

fun Any?.print() = print(this)

fun <T>List<T>.pairWith(other: List<T>): List<Pair<T,T>> {
    val result = mutableListOf<Pair<T,T>>()
    val len = min(this.size, other.size)
    for (indx in 0 until len)
        result.add(Pair(this[indx], other[indx]))
    return result
}

fun Int.divisors() = this.toLong().divisors().map { it.toInt() }.toSet()

fun Long.divisors(): Set<Long> {
    val result = mutableSetOf<Long>()
    for (i in 1L .. this)
        if (this % i == 0L)
            result.add(i)
    return result
}

fun Long.primeFactors(): Set<List<Long>> {
    var num = this
    val factors = mutableListOf<Long>()
    while (num > 1) {
        val divisor = num.findFirstDivisor()
        factors.add(divisor)
        num /= divisor
    }
    return factors.groupBy { it }.values.toSet()
}

fun Long.findFirstDivisor(): Long {
    for (i in 2 .. this)
        if (this % i == 0L)
            return i
    return 1
}

fun Set<Int>.gcd() = this.map { it.toLong() }.toSet().gcd().toInt()

fun Set<Long>.gcd(): Long {
    for (i in this.min() downTo 1)
        if (this.all { it % i == 0L })
            return i
    return 1
}

fun Set<Int>.lcm() = this.map { it.toLong() }.toSet().lcm().toInt()

fun Set<Long>.lcm(): Long {
    val gcd = this.gcd()
    return this.fold(gcd) { acc, l -> l / gcd * acc }
}

fun Char.isVowel() = setOf('a', 'e', 'i', 'o', 'u').contains(this)

fun String.containsSeqTwice(seqLen: Int, gapLen: Int): Boolean {
    val endIndex = if (gapLen >= 0) this.length - 1 - seqLen - gapLen else this.length - 1 - seqLen
    for (i in 0 .. endIndex) {
        val seqToCheck = this.substring(i, i + seqLen)
        when (gapLen) {
            // seq repeated twice in a row
            0 -> if (this.substring(i + seqLen, this.length).startsWith(seqToCheck))
                return true
            // seq repeated anywhere in the string (but not overlapping)
            -1 -> if (this.substring(i + seqLen, this.length).contains(seqToCheck))
                return true
            // seq repeated after exactly gapLen chars
            else -> if (this.substring(i + seqLen + gapLen, this.length).startsWith(seqToCheck))
                return true
        }

    }
    return false
}
