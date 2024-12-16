package year2016.day08

import utils.readInputString
import kotlin.system.measureTimeMillis

fun main() {
    fun printScreen(screen: Array<Array<Boolean>>) {
        for (r in screen) {
            println(r.joinToString("") { if (it) "#" else " " })
        }
    }

    fun part1(input: List<String>): Int {
        val rows = 6
        val cols = 50
        val screen = Array(rows) { Array (cols) { false } }

        for (line in input) {
            val splits = line.split(" ")
            val instruction = splits[0]
            when (instruction) {
                "rect" -> {
                    val (a, b) = splits[1].split("x").map { it.toInt() }
                    for (r in 0 until b) {
                        for (c in 0 until a) {
                            screen[r][c] = true
                        }
                    }
                }
                "rotate" -> {
                    val direction = splits[1]
                    val amount = splits.last().toInt()
                    val rc = splits[2].split("=").last().toInt()
                    when (direction) {
                        "row" -> {
                            val oldRow = screen[rc].clone()
                            for (c in 0 until cols) {
                                screen[rc][c] = oldRow[(cols + c - amount) % cols]
                            }
                        }
                        "column" -> {
                            val oldCol = screen.map { it[rc] }
                            for (r in 0 until rows) {
                                screen[r][rc] = oldCol[(rows + r - amount) % rows]
                            }
                        }
                        else -> throw Exception("Direction $direction not found")
                    }
                }
                else -> throw Exception("Instruction $instruction not found")
            }
            println()
            println()
            printScreen(screen)
        }

        return screen.sumOf { row -> row.count() { it } }
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    val input = readInputString("year2016/day08/input")

    val timer1 = measureTimeMillis {
        println(part1(input))
    }
    println("Part 1 took $timer1 ms\n")

    val timer2 = measureTimeMillis {
        println(part2(input))
    }
    println("Part 2 took $timer2 ms")
}
