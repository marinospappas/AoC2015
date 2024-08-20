package mpdev.springboot.aoc2015.day20

import mpdev.springboot.aoc2015.input.InputDataReader
import mpdev.springboot.aoc2015.solutions.day20.PacketsDistribution
import mpdev.springboot.aoc2015.utils.PrimeNumbers
import mpdev.springboot.aoc2015.utils.divisors
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
    private val inputDataReader = InputDataReader("src/main/resources/inputdata/input")

    @BeforeEach
    fun setup() {
        solver = PacketsDistribution(inputDataReader)
        solver.initialize()
    }

    @Test
    @Order(1)
    fun `Sets Day correctly`() {
        assertThat(solver.day).isEqualTo(day)
        PrimeNumbers.eratosthenesSieve(1000)
        PrimeNumbers.primes.println()
    }

    @Test
    @Order(1)
    fun `Calculates Prime Numbers`() {
        val elapsed = measureTimeMillis {
            PrimeNumbers.eratosthenesSieve0(100_000)
        }
        PrimeNumbers.primes.also { it.println() }
        kotlin.io.println()
        elapsed.println()
        PrimeNumbers.primeFactors(677376).println()
        solver.sigma(677376).println()
        677376.divisors().sum().println()
        PrimeNumbers.divisors(677376).sum().println()
        PrimeNumbers.divisors2(677376).sum().println()
        677376.divisors().sorted().println()
        PrimeNumbers.divisors(677376).sorted().println()
        PrimeNumbers.divisors2(677376).sorted().println()
    }

    @ParameterizedTest
    @CsvSource(value = ["1", "2", "3", "4", "5", "6", "7", "8", "9"])
    @Order(2)
    fun `Calculates number of packages`(n: Int) {
        solver.findTotalPackets(n).println()
    }
}
