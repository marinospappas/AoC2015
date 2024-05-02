package mpdev.springboot.aoc2016.solutions.day01

import mpdev.springboot.aoc2016.input.InputDataReader
import mpdev.springboot.aoc2016.solutions.PuzzleSolver
import mpdev.springboot.aoc2016.utils.AocException
import mpdev.springboot.aoc2016.utils.GridUtils
import mpdev.springboot.aoc2016.utils.Point
import org.springframework.stereotype.Component

@Component
class ElevatorButtons(inputDataReader: InputDataReader): PuzzleSolver(inputDataReader, 1) {

    val buttonsList: List<Button> = inputData[0].toList().map { c -> Button.values().first { it.value == c } }

    override fun solvePart1() = buttonsList.sumOf { it.floor }

    override fun solvePart2(): Int {
        for (i in buttonsList.indices)
            if (buttonsList.subList(0, i + 1).sumOf { it.floor } == -1)
                return i + 1
        return -1
    }

}

enum class Button(val value: Char, val floor: Int) {
    UP('(', 1),
    DOWN(')', -1)
}