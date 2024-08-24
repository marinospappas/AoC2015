package mpdev.springboot.aoc2015.solutions.day10

import mpdev.springboot.aoc2015.input.InputFileReader
import mpdev.springboot.aoc2015.solutions.PuzzleSolver
import org.springframework.stereotype.Component

@Component
class NumericTransformer(inputFileReader: InputFileReader): PuzzleSolver(inputFileReader, 10) {

    val startSequence =  inputData[0].toCharArray().toList()

    fun lookAndSay(s: List<Char>): List<Char> {
        val result = mutableListOf<Char>()
        var subSeq = mutableListOf<Char>()
        for (i in s.indices) {
            subSeq += s[i]
            if (i == s.lastIndex || s[i + 1] != s[i]) {
                result.addAll(lookAndSaySubSeq(subSeq))
                subSeq = mutableListOf()
            }
        }
        return result
    }

    fun lookAndSaySubSeq(s: List<Char>) = s.size.toString().toCharArray().toList() + s[0]

    lateinit var sequencePart1: List<Char>

    override fun solvePart1(): Int {
        var sequence = startSequence
        repeat(40) { sequence = lookAndSay(sequence) }
        sequencePart1 = sequence
        return sequence.size
    }

    override fun solvePart2(): Int {
        var sequence = sequencePart1
        repeat(10) { sequence = lookAndSay(sequence) }
        return sequence.size
    }

}
