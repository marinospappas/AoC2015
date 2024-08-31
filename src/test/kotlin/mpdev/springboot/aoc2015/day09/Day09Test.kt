package mpdev.springboot.aoc2015.day09

import mpdev.springboot.aoc2015.input.InputFileReader
import mpdev.springboot.aoc2015.solutions.day09.ShortestRoute
import mpdev.springboot.aoc2015.utils.println
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test

class Day09Test {

    private val day = 9                                    ///////// Update this for a new dayN test
    private var solver: ShortestRoute         ///////// Update this for a new dayN test
    private val inputDataReader = InputFileReader("src/test/resources/inputdata/")

    init {
        solver = ShortestRoute(inputDataReader)
    }

    @BeforeEach
    fun setup() {
        solver = ShortestRoute(inputDataReader)
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
        assertThat(result).isEqualTo(605)
    }

    @Test
    @Order(6)
    fun `Solves Part 2`() {
        val result = solver.solvePart2().also { it.println() }
        assertThat(result).isEqualTo(982)
    }
}
