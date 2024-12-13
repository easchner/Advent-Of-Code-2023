package year2023.day07

import readInputString
import kotlin.system.measureTimeMillis

val ordinals = mapOf<Char, Int>('A' to 13, 'K' to 12, 'Q' to 11, 'J' to 10, 'T' to 9, '9' to 8, '8' to 7,
    '7' to 6, '6' to 5, '5' to 4, '4' to 3, '3' to 2, '2' to 1)

val ordinals2 = mapOf<Char, Int>('A' to 13, 'K' to 12, 'J' to 0, 'Q' to 11, 'T' to 9, '9' to 8, '8' to 7,
    '7' to 6, '6' to 5, '5' to 4, '4' to 3, '3' to 2, '2' to 1)

fun main() {
    fun scoreHand(hand: String): Long {
        var cardScores = 0L
        for (i in hand) {
            cardScores = (cardScores shl 4) + ordinals[i]!!
        }

        // 5 of a kind = 600M
        if (hand.toCharArray().toSet().size == 1)
            return cardScores + 600_000_000L

        // 4 of a kind = 500M
        if (hand.toCharArray().toSet().size == 2 &&
            (hand.count { it == hand[0] } == 4) || hand.count { it == hand[1] } == 4) {
            return cardScores + 500_000_000L
        }

        // Full house = 400M
        if (hand.toCharArray().toSet().size == 2) {
            return cardScores + 400_000_000L
        }

        // 3 of a kind = 300M
        if (hand.toCharArray().toSet().size == 3 &&
            (hand.count { it == hand[0] } == 3 || hand.count { it == hand[1] } == 3 || hand.count { it == hand[2] } == 3)) {
            return cardScores + 300_000_000L
        }

        // two pair = 200M
        if (hand.toCharArray().toSet().size == 3) {
            return cardScores + 200_000_000L
        }

        // one pair = 100M
        if (hand.toCharArray().toSet().size == 4) {
            return cardScores + 100_000_000L
        }

        return cardScores
    }

    fun scoreHand2(hand: String): Long {
        var cardScores = 0L
        for (i in hand) {
            cardScores = (cardScores shl 4) + ordinals2[i]!!
        }

        val counts = ordinals.keys.associateWith { hand.toCharArray().count { pip -> it == pip} }.toMutableMap()
        val wilds = counts['J']!!
        counts['J'] = 0

        // 5 of a kind = 600M
        if (counts.filter { it.value + wilds >= 5 }.isNotEmpty())
            return cardScores + 600_000_000L

        // 4 of a kind = 500M
        if (counts.filter { it.value + wilds >= 4 }.isNotEmpty())
            return cardScores + 500_000_000L

        // Full house = 400M
        // Two pair and a wild (two+ wilds and a pair would always be at least 4 of a kind)
        if ((counts.filter { it.value == 3 }.isNotEmpty() && counts.filter { it.value == 2 }.isNotEmpty()) ||
            (counts.filter { it.value == 2 }.size == 2 && wilds == 1))
            return cardScores + 400_000_000L

        // 3 of a kind = 300M
        if (counts.filter { it.value + wilds >= 3 }.isNotEmpty()) {
            return cardScores + 300_000_000L
        }

        // two pair = 200M
        if (counts.filter { it.value == 2 }.size == 2 || (counts.filter { it.value == 2 }.size == 1 && wilds > 0)) {
            return cardScores + 200_000_000L
        }

        // one pair = 100M
        if (counts.filter { it.value == 2 }.size == 1 || wilds == 1) {
            return cardScores + 100_000_000L
        }

        return cardScores
    }

    fun part1(input: List<String>): Long {
        val hands = input.map { it.split(" ") }
        val sorted = hands.sortedBy { scoreHand(it.first()) }
        return sorted.mapIndexed { index, hand -> hand[1].toLong() * (index + 1) }.sum()
    }

    data class Scored (val hand: String, val points: Long, val score: Long)
    fun part2(input: List<String>): Long {
        val hands = input.map { it.split(" ") }
        val scored = hands.map { Scored(it[0], it[1].toLong(), scoreHand2(it[0])) }
        val sorted = scored.sortedBy { it.score }
        return sorted.mapIndexed { index, hand -> hand.points * (index + 1) }.sum()
    }

    val testInput = readInputString("day07/test")
    val input = readInputString("day07/input")

    check(part1(testInput) == 6440L)
    val timer1 = measureTimeMillis {
        println(part1(input))
    }
    println("Part 1 took $timer1 ms\n")

    check(part2(testInput) == 5905L)
    val timer2 = measureTimeMillis {
        println(part2(input))
    }
    println("Part 2 took $timer2 ms")
}
