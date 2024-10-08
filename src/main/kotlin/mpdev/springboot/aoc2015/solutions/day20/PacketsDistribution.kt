package mpdev.springboot.aoc2015.solutions.day20

import mpdev.springboot.aoc2015.input.InputFileReader
import mpdev.springboot.aoc2015.solutions.PuzzleSolver
import mpdev.springboot.aoc2015.utils.PrimeNumbers
import org.springframework.stereotype.Component
import kotlin.math.ceil
import kotlin.math.sqrt

@Component
class PacketsDistribution(inputFileReader: InputFileReader): PuzzleSolver(inputFileReader, 20) {

    private final val target = inputData[0].toInt()

    override fun initialize() {
        PrimeNumbers.eratosthenesSieve(10_000_000)
    }

    fun findSumOfDivisors(number: Int) = PrimeNumbers.divisors(number).sum()

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
        while (findSumOfDivisors(n) < upperLimit)
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

