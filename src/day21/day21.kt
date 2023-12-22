package day21

import readInputString
import kotlin.system.measureTimeMillis

data class Point(val x: Int, val y: Int, val odd: Boolean)
fun main() {
    fun printGrid(good: Set<Pair<Int, Int>>, x: Int, y: Int) {
        for (j in 0 until y) {
            for (i in 0 until x) {
                if (good.contains(Pair(i, j)))
                    print("O")
                else
                    print(".")
            }
            println()
        }
        println()
        println()
    }

    fun printGrid2(grid: Array<CharArray>) {
        for (y in grid.indices) {
            for (x in grid[0].indices) {
                print(grid[y][x])
            }
            println()
        }
        println()
        println()
    }

    fun part1(input: List<String>, steps: Int): Int {
        val grid = input.map { it.toCharArray() }.toTypedArray()
        var workingStep = mutableSetOf<Pair<Int, Int>>()
        for (y in grid.indices) {
            for (x in grid.indices) {
                if (grid[y][x] == 'S')
                    workingStep.add(Pair(x, y))
            }
        }

        for (i in 0 until steps) {
            val currentStep = workingStep.toSet()
            val nextStep = mutableSetOf<Pair<Int, Int>>()
            for (block in currentStep) {
                val options = mutableListOf(Pair(block.first - 1, block.second), Pair(block.first + 1, block.second), Pair(block.first, block.second - 1), Pair(block.first, block.second + 1))
                for (o in options) {
                    if (o.first >= 0 && o.first < grid[0].size && o.second >= 0 && o.second < grid.size && grid[o.second][o.first] != '#')
                        nextStep.add(o)
                }
            }
            workingStep = nextStep
        }
//        printGrid(workingStep, grid[0].size, grid.size)
        return workingStep.size
    }

    fun part2(input: List<String>, steps: Long): Long {
        val ogrid = input.map { it.toCharArray() }.toTypedArray()
        val extras = 3
        val grid = Array(ogrid.size * (extras * 2 + 1)) { CharArray(ogrid[0].size * (extras * 2 + 1)) }
        for (y in grid.indices) {
            for (x in grid[0].indices) {
                grid[y][x] = ogrid[y % ogrid.size][x % ogrid[0].size]
                if (grid[y][x] == 'S')
                    grid[y][x] = '.'
            }
        }
        grid[extras * 131 + 65][extras * 131 + 65] = 'E'
        var workingStep = mutableListOf<Triple<Int, Int, Long>>()
        workingStep.add(Triple(extras * 131 + 65, extras * 131 + 65, 1L))

        for (i in 0 until (65 + 131 * extras)) {
            val currentStep = workingStep.toList()
            val nextStep = mutableListOf<Triple<Int, Int, Long>>()
            for (block in currentStep) {
                val options = mutableListOf(Pair(block.first - 1, block.second), Pair(block.first + 1, block.second), Pair(block.first, block.second - 1), Pair(block.first, block.second + 1))
                for (o in options) {
                    if (o.first >= 0 && o.first < grid[0].size && o.second >= 0 && o.second < grid.size && grid[o.second][o.first] == '.') {
                        val dup = nextStep.find { it.first == o.first && it.second == o.second }
                        if (dup == null) {
                            nextStep.add(Triple(o.first, o.second, 1))
                        }
                    }
                }
            }
            workingStep = nextStep
            for (w in workingStep) {
                grid[w.second][w.first] = if ((i % 2).toInt() == 0) 'E' else 'O'
            }
            if (((i - 65) % 131) == 0) {
                if ((i % 2) == 0) {
                    println("Size is ${grid.sumOf { it.count { c -> c == 'E' } }} after $i steps.")
                } else {
                    println("Size is ${grid.sumOf { it.count { c -> c == 'O' } }} after $i steps.")
                }
            }
        }
//        printGrid2(grid)
        return 0L
    }

    val testInput = readInputString("day21/test")
    val input = readInputString("day21/input")

    check(part1(testInput, 6) == 16)
    val timer1 = measureTimeMillis {
        println(part1(input, 64))
    }
    println("Part 1 took $timer1 ms\n")

//    check(part2(testInput, 20000L) == 16733044L)
    val timer2 = measureTimeMillis {
        println(part2(input, 26501365L))
    }
    println("Part 2 took $timer2 ms")
}
