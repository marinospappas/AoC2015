package mpdev.springboot.aoc2015.day16

import mpdev.springboot.aoc2015.input.InputFileReader
import mpdev.springboot.aoc2015.solutions.day16.IdentifyItem
import mpdev.springboot.aoc2015.utils.println
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test

class Day16Test {

    private val day = 16                                   ///////// Update this for a new dayN test
    private var solver: IdentifyItem         ///////// Update this for a new dayN test
    private val inputDataReader = InputFileReader("src/test/resources/inputdata/")

    init {
        solver = IdentifyItem(inputDataReader)
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
    fun `Reads Input and sets list of items up`() {
        solver.items.forEach { it.println() }
        assertThat(solver.items.size).isEqualTo(500)
    }

    @Test
    @Order(5)
    fun `Solves Part 1`() {
        val matchingItems = solver.items
            .filter { it.matchPart1(IdentifyItem.inputAttributes.map { at -> Pair(at.first, at.second) }) }
            .also { it.println() }
        assertThat(matchingItems.size).isEqualTo(1)
    }

    @Test
    @Order(6)
    fun `Solves Part 2`() {
        val matchingItems = solver.items
            .filter { it.matchPart2(IdentifyItem.inputAttributes.map { at -> Pair(at.first, at.third) }) }
            .also { it.println() }
        assertThat(matchingItems.size).isEqualTo(1)
    }
}
