package year2023.day09

import readInputString
import kotlin.system.measureTimeMillis

fun main() {
    fun part1(input: List<String>): Long {
        var total = 0L

        for (line in input) {
            val nums = line.split(" ").map { it.toLong() }
            val steps = mutableListOf<Long>()
            var current = nums
            while (current.any { it != 0L }) {
                steps.add(current.last())
                current = current.mapIndexed { i, num -> if (i > 0) num - current[i-1] else 0L }.takeLast(current.size - 1)
            }
            total += steps.sum()
        }

        return total
    }

    fun part2(input: List<String>): Long {
        var total = 0L

        for (line in input) {
            val nums = line.split(" ").map { it.toLong() }
            val steps = mutableListOf<Long>()
            var current = nums
            while (current.any { it != 0L }) {
                steps.add(current.first())
                current = current.mapIndexed { i, num -> if (i > 0) num - current[i-1] else 0L }.takeLast(current.size - 1)
            }
            var prev = 0L
            while (steps.isNotEmpty()) {
                val diff = steps.last() - prev
                prev = diff
                steps.removeLast()
            }
            total += prev
        }

        return total
    }

    val testInput = readInputString("day09/test")
    val input = readInputString("day09/input")

    check(part1(testInput) == 114L)
    val timer1 = measureTimeMillis {
        println(part1(input))
    }
    println("Part 1 took $timer1 ms\n")

    check(part2(testInput) == 2L)
    val timer2 = measureTimeMillis {
        println(part2(input))
    }
    println("Part 2 took $timer2 ms")
}
