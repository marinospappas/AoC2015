package mpdev.springboot.aoc2015.day19

import mpdev.springboot.aoc2015.input.InputFileReader
import mpdev.springboot.aoc2015.solutions.day19.Element
import mpdev.springboot.aoc2015.solutions.day19.Transformations
import mpdev.springboot.aoc2015.utils.println
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import java.nio.file.Files
import kotlin.io.path.Path

class Day19Test {

    private val day = 19                                    ///////// Update this for a new dayN test
    private var solver: Transformations         ///////// Update this for a new dayN test
    private val inputDataReader = InputFileReader("src/test/resources/inputdata/")

    init {
        solver = Transformations(inputDataReader)
    }

    @BeforeEach
    fun setup() {
        inputDataReader.setTestInput(day, Files.readAllLines(Path("src/test/resources/inputdata/input${day}.txt")))
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
    fun `Reverse-transforms molecule`() {
        inputDataReader.setTestInput(day, Files.readAllLines(Path("src/main/resources/inputdata/input${day}.txt")))
        val solver1 = Transformations(inputDataReader)
        solver1.initialize()
        solver1.findReverseTransformations()
        solver1.transformationCount.println()
    }

    @Test
    @Order(7)
    fun `Solves Part 2`() {
        inputDataReader.setTestInput(day, Files.readAllLines(Path("src/main/resources/inputdata/input${day}.txt")))
        val solver1 = Transformations(inputDataReader)
        solver1.initialize()
        val result = solver1.solvePart2().also { it.println() }
        assertThat(result).isEqualTo(195)
    }
}
