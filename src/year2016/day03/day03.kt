package year2016.day03

import utils.readInputString
import kotlin.system.measureTimeMillis

fun main() {
    val trimFront = """^\s+""".toRegex()
    val spaces = """\s+""".toRegex()

    fun part1(input: List<String>): Int {
        return input.map { line ->
            val lengths = line.replace(trimFront, "").split(spaces).map { it.toInt() }.sorted()
            if (lengths[0] + lengths[1] > lengths[2])
                1
            else
                0
        }.sum()
    }

    fun part2(input: List<String>): Int {
        return input.chunked(3).map { lines ->
            val lengths = lines.map { it.replace(trimFront, "").split(spaces).map { v -> v.toInt() } }

            var count = 0
            for (triangle in 0 until 3) {
                val sides = lengths.map { it[triangle] }.sorted()
                if (sides[0] + sides[1] > sides[2])
                    count++
            }

            count
        }.sum()
    }

    val input = readInputString("year2016/day03/input")

    val timer1 = measureTimeMillis {
        println(part1(input))
    }
    println("Part 1 took $timer1 ms\n")

    val timer2 = measureTimeMillis {
        println(part2(input))
    }
    println("Part 2 took $timer2 ms")
}
