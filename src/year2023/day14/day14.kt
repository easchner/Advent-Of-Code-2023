package year2023.day14

import readInputString
import kotlin.system.measureTimeMillis

fun main() {
    fun rollNorth(grid: List<List<Char>>): List<List<Char>> {
        val newGrid = grid.map { it.toMutableList() }.toMutableList()
        for (x in grid[0].indices) {
            var high = 0
            for (y in grid.indices) {
                if (grid[y][x] == '#') {
                    high = y + 1
                } else if (grid[y][x] == 'O') {
                    newGrid[y][x] = '.'
                    newGrid[high][x] = 'O'
                    high++
                }
            }
        }

        return newGrid
    }

    fun rollSouth(grid: List<List<Char>>): List<List<Char>> {
        val newGrid = grid.map { it.toMutableList() }.toMutableList()
        for (x in grid[0].indices) {
            var low = grid.size - 1
            for (y in grid.indices.reversed()) {
                if (grid[y][x] == '#') {
                    low = y - 1
                } else if (grid[y][x] == 'O') {
                    newGrid[y][x] = '.'
                    newGrid[low][x] = 'O'
                    low--
                }
            }
        }

        return newGrid
    }

    fun rollEast(grid: List<List<Char>>): List<List<Char>> {
        val newGrid = grid.map { it.toMutableList() }.toMutableList()
        for (y in grid.indices) {
            var right = grid.size - 1
            for (x in grid[0].indices.reversed()) {
                if (grid[y][x] == '#') {
                    right = x - 1
                } else if (grid[y][x] == 'O') {
                    newGrid[y][x] = '.'
                    newGrid[y][right] = 'O'
                    right--
                }
            }
        }

        return newGrid
    }

    fun rollWest(grid: List<List<Char>>): List<List<Char>> {
        val newGrid = grid.map { it.toMutableList() }.toMutableList()
        for (y in grid.indices) {
            var left = 0
            for (x in grid[0].indices) {
                if (grid[y][x] == '#') {
                    left = x + 1
                } else if (grid[y][x] == 'O') {
                    newGrid[y][x] = '.'
                    newGrid[y][left] = 'O'
                    left++
                }
            }
        }

        return newGrid
    }

    fun countNorth(grid: List<List<Char>>): Long {
        var total = 0L
        for (y in grid.indices) {
            val weight = grid.size - y
            total += grid[y].count { it == 'O' } * weight
        }
        return total
    }

    fun part1(input: List<String>) = countNorth(rollNorth(input.map { it.toList() }))

    fun cycle(grid: List<List<Char>>) = rollEast(rollSouth(rollWest(rollNorth(grid))))

    fun part2(input: List<String>): Long {
        val grid = input.map { it.toList() }
        var nextGrid = grid
        for (i in 0 until 100) {
            nextGrid = cycle(nextGrid)
            println("$i = ${countNorth(nextGrid)}")
        }
        return countNorth(nextGrid)
    }

    val grids = hashMapOf<List<List<Char>>, Long>()
    fun part2Good(input: List<String>): Long {
        val grid = input.map { it.toList() }
        var nextGrid = grid
        var baseCycles = 0
        while (!grids.contains(nextGrid)) {
            grids[nextGrid] = 1
            nextGrid = cycle(nextGrid)
            baseCycles++
        }

        val original = nextGrid
        val cycleVals = mutableListOf(countNorth(nextGrid))
        nextGrid = cycle(nextGrid)

        while (nextGrid != original) {
            cycleVals.add(countNorth(nextGrid))
            nextGrid = cycle(nextGrid)
        }

        println("Base cycles = $baseCycles")
        println("Cycle length = ${cycleVals.size}")

        return cycleVals[(1_000_000_000 - baseCycles) % cycleVals.size]
    }

    val testInput = readInputString("day14/test")
    val input = readInputString("day14/input")

    println(part1(testInput))
    check(part1(testInput) == 136L)
    val timer1 = measureTimeMillis {
        println(part1(input))
    }
    println("Part 1 took $timer1 ms\n")

    check(part2Good(testInput) == 64L)
    val timer2 = measureTimeMillis {
        println(part2Good(input))
    }
    println("Part 2 took $timer2 ms")
}
