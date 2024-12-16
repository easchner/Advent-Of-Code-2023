package year2016.day06

import utils.readInputString
import kotlin.math.max
import kotlin.system.measureTimeMillis

fun main() {
    fun part1(input: List<String>): String {
        var answer = ""
        for (col in input[0].indices) {
            answer += input.groupingBy { it[col] }.eachCount().maxBy { it.value }.key
        }
        return answer
    }

    fun part2(input: List<String>): String {
        var answer = ""
        for (col in input[0].indices) {
            answer += input.groupingBy { it[col] }.eachCount().minBy { it.value }.key
        }
        return answer
    }

    val test = readInputString("year2016/day06/test")
    val input = readInputString("year2016/day06/input")

    check(part1(test) == "easter")
    val timer1 = measureTimeMillis {
        println(part1(input))
    }
    println("Part 1 took $timer1 ms\n")

    check(part2(test) == "advent")
    val timer2 = measureTimeMillis {
        println(part2(input))
    }
    println("Part 2 took $timer2 ms")
}
