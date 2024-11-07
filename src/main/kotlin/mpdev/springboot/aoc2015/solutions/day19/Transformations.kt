package mpdev.springboot.aoc2015.solutions.day19

import kotlinx.serialization.Serializable
import mpdev.springboot.aoc2015.input.InputFileReader
import mpdev.springboot.aoc2015.solutions.PuzzleSolver
import mpdev.springboot.aoc2015.utils.*
import org.springframework.stereotype.Component
import kotlin.random.Random

@Component
class Transformations(inputFileReader: InputFileReader): PuzzleSolver(inputFileReader, 19) {

    val mainMolecule = Molecule(inputData.last().toElementsList())
    private final val aocInputList: List<AoCInput> = InputUtils(AoCInput::class.java).readAoCInput(inputData.subList(0, inputData.lastIndex - 1))
    final val rules: List<Pair<Element, List<Element>>> = aocInputList.map { Pair(Element.valueOf(it.from), it.to.toElementsList()) }
    final val rulesRnAr: List<Pair<Element, List<Element>>> = rules.filter { rule -> rule.second.contains(Element.Rn) }
    final val rulesNonRnAr: List<Pair<Element, List<Element>>> = rules - rulesRnAr.toSet()
    var transformationCount = -1

    fun findAllTransformations(): Set<Molecule> {
        val transformations = mutableSetOf<Molecule>()
        rules.forEach { rule -> transformations.addAll(applyRule(rule)) }
        return transformations.toSet()
    }

    fun applyRule(rule: Pair<Element, List<Element>>): Set<Molecule> {
        val transformations = mutableSetOf<Molecule>()
        for (i in 0 .. mainMolecule.elements.lastIndex) {
            if (mainMolecule.matches(i, listOf(rule.first)))
                transformations.add(mainMolecule.transform(i, i + 1, rule.second))
        }
        return transformations
    }

    fun findReverseTransformations() {
        var count = 0
        do {
            val molecule = tryTransform(0, mainMolecule, rules)
            log.info("attempt ${++count} returned $molecule")
        }
        while (molecule.elements != listOf(Element.e))
    }

    fun tryTransform(level: Int, molecule: Molecule, rules: List<Pair<Element, List<Element>>>): Molecule {
        if (molecule.elements == listOf(Element.e)) {
            transformationCount = level
            return molecule
        }
        var newMolecule = Molecule.from(molecule)
        val currentRules = rules.toMutableList()
        while (newMolecule == molecule) {
            val thisRule = currentRules.removeAt(Random.nextInt(currentRules.size))
            newMolecule = molecule.applyRuleReverse(thisRule)
            if (currentRules.isEmpty())
                return newMolecule
        }
        return tryTransform(level + 1, newMolecule, rules)
    }

    override fun solvePart1(): Int {
        return findAllTransformations().size
    }

    override fun solvePart2(): Int {
        // alternative solution using formula without actually performing reverse transformations
        // val elements = mainMolecule.elements
        // return elements.count() - elements.count{ it == Element.Rn } - elements.count{ it == Element.Ar } - 2 * elements.count{ it == Element.Y } - 1

        // solution using reverse transformations
        findReverseTransformations()
        return transformationCount
    }

    private final fun String.toElementsList(): List<Element> {
        var s = this
        val result = mutableListOf<Element>()
        whileLoop@while (s.isNotEmpty()) {
            for (element in Element.values()) {
                if (s.startsWith(element.name)) {
                    result.add(element)
                    s = s.removePrefix(element.name)
                    continue@whileLoop
                }
            }
            throw AocException("could not find element for string $s")
        }
        return result
    }
}

@Serializable
@AocInClass(delimiters = [" "])
@AocInReplacePatterns(["=> ", ""])
data class AoCInput(
    @AocInField(0) val from: String,
    @AocInField(1) val to: String
)

enum class Element {
    Al, B, Ca, F, H, Mg, N, O, P, Si, Th, Ti,
    C, Y,
    Rn, Ar, e
}

data class Molecule(val elements: List<Element> = emptyList()) {

    fun size() = elements.size

    fun matches(index: Int, elementList: List<Element>) =
        elements.subList(index, index + elementList.size) == elementList

    fun transform(index1: Int, index2: Int, newElements: List<Element>): Molecule
        = Molecule(elements.subList(0, index1) + newElements + elements.subList(index2, elements.size))

    fun applyRuleReverse(rule: Pair<Element, List<Element>>): Molecule {
        var molecule = this
        for (index in elements.indices) {
            if (index + rule.second.size > elements.size)
                    break
            if (matches(index, rule.second)) {
                molecule = transform(index, index + rule.second.size, listOf(rule.first))
                return molecule
            }
        }
        return molecule
    }

    fun toString(index1: Int = 0, index2: Int = elements.size) =
        "elements [" + elements.subList(index1, index2).joinToString("") + "]"

    companion object {
        fun from(molecule: Molecule): Molecule = Molecule(molecule.elements)
    }
}