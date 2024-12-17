package year2024.day17

import utils.readInputSpaceDelimited
import utils.readInputString
import java.lang.Math.pow
import kotlin.math.floor
import kotlin.math.min
import kotlin.math.pow
import kotlin.system.measureTimeMillis

fun main() {
    fun part1(input: List<List<String>>): String {
        var (a, b, c) = input[0].map { it.split(": ").last().toLong() }
        val instructions = input[1][0].split(": ").last().split(",").map { it.toInt() }
        val outputs = mutableListOf<Int>()

        a = 202366627359274L
        var ptr = 0

        while (ptr in instructions.indices) {
            val instruction = instructions[ptr]
            val operand = instructions[ptr + 1]
            val combo = when (operand) {
                4 -> a
                5 -> b
                6 -> c
                else -> operand
            }

            when (instruction) {
                0 -> a = floor(a / 2.0.pow(combo.toDouble())).toLong()
                1 -> b = b xor operand.toLong()
                2 -> b = combo.toLong() % 8L
                3 -> if (a != 0L) ptr = operand - 2
                4 -> b = b xor c
                5 -> outputs.add((combo.toLong() % 8L).toInt())
                6 -> b = floor(a / 2.0.pow(combo.toDouble())).toLong()
                7 -> c = floor(a / 2.0.pow(combo.toDouble())).toLong()
            }

            ptr += 2
        }

        return outputs.joinToString(",")
    }

    fun part2(input: List<List<String>>): String {
        var (a, b, c) = input[0].map { it.split(": ").last().toLong() }
        val instructions = input[1][0].split(": ").last().split(",").map { it.toInt() }
        val outputs = mutableListOf<Int>()

        var initA = 35184446024234L - 67108859L
        val offset = listOf(3L, 2L, 67108859L)
//        var initA = 35184372088831L
        var initAStep = 1L
        var initAStepClimbing = true
        var offsetIndex = 2
        val initB = b
        val initC = c

        while (outputs != instructions && initA < 202366920436269L) {
//            initA++
            initA += offset[offsetIndex]
            offsetIndex = (offsetIndex + 1) % offset.size
            a = initA
            b = initB
            c = initC
            outputs.clear()
            var ptr = 0L

            if (a % 10_000_000 == 0L) println(a)

            while (ptr in instructions.indices) {
                val instruction = instructions[ptr.toInt()]
                val operand = instructions[(ptr + 1).toInt()].toLong()
                val combo = when (operand) {
                    4L -> a
                    5L -> b
                    6L -> c
                    else -> operand
                }

                when (instruction) {
                    0 -> a = floor(a / 2.0.pow(combo.toDouble())).toLong()
                    1 -> b = b xor operand
                    2 -> b = combo % 8
                    3 -> if (a != 0L) ptr = operand - 2L
                    4 -> b = b xor c
                    5 -> {
                        outputs.add((combo % 8).toInt())
                        if (outputs.size == 13 && outputs == instructions.subList(0, outputs.size)) {
                            println("Partial match at $initA")
                            break
//                            println("Outputs: ${outputs.joinToString(",")}")
//                            println("Inputs: ${instructions.joinToString(",")}")
                        }
                        if (outputs.size > instructions.size || outputs != instructions.subList(0, outputs.size)) break
                    }
                    6 -> b = floor(a / 2.0.pow(combo.toDouble())).toLong()
                    7 -> c = floor(a / 2.0.pow(combo.toDouble())).toLong()
                }

                ptr += 2
            }

//            if (outputs.size >= instructions.size && outputs.last() == instructions.last()) {
//                println("$initA")
//            }
//            println("$initA - ${outputs.size}")
//            if (outputs.size < instructions.size) {
//                initA += initAStep
//                if (initAStepClimbing)
//                    initAStep *= 2
//            } else {
//                initAStepClimbing = false
//                initAStep /= 2
//                initA -= initAStep
//            }
        }

        println(initA)
        return initA.toString()
    }

    fun part2b(input: List<List<String>>): String {
        var (a, b, c) = input[0].map { it.split(": ").last().toLong() }
        val instructions = input[1][0].split(": ").last().split(",").map { it.toInt() }
        val outputs = mutableListOf<Int>()

        var initA = 0L
        val initB = b
        val initC = c
        var offset = 0

        while (outputs.size != instructions.size) {
            initA = (initA + offset) shl 3
            outputs.clear()
            while (outputs.isEmpty() || outputs != instructions.subList(instructions.size - outputs.size, instructions.size)) {
                a = initA + offset
                offset++
                b = initB
                c = initC
                outputs.clear()
                var ptr = 0L

                while (ptr in instructions.indices) {
                    val instruction = instructions[ptr.toInt()]
                    val operand = instructions[(ptr + 1).toInt()].toLong()
                    val combo = when (operand) {
                        4L -> a
                        5L -> b
                        6L -> c
                        else -> operand
                    }

                    when (instruction) {
                        0 -> a = floor(a / 2.0.pow(combo.toDouble())).toLong()
                        1 -> b = b xor operand
                        2 -> b = combo % 8
                        3 -> if (a != 0L) ptr = operand - 2L
                        4 -> b = b xor c
                        5 -> outputs.add((combo % 8).toInt())
                        6 -> b = floor(a / 2.0.pow(combo.toDouble())).toLong()
                        7 -> c = floor(a / 2.0.pow(combo.toDouble())).toLong()
                    }

                    ptr += 2
                }
            }
        }

        println("$initA - ${outputs.last()}")
        return initA.toString()
    }

    val testInput = readInputSpaceDelimited("year2024/day17/test")
    val test2 = readInputSpaceDelimited("year2024/day17/test2")
    val input = readInputSpaceDelimited("year2024/day17/input")

//    check(part1(testInput) == "4,6,3,5,6,3,5,2,1,0")
    val timer1 = measureTimeMillis {
        println(part1(input))
    }
    println("Part 1 took $timer1 ms\n")

//    check(part2(test2) == "117440")
    val timer2 = measureTimeMillis {
        println(part2b(input))
    }
    println("Part 2 took $timer2 ms")
}