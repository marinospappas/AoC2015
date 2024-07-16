package mpdev.springboot.aoc2015.day04

import mpdev.springboot.aoc2015.input.InputDataReader
import mpdev.springboot.aoc2015.solutions.day04.AdventCoinMining
import mpdev.springboot.aoc2015.utils.println
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test

class Day04Test {

    private val day = 4                                     ///////// Update this for a new dayN test
    private lateinit var solver: AdventCoinMining         ///////// Update this for a new dayN test
    private val inputDataReader = InputDataReader("src/test/resources/inputdata/input")

    @BeforeEach
    fun setup() {
        solver = AdventCoinMining(inputDataReader)
        solver.initialize()
    }

    @Test
    @Order(1)
    fun `Sets Day correctly`() {
        assertThat(solver.day).isEqualTo(day)
    }

    @Test
    @Order(2)
    fun `Reads Input ans sets the Key`() {
        solver.key.println()
    }

    @Test
    @Order(4)
    fun `Solves Part 1`() {
        val expected = listOf(609043, 1048970)
        solver.inputData.indices.forEach { i ->
            solver.key = solver.inputData[i]
            val result = solver.solvePart1().also { it.println() }
            assertThat(result).isEqualTo(expected[i])
        }
    }

    @Test
    @Order(6)
    fun `Solves Part 2`() {
        val expected = listOf(6742839, 5714438)
        solver.inputData.indices.forEach { i ->
            solver.key = solver.inputData[i]
            val result = solver.solvePart2().also { it.println() }
            assertThat(result).isEqualTo(expected[i])
        }
    }
}
