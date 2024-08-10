package mpdev.springboot.aoc2015.utils

import kotlinx.coroutines.channels.Channel
import mpdev.springboot.aoc2015.utils.InstructionSet.Companion.getOpCode
import mpdev.springboot.aoc2015.utils.OpResultCode.*

class Program(prog: List<String>, private val ioChannel: List<Channel<Long>> = listOf(), val debug: Boolean = false) {

    private val instructionList: MutableList< Pair<InstructionSet.OpCode, List<Any>> > = if (prog[0].equals("#test", true))
        mutableListOf()
    else
        prog.map { it.split(",") }
            .map { Pair(getOpCode(it[0]), it.subList(1, it.size).map { v -> v.toIntOrString() }) }
            .toMutableList()

    private val registers = mutableMapOf<String,Long>()

    suspend fun run(initReg: Map<String, Long> = emptyMap(), maxCount: Int = Int.MAX_VALUE) {
        var pc = 0
        var outputCount = 0
        registers.clear()
        initReg.forEach { (reg, v) -> registers[reg] = v }
        while (pc <= instructionList.lastIndex && outputCount < maxCount) {
            val (instr, params) = instructionList[pc]
            val (resCode, values) = instr.execute(mapParams(params, instr.paramMode, instr.numberOfParams))
            when (resCode) {
                SET_MEMORY -> registers[values[0] as String] = valueOf(values[1])
                INCR_PC -> pc += valueOf(values[0]).toInt() - 1
                OUTPUT -> { ioChannel[1].send(valueOf(values[0])); ++outputCount }
                INPUT -> { registers[values[0] as String] = ioChannel[0].receive() }
                EXIT -> break
                NONE -> {}
            }
            ++pc
        }
    }

    fun getRegister(reg: String): Long = registers.getOrPut(reg) { 0 }

    private fun valueOf(s: Any) =
        when (s) {
            is Int -> s.toLong()
            is Long -> s
            is String -> registers.getOrPut(s) { 0 }
            else -> throw AocException("unexpected error PROG001 [$s]")
        }

    private fun String.toIntOrString() = try {
            this.toInt()
        } catch (e: Exception) {
            this
        }

    private fun mapParams(params: List<Any>, paramMode: List<ParamReadWrite>, numberOfParams: Int): List<Any> {
        val newParams = mutableListOf<Any>()
        val thisParams = if (numberOfParams > params.size) params + params[0] else params
        thisParams.indices.forEach { i ->
            newParams.add(if (paramMode[i] == ParamReadWrite.R) valueOf(thisParams[i]) else thisParams[i])
        }
        return newParams
    }
}


