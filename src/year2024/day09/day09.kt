package year2024.day09

import readInputString
import kotlin.system.measureTimeMillis

fun main() {
    fun part1(input: List<String>): Long {
        val line = input[0]

        var total = 0L
        var index = 0L
        var isFile = true
        var frontId = 0L
        var frontItem = 0
        var backItem = line.length - 1
        var backRemaining = line[backItem].toString().toInt()

        while (frontItem < backItem) {
            val v = line[frontItem].toString().toInt()
            if (isFile) {
                for (i in 0 until v) {
                    total += index * frontId
                    index++
                }
                frontId++
            } else {
                for (i in 0 until v) {
                    if (backRemaining > 0) {
                        total += index * ((backItem + 1L) / 2L)
                        backRemaining--
                    } else {
                        backItem -= 2
                        if (backItem < frontItem) {
                            break
                        }
                        total += index * ((backItem + 1L) / 2L)
                        backRemaining = line[backItem].toString().toInt() - 1
                    }
                    index++
                }
            }
            isFile = !isFile
            frontItem++
        }

        while (backRemaining > 0) {
            total += index * ((backItem + 1L) / 2L)
            backRemaining--
            index++
        }

        return total
    }

    fun part2(input: List<String>): Long {
        val line = input[0]
        val blocks = mutableListOf<Int>()

        var isFile = true
        var id = 0
        for (c in line) {
            val v = c.toString().toInt()
            if (isFile) {
                for (i in 0 until v) {
                    blocks.add(id)
                }
                id++
            } else {
                for (i in 0 until v) {
                    blocks.add(-1)
                }
            }
            isFile = !isFile
        }

        var currentFile = blocks.max()
        while (currentFile > 0) {
            val currentLength = blocks.count { it == currentFile }
            var currentIndex = 0
            var firstSpaceLength = 0
            while (firstSpaceLength < currentLength && currentIndex < blocks.size) {
                if (blocks[currentIndex] == -1) {
                    firstSpaceLength++
                } else {
                    firstSpaceLength = 0
                }
                currentIndex++
            }
            val firstSpace = currentIndex - currentLength

            if (firstSpace < blocks.indexOfFirst { it == currentFile }) {
                blocks.replaceAll { if (it == currentFile) -1 else it }
                for (i in 0 until currentLength) {
                    blocks[firstSpace + i] = currentFile
                }
            }
            currentFile--
        }

        var total = 0L
        for (i in blocks.indices) {
            if (blocks[i] != -1)
                total += i * blocks[i].toLong()
        }

        return total
    }

    val testInput = readInputString("day2409/test")
    val input = readInputString("day2409/input")

    check(part1(testInput) == 1928L)
    val timer1 = measureTimeMillis {
        println(part1(input))
    }
    println("Part 1 took $timer1 ms\n")

    check(part2(testInput) == 2858L)
    val timer2 = measureTimeMillis {
        println(part2(input))
    }
    println("Part 2 took $timer2 ms")
}
