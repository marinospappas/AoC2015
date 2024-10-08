package mpdev.springboot.aoc2015.day25

import mpdev.springboot.aoc2015.input.InputFileReader
import mpdev.springboot.aoc2015.solutions.day25.Encryption
import mpdev.springboot.aoc2015.utils.println
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test

class Day25Test {

    private val day = 25                                    ///////// Update this for a new dayN test
    private var solver: Encryption         ///////// Update this for a new dayN test
    private val inputDataReader = InputFileReader("src/test/resources/inputdata/")

    init {
        solver = Encryption(inputDataReader)
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
    @Order(4)
    fun `Solves Part 1`() {
        solver.debug = true
        val result = solver.solvePart1().also { it.println() }
        assertThat(result).isEqualTo(27995004)
    }

}
