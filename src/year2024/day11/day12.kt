package year2024.day11

import readInputString
import kotlin.system.measureTimeMillis

fun main() {
    val memo = mutableMapOf<Pair<Long, Int>, Long>()

    fun traverse(stone: Long, steps: Int): Long {
        if (steps == 0) return 1
        if (memo.containsKey(stone to steps)) return memo[stone to steps]!!

        val str = stone.toString()
        val result =
            if (stone == 0L)
                traverse(1, steps - 1)
            else if (str.length % 2 == 0)
                traverse(str.substring(0 until str.length / 2).toLong(), steps - 1) +
                traverse(str.substring(str.length / 2 until str.length).toLong(), steps - 1)
            else
                traverse(stone * 2024, steps - 1)

        memo[stone to steps] = result
        return result
    }

    fun part1(input: List<String>): Long {
        return input[0].split(" ").sumOf { stone -> traverse(stone.toLong(), 25) }
    }

    fun part2(input: List<String>): Long {
        return input[0].split(" ").sumOf { stone -> traverse(stone.toLong(), 75) }
    }

    val testInput = readInputString("day2411/test")
    val input = readInputString("day2411/input")

    check(part1(testInput) == 55312L)
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
