package year2015.day10

import utils.readInputString
import kotlin.system.measureTimeMillis

fun main() {
    fun part1(input: String, steps: Int): Int {
        var items = input.map { it.toString().toInt() }
        for (i in 1..steps) {
            val newItems = mutableListOf<Int>()
            var count = 1
            for (j in 1 until items.size) {
                if (items[j] == items[j - 1]) {
                    count++
                } else {
                    newItems.add(count)
                    newItems.add(items[j - 1])
                    count = 1
                }
            }
            newItems.add(count)
            newItems.add(items.last())
            items = newItems
        }
        return items.size
    }

    fun part2(input: String): Int {
        return 0
    }

    val input = readInputString("year2015/day10/input")[0]

    check(part1("1", 1) == 2)
    check(part1("11", 1) == 2)
    check(part1("21", 1) == 4)
    check(part1("1211", 1) == 6)
    check(part1("111221", 1) == 6)
    val timer1 = measureTimeMillis {
        println(part1(input, 40))
    }
    println("Part 1 took $timer1 ms\n")

//    check(part2(test) == 0)
    val timer2 = measureTimeMillis {
        println(part1(input, 50))
    }
    println("Part 2 took $timer2 ms")
}
