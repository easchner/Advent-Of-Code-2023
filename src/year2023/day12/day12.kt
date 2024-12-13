package year2023.day12

import readInputString
import kotlin.system.measureTimeMillis

val seenTable = hashMapOf<String, Long>()

fun main() {
    fun isValid(line: String, matches: List<Int>): Boolean {
        if (line.contains("?"))
            return false
        val groups = line.split(".").map { it.length }.filter { it > 0 }
        return groups == matches
    }

    fun individual(line: String, matches: List<Int>): Long {
        if (!line.contains("?")) {
            return if (isValid(line, matches)) 1L else 0L
        }

        return individual(line.replaceFirst("?", "."), matches) +
               individual(line.replaceFirst("?", "#"), matches)
    }

    fun potentialPositions(line: String, length: Int): List<Int> {
        val positions = mutableListOf<Int>()
        for (i in 0..(line.length - length)) {
            if (!line.substring(i until (i + length)).contains(".")) {
                if (!line.substring(0 until i).contains('#')) {
                    if (line.length > i + length && line[i + length] != '#') {
                        if (i == 0 || line[i - 1] != '#')
                            positions.add(i)
                    } else if (line.length == i + length) {
                        if (i == 0 || line[i - 1] != '#')
                            positions.add(i)
                    }
                }
            }
        }
        return positions
    }

    fun individual2(line: String, matches: List<Int>): Long {
        val seen = seenTable[line + matches.joinToString(",")]
        if (seen != null)
            return seen

        if (matches.isEmpty() && line.isEmpty())
            return 1
        if (matches.isEmpty() && line.contains('#'))
            return 0
        if (matches.isEmpty())
            return 1
        if (line.length < matches.first())
            return 0
        if (line.take(matches.first() + 1) == "#".repeat(matches.first() + 1))
            return 0
        if (!line.contains("?"))
            return if (isValid(line, matches)) 1 else 0
        if (matches.size == 1 && line.length == matches.first() && !line.contains('.'))
            return 1

        var sum = 0L
        for (p in potentialPositions(line, matches.first())) {
            sum += if (p + matches.first() == line.length) {
                individual2(line.substring(p + matches.first()), matches.takeLast(matches.size - 1))
            } else {
                individual2(line.substring(p + matches.first() + 1), matches.takeLast(matches.size - 1))
            }
        }

        seenTable[line + matches.joinToString(",")] = sum
        return sum
    }

    fun part1(input: List<String>): Long {
        return input.sumOf {
            val record = it.split(" ")[0]
            val matches = it.split(" ")[1].split(",").map { n -> n.toInt() }
            individual(record, matches)
        }
    }

    fun part2(input: List<String>): Long {
        return input.sumOf {
            val record = it.split(" ")[0].plus("?").repeat(5).removeSuffix("?")
            val matches = it.split(" ")[1].split(",").map { n -> n.toInt() }
            val result = individual2(record, matches + matches + matches + matches + matches)
            result
        }
    }

    val testInput = readInputString("day12/test")
    val input = readInputString("day12/input")

    check(part1(testInput) == 21L)
    val timer1 = measureTimeMillis {
        println(part1(input))
    }
    println("Part 1 took $timer1 ms\n")

    check(part2(testInput) == 525152L)
    val timer2 = measureTimeMillis {
        println(part2(input))
    }
    println("Part 2 took $timer2 ms")
}
