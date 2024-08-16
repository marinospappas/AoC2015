package mpdev.springboot.aoc2015.solutions.day24

import mpdev.springboot.aoc2015.input.InputDataReader
import mpdev.springboot.aoc2015.solutions.PuzzleSolver
import mpdev.springboot.aoc2015.utils.AocException
import mpdev.springboot.aoc2015.utils.combinations
import org.springframework.stereotype.Component
import kotlin.math.ceil

@Component
class PackageSorting(inputDataReader: InputDataReader): PuzzleSolver(inputDataReader, 24) {

    final val packageList = inputData.map { it.toInt() }

    fun sortPackages(packages: List<Int>, numOfGroups: Int, sumOfGroup: Int, nMin: Int, groupsOfPackages: MutableList<Set<Int>>): Boolean {
        if (numOfGroups == 1) {
            return if  (packages.sum() == sumOfGroup) {
                groupsOfPackages.add(packages.toSet())
                true
            } else
                false
        } else {
            val nMax = packages.size - (numOfGroups - 1) * nMin
            for (n in nMin .. nMax) {
                packages.combinations(n).forEach { group ->
                    if (group.sum() == sumOfGroup) {
                        groupsOfPackages.add(group.toSet())
                        return sortPackages(packages - group.toSet(), numOfGroups - 1, sumOfGroup, nMin, groupsOfPackages)
                    }
                }
            }
            return false
        }
    }

    fun findGroupsAndQe(numberOfGroups: Int): Long {
        val result = mutableListOf<Set<Int>>()
        val nMin = ceil(packageList.count().toDouble() / packageList.max()).toInt()
        val solved = sortPackages(packageList, numOfGroups = numberOfGroups, sumOfGroup = packageList.sum() / numberOfGroups, nMin = nMin, result)
        if (solved)
            return PackageGroupComparator.qe(result[0].toList())
        throw AocException("2015 day 24 p 1 - no solution found")
    }

    override fun solvePart1() = findGroupsAndQe(3)

    override fun solvePart2() = findGroupsAndQe(4)

    class PackageGroupComparator : Comparator<Set<Int>> {
        override fun compare(o1: Set<Int>?, o2: Set<Int>?): Int {
            if (o1 == null || o2 == null)
                return -1
            return if (o1.size == o2.size)
                qe(o1.map { it }).compareTo(qe(o2.map { it }))
            else
                o1.size.compareTo(o2.size)
        }
        companion object {
            fun qe(input: List<Int>) = input.map { it.toLong() }.reduce { acc, i -> acc * i }
        }
    }

}
