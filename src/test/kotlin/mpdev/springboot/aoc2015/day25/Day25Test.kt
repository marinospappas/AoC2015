package mpdev.springboot.aoc2015.day25

import mpdev.springboot.aoc2015.input.InputDataReader
import mpdev.springboot.aoc2015.solutions.day25.Encryption
import mpdev.springboot.aoc2015.utils.println
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test

class Day25Test {

    private val day = 25                                    ///////// Update this for a new dayN test
    private lateinit var solver: Encryption         ///////// Update this for a new dayN test
    private val inputDataReader = InputDataReader("src/test/resources/inputdata/input")

    @BeforeEach
    fun setup() {
        solver = Encryption(inputDataReader)
        solver.initialize()
    }

    @Test
    @Order(1)
    fun `Sets Day correctly`() {
        assertThat(solver.day).isEqualTo(day)
    }

    @Test
    @Order(4)
    fun `Solves Part 1`() {
        solver.debug = true
        val result = solver.solvePart1().also { it.println() }
        assertThat(result).isEqualTo(27995004)
    }

}
