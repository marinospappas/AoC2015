package mpdev.springboot.aoc2015.input

import mpdev.springboot.aoc2015.utils.AocException
import org.apache.camel.CamelContext
import org.apache.camel.Exchange
import org.apache.camel.Processor
import org.apache.camel.builder.RouteBuilder
import org.apache.camel.impl.DefaultCamelContext
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component


@Component
class InputFileReader(@Value("\${input.files.dir}") val inputFilesDir: String) {

    private val log: Logger = LoggerFactory.getLogger(this::class.java)

    private final val inputData = mutableMapOf<Int, List<String>>()

    private final val ctx: CamelContext = DefaultCamelContext()
    private final val inputRoute = InputRouteBuilder(inputFilesDir, inputData)

    init {
        ctx.addRoutes(inputRoute)
        ctx.start()
        Thread.sleep(2000)
        log.info("Completed reading of input files via Camel route, read ${inputData.size} days")
    }

    fun getInput(day: Int): List<String> {
        return inputData[day] ?: throw AocException("Could not find input for day $day")
    }

    class InputRouteBuilder(private val inputFilesDir: String, val inputData: MutableMap<Int, List<String>>): RouteBuilder() {
        var day = 0
        override fun configure() {
            from("file:${inputFilesDir}?noop=true")
                .routeId("INPUT-FILES")
                //.split(body().tokenize("\n")).streaming()   --> process is called once for each line
                .process(InputProcessor(inputData))
        }
    }

    class InputProcessor(val inputData: MutableMap<Int, List<String>>): Processor {
        override fun process(exchange: Exchange) {
            val filename = (exchange.message.getHeader("CamelFileName") as String)
            val indexOfDot = filename.indexOfLast { c -> c == '.' }
            val day = filename.substring(indexOfDot - 2, indexOfDot).toInt()
            inputData[day] = exchange.message.getBody(String::class.java).split("\r\n").toList()
        }
    }
}

