package year2024.day15

import utils.readInputSpaceDelimited
import kotlin.system.measureTimeMillis

fun main() {
    fun parseInput(input: List<List<String>>): Pair<List<MutableList<Char>>, String> {
        val grid = input[0].map { line -> line.map { c -> c }.toMutableList() }
        val instructions = input[1].joinToString("")

        return Pair(grid, instructions)
    }

    fun parseInput2(input: List<List<String>>): Pair<List<MutableList<Char>>, String> {
        val grid = input[0].map { line ->
            line.replace("#", "##")
                .replace("O", "[]")
                .replace(".", "..")
                .replace("@", "@.")
            .map { c -> c }.toMutableList() }
        val instructions = input[1].joinToString("")

        return Pair(grid, instructions)
    }

    fun scoreGrid(grid: List<List<Char>>): Long {
        var score = 0L
        for (row in grid.indices) {
            for (col in grid[row].indices) {
                val cell = grid[row][col]
                if (cell == 'O' || cell == '[') {
                    score += 100L * row + col
                }
            }
        }
        return score
    }

    fun checkMove(grid: List<MutableList<Char>>, row: Int, col: Int, direction: Char): Boolean {
        val next = when (direction) {
            '^' -> grid[row - 1][col]
            'v' -> grid[row + 1][col]
            '<' -> grid[row][col - 1]
            '>' -> grid[row][col + 1]
            else -> throw IllegalArgumentException("Invalid direction")
        }
        return when(next) {
            '#' -> false
            '.' -> true
            'O' -> when (direction) {
                '^' -> checkMove(grid, row - 1, col, direction)
                'v' -> checkMove(grid, row + 1, col, direction)
                '<' -> checkMove(grid, row, col - 1, direction)
                '>' -> checkMove(grid, row, col + 1, direction)
                else -> throw IllegalArgumentException("Invalid direction 2")
            }
            '[' -> when (direction) {
                '^' -> checkMove(grid, row - 1, col, direction) && checkMove(grid, row - 1, col + 1, direction)
                'v' -> checkMove(grid, row + 1, col, direction) && checkMove(grid, row + 1, col + 1, direction)
                '<' -> checkMove(grid, row, col - 1, direction)
                '>' -> checkMove(grid, row, col + 1, direction)
                else -> throw IllegalArgumentException("Invalid direction 2a")
            }
            ']' -> when (direction) {
                '^' -> checkMove(grid, row - 1, col, direction) && checkMove(grid, row - 1, col - 1, direction)
                'v' -> checkMove(grid, row + 1, col, direction) && checkMove(grid, row + 1, col - 1, direction)
                '<' -> checkMove(grid, row, col - 1, direction)
                '>' -> checkMove(grid, row, col + 1, direction)
                else -> throw IllegalArgumentException("Invalid direction 2b")
            }
            else -> false
        }
    }

    fun makeMove(grid: List<MutableList<Char>>, row: Int, col: Int, direction: Char) {
        when (direction) {
            '^' -> {
                val next = grid[row - 1][col]
                when (next) {
                    'O' -> makeMove(grid, row - 1, col, direction)
                    '[' -> { makeMove(grid, row - 1, col, direction); makeMove(grid, row - 1, col + 1, direction) }
                    ']' -> { makeMove(grid, row - 1, col, direction); makeMove(grid, row - 1, col - 1, direction) }
                    '#' -> throw IllegalArgumentException("Invalid move")
                }
                grid[row - 1][col] = grid[row][col]
            }
            'v' -> {
                val next = grid[row + 1][col]
                when (next) {
                    'O' -> makeMove(grid, row + 1, col, direction)
                    '[' -> { makeMove(grid, row + 1, col, direction); makeMove(grid, row + 1, col + 1, direction) }
                    ']' -> { makeMove(grid, row + 1, col, direction); makeMove(grid, row + 1, col - 1, direction) }
                    '#' -> throw IllegalArgumentException("Invalid move")
                }
                grid[row + 1][col] = grid[row][col]
            }
            '<' -> {
                val next = grid[row][col - 1]
                when (next) {
                    'O' -> makeMove(grid, row, col - 1, direction)
                    '[' -> makeMove(grid, row, col - 1, direction)
                    ']' -> makeMove(grid, row, col - 1, direction)
                    '#' -> throw IllegalArgumentException("Invalid move")
                }
                grid[row][col - 1] = grid[row][col]
            }
            '>' -> {
                val next = grid[row][col + 1]
                when (next) {
                    'O' -> makeMove(grid, row, col + 1, direction)
                    '[' -> makeMove(grid, row, col + 1, direction)
                    ']' -> makeMove(grid, row, col + 1, direction)
                    '#' -> throw IllegalArgumentException("Invalid move")
                }
                grid[row][col + 1] = grid[row][col]
            }
        }
        grid[row][col] = '.'
    }

    fun printGrid(grid: List<List<Char>>) {
        for (row in grid.indices) {
            for (col in grid[row].indices) {
                print(grid[row][col])
            }
            println()
        }
    }

    fun part1(input: List<List<String>>): Long {
        val (grid, instructions) = parseInput(input)
        var r = 0
        var c = 0
        for (row in grid.indices) {
            for (col in grid[row].indices) {
                if (grid[row][col] == '@') {
                    r = row
                    c = col
                }
            }
        }

        for (d in instructions) {
            val canMove = checkMove(grid, r, c, d)
            if (canMove) {
                makeMove(grid, r, c, d)
                when (d) {
                    '^' -> r--
                    'v' -> r++
                    '<' -> c--
                    '>' -> c++
                }
            }
//            println(d)
//            printGrid(grid)
        }

        return scoreGrid(grid)
    }

    fun part2(input: List<List<String>>): Long {
        val (grid, instructions) = parseInput2(input)
        var r = 0
        var c = 0
        for (row in grid.indices) {
            for (col in grid[row].indices) {
                if (grid[row][col] == '@') {
                    r = row
                    c = col
                }
            }
        }

        for (d in instructions) {
            val canMove = checkMove(grid, r, c, d)
            if (canMove) {
                makeMove(grid, r, c, d)
                when (d) {
                    '^' -> r--
                    'v' -> r++
                    '<' -> c--
                    '>' -> c++
                }
            }
//            println(d)
//            printGrid(grid)
        }

        return scoreGrid(grid)
    }

    val testInput = readInputSpaceDelimited("year2024/day15/test")
    val input = readInputSpaceDelimited("year2024/day15/input")

    check(part1(testInput) == 10092L)
    val timer1 = measureTimeMillis {
        println(part1(input))
    }
    println("Part 1 took $timer1 ms\n")

    check(part2(testInput) == 9021L)
    val timer2 = measureTimeMillis {
        println(part2(input))
    }
    println("Part 2 took $timer2 ms")
}