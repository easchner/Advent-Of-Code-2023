package day17

import readInputString
import java.util.PriorityQueue
import kotlin.system.measureTimeMillis

enum class Direction { U, D, L, R }
data class Node (
    val x: Int,
    val y: Int,
    var dir: Direction? = null,
    var shortest: Int = Int.MAX_VALUE
)

fun main() {
    val nodeComp: Comparator<Node> = compareBy { it.shortest }

    fun getCandidatesPt1(node: Node, grid: List<List<Int>>): List<Node> {
        val candidates = mutableListOf<Node>()
        if (node.dir != Direction.D && node.dir != Direction.U) { // VERTICAL
            var shortestU = node.shortest
            var shortestD = node.shortest
            for (s in 1..3) {
                shortestU += if (node.y - s >= 0) grid[node.y-s][node.x] else 0
                candidates.add(Node(node.x, node.y - s, Direction.U, shortestU))

                shortestD += if (node.y + s < grid.size) grid[node.y+s][node.x] else 0
                candidates.add(Node(node.x, node.y + s, Direction.D, shortestD))
            }
        }
        if (node.dir != Direction.L && node.dir != Direction.R) { // HORIZONTAL
            var shortestL = node.shortest
            var shortestR = node.shortest
            for (s in 1..3) {
                shortestL += if (node.x - s >= 0) grid[node.y][node.x-s] else 0
                candidates.add(Node(node.x - s, node.y, Direction.L, shortestL))

                shortestR += if (node.x + s < grid[0].size) grid[node.y][node.x+s] else 0
                candidates.add(Node(node.x + s, node.y, Direction.R, shortestR))
            }
        }

        candidates.removeAll { it.x < 0 || it.x >= grid[0].size || it.y < 0 || it.y >= grid.size }
        return candidates
    }

    fun part1(input: List<String>): Int {
        val grid = input.map { row -> row.toList().map { col -> col.toString().toInt() } }
        val first = Node(0, 0, null, 0)
        val nodes = Array<Array<IntArray>>(4) { Array(grid[0].size) { IntArray(grid.size) { Int.MAX_VALUE } } }
        val pq = PriorityQueue(nodeComp)
        pq.add(first)

        while (pq.isNotEmpty()) {
            val cur = pq.remove()
            if (cur.x == grid[0].size && cur.y == grid.size) break
            val candidates = getCandidatesPt1(cur, grid)

            for (c in candidates) {
                val existing = nodes[c.dir?.ordinal ?: 0][c.x][c.y]
                if (existing > c.shortest) {
                    nodes[c.dir?.ordinal ?: 0][c.x][c.y] = c.shortest
                    pq.add(c)
                }
            }

            if (nodes.size % 100 == 0)
                println("Visited = ${nodes.size}")
        }

        return Direction.entries.minOfOrNull { nodes[it.ordinal][grid[0].size - 1][grid.size - 1] }!!
    }

    fun getCandidatesPt2(node: Node, grid: List<List<Int>>): List<Node> {
        val candidates = mutableListOf<Node>()
        if (node.dir != Direction.D && node.dir != Direction.U) { // VERTICAL
            var shortestU = node.shortest
            var shortestD = node.shortest
            for (h in 1..3) {
                shortestU += if (node.y - h >= 0) grid[node.y-h][node.x] else 0
                shortestD += if (node.y + h < grid.size) grid[node.y+h][node.x] else 0
            }
            for (s in 4..10) {
                shortestU += if (node.y - s >= 0) grid[node.y-s][node.x] else 0
                candidates.add(Node(node.x, node.y - s, Direction.U, shortestU))

                shortestD += if (node.y + s < grid.size) grid[node.y+s][node.x] else 0
                candidates.add(Node(node.x, node.y + s, Direction.D, shortestD))
            }
        }
        if (node.dir != Direction.L && node.dir != Direction.R) { // HORIZONTAL
            var shortestL = node.shortest
            var shortestR = node.shortest
            for (h in 1..3) {
                shortestL += if (node.x - h >= 0) grid[node.y][node.x-h] else 0
                shortestR += if (node.x + h < grid[0].size) grid[node.y][node.x+h] else 0
            }
            for (s in 4..10) {
                shortestL += if (node.x - s >= 0) grid[node.y][node.x-s] else 0
                candidates.add(Node(node.x - s, node.y, Direction.L, shortestL))

                shortestR += if (node.x + s < grid[0].size) grid[node.y][node.x+s] else 0
                candidates.add(Node(node.x + s, node.y, Direction.R, shortestR))
            }
        }

        candidates.removeAll { it.x < 0 || it.x >= grid[0].size || it.y < 0 || it.y >= grid.size }
        return candidates
    }

    fun part2(input: List<String>): Int {
        val grid = input.map { row -> row.toList().map { col -> col.toString().toInt() } }
        val first = Node(0, 0, null, 0)
        val nodes = Array<Array<IntArray>>(4) { Array(grid[0].size) { IntArray(grid.size) { Int.MAX_VALUE } } }
        val pq = PriorityQueue(nodeComp)
        pq.add(first)

        while (pq.isNotEmpty()) {
            val cur = pq.remove()
            if (cur.x == grid[0].size && cur.y == grid.size) break
            val candidates = getCandidatesPt2(cur, grid)

            for (c in candidates) {
                val existing = nodes[c.dir?.ordinal ?: 0][c.x][c.y]
                if (existing > c.shortest) {
                    nodes[c.dir?.ordinal ?: 0][c.x][c.y] = c.shortest
                    pq.add(c)
                }
            }

            if (nodes.size % 100 == 0)
                println("Visited = ${nodes.size}")
        }

        return Direction.entries.minOfOrNull { nodes[it.ordinal][grid[0].size - 1][grid.size - 1] }!!
    }

    val testInput = readInputString("day17/test")
    val input = readInputString("day17/input")

    check(part1(testInput) == 102)
    val timer1 = measureTimeMillis {
        println(part1(input))
    }
    println("Part 1 took $timer1 ms\n")

    check(part2(testInput) == 94)
    val timer2 = measureTimeMillis {
        println(part2(input))
    }
    println("Part 2 took $timer2 ms")
}
