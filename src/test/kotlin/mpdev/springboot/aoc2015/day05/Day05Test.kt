package mpdev.springboot.aoc2015.day05

import mpdev.springboot.aoc2015.input.InputFileReader
import mpdev.springboot.aoc2015.solutions.day05.StringsInspection
import mpdev.springboot.aoc2015.utils.containsSeqTwice
import mpdev.springboot.aoc2015.utils.isVowel
import mpdev.springboot.aoc2015.utils.println
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class Day05Test {

    private val day = 5                                     ///////// Update this for a new dayN test
    private var solver: StringsInspection         ///////// Update this for a new dayN test
    private val inputDataReader = InputFileReader("src/test/resources/inputdata/")

    init {
        solver = StringsInspection(inputDataReader)
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
    fun `Reads Input`() {
        solver.inputData.println()
        assertThat(solver.inputData.size).isEqualTo(5)
    }

    @Test
    @Order(3)
    fun `Solves Part 1 Criteria`() {
        val expected = listOf(true, true, false, false, false)
        for (i in solver.inputData.indices) {
            val s = solver.inputData[i]
            val result = (s.count { c -> c.isVowel() } >= 3 && s.containsSeqTwice(1, 0) && !solver.containsUnwanted(s)).also { it.println() }
            assertThat(result).isEqualTo(expected[i])
        }
    }

    @ParameterizedTest
    @CsvSource(value = [
        "xyxy, true", "aaa, false", "abbccb, false", "aabcdefgaax, true"
    ])
    @Order(3)
    fun `Solves Part 2 Criterion 1`(s: String, expected: Boolean) {
        val result = (s.containsSeqTwice(2, -1)).also { it.println() }
        assertThat(result).isEqualTo(expected)
    }

    @ParameterizedTest
    @CsvSource(value = [
        "xyx, true", "abcdefeghi, true", "aaa, true", "abc, false"
    ])
    @Order(3)
    fun `Solves Part 2 Criterion 2`(s: String, expected: Boolean) {
        val result = (s.containsSeqTwice(1, 1)).also { it.println() }
        assertThat(result).isEqualTo(expected)
    }

    @Test
    @Order(4)
    fun `Solves Part 1`() {
        val result = solver.solvePart1().also { it.println() }
        assertThat(result).isEqualTo(2)
    }

    @ParameterizedTest
    @CsvSource(value = [
        "qjhvhtzxzqqjkmpb, true", "xxyxx, true", "uurcxstgmygtbstg, false", "ieodomkazucvgmuy, false"
    ])
    @Order(6)
    fun `Solves Part 2`(s: String, expected: Boolean) {
        val result = (s.containsSeqTwice(2, -1) && s.containsSeqTwice(1, 1)).also { it.println() }
        assertThat(result).isEqualTo(expected)
    }
}
