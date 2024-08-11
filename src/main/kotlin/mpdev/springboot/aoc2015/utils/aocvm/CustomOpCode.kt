package mpdev.springboot.aoc2015.utils.aocvm

interface CustomOpCode {
    fun execute(instructionList: MutableList< Pair<InstructionSet.OpCode, List<Any>> >, pc: Int, params: List<Any>)
}