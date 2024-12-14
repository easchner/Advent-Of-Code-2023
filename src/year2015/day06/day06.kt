package year2015.day06

import utils.readInputString
import kotlin.math.max
import kotlin.system.measureTimeMillis

fun main() {
    fun part1(input: List<String>): Int {
        val grid = Array(1000) { BooleanArray(1000) }

        for (line in input) {
            val parts = line.split(" ")
            val start = parts[parts.size - 3].split(",")
            val end = parts[parts.size - 1].split(",")
            val startX = start[0].toInt()
            val startY = start[1].toInt()
            val endX = end[0].toInt()
            val endY = end[1].toInt()

            when {
                parts[1] == "on" -> {
                    for (x in startX..endX) {
                        for (y in startY..endY) {
                            grid[x][y] = true
                        }
                    }
                }
                parts[1] == "off" -> {
                    for (x in startX..endX) {
                        for (y in startY..endY) {
                            grid[x][y] = false
                        }
                    }
                }
                parts[0] == "toggle" -> {
                    for (x in startX..endX) {
                        for (y in startY..endY) {
                            grid[x][y] = !grid[x][y]
                        }
                    }
                }
            }
        }

        return grid.sumOf { r -> r.count { it } }
    }

    fun part2(input: List<String>): Long {
        val grid = Array(1000) { IntArray(1000) { 0 } }

        for (line in input) {
            val parts = line.split(" ")
            val start = parts[parts.size - 3].split(",")
            val end = parts[parts.size - 1].split(",")
            val startX = start[0].toInt()
            val startY = start[1].toInt()
            val endX = end[0].toInt()
            val endY = end[1].toInt()

            when {
                parts[1] == "on" -> {
                    for (x in startX..endX) {
                        for (y in startY..endY) {
                            grid[x][y] += 1
                        }
                    }
                }
                parts[1] == "off" -> {
                    for (x in startX..endX) {
                        for (y in startY..endY) {
                            grid[x][y] = max(0, grid[x][y] - 1)
                        }
                    }
                }
                parts[0] == "toggle" -> {
                    for (x in startX..endX) {
                        for (y in startY..endY) {
                            grid[x][y] += 2
                        }
                    }
                }
            }
        }

        return grid.sumOf { r -> r.sumOf { it.toLong() } }
    }

    val input = readInputString("year2015/day06/input")

    val timer1 = measureTimeMillis {
        println(part1(input))
    }
    println("Part 1 took $timer1 ms\n")

    val timer2 = measureTimeMillis {
        println(part2(input))
    }
    println("Part 2 took $timer2 ms")
}
