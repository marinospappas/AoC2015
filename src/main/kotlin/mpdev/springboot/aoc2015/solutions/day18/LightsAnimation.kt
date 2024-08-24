package mpdev.springboot.aoc2015.solutions.day18

import mpdev.springboot.aoc2015.input.InputFileReader
import mpdev.springboot.aoc2015.solutions.PuzzleSolver
import mpdev.springboot.aoc2015.utils.Grid
import mpdev.springboot.aoc2015.utils.Point
import org.springframework.stereotype.Component

@Component
class LightsAnimation(inputFileReader: InputFileReader): PuzzleSolver(inputFileReader, 18) {

    var grid = Grid(inputData, LightState.mapper, border = 0, defaultSize = Pair(inputData[0].length, inputData.size))
    var repeatAnimation = 100
    var debug = false

    fun animate(grid: Grid<LightState>, part: Int = 1): Grid<LightState> {
        val (minX, maxX, minY, maxY) = grid.getMinMaxXY()
        if (part == 2)
            grid.getCorners().forEach { p -> grid.setDataPoint(p, LightState.ON) }
        val newGrid = Grid(grid.getDataPoints(), LightState.mapper, border = 0, defaultSize = Pair(maxX-minX+1, maxY-minY+1))
        for (x in minX .. maxX)
            for (y in minY .. maxY) {
                val p = Point(x,y)
                val neighboursOn = grid.getAdjacent(p, true).count { grid.getDataPoint(it) == LightState.ON }
                if (grid.containsDataPoint(p) && grid.getDataPoint(p) == LightState.ON) {
                    if (ruleOff(neighboursOn))
                        newGrid.removeDataPoint(p)
                }
                else
                    if (ruleOn(neighboursOn))
                        newGrid.setDataPoint(p, LightState.ON)
            }
        if (part == 2)
            newGrid.getCorners().forEach { p -> newGrid.setDataPoint(p, LightState.ON) }
        return newGrid
    }

    fun ruleOn(neighboursOn: Int) = neighboursOn == 3

    fun ruleOff(neighboursOn: Int) = neighboursOn < 2 || neighboursOn > 3

    override fun solvePart1(): Int {
        var currentGrid = Grid(grid.getDataPoints(), LightState.mapper, border = 0)
        repeat(repeatAnimation) { currentGrid = animate(currentGrid) }
        if (debug)
            currentGrid.print()
        return currentGrid.countOf(LightState.ON)
    }

    override fun solvePart2(): Int {
        var currentGrid = Grid(grid.getDataPoints(), LightState.mapper, border = 0)
        repeat(repeatAnimation) { currentGrid = animate(currentGrid, 2) }
        if (debug)
            currentGrid.print()
        return currentGrid.countOf(LightState.ON)
    }
}

enum class LightState(val value: Char) {
    ON('#');
    companion object {
        val mapper: Map<Char, LightState> = values().associateBy { it.value }
    }
}
