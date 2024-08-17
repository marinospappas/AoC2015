package mpdev.springboot.aoc2015.solutions.day25

import mpdev.springboot.aoc2015.input.InputDataReader
import mpdev.springboot.aoc2015.solutions.PuzzleSolver
import mpdev.springboot.aoc2015.utils.Point
import mpdev.springboot.aoc2015.utils.println
import org.springframework.stereotype.Component

@Component
class Encryption(inputDataReader: InputDataReader): PuzzleSolver(inputDataReader, 25) {

    val coords = inputData.map {val a =it.split(","); Point(a[1].toInt(), a[0].toInt()) }.first()
    var debug = false

    override fun solvePart1(): Long {
        var x = 1
        var y = 1
        var currentNumber = 20151125L
        if (debug) currentNumber.println()
        while (x != coords.x || y !=coords.y) {
            if (y == 1) {
                y = x + 1
                x = 1
            }
            else {
                --y
                ++x
            }
            currentNumber = (currentNumber * 252533L) % 33554393L
            if (debug) println("$y,$x: $currentNumber")
        }
        return currentNumber
    }

    override fun solvePart2(): String = "End of AoC 2015"

}