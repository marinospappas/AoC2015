package mpdev.springboot.aoc2015.day15

import mpdev.springboot.aoc2015.input.InputDataReader
import mpdev.springboot.aoc2015.solutions.day15.Recipe
import mpdev.springboot.aoc2015.utils.println
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test

class Day15Test {

    private val day = 15                                   ///////// Update this for a new dayN test
    private lateinit var solver: Recipe         ///////// Update this for a new dayN test
    private val inputDataReader = InputDataReader("src/test/resources/inputdata/input")

    @BeforeEach
    fun setup() {
        solver = Recipe(inputDataReader)
        solver.initialize()
    }

    @Test
    @Order(1)
    fun `Sets Day correctly`() {
        assertThat(solver.day).isEqualTo(day)
    }

    @Test
    @Order(2)
    fun `Reads Input and sets up Ingredients`() {
        solver.ingredients.forEach { it.println() }
        assertThat(solver.ingredients.size).isEqualTo(2)
    }

    @Test
    @Order(3)
    fun `Calculates Score`() {
        (1 .. 99).map { listOf(it, 100-it) }.forEach { println("$it: ${solver.calculateScore(it)}") }
        val result = (1 .. 99).map { listOf(it, 100-it) }.maxOf { solver.calculateScore(it) }.also { it.println() }
        assertThat(result).isEqualTo(62842880)
    }

    @Test
    @Order(4)
    fun `Calculates Calories`() {
        (1 .. 99).map { listOf(it, 100-it) }.forEach { println("$it: ${solver.calculateCalories(it)}") }
        val result = (1 .. 99).map { listOf(it, 100-it) }.filter { solver.calculateCalories(it) == 500 }.also { it.println() }
        assertThat(result).contains(listOf(40,60))
    }
}
