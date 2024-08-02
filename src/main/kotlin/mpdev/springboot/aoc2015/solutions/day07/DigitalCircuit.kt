package mpdev.springboot.aoc2015.solutions.day07

import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.StructureKind
import mpdev.springboot.aoc2015.input.InputDataReader
import mpdev.springboot.aoc2015.solutions.PuzzleSolver
import mpdev.springboot.aoc2015.utils.*
import org.springframework.stereotype.Component

@Component
class DigitalCircuit(inputDataReader: InputDataReader): PuzzleSolver(inputDataReader, 7) {

    lateinit var circuit: Node

    override fun initialize() {
        val aocInputList: List<AoCInput> = InputUtils(AoCInput::class.java).readAoCInput(inputData)
        val connectionsMap = aocInputList.map { Pair(it.connectedTo, listOf(it.inputA, it.inputB)) }.associate { it.first to it.second }
        val gatesMap = aocInputList.associate { it.connectedTo to Node(it.connectedTo, it.gate) }
        gatesMap.values.forEach { node ->
            val connections = connectionsMap[node.id] ?: throw AocException("application error 07-001")
            for (i in 0..1)
                node.inputs[i] = if (connections[i].all { it.isDigit() })
                    connections[i].toInt()
                else
                    gatesMap[connections[i]]
        }
        circuit = gatesMap[circuitRoot] ?: throw AocException("application error 07-002")
        gatesMap.values.forEach { it.print() }
    }

    override fun solvePart1() = 0

    override fun solvePart2() = 0

    companion object {
        var circuitRoot = "a"
    }
}

data class Node(val id: String, val gate: Gate, var inputs: MutableList<Any?> = mutableListOf(null,null)) {
    fun print() {
        println("Node id: $id, $gate, ${if (inputs[0] is Node) (inputs[0] as Node).id else inputs[0]}" +
                ", ${if (inputs[1] is Node) (inputs[1] as Node).id else inputs[1]}")
    }
}

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