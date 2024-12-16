package year2016.day09

import utils.readInputString
import kotlin.math.max
import kotlin.math.min
import kotlin.system.measureTimeMillis

fun main() {
    data class Token(val numChar: Int, val numRepeat: Int, var length: Long)

    fun decompress(msg: String): String {
        var newMsg = ""
        var i = 0

        while (i < msg.length) {
            if (msg[i] == '(') {
                val (numChar, numRepeat) = msg.substring(i + 1).split(")")[0].split("x").map { it.toInt() }
                val repeat = msg.substring(i).split(")").drop(1).joinToString(")").substring(0 until numChar)
                for (r in 0 until numRepeat) {
                    newMsg += repeat
                }
                while (msg[i] != ')') {
                    i++
                }
                i += numChar + 1
            } else {
                newMsg += msg[i]
                i++
            }
        }

        return newMsg
    }

    fun part1(input: List<String>): Int {
        return decompress(input[0]).length
    }

    fun part2(input: List<String>): Int {
        var msg = input[0]
        while (msg.contains('(')) {
            msg = decompress(msg)
            println("msg length = ${msg.length}. Remaining = ${msg.count { it == '(' }}")
        }
        return msg.length
    }

    val input = readInputString("year2016/day09/input")

    check(part1(listOf("ADVENT")) == 6)
    check(part1(listOf("A(1x5)BC")) == 7)
    check(part1(listOf("(3x3)XYZ")) == 9)
    check(part1(listOf("A(2x2)BCD(2x2)EFG")) == 11)
    check(part1(listOf("(6x1)(1x3)A")) == 6)
    check(part1(listOf("X(8x2)(3x3)ABCY")) == 18)
    val timer1 = measureTimeMillis {
        println(part1(input))
    }
    println("Part 1 took $timer1 ms\n")

//    check(part2(test) == 0)
    val timer2 = measureTimeMillis {
        println(part2(input))
    }
    println("Part 2 took $timer2 ms")
}
