package mpdev.springboot.aoc2015.day14

import mpdev.springboot.aoc2015.input.InputDataReader
import mpdev.springboot.aoc2015.solutions.day14.SpeedRace
import mpdev.springboot.aoc2015.utils.println
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class Day14Test {

    private val day = 14                                   ///////// Update this for a new dayN test
    private lateinit var solver: SpeedRace         ///////// Update this for a new dayN test
    private val inputDataReader = InputDataReader("src/test/resources/inputdata/input")

    @BeforeEach
    fun setup() {
        solver = SpeedRace(inputDataReader)
        solver.initialize()
    }

    @Test
    @Order(1)
    fun `Sets Day correctly`() {
        assertThat(solver.day).isEqualTo(day)
    }

    @Test
    @Order(2)
    fun `Reads Input and sets race up`() {
        solver.racers.forEach { it.println() }
        assertThat(solver.racers.size).isEqualTo(2)
    }

    @ParameterizedTest
    @CsvSource(value = [
        "0, 1000, 1120",
        "1, 1000, 1056"
    ])
    @Order(3)
    fun `Calculates distance covered`(index: Int, time: Int, expected: Int) {
        val result = solver.racers[index].distanceCovered(time).also { it.println() }
        assertThat(result).isEqualTo(expected)
    }

    @Test
    @Order(5)
    fun `Solves Part 1`() {
        solver.raceTime = 1000
        val result = solver.solvePart1().also { it.println() }
        assertThat(result).isEqualTo(1120)
    }

    @Test
    @Order(6)
    fun `Solves Part 2`() {
        solver.raceTime = 1000
        val result = solver.solvePart2().also { it.println() }
        assertThat(result).isEqualTo(689)
    }
}
