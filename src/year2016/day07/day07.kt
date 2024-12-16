package year2016.day07

import utils.readInputString
import kotlin.system.measureTimeMillis

fun main() {
    val hypernet = """\[[a-z]*\]""".toRegex()

    fun hasAbba(sub: String): Boolean {
        for (i in 0..sub.length - 4) {
            if (sub[i] == sub[i + 3] && sub[i + 1] == sub[i + 2] && sub[i] != sub[i + 1])
                return true
        }
        return false
    }

    fun supportTLS(ip: String): Boolean {
        val hypers = hypernet.findAll(ip)
        for (hyper in hypers) {
            if (hasAbba(hyper.value))
                return false
        }
        val nonHypers = ip.split(hypernet)
        for (non in nonHypers) {
            if (hasAbba(non))
                return true
        }
        return false
    }

    fun getAbas(sub: String): Set<String> {
        val items = mutableSetOf<String>()
        for (i in 0..sub.length - 3) {
            if (sub[i] == sub[i + 2] && sub[i] != sub[i + 1])
                items.add(sub.substring(i..i + 2))
        }
        return items
    }

    fun flipAbas(abas: Set<String>): Set<String> {
        val newItems = mutableSetOf<String>()
        for (item in abas)
            newItems.add("${item[1]}${item[0]}${item[1]}")
        return newItems
    }

    fun supportSLS(ip: String): Boolean {
        val hypers = hypernet.findAll(ip)
        val supers = ip.split(hypernet)

        val hyperSet = mutableSetOf<String>()
        for (hyper in hypers)
            hyperSet.addAll(getAbas(hyper.value))

        val superSet = mutableSetOf<String>()
        for (s in supers) {
            superSet.addAll(getAbas(s))
        }

        val flipped = flipAbas(hyperSet)
        return flipped.any { superSet.contains(it) }
    }

    fun part1(input: List<String>): Int {
        return input.count { supportTLS(it) }
    }

    fun part2(input: List<String>): Int {
        return input.count { supportSLS(it) }
    }

    val test = readInputString("year2016/day07/test")
    val input = readInputString("year2016/day07/input")

    check(part1(test) == 3)
    val timer1 = measureTimeMillis {
        println(part1(input))
    }
    println("Part 1 took $timer1 ms\n")

    val timer2 = measureTimeMillis {
        println(part2(input))
    }
    println("Part 2 took $timer2 ms")
}
