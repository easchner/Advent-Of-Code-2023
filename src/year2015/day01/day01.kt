package year2015.day01

import utils.readInputString
import kotlin.system.measureTimeMillis

fun main() {
    fun part1(input: List<String>): Int {
        return input[0].count { it == '(' } - input[0].count { it == ')' }
    }

    fun part2(input: List<String>): Int {
        return input[0].foldIndexed(0) { index, acc, c ->
            if (acc < 0) return index
            acc + if (c == '(') 1 else -1
        }
    }

    val testInput = readInputString("year2015/day01/test")
    val input = readInputString("year2015/day01/input")

    check(part1(testInput) == 1)
    val timer1 = measureTimeMillis {
        println(part1(input))
    }
    println("Part 1 took $timer1 ms\n")

    check(part2(testInput) == 5)
    val timer2 = measureTimeMillis {
        println(part2(input))
    }
    println("Part 2 took $timer2 ms")
}
