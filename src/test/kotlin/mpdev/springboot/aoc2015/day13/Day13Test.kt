package mpdev.springboot.aoc2015.day13

import mpdev.springboot.aoc2015.input.InputFileReader
import mpdev.springboot.aoc2015.solutions.day13.OptimumSeating
import mpdev.springboot.aoc2015.utils.println
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test

class Day13Test {

    private val day = 13                                   ///////// Update this for a new dayN test
    private lateinit var solver: OptimumSeating         ///////// Update this for a new dayN test
    private val inputDataReader = InputFileReader("src/test/resources/inputdata/")

    @BeforeEach
    fun setup() {
        solver = OptimumSeating(inputDataReader)
        solver.initialize()
    }

    @Test
    @Order(1)
    fun `Sets Day correctly`() {
        assertThat(solver.day).isEqualTo(day)
    }

    @Test
    @Order(2)
    fun `Reads Input and sets up Graph`() {
        solver.graph.print()
        assertThat(solver.graph.getNodes()).hasSize(4)
    }

    @Test
    @Order(5)
    fun `Solves Part 1`() {
        val result = solver.solvePart1().also { it.println() }
        assertThat(result).isEqualTo(330)
    }

    @Test
    @Order(6)
    fun `Solves Part 2`() {
        solver.debug = true
        val result = solver.solvePart2().also { it.println() }
        assertThat(result).isEqualTo(286)
    }
}
