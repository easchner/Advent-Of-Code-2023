package year2023.day18

import utils.readInputString
import kotlin.system.measureTimeMillis

fun main() {
    fun printTrench(trench: List<Pair<Int, Int>>) {
        for (y in trench.minBy { it.second }.second .. trench.maxBy { it.second }.second) {
            for (x in trench.minBy { it.first }.first .. trench.maxBy { it.first }.first) {
                if (trench.contains(Pair(x, y)))
                    print("#")
                else
                    print(" ")
            }
            println()
        }
    }

    fun part1(input: List<String>): Int {
        val digs = input.map { it.split(" ") }
        val trench = mutableListOf<Pair<Int, Int>>()
        var x = 0
        var y = 0
        trench.add(Pair(x, y))
        for (dig in digs) {
            val dir = dig[0]
            val dist = dig[1].toInt()

            for (i in 1..dist) {
                when (dir) {
                    "U" -> y--
                    "D" -> y++
                    "L" -> x--
                    "R" -> x++
                    else -> throw Exception("Bad direction $dir")
                }
                trench.add(Pair(x, y))
            }
        }

        printTrench(trench)

        val fill = mutableListOf<Pair<Int, Int>>()
        val q = mutableListOf<Pair<Int, Int>>()
        val txMin = trench.minBy{ it.first }.first
        val txMax = trench.maxBy{ it.first }.first
        val tyMin = trench.minBy{ it.second }.second
        val tyMax = trench.maxBy{ it.second }.second

        q.add(Pair(txMin - 2, tyMin - 2))
        q.add(Pair(txMin - 2, tyMax + 2))
        q.add(Pair(txMax + 2, tyMin - 2))
        q.add(Pair(txMax + 2, tyMax + 2))

        while (q.isNotEmpty()) {
            val cur = q.removeFirst()
            val up = Pair(cur.first, cur.second - 1)
            val down = Pair(cur.first, cur.second + 1)
            val left = Pair(cur.first - 1, cur.second)
            val right = Pair(cur.first + 1, cur.second)

            for (dir in listOf(up, down, left, right)) {
                if (dir.first >= txMin - 2 && dir.second >= tyMin - 2 && dir.first <= txMax + 2 && dir.second <= tyMax + 2) {
                    if (!fill.contains(dir) && !trench.contains(dir)) {
                        fill.add(dir)
                        q.add(dir)
                    }
                }
            }
        }

        fill.removeIf { it.first < txMin }
        fill.removeIf { it.first > txMax }
        fill.removeIf { it.second < tyMin }
        fill.removeIf { it.second > tyMax }

        println()
        println()
        printTrench(fill)

        return (txMax - txMin + 1) * (tyMax - tyMin + 1) - fill.size
    }

    fun part2(input: List<String>): Long {
        val digs = input.map { it.split("#").last().split(")").first() }
        val corners = mutableListOf<Pair<Long, Long>>()
        var trench = 0L

        var x = 0L
        var y = 0L
        corners.add(Pair(x, y))
        for (dig in digs) {
            val dir = dig.last() // 0R, 1D, 2L, 3U
            val dist = dig.substring(0..4).toLong(16)
            when (dir) {
                '0' -> x += dist
                '1' -> y += dist
                '2' -> x -= dist
                '3' -> y -= dist
                else -> throw Exception("Bad direction $dir")
            }
            corners.add(Pair(x, y))
            trench += dist
        }

        corners.removeLast()

        var area = 0L
        val n = corners.size
        for (i in 0 until n - 1) {
            area += corners[i].first * corners[i + 1].second - corners[i + 1].first * corners[i].second
        }
        area += corners[n - 1].first * corners[0].second - corners[0].first * corners[n - 1].second
        area += trench
        area /= 2

        return area + 1
    }

    val testInput = readInputString("day18/test")
    val input = readInputString("day18/input")

//    check(part1(testInput) == 62)
//    val timer1 = measureTimeMillis {
//        println(part1(input))
//    }
//    println("Part 1 took $timer1 ms\n")

    check(part2(testInput) == 952408144115L)
    val timer2 = measureTimeMillis {
        println(part2(input))
    }
    println("Part 2 took $timer2 ms")
}
