package year2023.day19

import utils.readInputSpaceDelimited
import java.lang.Integer.max
import java.lang.Integer.min
import kotlin.system.measureTimeMillis

data class Rule(val xmas: Char, val greater: Boolean, val number: Int, val state: String)
data class State(val name: String, val rules: List<Rule>)
data class Part(val x: Int, val m: Int, val a: Int, val s: Int, var state: String)
data class PartRange(val min: Int, val max: Int)
data class GhostPart(var x: PartRange, var m: PartRange, var a: PartRange, var s: PartRange, var state: String)

fun main() {
    fun parseState(input: String): State {
        val name = input.split("{").first()
        val rules = input.split("{")[1].dropLast(1).split(",")
        val default = Rule('x', true, -1, rules.last())
        return State(name = name, rules =
            rules.dropLast(1).map { rule ->
                val xmas = rule.first()
                val greater = rule[1] == '>'
                val number = rule.split(Regex("[<>]"))[1].split(":")[0].toInt()
                val destination = rule.split(":")[1]
                Rule(xmas, greater, number, destination)
            }.toMutableList().plus(default)
        )
    }

    fun parsePart(input: String): Part {
        val n = input.split(",").map { it.split("=")[1].split("}")[0].toInt() }
        return Part(n[0], n[1], n[2], n[3], "in")
    }

    fun part1(input: List<List<String>>): Long {
        val states = input[0].map { parseState(it) }
        val parts = input[1].map { parsePart(it) }

        while (parts.any { !listOf("A", "R").contains(it.state) }) {
            for (part in parts.filter { !listOf("A", "R").contains(it.state) }) {
                val state = states.find { it.name == part.state }!!
                var destination: String? = null
                for (rule in state.rules) {
                    destination = if (rule.greater) {
                        when(rule.xmas) {
                            'x' -> if (part.x > rule.number) rule.state else null
                            'm' -> if (part.m > rule.number) rule.state else null
                            'a' -> if (part.a > rule.number) rule.state else null
                            's' -> if (part.s > rule.number) rule.state else null
                            else -> null
                        }
                    } else {
                        when(rule.xmas) {
                            'x' -> if (part.x < rule.number) rule.state else null
                            'm' -> if (part.m < rule.number) rule.state else null
                            'a' -> if (part.a < rule.number) rule.state else null
                            's' -> if (part.s < rule.number) rule.state else null
                            else -> null
                        }
                    }
                    if (destination != null) {
                        part.state = destination
                        break
                    }
                }
            }
        }

        return parts.filter { it.state == "A" }.sumOf { it.x.toLong() + it.m + it.a + it.s }
    }

    fun part2(input: List<List<String>>): Long {
        val states = input[0].map { parseState(it) }
        var ghosts = listOf(
            GhostPart(PartRange(1, 4000), PartRange(1, 4000), PartRange(1, 4000), PartRange(1, 4000), "in")
        )
        val accepted = mutableListOf<GhostPart>()

        while (ghosts.isNotEmpty()) {
            val next = mutableListOf<GhostPart>()
            for (ghost in ghosts) {
                val state = states.find { it.name == ghost.state }!!
                for (rule in state.rules) {
                    val pr = when (rule.xmas) {
                        'x' -> ghost.x
                        'm' -> ghost.m
                        'a' -> ghost.a
                        's' -> ghost.s
                        else -> throw Exception("rule not xmas - pr")
                    }
                    // If range has nothing, skip
                    if (pr.max > pr.min) {
                        val newGhostRange = if (rule.greater) PartRange(max(rule.number + 1, pr.min), pr.max) else PartRange(pr.min, min(rule.number - 1, pr.max))
                        val thisGhostNewRange = if (rule.greater) PartRange(pr.min, min(rule.number, pr.max)) else PartRange(max(rule.number, pr.min), pr.max)

                        when(rule.xmas) {
                            'x' -> {
                                next.add(GhostPart(newGhostRange, ghost.m, ghost.a, ghost.s, rule.state))
                                ghost.x = thisGhostNewRange
                            }
                            'm' -> {
                                next.add(GhostPart(ghost.x, newGhostRange, ghost.a, ghost.s, rule.state))
                                ghost.m = thisGhostNewRange
                            }
                            'a' -> {
                                next.add(GhostPart(ghost.x, ghost.m, newGhostRange, ghost.s, rule.state))
                                ghost.a = thisGhostNewRange
                            }
                            's' -> {
                                next.add(GhostPart(ghost.x, ghost.m, ghost.a, newGhostRange, rule.state))
                                ghost.s = thisGhostNewRange
                            }
                        }
                    }
                }
            }
            accepted.addAll(next.filter { it.state == "A" })
            next.removeAll { it.state == "A" || it.state == "R" }
            ghosts = next
        }

        return accepted.sumOf { (it.x.max.toLong() - it.x.min + 1) * (it.m.max - it.m.min + 1) * (it.a.max - it.a.min + 1) * (it.s.max - it.s.min + 1) }
    }

    val testInput = readInputSpaceDelimited("day19/test")
    val input = readInputSpaceDelimited("day19/input")

    check(part1(testInput) == 19114L)
    val timer1 = measureTimeMillis {
        println(part1(input))
    }
    println("Part 1 took $timer1 ms\n")

    check(part2(testInput) == 167409079868000L)
    val timer2 = measureTimeMillis {
        println(part2(input))
    }
    println("Part 2 took $timer2 ms")
}
