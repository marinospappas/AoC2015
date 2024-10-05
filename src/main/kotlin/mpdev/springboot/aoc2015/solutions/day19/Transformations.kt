package mpdev.springboot.aoc2015.solutions.day19

import kotlinx.serialization.Serializable
import mpdev.springboot.aoc2015.input.InputFileReader
import mpdev.springboot.aoc2015.solutions.PuzzleSolver
import mpdev.springboot.aoc2015.utils.*
import org.springframework.stereotype.Component

@Component
class Transformations(inputFileReader: InputFileReader): PuzzleSolver(inputFileReader, 19) {

    val mainMolecule: String = inputData.last()
    final val aocInputList: List<AoCInput> = InputUtils(AoCInput::class.java).readAoCInput(inputData.subList(0, inputData.lastIndex - 1))
    var rules: List<Pair<String,String>> = aocInputList.map { Pair(it.from, it.to) }

    fun findAllTransformations(): Set<String> {
        val transformations = mutableSetOf<String>()
        rules.forEach { rule -> transformations.addAll(applyRule(rule)) }
        return transformations.toSet()
    }

    fun applyRule(rule: Pair<String,String>): Set<String> {
        val transformations = mutableSetOf<String>()
        for (i in 0 .. mainMolecule.length - rule.first.length) {
            val subStr = mainMolecule.substring(i)
            if (subStr.startsWith(rule.first))
                transformations.add(mainMolecule.substring(0, i) + rule.second + mainMolecule.substring(i + rule.first.length))
        }
        return transformations
    }

    override fun solvePart1(): Int {
        return findAllTransformations().size
    }

    fun reverseTransorfmation(s: String, rules: List<Pair<String, String>>): String {
        var molecule = s
        while (molecule != "e") {
            for (rule in rules) {
                if (molecule.contains(rule.second)) {
                    val index = molecule.lastIndexOf(rule.second)
                    molecule = molecule.substring(0, index) + rule.first + molecule.substring(index + rule.second.length)
                }
            }
        }
        return molecule
    }

    override fun solvePart2(): Int {
        var count = 0
        val rulesRnAr = rules.toList().filter { it.second.contains(Regex("Rn.*Ar")) }
        val rulesOther = rules.toMutableList() - rulesRnAr.toSet()
        var molecule = mainMolecule
        molecule = reverseTransorfmation(molecule, rulesRnAr)
        molecule = reverseTransorfmation(molecule, rulesOther)
        return count
    }
}

@Serializable
@AocInClass(delimiters = [" "])
@AocInReplacePatterns(["=> ", ""])
data class AoCInput(
    @AocInField(0) val from: String,
    @AocInField(1) val to: String
)
