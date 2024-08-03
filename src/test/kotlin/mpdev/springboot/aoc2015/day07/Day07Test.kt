package mpdev.springboot.aoc2015.day07

import mpdev.springboot.aoc2015.input.InputDataReader
import mpdev.springboot.aoc2015.solutions.day07.DigitalCircuit
import mpdev.springboot.aoc2015.utils.println
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class Day07Test {

    private val day = 7                                    ///////// Update this for a new dayN test
    private lateinit var solver: DigitalCircuit         ///////// Update this for a new dayN test
    private val inputDataReader = InputDataReader("src/test/resources/inputdata/input")

    @BeforeEach
    fun setup() {
        solver = DigitalCircuit(inputDataReader)
        solver.initialize()
    }

    @Test
    @Order(1)
    fun `Sets Day correctly`() {
        assertThat(solver.day).isEqualTo(day)
    }

    @Test
    @Order(2)
    fun `Reads Input and sets up Circuit`() {
        solver.gatesMap.values.forEach { it.println() }
        assertThat(solver.gatesMap.size).isEqualTo(8)
    }

    @ParameterizedTest
    @CsvSource(value = [
        "d, 72", "e, 507", "f, 492", "g, 114", "h, 65412", "i, 65079"
    ])
    @Order(4)
    fun `Calculates output`(rootNode: String, expected: Int) {
        DigitalCircuit.circuitRoot = rootNode
        solver.initialize()
        val result = solver.calculateOutput(solver.circuit, mutableMapOf()).also { it.println() }
        assertThat(result).isEqualTo(expected)
    }

    @Test
    @Order(5)
    fun `Solves Part 1`() {
        val result = solver.solvePart1().also { it.println() }
        assertThat(result).isEqualTo(507)
    }

    @Test
    @Order(6)
    fun `Solves Part 2`() {
        val result = solver.solvePart2().also { it.println() }
        assertThat(result).isEqualTo(1001996)
    }
}
