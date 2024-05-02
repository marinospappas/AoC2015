package mpdev.springboot.aoc2016

import lombok.extern.slf4j.Slf4j
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
@Slf4j
class AoC2015Application

fun main(args: Array<String>) {
    runApplication<AoC2015Application>(*args)
}