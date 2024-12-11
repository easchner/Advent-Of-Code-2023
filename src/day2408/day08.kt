package day2408

import readInputString
import kotlin.system.measureTimeMillis

data class Coord (val x: Int, val y: Int)

fun main() {
    fun getAntinodes(pos1: Coord, pos2: Coord): List<Coord> {
        val xDiff = pos2.x - pos1.x
        val yDiff = pos2.y - pos1.y
        val anti1 = Coord(pos1.x - xDiff, pos1.y - yDiff)
        val anti2 = Coord(pos2.x + xDiff, pos2.y + yDiff)
        return listOf(anti1, anti2)
    }

    fun part1(input: List<String>): Int {
        val grid = input.map { it.toCharArray() }
        val signals = mutableMapOf<Char, MutableSet<Coord>>()

        for (x in grid.indices) {
            for (y in grid[x].indices) {
                val signal = grid[x][y]
                if (signal != '.') {
                    if (signals[signal] == null) {
                        signals[signal] = mutableSetOf()
                    }
                    signals[signal]!!.add(Coord(x, y))
                }
            }
        }

        val antinodes = mutableSetOf<Coord>()

        for (signal in signals) {
            for (pos1 in signal.value) {
                for (pos2 in signal.value) {
                    if (pos1 != pos2) {
                        val nodes = getAntinodes(pos1, pos2)
                        for (node in nodes) {
                            if (node.x in grid.indices && node.y in grid[0].indices) {
                                antinodes.add(node)
                            }
                        }
                    }
                }
            }
        }

        println(signals)

        return antinodes.size
    }

    fun getMoreAntinodes(pos1: Coord, pos2: Coord, maxX: Int, maxY: Int): List<Coord> {
        val antinodes = mutableListOf<Coord>()
        val xDiff = pos2.x - pos1.x
        val yDiff = pos2.y - pos1.y
        var posx = pos1.x
        var posy = pos1.y
        while (posx >= 0 && posx < maxX && posy >= 0 && posy < maxY) {
            antinodes.add(Coord(posx, posy))
            posx += xDiff
            posy += yDiff
        }
        posx = pos2.x
        posy = pos2.y
        while (posx >= 0 && posx < maxX && posy >= 0 && posy < maxY) {
            antinodes.add(Coord(posx, posy))
            posx -= xDiff
            posy -= yDiff
        }
        return antinodes
    }

    fun part2(input: List<String>): Int {
        val grid = input.map { it.toCharArray() }
        val signals = mutableMapOf<Char, MutableSet<Coord>>()

        for (x in grid.indices) {
            for (y in grid[x].indices) {
                val signal = grid[x][y]
                if (signal != '.') {
                    if (signals[signal] == null) {
                        signals[signal] = mutableSetOf()
                    }
                    signals[signal]!!.add(Coord(x, y))
                }
            }
        }

        val antinodes = mutableSetOf<Coord>()

        for (signal in signals) {
            for (pos1 in signal.value) {
                for (pos2 in signal.value) {
                    if (pos1 != pos2) {
                        val nodes = getMoreAntinodes(pos1, pos2, grid.size, grid[0].size)
                        for (node in nodes) {
                            if (node.x in grid.indices && node.y in grid[0].indices) {
                                antinodes.add(node)
                            }
                        }
                    }
                }
            }
        }

        println(signals)

        return antinodes.size
    }

    val testInput = readInputString("day2408/test")
    val input = readInputString("day2408/input")

    check(part1(testInput) == 14)
    val timer1 = measureTimeMillis {
        println(part1(input))
    }
    println("Part 1 took $timer1 ms\n")

    check(part2(testInput) == 34)
    val timer2 = measureTimeMillis {
        println(part2(input))
    }
    println("Part 2 took $timer2 ms")
}
