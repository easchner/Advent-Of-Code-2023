package year2023.day16

import utils.readInputString
import java.lang.Long.max
import kotlin.system.measureTimeMillis

enum class Direction { U, D, L, R }
data class Beam (val x: Int, val y: Int, val dir: Direction)

fun main() {
    fun moveBeam (grid: List<List<Char>>, beam: Beam): List<Beam> {
        val next = mutableListOf<Beam>()
        val cur = grid[beam.y][beam.x]
        val vertical = beam.dir == Direction.U || beam.dir == Direction.D
        val horizontal = beam.dir == Direction.L || beam.dir == Direction.R

        if (cur == '.' || (vertical && cur == '|') || (horizontal && cur == '-')) {
            val nextBeam = when (beam.dir) {
                Direction.U -> Beam(beam.x, beam.y - 1, Direction.U)
                Direction.D -> Beam(beam.x, beam.y + 1, Direction.D)
                Direction.L -> Beam(beam.x - 1, beam.y, Direction.L)
                Direction.R -> Beam(beam.x + 1, beam.y, Direction.R)
            }
            if (nextBeam.x in grid[0].indices && nextBeam.y in grid.indices) {
                next.add(nextBeam)
            }
        }

        if (cur == '|' && horizontal) {
            var nextPt = Pair(beam.x, beam.y - 1)
            if (nextPt.first in grid[0].indices && nextPt.second in grid.indices) {
                next.add(Beam(x = nextPt.first, y = nextPt.second, dir = Direction.U))
            }
            nextPt = Pair(beam.x, beam.y + 1)
            if (nextPt.first in grid[0].indices && nextPt.second in grid.indices) {
                next.add(Beam(x = nextPt.first, y = nextPt.second, dir = Direction.D))
            }
        }

        if (cur == '-' && vertical) {
            var nextPt = Pair(beam.x - 1, beam.y)
            if (nextPt.first in grid[0].indices && nextPt.second in grid.indices) {
                next.add(Beam(x = nextPt.first, y = nextPt.second, Direction.L))
            }
            nextPt = Pair(beam.x + 1, beam.y)
            if (nextPt.first in grid[0].indices && nextPt.second in grid.indices) {
                next.add(Beam(x = nextPt.first, y = nextPt.second, Direction.R))
            }
        }

        if (cur == '\\') {
            val nextBeam = when (beam.dir) {
                Direction.U -> Beam(beam.x - 1, beam.y, Direction.L)
                Direction.D -> Beam(beam.x + 1, beam.y, Direction.R)
                Direction.L -> Beam(beam.x, beam.y - 1, Direction.U)
                Direction.R -> Beam(beam.x, beam.y + 1, Direction.D)
            }
            if (nextBeam.x in grid[0].indices && nextBeam.y in grid.indices) {
                next.add(nextBeam)
            }
        }

        if (cur == '/') {
            val nextBeam = when (beam.dir) {
                Direction.U -> Beam(beam.x + 1, beam.y, Direction.R)
                Direction.D -> Beam(beam.x - 1, beam.y, Direction.L)
                Direction.L -> Beam(beam.x, beam.y + 1, Direction.D)
                Direction.R -> Beam(beam.x, beam.y - 1, Direction.U)
            }
            if (nextBeam.x in grid[0].indices && nextBeam.y in grid.indices) {
                next.add(nextBeam)
            }
        }

        return next
    }

    fun printGrid(grid: List<List<Char>>, visited: List<Beam>) {
        for (y in grid.indices) {
            for (x in grid.indices) {
                if (visited.any { it.x == x && it.y == y })
                    print("#")
                else
                    print(".")
            }
            println()
        }
        println()
        println()
    }

    fun runBeams(grid: List<List<Char>>, starter: Beam): Long {
        val active = mutableListOf(starter)
        val visited = mutableListOf<Beam>()
        while (active.isNotEmpty()) {
            val current = active.removeFirst()
            visited.add(current)
            val nexts = moveBeam(grid, current)
            for (next in nexts) {
                if (!visited.contains(next) && !active.contains(next)) {
                    active.add(next)
                }
            }
        }

        return visited.map { Pair(it.x, it.y) }.toSet().size.toLong()
    }

    fun part1(input: List<String>): Long {
        val grid = input.map { it.toList() }
        return runBeams(grid, Beam(x = 0, y = 0, dir = Direction.R))
    }

    fun part2(input: List<String>): Long {
        val grid = input.map { it.toList() }
        var max = 0L
        val starters = mutableListOf<Beam>()
        for (y in grid.indices) {
            starters.add(Beam(x = 0, y = y, dir = Direction.R))
            starters.add(Beam(x = grid[0].size - 1, y = y, dir = Direction.L))
        }
        for (x in grid[0].indices) {
            starters.add(Beam(x = x, y = 0, dir = Direction.D))
            starters.add(Beam(x = x, y = grid.size - 1, dir = Direction.U))
        }

        for (starter in starters) {
            val energized = runBeams(grid, starter)
            max = max(max, energized)
//            println("$starter = $energized. Max = $max")
        }

        return max
    }

    val testInput = readInputString("day16/test")
    val input = readInputString("day16/input")

    println(part1(testInput))
    check(part1(testInput) == 46L)
    val timer1 = measureTimeMillis {
        println(part1(input))
    }
    println("Part 1 took $timer1 ms\n")

    check(part2(testInput) == 51L)
    val timer2 = measureTimeMillis {
        println(part2(input))
    }
    println("Part 2 took $timer2 ms")
}
