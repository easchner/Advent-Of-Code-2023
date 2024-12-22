package year2024.day21

import utils.readInputString
import kotlin.math.abs
import kotlin.system.measureTimeMillis

fun main() {
    val POSITIONS = mapOf(
        '7' to intArrayOf(0, 0),
        '8' to intArrayOf(0, 1),
        '9' to intArrayOf(0, 2),
        '4' to intArrayOf(1, 0),
        '5' to intArrayOf(1, 1),
        '6' to intArrayOf(1, 2),
        '1' to intArrayOf(2, 0),
        '2' to intArrayOf(2, 1),
        '3' to intArrayOf(2, 2),
        '0' to intArrayOf(3, 1),
        'A' to intArrayOf(3, 2),
        '^' to intArrayOf(0, 1),
        'a' to intArrayOf(0, 2),
        '<' to intArrayOf(1, 0),
        'v' to intArrayOf(1, 1),
        '>' to intArrayOf(1, 2)
    )

    val DIRECTIONS = mapOf(
        '^' to intArrayOf(-1, 0),
        'v' to intArrayOf(1, 0),
        '<' to intArrayOf(0, -1),
        '>' to intArrayOf(0, 1)
    )

    fun sequenceToMoveSet(start: IntArray, end: IntArray, avoid: IntArray = intArrayOf(0, 0)): List<String> {
        val delta = intArrayOf(end[0] - start[0], end[1] - start[1])
        val moves = mutableListOf<String>()

        val dx = delta[0]
        val dy = delta[1]
        val permutations = mutableSetOf<String>()

        // Check rows first
        var sub = ""
        if (dx < 0) {
            for (r in dx until 0) {
                sub += "^"
            }
        } else {
            for (r in 0 until dx) {
                sub += "v"
            }
        }
        if (dy < 0) {
            for (c in dy until 0) {
                sub += "<"
            }
        } else {
            for (c in 0 until dy) {
                sub += ">"
            }
        }

        permutations.add(sub)

        // Check columns second
        sub = ""
        if (dy < 0) {
            for (c in dy until 0) {
                sub += "<"
            }
        } else {
            for (c in 0 until dy) {
                sub += ">"
            }
        }
        if (dx < 0) {
            for (r in dx until 0) {
                sub += "^"
            }
        } else {
            for (r in 0 until dx) {
                sub += "v"
            }
        }
        permutations.add(sub)

        val validSequences = mutableListOf<String>()
        for (p in permutations) {
            val positions = mutableListOf(start)
            var valid = true
            for (move in p) {
                val nextPos = intArrayOf(positions.last()[0] + DIRECTIONS[move]!![0], positions.last()[1] + DIRECTIONS[move]!![1])
                if (nextPos.contentEquals(avoid)) {
                    valid = false
                    break
                }
                positions.add(nextPos)
            }
            if (valid) {
                validSequences.add(p + "a")
            }
        }

        return if (validSequences.isNotEmpty()) validSequences else listOf("a")
    }

    val mlMemos = mutableMapOf<Triple<String, Int, Int>, Int>()

    fun minLength(sequence: String, limit: Int = 2, depth: Int = 0): Int {
        val memoKey = Triple(sequence, depth, limit)
        if (mlMemos.containsKey(memoKey)) {
            return mlMemos[memoKey]!!
        }

        val avoid = if (depth == 0) intArrayOf(3, 0) else intArrayOf(0, 0)
        var current = if (depth == 0) POSITIONS['A']!! else POSITIONS['a']!!

        var totalLength = 0
        for (char in sequence) {
            val nextPos = POSITIONS[char]!!
            val moveSets = sequenceToMoveSet(current, nextPos, avoid)

            if (depth >= limit) {
                totalLength += moveSets.minByOrNull { it.length }!!.length
            } else {
                var minMoves = Int.MAX_VALUE
                for (moveSet in moveSets) {
                    try {
                        val length = minLength(moveSet, limit, depth + 1)
                        minMoves = minOf(minMoves, length)
                    } catch (e: StackOverflowError) {
                        continue
                    }
                }
                if (minMoves == Int.MAX_VALUE) {
                    totalLength += moveSets.minByOrNull { it.length }!!.length
                } else {
                    totalLength += minMoves
                }
            }
            current = nextPos
        }

        mlMemos[memoKey] = totalLength
        return totalLength
    }

    fun part2(input: List<String>): Long {
        var total = 0L

        for (target in input) {
            total += minLength(target, 2) * target.dropLast(1).toInt()
        }

        return total
    }

    val input = readInputString("year2024/day21/input")

    val timer2 = measureTimeMillis {
        println(part2(input))
    }
    println("Part 2 took $timer2 ms")
}