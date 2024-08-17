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

    // finds the first grouping of packages - this happens to satisfy the conditions as the packages are sorted
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

    // cache for the various combinations so that they are calculated only once (used in V2)
    val combinationsMap = mutableMapOf<Pair<Int,List<Int>>, List<List<Int>>>()
    fun getCombinations(n: Int, packages: List<Int>, sumOfGroup: Int): List<List<Int>> {
        return combinationsMap.getOrPut(Pair(n, packages)) { packages.combinations(n).toList().filter { group -> group.sum() == sumOfGroup } }
    }

    // finds all groupings that have as first group the group with the least number of packages
    fun sortPackagesV2(packages: List<Int>, numOfGroups: Int, sumOfGroup: Int, nMin: Int,
                       groupsOfPackages: MutableList<Set<Int>>, solutions: MutableList<List<Set<Int>>>, numberOfSolutions: Int):
            Boolean {
        if (numOfGroups == 1) {
            if (packages.sum() == sumOfGroup) {
                groupsOfPackages.add(packages.toSet())
                solutions.add(groupsOfPackages.toList())
                if (solutions.size == numberOfSolutions)
                    return true
            } else
                return false
        } else {
            val nMax = packages.size - (numOfGroups - 1) * nMin
            for (n in nMin..nMax) {
                val currentCombinations = getCombinations(n, packages, sumOfGroup)
                if (currentCombinations.isEmpty())
                    continue
                // -- given that we are only interested in the first group of the solution
                // if we are looking for the first group in a new solution
                // and all candidate combinations have  been identified as the first group in another solution
                // then we are done
                if (groupsOfPackages.isEmpty() && currentCombinations.all { combi -> solutions.any { solution -> solution[0] == combi.toSet() } }) {
                    log.info("${solutions.size} solutions with size of first group ${solutions[0][0].size} found")
                    return true
                }
                currentCombinations.forEach { group ->
                    // if this is the first group in a potential solution
                    // and has already been identified as the first group in another solution then skip it
                    if (groupsOfPackages.isNotEmpty() || solutions.none { it[0] == group.toSet() }) {
                        groupsOfPackages.add(group.toSet())
                        var res = sortPackagesV2(
                            packages - group.toSet(), numOfGroups - 1, sumOfGroup, nMin,
                            groupsOfPackages, solutions, numberOfSolutions
                        )
                        // if we are at the top level and
                        // need to identify more solutions, continue recursion from scratch
                        if (packages.size == packageList.size) {
                            if (solutions.size < numberOfSolutions)
                                res = sortPackagesV2(packages, numOfGroups, sumOfGroup, nMin, mutableListOf(), solutions, numberOfSolutions)
                            else
                                log.info("${solutions.size} solutions with size of first group ${solutions[0][0].size} found")
                        }
                        return res
                    }
                }
            }
        }
        return false
    }

    fun findGroupsAndQe(numberOfGroups: Int): Long {
        val result = mutableListOf<Set<Int>>()
        val nMin = ceil(packageList.count().toDouble() / packageList.max()).toInt()
        val solved = sortPackages(packageList, numOfGroups = numberOfGroups, sumOfGroup = packageList.sum() / numberOfGroups, nMin = nMin, result)
        if (solved)
            return PackageGroupComparator.qe(result[0].toList())
        throw AocException("2015 day 24 p 1 - no solution found")
    }

    fun findGroupsAndQeV2(numberOfGroups: Int): Long {
        val result: MutableList<List<Set<Int>>> = mutableListOf()
        val nMin = ceil(packageList.sum().toDouble() / numberOfGroups / packageList.max()).toInt()
        val solved = sortPackagesV2(
            packageList, numOfGroups = numberOfGroups, sumOfGroup = packageList.sum() / numberOfGroups,
            nMin = nMin, mutableListOf(), result, 1000
        )
        if (solved)
            return PackageGroupComparator.qe(result.sortedWith(PackageGroupComparator())[0][0].toList())
        throw AocException("2015 day 24 p 1 - no solution found")
    }

    override fun solvePart1() = findGroupsAndQeV2(3)

    override fun solvePart2() = findGroupsAndQeV2(4)

    class PackageGroupComparator : Comparator<List<Set<Int>>> {
        override fun compare(o1: List<Set<Int>>?, o2: List<Set<Int>>?): Int {
            if (o1 == null || o2 == null)
                return -1
            return if (o1[0].size == o2[0].size)
                qe(o1[0].map { it }).compareTo(qe(o2[0].map { it }))
            else
                o1.size.compareTo(o2.size)
        }
        companion object {
            fun qe(input: List<Int>) = input.map { it.toLong() }.reduce { acc, i -> acc * i }
        }
    }

}
