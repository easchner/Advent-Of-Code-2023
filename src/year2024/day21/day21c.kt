package year2024.day21

import utils.readInputString
import kotlin.math.abs
import kotlin.system.measureTimeMillis

fun main() {
    val codes = listOf("140A", "143A", "349A", "582A", "964A")

    val keyp = mapOf(
        '7' to intArrayOf(0, 0), '8' to intArrayOf(0, 1), '9' to intArrayOf(0, 2),
        '4' to intArrayOf(1, 0), '5' to intArrayOf(1, 1), '6' to intArrayOf(1, 2),
        '1' to intArrayOf(2, 0), '2' to intArrayOf(2, 1), '3' to intArrayOf(2, 2),
        ' ' to intArrayOf(3, 0), '0' to intArrayOf(3, 1), 'A' to intArrayOf(3, 2)
    )

    val dirp = mapOf(
        '^' to intArrayOf(0, 1), 'A' to intArrayOf(0, 2), '<' to intArrayOf(1, 0),
        'v' to intArrayOf(1, 1), '>' to intArrayOf(1, 2)
    )

    fun steps(G: Map<Char, IntArray>, s: String, i: Int = 1): Map<Triple<Int, Int, Boolean>, Int> {
        var (px, py) = G['A']!!
        val (bx, by) = G[' ']!!
        val res = mutableMapOf<Triple<Int, Int, Boolean>, Int>()
        for (c in s) {
            val (npx, npy) = G[c]!!
            val f = (npx == bx && py == by) || (npy == by && px == bx)
            val key = Triple(npx - px, npy - py, f)
            res[key] = res.getOrDefault(key, 0) + i
            px = npx
            py = npy
        }
        return res
    }

    fun go(n: Int): Int {
        var r = 0
        for (code in codes) {
            var res = steps(keyp, code)
            repeat(n + 1) {
                res = res.entries.fold(mutableMapOf()) { acc, (key, value) ->
                    val (x, y, f) = key
                    val newSteps = steps(dirp, ("<".repeat(-x) + "v".repeat(y) + "^".repeat(-y) + ">".repeat(x)).reversed().let { if (f) it.reversed() else it } + "A", value)
                    newSteps.forEach { (k, v) -> acc[k] = acc.getOrDefault(k, 0) + v }
                    acc
                }
            }
            r += res.values.sum() * code.dropLast(1).toInt()
        }
        return r
    }

    println(go(2))
    println(go(25))
}