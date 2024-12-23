package year2024.day23

import utils.readInputString
import kotlin.system.measureTimeMillis

fun main() {
    fun part1(input: List<String>): Int {
        val nodes = mutableMapOf<String, List<String>>()

        for (connection in input) {
            val (from, to) = connection.split("-")
            if (nodes.containsKey(from)) {
                nodes[from] = nodes[from]!! + to
            } else {
                nodes[from] = listOf(to)
            }
            if (nodes.containsKey(to)) {
                nodes[to] = nodes[to]!! + from
            } else {
                nodes[to] = listOf(from)
            }
        }

        var lans = mutableSetOf<Triple<String, String, String>>()
        for (node in nodes) {
            if (node.key.first() == 't') {
                for (a in nodes) {
                    for (b in nodes) {
                        if (a.key != b.key && a.key != node.key && b.key != node.key) {
                            if (a.value.contains(b.key) && b.value.contains(node.key) && node.value.contains(a.key)) {
                                val alpha = listOf(a.key, b.key, node.key).sorted()
                                lans.add(Triple(alpha[0], alpha[1], alpha[2]))
                            }
                        }
                    }
                }
            }
        }

        return lans.size
    }

    fun checkNode(nodes: Map<String, List<String>>, node: String): Set<String> {
        val checkNodes = nodes[node]!!
        var best = setOf<String>()
        for (i in 1 until (1 shl checkNodes.size)) {
            val inNodes = mutableSetOf(node)
            for (j in checkNodes.indices) {
                if ((i and (1 shl j)) != 0) {
                    inNodes.add(checkNodes[j])
                }
            }

            var valid = true
            if (inNodes.size > best.size) {
                for (inNode in inNodes) {
                    for (other in inNodes.minus(inNode)) {
                        if (!nodes[inNode]!!.contains(other)) {
                           valid = false
                           break
                        }
                    }
                }
                if (valid)
                    best = inNodes
            }
        }

        return best
    }

    fun part2(input: List<String>): String {
        val nodes = mutableMapOf<String, List<String>>()

        for (connection in input) {
            val (from, to) = connection.split("-")
            if (nodes.containsKey(from)) {
                nodes[from] = nodes[from]!! + to
            } else {
                nodes[from] = listOf(to)
            }
            if (nodes.containsKey(to)) {
                nodes[to] = nodes[to]!! + from
            } else {
                nodes[to] = listOf(from)
            }
        }

        var best = setOf<String>()
        for (node in nodes) {
            val score = checkNode(nodes, node.key)
            if (score.size > best.size) {
                best = score
            }
        }

        return best.sorted().joinToString(",")
    }

    val testInput = readInputString("year2024/day23/test")
    val input = readInputString("year2024/day23/input")

    check(part1(testInput) == 7)
    val timer1 = measureTimeMillis {
        println(part1(input))
    }
    println("Part 1 took $timer1 ms\n")

    check(part2(testInput) == "co,de,ka,ta")
    val timer2 = measureTimeMillis {
        println(part2(input))
    }
    println("Part 2 took $timer2 ms")
}