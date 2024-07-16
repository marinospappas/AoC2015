package mpdev.springboot.aoc2015.solutions.day04

import mpdev.springboot.aoc2015.input.InputDataReader
import mpdev.springboot.aoc2015.solutions.PuzzleSolver
import mpdev.springboot.aoc2015.utils.*
import org.springframework.stereotype.Component
import kotlin.experimental.and

@Component
class AdventCoinMining(inputDataReader: InputDataReader): PuzzleSolver(inputDataReader, 4) {

    var key = inputData[0]

    override fun initialize() {
    }

    override fun solvePart1(): Int {
        (0..Int.MAX_VALUE).forEach { i ->
            val md5Chcksum = Md5.checksum("$key$i")
            if (md5Chcksum[0] == byte0 && md5Chcksum[1] == byte0 && (md5Chcksum[2] and 0xf0.toByte()) == byte0)
                return i
        }
        throw AocException("no solution for day $day part 1")
    }

    override fun solvePart2(): Int {
        (0..Int.MAX_VALUE).forEach { i ->
            val md5Chcksum = Md5.checksum("$key$i")
            if (md5Chcksum[0] == byte0 && md5Chcksum[1] == byte0 && md5Chcksum[2] == byte0)
                return i
        }
        throw AocException("no solution for day $day part 1")
    }

    companion object {
        const val byte0 = 0.toByte()
    }
}