package year2024.day20

import utils.readInputString
import kotlin.math.abs
import kotlin.system.measureTimeMillis

fun main() {
    fun solveMazeCheat(map: List<List<Char>>, cheatLength: Int, threshold: Int): Long {
        val minSteps = map.map { row -> row.map { -1 }.toMutableList() }

        var startX = 0
        var startY = 0

        for (row in map.indices) {
            for (col in map[row].indices) {
                if (map[row][col] == 'S') {
                    startY = row
                    startX = col
                }
            }
        }

        val toVisit = mutableListOf(Triple(startX, startY, 0))
        val visited = mutableSetOf<Pair<Int, Int>>()

        while (toVisit.isNotEmpty()) {
            val (x, y, steps) = toVisit.minBy { it.third }
            visited.add(Pair(x, y))
            toVisit.remove(Triple(x, y, steps))

            if (map[y][x] == '#') {
                continue
            }

            minSteps[y][x] = steps

            if (x > 0 && !visited.contains(Pair(x - 1, y))) {
                toVisit.add(Triple(x - 1, y, steps + 1))
            }

            if (x < map[0].size - 1 && !visited.contains(Pair(x + 1, y))) {
                toVisit.add(Triple(x + 1, y, steps + 1))
            }

            if (y > 0 && !visited.contains(Pair(x, y - 1))) {
                toVisit.add(Triple(x, y - 1, steps + 1))
            }

            if (y < map.size - 1 && !visited.contains(Pair(x, y + 1))) {
                toVisit.add(Triple(x, y + 1, steps + 1))
            }
        }

        var total = 0L

        for (srow in 1 until map.size - 1) {
            for (scol in 1 until map[srow].size - 1) {
                // For each potential starting location of the cheat
                if (minSteps[srow][scol] != -1) {
                    for (eroff in (-1 * cheatLength)..cheatLength) {
                        for (ecoff in (-1 * (cheatLength - abs(eroff)))..(cheatLength - abs(eroff))) {
                            // For each potential ending location of the cheat
                            val erow = srow + eroff
                            val ecol = scol + ecoff
                            if (erow > 0 && erow < map.size && ecol > 0 && ecol < map[0].size && minSteps[erow][ecol] != -1) {
                                val diff =
                                    minSteps[erow][ecol] - minSteps[srow][scol] - abs(eroff) - abs(ecoff)
                                if (diff >= threshold) {
                                    total++
                                }
                            }
                        }
                    }
                }
            }
        }

        return total
    }

    fun part1(input: List<String>, minSave: Int): Long {
        val map = input.map { row -> row.map { it }.toMutableList() }
        return solveMazeCheat(map, 2, minSave)
    }

    fun part2(input: List<String>, minSave: Int): Long {
        val map = input.map { row -> row.map { it }.toMutableList() }
        return solveMazeCheat(map,20, minSave)
    }

    val testInput = readInputString("year2024/day20/test")
    val input = readInputString("year2024/day20/input")

    check(part1(testInput, 20) == 5L)
    val timer1 = measureTimeMillis {
        println(part1(input, 100))
    }
    println("Part 1 took $timer1 ms\n")

    check(part2(testInput, 50) == 285L)
    val timer2 = measureTimeMillis {
        println(part2(input, 100))
    }
    println("Part 2 took $timer2 ms")
}