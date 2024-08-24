package mpdev.springboot.aoc2015.solutions.day02

import kotlinx.serialization.Serializable
import mpdev.springboot.aoc2015.input.InputFileReader
import mpdev.springboot.aoc2015.solutions.PuzzleSolver
import mpdev.springboot.aoc2015.utils.AocInClass
import mpdev.springboot.aoc2015.utils.AocInField
import mpdev.springboot.aoc2015.utils.InputUtils
import org.springframework.stereotype.Component
import kotlin.math.min

@Component
class PresentWrapping(inputFileReader: InputFileReader): PuzzleSolver(inputFileReader, 2) {

    lateinit var parcels: List<Parcel>

    override fun initialize() {
        val aocInputList: List<AoCInput> = InputUtils(AoCInput::class.java).readAoCInput(inputData)
        parcels = aocInputList.map { item -> Parcel(item.w, item.l, item.h) }
    }

    override fun solvePart1() = parcels.sumOf { it.area() + it.extra() }

    override fun solvePart2() = parcels.sumOf { it.ribbon() }
}

data class Parcel(val w: Int, val l: Int, val h: Int) {
    fun area(): Int = 2*w*l + 2*l*h + 2*h*w
    fun extra() = min(w*l, min(l*h, h*w))
    fun ribbon() = min(2*(w+l), min(2*(l+h), 2*(h+w))) + w*l*h
}

@Serializable
@AocInClass(delimiters = ["x"])
data class AoCInput(
    @AocInField(0) val w: Int,
    @AocInField(1) val l: Int,
    @AocInField(2) val h: Int
)