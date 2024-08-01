package mpdev.springboot.aoc2015.solutions.day06

import kotlinx.serialization.Serializable
import mpdev.springboot.aoc2015.input.InputDataReader
import mpdev.springboot.aoc2015.solutions.PuzzleSolver
import mpdev.springboot.aoc2015.utils.*
import org.springframework.stereotype.Component
import java.util.*

@Component
class LightsGrid(inputDataReader: InputDataReader): PuzzleSolver(inputDataReader, 6) {

    lateinit var instructions: List<Triple<Instruction, Point, Point>>
    val lights = Array(1000) { Array(1000) { 0 } }

    override fun initialize() {
        val aocInputList: List<AoCInput> = InputUtils(AoCInput::class.java).readAoCInput(inputData)
        instructions = aocInputList.map { line ->
            Triple(line.instruction, Point(line.from[0], line.from[1]), Point(line.to[0], line.to[1]))
        }
        initLights()
    }

    fun initLights() {
        for (x in 0..999)
            for (y in 0..999)
                lights[y][x] = 0
    }

    fun executeInstructions(part: Int) {
        instructions.forEach { instruction ->
            for (x in instruction.second.x .. instruction.third.x)
                for (y in instruction.second.y .. instruction.third.y) {
                    lights[y][x] = instruction.first.execute(lights[y][x], part)
                }
        }
    }

    override fun solvePart1(): Int {
        executeInstructions(1)
        return lights.sumOf { it.sum() }
    }

    override fun solvePart2(): Int {
        initLights()
        executeInstructions(2)
        return lights.sumOf { it.sum() }
    }
}

enum class Instruction(val execute: (Int, Int) -> Int) {
    TURN_ON({ state, part ->
        if (part == 1) 1 else state + 1
    }),
    TURN_OFF({ state, part ->
        if (part == 1) 0
        else {
            if (state > 0) state - 1 else 0
        }
    }),
    TOGGLE({ state, part ->
        if (part == 1) {
            if (state == 0) 1 else 0
        }
        else
            state + 2
    });
    companion object {
        @JvmStatic
        fun fromString(str: String) = Instruction.valueOf(str.uppercase(Locale.getDefault()))
    }
}

@Serializable
@AocInClass(delimiters = [" "])
@AocInReplacePatterns(["turn ", "turn_", "through ", ""])
data class AoCInput(
    // turn off 499,499 through 500,500
    // turn on 0,0 through 999,999
    // toggle 0,0 through 999,0
    @AocInField(0) val instruction: Instruction,
    @AocInField(1) val from: List<Int>,
    @AocInField(2) val to: List<Int>
)