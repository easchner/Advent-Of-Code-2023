package year2023.day02

import readInputString
import java.lang.Integer.max

fun main() {
    fun part1(input: List<String>): Int {
        var output = 0
        var game = 0

        for (line in input) {
            game++
            var maxGreen = 0
            var maxRed = 0
            var maxBlue = 0

            val turns = line.split(": ")[1].split("; ")
            for (turn in turns) {
                val colors = turn.split(", ")
                for (color in colors) {
                    val shown = color.split(" ")
                    when (shown[1]) {
                        "red" -> maxRed = max(maxRed, shown[0].toInt())
                        "green" -> maxGreen = max(maxGreen, shown[0].toInt())
                        "blue" -> maxBlue = max(maxBlue, shown[0].toInt())
                    }
                }
            }

            if (maxRed < 13 && maxGreen < 14 && maxBlue < 15)
                output += game
        }

        return output
    }

    fun part2(input: List<String>): Int {
        var output = 0
        var game = 0

        for (line in input) {
            game++
            var maxGreen = 0
            var maxRed = 0
            var maxBlue = 0
            val turns = line.split(": ")[1].split("; ")
            for (turn in turns) {
                val colors = turn.split(", ")
                for (color in colors) {
                    val shown = color.split(" ")
                    when (shown[1]) {
                        "red" -> maxRed = max(maxRed, shown[0].toInt())
                        "green" -> maxGreen = max(maxGreen, shown[0].toInt())
                        "blue" -> maxBlue = max(maxBlue, shown[0].toInt())
                    }
                }
            }

            output += maxGreen * maxRed * maxBlue
        }

        return output
    }


    val testInput = readInputString("day02/test")
    val input = readInputString("day02/input")

    check(part1(testInput) == 8)
    println(part1(input))

    check(part2(testInput) == 2286)
    println(part2(input))
}
