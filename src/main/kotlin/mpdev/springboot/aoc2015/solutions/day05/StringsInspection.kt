package mpdev.springboot.aoc2015.solutions.day05

import mpdev.springboot.aoc2015.input.InputDataReader
import mpdev.springboot.aoc2015.solutions.PuzzleSolver
import mpdev.springboot.aoc2015.utils.*
import org.springframework.stereotype.Component

@Component
class StringsInspection(inputDataReader: InputDataReader): PuzzleSolver(inputDataReader, 5) {

    fun containsUnwanted(s: String): Boolean {
        for (unwanted in unwantedStrings)
            if (s.contains(unwanted))
                return true
        return false
    }

    override fun solvePart1() =
        inputData.count { s -> s.count { c -> c.isVowel() } >= 3 && s.containsCharTwiceInARow() && !containsUnwanted(s) }

    override fun solvePart2(): Int {
        return 1
    }

    companion object {
        val unwantedStrings = setOf("ab", "cd", "pq", "xy")
    }
}