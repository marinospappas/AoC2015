package mpdev.springboot.aoc2015.day08

import mpdev.springboot.aoc2015.input.InputFileReader
import mpdev.springboot.aoc2015.solutions.day08.EscapeCharacters
import mpdev.springboot.aoc2015.utils.println
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test

class Day08Test {

    private val day = 8                                    ///////// Update this for a new dayN test
    private lateinit var solver: EscapeCharacters         ///////// Update this for a new dayN test
    private val inputDataReader = InputFileReader("src/test/resources/inputdata/")

    @BeforeEach
    fun setup() {
        solver = EscapeCharacters(inputDataReader)
        solver.initialize()
    }

    @Test
    @Order(1)
    fun `Sets Day correctly`() {
        assertThat(solver.day).isEqualTo(day)
    }

    @Test
    @Order(2)
    fun `Reads Input and sets up Strings List`() {
        solver.inputData.forEach { it.println() }
        assertThat(solver.inputData).hasSize(4)
        assertThat(solver.inputData.sumOf { it.length }).isEqualTo(23)
    }

    @Test
    @Order(5)
    fun `Solves Part 1`() {
        val result = solver.solvePart1().also { it.println() }
        assertThat(result).isEqualTo(12)
    }

    @Test
    @Order(6)
    fun `Solves Part 2`() {
        solver.processedStrings.forEach { it.println() }
        val result = solver.solvePart2().also { it.println() }
        assertThat(result).isEqualTo(19)
    }
}
