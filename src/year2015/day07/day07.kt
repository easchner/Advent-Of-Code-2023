package year2015.day07

import utils.readInputString
import kotlin.system.measureTimeMillis

data class Node (val name: String, var value: UShort = 0u, val operation: String, var updated: Boolean = false)

fun main() {
    fun updateNodes(nodes: List<Node>) {
        while (!nodes.first { it.name == "a" }.updated) {
            for (node in nodes) {
                if (!node.updated) {
                    val parts = node.operation.split(" ")
                    if (parts.size == 1) {
                        val firstValid = parts[0].toUShortOrNull() != null || nodes.first { it.name == parts[0] }.updated
                        if (firstValid) {
                            val first = if (parts[0].toUShortOrNull() != null) parts[0].toUShort() else nodes.first { it.name == parts[0] }.value
                            node.value = first
                            node.updated = true
                        }
                    } else if (parts.size == 2) {
                        val secondValid = parts[1].toUShortOrNull() != null || nodes.first { it.name == parts[1] }.updated
                        if (secondValid) {
                            val second = if (parts[1].toUShortOrNull() != null) parts[1].toUShort() else nodes.first { it.name == parts[1] }.value
                            node.value = second.inv()
                            node.updated = true
                        }
                    } else {
                        when (parts[1]) {
                            "AND" -> {
                                val firstValid = parts[0].toUShortOrNull() != null || nodes.first { it.name == parts[0] }.updated
                                val secondValid = parts[2].toUShortOrNull() != null || nodes.first { it.name == parts[2] }.updated
                                if (firstValid && secondValid) {
                                    val first = if (parts[0].toUShortOrNull() != null) parts[0].toUShort() else nodes.first { it.name == parts[0] }.value
                                    val second = if (parts[2].toUShortOrNull() != null) parts[2].toUShort() else nodes.first { it.name == parts[2] }.value
                                    node.value = (first.toInt() and second.toInt()).toUShort()
                                    node.updated = true
                                }
                            }
                            "OR" -> {
                                val firstValid = parts[0].toUShortOrNull() != null || nodes.first { it.name == parts[0] }.updated
                                val secondValid = parts[2].toUShortOrNull() != null || nodes.first { it.name == parts[2] }.updated
                                if (firstValid && secondValid) {
                                    val first = if (parts[0].toUShortOrNull() != null) parts[0].toUShort() else nodes.first { it.name == parts[0] }.value
                                    val second = if (parts[2].toUShortOrNull() != null) parts[2].toUShort() else nodes.first { it.name == parts[2] }.value
                                    node.value = (first.toInt() or second.toInt()).toUShort()
                                    node.updated = true
                                }
                            }
                            "LSHIFT" -> {
                                val firstValid = parts[0].toUShortOrNull() != null || nodes.first { it.name == parts[0] }.updated
                                val secondValid = parts[2].toUShortOrNull() != null || nodes.first { it.name == parts[2] }.updated
                                if (firstValid && secondValid) {
                                    val first = if (parts[0].toUShortOrNull() != null) parts[0].toUShort() else nodes.first { it.name == parts[0] }.value
                                    val second = if (parts[2].toUShortOrNull() != null) parts[2].toUShort() else nodes.first { it.name == parts[2] }.value
                                    node.value = (first.toInt() shl second.toInt()).toUShort()
                                    node.updated = true
                                }
                            }
                            "RSHIFT" -> {
                                val firstValid = parts[0].toUShortOrNull() != null || nodes.first { it.name == parts[0] }.updated
                                val secondValid = parts[2].toUShortOrNull() != null || nodes.first { it.name == parts[2] }.updated
                                if (firstValid && secondValid) {
                                    val first = if (parts[0].toUShortOrNull() != null) parts[0].toUShort() else nodes.first { it.name == parts[0] }.value
                                    val second = if (parts[2].toUShortOrNull() != null) parts[2].toUShort() else nodes.first { it.name == parts[2] }.value
                                    node.value = (first.toInt() shr second.toInt()).toUShort()
                                    node.updated = true
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    fun part1(input: List<String>): UShort {
        val nodes = mutableListOf<Node>()

        for (line in input) {
            val (operation, name) = line.split(" -> ")
            nodes.add(Node(name, 0u, operation))
        }

        updateNodes(nodes)

        return nodes.first { it.name == "a" }.value
    }

    fun part2(input: List<String>): UShort {
        val nodes = mutableListOf<Node>()

        for (line in input) {
            val (operation, name) = line.split(" -> ")
            nodes.add(Node(name, 0u, operation))
        }

        val b = nodes.first { it.name == "b" }
        b.value = 3176u
        b.updated = true
        updateNodes(nodes)

        return nodes.first { it.name == "a" }.value
    }

    val test = readInputString("year2015/day07/test")
    val input = readInputString("year2015/day07/input")

    check(part1(test) == 65079.toUShort())
    val timer1 = measureTimeMillis {
        println(part1(input))
    }
    println("Part 1 took $timer1 ms\n")

    val timer2 = measureTimeMillis {
        println(part2(input))
    }
    println("Part 2 took $timer2 ms")
}
