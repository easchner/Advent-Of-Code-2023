package year2015.day09

import utils.readInputString
import kotlin.math.max
import kotlin.math.min
import kotlin.system.measureTimeMillis

data class Node(val name: String, val paths: MutableList<Pair<String, Int>>, var visited: Boolean = false)

fun main() {
    fun shortest(node: Node, nodes: List<Node>, distance: Int): Int {
        node.visited = true

        if (nodes.all { it.visited }) {
            node.visited = false
            return distance
        }

        var shortest = 100_000
        for (path in node.paths) {
            val next = nodes.find { it.name == path.first }!!
            if (!next.visited) {
                val d = shortest(next, nodes, distance + path.second)
                shortest = min(d, shortest)
            }
        }

        node.visited = false
        return shortest
    }

    fun longest(node: Node, nodes: List<Node>, distance: Int): Int {
        node.visited = true

        if (nodes.all { it.visited }) {
            node.visited = false
            return distance
        }

        var long = 0
        for (path in node.paths) {
            val next = nodes.find { it.name == path.first }!!
            if (!next.visited) {
                val d = longest(next, nodes, distance + path.second)
                long = max(d, long)
            }
        }

        node.visited = false
        return long
    }

    fun part1(input: List<String>): Int {
        val nodes = mutableListOf<Node>()
        for (line in input) {
            val (from, to, distance) = line.split(" to ", " = ")
            if (nodes.find { it.name == from } == null) {
                nodes.add(Node(from, mutableListOf()))
            }
            if (nodes.find { it.name == to } == null) {
                nodes.add(Node(to, mutableListOf()))
            }
            nodes.find { it.name == from }!!.paths += Pair(to, distance.toInt())
            nodes.find { it.name == to }!!.paths += Pair(from, distance.toInt())
        }

        return nodes.minOf { shortest(it, nodes, 0) }
    }

    fun part2(input: List<String>): Int {
        val nodes = mutableListOf<Node>()
        for (line in input) {
            val (from, to, distance) = line.split(" to ", " = ")
            if (nodes.find { it.name == from } == null) {
                nodes.add(Node(from, mutableListOf()))
            }
            if (nodes.find { it.name == to } == null) {
                nodes.add(Node(to, mutableListOf()))
            }
            nodes.find { it.name == from }!!.paths += Pair(to, distance.toInt())
            nodes.find { it.name == to }!!.paths += Pair(from, distance.toInt())
        }

        return nodes.maxOf { longest(it, nodes, 0) }
    }

    val test = readInputString("year2015/day09/test")
    val input = readInputString("year2015/day09/input")

    check(part1(test) == 605)
    val timer1 = measureTimeMillis {
        println(part1(input))
    }
    println("Part 1 took $timer1 ms\n")

    check(part2(test) == 982)
    val timer2 = measureTimeMillis {
        println(part2(input))
    }
    println("Part 2 took $timer2 ms")
}
