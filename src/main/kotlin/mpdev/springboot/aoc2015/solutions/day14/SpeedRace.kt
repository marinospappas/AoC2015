package mpdev.springboot.aoc2015.solutions.day14

import kotlinx.serialization.Serializable
import mpdev.springboot.aoc2015.input.InputDataReader
import mpdev.springboot.aoc2015.solutions.PuzzleSolver
import mpdev.springboot.aoc2015.utils.*
import org.springframework.stereotype.Component

@Component
class SpeedRace(inputDataReader: InputDataReader): PuzzleSolver(inputDataReader, 14) {

    lateinit var racers: List<Racer>
    var raceTime = 2503

    override fun initialize() {
        val aocInputList: List<AoCInput> = InputUtils(AoCInput::class.java).readAoCInput(inputData)
        racers = aocInputList.map { Racer(it.id, it.speed, it.runningTime, it.restTime) }
    }

    override fun solvePart1() = racers.maxOf { it.distanceCovered(raceTime) }

    override fun solvePart2(): Int {
        for(t in 1 .. raceTime) {
            val distanceOfLeader = racers.maxOf { racer -> racer.distanceCovered(t) }
            racers.filter { racer -> racer.currentDistance == distanceOfLeader }.forEach { it.bonus++ }
        }
        return racers.maxOf { it.bonus }
    }
}

data class Racer(val id: String, val speed: Int, val continuousRaceTime: Int, val restTime: Int) {
    var bonus = 0
    var currentDistance = 0
    fun distanceCovered(t: Int): Int {
        var timeRemaining = t
        var distance = 0
        while (timeRemaining > continuousRaceTime) {
            distance += continuousRaceTime * speed
            timeRemaining -= continuousRaceTime
            timeRemaining = if (timeRemaining <= restTime) 0 else timeRemaining - restTime
        }
        return (distance + timeRemaining * speed).also { currentDistance = it }
    }
}

@Serializable
@AocInClass(delimiters = [" "])
@AocInReplacePatterns([" can fly ", " ", " km/s for ", " ", " seconds, but then must rest for ", " ", " seconds.$", ""])
data class AoCInput(
    // Dancer can fly 16 km/s for 11 seconds, but then must rest for 162 seconds.
    @AocInField val id: String,
    @AocInField val speed: Int,
    @AocInField val runningTime: Int,
    @AocInField val restTime: Int
)