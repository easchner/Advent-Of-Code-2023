package year2015.day03

import utils.readInputString
import kotlin.system.measureTimeMillis

data class Coord (val x: Int, val y: Int)

fun main() {
    fun part1(input: List<String>): Int {
        val visited = mutableSetOf(Coord(0, 0))
        var x = 0
        var y = 0

        for (d in input[0]) {
            when (d) {
                '^' -> y++
                'v' -> y--
                '<' -> x--
                '>' -> x++
            }
            visited.add(Coord(x, y))
        }

        return visited.size
    }

    fun part2(input: List<String>): Int {
        val visited = mutableSetOf(Coord(0, 0))
        var sx = 0; var sy = 0
        var rx = 0; var ry = 0

        for (d in 0 until input[0].length step 2) {
            when (input[0][d]) {
                '^' -> sy++
                'v' -> sy--
                '<' -> sx--
                '>' -> sx++
            }
            visited.add(Coord(sx, sy))
            when (input[0][d + 1]) {
                '^' -> ry++
                'v' -> ry--
                '<' -> rx--
                '>' -> rx++
            }
            visited.add(Coord(rx, ry))
        }

        return visited.size
    }

    val input = readInputString("year2015/day03/input")

    val timer1 = measureTimeMillis {
        println(part1(input))
    }
    println("Part 1 took $timer1 ms\n")

    val timer2 = measureTimeMillis {
        println(part2(input))
    }
    println("Part 2 took $timer2 ms")
}
