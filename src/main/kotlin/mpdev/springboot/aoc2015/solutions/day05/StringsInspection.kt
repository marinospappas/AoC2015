package mpdev.springboot.aoc2015.solutions.day05

import mpdev.springboot.aoc2015.input.InputFileReader
import mpdev.springboot.aoc2015.solutions.PuzzleSolver
import mpdev.springboot.aoc2015.utils.containsSeqTwice
import mpdev.springboot.aoc2015.utils.isVowel
import org.springframework.stereotype.Component

@Component
class StringsInspection(inputFileReader: InputFileReader): PuzzleSolver(inputFileReader, 5) {

    var strings = inputData

    fun containsUnwanted(s: String): Boolean {
        for (unwanted in unwantedStrings)
            if (s.contains(unwanted))
                return true
        return false
    }

    override fun solvePart1() =
        strings.count { s -> s.count { c -> c.isVowel() } >= 3 && s.containsSeqTwice(1, 0) && !containsUnwanted(s) }

    override fun solvePart2() =
        strings.count { s -> s.containsSeqTwice(2, -1) && s.containsSeqTwice(1, 1) }

    companion object {
        val unwantedStrings = setOf("ab", "cd", "pq", "xy")
    }
}