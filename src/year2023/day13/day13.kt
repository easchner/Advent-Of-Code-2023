package year2023.day13

import readInputSpaceDelimited
import java.lang.Integer.min
import kotlin.system.measureTimeMillis

fun main() {
    fun findHorizontalMirror(grid: List<List<Char>>, original: Int = -1): Long {
        for (i in 1 until grid.size) {
            if (i != original) {
                val minRows = min(i, grid.size - i)
                var match = true
                for (r in 1..minRows) {
                    if (grid[i - r] != grid[i + r - 1]) {
                        match = false
                        break
                    }
                }
                if (match)
                    return i.toLong()
            }
        }
        return 0L
    }

    fun findVerticalMirror(grid: List<List<Char>>, original: Int = -1): Long {
        for (i in 1 until grid[0].size) {
            if (i != original) {
                val minCols = min(i, grid[0].size - i)
                var match = true
                for (r in 1..minCols) {
                    if (grid.map { it[i - r] } != grid.map { it[i + r - 1] }) {
                        match = false
                        break
                    }
                }
                if (match)
                    return i.toLong()
            }
        }
        return 0L
    }

    fun part1(input: List<List<String>>): Long {
        return input.sumOf {
            val grid = it.map { line -> line.toCharArray().toList() }
            findVerticalMirror(grid) + findHorizontalMirror(grid) * 100
        }
    }

    fun smudge(grid: List<List<Char>>): Long {
        val initialVertical = findVerticalMirror(grid)
        val initialHorizontal = findHorizontalMirror(grid)
        val newGrid = grid.map { it.toMutableList() }.toMutableList()
        for (y in grid.indices) {
            for (x in grid[y].indices) {
                when (newGrid[y][x]) {
                    '.' -> newGrid[y][x] = '#'
                    '#' -> newGrid[y][x] = '.'
                }

                val newVertical = findVerticalMirror(newGrid, initialVertical.toInt())
                val newHorizontal = findHorizontalMirror(newGrid, initialHorizontal.toInt())
                if (newVertical > 0)
                    return newVertical
                if (newHorizontal > 0)
                    return newHorizontal * 100

                when (newGrid[y][x]) {
                    '.' -> newGrid[y][x] = '#'
                    '#' -> newGrid[y][x] = '.'
                }
            }
        }
        return 0
    }

    fun part2(input: List<List<String>>): Long {
        return input.sumOf {
            val grid = it.map { line -> line.toCharArray().toList() }
            smudge(grid)
        }
    }

    val testInput = readInputSpaceDelimited("day13/test")
    val input = readInputSpaceDelimited("day13/input")

    println(part1(testInput))
    check(part1(testInput) == 405L)
    val timer1 = measureTimeMillis {
        println(part1(input))
    }
    println("Part 1 took $timer1 ms\n")

    check(part2(testInput) == 400L)
    val timer2 = measureTimeMillis {
        println(part2(input))
    }
    println("Part 2 took $timer2 ms")
}
