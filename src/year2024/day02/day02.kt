package year2024.day02

import utils.readInputString
import kotlin.math.abs
import kotlin.system.measureTimeMillis

fun main() {
    fun part1(input: List<String>): Int {
        var numSafe = 0

        for (line in input) {
            val levels = line.split(" ")
            var increasing = true
            var decreasing = true
            var stepsize = true
            var previous = levels[0].toInt()

            for (i in 1 until levels.size) {
                val current = levels[i].toInt()
                if (current > previous)
                    decreasing = false
                if (current < previous)
                    increasing = false
                if (abs(current - previous) !in 1..3)
                    stepsize = false
                previous = current
            }

            if ((increasing || decreasing) && stepsize)
                numSafe++
        }

        return numSafe
    }

    fun part2(input: List<String>): Int {
        var numSafe = 0

        for (line in input) {
            val levels = line.split(" ")
            var anySafe = false

            for (skip in levels.indices.plus(-1)) {
                val skippedLevels = levels.filterIndexed { index, s -> index != skip }

                var increasing = true
                var decreasing = true
                var stepsize = true
                var previous = skippedLevels[0].toInt()

                for (i in 1 until skippedLevels.size) {
                    val current = skippedLevels[i].toInt()
                    if (current > previous)
                        decreasing = false
                    if (current < previous)
                        increasing = false
                    if (abs(current - previous) !in 1..3)
                        stepsize = false
                    previous = current
                }

                if ((increasing || decreasing) && stepsize)
                    anySafe = true
            }

            if (anySafe)
                numSafe++
        }

        return numSafe
    }

    val testInput = readInputString("day2402/test")
    val input = readInputString("day2402/input")

    check(part1(testInput) == 2)
    val timer1 = measureTimeMillis {
        println(part1(input))
    }
    println("Part 1 took $timer1 ms\n")

    check(part2(testInput) == 4)
    val timer2 = measureTimeMillis {
        println(part2(input))
    }
    println("Part 2 took $timer2 ms")
}
