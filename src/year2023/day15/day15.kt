package year2023.day15

import readInputString
import kotlin.system.measureTimeMillis

fun main() {
    fun elfHash(s: String): Int {
        var current = 0
        for (c in s) {
            current += c.code
            current *= 17
            current %= 256
        }

        return current
    }

    fun part1(input: List<String>) = input[0].split(",").sumOf { elfHash(it) }

    fun part2(input: List<String>): Long {
        val boxes = List(256) { mutableListOf<Pair<String, Int>>() }
        val steps = input[0].split(",")
        for (step in steps) {
            if (step.last() == '-') {
                val label = step.split("-")[0]
                val hash = elfHash(label)
                val box = boxes[hash]
                box.removeIf { it.first == label }
            } else {
                val label = step.split("=")[0]
                val focal = step.split("=")[1]
                val hash = elfHash(label)
                val box = boxes[hash]
                val original = box.find { it.first == label }

                if (original != null) {
                    val index = box.indexOf(original)
                    box[index] = Pair(label, focal.toInt())
                } else {
                    box.add(Pair(label, focal.toInt()))
                }
            }
        }

        var total = 0L
        for (b in boxes.indices) {
            for (l in boxes[b].indices) {
                total += (b + 1) * (l + 1) * boxes[b][l].second
            }
        }

        return total
    }

    val testInput = readInputString("day15/test")
    val input = readInputString("day15/input")

    check(part1(testInput) == 1320)
    val timer1 = measureTimeMillis {
        println(part1(input))
    }
    println("Part 1 took $timer1 ms\n")

    check(part2(testInput) == 145L)
    val timer2 = measureTimeMillis {
        println(part2(input))
    }
    println("Part 2 took $timer2 ms")
}
