package mpdev.springboot.aoc2015.solutions.day19

import kotlinx.serialization.Serializable
import mpdev.springboot.aoc2015.input.InputFileReader
import mpdev.springboot.aoc2015.solutions.PuzzleSolver
import mpdev.springboot.aoc2015.utils.*
import org.springframework.stereotype.Component

@Component
class Transformations(inputFileReader: InputFileReader): PuzzleSolver(inputFileReader, 19) {

    val mainMolecule = Molecule(inputData.last().toElementsList())
    private final val aocInputList: List<AoCInput> = InputUtils(AoCInput::class.java).readAoCInput(inputData.subList(0, inputData.lastIndex - 1))
    final val rules: List<Pair<Element, List<Element>>> = aocInputList.map { Pair(Element.valueOf(it.from), it.to.toElementsList()) }
    final val rulesRnAr: List<Pair<Element, List<Element>>> = rules.filter { rule -> rule.second.contains(Element.Rn) }
    final val rulesNonRnAr: List<Pair<Element, List<Element>>> = rules - rulesRnAr.toSet()

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

    fun reverseTransformation() {
        var molecule = mainMolecule
        var count = 0
        while (molecule.elements != listOf(Element.e)) {
            var rnArIndx = molecule.indexOfLastRnAr()
            var newMolecule = Molecule(molecule.elements)
            molecule = Molecule(listOf())
            if (rnArIndx != Pair(-1, -1)) {
                while (newMolecule != molecule) {
                    molecule = newMolecule
                    molecule.println()
                    newMolecule = molecule.applyRulesReverse(rulesNonRnAr, rnArIndx.first + 1, rnArIndx.second - 1)
                    ++count
                    rnArIndx = molecule.indexOfLastRnAr()
                }
                molecule.println()
                molecule = molecule.applyRulesReverse(rulesRnAr, rnArIndx.first - 1, rnArIndx.second)
                ++count
                molecule.println()
            }
        }
        molecule = molecule.applyRulesReverse(rulesNonRnAr, 0, molecule.size())
        molecule.println()
    }

    override fun solvePart1(): Int {
        return findAllTransformations().size
    }

    override fun solvePart2(): Int {
        val elements = mainMolecule.elements
        return elements.count() - elements.count{ it == Element.Rn } - elements.count{ it == Element.Ar } -
                2 * elements.count{ it == Element.Y } - 1
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

data class Molecule(val elements: List<Element>) {

    fun size() = elements.size

    fun matches(index: Int, elementList: List<Element>) =
        elements.subList(index, index + elementList.size) == elementList

    fun transform(index1: Int, index2: Int, newElements: List<Element>): Molecule
        = Molecule(elements.subList(0, index1) + newElements + elements.subList(index2, elements.size))

    fun applyRulesReverse(rules: List<Pair<Element, List<Element>>>, index1: Int, index2: Int): Molecule {
        var molecule = this
        var endIndex = index2
        while (endIndex > index1) {
            for (rule in rules) {
                if (endIndex - rule.second.size < index1)
                    continue
                if (matches(endIndex - rule.second.size, rule.second)) {
                    molecule = transform(endIndex - rule.second.size, endIndex, listOf(rule.first))
                    return molecule
                }
            }
            --endIndex
        }
        return molecule
    }

    fun indexOfLastRnAr(): Pair<Int, Int> {
        var index1 = -1
        var index2 = -1
        for (i in elements.lastIndex downTo 0) {
            if (elements[i] == Element.Ar) {
                index2 = i + 1
            } else {
                if (index2 >= 0 && elements[i] == Element.Rn) {
                    index1 = i
                    break
                }
            }
        }
        return Pair(index1, index2)
    }

    fun toString(index1: Int = 0, index2: Int = elements.size) =
        "elements [" + elements.subList(index1, index2).joinToString("") + "]"
}