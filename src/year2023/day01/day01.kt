package year2023.day01

import readInputString

fun main() {
    fun part1(input: List<String>): Int {
        var total = 0
        for (line in input) {
            val nums = line.toCharArray().filter { it in '0'..'9' }
            total += (nums.first().toString() + nums.last().toString()).toInt()
        }
        return total
    }

    fun part1a(input: List<String>) = input.sumOf {
        val nums = it.toCharArray().filter { c -> c in '0'..'9' }
        "${nums.first()}${nums.last()}".toInt()
    }

    fun part2(input: List<String>): Int {
        var total = 0
        for (line in input) {
            val simplified = line.replace("one", "one1one")
                .replace("two", "two2two")
                .replace("three", "three3three")
                .replace("four", "four4four")
                .replace("five", "five5five")
                .replace("six", "six6six")
                .replace("seven", "seven7seven")
                .replace("eight", "eight8eight")
                .replace("nine", "nine9nine")
                .replace("zero", "zero0zero")
            val nums = simplified.toCharArray().filter { it in '0'..'9' }
            total += (nums.first().toString() + nums.last().toString()).toInt()
        }
        return total
    }

    fun part2a(input: List<String>): Int {
        val replacements = listOf("one" to "o1e", "two" to "t2o", "three" to "t3e", "four" to "4", "five" to "5e",
            "six" to "6", "seven" to "7n", "eight" to "e8t", "nine" to "n9e")
        var total = 0
        for (line in input) {
            var simplified = line
            for (replacement in replacements)
                simplified = simplified.replace(replacement.first, replacement.second)
            val nums = simplified.toCharArray().filter { it in '0'..'9' }
            total += (nums.first().toString() + nums.last().toString()).toInt()
        }
        return total
    }

    val testInput = readInputString("day01/test")
    val testInput2 = readInputString("day01/test2")
    val input = readInputString("day01/input")

    check(part1(testInput) == 142)
    println(part1(input))

    check(part1a(testInput) == 142)
    println(part1a(input))

    check(part2(testInput2) == 281)
    println(part2(input))

    check(part2a(testInput2) == 281)
    println(part2a(input))
}
