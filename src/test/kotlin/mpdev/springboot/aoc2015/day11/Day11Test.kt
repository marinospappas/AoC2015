package mpdev.springboot.aoc2015.day11

import mpdev.springboot.aoc2015.input.InputDataReader
import mpdev.springboot.aoc2015.solutions.day11.PasswordGenerator
import mpdev.springboot.aoc2015.utils.println
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class Day11Test {

    private val day = 11                                    ///////// Update this for a new dayN test
    private lateinit var solver: PasswordGenerator         ///////// Update this for a new dayN test
    private val inputDataReader = InputDataReader("src/test/resources/inputdata/input")

    @BeforeEach
    fun setup() {
        solver = PasswordGenerator(inputDataReader)
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
        solver.initialString.println()
        assertThat(solver.initialString).isEqualTo(solver.inputData[0])
    }

    @ParameterizedTest
    @CsvSource(value = [
        "abcd, abce",
        "xyz, xza",
        "zzz, aaa"
    ])
    @Order(3)
    fun `Count Up in String`(s: String, expected: String) {
        val result = solver.stringCountUp(s).also { it.println() }
        assertThat(result).isEqualTo(expected)
    }

    @ParameterizedTest
    @CsvSource(value = [
        "hijklmmn, true",
        "abbceffg, false",
        "abbcegjk, false",
        "xy, false"
    ])
    @Order(5)
    fun `Rule 1 Test`(s: String, expected: Boolean) {
        val result = solver.rule1Check(s).also { it.println() }
        assertThat(result).isEqualTo(expected)
    }

    @ParameterizedTest
    @CsvSource(value = [
        "hijklmmn, false",
        "hojkxmmn, false",
        "hpjklmmn, false",
        "abbceffg, true",
        "abbcegjk, true"
    ])
    @Order(5)
    fun `Rule 2 Test`(s: String, expected: Boolean) {
        val result = solver.rule2Check(s).also { it.println() }
        assertThat(result).isEqualTo(expected)
    }

    @ParameterizedTest
    @CsvSource(value = [
        "hijklmmn, false",
        "abbceffg, true",
        "abbcegjk, false"
    ])
    @Order(5)
    fun `Rule 3 Test`(s: String, expected: Boolean) {
        val result = solver.rule3Check(s).also { it.println() }
        assertThat(result).isEqualTo(expected)
    }

    @Test
    @Order(7)
    fun `Solves Part 1`() {
        val expected = listOf("abcdffaa", "ghjaabcc")
        for (i in 0..1) {
            solver.initialString = solver.inputData[i]
            val result = solver.solvePart1().also { it.println() }
            assertThat(result).isEqualTo(expected[i])
        }
    }
}
