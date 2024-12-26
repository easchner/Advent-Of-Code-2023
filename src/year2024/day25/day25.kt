package year2024.day25

import utils.readInputSpaceDelimited
import kotlin.system.measureTimeMillis

fun main() {
    fun readLock(input: List<String>): List<Int> {
        val answer = MutableList(5) { 0 }
        for (pin in 0 until 5) {
            for (i in 1..5) {
                if (input[i][pin] == '#') {
                    answer[pin] = i
                }
            }
        }

        return answer
    }

    fun readKey(input: List<String>): List<Int> {
        val answer = MutableList(5) { 0 }
        for (pin in 0 until 5) {
            for (i in 5 downTo 0) {
                if (input[i][pin] == '#') {
                    answer[pin] = 6 - i
                }
            }
        }

        return answer
    }

    fun part1(input: List<List<String>>): Long {
        val locks = mutableListOf<List<Int>>()
        val keys = mutableListOf<List<Int>>()

        for (item in input) {
            if (item[0][0] == '#')
                locks.add(readLock(item))
            else
                keys.add(readKey(item))
        }

        var total = 0

        for (lock in locks) {
            for (key in keys) {
                var match = true
                for (pin in 0 until 5) {
                    if (lock[pin] + key[pin] > 5) {
                        match = false
                        break
                    }
                }

                if (match) {
                    total++
                }
            }
        }

        return total.toLong()
    }

    fun part2(input: List<List<String>>): Long {
        return 0L
    }

    val testInput = readInputSpaceDelimited("year2024/day25/test")
    val input = readInputSpaceDelimited("year2024/day25/input")

    check(part1(testInput) == 3L)
    val timer1 = measureTimeMillis {
        println(part1(input))
    }
    println("Part 1 took $timer1 ms\n")

    check(part2(testInput) == 0L)
    val timer2 = measureTimeMillis {
        println(part2(input))
    }
    println("Part 2 took $timer2 ms")
}