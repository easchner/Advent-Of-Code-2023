package year2024.day16

import utils.readInputString
import kotlin.math.min
import kotlin.system.measureTimeMillis

data class Node(val x: Int, val y: Int, val d: Direction, val s: Long, val visited: Set<Pair<Int, Int>> = setOf())
enum class Direction { UP, DOWN, LEFT, RIGHT }

fun main() {
    fun dfs(grid: List<CharArray>, x: Int, y: Int, d: Direction): Long {
        val toVisit = mutableSetOf(Node(x, y, d, 0L))

        while (toVisit.isNotEmpty()) {
            val node = toVisit.minBy { it.s }
            toVisit.remove(node)

            if (grid[node.y][node.x] == 'E')
                return node.s

            if (grid[node.y][node.x] == '.' || grid[node.y][node.x] == 'S') {
                when (node.d) {
                    Direction.UP -> {
                        toVisit.add(Node(node.x, node.y - 1, Direction.UP, node.s + 1))
                        toVisit.add(Node(node.x, node.y, Direction.RIGHT, node.s + 1000))
                        toVisit.add(Node(node.x, node.y, Direction.LEFT, node.s + 1000))
                    }
                    Direction.DOWN -> {
                        toVisit.add(Node(node.x, node.y + 1, Direction.DOWN, node.s + 1))
                        toVisit.add(Node(node.x, node.y, Direction.RIGHT, node.s + 1000))
                        toVisit.add(Node(node.x, node.y, Direction.LEFT, node.s + 1000))
                    }
                    Direction.LEFT -> {
                        toVisit.add(Node(node.x - 1, node.y, Direction.LEFT, node.s + 1))
                        toVisit.add(Node(node.x, node.y, Direction.UP, node.s + 1000))
                        toVisit.add(Node(node.x, node.y, Direction.DOWN, node.s + 1000))
                    }
                    Direction.RIGHT -> {
                        toVisit.add(Node(node.x + 1, node.y, Direction.RIGHT, node.s + 1))
                        toVisit.add(Node(node.x, node.y, Direction.UP, node.s + 1000))
                        toVisit.add(Node(node.x, node.y, Direction.DOWN, node.s + 1000))
                    }
                }
            }
        }

        return -1
    }

    fun dfs2(grid: List<CharArray>, x: Int, y: Int, d: Direction, max: Long): Long {
        val toVisit = mutableSetOf(Node(x, y, d, 0L))
        var minFound = Long.MAX_VALUE
        val visited = mutableSetOf<Node>()
        val bestRoutes = mutableSetOf<Pair<Int, Int>>()

        var i = 0
        while (toVisit.isNotEmpty()) {
            var node = toVisit.minBy { it.s }
            toVisit.remove(node)
            visited.add(Node(node.x, node.y, node.d, node.s))
            for (tv in toVisit.filter { it.x == node.x && it.y == node.y && it.d == node.d && it.s == node.s }) {
                node = Node(node.x, node.y, node.d, node.s, node.visited + tv.visited)
                toVisit.remove(tv)
            }

            if (grid[node.y][node.x] == 'E') {
                minFound = min(minFound, node.s)
                for (pair in node.visited) {
                    bestRoutes.add(pair)
                }
            }

            if (grid[node.y][node.x] == '.' || grid[node.y][node.x] == 'S') {
                when (node.d) {
                    Direction.UP -> {
                        toVisit.add(Node(node.x, node.y - 1, Direction.UP, node.s + 1, node.visited + Pair(node.x, node.y)))
                        toVisit.add(Node(node.x, node.y, Direction.RIGHT, node.s + 1000, node.visited))
                        toVisit.add(Node(node.x, node.y, Direction.LEFT, node.s + 1000, node.visited))
                    }
                    Direction.DOWN -> {
                        toVisit.add(Node(node.x, node.y + 1, Direction.DOWN, node.s + 1, node.visited + Pair(node.x, node.y)))
                        toVisit.add(Node(node.x, node.y, Direction.RIGHT, node.s + 1000, node.visited))
                        toVisit.add(Node(node.x, node.y, Direction.LEFT, node.s + 1000, node.visited))
                    }
                    Direction.LEFT -> {
                        toVisit.add(Node(node.x - 1, node.y, Direction.LEFT, node.s + 1, node.visited + Pair(node.x, node.y)))
                        toVisit.add(Node(node.x, node.y, Direction.UP, node.s + 1000, node.visited))
                        toVisit.add(Node(node.x, node.y, Direction.DOWN, node.s + 1000, node.visited))
                    }
                    Direction.RIGHT -> {
                        toVisit.add(Node(node.x + 1, node.y, Direction.RIGHT, node.s + 1, node.visited + Pair(node.x, node.y)))
                        toVisit.add(Node(node.x, node.y, Direction.UP, node.s + 1000, node.visited))
                        toVisit.add(Node(node.x, node.y, Direction.DOWN, node.s + 1000, node.visited))
                    }
                }
            }

            toVisit.removeAll { it.s > max }
            toVisit.removeAll { grid[it.y][it.x] == '#' }
            toVisit.removeAll { tv -> visited.any { v -> v.x == tv.x && v.y == tv.y && v.d == tv.d && v.s < tv.s } }

            if (i % 1000 == 0) {
                println("i: $i, minFound: ${toVisit.minOf { it.s }}, toVisit: ${toVisit.size}, visited: ${visited.size}")
            }
            i++
        }

        return bestRoutes.count().toLong()
    }

    fun part1(input: List<String>): Long {
        val grid = input.map { it.toCharArray() }
        var x = 0
        var y = 0

        for (row in grid.indices) {
            for (col in grid[0].indices) {
                if (grid[row][col] == 'S') {
                    x = col
                    y = row
                }
            }
        }

        return dfs(grid, x, y, Direction.RIGHT)
    }

    fun part2(input: List<String>, max: Long): Long {
        val grid = input.map { it.toCharArray() }
        var x = 0
        var y = 0

        for (row in grid.indices) {
            for (col in grid[0].indices) {
                if (grid[row][col] == 'S') {
                    x = col
                    y = row
                }
            }
        }

//        println(dfs2(grid, x, y, Direction.RIGHT, max))
        return dfs2(grid, x, y, Direction.RIGHT, max) + 1
    }

    val testInput = readInputString("year2024/day16/test")
    val input = readInputString("year2024/day16/input")

    check(part1(testInput) == 7036L)
    val timer1 = measureTimeMillis {
        println(part1(input))
    }
    println("Part 1 took $timer1 ms\n")

    check(part2(testInput, 7036L) == 45L)
    val timer2 = measureTimeMillis {
        println(part2(input, 102460L))
    }
    println("Part 2 took $timer2 ms")
}