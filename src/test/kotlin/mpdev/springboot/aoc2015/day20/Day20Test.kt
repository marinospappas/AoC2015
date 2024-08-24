package mpdev.springboot.aoc2015.day20

import mpdev.springboot.aoc2015.input.InputFileReader
import mpdev.springboot.aoc2015.solutions.day20.PacketsDistribution
import mpdev.springboot.aoc2015.utils.PrimeNumbers
import mpdev.springboot.aoc2015.utils.divisors
import mpdev.springboot.aoc2015.utils.print
import mpdev.springboot.aoc2015.utils.println
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import kotlin.system.measureTimeMillis

class Day20Test {

    private val day = 20                                    ///////// Update this for a new dayN test
    private lateinit var solver: PacketsDistribution         ///////// Update this for a new dayN test
    private val inputDataReader = InputFileReader("src/test/resources/inputdata/")

    @BeforeEach
    fun setup() {
        solver = PacketsDistribution(inputDataReader)
        solver.initialize()
    }

    @Test
    @Order(1)
    fun `Sets Day correctly`() {
        assertThat(solver.day).isEqualTo(day)
    }

    @Test
    @Order(1)
    fun `Calculates Prime Numbers`() {
        val elapsed = measureTimeMillis {
            PrimeNumbers.eratosthenesSieve0(1_000_000)
        }
        PrimeNumbers.primes.subList(0, 100).print(); println(" ...")
        println("time to calculate primes up to 1,000,000: $elapsed msec")
        assertThat(PrimeNumbers.primes).hasSize(78498)
    }

    @Test
    @Order(1)
    fun `Finds Divisors using Prime factors`() {
        PrimeNumbers.eratosthenesSieve0(1_000_000)
        PrimeNumbers.primeFactors(677376).println()
        val divisors = PrimeNumbers.divisors(677376).sorted().also { it.println() }
        val divisors2 = PrimeNumbers.divisors2(677376).sorted().also { it.println() }
        val expDivisors = 677376.divisors().toList().also { it.println() }
        PrimeNumbers.sigma(677376).println()
        assertThat(divisors).isEqualTo(expDivisors)
        assertThat(divisors2).isEqualTo(expDivisors)
        assertThat(PrimeNumbers.sigma(677376)).isEqualTo(expDivisors.sum())
    }

    @ParameterizedTest
    @CsvSource(value = ["1, 1", "2, 3", "3, 4", "4, 7", "5, 6", "6, 12", "7, 8", "8, 15", "9, 13"])
    @Order(2)
    fun `Calculates sums of divisors`(n: Int, expected: Int) {
        PrimeNumbers.divisors(n).print(); print(" ")
        assertThat(solver.findSumOfDivisors(n).also { it.println() }).isEqualTo(expected)
        assertThat(PrimeNumbers.sigma(n)).isEqualTo(expected)
    }
}
