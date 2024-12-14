package year2023.day06

import utils.readInputString
import kotlin.system.measureTimeMillis

fun main() {
    fun part1(input: List<String>): Int {
        val times = input[0].split(": ")[1].split(" ").filter { it.isNotEmpty() }.map { it.toInt() }
        val dists = input[1].split(": ")[1].split(" ").filter { it.isNotEmpty() }.map { it.toInt() }

        var totalWins = 1
        for (race in times.indices) {
            val time = times[race]
            val dist = dists[race]
            var wins = 0
            for (hold in 1 until time) {
                if (hold * (time - hold) > dist)
                    wins++
            }
            totalWins *= wins
        }

        return totalWins
    }

    fun part2(input: List<String>): Long {
        val time = input[0].split(": ")[1].split(" ").filter { it.isNotEmpty() }.map { it.toInt() }
            .joinToString("").toLong()
        val dist = input[1].split(": ")[1].split(" ").filter { it.isNotEmpty() }.map { it.toInt() }
            .joinToString("").toLong()

        var wins = 0L
        for (hold in 1 until time) {
            if (hold * (time - hold) > dist)
                wins++
        }

        return wins
    }

    val testInput = readInputString("day06/test")
    val input = readInputString("day06/input")

    check(part1(testInput) == 288)
    val timer1 = measureTimeMillis {
        println(part1(input))
    }
    println("Part 1 took $timer1 ms\n")

    check(part2(testInput) == 71503L)
    val timer2 = measureTimeMillis {
        println(part2(input))
    }
    println("Part 2 took $timer2 ms")
}
