package day2407

import readInputString
import kotlin.system.measureTimeMillis

fun main() {
//    fun canBeFound(target: Long, inputs: List<Long>, withConcat: Boolean = false): Boolean {
//        val currentResults = mutableSetOf(inputs[0])
//
//        for (i in 1 until inputs.size) {
//            val nextResults = mutableSetOf<Long>()
//            for (currentResult in currentResults) {
//                nextResults.add(currentResult + inputs[i])
//                nextResults.add(currentResult * inputs[i])
//                if (withConcat) nextResults.add((currentResult.toString() + inputs[i].toString()).toLong())
//            }
//            currentResults.clear()
//            currentResults.addAll(nextResults.filter { it <= target })
//        }
//
//        return currentResults.contains(target)
//    }

    fun canBeFound(target: Long, inputs: List<Long>, withConcat: Boolean = false): Boolean {
        val currentTargets = mutableSetOf(target)

        for (i in inputs.size - 1 downTo 1) {
            val nextTargets = mutableSetOf<Long>()
            for (currentTarget in currentTargets) {
                if (currentTarget % inputs[i] == 0L) {
                    nextTargets.add(currentTarget / inputs[i])
                }
                if (currentTarget - inputs[i] >= inputs[0]) {
                    nextTargets.add(currentTarget - inputs[i])
                }
                if (withConcat && currentTarget.toString().endsWith(inputs[i].toString())) {
                    val next = currentTarget.toString().dropLast(inputs[i].toString().length)
                    if (next.isNotEmpty()) nextTargets.add(next.toLong())
                }
            }
            currentTargets.clear()
            currentTargets.addAll(nextTargets.filter { it <= target })
        }

        return currentTargets.contains(inputs[0])
    }

    fun part1(input: List<String>): Long {
        var total = 0L
        for (line in input) {
            val target = line.split(":")[0].toLong()
            val inputs = line.split(" ").drop(1).map { it.toLong() }
            if (canBeFound(target, inputs)) {
                total += target
            }
        }

        return total
    }

    fun part2(input: List<String>): Long {
        var total = 0L
        for (line in input) {
            val target = line.split(":")[0].toLong()
            val inputs = line.split(" ").drop(1).map { it.toLong() }
            if (canBeFound(target, inputs, true)) {
                total += target
            }
        }

        return total
    }

    val testInput = readInputString("day2407/test")
    val input = readInputString("day2407/input")

    check(part1(testInput) == 3749L)
    val timer1 = measureTimeMillis {
        println(part1(input))
    }
    println("Part 1 took $timer1 ms\n")

    check(part2(testInput) == 11387L)
    val timer2 = measureTimeMillis {
        println(part2(input))
    }
    println("Part 2 took $timer2 ms")
}
