package day2405

import readInputString
import java.lang.Exception
import kotlin.system.measureTimeMillis

enum class Direction { UP, DOWN, LEFT, RIGHT }

fun main() {
    fun printGrid(grid: List<List<Boolean>>) {
        val printable = grid.map { it.map { i -> if (i) 'X' else '.' } }
        for (row in printable) {
            println(row.joinToString(""))
        }
    }

    fun part1(input: List<String>): Int {
        val grid = input.map { it.toCharArray().toMutableList() }.toMutableList()
        val visited = input.map { it.toCharArray().map { false }.toMutableList() }.toMutableList()
        var position = Pair(0, 0)
        var direction = Direction.UP

        for (i in grid.indices) {
            for (j in grid[0].indices) {
                if (grid[i][j] == '^') {
                    position = Pair(i, j)
                    grid[i][j] = '.'
                }
            }
        }

        while (position.first in 0 until grid.size && position.second in 0 until grid[0].size) {
            visited[position.first][position.second] = true

            when (direction) {
                Direction.UP -> {
                    if (position.first - 1 >= 0 && grid[position.first - 1][position.second] == '#') {
                        direction = Direction.RIGHT
                    } else {
                        position = Pair(position.first - 1, position.second)
                    }
                }

                Direction.RIGHT -> {
                    if (position.second + 1 < grid[0].size && grid[position.first][position.second + 1] == '#') {
                        direction = Direction.DOWN
                    } else {
                        position = Pair(position.first, position.second + 1)
                    }
                }

                Direction.DOWN -> {
                    if (position.first + 1 < grid.size && grid[position.first + 1][position.second] == '#') {
                        direction = Direction.LEFT
                    } else {
                        position = Pair(position.first + 1, position.second)
                    }
                }

                Direction.LEFT -> {
                    if (position.second - 1 >= 0 && grid[position.first][position.second - 1] == '#') {
                        direction = Direction.UP
                    } else {
                        position = Pair(position.first, position.second - 1)
                    }
                }
            }
        }

        return visited.sumOf { it.count { v -> v } }
    }

    fun isLoop(grid: List<List<Char>>): Boolean {
        val visited = grid.map { it.toCharArray().map { mutableSetOf<Direction>() }.toMutableList() }.toMutableList()
        var position = Pair(0, 0)
        var direction = Direction.UP

        for (i in grid.indices) {
            for (j in grid[0].indices) {
                if (grid[i][j] == '^') {
                    position = Pair(i, j)
                }
            }
        }

        while (position.first in 0 until grid.size && position.second in 0 until grid[0].size) {
            if (visited[position.first][position.second].contains(direction)) {
                return true
            }
            visited[position.first][position.second].add(direction)

//            println()
//            println()
//            printGrid(visited.map { it.map { i -> i.isNotEmpty() } })

            when (direction) {
                Direction.UP -> {
                    if (position.first - 1 >= 0 && listOf('#').contains(grid[position.first - 1][position.second])) {
                        direction = Direction.RIGHT
                    } else {
                        position = Pair(position.first - 1, position.second)
                    }
                }

                Direction.RIGHT -> {
                    if (position.second + 1 < grid[0].size && listOf('#').contains(grid[position.first][position.second + 1])) {
                        direction = Direction.DOWN
                    } else {
                        position = Pair(position.first, position.second + 1)
                    }
                }

                Direction.DOWN -> {
                    if (position.first + 1 < grid.size && listOf('#').contains(grid[position.first + 1][position.second])) {
                        direction = Direction.LEFT
                    } else {
                        position = Pair(position.first + 1, position.second)
                    }
                }

                Direction.LEFT -> {
                    if (position.second - 1 >= 0 && listOf('#').contains(grid[position.first][position.second - 1])) {
                        direction = Direction.UP
                    } else {
                        position = Pair(position.first, position.second - 1)
                    }
                }
            }
        }
        return false
    }

    fun part2(input: List<String>): Int {
        val grid = input.map { it.toCharArray().toMutableList() }.toMutableList()
        var count = 0

        for (i in grid.indices) {
            for (j in grid[0].indices) {
                if (grid[i][j] == '.') {
                    grid[i][j] = '#'
                    if (isLoop(grid)) {
                        count++
                        println(count)
                    }
                    grid[i][j] = '.'
                }
            }
        }

//        grid[6][3] = '#'
//        val sanity = isLoop(grid)

        return count
    }

    val testInput = readInputString("day2406/test")
    val input = readInputString("day2406/input")

    check(part1(testInput) == 41)
    val timer1 = measureTimeMillis {
        println(part1(input))
    }
    println("Part 1 took $timer1 ms\n")

    check(part2(testInput) == 6)
    val timer2 = measureTimeMillis {
        println(part2(input))
    }
    println("Part 2 took $timer2 ms")
}
