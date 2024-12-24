package year2024.day24

import utils.readInputSpaceDelimited
import kotlin.system.measureTimeMillis

fun main() {
    fun part1(input: List<List<String>>): Long {
        val wires = mutableMapOf<String, Boolean>()
        for (i in input[0]) {
            val (wire, output) = i.split(": ")
            wires[wire] = output.toInt() == 1
        }

        val zwires = mutableMapOf<String, Boolean?>()
        for (i in input[1]) {
            val (_, output) = i.split(" -> ")
            if (output.first() == 'z') {
                zwires[output] = null
            }
        }

        while (zwires.any { it.value == null }) {
            for (i in input[1]) {
                val (operation, output) = i.split(" -> ")
                if (operation.contains("AND")) {
                    val (a, b) = operation.split(" AND ")
                    if (wires[a] != null && wires[b] != null) {
                        if (zwires.containsKey(output)) {
                            zwires[output] = wires[a]!! && wires[b]!!
                        }
                        wires[output] = wires[a]!! && wires[b]!!
                    }
                } else if (operation.contains(" OR")) {
                    val (a, b) = operation.split(" OR ")
                    if (wires[a] != null && wires[b] != null) {
                        if (zwires.containsKey(output)) {
                            zwires[output] = wires[a]!! || wires[b]!!
                        }
                        wires[output] = wires[a]!! || wires[b]!!
                    }
                } else if (operation.contains("XOR")) {
                    val (a, b) = operation.split(" XOR ")
                    if (wires[a] != null && wires[b] != null) {
                        if (zwires.containsKey(output)) {
                            zwires[output] = wires[a]!! xor wires[b]!!
                        }
                        wires[output] = wires[a]!! xor wires[b]!!
                    }
                }
            }
        }

        var answer = 0L
        for (zwire in zwires.toList().sortedByDescending { it.first }) {
            answer = answer shl 1
            answer += if (zwire.second == null) 0 else if (zwire.second!!) 1 else 0
        }
        return answer
    }

    fun padName(name: String, index: Int): String {
        return if (index < 10) {
            "${name}0$index"
        } else {
            "${name}$index"
        }
    }

    fun runSim(wires: MutableMap<String, Boolean>, instructions: List<String>): List<Boolean> {
        val zwires = mutableMapOf<String, Boolean?>()
        for (i in 0..45) {
            zwires[padName("z", i)] = null
        }

        while (zwires.any { it.value == null }) {
            var updated = false
            for (i in instructions) {
                val (operation, output) = i.split(" -> ")
                if (wires.containsKey(output)) {
                    continue
                }
                if (operation.contains("AND")) {
                    val (a, b) = operation.split(" AND ")
                    if (wires[a] != null && wires[b] != null) {
                        if (zwires.containsKey(output)) {
                            zwires[output] = wires[a]!! && wires[b]!!
                        }
                        wires[output] = wires[a]!! && wires[b]!!
                        updated = true
                    }
                } else if (operation.contains(" OR")) {
                    val (a, b) = operation.split(" OR ")
                    if (wires[a] != null && wires[b] != null) {
                        if (zwires.containsKey(output)) {
                            zwires[output] = wires[a]!! || wires[b]!!
                        }
                        wires[output] = wires[a]!! || wires[b]!!
                        updated = true
                    }
                } else if (operation.contains("XOR")) {
                    val (a, b) = operation.split(" XOR ")
                    if (wires[a] != null && wires[b] != null) {
                        if (zwires.containsKey(output)) {
                            zwires[output] = wires[a]!! xor wires[b]!!
                        }
                        wires[output] = wires[a]!! xor wires[b]!!
                        updated = true
                    }
                }
            }
            if (!updated) {
                return List(45) { true }
            }
        }

        return zwires.toList().sortedBy { it.first }.map { it.second!! }
    }

    fun part2(input: List<List<String>>): String {
        val wires00 = mutableMapOf<String, Boolean>()
        for (j in 0..44) {
            wires00[padName("x", j)] = false
            wires00[padName("y", j)] = false
        }

        val wires01 = mutableMapOf<String, Boolean>()
        for (j in 0..44) {
            wires01[padName("x", j)] = false
            wires01[padName("y", j)] = true
        }

        val wires10 = mutableMapOf<String, Boolean>()
        for (j in 0..44) {
            wires10[padName("x", j)] = true
            wires10[padName("y", j)] = false
        }

        val wires11 = mutableMapOf<String, Boolean>()
        for (j in 0..44) {
            wires11[padName("x", j)] = true
            wires11[padName("y", j)] = true
        }

        val wiresOff = mutableMapOf<String, Boolean>()
        for (j in 0..44) {
            wiresOff[padName("x", j)] = j % 2 == 1
            wiresOff[padName("y", j)] = j % 2 == 0
        }

        for (i in 1..44) {
            for (j in 0..44) {
                wires00[padName("x", j)] = false
                wires00[padName("y", j)] = false
                wires01[padName("x", j)] = false
                wires01[padName("y", j)] = false
                wires10[padName("x", j)] = false
                wires10[padName("y", j)] = false
                wires11[padName("x", j)] = false
                wires11[padName("y", j)] = false
            }

            for (j in 0..i) {
                wires00[padName("x", j)] = false
                wires00[padName("y", j)] = false
                wires01[padName("x", j)] = false
                wires01[padName("y", j)] = true
                wires10[padName("x", j)] = true
                wires10[padName("y", j)] = false
                wires11[padName("x", j)] = true
                wires11[padName("y", j)] = true
            }

            var result00 = runSim(wires00.toMutableMap(), input[1])
            var result01 = runSim(wires01.toMutableMap(), input[1])
            var result10 = runSim(wires10.toMutableMap(), input[1])
            var result11 = runSim(wires11.toMutableMap(), input[1])
            var resultOff = runSim(wiresOff.toMutableMap(), input[1])

            if (result00[i] == false && result01[i] == true && result10[i] == true && result11[i] == true && resultOff[1] == true) {
                println("Correct value for $i")
                continue
            }

            if (result00[i] == true || result01[i] == false || result10[i] == false || result11[i] == false || resultOff[1] == false) {
                println("Wrong value for $i on 00")
                var swap1 = 0
                var swap2 = 1
                while (swap1 < input[1].size - 1) {
                    val dupInst = input[1].toMutableList()
                    val op1 = dupInst[swap1]
                    val op2 = dupInst[swap2]
                    dupInst[swap1] = op1.split(" -> ")[0] + " -> " + op2.split(" -> ")[1]
                    dupInst[swap2] = op2.split(" -> ")[0] + " -> " + op1.split(" -> ")[1]
                    result00 = runSim(wires00.toMutableMap(), dupInst)
                    result01 = runSim(wires01.toMutableMap(), dupInst)
                    result10 = runSim(wires10.toMutableMap(), dupInst)
                    result11 = runSim(wires11.toMutableMap(), dupInst)
                    resultOff = runSim(wiresOff.toMutableMap(), dupInst)
                    if (result00[i] == false && result01[i] == true && result10[i] == true && result11[i] == true && resultOff[1] == true) {
                        println("Could swap $op1 and $op2 for $i on 00")
                    }

                    swap2++
                    if (swap2 >= input[1].size) {
                        swap1++
                        swap2 = swap1 + 1
                        println("Trying line $swap1")
                    }
                }
            }

//            if (result01[i] != true) {
//                println("Wrong value for $i on 01")
//                var swap1 = 0
//                var swap2 = 1
//                while (result01[i] != true) {
//                    val dupInst = input[1].toMutableList()
//                    val op1 = dupInst[swap1]
//                    val op2 = dupInst[swap2]
//                    dupInst[swap1] = op1.split(" -> ")[0] + " -> " + op2.split(" -> ")[1]
//                    dupInst[swap2] = op2.split(" -> ")[0] + " -> " + op1.split(" -> ")[1]
//                    result01 = runSim(wires01.toMutableMap(), dupInst)
//                    if (result01[i] == false) {
//                        println("Need to swap $op1 and $op2 for $i on 01")
//                        break
//                    }
//
//                    swap2++
//                    if (swap2 >= input[1].size) {
//                        swap1++
//                        swap2 = swap1 + 1
//                        println("Trying line $swap1")
//                    }
//                }
//            }
//
//            if (result10[i] != true) {
//                println("Wrong value for $i on 10")
//                var swap1 = 0
//                var swap2 = 1
//                while (result10[i] != true) {
//                    val dupInst = input[1].toMutableList()
//                    val op1 = dupInst[swap1]
//                    val op2 = dupInst[swap2]
//                    dupInst[swap1] = op1.split(" -> ")[0] + " -> " + op2.split(" -> ")[1]
//                    dupInst[swap2] = op2.split(" -> ")[0] + " -> " + op1.split(" -> ")[1]
//                    result10 = runSim(wires10.toMutableMap(), dupInst)
//                    if (result10[i] == true) {
//                        println("Need to swap $op1 and $op2 for $i on 10")
//                        break
//                    }
//
//                    swap2++
//                    if (swap2 >= input[1].size) {
//                        swap1++
//                        swap2 = swap1 + 1
//                        println("Trying line $swap1")
//                    }
//                }
//            }
//
//            if (result11[i] != false) {
//                println("Wrong value for $i on 11")
//                var swap1 = 0
//                var swap2 = 1
//                while (result11[i] != false) {
//                    val dupInst = input[1].toMutableList()
//                    val op1 = dupInst[swap1]
//                    val op2 = dupInst[swap2]
//                    dupInst[swap1] = op1.split(" -> ")[0] + " -> " + op2.split(" -> ")[1]
//                    dupInst[swap2] = op2.split(" -> ")[0] + " -> " + op1.split(" -> ")[1]
//                    result11 = runSim(wires11.toMutableMap(), dupInst)
//                    if (result11[i] == false) {
//                        println("Need to swap $op1 and $op2 for $i on 11")
//                        break
//                    }
//
//                    swap2++
//                    if (swap2 >= input[1].size) {
//                        swap1++
//                        swap2 = swap1 + 1
//                        println("Trying line $swap1")
//                    }
//                }
//            }
        }

        return ""
    }

    val testInput = readInputSpaceDelimited("year2024/day24/test")
    val input = readInputSpaceDelimited("year2024/day24/input")

    check(part1(testInput) == 4L)
    val timer1 = measureTimeMillis {
        println(part1(input))
    }
    println("Part 1 took $timer1 ms\n")

    val timer2 = measureTimeMillis {
        println(part2(input))
    }
    println("Part 2 took $timer2 ms")
}