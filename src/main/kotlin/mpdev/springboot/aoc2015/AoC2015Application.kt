package mpdev.springboot.aoc2015

import lombok.extern.slf4j.Slf4j
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
@Slf4j
class AoC2015Application

fun main(args: Array<String>) {
    runApplication<AoC2015Application>(*args)
}