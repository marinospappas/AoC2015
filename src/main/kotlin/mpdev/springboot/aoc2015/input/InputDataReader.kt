package mpdev.springboot.aoc2015.input

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.io.File

@Component
class InputDataReader(@Value("\${input.filename.pattern}") var inputFileNamePattern: String) {

    var testInput = listOf<String>()

    fun read(day: Int, fileExtension: String = "txt"): List<String> = testInput.ifEmpty {
        File("$inputFileNamePattern${String.format("%02d",day)}.${fileExtension}").readLines()
    }

}