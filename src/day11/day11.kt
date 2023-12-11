package day11

import readInputString
import java.lang.Math.abs
import kotlin.system.measureTimeMillis

data class Point(var x: Int, var y: Int, var blankX: Long, var blankY: Long)
fun main() {
    fun dist(input: List<String>, expansion: Int): Long {
        val galaxies = mutableListOf<Point>()
        for (y in input.indices) {
            for (x in input[y].indices) {
                if (input[y][x] == '#')
                    galaxies.add(Point(x, y, 0 , 0))
            }
        }

        // Expand y
        var y = galaxies.maxOf { it.y }
        while (y >= 0) {
            if (!galaxies.any { it.y == y }) {
                for (g in galaxies.filter { it.y > y }) {
                    galaxies.remove(g)
                    galaxies.add(Point(g.x, g.y, g.blankX, g.blankY + 1))
                }
            }
            y--
        }

        // Expand x
        var x = galaxies.maxOf { it.x }
        while (x >= 0 ) {
            if (!galaxies.any { it.x == x }) {
                for (g in galaxies.filter { it.x > x }) {
                    galaxies.remove(g)
                    galaxies.add(Point(g.x, g.y, g.blankX + 1, g.blankY))
                }
            }
            x--
        }

        var total = 0L
        for (g in galaxies.indices) {
            for (ng in g + 1 until galaxies.size) {
                total += (abs(galaxies[g].x - galaxies[ng].x) + abs(galaxies[g].blankX - galaxies[ng].blankX) * (expansion - 1) +
                        abs(galaxies[g].y - galaxies[ng].y) + abs(galaxies[g].blankY - galaxies[ng].blankY) * (expansion - 1))
            }
        }

        return total
    }

    val testInput = readInputString("day11/test")
    val input = readInputString("day11/input")

    check(dist(testInput, 2) == 374L)
    val timer1 = measureTimeMillis {
        println(dist(input, 2))
    }
    println("Part 1 took $timer1 ms\n")

    check(dist(testInput, 10) == 1030L)
    check(dist(testInput, 100) == 8410L)
    val timer2 = measureTimeMillis {
        println(dist(input, 1_000_000))
    }
    println("Part 2 took $timer2 ms")
}
