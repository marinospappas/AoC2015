package mpdev.springboot.aoc2015.solutions.day23

import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import mpdev.springboot.aoc2015.input.InputDataReader
import mpdev.springboot.aoc2015.solutions.PuzzleSolver
import mpdev.springboot.aoc2015.utils.aocvm.AocVm
import mpdev.springboot.aoc2015.utils.aocvm.InstructionSet
import mpdev.springboot.aoc2015.utils.aocvm.OpResultCode
import mpdev.springboot.aoc2015.utils.aocvm.ParamReadWrite.*
import mpdev.springboot.aoc2015.utils.aocvm.Program
import org.springframework.stereotype.Component

@Component
class Computer(inputDataReader: InputDataReader): PuzzleSolver(inputDataReader, 23) {

    lateinit var program: Program
    lateinit var aocVm: AocVm

    override fun initialize() {
        InstructionSet.opCodesList["hlf"] = InstructionSet.OpCode("hlf", 2, listOf(R, W))
            { a -> Pair(OpResultCode.SET_MEMORY, listOf(a[1], a[0] as Long / 2L)) }
        InstructionSet.opCodesList["tpl"] = InstructionSet.OpCode("tpl", 2, listOf(R, W))
            { a -> Pair(OpResultCode.SET_MEMORY, listOf(a[1], a[0] as Long * 3L)) }
        InstructionSet.opCodesList["jie"] = InstructionSet.OpCode("jie", 2, listOf(R, R))
            { a -> Pair(OpResultCode.INCR_PC, listOf(if (a[0] as Long % 2L == 0L) a[1] as Long else 1L)) }
        InstructionSet.opCodesList["jio"] = InstructionSet.OpCode("jio", 2, listOf(R, R))
            { a -> Pair(OpResultCode.INCR_PC, listOf(if (a[0] as Long == 1L) a[1] as Long else 1L)) }
        aocVm = AocVm(inputData.map { it.replace(", ", ",").replace(" ", ",") })
    }

    override fun solvePart1(): Int {
        var result: Int
        runBlocking {
            val job = launch { aocVm.runProgram() }
            aocVm.waitProgram(job)
            result = aocVm.getProgramRegister("b")
        }
        return result
    }

    override fun solvePart2(): Int {
        var result: Int
        runBlocking {
            val job = launch { aocVm.runProgram(mapOf("a" to 1)) }
            aocVm.waitProgram(job)
            result = aocVm.getProgramRegister("b")
        }
        return result
    }

}
