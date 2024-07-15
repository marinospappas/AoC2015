package mpdev.springboot.aoc2015.solutions.day03

import mpdev.springboot.aoc2015.input.InputDataReader
import mpdev.springboot.aoc2015.solutions.PuzzleSolver
import mpdev.springboot.aoc2015.utils.GridUtils
import mpdev.springboot.aoc2015.utils.Point
import org.springframework.stereotype.Component

@Component
class PackageDelivery(inputDataReader: InputDataReader): PuzzleSolver(inputDataReader, 3) {

    lateinit var directions: List<GridUtils.Direction>

    override fun initialize() {
        directions = inputData[0].toList().map { GridUtils.Direction.of(it) }
    }

    fun deliveryRoute1(): List<Point> {
        val route = mutableListOf<Point>().also { list -> list.add(Point(0,0)) }
        directions.forEach { route.add(route.last() + it.increment) }
        return route
    }

    fun deliveryRoute2(): List<Point> {
        val route: Array<MutableList<Point>> = Array(2) { mutableListOf<Point>().also { list -> list.add(Point(0,0)) } }
        for (indx in directions.indices)
            route[indx % 2].add(route[indx % 2].last() + directions[indx].increment)
        return route[0] + route[1]
    }

    override fun solvePart1() = deliveryRoute1().distinct().size

    override fun solvePart2() = deliveryRoute2().distinct().size
}