package day2410

import readInputString
import kotlin.math.abs
import kotlin.system.measureTimeMillis

data class Coord(val x: Int, val y: Int)

fun main() {
    fun countTrailHead(map: List<List<Int>>, start: Coord): Int {
        var currentLocations = mutableSetOf(start)
        var nextElevation = 1

        while (nextElevation <= 9) {
            val nextLocations = mutableSetOf<Coord>()
            for (loc in currentLocations) {
                for (dx in -1..1) {
                    for (dy in -1..1) {
                        if (dx == 0 && dy == 0) continue
                        if (abs(dx) + abs(dy) == 2) continue
                        val next = Coord(loc.x + dx, loc.y + dy)
                        if (next.x < 0 || next.x >= map.size || next.y < 0 || next.y >= map[0].size) continue
                        if (map[next.x][next.y] == nextElevation) {
                            nextLocations.add(next)
                        }
                    }
                }
            }
            if (nextLocations.isEmpty()) return 0
            currentLocations = nextLocations
            nextElevation++
        }

        return currentLocations.size
    }

    fun part1(input: List<String>): Int {
        val map = input.map { line -> line.map { c -> c.digitToInt() }}
        var total = 0
        for (x in map.indices) {
            for (y in map[0].indices) {
                if (map[x][y] == 0) {
                    total += countTrailHead(map, Coord(x, y))
                }
            }
        }

        return total
    }

    fun countDistinct(map: List<List<Int>>, start: Coord): Int {
        var currentLocations = mutableSetOf(listOf(start))
        var nextElevation = 1

        while (nextElevation <= 9) {
            val nextLocations = mutableSetOf<List<Coord>>()
            for (path in currentLocations) {
                val loc = path.last()
                for (dx in -1..1) {
                    for (dy in -1..1) {
                        if (dx == 0 && dy == 0) continue
                        if (abs(dx) + abs(dy) == 2) continue
                        val next = Coord(loc.x + dx, loc.y + dy)
                        if (next.x < 0 || next.x >= map.size || next.y < 0 || next.y >= map[0].size) continue
                        if (map[next.x][next.y] == nextElevation) {
                            nextLocations.add(path.plus(next))
                        }
                    }
                }
            }
            if (nextLocations.isEmpty()) return 0
            currentLocations = nextLocations
            nextElevation++
        }

        return currentLocations.size
    }

    fun part2(input: List<String>): Int {
        val map = input.map { line -> line.map { c -> c.digitToInt() }}
        var total = 0
        for (x in map.indices) {
            for (y in map[0].indices) {
                if (map[x][y] == 0) {
                    total += countDistinct(map, Coord(x, y))
                }
            }
        }

        return total
    }

    val testInput = readInputString("day2410/test")
    val input = readInputString("day2410/input")

    check(part1(testInput) == 36)
    val timer1 = measureTimeMillis {
        println(part1(input))
    }
    println("Part 1 took $timer1 ms\n")

    check(part2(testInput) == 81)
    val timer2 = measureTimeMillis {
        println(part2(input))
    }
    println("Part 2 took $timer2 ms")
}
