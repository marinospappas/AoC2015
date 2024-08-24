package mpdev.springboot.aoc2015.solutions.day08

import mpdev.springboot.aoc2015.input.InputFileReader
import mpdev.springboot.aoc2015.solutions.PuzzleSolver
import org.springframework.stereotype.Component

@Component
class EscapeCharacters(inputFileReader: InputFileReader): PuzzleSolver(inputFileReader, 8) {

    val compiledStrings = inputData.map {
        it.removePrefix("\"").removeSuffix("\"")
            .replace("\\\\", "\\")
            .replace("\\\"", "\"")
            .replace(Regex("""\\x[a-f0-9]{2}""")) { match -> match.value.substring(2).toInt(16).toChar().toString() }
    }
    val processedStrings = inputData.map {
        "\"" + it .replace("\\", "\\\\")
                .replace("\"", "\\\"") +
        "\""
    }

    override fun solvePart1() = inputData.sumOf { it.length } - compiledStrings.sumOf { it.length }

    override fun solvePart2() = processedStrings.sumOf { it.length } - inputData.sumOf { it.length }
}
