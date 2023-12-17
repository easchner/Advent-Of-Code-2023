package day17

import readInputString
import java.util.PriorityQueue
import kotlin.system.measureTimeMillis

enum class Direction { U, D, L, R }
data class Node (
    val x: Int,
    val y: Int,
    var dir: Direction? = null,
    var consecutive: Int = 0,
    var shortest: Int = Int.MAX_VALUE,
    var visited: Boolean = false
)

fun main() {
    val nodeComp: Comparator<Node> = compareBy { it.shortest }

    fun printNodes(nodes: List<Node>) {
        val maxX = nodes.maxByOrNull { it.x }!!.x
        val maxY = nodes.maxByOrNull { it.y }!!.y
        for (y in 0..maxY) {
            for (x in 0..maxX) {
                val node = nodes.filter { it.x == x && it.y == y }.minByOrNull { it.shortest }
                if (node != null && node.shortest < Int.MAX_VALUE)
                    print("%4d".format(node.shortest))
                else
                    print("   -")
                if (node == null)
                    print(".")
                else
                    print(when(node.dir) {
                        Direction.D -> "v"
                        Direction.U -> "^"
                        Direction.L -> "<"
                        Direction.R -> ">"
                        else -> "S"
                    })
            }
            println()
        }
    }

    fun part1(input: List<String>): Int {
        val grid = input.map { it.toList() }
        val nodes = mutableListOf<Node>()
        for (y in grid.indices) {
            for (x in grid[0].indices) {
                for (dir in Direction.values()) {
                    for (cons in 0..2) {
                        nodes.add(Node(x = x, y = y, consecutive = cons, dir = dir))
                    }
                }
            }
        }

        nodes.filter { it.x == 0 && it.y == 0 }.forEach { it.shortest = 0 }
        var cur = nodes.find { it.x == 0 && it.y == 0 }

        while (cur != null) {
            val valid = mutableListOf<Direction>()
            if (cur.dir != Direction.D && (cur.dir != Direction.U || cur.consecutive < 2) && cur.y - 1 >= 0)
                valid.add(Direction.U)
            if (cur.dir != Direction.U && (cur.dir != Direction.D || cur.consecutive < 2) && cur.y + 1 < grid.size)
                valid.add(Direction.D)
            if (cur.dir != Direction.L && (cur.dir != Direction.R || cur.consecutive < 2) && cur.x + 1 < grid[0].size)
                valid.add(Direction.R)
            if (cur.dir != Direction.R && (cur.dir != Direction.L || cur.consecutive < 2) && cur.x - 1 >= 0)
                valid.add(Direction.L)

            for (dir in valid) {
                val cons = if (dir == cur.dir) cur.consecutive + 1 else 0
                val next = when (dir) {
                    Direction.U -> nodes.find { it.x == cur!!.x && it.y == cur!!.y - 1 && it.consecutive == cons && it.dir == dir }
                    Direction.D -> nodes.find { it.x == cur!!.x && it.y == cur!!.y + 1 && it.consecutive == cons && it.dir == dir }
                    Direction.L -> nodes.find { it.x == cur!!.x - 1 && it.y == cur!!.y && it.consecutive == cons && it.dir == dir }
                    Direction.R -> nodes.find { it.x == cur!!.x + 1 && it.y == cur!!.y && it.consecutive == cons && it.dir == dir }
                }!!
                val stepSize = grid[next.y][next.x].toString().toInt()
                if (next.shortest > cur.shortest + stepSize) {
                    next.shortest = cur.shortest + stepSize
                }
            }

            cur.visited = true
            cur = nodes.filter { !it.visited && it.shortest != Int.MAX_VALUE }.minByOrNull { it.shortest }
            println("Unvisted = ${nodes.count { !it.visited }}")
        }

        printNodes(nodes)
        return nodes.filter { it.x == grid[0].size - 1 && it.y == grid.size - 1 }.minByOrNull { it.shortest }!!.shortest
    }

    fun getCandidatesPt1(node: Node, grid: List<List<Int>>): List<Node> {
        val candidates = mutableListOf<Node>()
        if (node.dir != Direction.D && node.dir != Direction.U) { // UP
            var shortest = node.shortest
            for (s in 1..3) {
                shortest += if (node.y - s >= 0) grid[node.y-s][node.x] else 0
                candidates.add(Node(node.x, node.y - s, Direction.U, 0, shortest, false))
            }
        }
        if (node.dir != Direction.D && node.dir != Direction.U) { // DOWN
            var shortest = node.shortest
            for (s in 1..3) {
                shortest += if (node.y + s < grid.size) grid[node.y+s][node.x] else 0
                candidates.add(Node(node.x, node.y + s, Direction.D, 0, shortest, false))
            }
        }
        if (node.dir != Direction.L && node.dir != Direction.R) { // LEFT
            var shortest = node.shortest
            for (s in 1..3) {
                shortest += if (node.x - s >= 0) grid[node.y][node.x-s] else 0
                candidates.add(Node(node.x - s, node.y, Direction.L, 0, shortest, false))
            }
        }
        if (node.dir != Direction.L && node.dir != Direction.R) { // RIGHT
            var shortest = node.shortest
            for (s in 1..3) {
                shortest += if (node.x + s < grid[0].size) grid[node.y][node.x+s] else 0
                candidates.add(Node(node.x + s, node.y, Direction.R, 0, shortest, false))
            }
        }

        candidates.removeAll { it.x < 0 || it.x >= grid[0].size || it.y < 0 || it.y >= grid.size }
        return candidates
    }

    fun part1Good(input: List<String>): Int {
        val grid = input.map { row -> row.toList().map { col -> col.toString().toInt() } }
        val first = Node(0, 0, null, 0, 0, false)
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

    fun getCandidates(node: Node, grid: List<List<Int>>): List<Node> {
        val candidates = mutableListOf<Node>()
        if (node.dir != Direction.D && node.dir != Direction.U) { // UP
            var shortest = node.shortest
            for (h in 1..3) {
                shortest += if (node.y - h >= 0) grid[node.y-h][node.x] else 0
            }
            for (s in 4..10) {
                shortest += if (node.y - s >= 0) grid[node.y-s][node.x] else 0
                candidates.add(Node(node.x, node.y - s, Direction.U, 0, shortest, false))
            }
        }
        if (node.dir != Direction.D && node.dir != Direction.U) { // DOWN
            var shortest = node.shortest
            for (h in 1..3) {
                shortest += if (node.y + h < grid.size) grid[node.y+h][node.x] else 0
            }
            for (s in 4..10) {
                shortest += if (node.y + s < grid.size) grid[node.y+s][node.x] else 0
                candidates.add(Node(node.x, node.y + s, Direction.D, 0, shortest, false))
            }
        }
        if (node.dir != Direction.L && node.dir != Direction.R) { // LEFT
            var shortest = node.shortest
            for (h in 1..3) {
                shortest += if (node.x - h >= 0) grid[node.y][node.x-h] else 0
            }
            for (s in 4..10) {
                shortest += if (node.x - s >= 0) grid[node.y][node.x-s] else 0
                candidates.add(Node(node.x - s, node.y, Direction.L, 0, shortest, false))
            }
        }
        if (node.dir != Direction.L && node.dir != Direction.R) { // RIGHT
            var shortest = node.shortest
            for (h in 1..3) {
                shortest += if (node.x + h < grid[0].size) grid[node.y][node.x+h] else 0
            }
            for (s in 4..10) {
                shortest += if (node.x + s < grid[0].size) grid[node.y][node.x+s] else 0
                candidates.add(Node(node.x + s, node.y, Direction.R, 0, shortest, false))
            }
        }

        candidates.removeAll { it.x < 0 || it.x >= grid[0].size || it.y < 0 || it.y >= grid.size }
        return candidates
    }

    fun part2(input: List<String>): Int {
        val grid = input.map { row -> row.toList().map { col -> col.toString().toInt() } }
        val first = Node(0, 0, null, 0, 0, false)
        val nodes = mutableListOf(first)
        val pq = PriorityQueue(nodeComp)
        pq.add(first)

        while (pq.isNotEmpty()) {
            val cur = pq.remove()
            if (cur.x == grid[0].size && cur.y == grid.size) break
            val candidates = getCandidates(cur, grid)

            for (c in candidates) {
                val existing = nodes.find { it.x == c.x && it.y == c.y && it.dir == c.dir }
                if (existing != null && existing.shortest > c.shortest)
                    existing.shortest = c.shortest
                if (existing == null) {
                    nodes.add(c)
                    pq.add(c)
                }
            }

            if (nodes.size % 100 == 0)
                println("Visited = ${nodes.size}")
        }

        printNodes(nodes)
        return nodes.filter { it.x == grid[0].size - 1 && it.y == grid.size - 1 }.minByOrNull { it.shortest }!!.shortest
    }

    fun part2Good(input: List<String>): Int {
        val grid = input.map { row -> row.toList().map { col -> col.toString().toInt() } }
        val first = Node(0, 0, null, 0, 0, false)
        val nodes = Array<Array<IntArray>>(4) { Array(grid[0].size) { IntArray(grid.size) { Int.MAX_VALUE } } }
        val pq = PriorityQueue(nodeComp)
        pq.add(first)

        while (pq.isNotEmpty()) {
            val cur = pq.remove()
            if (cur.x == grid[0].size && cur.y == grid.size) break
            val candidates = getCandidates(cur, grid)

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

    check(part1Good(testInput) == 102)
    val timer1 = measureTimeMillis {
        println(part1Good(input))
    }
    println("Part 1 took $timer1 ms\n")

    check(part2Good(testInput) == 94)
    val timer2 = measureTimeMillis {
        println(part2Good(input))
    }
    println("Part 2 took $timer2 ms")
}
