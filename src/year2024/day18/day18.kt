package year2024.day18

import utils.readInputString
import kotlin.system.measureTimeMillis

fun main() {
    fun part1(input: List<String>, maxX: Int, maxY: Int, blocks: Int): Int {
        val grid = Array(maxX + 1) { Array(maxY + 1) { '.' } }

        for (step in 0 until blocks) {
            val (x, y) = input[step].split(",").map { it.toInt() }
            grid[x][y] = '#'
        }

        val toVisit = mutableListOf(Triple(0, 0 , 0))

        while (toVisit.isNotEmpty()) {
            val next = toVisit.minBy { it.third }
            val (x, y, steps) = next
            toVisit.remove(next)
            if (x == maxX && y == maxY) {
                return steps
            }
            if (x < 0 || x > maxX || y < 0 || y > maxY || grid[x][y] == '#') {
                continue
            }
            grid[x][y] = '#'
            toVisit.add(Triple(x + 1, y, steps + 1))
            toVisit.add(Triple(x - 1, y, steps + 1))
            toVisit.add(Triple(x, y + 1, steps + 1))
            toVisit.add(Triple(x, y - 1, steps + 1))
        }

        return 0
    }

    fun part2(input: List<String>, maxX: Int, maxY: Int): Pair<Int, Int> {
        var min = 0
        var max = input.size - 1
        while (max > min + 1) {
            val trial = (max + min) / 2
            if (part1(input, maxX, maxY, trial) > 0) {
                min = trial
            } else {
                max = trial
            }
        }
        val (bx, by) = input[min].split(",").map { it.toInt() }
        return Pair(bx, by)
    }

    val testInput = readInputString("year2024/day18/test")
    val input = readInputString("year2024/day18/input")

    check(part1(testInput, 6, 6, 12) == 22)
    val timer1 = measureTimeMillis {
        println(part1(input, 70, 70, 1024))
    }
    println("Part 1 took $timer1 ms\n")

    check(part2(testInput, 6, 6) == Pair(6, 1))
    val timer2 = measureTimeMillis {
        println(part2(input, 70, 70))
    }
    println("Part 2 took $timer2 ms")
}