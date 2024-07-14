package mpdev.springboot.aoc2015.day01

import mpdev.springboot.aoc2015.input.InputDataReader
import mpdev.springboot.aoc2015.solutions.day01.ElevatorButtons
import mpdev.springboot.aoc2015.utils.println
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test

class Day01Test {

    private val day = 1                                     ///////// Update this for a new dayN test
    private lateinit var solver: ElevatorButtons         ///////// Update this for a new dayN test
    private val inputDataReader = InputDataReader("src/test/resources/inputdata/input")

    @BeforeEach
    fun setup() {
        solver = ElevatorButtons(inputDataReader)
        solver.initialize()
    }

    @Test
    @Order(1)
    fun `Sets Day correctly`() {
        assertThat(solver.day).isEqualTo(day)
    }

    @Test
    @Order(2)
    fun `Reads Input ans sets the Directions List`() {
        solver.buttonsList.forEach { it.println() }
        assertThat(solver.buttonsList.size).isEqualTo(4)
    }

    @Test
    @Order(4)
    fun `Solves Part 1`() {
        val inputs = solver.inputData.toList()
        val expected = listOf(0, 0, 3, 3, 3, -1, -3, -3)
        for (i in inputs.indices) {
            solver.inputValue = inputs[i]
            solver.initialize()
            val result = solver.solvePart1().also { it.println() }
            assertThat(result).isEqualTo(expected[i])
        }
    }

    @Test
    @Order(6)
    fun `Solves Part 2`() {
        val inputs = solver.inputData.toList()
        val expected = listOf(-1, -1, -1, -1, 1, 3, 1, 3)
        for (i in inputs.indices) {
            solver.inputValue = inputs[i]
            solver.initialize()
            val result = solver.solvePart2().also { it.println() }
            assertThat(result).isEqualTo(expected[i])
        }
    }
}
