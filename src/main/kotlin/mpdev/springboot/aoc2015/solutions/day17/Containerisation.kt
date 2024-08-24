package mpdev.springboot.aoc2015.solutions.day17

import mpdev.springboot.aoc2015.input.InputFileReader
import mpdev.springboot.aoc2015.solutions.PuzzleSolver
import mpdev.springboot.aoc2015.utils.combinations
import org.springframework.stereotype.Component

@Component
class Containerisation(inputFileReader: InputFileReader): PuzzleSolver(inputFileReader, 17) {

    val containers = inputData.map { it.toInt() }
    var totalQuantity = 150
    val solutions = mutableListOf<List<Int>>()

    override fun solvePart1(): Int {
        solutions.clear()
        for (n in 2 .. containers.size) {
            containers.combinations(n).forEach { containerList ->
                if (containerList.sum() == totalQuantity)
                    solutions.add(containerList)
            }
        }
        return solutions.size
    }

    override fun solvePart2(): Int {
        val minSize = solutions.minOf { it.size }
        return solutions.count { it.size == minSize }
    }
}