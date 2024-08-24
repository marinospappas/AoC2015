package mpdev.springboot.aoc2015.day12

import com.fasterxml.jackson.databind.node.JsonNodeType
import mpdev.springboot.aoc2015.input.InputFileReader
import mpdev.springboot.aoc2015.solutions.day12.JsonProcessor
import mpdev.springboot.aoc2015.utils.println
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test

class Day12Test {

    private val day = 12                                    ///////// Update this for a new dayN test
    private lateinit var solver: JsonProcessor         ///////// Update this for a new dayN test
    private val inputDataReader = InputFileReader("src/test/resources/inputdata/")

    @BeforeEach
    fun setup() {
        solver = JsonProcessor(inputDataReader)
        solver.initialize()
    }

    @Test
    @Order(1)
    fun `Sets Day correctly`() {
        assertThat(solver.day).isEqualTo(day)
    }

    @Test
    @Order(2)
    fun `Reads Json from Input`() {
        solver.jsonInput.println()
        assertThat(solver.jsonInput.nodeType).isEqualTo(JsonNodeType.ARRAY)
    }

    @Test
    @Order(5)
    fun `Solves Part 1`() {
        val result = solver.solvePart1().also { it.println() }
        assertThat(result).isEqualTo(3531)
    }

    @Test
    @Order(6)
    fun `Solves Part 2`() {
        val result = solver.solvePart2().also { it.println() }
        assertThat(result).isEqualTo(3074)
    }
}
