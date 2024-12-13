package year2023.day08

import lcm
import readInputString
import kotlin.system.measureTimeMillis

data class Node (val name: String, val left: String, val right: String)
fun main() {
    fun part1(input: List<String>): Int {
        val directions = input[0]
        val nodes = input.takeLast(input.size - 2).map {
            Node (name = it.split(" = ")[0],
                left = it.split("(")[1].split(",")[0],
                right = it.split(", ")[1].split(")")[0]
                )
        }

        var current = nodes.find { it.name == "AAA" }!!
        var steps = 0
//        while (current.name != "ZZZ") {
//            current = when (directions[steps % directions.length]) {
//                'L' -> nodes.find{ it.name == current.left }!!
//                'R' -> nodes.find{ it.name == current.right }!!
//                else -> throw Exception("bad directions")
//            }
//            steps++
//        }
        while (true) {
            current = when (directions[steps % directions.length]) {
                'L' -> nodes.find { it.name == current.left }!!
                'R' -> nodes.find { it.name == current.right }!!
                else -> throw Exception("bad directions")
            }
            steps++
            if (current.name == "ZZZ" && steps < 200_000) {
                println(steps)
            }
        }

        return steps
    }

    fun part2(input: List<String>): Long {
        val directions = input[0]
        val nodes = input.takeLast(input.size - 2).map {
            Node (name = it.split(" = ")[0],
                left = it.split("(")[1].split(",")[0],
                right = it.split(", ")[1].split(")")[0]
            )
        }

        val starters = nodes.filter { it.name[2] == 'A' }
        val firstZs = starters.map {
            var current = it
            var steps = 0
            while (current.name[2] != 'Z') {
                current = when (directions[steps % directions.length]) {
                    'L' -> nodes.find { n -> n.name == current.left }!!
                    'R' -> nodes.find { n -> n.name == current.right }!!
                    else -> throw Exception("bad directions")
                }
                steps++
            }
            steps.toLong()
        }

        return lcm(firstZs)
    }

    val testInput = readInputString("day08/test")
    val test2Input = readInputString("day08/test2")
    val input = readInputString("day08/input")

//    check(part1(testInput) == 6)
    val timer1 = measureTimeMillis {
        println(part1(input))
    }
    println("Part 1 took $timer1 ms\n")

    check(part2(test2Input) == 6L)
    val timer2 = measureTimeMillis {
        println(part2(input))
    }
    println("Part 2 took $timer2 ms")
}
