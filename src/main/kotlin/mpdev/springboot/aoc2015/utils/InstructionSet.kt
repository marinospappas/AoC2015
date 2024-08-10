package mpdev.springboot.aoc2015.utils

import mpdev.springboot.aoc2015.utils.OpResultCode.*
import mpdev.springboot.aoc2015.utils.ParamReadWrite.*
abstract class InstructionSet {

    data class OpCode(val code: String, val numberOfParams: Int, val paramMode: List<ParamReadWrite>, val execute: (List<Any>) -> Pair<OpResultCode, List<Any>>)

    companion object {

        const val MOV = "mov"
        const val ADD = "add"
        const val SUB = "sub"
        const val INC = "inc"
        const val JMP = "jmp"
        const val JNZ = "jnz"
        const val IN = "in"
        const val OUT = "out"
        const val NOP = "nop"
        const val HLT = "hlt"

        val opCodesList: MutableMap<String, OpCode> = mutableMapOf(
            MOV to OpCode(MOV, 3, listOf(R, R, W)) { a -> Pair(SET_MEMORY, listOf(a[0], a[1] as Long)) },
            ADD to OpCode(ADD, 3, listOf(R, R, W)) { a -> Pair(SET_MEMORY, listOf(a[2], a[0] as Long + a[1] as Long)) },
            SUB to OpCode(SUB, 3, listOf(R, R, W)) { a -> Pair(SET_MEMORY, listOf(a[2], a[0] as Long - a[1] as Long)) },
            INC to OpCode(INC, 2, listOf(R, W)) { a -> Pair(SET_MEMORY, listOf(a[1], a[0] as Long + 1L)) },
            JMP to OpCode(JMP, 1, listOf(R)) { a -> Pair(INCR_PC, listOf(a[0] as Long)) },
            JNZ to OpCode(JNZ, 2, listOf(R, R)) { a -> Pair(INCR_PC, listOf(if (a[0] as Long == 0L) a[1] as Long else 1L)) },
            IN to OpCode(IN, 1, listOf(W)) { a -> Pair(INPUT, listOf(a[0])) },
            OUT to OpCode(OUT, 1, listOf(R)) { a -> Pair(OUTPUT, listOf(a[0] as Long)) },
            NOP to OpCode(NOP, 0, listOf()) { a -> Pair(NONE, listOf()) },
            HLT to OpCode(HLT, 0, listOf()) { a -> Pair(EXIT, listOf()) }
        )

        fun getOpCode(opCode: String) = opCodesList[opCode] ?: throw AocException("no OpCode found for $opCode")
    }
}

enum class ParamReadWrite {
    R, W
}
enum class OpResultCode {
    SET_MEMORY, INCR_PC, INPUT, OUTPUT, EXIT, NONE
}