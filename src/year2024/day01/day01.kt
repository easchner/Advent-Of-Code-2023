package year2024.day01

import utils.readInputString
import kotlin.math.abs
import kotlin.system.measureTimeMillis

fun main() {
    fun part1(input: List<String>): Int {
        val list1 = mutableListOf<Int>()
        val list2 = mutableListOf<Int>()

        for (l in input) {
            val (i1, i2) = l.split("   ")
            list1.add(i1.toInt())
            list2.add(i2.toInt())
        }

        list1.sort()
        list2.sort()

        var total = 0
        for (i in list1.indices) {
            total += abs(list1[i] - list2[i])
        }

        return total
    }

    fun part2(input: List<String>): Int {
        val list1 = mutableListOf<Int>()
        val list2 = mutableListOf<Int>()

        for (l in input) {
            val (i1, i2) = l.split("   ")
            list1.add(i1.toInt())
            list2.add(i2.toInt())
        }

        var total = 0
        for (i in list1) {
            val count = list2.count { it == i }
            total += i * count
        }

        return total
    }

    val testInput = readInputString("day2401/test")
    val input = readInputString("day2401/input")

    check(part1(testInput) == 11)
    val timer1 = measureTimeMillis {
        println(part1(input))
    }
    println("Part 1 took $timer1 ms\n")

    check(part2(testInput) == 31)
    val timer2 = measureTimeMillis {
        println(part2(input))
    }
    println("Part 2 took $timer2 ms")
}
