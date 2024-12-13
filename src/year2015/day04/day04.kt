package year2015.day04

import md5
import readInputString
import kotlin.system.measureTimeMillis

fun main() {
    fun part1(input: List<String>): Int {
        val key = input[0]
        var salt = 0

        while (true) {
            val hash = "$key$salt".md5()
            if (hash.startsWith("00000")) {
                return salt
            }
            salt++
        }
    }

    fun part2(input: List<String>): Int {
        val key = input[0]
        var salt = 0

        while (true) {
            val hash = "$key$salt".md5()
            if (hash.startsWith("000000")) {
                return salt
            }
            salt++
        }
    }

    val input = readInputString("year2015/day04/input")

    val timer1 = measureTimeMillis {
        println(part1(input))
    }
    println("Part 1 took $timer1 ms\n")

    val timer2 = measureTimeMillis {
        println(part2(input))
    }
    println("Part 2 took $timer2 ms")
}
