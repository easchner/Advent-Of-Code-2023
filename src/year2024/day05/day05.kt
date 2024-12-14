package year2024.day05

import utils.readInputSpaceDelimited
import kotlin.system.measureTimeMillis

fun main() {
    fun part1(input: List<List<String>>): Int {
        val pageOrders: MutableMap<Int, MutableSet<Int>> = mutableMapOf()
        var total = 0

        for (line in input[0]) {
            val pages = line.split("|").map { it.toInt() }
            if (pageOrders[pages[1]].isNullOrEmpty()) {
                pageOrders[pages[1]] = mutableSetOf()
            }
            pageOrders[pages[1]]!!.add(pages[0])
        }

        for (line in input[1]) {
            val update = line.split(",").map { it.toInt() }
            var valid = true

            for (i in update.indices) {
                val page1 = update[i]
                for (j in i + 1 until update.size) {
                    val page2 = update[j]
                    if (pageOrders[page2]?.contains(page1) != true) {
                        valid = false
                        break
                    }
                }
            }

            if (valid) {
                total += update[(update.size - 1) / 2]
            }
        }

        return total
    }

    fun part2(input: List<List<String>>): Int {
        val pageOrders: MutableMap<Int, MutableSet<Int>> = mutableMapOf()
        var total = 0

        for (line in input[0]) {
            val pages = line.split("|").map { it.toInt() }
            if (pageOrders[pages[1]].isNullOrEmpty()) {
                pageOrders[pages[1]] = mutableSetOf()
            }
            pageOrders[pages[1]]!!.add(pages[0])
        }

        for (line in input[1]) {
            val update = line.split(",").map { it.toInt() }
            var valid = true

            for (i in update.indices) {
                val page1 = update[i]
                for (j in i + 1 until update.size) {
                    val page2 = update[j]
                    if (pageOrders[page2]?.contains(page1) != true) {
                        valid = false
                        break
                    }
                }
            }

            if (!valid) {
                val newOrder = mutableListOf<Int>()
                val remaining = update.toMutableList()

                while (remaining.isNotEmpty()) {
                    for (page in remaining) {
                        if (pageOrders[page]?.containsAll(remaining.minus(page)) == true || remaining.size == 1) {
                            newOrder.add(page)
                            remaining.remove(page)
                            break
                        }
                    }
                }

                total += newOrder[(update.size - 1) / 2]
            }
        }

        return total
    }

    val testInput = readInputSpaceDelimited("day2405/test")
    val input = readInputSpaceDelimited("day2405/input")

    check(part1(testInput) == 143)
    val timer1 = measureTimeMillis {
        println(part1(input))
    }
    println("Part 1 took $timer1 ms\n")

    check(part2(testInput) == 123)
    val timer2 = measureTimeMillis {
        println(part2(input))
    }
    println("Part 2 took $timer2 ms")
}
