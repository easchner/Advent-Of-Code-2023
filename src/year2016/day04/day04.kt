package year2016.day04

import utils.readInputString
import kotlin.system.measureTimeMillis

fun main() {
    fun part1(input: List<String>): Int {
        var total = 0
        for (line in input) {
            val sector = line.split("-").last().split("[").first().toInt()
            val check = line.split("[").last().split("]").first()
            val name = line.split("-").dropLast(1).joinToString("")

            val letters = mutableMapOf<Char, Int>()

            for (c in name) {
                if (!letters.containsKey(c))
                    letters[c] = 0
                letters[c] = letters[c]!! + 1
            }

            val computed = letters.toList().sortedBy { it.first }.sortedByDescending { it.second }
                .map { it.first }.joinToString("").substring(0..4)
            if (check == computed)
                total += sector
        }
        return total
    }

    fun part2(input: List<String>) {
        for (line in input) {
            val sector = line.split("-").last().split("[").first().toInt()
            val check = line.split("[").last().split("]").first()
            val name = line.split("-").dropLast(1).joinToString("")

            val letters = mutableMapOf<Char, Int>()

            for (c in name) {
                if (!letters.containsKey(c))
                    letters[c] = 0
                letters[c] = letters[c]!! + 1
            }

            val computed = letters.toList().sortedBy { it.first }.sortedByDescending { it.second }
                .map { it.first }.joinToString("").substring(0..4)
            if (check == computed) {
                var decoded = line.split('-').dropLast(1).joinToString("-").map { c ->
                    if (c == '-')
                        " "
                    else {
                        ((c.code - 'a'.code + sector) % 26 + 'a'.code).toChar()
                    }
                }
                println("${decoded.joinToString("")}  -  $sector")
            }
        }
    }

    val test = readInputString("year2016/day04/test")
    val input = readInputString("year2016/day04/input")

    check(part1(test) == 1514)
    val timer1 = measureTimeMillis {
        println(part1(input))
    }
    println("Part 1 took $timer1 ms\n")

    part2(listOf("qzmt-zixmtkozy-ivhz-343"))
    val timer2 = measureTimeMillis {
        println(part2(input))
    }
    println("Part 2 took $timer2 ms")
}
