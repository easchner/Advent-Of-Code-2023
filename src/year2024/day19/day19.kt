package year2024.day19

import utils.readInputSpaceDelimited
import kotlin.system.measureTimeMillis

fun main() {
    val memoPatterns = mutableMapOf<String, Boolean>()

    fun isComboPossible(target: String, towels: List<String>): Boolean {
        if (target.isEmpty()) return true
        if (memoPatterns.containsKey(target)) return memoPatterns[target]!!
        for (towel in towels) {
            if (target.startsWith(towel)) {
                if (isComboPossible(target.substring(towel.length), towels)) {
                    memoPatterns[target] = true
                    return true
                }
            }
        }
        memoPatterns[target] = false
        return false
    }

    val memoArrangements = mutableMapOf<String, Long>()

    fun countArrangements(target: String, towels: List<String>): Long {
        if (target.isEmpty()) return 1L
        if (memoArrangements.containsKey(target)) return memoArrangements[target]!!

        var count = 0L
        for (towel in towels) {
            if (target.startsWith(towel)) {
                count += countArrangements(target.substring(towel.length), towels)
            }
        }
        memoArrangements[target] = count
        return count
    }

    fun part1(input: List<List<String>>): Int {
        val towels = input[0][0].split(", ")
        val targets = input[1]

        return targets.count { isComboPossible(it, towels) }
    }

    fun part2(input: List<List<String>>): Long {
        val towels = input[0][0].split(", ")
        val targets = input[1]

        return targets.sumOf { countArrangements(it, towels) }
    }

    val testInput = readInputSpaceDelimited("year2024/day19/test")
    val input = readInputSpaceDelimited("year2024/day19/input")

    check(part1(testInput) == 6)
    val timer1 = measureTimeMillis {
        memoPatterns.clear()
        println(part1(input))
    }
    println("Part 1 took $timer1 ms\n")

    check(part2(testInput) == 16L)
    val timer2 = measureTimeMillis {
        memoArrangements.clear()
        println(part2(input))
    }
    println("Part 2 took $timer2 ms")
}