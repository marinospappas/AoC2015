package mpdev.springboot.aoc2015.solutions.day16

import kotlinx.serialization.Serializable
import mpdev.springboot.aoc2015.input.InputFileReader
import mpdev.springboot.aoc2015.solutions.PuzzleSolver
import mpdev.springboot.aoc2015.utils.*
import org.springframework.stereotype.Component

@Component
class IdentifyItem(inputFileReader: InputFileReader): PuzzleSolver(inputFileReader, 16) {

    lateinit var items: List<Item>

    override fun initialize() {
        val aocInputList: List<AoCInput> = InputUtils(AoCInput::class.java).readAoCInput(inputData)
        items = aocInputList.map { Item(it.id, it.attributes.toList()) }
    }

    override fun solvePart1() =
        items.first { it.matchPart1(inputAttributes.map { at -> Pair(at.first, at.second) }) }.id

    override fun solvePart2() =
        items.first { it.matchPart2(inputAttributes.map { at -> Pair(at.first, at.third) }) }.id

    companion object {
        val inputAttributes = listOf(
            Triple("children", 3) { n: Int -> n == 3 },
            Triple("cats", 7) { n: Int -> n > 7 },
            Triple("samoyeds", 2) { n: Int -> n == 2 },
            Triple("pomeranians", 3) { n: Int -> n < 3 },
            Triple("akitas", 0) { n: Int -> n == 0 },
            Triple("vizslas", 0) { n: Int -> n == 0 },
            Triple("goldfish", 5) { n: Int -> n < 5 },
            Triple("trees", 3) { n: Int -> n > 3 },
            Triple("cars", 2) { n: Int -> n == 2 },
            Triple("perfumes", 1) { n: Int -> n == 1 }
        )
    }
}

data class Item(val id: Int, val attributes: List<Pair<String, Int>>) {
    fun matchPart1(givenAttributes: List<Pair<String, Int>>): Boolean =
        attributes.all { givenAttributes.contains(it) }
    fun matchPart2(givenAttributes: List<Pair<String, (Int) -> Boolean>>): Boolean =
        attributes.all { givenAttributes.first { at -> at.first == it.first }.second(it.second) }
}

@Serializable
@AocInClass(delimiters = ["="])
@AocInReplaceFirst([": ", "="])
@AocInReplacePatterns(["Sue ", "", " ", ""])
data class AoCInput(
    // Sue 10: trees: 2, children: 10, samoyeds: 10
    @AocInField val id: Int,
    @AocInField(listType = [ListType.pair], delimiters = [",", ":"]) val attributes: List<Pair<String, Int>>
)