package year2024.day22

import utils.readInputString
import kotlin.system.measureTimeMillis

fun main() {
    val mod = 16777216L

    fun rotateSecret(initial: Long, times: Long): Long {
        var current = initial
        for (i in 0 until times) {
            var working = current * 64
            current = working xor current
            current = current % mod
            working = current / 32
            current = working xor current
            current = current % mod
            working = current * 2048
            current = working xor current
            current = current % mod
        }

        return current
    }

    fun part1(input: List<String>): Long {
        return input.sumOf { rotateSecret(it.toLong(), 2000) }
    }

    data class key (val a: Long, val b: Long, val c: Long, val d: Long)

    fun part2(input: List<String>): Long {
        val monkeys = mutableListOf<MutableList<Pair<Long, Long>>>()
        for (line in input) {
            val theseChanges = mutableListOf<Pair<Long, Long>>()
            var secret = line.toLong()
            var banana = secret % 10
            for (i in 0 until 2000) {
                secret = rotateSecret(secret, 1)
                val next = secret % 10
                theseChanges.add(Pair(next - banana, next))
                banana = next
            }
            monkeys.add(theseChanges)
        }

        val map = mutableMapOf<key, Long>()
        for (monkey in monkeys) {
            for (i in 0 until monkey.size - 3) {
                val k = key(monkey[i].first, monkey[i + 1].first, monkey[i + 2].first, monkey[i + 3].first)
                map[k] = map.getOrDefault(k, 0) + monkey[i + 3].second
            }
        }

        val sorted = map.toList().sortedByDescending { it.second }

        var best = 0L
        for (key in sorted) {
            if (key.second < best) {
                break
            }
            val k = key.first
            var current = 0L
            for (m in monkeys) {
                for (i in 0 until m.size - 3) {
                    if (m[i].first == k.a && m[i + 1].first == k.b && m[i + 2].first == k.c && m[i + 3].first == k.d) {
                        current += m[i + 3].second
                        break
                    }
                }
            }
            best = maxOf(best, current)
            println("Checking $k. Best so far: $best. Current: $current")
        }

//        var best = 0L
//        for (a in -9L..9L) {
//            for (b in -9L..9L) {
//                println("Checking $a $b. Best so far: $best")
//                for (c in -9L..9L) {
//                    for (d in -9L..9L) {
//                        var current = 0L
//                        for (m in monkeys) {
//                            for (i in 0 until m.size - 3) {
//                                if (m[i].first == a && m[i + 1].first == b && m[i + 2].first == c && m[i + 3].first == d) {
//                                    current += m[i + 3].second
//                                    break
//                                }
//                            }
//                        }
//                        best = maxOf(best, current)
//                    }
//                }
//            }
//        }

        return best
    }

    val testInput = readInputString("year2024/day22/test")
    val input = readInputString("year2024/day22/input")

    check(part1(testInput) == 37327623L)
    val timer1 = measureTimeMillis {
        println(part1(input))
    }
    println("Part 1 took $timer1 ms\n")

//    part2(listOf("123"))
//    check(part2(testInput) == 23L)
    val timer2 = measureTimeMillis {
        println(part2(input))
    }
    println("Part 2 took $timer2 ms")
}