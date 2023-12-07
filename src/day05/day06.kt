package day05

import readInputSpaceDelimited
import java.lang.Long.max
import java.lang.Long.min
import kotlin.system.measureTimeMillis

data class RangeMap(val destStart: Long, val sourceStart: Long, val size: Long)
fun main() {
    fun parseGroup(group: List<String>): List<RangeMap> {
        val ranges = mutableListOf<RangeMap>()
        for (line in group.takeLast(group.size - 1)) {
            val nums = line.split(" ")
            ranges.add (RangeMap(nums[0].toLong(), nums[1].toLong(), nums[2].toLong()))
        }
        return ranges
    }

    fun convertRange(range: RangeMap, value: Long): Long? {
        if (value in range.sourceStart until (range.sourceStart + range.size)) {
            return range.destStart + (value - range.sourceStart)
        }
        return null
    }

    fun mapSeed(groups: List<List<RangeMap>>, seed: Long): Long {
        var current = seed
        for (group in groups) {
            for (range in group) {
                val converted = convertRange(range, current)
                if (converted != null) {
                    current = converted
                    break
                }
            }
        }
        return current
    }

    fun part1(input: List<List<String>>): Long {
        val seeds = input[0][0].split(": ")[1].split(" ").map { it.toLong() }
        val groups = input.takeLast(input.size - 1).map {
            parseGroup(it)
        }

        val mappedSeeds = seeds.map { mapSeed(groups, it) }
        return mappedSeeds.min()
    }

    fun rangeIntersect(r1: LongRange, r2: LongRange): LongRange {
        val left = max(r1.first, r2.first)
        val right = min(r1.last, r2.last)
        if (right - left + 1 > 0)
            return left..right
        return LongRange.EMPTY
    }

    fun mapSeedPart2(transitions: List<List<RangeMap>>, seedRanges: List<LongRange>): List<LongRange> {
        var currentRanges = seedRanges
        for (transition in transitions) {
            val newRanges = mutableListOf<LongRange>()

            // Find all intersections
            for (r in currentRanges) {
                for (line in transition) {
                    // The Kotlin built-in intersection takes forever! Maybe converting to sets? :thonk:
                    // val intersect = (line.sourceStart until (line.sourceStart + line.size)).intersect(r)
                    val intersect = rangeIntersect(line.sourceStart until (line.sourceStart + line.size), r)
                    if (!intersect.isEmpty()) {
                        val start = line.destStart + (intersect.first() - line.sourceStart)
                        newRanges.add(start until start + intersect.last - intersect.first + 1)
                    }
                }
            }

            // Find all non-intersections
            for (r in currentRanges) {
                val noIntersects = mutableSetOf(r)
                for (line in transition) {
                    val next = mutableSetOf<LongRange>()
                    for (nir in noIntersects) {
                        if (nir.first < line.sourceStart) {
                            next.add(nir.first..(min(nir.last, line.sourceStart - 1)))
                        }
                        if (nir.last > line.sourceStart + line.size - 1) {
                            next.add((max(nir.first, line.sourceStart + line.size)..nir.last))
                        }
                    }
                    noIntersects.clear()
                    noIntersects.addAll(next)
                }
                newRanges.addAll(noIntersects)
            }

            currentRanges = newRanges
        }
        return currentRanges
    }

    fun part2(input: List<List<String>>): Long {
        val seeds = input[0][0].split(": ")[1].split(" ").chunked(2).map {
            it[0].toLong() until it[0].toLong() + it[1].toLong() }
        val groups = input.takeLast(input.size - 1).map {
            parseGroup(it)
        }

        var min = Long.MAX_VALUE
        for (seedRange in seeds) {
            for (i in seedRange) {
                val endVal = mapSeed(groups, i)
                if (endVal < min)
                    min = endVal
            }
            println("seed range done")
        }
        return min
    }

    fun part2Good(input: List<List<String>>): Long {
        val seeds = input[0][0].split(": ")[1].split(" ").chunked(2).map {
            it[0].toLong()..it[0].toLong() + it[1].toLong() }
        val groups = input.takeLast(input.size - 1).map {
            parseGroup(it)
        }

        val endRanges = mapSeedPart2(groups, seeds)
        return endRanges.minOfOrNull { it.first }!!
    }

    val testInput = readInputSpaceDelimited("day05/test")
    val input = readInputSpaceDelimited("day05/input")

    check(part1(testInput) == 35L)
    println(part1(input))

//    check(part2(testInput) == 46L)
//    val badTimer = measureTimeMillis {
//        println(part2(input))
//    }
//    println("Bad solution took $badTimer ms")

    check(part2Good(testInput) == 46L)
    val timer = measureTimeMillis {
        println(part2Good(input))
    }
    println("Good solution took $timer ms")
}
