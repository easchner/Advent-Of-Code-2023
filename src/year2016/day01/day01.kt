package year2016.day01

import utils.readInputString
import kotlin.math.abs
import kotlin.system.measureTimeMillis

fun main() {
    fun part1(input: String): Int {
        val steps = input.split(", ")

        var x = 0
        var y = 0
        var direction = 0

        for (step in steps) {
            when (step[0]) {
                'L' -> direction--
                'R' -> direction++
            }
            if (direction < 0) direction = 3
            if (direction > 3) direction = 0
            val distance = step.substring(1).toInt()
            when (direction) {
                0 -> y += distance
                1 -> x += distance
                2 -> y -= distance
                3 -> x -= distance
            }
        }

        return abs(x) + abs(y)
    }

    fun part2(input: String): Int {
        val steps = input.split(", ")
        var visited = mutableSetOf(Pair(0, 0))

        var x = 0
        var y = 0
        var direction = 0

        for (step in steps) {
            when (step[0]) {
                'L' -> direction--
                'R' -> direction++
            }
            if (direction < 0) direction = 3
            if (direction > 3) direction = 0
            val distance = step.substring(1).toInt()
            for (i in 0 until distance) {
                when (direction) {
                    0 -> y += 1
                    1 -> x += 1
                    2 -> y -= 1
                    3 -> x -= 1
                }

                if (visited.contains(Pair(x, y)))
                    return abs(x) + abs(y)
                visited.add(Pair(x, y))
            }
        }

        return abs(x) + abs(y)
    }

    val input = readInputString("year2016/day01/input")[0]

//    check(part1("R2, L3") == 5)
//    check(part1("R2, R2, R2") == 2)
//    check(part1("R5, L5, R5, R3") == 12)
    val timer1 = measureTimeMillis {
        println(part1(input))
    }
    println("Part 1 took $timer1 ms\n")

//    check(part2(testInput) == 0)
    val timer2 = measureTimeMillis {
        println(part2(input))
    }
    println("Part 2 took $timer2 ms")
}
