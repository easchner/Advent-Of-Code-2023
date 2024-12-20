package year2024.day20

import utils.readInputString
import kotlin.math.abs
import kotlin.system.measureTimeMillis

//data class State(val x: Int, val y: Int, val steps: Int, val cheats: Int, val active: Boolean = false)

fun main() {
    fun solveMaze(map: List<List<Char>>, startX: Int, startY: Int): Int {
        val toVisit = mutableListOf(Triple(startX, startY, 0))
        val visited = mutableSetOf<Pair<Int, Int>>()

        while (toVisit.isNotEmpty()) {
            val (x, y, steps) = toVisit.minBy { it.third }
            visited.add(Pair(x, y))
            toVisit.remove(Triple(x, y, steps))

            if (map[y][x] == 'E') {
                return steps
            }

            if (map[y][x] == '#') {
                continue
            }

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

        return -1
    }

    fun part1(input: List<String>, minSave: Int): Int {
        val map = input.map { row -> row.map { it }.toMutableList() }
        var x = 0
        var y = 0
        var total = 0

        for (row in map.indices) {
            for (col in map[row].indices) {
                if (map[row][col] == 'S') {
                    y = row
                    x = col
                }
            }
        }

        val reference = solveMaze(map, x, y)
        for (row in 1 until map.size - 1) {
            for (col in 1 until map[row].size - 1) {
                if (map[row][col] == '#') {
                    map[row][col] = '.'
                    val result = solveMaze(map, x, y)
                    if (result != -1 && result <= reference - minSave) {
                        total++
                    }
                    map[row][col] = '#'
                }
            }
        }

        return total
    }

    fun solveMazeCheat(map: List<List<Char>>, startX: Int, startY: Int, threshold: Int): Long {
        val minSteps = map.map { row -> row.map { -1 }.toMutableList() }
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

//        for (r in map.indices) {
//            for (c in map[r].indices) {
//                if (map[r][c] == '#') {
//                    print("#")
//                } else if (minSteps[r][c] == -1) {
//                    print(".")
//                } else {
//                    print(" ")
//                }
//            }
//            println()
//        }

        var total = 0L

        for (srow in 1 until map.size - 1) {
            for (scol in 1 until map[srow].size - 1) {
                // For each potential starting location of the cheat
                if (minSteps[srow][scol] != -1) {
                    for (eroff in -20..20) {
                        for (ecoff in -20..20) {
                            // For each potential ending location of the cheat
                            val erow = srow + eroff
                            val ecol = scol + ecoff
                            if (erow > 0 && erow < map.size && ecol > 0 && ecol < map[0].size && (abs(eroff) + abs(ecoff)) <= 20 && minSteps[erow][ecol] != -1) {
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

    fun part2(input: List<String>, minSave: Int): Long {
        val map = input.map { row -> row.map { it }.toMutableList() }
        var x = 0
        var y = 0

        for (row in map.indices) {
            for (col in map[row].indices) {
                if (map[row][col] == 'S') {
                    y = row
                    x = col
                }
            }
        }

        return solveMazeCheat(map, x, y, minSave)
    }

    val testInput = readInputString("year2024/day20/test")
    val input = readInputString("year2024/day20/input")

    check(part1(testInput, 20) == 5)
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