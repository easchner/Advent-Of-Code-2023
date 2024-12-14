package year2015.day05

import utils.readInputString
import kotlin.system.measureTimeMillis

fun main() {
    fun part1(input: List<String>): Int {
        return input.map { line ->
            val vowels = line.count { it in "aeiou" }
            val doubleLetter = line.zipWithNext().any { it.first == it.second }
            val forbidden = line.contains("ab") || line.contains("cd") || line.contains("pq") || line.contains("xy")
            if (vowels >= 3 && doubleLetter && !forbidden) 1 else 0
        }.sum()
    }

    fun pairToString(pair: Pair<Char, Char>): String {
        return pair.first.toString() + pair.second.toString()
    }

    fun part2(input: List<String>): Int {
        return input.map { line ->
            val pair = line.contains(Regex("""(\w{2}).*\1"""))
            val repeat = line.contains(Regex("""(\w)\w\1"""))
            if (pair && repeat) 1 else 0
        }.sum()
    }

    val input = readInputString("year2015/day05/input")

    val timer1 = measureTimeMillis {
        println(part1(input))
    }
    println("Part 1 took $timer1 ms\n")

    val timer2 = measureTimeMillis {
        println(part2(input))
    }
    println("Part 2 took $timer2 ms")
}
