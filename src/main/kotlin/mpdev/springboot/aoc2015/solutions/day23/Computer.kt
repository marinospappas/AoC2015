package mpdev.springboot.aoc2015.solutions.day23

import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import mpdev.springboot.aoc2015.input.InputFileReader
import mpdev.springboot.aoc2015.solutions.PuzzleSolver
import mpdev.springboot.aoc2015.utils.aocvm.AocVm
import mpdev.springboot.aoc2015.utils.aocvm.InstructionSet
import mpdev.springboot.aoc2015.utils.aocvm.OpResultCode
import mpdev.springboot.aoc2015.utils.aocvm.ParamReadWrite.R
import mpdev.springboot.aoc2015.utils.aocvm.ParamReadWrite.W
import org.springframework.stereotype.Component

@Component
class Computer(inputFileReader: InputFileReader): PuzzleSolver(inputFileReader, 23) {

    lateinit var aocVm: AocVm

    override fun initialize() {
        initialiseOpCodes()
        aocVm = AocVm(inputData
            .toMutableList().also { it.add(0, "in a") }.also { it.add("out b") }
            .map { it.replace(", ", " ") }
        )
    }

    override fun solvePart1(): Int {
        var result: Int
        runBlocking {
            aocVm.sendInputToProgram(0)
            val job = launch { aocVm.runProgram() }
            aocVm.waitProgram(job)
            result = aocVm.getFinalOutputFromProgram().last()
        }
        return result
    }

    override fun solvePart2(): Int {
        var result: Int
        runBlocking {
            aocVm.sendInputToProgram(1)
            val job = launch { aocVm.runProgram() }
            aocVm.waitProgram(job)
            result = aocVm.getFinalOutputFromProgram().last()
        }
        return result
    }

    companion object {
        fun initialiseOpCodes() {
            InstructionSet.opCodesList["hlf"] = InstructionSet.OpCode("hlf", 2, listOf(R, W))
            { a -> Pair(OpResultCode.SET_MEMORY, listOf(a[1], a[0] as Long / 2L)) }
            InstructionSet.opCodesList["tpl"] = InstructionSet.OpCode("tpl", 2, listOf(R, W))
            { a -> Pair(OpResultCode.SET_MEMORY, listOf(a[1], a[0] as Long * 3L)) }
            InstructionSet.opCodesList["jie"] = InstructionSet.OpCode("jie", 2, listOf(R, R))
            { a -> if (a[0] as Long % 2L == 0L) Pair(OpResultCode.INCR_PC, listOf(a[1] as Long)) else
                Pair(OpResultCode.NONE, emptyList()) }
            InstructionSet.opCodesList["jio"] = InstructionSet.OpCode("jio", 2, listOf(R, R))
            { a -> if (a[0] as Long == 1L) Pair(OpResultCode.INCR_PC, listOf(a[1] as Long)) else
                Pair(OpResultCode.NONE, emptyList()) }
        }
    }
}
