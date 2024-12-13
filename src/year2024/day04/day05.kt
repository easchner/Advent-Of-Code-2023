package year2024.day04

import readInputString
import kotlin.system.measureTimeMillis

fun main() {



    fun part1(input: List<String>): Int {
        val grid: MutableList<MutableList<Char>> = mutableListOf()
        val grid2: MutableList<MutableList<Char>> = mutableListOf()

        for (line in input) {
            grid.add(line.toMutableList())
            grid2.add(line.map { ' ' }.toMutableList())
        }

        var count = 0

        // horizontal
        for (r in 0 until grid.size) {
            val row = grid[r]
            var i = 0
            while (i < row.size - 3) {
                if (row[i] == 'X' && row[i + 1] == 'M' && row[i + 2] == 'A' && row[i + 3] == 'S') {
                    count++
                } else if (row[i] == 'S' && row[i + 1] == 'A' && row[i + 2] == 'M' && row[i + 3] == 'X') {
                    count++
                } else {
                    grid2[r][i] = 'X'
                }
                i++
            }
        }

        println("horizontal: $count")

        // vertical
        for (i in 0 until grid[0].size) {
            var j = 0
            while (j < grid.size - 3) {
                if (grid[j][i] == 'X' && grid[j + 1][i] == 'M' && grid[j + 2][i] == 'A' && grid[j + 3][i] == 'S') {
                    count++
                } else if (grid[j][i] == 'S' && grid[j + 1][i] == 'A' && grid[j + 2][i] == 'M' && grid[j + 3][i] == 'X') {
                    count++
                } else {
                    grid2[j][i] = 'X'
                }
                j++
            }
        }

        println("vertical: $count")

        // diagonal down right
        for (i in 0 until grid.size - 3) {
            for (j in 0 until grid[0].size - 3) {
                if (grid[i][j] == 'X' && grid[i + 1][j + 1] == 'M' && grid[i + 2][j + 2] == 'A' && grid[i + 3][j + 3] == 'S') {
                    count++
                } else if (grid[i][j] == 'S' && grid[i + 1][j + 1] == 'A' && grid[i + 2][j + 2] == 'M' && grid[i + 3][j + 3] == 'X') {
                    count++
                } else {
                    grid2[i][j] = 'X'
                }
            }
        }

        println("diagonal down right: $count")

        // diagonal down left
        for (i in 0 until grid.size - 3) {
            for (j in 3 until grid[0].size) {
                if (grid[i][j] == 'X' && grid[i + 1][j - 1] == 'M' && grid[i + 2][j - 2] == 'A' && grid[i + 3][j - 3] == 'S') {
                    count++
                } else if (grid[i][j] == 'S' && grid[i + 1][j - 1] == 'A' && grid[i + 2][j - 2] == 'M' && grid[i + 3][j - 3] == 'X') {
                    count++
                } else {
                    grid2[i][j] = 'x'
                }
            }
        }

        println("diagonal down left: $count")

//        println(grid2.joinToString("\n") { it.joinToString("") })

        return count
    }

    fun part2(input: List<String>): Int {
        val grid: MutableList<MutableList<Char>> = mutableListOf()
        val grid2: MutableList<MutableList<Char>> = mutableListOf()

        for (line in input) {
            grid.add(line.toMutableList())
            grid2.add(line.map { ' ' }.toMutableList())
        }

        var count = 0

        for (r in 0 until grid.size - 2) {
            for (c in 0 until grid.size - 2) {
                var downright = false
                var downleft = false

                if (grid[r][c] == 'M' && grid[r + 1][c + 1] == 'A' && grid[r + 2][c + 2] == 'S')
                    downright = true
                if (grid[r][c] == 'S' && grid[r + 1][c + 1] == 'A' && grid[r + 2][c + 2] == 'M')
                    downright = true
                if (grid[r + 2][c] == 'M' && grid[r + 1][c + 1] == 'A' && grid[r][c + 2] == 'S')
                    downleft = true
                if (grid[r + 2][c] == 'S' && grid[r + 1][c + 1] == 'A' && grid[r][c + 2] == 'M')
                    downleft = true

                if (downleft && downright)
                    count++
            }
        }

        return count
    }

    val testInput = readInputString("day2404/test")
    val input = readInputString("day2404/input")

    check(part1(testInput) == 18)
    val timer1 = measureTimeMillis {
        println(part1(input))
    }
    println("Part 1 took $timer1 ms\n")

    check(part2(testInput) == 9)
    val timer2 = measureTimeMillis {
        println(part2(input))
    }
    println("Part 2 took $timer2 ms")
}
