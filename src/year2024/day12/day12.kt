package year2024.day12

import readInputString
import kotlin.system.measureTimeMillis

fun main() {
    fun getCrops(map: List<List<Char>>, crop: Char): List<List<Char>> {
        val regions = map.map { it.toMutableList() }

        for (x in regions.indices) {
            for (y in regions[x].indices) {
                if (regions[x][y] != crop) {
                    regions[x][y] = ' '
                }
            }
        }

        return regions
    }

    fun getRegions(map: List<List<Char>>): List<List<List<Char>>> {
        val unexplored = map.map { it.toMutableList() }
        val regions = mutableListOf<List<List<Char>>>()

        while (unexplored.sumOf { x -> x.count { y -> y != ' ' } } > 0) {
            val stack = mutableListOf<Pair<Int, Int>>()
            val region = MutableList(unexplored.size) { MutableList(unexplored[0].size) { ' ' } }

            val fx = unexplored.indexOfFirst { x -> x.any { y -> y != ' ' } }
            val fy = unexplored[fx].indexOfFirst { it != ' ' }
            stack.add(fx to fy)

            while (stack.isNotEmpty()) {
                val (x, y) = stack.removeFirst()

                if (unexplored[x][y] != ' ') {
                    region[x][y] = unexplored[x][y]
                    unexplored[x][y] = ' '

                    if (x > 0) stack.add(x - 1 to y)
                    if (x < unexplored.size - 1) stack.add(x + 1 to y)
                    if (y > 0) stack.add(x to y - 1)
                    if (y < unexplored[x].size - 1) stack.add(x to y + 1)
                }
            }

            regions.add(region)
        }

        return regions
    }

    fun getFence(regions: List<List<Char>>): Int {
        var open = MutableList(regions.size + 2) { MutableList(regions[0].size + 2) { ' ' } }
        var fence = 0

        for (x in regions.indices) {
            for (y in regions[x].indices) {
                open[x + 1][y + 1] = regions[x][y]
            }
        }

        for (x in open.indices) {
            for (y in open[x].indices) {
                if (open[x][y] == ' ') {
                    if (x > 0 && open[x - 1][y] != ' ') fence++
                    if (x < open.size - 1 && open[x + 1][y] != ' ') fence++
                    if (y > 0 && open[x][y - 1] != ' ') fence++
                    if (y < open[x].size - 1 && open[x][y + 1] != ' ') fence++
                }
            }
        }

        return fence
    }

    fun getSides(regions: List<List<Char>>): Int {
        val open = MutableList(regions.size + 2) { MutableList(regions[0].size + 2) { ' ' } }
        val fence = mutableSetOf<Pair<Pair<Int, Int>, Pair<Int, Int>>>()
        var sides = 0

        for (x in regions.indices) {
            for (y in regions[x].indices) {
                open[x + 1][y + 1] = regions[x][y]
            }
        }

        for (x in open.indices) {
            for (y in open[x].indices) {
                if (open[x][y] == ' ') {
                    if (x > 0 && open[x - 1][y] != ' ') fence.add((x to y) to (x - 1 to y))
                    if (x < open.size - 1 && open[x + 1][y] != ' ') fence.add((x to y) to (x + 1 to y))
                    if (y > 0 && open[x][y - 1] != ' ') fence.add((x to y) to (x to y - 1))
                    if (y < open[x].size - 1 && open[x][y + 1] != ' ') fence.add((x to y) to (x to y + 1))
                }
            }
        }

        while (fence.isNotEmpty()) {
            val (cout, cin) = fence.first()
            fence.remove(cout to cin)
            sides++
            if (cout.first == cin.first) {
                var dx = -1
                while (fence.contains((cout.first + dx to cout.second) to (cin.first + dx to cin.second))) {
                    fence.remove((cout.first + dx to cout.second) to (cin.first + dx to cin.second))
                    dx--
                }
                dx = 1
                while (fence.contains((cout.first + dx to cout.second) to (cin.first + dx to cin.second))) {
                    fence.remove((cout.first + dx to cout.second) to (cin.first + dx to cin.second))
                    dx++
                }
            } else {
                var dy = -1
                while (fence.contains((cout.first to cout.second + dy) to (cin.first to cin.second + dy))) {
                    fence.remove((cout.first to cout.second + dy) to (cin.first to cin.second + dy))
                    dy--
                }
                dy = 1
                while (fence.contains((cout.first to cout.second + dy) to (cin.first to cin.second + dy))) {
                    fence.remove((cout.first to cout.second + dy) to (cin.first to cin.second + dy))
                    dy++
                }
            }
        }

        return sides
    }

    fun part1(input: List<String>): Int {
        val grid = input.map { line -> line.map { c -> c} }
        val types = ('A'..'Z').toSet()
        var total = 0

        for (type in types) {
            val crops = getCrops(grid, type)
            val regions = getRegions(crops)
            for (region in regions) {
                val fence = getFence(region)
                val area = region.sumOf { row -> row.count { it == type } }
                total += area * fence

//                region.forEach { row -> println(row.joinToString("")) }
//                println(area)
//                println(fence)
            }
        }

        return total
    }

    fun part2(input: List<String>): Int {
        val grid = input.map { line -> line.map { c -> c} }
        val types = ('A'..'Z').toSet()
        var total = 0

        for (type in types) {
            val crops = getCrops(grid, type)
            val regions = getRegions(crops)
            for (region in regions) {
//                region.forEach { row -> println(row.joinToString("")) }
                val sides = getSides(region)
                val area = region.sumOf { row -> row.count { it == type } }
                total += area * sides
//
//                println(area)
//                println(sides)
            }
        }

        return total
    }

    val testInput = readInputString("day2412/test")
    val input = readInputString("day2412/input")

    check(part1(testInput) == 1930)
    val timer1 = measureTimeMillis {
        println(part1(input))
    }
    println("Part 1 took $timer1 ms\n")

    check(part2(testInput) == 1206)
    val timer2 = measureTimeMillis {
        println(part2(input))
    }
    println("Part 2 took $timer2 ms")
}
