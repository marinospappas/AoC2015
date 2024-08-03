package mpdev.springboot.aoc2015.solutions.day07

import kotlinx.serialization.Serializable
import mpdev.springboot.aoc2015.input.InputDataReader
import mpdev.springboot.aoc2015.solutions.PuzzleSolver
import mpdev.springboot.aoc2015.utils.*
import org.springframework.stereotype.Component

@Component
class DigitalCircuit(inputDataReader: InputDataReader): PuzzleSolver(inputDataReader, 7) {

    lateinit var circuit: Node
    var gatesMap = mapOf<String, Node>()

    override fun initialize() {
        val aocInputList: List<AoCInput> = InputUtils(AoCInput::class.java).readAoCInput(inputData)
        val connectionsMap = aocInputList.map { Pair(it.connectedTo, listOf(it.inputA, it.inputB)) }.associate { it.first to it.second }
        gatesMap = aocInputList.associate { it.connectedTo to Node(it.connectedTo, it.gate) }
        gatesMap.values.forEach { node ->
            val connections = connectionsMap[node.id] ?: throw AocException("application error 07-001")
            for (i in 0..1)
                node.inputs[i] = if (connections[i].all { it.isDigit() })
                    connections[i].toInt()
                else
                    gatesMap[connections[i]]
        }
        circuit = gatesMap[circuitRoot] ?: throw AocException("application error 07-002")
    }

    fun calculateOutput(node: Node, calculatedOutput: MutableMap<String, Int>): Int {
        if (calculatedOutput.containsKey(node.id))
            return calculatedOutput[node.id] ?: throw AocException("application error 07-003")
        val inputValues = Array(2) {0}
        for (i in 0 .. 1)
            inputValues[i] = when (node.inputs[i]) {
                is Node -> calculateOutput(node.inputs[i] as Node, calculatedOutput)
                is Int -> node.inputs[i] as Int
                else -> -1
        }
        return node.gate.function(inputValues[0], inputValues[1]).also { calculatedOutput[node.id] = it }
    }

    override fun solvePart1() = calculateOutput(circuit, mutableMapOf())

    override fun solvePart2(): Int {
        gatesMap[overrideGate]?.inputs?.set(0, solvePart1())
        return calculateOutput(circuit, mutableMapOf())
    }

    companion object {
        var circuitRoot = "a"
        var overrideGate = "b"
    }
}

data class Node(val id: String, val gate: Gate, var inputs: MutableList<Any?> = mutableListOf(null,null))

enum class Gate(val function: (Int, Int) -> Int) {
    AND({ a, b -> a.and(b) }),
    OR({ a, b -> a.or(b) }),
    NOT({ _, a -> a.inv().and(0xffff) }),
    LSHIFT({ a, b -> a.shl(b) }),
    RSHIFT({ a, b -> a.shr(b).and(0xffff) }),
    DIRECT({ a, _ -> a }),
    VALUE({ a, _ -> a });
    companion object {
        @JvmStatic
        fun fromString(str: String) = Gate.valueOf(str)
    }
}

@Serializable
@AocInClass(delimiters = [" "])
@AocInReplacePatterns([
    "NOT", "_ NOT",
    "-> ", ""
])
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