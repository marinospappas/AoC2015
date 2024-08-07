package mpdev.springboot.aoc2015.solutions.day13

import kotlinx.serialization.Serializable
import mpdev.springboot.aoc2015.input.InputDataReader
import mpdev.springboot.aoc2015.solutions.PuzzleSolver
import mpdev.springboot.aoc2015.utils.*
import org.springframework.stereotype.Component

@Component
class OptimumSeating(inputDataReader: InputDataReader): PuzzleSolver(inputDataReader, 13) {

    val graph = SGraph<String>()
    lateinit var start: String
    var debug = false

    override fun initialize() {
        val aocInputList: List<AoCInput> = InputUtils(AoCInput::class.java).readAoCInput(inputData)
        val nodesMap = aocInputList.map { listOf(it.person1, it.person2) }.flatten().distinct().associateWith { mutableMapOf<String, Int>() }
        aocInputList.forEach { nodesMap[it.person1]?.set(it.person2, getTotalScore(aocInputList, it.person1, it.person2)) }
        nodesMap.forEach { (person, connections) -> graph.addNode(person, connections, true) }
        start = graph.getNodes().first()
    }

    override fun solvePart1() = graph.longestPathDfs(start) { id -> id == start }

    override fun solvePart2(): Int {
        graph.addNode(ADDITIONAL_GUEST, graph.getNodes().associateWith { 0 }, true)
        if (debug)
            graph.print()
        return graph.longestPathDfs(start) { id -> id == start }
    }

    companion object {
        const val ADDITIONAL_GUEST = "Me"
        fun getTotalScore(list: List<AoCInput>, p1: String, p2: String) =
            list.filter { it.person1 == p1 && it.person2 == p2 || it.person1 == p2 && it.person2 == p1 }
                .sumOf { it.score }
    }
}

@Serializable
@AocInClass(delimiters = [" "])
@AocInReplacePatterns([" would gain ", " ", " would lose ", " -", " happiness units by sitting next to ", " ", ".$", ""])
data class AoCInput(
    // Alice would gain 54 happiness units by sitting next to Bob.
    // Alice would lose 79 happiness units by sitting next to Carol.
    @AocInField val person1: String,
    @AocInField val score: Int,
    @AocInField val person2: String
)