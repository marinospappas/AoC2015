package mpdev.springboot.aoc2015.solutions.day09

import kotlinx.serialization.Serializable
import mpdev.springboot.aoc2015.input.InputFileReader
import mpdev.springboot.aoc2015.solutions.PuzzleSolver
import mpdev.springboot.aoc2015.utils.*
import org.springframework.stereotype.Component

@Component
class ShortestRoute(inputFileReader: InputFileReader): PuzzleSolver(inputFileReader, 9) {

    val graph = SGraph<String>()

    override fun initialize() {
        val aocInputList: List<AoCInput> = InputUtils(AoCInput::class.java).readAoCInput(inputData)
        val nodesMap = aocInputList.map { listOf(it.from, it.to) }.flatten().distinct().associateWith { mutableMapOf<String, Int>() }
        aocInputList.forEach { nodesMap[it.from]?.set(it.to, it.distance) }
        nodesMap.forEach { (city, connections) -> graph.addNode(city, connections, true) }
        // add "Start" node at distance 0 from each node (means the route can start anywhere)
        graph.addNode(START, nodesMap.keys.associateWith { 0 }, true)
    }

    override fun solvePart1() = graph.tsp(START).minCost

    override fun solvePart2() = graph.longestPathDfs(START) { id -> id == START }

    companion object {
        const val START = "Start"
    }
}

@Serializable
@AocInClass(delimiters = [" "])
@AocInReplacePatterns([" to ", " ", " = ", " "])
data class AoCInput(
    // London to Dublin = 464
    @AocInField(0) val from: String,
    @AocInField(1) val to: String,
    @AocInField(1) val distance: Int
)