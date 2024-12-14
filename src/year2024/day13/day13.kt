package year2024.day13

import utils.readInputSpaceDelimited
import kotlin.system.measureTimeMillis

fun main() {
    // Naive solution
    fun claimPrize(ax: Int, ay: Int, bx: Int, by: Int, px: Int, py: Int): Int {
        val prizes = mutableListOf<Int>()

        for (a in 0..100) {
            for (b in 0..100) {
                if (ax * a + bx * b == px && ay * a + by * b == py) {
                    prizes.add(3 * a + b)
                }
            }
        }

        if (prizes.isEmpty())
            return 0
        return prizes.min()
    }

    // Optimized solution
    fun claimBigPrize(ax: Int, ay: Int, bx: Int, by: Int, px: Long, py: Long): Long {
        // px = (ax * a) + (bx * b)
        // px - (bx * b) = ax * a
        // (px - (bx * b)) / ax = a

        // py = (ay * a) + (by * b)
        // py - (by * b) = ay * a
        // (py - (by * b)) / ay = a

        // (px - (bx * b)) / ax = (py - (by * b)) / ay
        // b = ((px * ay) - (py * ax)) / ((bx * ay) - (by * ax))

        val b = ((px * ay) - (py * ax)) / ((bx * ay).toLong() - (by * ax).toLong())
        val a = (px - (bx * b)) / ax

        println("a: $a, b: $b - tx: ${ax * a + bx * b}, ty: ${ay * a + by * b} - px: $px, py: $py")
        if (ax * a + bx * b == px && ay * a + by * b == py) {
            println("added")
            return 3 * a + b
        }

        return 0
    }

    fun part1(input: List<List<String>>): Long {
        var total = 0L

        for (machine in input) {
            val ax = machine[0].split("X+")[1].split(",")[0].toInt()
            val ay = machine[0].split("Y+")[1].toInt()
            val bx = machine[1].split("X+")[1].split(",")[0].toInt()
            val by = machine[1].split("Y+")[1].toInt()
            val px = machine[2].split("X=")[1].split(",")[0].toLong()
            val py = machine[2].split("Y=")[1].toLong()

            total += claimBigPrize(ax, ay, bx, by, px, py)
        }

        return total
    }

    fun part2(input: List<List<String>>): Long {
        var total = 0L

        for (machine in input) {
            val ax = machine[0].split("X+")[1].split(",")[0].toInt()
            val ay = machine[0].split("Y+")[1].toInt()
            val bx = machine[1].split("X+")[1].split(",")[0].toInt()
            val by = machine[1].split("Y+")[1].toInt()
            val px = machine[2].split("X=")[1].split(",")[0].toInt() + 10000000000000L
            val py = machine[2].split("Y=")[1].toInt() + 10000000000000L

            total += claimBigPrize(ax, ay, bx, by, px, py)
        }

        return total
    }

    val testInput = readInputSpaceDelimited("day2413/test")
    val input = readInputSpaceDelimited("day2413/input")

    check(part1(testInput) == 480L)
    val timer1 = measureTimeMillis {
        println(part1(input))
    }
    println("Part 1 took $timer1 ms\n")

    check(part2(testInput) == 875318608908L)
    val timer2 = measureTimeMillis {
        println(part2(input))
    }
    println("Part 2 took $timer2 ms")
}
