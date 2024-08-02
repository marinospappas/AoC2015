package mpdev.springboot.aoc2015.solutions.day07

import kotlinx.serialization.Serializable
import mpdev.springboot.aoc2015.input.InputDataReader
import mpdev.springboot.aoc2015.solutions.PuzzleSolver
import mpdev.springboot.aoc2015.utils.*
import org.springframework.stereotype.Component

@Component
class DigitalCircuit(inputDataReader: InputDataReader): PuzzleSolver(inputDataReader, 7) {

    override fun initialize() {
        InputUtils.DEBUG_INPUT = true
        val aocInputList: List<AoCInput> = InputUtils(AoCInput::class.java).readAoCInput(inputData)
        aocInputList.forEach { it.println() }
    }

    override fun solvePart1() = 0

    override fun solvePart2() = 0

    companion object {
    }
}

data class Node(val id: String, val gate: Gate, val inputs: List<Node>, var output: Int)

enum class Gate(val function: (Int, Int) -> Int) {
    AND({ a, b -> a.and(b) }),
    OR({ a, b -> a.or(b) }),
    NOT({ _, a -> a.inv() }),
    LSHIFT({ a, b -> a.shl(b) }),
    RSHIFT({ a, b -> a.ushr(b) }),
    DIRECT({ a, _ -> a }),
    VALUE({ _, _ -> 0 });
    companion object {
        @JvmStatic
        fun fromString(str: String) =
            Gate.valueOf(str)
    }
}

@Serializable
@AocInClass(delimiters = [" "])
@AocInReplacePatterns(["NOT", "_ NOT",  "-> ", ""])
@AocInReplacePatternsWhenLineMatches([
    """^\d+ -> .*""", "->", "VALUE _ ->",
    """^[a-z]+ -> .*""", "->", "DIRECT _ ->"
])
data class AoCInput(
    // x AND y -> d
    @AocInField(0) val inputA: String,
    @AocInField(1) val gate: Gate,
    @AocInField(2) val inputB: String,
    @AocInField(2) val connectedTo: String
)