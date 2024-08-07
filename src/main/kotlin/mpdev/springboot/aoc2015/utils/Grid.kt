package mpdev.springboot.aoc2015.utils

import mpdev.springboot.aoc2015.utils.GridUtils.Direction.*

open class Grid<T>(inputGridVisual: List<String> = emptyList(),
                   private val mapper: Map<Char,T> = emptyMap(),
                   private val border: Int = 1,
                   private val defaultChar: Char = '.',
                   private val defaultSize: Pair<Int,Int> = Pair(-1,-1)) {

    protected var data = mutableMapOf<Point,T>()
    protected var maxX: Int = 0
    protected var maxY: Int = 0
    protected var minX: Int = 0
    protected var minY: Int = 0
    protected var DEFAULT_CHAR: Char
    protected var cornerPoints: Set<Point> = setOf()

    init {
        if (inputGridVisual.isNotEmpty()) {
            processInputVisual(inputGridVisual)
            updateXYDimensions(border)
        }
        DEFAULT_CHAR = defaultChar
    }

    constructor(gridData: Map<Point,T>, mapper: Map<Char,T>, border: Int = 1, defaultChar: Char = '.', defaultSize: Pair<Int,Int> = Pair(-1,-1)):
            this(mapper = mapper, border = border, defaultChar = defaultChar, defaultSize = defaultSize) {
        data = gridData.toMutableMap()
        updateXYDimensions(border)
    }

    constructor(inputGridXY: Set<String>, mapper: Map<Char,T>, border: Int = 1, defaultChar: Char = '.', defaultSize: Pair<Int,Int> = Pair(-1,-1)):
            this(mapper = mapper, border = border, defaultChar = defaultChar, defaultSize = defaultSize) {
        processInputXY(inputGridXY)
        updateXYDimensions(border)
    }

    constructor(inputGridXY: Array<Point>, mapper: Map<Char,T>, border: Int = 1, defaultChar: Char = '.', defaultSize: Pair<Int,Int> = Pair(-1,-1)):
            this(mapper = mapper, border = border, defaultChar = defaultChar, defaultSize = defaultSize) {
        processInputXY(inputGridXY)
        updateXYDimensions(border)
    }

    constructor(xyList: List<Point>, mapper: Map<Char,T> = emptyMap(), border: Int = 1, defaultChar: Char = '.', defaultSize: Pair<Int,Int> = Pair(-1,-1), function: (Point) -> T):
            this(mapper = mapper, border = border, defaultChar = defaultChar, defaultSize = defaultSize) {
        xyList.forEach { p -> data[p] = function(p) }
        updateXYDimensions(border)
    }

    constructor(xRange: IntRange, yRange: IntRange, mapper: Map<Char,T> = emptyMap(), border: Int = 1, defaultChar: Char = '.', defaultSize: Pair<Int,Int> = Pair(-1,-1), function: (Int,Int) -> T):
            this(mapper = mapper, border = border, defaultChar = defaultChar, defaultSize = defaultSize) {
        xRange.forEach { x -> yRange.forEach { y -> data[Point(x,y)] = function(x, y) } }
        updateXYDimensions(border)
    }

    private fun updateXYDimensions(border: Int) {
        if (defaultSize.first > 0 && defaultSize.second > 0) {
            minX = 0
            maxX = defaultSize.first - 1
            minX = 0
            maxY = defaultSize.second - 1
        }
        else if (data.isNotEmpty()) {
            maxX = data.keys.maxOf { it.x } + border
            maxY = data.keys.maxOf { it.y } + border
            minX = data.keys.minOf { it.x } - border
            minY = data.keys.minOf { it.y } - border
        }
        else {
            maxX = 0; maxY = 0; minX = 0; minY = 0
        }
        cornerPoints = setOf(Point(minX, minY), Point(minX, maxY), Point(maxX, minY), Point(maxX, maxY))
    }

    fun fill(datum: T) {
        data.keys.forEach { key -> data[key] = datum }
    }

    fun getDataPoints() = data.toMap()
    open fun getDataPoint(p: Point) = data[p]
    open fun setDataPoint(p: Point, t: T) {
        data[p] = t
    }
    open fun containsDataPoint(p: Point) = data.containsKey(p)

    open fun getAdjacent(p: Point, includeDiagonals: Boolean = false): Set<Point> {
        val cardinal = setOf(UP, RIGHT, DOWN, LEFT).map { p + it.increment }.toSet()
        val diagonals = setOf(Point(1,1), Point(-1,1), Point(1,-1), Point(-1,-1))
            .map { p + it }.toSet()
        return if (includeDiagonals) cardinal + diagonals else cardinal
    }

    open fun removeDataPoint(p: Point) {
        data.remove(p)
    }

    open fun findFirstOrNull(d: T): Point? = data.filter { it.value == d }.firstNotNullOfOrNull { it.key }

    open fun findFirst(d: T): Point = findFirstOrNull(d) ?: throw AocException("data point not found: [$d]")

    open fun getColumn(x: Int): Set<Map.Entry<Point,T>> =
        data.entries.filter { it.key.x == x }.toSet()

    open fun getRow(y: Int): Set<Map.Entry<Point,T>> =
        data.entries.filter { it.key.y == y }.toSet()

    fun getAdjacentArea(p: Point): Set<Point> {
        val area = mutableSetOf<Point>()
        val value = data[p] ?: return area
        val visited = mutableSetOf<Point>()
        val queue = ArrayDeque<Point>().also { q -> q.add(p) }
        while (queue.isNotEmpty()) {
            val point = queue.removeFirst().also { visited.add(it) }
            area.add(point)
            point.adjacent(false).filter { data[it] == value }.forEach { adj ->
                if (!visited.contains(adj))
                    queue.add(adj)
            }
        }
        return area
    }

    fun getDimensions() = Pair(maxX-minX+1, maxY-minY+1)
    fun getMinMaxXY() = FourComponents(minX, maxX, minY, maxY)
    fun getCorners() = cornerPoints.toSet()
    fun countOf(item: T) = data.values.count { it == item }

    fun firstPoint() = Point(minX,minY)

    fun nextPoint(p: Point) = if (p.x < maxX) p + Point(1,0) else Point(minX,p.y+1)

    fun isInsideGrid(p: Point) = p.x in minX..maxX && p.y in minY..maxY

    open fun updateDimensions() {
        updateXYDimensions(border)
    }

    fun getRowAsList(n: Int): List<T> = data.filter { e -> e.key.y == n }.map { e -> e.value }

    fun getColAsList(n: Int): List<T> = data.filter { e -> e.key.x == n }.map { e -> e.value }

    fun setRowFromList(n: Int, rowData: List<T>) {
        for (x in rowData.indices)
            data[Point(minX + x, n)] = rowData[x]
    }

    // mapping of a column or a row to int by interpreting the co-ordinates as bit positions
    fun mapRowToInt(n: Int, predicate: (T) -> Boolean = { true }) =
        data.filter { e -> predicate(e.value) && e.key.y == n }.map { e -> bitToInt[e.key.x] }
            .fold(0) { acc, i -> acc + i }

    fun mapColToInt(n: Int, predicate: (T) -> Boolean = { true }) =
        data.filter { e -> predicate(e.value) && e.key.x == n }.map { e -> bitToInt[e.key.y] }
            .fold(0) { acc, i -> acc + i }

    companion object {
        val allCharsDefMapper = (' '..'~').associateWith { it }
        private val bitToInt = intArrayOf( 1, 2, 4, 8, 16, 32, 64, 128, 256, 512, 1024, 2048, 4096, 8192, 16384, 32768,
            65536, 131072, 262144, 524288, 1_048_576, 2_097_152, 4_194_304, 8_388_608,
            16_777_216, 33_554_432, 67_108_864, 134_217_728, 268_435_456, 536_870_912, 1_073_741_824 )
    }

    private fun processInputVisual(input: List<String>) {
        input.indices.forEach { y ->
            input[y].indices.forEach { x ->
                if (mapper[input[y][x]] != null)
                    data[Point(x, y)] = mapper[input[y][x]]!!
            }
        }
    }

    private fun processInputXY(input: Array<Point>) {
        input.forEach { p ->
            data[p] = mapper.values.first()
        }
    }

    private fun processInputXY(input: Set<String>) {
        input.forEach { s ->
            val (x, y) = s.split(",")
            data[Point(x.trim().toInt(), y.trim().toInt())] = mapper.values.first()
        }
    }

    private fun data2Grid(): Array<CharArray> {
        val grid: Array<CharArray> = Array(maxY-minY+1) { CharArray(maxX-minX+1) { DEFAULT_CHAR } }
        data.forEach { (pos, item) -> grid[pos.y - minY][pos.x - minX] = map2Char(item) }
        return grid
    }

    protected fun map2Char(t: T) =
        mapper.entries.firstOrNull { e -> e.value == t }?.key ?: if (t is Int) '0' + t%10 else 'x'

    open fun print() {
        printGrid(data2Grid())
    }

    protected fun printGrid(grid: Array<CharArray>) {
        for (i in grid.indices) {
            print("${String.format("%2d",i%100)} ")
            for (j in grid.first().indices)
                print(grid[i][j])
            println("")
        }
        print("   ")
        for (i in grid.first().indices)
            print(if (i%10 == 0) (i/10)%10 else " ")
        println("")
        print("   ")
        for (i in grid.first().indices)
            print(i%10)
        println("")
    }
}

data class FourComponents(val x1: Int, val x2: Int, val x3: Int, val x4: Int)