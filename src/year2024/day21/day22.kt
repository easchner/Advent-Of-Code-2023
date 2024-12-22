package year2024.day21

import utils.readInputString
import kotlin.math.abs
import kotlin.system.measureTimeMillis

fun main() {
    val numPad = listOf(
        listOf('7', '8', '9'),
        listOf('4', '5', '6'),
        listOf('1', '2', '3'),
        listOf(' ', '0', 'A')
    )

    val directionalPad = listOf(
        listOf(' ', '^', 'A'),
        listOf('<', 'v', '>')
    )

    data class MoveKey(val dirPad: Boolean, val sr: Int, val sc: Int, val tr: Int, val tc: Int)
    val memoMoves = mutableMapOf<MoveKey, Set<String>>()

    fun getSubPaths(pad: List<List<Char>>, sr: Int, sc: Int, tr: Int, tc: Int): Set<String> {
        if (memoMoves.containsKey(MoveKey(pad == directionalPad, sr, sc, tr, tc))) {
            return memoMoves[MoveKey(pad == directionalPad, sr, sc, tr, tc)]!!
        }

        val subs = mutableSetOf<String>()

        // Check rows first
        var sub = ""
        if (sr < tr) {
            for (r in sr until tr) {
                sub += "v"
            }
        } else {
            for (r in tr until sr) {
                sub += "^"
            }
        }
        if (sc < tc) {
            for (c in sc until tc) {
                sub += ">"
            }
        } else {
            for (c in tc until sc) {
                sub += "<"
            }
        }

        subs.add(sub + 'a')

        // Check columns second
        sub = ""
        if (sc < tc) {
            for (c in sc until tc) {
                sub += ">"
            }
        } else {
            for (c in tc until  sc) {
                sub += "<"
            }
        }
        if (sr < tr) {
            for (r in sr until tr) {
                sub += "v"
            }
        } else {
            for (r in tr until sr) {
                sub += "^"
            }
        }
        subs.add(sub + 'a')

        // Validate paths
        for (s in subs.toList()) {
            var r = sr
            var c = sc
            for (d in s) {
                when (d) {
                    '^' -> r--
                    'v' -> r++
                    '<' -> c--
                    '>' -> c++
                }
                if (r < 0 || r >= pad.size || c < 0 || c >= pad[r].size || pad[r][c] == ' ') {
                    subs.remove(s)
                    break
                }
            }
        }

        if (subs.isEmpty()) {
            subs.add("a")
        }

        memoMoves[MoveKey(pad == directionalPad, sr, sc, tr, tc)] = subs
        return subs
    }

    fun getShortest(target: String, pad: List<List<Char>>): Set<String> {
        val shortest = mutableSetOf("")
        var r = 0
        var c = 0

        for (row in pad.indices) {
            for (col in pad[row].indices) {
                if (pad[row][col] == 'A') {
                    c = col
                    r = row
                }
            }
        }

        for (t in target) {
            val newShortest = mutableSetOf<String>()

            var rc = 0
            var cc = 0

            for (row in pad.indices) {
                for (col in pad[row].indices) {
                    if (pad[row][col] == t) {
                        cc = col
                        rc = row
                    }
                }
            }

            val subs = getSubPaths(pad, r, c, rc, cc)

            for (s in shortest) {
                for (sub in subs) {
                    newShortest.add(s + sub + "A")
                }
            }

            r = rc
            c = cc

            shortest.clear()
            shortest.addAll(newShortest)
        }

        return shortest
    }

    fun part1(input: List<String>): Long {
        var total = 0L

        for (target in input) {
            val bot1 = getShortest(target, numPad)
            val bot2 = bot1.map { getShortest(it, directionalPad) }.flatten().toSet()
//            println(bot2)
            val bot3 = bot2.map { getShortest(it, directionalPad) }.flatten().toSet()
            total += bot3.minBy { it.length }.length * target.dropLast(1).toInt()
        }

        return total
    }

    fun scorePath(path: String): Int {
        var score = 0
        for (c in 0 until path.length - 1) {
            if (path[c] == '^' && (path[c + 1] == 'A' || path[c + 1] == 'v')) {
                score++
            } else if (path[c] == '^' && (path[c + 1] == '>' || path[c + 1] == '<')) {
                score += 2
            } else if (path[c] == 'A' && (path[c + 1] == '^' || path[c + 1] == '>')) {
                score++
            } else if (path[c] == 'A' && (path[c + 1] == 'v')) {
                score += 2
            } else if (path[c] == 'A' && (path[c + 1] == '<')) {
                score += 3
            } else if (path[c] == '<' && (path[c + 1] == 'v')) {
                score++
            } else if (path[c] == '<' && (path[c + 1] == '^' || path[c + 1] == '>')) {
                score += 2
            } else if (path[c] == '<' && (path[c + 1] == 'A')) {
                score += 3
            } else if (path[c] == 'v' && (path[c + 1] == '^' || path[c + 1] == '>' || path[c + 1] == '<')) {
                score++
            } else if (path[c] == 'v' && (path[c + 1] == 'A')) {
                score += 2
            } else if (path[c] == '>' && (path[c + 1] == 'A' || path[c + 1] == 'v')) {
                score++
            } else if (path[c] == '>' && (path[c + 1] == '^' || path[c + 1] == '<')) {
                score += 2
            }
        }
        return score
    }

    //    val directionalPad = listOf(
    //        listOf(' ', '^', 'A'),
    //        listOf('<', 'v', '>')
    //    )

    val positions = mapOf(
        '7' to Pair(0, 0),
        '8' to Pair(0, 1),
        '9' to Pair(0, 2),
        '4' to Pair(1, 0),
        '5' to Pair(1, 1),
        '6' to Pair(1, 2),
        '1' to Pair(2, 0),
        '2' to Pair(2, 1),
        '3' to Pair(2, 2),
        '0' to Pair(3, 1),
        'A' to Pair(3, 0),
        '^' to Pair(0, 1),
        'a' to Pair(1, 1),
        '<' to Pair(1, 0),
        'v' to Pair(1, 1),
        '>' to Pair(1, 2)
    )

    val memo = mutableMapOf<Triple<String, Int, Int>, Long>()

    fun minLength(sequence: String, limit: Int = 2, depth: Int = 0): Long {
        // Check memo
        if (memo.containsKey(Triple(sequence, depth, limit))) {
            return memo[Triple(sequence, depth, limit)]!!
        }

        // Set starting position and position to avoid
        val avoid = if (depth == 0) Pair(3, 0) else Pair(0, 0)
        var current = if (depth == 0) Pair(3, 2) else Pair(0, 2)

        // Find total length
        var totalLength = 0L
        for (char in sequence) {
            val nextPos = positions[char]!!
            val moveSets = getSubPaths(if(depth == 0) numPad else directionalPad, current.first, current.second, nextPos.first, nextPos.second)

            if (depth >= limit) {
                // At max depth, just take shortest valid sequence
                totalLength += moveSets.minByOrNull { it.length }!!.length
            } else {
                // Try all possible moveSets recursively
                var minMoves = Long.MAX_VALUE
                for (moveSet in moveSets) {
                    val length = minLength(moveSet, limit, depth + 1)
                    minMoves = minOf(minMoves, length)
                }
                if (minMoves == Long.MAX_VALUE) {
                    // If no valid sequences found, use shortest direct path
                    totalLength += moveSets.minByOrNull { it.length }!!.length
                } else {
                    totalLength += minMoves
                }
            }
            current = nextPos
        }

        // Cache and return
        memo[Triple(sequence, depth, limit)] = totalLength
        return totalLength
    }

    fun part2(input: List<String>): Long {
        var total = 0L

        for (target in input) {
            total += minLength(target)
        }


//        for (target in input) {
//            var nextTargets = getShortest(target, numPad).toMutableList()
//            println(nextTargets.size)
//            for (nextBot in 0 until 25) {
//                nextTargets = nextTargets.map { getShortest(it, directionalPad) }.flatten().toMutableList()
//            }
//            total += nextTargets.minBy { it.length }.length * target.dropLast(1).toInt()
//        }

        return total
    }

    val testInput = readInputString("year2024/day21/test")
    val input = readInputString("year2024/day21/input")

//    check(part1(testInput) == 126384L)
//    val timer1 = measureTimeMillis {
//        println(part1(input))
//    }
//    println("Part 1 took $timer1 ms\n")

//    check(part2(testInput) == 0L)
    val timer2 = measureTimeMillis {
        println(part2(input))
    }
    println("Part 2 took $timer2 ms")
}