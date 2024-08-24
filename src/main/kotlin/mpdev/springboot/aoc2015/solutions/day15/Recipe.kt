package mpdev.springboot.aoc2015.solutions.day15

import kotlinx.serialization.Serializable
import mpdev.springboot.aoc2015.input.InputFileReader
import mpdev.springboot.aoc2015.solutions.PuzzleSolver
import mpdev.springboot.aoc2015.utils.AocInClass
import mpdev.springboot.aoc2015.utils.AocInField
import mpdev.springboot.aoc2015.utils.AocInReplacePatterns
import mpdev.springboot.aoc2015.utils.InputUtils
import org.springframework.stereotype.Component

@Component
class Recipe(inputFileReader: InputFileReader): PuzzleSolver(inputFileReader, 15) {

    lateinit var ingredients: List<AoCInput>

    override fun initialize() {
        val aocInputList: List<AoCInput> = InputUtils(AoCInput::class.java).readAoCInput(inputData)
        ingredients = aocInputList.toList()
    }

    fun calculateScore(quantities: List<Int>): Int {
        val capacity = quantities.indices.sumOf { quantities[it] * ingredients[it].capacity }
        val durability = quantities.indices.sumOf { quantities[it] * ingredients[it].durability }
        val flavor = quantities.indices.sumOf { quantities[it] * ingredients[it].flavor }
        val texture = quantities.indices.sumOf { quantities[it] * ingredients[it].texture }
        return (if (capacity < 0) 0 else capacity) * (if (durability < 0) 0 else durability) *
                (if (flavor < 0) 0 else flavor) * (if (texture < 0) 0 else texture)
    }

    fun calculateCalories(quantities: List<Int>) = quantities.indices.sumOf { quantities[it] * ingredients[it].calories }

    override fun solvePart1(): Int {
        val analogies = mutableListOf<List<Int>>()
        for ( i in 1 .. 97)
            for (j in 1 .. 98 - i)
                for (k in 1 .. 99 - i - j)
                    analogies.add(listOf(i, j, k, 100 - i - j - k))
        return analogies.maxOf { calculateScore(it) }
    }

    override fun solvePart2(): Int {
        val analogies = mutableListOf<List<Int>>()
        for ( i in 1 .. 97)
            for (j in 1 .. 98 - i)
                for (k in 1 .. 99 - i - j) {
                    if (calculateCalories(listOf(i, j, k, 100 - i - j - k)) == 500)
                        analogies.add(listOf(i, j, k, 100 - i - j - k))
                }
        return analogies.maxOf { calculateScore(it) }
    }
}

@Serializable
@AocInClass(delimiters = [": +", ", +", " +"])
@AocInReplacePatterns(["capacity", "", "durability", " ", "flavor", " ", "texture", "", "calories", ""])
data class AoCInput(
    // Butterscotch: capacity -1, durability -2, flavor 6, texture 3, calories 8
    @AocInField val id: String,
    @AocInField val capacity: Int,
    @AocInField val durability: Int,
    @AocInField val flavor: Int,
    @AocInField val texture: Int,
    @AocInField val calories: Int
)