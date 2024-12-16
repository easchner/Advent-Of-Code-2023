package year2016.day02

import utils.readInputString
import kotlin.system.measureTimeMillis

fun main() {
    val keypad = mapOf(
        Pair(0, 0) to "1",
        Pair(1, 0) to "2",
        Pair(2, 0) to "3",
        Pair(0, 1) to "4",
        Pair(1, 1) to "5",
        Pair(2, 1) to "6",
        Pair(0, 2) to "7",
        Pair(1, 2) to "8",
        Pair(2, 2) to "9"
    )

    val keypad2 = mapOf(
        Pair(2, 0) to "1",
        Pair(1, 1) to "2",
        Pair(2, 1) to "3",
        Pair(3, 1) to "4",
        Pair(0, 2) to "5",
        Pair(1, 2) to "6",
        Pair(2, 2) to "7",
        Pair(3, 2) to "8",
        Pair(4, 2) to "9",
        Pair(1, 3) to "A",
        Pair(2, 3) to "B",
        Pair(3, 3) to "C",
        Pair(2, 4) to "D"
    )

    fun part1(input: List<String>): String {
        var password = ""
        var x = 1
        var y = 1
        for (presses in input) {
            for (dir in presses) {
                when (dir) {
                    'U' -> y--
                    'D' -> y++
                    'L' -> x--
                    'R' -> x++
                }
                if (x < 0) x = 0
                if (x > 2) x = 2
                if (y < 0) y = 0
                if (y > 2) y = 2
            }
            password = password + keypad[Pair(x, y)]
        }

        return password
    }

    fun part2(input: List<String>): String {
        var password = ""
        var x = 0
        var y = 2
        for (presses in input) {
            for (dir in presses) {
                val lx = x
                val ly = y
                when (dir) {
                    'U' -> y--
                    'D' -> y++
                    'L' -> x--
                    'R' -> x++
                }
                if (!keypad2.containsKey(Pair(x, y))) {
                    x = lx
                    y = ly
                }
            }
            password = password + keypad2[Pair(x, y)]
        }

        return password
    }

    val test = readInputString("year2016/day02/test")
    val input = readInputString("year2016/day02/input")

    check(part1(test) == "1985")
    val timer1 = measureTimeMillis {
        println(part1(input))
    }
    println("Part 1 took $timer1 ms\n")

    check(part2(test) == "5DB3")
    val timer2 = measureTimeMillis {
        println(part2(input))
    }
    println("Part 2 took $timer2 ms")
}
