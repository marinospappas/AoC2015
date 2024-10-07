package mpdev.springboot.aoc2015.day19

import mpdev.springboot.aoc2015.input.InputFileReader
import mpdev.springboot.aoc2015.solutions.day19.Element
import mpdev.springboot.aoc2015.solutions.day19.Transformations
import mpdev.springboot.aoc2015.utils.println
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test

class Day19Test {

    private val day = 19                                    ///////// Update this for a new dayN test
    private var solver: Transformations         ///////// Update this for a new dayN test
    private val inputDataReader = InputFileReader("src/main/resources/inputdata/")

    init {
        solver = Transformations(inputDataReader)
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
    fun `Reads transformation rules and main molecule`() {
        solver.rules.forEach { it.println() }
        println("Rn - Ar rules")
        solver.rulesRnAr.forEach { it.println() }
        println("non - Rn - Ar rules")
        solver.rulesNonRnAr.forEach { it.println() }
        println(solver.mainMolecule)
        println(solver.mainMolecule.toString(0))
        assertThat(solver.rules).hasSize(3)
        assertThat(solver.mainMolecule.elements).isEqualTo(listOf(Element.H, Element.O, Element.H))
    }

    @Test
    @Order(3)
    fun `Finds all possible transformations`() {
        val result = solver.findAllTransformations().also { it.println() }
        assertThat(result).hasSize(4)
    }

    @Test
    @Order(4)
    fun `Solves Part 1`() {
        val result = solver.solvePart1().also { it.println() }
        assertThat(result).isEqualTo(4)
    }

    @Test
    @Order(5)
    fun `Finds last Rn-Ar part`() {
        val result = solver.mainMolecule.indexOfLastRnAr().also { it.println() }
        println(solver.mainMolecule.toString(result.first - 1, result.second))
        assertThat(result).isEqualTo(Pair(7, 10))
    }

    @Test
    @Order(6)
    fun `Reverse-transforms molecule`() {
        val result = solver.reverseTransformation()
    }

    @Test
    @Order(7)
    fun `Solves Part 2`() {
        val result = solver.solvePart2().also { it.println() }
        assertThat(result).isEqualTo(17)
    }
}
