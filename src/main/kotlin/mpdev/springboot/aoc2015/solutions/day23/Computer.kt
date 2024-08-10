package mpdev.springboot.aoc2015.solutions.day23

import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import mpdev.springboot.aoc2015.input.InputDataReader
import mpdev.springboot.aoc2015.solutions.PuzzleSolver
import mpdev.springboot.aoc2015.utils.InstructionSet
import mpdev.springboot.aoc2015.utils.OpResultCode
import mpdev.springboot.aoc2015.utils.ParamReadWrite.*
import mpdev.springboot.aoc2015.utils.Program
import org.springframework.stereotype.Component

@Component
class Computer(inputDataReader: InputDataReader): PuzzleSolver(inputDataReader, 23) {

    lateinit var program: Program

    override fun initialize() {
        InstructionSet.opCodesList["hlf"] = InstructionSet.OpCode("hlf", 2, listOf(R, W))
            { a -> Pair(OpResultCode.SET_MEMORY, listOf(a[1], a[0] as Long / 2L)) }
        InstructionSet.opCodesList["tpl"] = InstructionSet.OpCode("tpl", 2, listOf(R, W))
            { a -> Pair(OpResultCode.SET_MEMORY, listOf(a[1], a[0] as Long * 3L)) }
        InstructionSet.opCodesList["jie"] = InstructionSet.OpCode("jie", 2, listOf(R, R))
            { a -> Pair(OpResultCode.INCR_PC, listOf(if (a[0] as Long % 2L == 0L) a[1] as Long else 1L)) }
        InstructionSet.opCodesList["jio"] = InstructionSet.OpCode("jio", 2, listOf(R, R))
            { a -> Pair(OpResultCode.INCR_PC, listOf(if (a[0] as Long == 1L) a[1] as Long else 1L)) }
        program = Program(inputData.map { it.replace(", ", ",").replace(" ", ",") })
    }

    suspend fun runProgram(initReg: Map<String,Long> = emptyMap()): Int {
        runBlocking {
            val job = launch { program.run(initReg) }
            job.join()
        }
        return program.getRegister("b").toInt()
    }

    override fun solvePart1(): Int {
        var result: Int
        runBlocking {
            result = runProgram()
        }
        return result
    }

    override fun solvePart2(): Int {
        var result: Int
        runBlocking {
            result = runProgram(mapOf("a" to 1))
        }
        return result
    }

}
