package day17

import readInputString
import java.lang.Integer.min
import java.util.PriorityQueue
import kotlin.system.measureTimeMillis

enum class Direction { U, D, L, R }
data class Node (
    val x: Int,
    val y: Int,
    var dir: Direction,
    var consecutive: Int = 0,
    var shortest: Int = Int.MAX_VALUE,
    var visited: Boolean = false
)

fun main() {
//    fun part1(input: List<String>): Int {
//        val grid = input.map { it.toList() }
//        val comparator = compareBy<State> { (grid[0].size - it.x) * 5 + (grid.size - it.y) * 5 + it.total }
//        val workQ = PriorityQueue(comparator)
//        workQ.add(State(x = 0, y = 0, dir = Direction.D, consecutive = 0, total = 0))
//        var best = 0
//        for (i in 0 until input.size - 1) {
//            best += input[i][i].toString().toInt()
//            best += input[i+1][i].toString().toInt()
//        }
//        best += input[input.size-1][input.size-1].toString().toInt()
//
//        while(workQ.isNotEmpty()) {
//            val c = workQ.remove()
//            val nexts = mutableListOf<State>()
//            when (c.dir) {
//                Direction.U -> { nexts.add(State(x = c.x - 1, y = c.y, dir = Direction.L, consecutive = 0, total = c.total ))
//                    nexts.add(State(x = c.x + 1, y = c.y, dir = Direction.R, consecutive = 0, total = c.total ))
//                    nexts.add(State(x = c.x, y = c.y - 1, dir = Direction.U, consecutive = c.consecutive + 1, total = c.total ))}
//                Direction.D -> { nexts.add(State(x = c.x - 1, y = c.y, dir = Direction.L, consecutive = 0, total = c.total ))
//                    nexts.add(State(x = c.x + 1, y = c.y, dir = Direction.R, consecutive = 0, total = c.total ))
//                    nexts.add(State(x = c.x, y = c.y + 1, dir = Direction.D, consecutive = c.consecutive + 1, total = c.total ))}
//                Direction.L -> { nexts.add(State(x = c.x - 1, y = c.y, dir = Direction.L, consecutive = c.consecutive + 1, total = c.total ))
//                    nexts.add(State(x = c.x, y = c.y - 1, dir = Direction.U, consecutive = 0, total = c.total ))
//                    nexts.add(State(x = c.x, y = c.y + 1, dir = Direction.D, consecutive = 0, total = c.total ))}
//                Direction.R -> { nexts.add(State(x = c.x + 1, y = c.y, dir = Direction.R, consecutive = c.consecutive + 1, total = c.total ))
//                    nexts.add(State(x = c.x, y = c.y - 1, dir = Direction.U, consecutive = 0, total = c.total ))
//                    nexts.add(State(x = c.x, y = c.y + 1, dir = Direction.D, consecutive = 0, total = c.total ))}
//            }
//
//            nexts.removeIf { it.consecutive > 2 }
//            nexts.removeIf { it.x < 0 || it.x >= grid[0].size }
//            nexts.removeIf { it.y < 0 || it.y >= grid.size }
//            for (n in nexts) {
//                val h = grid[n.y][n.x].toString().toInt()
//                if (n.x == grid[0].size - 1 && n.y == grid.size - 1) {
//                    best = min(best, n.total + h)
//                    println("New best is $best")
//                } else {
//                    workQ.add(State(x = n.x, y = n.y, dir = n.dir, consecutive = n.consecutive, total = n.total + h))
//                }
//            }
//
//            workQ.removeIf { (grid[0].size - it.x) + (grid.size - it.y) + it.total > best }
//            for (q in workQ.toList()) {
//                workQ.removeIf { it.x == q.x && it.y == q.y && it.total > q.total }
//            }
//        }
//
//        return best
//    }

    fun printNodes(nodes: List<Node>) {
        val maxX = nodes.maxByOrNull { it.x }!!.x
        val maxY = nodes.maxByOrNull { it.y }!!.y
        for (y in 0..maxY) {
            for (x in 0..maxX) {
                val node = nodes.filter { it.x == x && it.y == y }.sortedBy { it.shortest }.first()
                if (node.shortest < Int.MAX_VALUE)
                    print("%4d".format(node.shortest))
                else
                    print("   -")
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
//                    next.visited = false
                }
            }

            cur.visited = true
            cur = nodes.filter { !it.visited && it.shortest != Int.MAX_VALUE }.minByOrNull { it.shortest }
            println("Unvisted = ${nodes.count { !it.visited }}")
        }

        printNodes(nodes)
        return nodes.filter { it.x == grid[0].size - 1 && it.y == grid.size - 1 }.minByOrNull { it.shortest }!!.shortest
    }

    fun part2(input: List<String>): Int {
        val grid = input.map { it.toList() }
        val nodes = mutableListOf<Node>()
        for (y in grid.indices) {
            for (x in grid[0].indices) {
                for (dir in Direction.values()) {
                    for (cons in 0..10) {
                        nodes.add(Node(x = x, y = y, consecutive = cons, dir = dir))
                    }
                }
            }
        }

        nodes.filter { it.x == 0 && it.y == 0 && it.consecutive == 0 }.forEach { it.shortest = 0 }
        var cur = nodes.find { it.x == 0 && it.y == 0 && it.consecutive == 0 }!!

        while (true) {
            val valid = mutableListOf<Direction>()
            if (cur.consecutive < 10) {
                valid.add(cur.dir)
            }
            if (cur.consecutive > 2) {
                if (cur.dir != Direction.D && cur.dir != Direction.U)
                    valid.add(Direction.U)
                if (cur.dir != Direction.U && cur.dir != Direction.D)
                    valid.add(Direction.D)
                if (cur.dir != Direction.L && cur.dir != Direction.R)
                    valid.add(Direction.R)
                if (cur.dir != Direction.R && cur.dir != Direction.L)
                    valid.add(Direction.L)
            }
            if (cur.x == 0)
                valid.removeIf { it == Direction.L }
            if (cur.x == grid[0].size - 1)
                valid.removeIf { it == Direction.R }
            if (cur.y == 0)
                valid.removeIf { it == Direction.U }
            if (cur.y == grid.size - 1)
                valid.removeIf { it == Direction.D }

            for (dir in valid) {
                val cons = if (dir == cur.dir) cur.consecutive + 1 else 0
                val next = when (dir) {
                    Direction.U -> nodes.find { it.x == cur.x && it.y == cur.y - 1 && it.consecutive == cons && it.dir == dir }
                    Direction.D -> nodes.find { it.x == cur.x && it.y == cur.y + 1 && it.consecutive == cons && it.dir == dir }
                    Direction.L -> nodes.find { it.x == cur.x - 1 && it.y == cur.y && it.consecutive == cons && it.dir == dir }
                    Direction.R -> nodes.find { it.x == cur.x + 1 && it.y == cur.y && it.consecutive == cons && it.dir == dir }
                }!!
                val stepSize = grid[next.y][next.x].toString().toInt()
                if (next.x == grid[0].size - 1 && next.y == grid.size - 1) {
                    if (cur.consecutive > 2 && next.shortest > cur.shortest + stepSize) {
                        next.shortest = cur.shortest + stepSize
                    }
                } else if (next.shortest > cur.shortest + stepSize) {
                    next.shortest = cur.shortest + stepSize
                }
            }

            cur.visited = true
            cur = nodes.filter { !it.visited && it.shortest != Int.MAX_VALUE }.minByOrNull { it.shortest } ?: break
            val untouched = nodes.count { !it.visited }
            if (untouched % 1_000 == 0)
                println("Unvisted = $untouched")
        }

        printNodes(nodes)
        return nodes.filter { it.x == grid[0].size - 1 && it.y == grid.size - 1 }.minByOrNull { it.shortest }!!.shortest
    }

    val testInput = readInputString("day17/test")
    val input = readInputString("day17/input")

//    check(part1(testInput) == 102)
//    val timer1 = measureTimeMillis {
//        println(part1(input))
//    }
//    println("Part 1 took $timer1 ms\n")

    check(part2(testInput) == 94)
    val timer2 = measureTimeMillis {
        println(part2(input))
    }
    println("Part 2 took $timer2 ms")
}
