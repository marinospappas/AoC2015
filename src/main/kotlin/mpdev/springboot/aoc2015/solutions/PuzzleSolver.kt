package mpdev.springboot.aoc2015.solutions

import mpdev.springboot.aoc2015.input.InputFileReader
import mpdev.springboot.aoc2015.model.PuzzlePartSolution
import mpdev.springboot.aoc2015.model.PuzzleSolution
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import kotlin.system.measureTimeMillis

abstract class PuzzleSolver(inputFileReader: InputFileReader, val day: Int) {

    protected val log: Logger = LoggerFactory.getLogger(this::class.java)

    var inputData: List<String> = inputFileReader.getInput(day)

    fun solve(): PuzzleSolution {
        log.info("solver for day {} called", day)
        val part1: Any
        val part2: Any
        val initTime = measureTimeMillis { initialize() }
        val elapsed1 = measureTimeMillis { part1 = solvePart1() }
        val elapsed2 = measureTimeMillis { part2 = solvePart2() }
        log.info("day {} part 1 answer: {} part 2 answer: {}", day, part1, part2)
        return PuzzleSolution(day = day, initTime = initTime,
            solution = setOf(
                PuzzlePartSolution(1, part1.toString(), elapsed1),
                PuzzlePartSolution(2, part2.toString(), elapsed2)
            )
        )
    }

    open fun initialize() {}
    abstract fun solvePart1(): Any
    abstract fun solvePart2(): Any

}