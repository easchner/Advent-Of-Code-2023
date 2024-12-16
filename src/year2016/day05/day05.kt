package year2016.day05

import utils.md5
import utils.readInputString
import kotlin.system.measureTimeMillis

fun main() {
    fun part1(input: String): String {
        var i = 0
        var pw = ""
        var found = 0

        while (found < 8) {
            val hash = "$input$i".md5()
            if (hash.startsWith("00000")) {
                pw = "$pw${hash[5]}"
                found++
            }
            i++
        }

        return pw
    }

    fun part2(input: String): String {
        var i = 0
        var pw = CharArray(8) { 'x' }
        var found = 0

        while (found < 8) {
            val hash = "$input$i".md5()
            if (hash.startsWith("00000")) {
                var position = hash[5].code - '0'.code
                if (position in 0..7 && pw[position] == 'x') {
                    pw[position] = hash[6]
                    found++
                }
            }
            i++
        }

        return pw.joinToString("")
    }

    val input = readInputString("year2016/day05/input")[0]

    check(part1("abc") == "18f47a30")
    val timer1 = measureTimeMillis {
        println(part1(input))
    }
    println("Part 1 took $timer1 ms\n")

    check(part2("abc") == "05ace8e3")
    val timer2 = measureTimeMillis {
        println(part2(input))
    }
    println("Part 2 took $timer2 ms")
}
