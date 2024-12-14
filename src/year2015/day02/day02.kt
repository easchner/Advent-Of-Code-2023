package year2015.day02

import utils.readInputString
import kotlin.system.measureTimeMillis

fun main() {
    fun part1(input: List<String>): Int {
        var total = 0
        for (line in input) {
            val (l, w, h) = line.split("x").map { it.toInt() }
            val sides = listOf(l * w, w * h, h * l)
            total += 2 * sides.sum() + sides.minOrNull()!!
        }
        return total
    }

    fun part2(input: List<String>): Int {
        var total = 0
        for (line in input) {
            val (l, w, h) = line.split("x").map { it.toInt() }
            val sides = listOf(l, w, h).sorted()
            total += 2 * sides[0] + 2 * sides[1] + l * w * h
        }
        return total
    }

    val input = readInputString("year2015/day02/input")

    val timer1 = measureTimeMillis {
        println(part1(input))
    }
    println("Part 1 took $timer1 ms\n")

    val timer2 = measureTimeMillis {
        println(part2(input))
    }
    println("Part 2 took $timer2 ms")
}
