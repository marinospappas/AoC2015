package mpdev.springboot.aoc2015.solutions.day11

import mpdev.springboot.aoc2015.input.InputDataReader
import mpdev.springboot.aoc2015.solutions.PuzzleSolver
import mpdev.springboot.aoc2015.utils.AocException
import org.springframework.stereotype.Component

@Component
class PasswordGenerator(inputDataReader: InputDataReader): PuzzleSolver(inputDataReader, 11) {

    var initialString = inputData[0]

    fun String.countUp(): String {
        var s = this
        for (i in this.length - 1 downTo 0) {
            if (s[i] == 'z')
                s = s.replaceRange(i, i+1, 'a'.toString())
            else {
                s = s.replaceRange(i, i+1, s[i].inc().toString())
                break
            }
        }
        return s
    }

    fun rule1Check(s: String): Boolean {
        for (i in 0 .. s.length - 3)
            if (s[i+1] == s[i].inc() && s[i+2] == s[i+1].inc())
                return true
        return false
    }

    fun rule2Check(s: String) = (s.toList().toSet() intersect setOf('i', 'o', 'l')).isEmpty()

    fun rule3Check(s: String): Boolean {
        val pairs = mutableSetOf<String>()
        for (i in 0 .. s.length - 2)
            if (s[i] == s[i+1])
                pairs.add(s.substring(i, i + 2))
        return pairs.size >= 2
    }

    fun stringCountUp(s: String) = s.countUp()  // used by the test class

    lateinit var part1Result: String

    override fun solvePart1(): String {
        var s = initialString
        repeat (MAX_REPEAT) {
            s = s.countUp()
            if (rule1Check(s) && rule2Check(s) && rule3Check(s)) {
                part1Result = s
                return s
            }
        }
        throw AocException("day 11 part 1 - no solution after $MAX_REPEAT iterations")
    }

    override fun solvePart2(): String {
        var s = part1Result
        repeat (MAX_REPEAT) {
            s = s.countUp()
            if (rule1Check(s) && rule2Check(s) && rule3Check(s)) {
                part1Result = s
                return s
            }
        }
        throw AocException("day 11 part 2 - no solution after $MAX_REPEAT iterations")
    }

    companion object {
        const val MAX_REPEAT = 10_000_000
    }
}
