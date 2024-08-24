package mpdev.springboot.aoc2015.day18

import mpdev.springboot.aoc2015.input.InputFileReader
import mpdev.springboot.aoc2015.solutions.day18.LightState
import mpdev.springboot.aoc2015.solutions.day18.LightsAnimation
import mpdev.springboot.aoc2015.utils.Grid
import mpdev.springboot.aoc2015.utils.println
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test

class Day18Test {

    private val day = 18                                    ///////// Update this for a new dayN test
    private lateinit var solver: LightsAnimation         ///////// Update this for a new dayN test
    private val inputDataReader = InputFileReader("src/test/resources/inputdata/")

    @BeforeEach
    fun setup() {
        solver = LightsAnimation(inputDataReader)
        solver.initialize()
        solver.debug = true
    }

    @Test
    @Order(1)
    fun `Sets Day correctly`() {
        assertThat(solver.day).isEqualTo(day)
    }

    @Test
    @Order(2)
    fun `Reads initial light state and sets up Grid`() {
        solver.grid.print()
        assertThat(solver.grid.countOf(LightState.ON)).isEqualTo(15)
    }

    @Test
    @Order(3)
    fun `Animates Lights`() {
        val expectedOutput = listOf(
            "......",
            "......",
            "..##..",
            "..##..",
            "......",
            "......"
        )
        val expectedGid = Grid(expectedOutput, LightState.mapper, border = 0)
        println("Initial")
        var currentGrid = solver.grid.also { it.print() }
        repeat(4) {
            println(it + 1)
            currentGrid = solver.animate(currentGrid).also { g -> g.print() }
        }
        assertThat(currentGrid.getDataPoints()).isEqualTo(expectedGid.getDataPoints())
    }

    @Test
    @Order(4)
    fun `Solves Part 1`() {
        solver.repeatAnimation = 4
        val result = solver.solvePart1().also { it.println() }
        assertThat(result).isEqualTo(4)
    }

    @Test
    @Order(5)
    fun `Animates Lights 2`() {
        val expectedOutput = listOf(
            "##.###",
            ".##..#",
            ".##...",
            ".##...",
            "#.#...",
            "##...#"
        )
        val expectedGid = Grid(expectedOutput, LightState.mapper, border = 0)
        println("Initial")
        var currentGrid = solver.grid.also { it.print() }
        repeat(5) {
            println(it + 1)
            currentGrid = solver.animate(currentGrid, 2).also { g -> g.print() }
        }
        assertThat(currentGrid.getDataPoints()).isEqualTo(expectedGid.getDataPoints())
    }

    @Test
    @Order(6)
    fun `Solves Part 2`() {
        solver.repeatAnimation = 5
        val result = solver.solvePart2().also { it.println() }
        assertThat(result).isEqualTo(17)
    }
}
