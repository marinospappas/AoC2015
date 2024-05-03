package mpdev.springboot.aoc2016.solutions.day01

import mpdev.springboot.aoc2016.input.InputDataReader
import mpdev.springboot.aoc2016.solutions.PuzzleSolver
import org.springframework.stereotype.Component

@Component
class ElevatorButtons(inputDataReader: InputDataReader): PuzzleSolver(inputDataReader, 1) {

    lateinit var buttonsList: List<Button>
    var inputValue = inputData[0]

    override fun initialize() {
        buttonsList = inputValue.toList().map { c -> Button.values().first { it.value == c } }
    }

    override fun solvePart1() = buttonsList.sumOf { it.floor }

    override fun solvePart2(): Int {
        var curFloor = 0
        for (i in buttonsList.indices)
            if ((curFloor + buttonsList[i].floor).also { curFloor = it } == -1)
                return i + 1
        return -1
    }
}

enum class Button(val value: Char, val floor: Int) {
    UP('(', 1),
    DOWN(')', -1)
}