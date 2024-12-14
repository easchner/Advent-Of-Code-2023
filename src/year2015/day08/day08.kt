package year2015.day08

import utils.readInputString
import kotlin.system.measureTimeMillis

fun main() {
    fun part1(input: List<String>): Int {
        return input.sumOf {
            val copy = it.replace("""\\\\|\\\"|\\x[0-9a-fA-F]{2}""".toRegex(), ".")
            it.length - (copy.length - 2)
        }
    }

    fun part2(input: List<String>): Int {
        return input.sumOf {
            val copy = it.replace("""\\|\"""".toRegex(), "..")
            copy.length + 2 - it.length
        }
    }

    val test = readInputString("year2015/day08/test")
    val input = readInputString("year2015/day08/input")

    check(part1(test) == 12)
    val timer1 = measureTimeMillis {
        println(part1(input))
    }
    println("Part 1 took $timer1 ms\n")

    check(part2(test) == 19)
    val timer2 = measureTimeMillis {
        println(part2(input))
    }
    println("Part 2 took $timer2 ms")
}
