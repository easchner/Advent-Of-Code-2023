package year2024.day03

import utils.readInputString
import kotlin.system.measureTimeMillis

fun main() {
    fun part1(input: List<String>): Int {
        var total = 0
        val regex = Regex("mul\\(\\d{1,3},\\d{1,3}\\)")

        for (line in input) {
            val matches = regex.findAll(line)
            for (match in matches) {
                val num1 = match.value.split("(")[1].split(",")[0].toInt()
                val num2 = match.value.split(",")[1].split(")")[0].toInt()
                total += num1 * num2
            }
        }

        return total
    }

    fun part2(input: List<String>): Int {
        var total = 0
        var enabled = true
        val regex = Regex("mul\\(\\d{1,3},\\d{1,3}\\)|don't\\(\\)|do\\(\\)")

        for (line in input) {
            val matches = regex.findAll(line)
            for (match in matches) {
                if (match.value == "do()") {
                    enabled = true
                } else if (match.value == "don't()") {
                    enabled = false
                } else if (enabled) {
                    val num1 = match.value.split("(")[1].split(",")[0].toInt()
                    val num2 = match.value.split(",")[1].split(")")[0].toInt()
                    total += num1 * num2
                }
            }
        }

        return total
    }

    val testInput = readInputString("day2403/test")
    val input = readInputString("day2403/input")

    check(part1(testInput) == 161)
    val timer1 = measureTimeMillis {
        println(part1(input))
    }
    println("Part 1 took $timer1 ms\n")

    check(part2(testInput) == 48)
    val timer2 = measureTimeMillis {
        println(part2(input))
    }
    println("Part 2 took $timer2 ms")
}
