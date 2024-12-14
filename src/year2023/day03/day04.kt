package year2023.day03

import utils.readInputString

fun main() {
    fun getWholeNumber(line: List<Char>, index: Int): Pair<Int, Int> {
        var start = index
        var end = index
        while (start > 0 && line[start - 1].isDigit())
            start--
        while (end < line.size - 1 && line[end + 1].isDigit())
            end++
        return Pair(start, end)
    }

    fun part1(input: List<String>): Int {
        val grid = mutableListOf<List<Char>> ()
        val symbols = mutableSetOf<Pair<Int, Int>>()
        val numbers = mutableSetOf<Pair<Int, Int>>()

        for (line in input) {
            grid.add(line.toCharArray().toList())
        }

        for (x in grid.indices) {
            val line = grid[x]
            for (y in line.indices) {
                val c = line[y]
                if (c !in '0'..'9' && c != '.')
                    symbols.add(Pair(x, y))
                if (c in '0'..'9')
                    numbers.add(Pair(x, getWholeNumber(line, y).first))
            }
        }

        var total = 0
        for (n in numbers) {
            val (start, end) = getWholeNumber(grid[n.first], n.second)
            val num = grid[n.first].joinToString("").substring(start, end + 1)
            var valid = false
            for (x in -1..1) {
                for (y in -1..num.length) {
                    if (symbols.contains(Pair(n.first + x, n.second + y))) {
                        valid = true
                    }
                }
            }
            if (valid)
                total += num.toInt()
        }

        return total
    }

    fun part2(input: List<String>): Long {
        val grid = mutableListOf<List<Char>> ()
        val gears = mutableSetOf<Pair<Int, Int>>()
        var total = 0L

        for (line in input) {
            grid.add(line.toCharArray().toList())
        }

        for (x in grid.indices) {
            val line = grid[x]
            for (y in line.indices) {
                val c = line[y]
                if (c == '*')
                    gears.add(Pair(x, y))
            }
        }

        for (gear in gears) {
            val adjacent = mutableListOf<Int>()
            // left
            if (grid[gear.first][gear.second - 1].isDigit()) {
                val (start, end) = getWholeNumber(grid[gear.first], gear.second - 1)
                adjacent.add(grid[gear.first].joinToString("").substring(start, end + 1).toInt())
            }

            // right
            if (grid[gear.first][gear.second + 1].isDigit()) {
                val (start, end) = getWholeNumber(grid[gear.first], gear.second + 1)
                adjacent.add(grid[gear.first].joinToString("").substring(start, end + 1).toInt())
            }

            // top
            if (grid[gear.first - 1].joinToString("").substring(gear.second - 1, gear.second + 2).any { it.isDigit() }) {
                // We could have a left AND right, or JUST a center, or one long number
                val topSet = mutableSetOf<Pair<Int, Int>>()
                if (grid[gear.first - 1][gear.second - 1].isDigit())
                    topSet.add(getWholeNumber(grid[gear.first - 1], gear.second - 1))
                if (grid[gear.first - 1][gear.second].isDigit())
                    topSet.add(getWholeNumber(grid[gear.first - 1], gear.second))
                if (grid[gear.first - 1][gear.second + 1].isDigit())
                    topSet.add(getWholeNumber(grid[gear.first - 1], gear.second + 1))

                for (n in topSet)
                    adjacent.add(grid[gear.first - 1].joinToString("").substring(n.first, n.second + 1).toInt())
            }

            // bottom
            if (grid[gear.first + 1].joinToString("").substring(gear.second - 1, gear.second + 2).any { it.isDigit() }) {
                // We could have a left AND right, or JUST a center, or one long number
                val bottomSet = mutableSetOf<Pair<Int, Int>>()
                if (grid[gear.first + 1][gear.second - 1].isDigit())
                    bottomSet.add(getWholeNumber(grid[gear.first + 1], gear.second - 1))
                if (grid[gear.first + 1][gear.second].isDigit())
                    bottomSet.add(getWholeNumber(grid[gear.first + 1], gear.second))
                if (grid[gear.first + 1][gear.second + 1].isDigit())
                    bottomSet.add(getWholeNumber(grid[gear.first + 1], gear.second + 1))

                for (n in bottomSet)
                    adjacent.add(grid[gear.first + 1].joinToString("").substring(n.first, n.second + 1).toInt())
            }

            if (adjacent.size == 2)
                total += adjacent[0] * adjacent[1]
        }

        return total
    }

    val testInput = readInputString("day03/test")
    val input = readInputString("day03/input")

    check(part1(testInput) == 4361)
    println(part1(input))

    check(part2(testInput) == 467835L)
    println(part2(input))
}
