package mpdev.springboot.aoc2015.utils

/**
 * simple graph class
 * nodes: map of node_id to (map connected_node_id to weight)
 */
//TODO replace the Int weight with Weight<U> to be able to implement custom compare
class SGraph<T>(var nodes: MutableMap<T, MutableMap<T, Int>> = mutableMapOf(),
    val getConnected: (T) -> Set<Pair<T,Int>> = {id -> (nodes[id] ?: emptyMap()).map { Pair(it.key, it.value) }.toSet()},
    val heuristic: (T) -> Int? = { null }) {

    constructor(nodesList: List<Pair<T, MutableSet<T>>>):
            this (nodesList.map { (id, conn) ->
                id to conn.associateWith { 1 }.toMutableMap()
            }.toMap(mutableMapOf()))

    operator fun get(id: T) = nodes[id] ?: throw AocException("SGraph: node $id not found")

    fun getOrNull(id: T) = nodes[id]

    fun getNodes() = nodes.keys.toList()

    fun getNodesAndConnections() = nodes.toList()

    fun addNode(id: T, connected: T, connectBothWays: Boolean = false) {
        addNode(id, mapOf(connected to 1), connectBothWays)
    }

    fun addNode(id: T, connected: Set<T> = emptySet(), connectBothWays: Boolean = false) {
        addNode(id, connected.associateWith { 1 }, connectBothWays)
    }

    fun addNode(id: T, connected: Map<T,Int> = mapOf(), connectBothWays: Boolean = false) {
        nodes.computeIfAbsent(id) { mutableMapOf() }
        connected.forEach { (k, v) -> nodes[id]!![k] = v }
        if (connectBothWays)
            connected.forEach { (cId, v) -> addNode(cId, mapOf(id to v)) }
    }

    fun getAllConnectedPairs(): Set<Set<T>> {
        return nodes.map { n -> n.value.map { c -> setOf(n.key, c.key) } }.flatten().toSet()
    }

    fun removeConnection(a: T, b: T) {
        removeConnection(setOf(a, b))
    }

    fun getCost(a:T, b:T) = nodes[a]?.get(b) ?: throw SGraphException("cannot find the cost from [$a] to [$b]")

    fun setCost(a:T, b:T, cost: Int) {
        nodes[a]?.set(b, cost) ?: throw SGraphException("cannot set the cost from [$a] to [$b]")
    }

    fun removeConnection(edge: Set<T>) {
        nodes[edge.first()]?.remove(edge.last())
        nodes[edge.last()]?.remove(edge.first())
    }

    fun print() {
        var count = 1
        println("SGraph")
        nodes.forEach { (k, v) ->
            println("node ${count++}: $k connected to: $v")
        }
    }

    fun longestPathDfs(start: T, includeAllNodes: Boolean = true, isAtEnd: (T) -> Boolean): Int {
        return dfsMaxPath(start, isAtEnd, mutableMapOf(), includeAllNodes)
    }

    //TODO: refactor the below function to use Stack instead of recursion
    private fun dfsMaxPath(cur: T, isAtEnd: (T) -> Boolean, visited: MutableMap<T, Int>, includeAllNodes: Boolean): Int {
        if (isAtEnd(cur) &&
            if (includeAllNodes) visited.size == nodes.size else visited.isNotEmpty()) {
            return visited.values.sum()
        }
        var maxPath = Int.MIN_VALUE
        getConnected(cur).forEach { (neighbor, steps) ->
            if (neighbor !in visited) {
                visited[neighbor] = steps
                val res = dfsMaxPath(neighbor, isAtEnd, visited, includeAllNodes)
                if (res > maxPath) {
                    maxPath = res
                }
                visited.remove(neighbor)
            }
        }
        return maxPath
    }

    companion object {
        var aStarAlgorithm = false      // flag used to distinguish between A* and Dijkstra algorithm for min cost path
        inline fun <reified T: Comparable<T>> of(g: SGraph<T>): SGraph<T> {
            val newGraph = SGraph<T>()
            for ((id, connxns) in g.nodes) {
                newGraph.addNode(id, connxns.toMap())
            }
            return newGraph
        }
    }

    enum class Algorithm { Dijkstra, AStar }
}