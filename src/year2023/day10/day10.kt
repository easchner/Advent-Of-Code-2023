package year2023.day10

import readInputString
import kotlin.system.measureTimeMillis

data class Point(val x: Int, val y: Int)

fun main() {
    fun neighbors(current: Point, pipe: Char): Pair<Point, Point> {
        return when (pipe) {
            '|' -> Point(current.x, current.y + 1) to Point(current.x, current.y - 1)
            '-' -> Point(current.x + 1, current.y) to Point(current.x - 1, current.y)
            'L' -> Point(current.x + 1, current.y) to Point(current.x, current.y - 1)
            'J' -> Point(current.x - 1, current.y) to Point(current.x, current.y - 1)
            '7' -> Point(current.x - 1, current.y) to Point(current.x, current.y + 1)
            'F' -> Point(current.x + 1, current.y) to Point(current.x, current.y + 1)
            else -> throw Exception("Map char not found $pipe")
        }
    }

    fun printCycle(grid: List<CharArray>, cycle: List<Point>) {
        for (y in grid.indices) {
            for (x in grid[y].indices) {
                if (cycle.contains(Point(x, y))) {
                    print(grid[y][x])
                } else {
                    print(' ')
                }
            }
            println()
        }
    }

    fun fillGrid(grid: MutableList<CharArray>, cycle: List<Point>): Int{
        val bigGrid = mutableListOf<CharArray>()
        for (y in 0 until grid.size * 2) {
            bigGrid.add(CharArray(grid[0].size * 2) { ' ' })
        }

        for (p in cycle) {
            val newP = Point(p.x * 2, p.y * 2)
            bigGrid[newP.y][newP.x] = grid[p.y][p.x]
            val neighbors = neighbors(newP, grid[p.y][p.x])
            if (neighbors.first.y in bigGrid.indices && neighbors.first.x in bigGrid[0].indices)
                bigGrid[neighbors.first.y][neighbors.first.x] = '#'
            if (neighbors.second.y in bigGrid.indices && neighbors.second.x in bigGrid[0].indices)
                bigGrid[neighbors.second.y][neighbors.second.x] = '#'
        }

        val working = mutableListOf(Point(0,0),
            Point(bigGrid[0].size - 1, 0),
            Point(0, bigGrid.size - 1),
            Point(bigGrid[0].size - 1, bigGrid.size - 1)
        )

        while (working.isNotEmpty()) {
            val next = working.removeFirst()
            if (next.y in bigGrid.indices && next.x in bigGrid[0].indices && bigGrid[next.y][next.x] == ' ') {
                working.add(Point(next.x, next.y - 1))
                working.add(Point(next.x, next.y + 1))
                working.add(Point(next.x - 1, next.y))
                working.add(Point(next.x + 1, next.y))
                bigGrid[next.y][next.x] = '.'
            }
        }

        for (y in 0 until bigGrid.size) {
            for (x in 0 until bigGrid[0].size) {
                print(bigGrid[y][x])
            }
            println()
        }

        val smallGrid = mutableListOf<CharArray>()
        for (y in grid.indices) {
            smallGrid.add(CharArray(grid[0].size) { ' ' })
            for (x in grid[y].indices) {
                smallGrid[y][x] = bigGrid[y*2][x*2]
            }
        }

        for (y in 0 until grid.size) {
            for (x in 0 until grid[0].size) {
                print(smallGrid[y][x])
            }
            println()
        }
        print(smallGrid.sumOf { y -> y.count { x -> x == ' ' } })
        println()
        println()

        return smallGrid.sumOf { y -> y.count { x -> x == ' ' } }
    }

    fun part1(input: List<String>): Int {
        val grid = input.map { it.toCharArray() }
        var start = Point(0, 0)
        for (y in grid.indices) {
            for (x in grid[y].indices) {
                if (grid[y][x] == 'S')
                    start = Point(x, y)
            }
        }

        var max = 0
        for (replacement in listOf('|', '-', 'L', 'J', '7', 'F')) {
            grid[start.y][start.x] = replacement
            var current = neighbors(start, grid[start.y][start.x]).first
            var last = start
            val cycle = mutableListOf<Point>()

            while (current != start && current.y in grid.indices && current.x in grid[0].indices && grid[current.y][current.x] != '.') {
                cycle.add(current)
                val neighbors = neighbors(current, grid[current.y][current.x])
                if (last == neighbors.first) {
                    if (cycle.contains(neighbors.second)) {
                        break
                    } else {
                        last = current
                        current = neighbors.second
                    }
                } else {
                    if (cycle.contains(neighbors.first)) {
                        break
                    } else {
                        last = current
                        current = neighbors.first
                    }
                }
            }

            max = maxOf(max, cycle.size + 1)
        }

        return max / 2
    }

    fun part2(input: List<String>): Int {
        val grid = input.map { it.toCharArray() }
        var start = Point(0, 0)
        for (y in grid.indices) {
            for (x in grid[y].indices) {
                if (grid[y][x] == 'S')
                    start = Point(x, y)
            }
        }

        var maxCycle = 0
        var maxHoles = 0
        for (replacement in listOf('|', '-', 'L', 'J', '7', 'F')) {
            grid[start.y][start.x] = replacement
            var current = neighbors(start, grid[start.y][start.x]).first
            var last = start
            val cycle = mutableListOf<Point>()

            while (current != start && current.y in grid.indices && current.x in grid[0].indices && grid[current.y][current.x] != '.') {
                cycle.add(current)
                val neighbors = neighbors(current, grid[current.y][current.x])
                if (last == neighbors.first) {
                    if (cycle.contains(neighbors.second)) {
                        break
                    } else {
                        last = current
                        current = neighbors.second
                    }
                } else {
                    if (cycle.contains(neighbors.first)) {
                        break
                    } else {
                        last = current
                        current = neighbors.first
                    }
                }
            }
            cycle.add(start)
            if (cycle.size > maxCycle) {
                maxCycle = cycle.size
                maxHoles = fillGrid(grid.toMutableList(), cycle)
            }
        }

        return maxHoles
    }

    val testInput = readInputString("day10/test")
    val input = readInputString("day10/input")
    val test2 = readInputString("day10/test2")

    val part1Test = part1(testInput)
    println("Part 1 test = $part1Test")
    check(part1Test == 8)
    val timer1 = measureTimeMillis {
        println(part1(input))
    }
    println("Part 1 took $timer1 ms\n")

    val part2Test = part2(test2)
    println("Part 2 test = $part2Test")
    check(part2Test == 10)
//    val timer2 = measureTimeMillis {
//        println(part2(input))
//    }
//    println("Part 2 took $timer2 ms")
}
