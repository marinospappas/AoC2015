package mpdev.springboot.aoc2015.day17

import mpdev.springboot.aoc2015.input.InputFileReader
import mpdev.springboot.aoc2015.solutions.day17.Containerisation
import mpdev.springboot.aoc2015.utils.println
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test

class Day17Test {

    private val day = 17                                    ///////// Update this for a new dayN test
    private var solver: Containerisation         ///////// Update this for a new dayN test
    private val inputDataReader = InputFileReader("src/test/resources/inputdata/")

    init {
        solver = Containerisation(inputDataReader)
    }

    @BeforeEach
    fun setup() {
        solver.initialize()
    }

    @Test
    @Order(1)
    fun `Sets Day correctly`() {
        assertThat(solver.day).isEqualTo(day)
    }

    @Test
    @Order(2)
    fun `Reads containers list from Input`() {
        solver.containers.println()
        assertThat(solver.containers.size).isEqualTo(5)
    }

    @Test
    @Order(5)
    fun `Solves Part 1`() {
        solver.totalQuantity = 25
        val result = solver.solvePart1().also { it.println() }
        assertThat(result).isEqualTo(4)
    }

    @Test
    @Order(6)
    fun `Solves Part 2`() {
        solver.totalQuantity = 25
        solver.solvePart1()
        val result = solver.solvePart2().also { it.println() }
        assertThat(result).isEqualTo(3)
    }
}
