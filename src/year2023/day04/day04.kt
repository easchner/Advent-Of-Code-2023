package year2023.day04

import readInputString

fun main() {
    fun part1(input: List<String>): Int {
        var total = 0
        for (line in input) {
            var winnings = 0
            val split = line.split(": ")[1].split(" | ")
            val winners = split[0].split(" ").map { if (it.isNotEmpty()) it.toInt() else -1 }.toSet()
            val ticket = split[1].split(" ").map { if (it.isNotEmpty()) it.toInt() else -2 }.toSet()

            for (num in ticket) {
                if (winners.contains(num)) {
                    winnings = if (winnings == 0) 1 else winnings * 2
                }
            }

            total += winnings
        }

        return total
    }

    fun part2(input: List<String>): Int {
        val ticketCopies = MutableList(input.size) { 1 }

        for (lineNum in input.indices) {
            val line = input[lineNum]

            var winnings = 0
            val split = line.split(": ")[1].split(" | ")
            val winners = split[0].split(" ").map { if (it.isNotEmpty()) it.toInt() else -1 }.toSet()
            val ticket = split[1].split(" ").map { if (it.isNotEmpty()) it.toInt() else -2 }.toSet()

            for (num in ticket) {
                if (winners.contains(num)) {
                    winnings++
                }
            }

            for (next in (lineNum + 1) .. (lineNum + winnings)) {
                ticketCopies[next] = ticketCopies[next] + ticketCopies[lineNum]
            }
        }

        return ticketCopies.sum()
    }

    val testInput = readInputString("day04/test")
    val input = readInputString("day04/input")

    check(part1(testInput) == 13)
    println(part1(input))

    check(part2(testInput) == 30)
    println(part2(input))
}
