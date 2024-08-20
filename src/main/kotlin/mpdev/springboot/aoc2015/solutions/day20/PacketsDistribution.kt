package mpdev.springboot.aoc2015.solutions.day20

import mpdev.springboot.aoc2015.input.InputDataReader
import mpdev.springboot.aoc2015.solutions.PuzzleSolver
import mpdev.springboot.aoc2015.utils.PrimeNumbers
import org.springframework.stereotype.Component
import kotlin.math.ceil
import kotlin.math.pow
import kotlin.math.sqrt

@Component
class PacketsDistribution(inputDataReader: InputDataReader): PuzzleSolver(inputDataReader, 20) {

    private final val target = inputData[0].toInt()

    override fun initialize() {
        PrimeNumbers.eratosthenesSieve(10_000_000)
    }
    fun sigma(n: Int): Int {
        var s = 1
        val primeF = PrimeNumbers.primeFactors(n)
        for (entry in primeF.entries) {
            s *= ((entry.key.toDouble().pow(entry.value + 1).toInt() - 1) / (entry.key - 1))
        }
        return s
    }

    fun findTotalPackets(number: Int) = PrimeNumbers.divisors(number).sum()

    fun findTotalPackets2(number: Int): Int {
        var result = 0
        val n = ceil(sqrt(number.toDouble())).toInt()
        for (i in 1 .. n) {
            if (number % i == 0) {
                if( i <= 50) {
                    result += number/i
                }
                if (number/i <= 50) {
                    result += i
                }
            }

        }
        return result * 11
    }

    override fun solvePart1(): Int {
        var n = 1
        val upperLimit = target / 10
        while (findTotalPackets(n) < upperLimit)
            ++n
        return n
    }

    override fun solvePart2(): Int {
        var n = 1
        while (findTotalPackets2(n) < target)
            ++n
        return n
    }
}

