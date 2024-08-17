package mpdev.springboot.aoc2015.day24

import mpdev.springboot.aoc2015.input.InputDataReader
import mpdev.springboot.aoc2015.solutions.day24.PackageSorting
import mpdev.springboot.aoc2015.utils.println
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import kotlin.math.ceil

class Day24Test {

    private val day = 24                                    ///////// Update this for a new dayN test
    private lateinit var solver: PackageSorting         ///////// Update this for a new dayN test
    private val inputDataReader = InputDataReader("src/test/resources/inputdata/input")

    @BeforeEach
    fun setup() {
        solver = PackageSorting(inputDataReader)
        solver.initialize()
    }

    @Test
    @Order(1)
    fun `Sets Day correctly`() {
        assertThat(solver.day).isEqualTo(day)
    }

    @Test
    @Order(2)
    fun `Reads packages list`() {
        solver.packageList.println()
        assertThat(solver.packageList).hasSize(10)
        assertThat(solver.packageList.sum()).isEqualTo(60)
    }

    @Test
    @Order(3)
    fun `Finds First combination of packages (recursive)`() {
        val input = listOf(3, 4)
        val expected = listOf(setOf(9, 11), setOf(4, 11))
        for (i in input.indices) {
            val result = mutableListOf<Set<Int>>()
            val numOfGroups = input[i]
            val nMin = ceil(solver.packageList.sum().toDouble() / solver.packageList.max()).toInt()
            solver.sortPackages(
                solver.packageList, numOfGroups = numOfGroups, sumOfGroup = solver.packageList.sum() / numOfGroups,
                nMin = nMin, result
            ).println()
            result.println()
            assertThat(result[0]).isEqualTo(expected[i])
        }
    }

    @Test
    @Order(3)
    fun `Finds First combination of packages (recursive) V2`() {
        val input = listOf(3, 4)
        val expected = listOf(setOf(9, 11), setOf(4, 11))
        for (i in input.indices) {
            solver.combinationsMap.clear()
            val result: MutableList<List<Set<Int>>> = mutableListOf()
            val numOfGroups = input[i]
            val nMin = ceil(solver.packageList.sum().toDouble() / numOfGroups / solver.packageList.max()).toInt()
            solver.sortPackagesV2(
                solver.packageList, numOfGroups = numOfGroups, sumOfGroup = solver.packageList.sum() / numOfGroups,
                nMin = nMin, mutableListOf(), result, 1000
            ).println()
            result.sortedWith(PackageSorting.PackageGroupComparator()).println()
            result.size.println()
            assertThat(result.sortedWith(PackageSorting.PackageGroupComparator())[0][0]).isEqualTo(expected[i])
        }
    }

    @Test
    @Order(4)
    fun `Solves Part 1`() {
        val result = solver.solvePart1().also { it.println() }
        assertThat(result).isEqualTo(99)
    }

    @Test
    @Order(6)
    fun `Solves Part 2`() {
        val result = solver.solvePart2().also { it.println() }
        assertThat(result).isEqualTo(44)
    }
}
