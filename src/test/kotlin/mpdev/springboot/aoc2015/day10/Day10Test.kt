package mpdev.springboot.aoc2015.day10

import mpdev.springboot.aoc2015.input.InputFileReader
import mpdev.springboot.aoc2015.solutions.day10.NumericTransformer
import mpdev.springboot.aoc2015.utils.println
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test

class Day10Test {

    private val day = 10                                    ///////// Update this for a new dayN test
    private var solver: NumericTransformer         ///////// Update this for a new dayN test
    private val inputDataReader = InputFileReader("src/test/resources/inputdata/")

    init {
        solver = NumericTransformer(inputDataReader)
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
    fun `Executes Look and Say`() {
        val expected = listOf("312211", "11", "21",  "1211", "111221")
        for (i in solver.inputData.indices) {
            val result = solver.lookAndSay(solver.inputData[i].toCharArray().toList()).also { it.println() }
            assertThat(result).isEqualTo(expected[i].toCharArray().toList())
        }
    }
}
