package mpdev.springboot.aoc2015.solutions.day12

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.JsonNodeType
import mpdev.springboot.aoc2015.input.InputFileReader
import mpdev.springboot.aoc2015.solutions.PuzzleSolver
import org.springframework.stereotype.Component

@Component
class JsonProcessor(inputFileReader: InputFileReader): PuzzleSolver(inputFileReader, 12) {

    val jsonInput: JsonNode = ObjectMapper().readTree(inputData.joinToString(""))

    var numericSum = 0

    fun processJsonIntFields(jsonNode: JsonNode, ignoreRed: Boolean) {
        if (isRedObject(jsonNode) && ignoreRed)
            return
        jsonNode.toList().forEach { node ->
            when (node.nodeType) {
                JsonNodeType.NUMBER -> numericSum += node.intValue()
                JsonNodeType.OBJECT, JsonNodeType.ARRAY -> processJsonIntFields(node, ignoreRed)
                else -> {}
            }
        }
    }

    fun isRedObject(jsonNode: JsonNode) = jsonNode.nodeType == JsonNodeType.OBJECT &&
        jsonNode.toList().any { node -> node.nodeType == JsonNodeType.STRING && node.textValue() == "red" }

    override fun solvePart1(): Int {
        numericSum = 0
        processJsonIntFields(jsonInput, false)
        return numericSum
    }

    override fun solvePart2(): Int {
        numericSum = 0
        processJsonIntFields(jsonInput, true)
        return numericSum
    }
}
