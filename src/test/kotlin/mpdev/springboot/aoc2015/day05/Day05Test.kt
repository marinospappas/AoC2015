package mpdev.springboot.aoc2015.day05

import mpdev.springboot.aoc2015.input.InputDataReader
import mpdev.springboot.aoc2015.solutions.day05.StringsInspection
import mpdev.springboot.aoc2015.utils.containsCharTwiceInARow
import mpdev.springboot.aoc2015.utils.isVowel
import mpdev.springboot.aoc2015.utils.println
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test

class Day05Test {

    private val day = 5                                     ///////// Update this for a new dayN test
    private lateinit var solver: StringsInspection         ///////// Update this for a new dayN test
    private val inputDataReader = InputDataReader("src/test/resources/inputdata/input")

    @BeforeEach
    fun setup() {
        solver = StringsInspection(inputDataReader)
        solver.initialize()
    }

    @Test
    @Order(1)
    fun `Sets Day correctly`() {
        assertThat(solver.day).isEqualTo(day)
    }

    @Test
    @Order(2)
    fun `Reads Input`() {
        solver.inputData.println()
        assertThat(solver.inputData.size).isEqualTo(5)
    }

    @Test
    @Order(3)
    fun `Solves PArt 1 Criteria`() {
        val expected = listOf(true, true, false, false, false)
        for (i in solver.inputData.indices) {
            val s = solver.inputData[i]
            val result = (s.count { c -> c.isVowel() } >= 3 && s.containsCharTwiceInARow() && !solver.containsUnwanted(s)).also { it.println() }
            assertThat(result).isEqualTo(expected[i])
        }
    }

    @Test
    @Order(4)
    fun `Solves Part 1`() {
        val result = solver.solvePart1().also { it.println() }
        assertThat(result).isEqualTo(2)
    }

    @Test
    @Order(6)
    fun `Solves Part 2`() {
        val result = solver.solvePart2().also { it.println() }
        assertThat(result).isEqualTo(111)
    }
}
