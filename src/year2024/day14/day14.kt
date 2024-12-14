package year2024.day14

import utils.readInputString
import kotlin.math.abs
import kotlin.system.measureTimeMillis

data class Robot (
    var x: Int,
    var y: Int,
    var dx: Int,
    var dy: Int
)

fun main() {
    fun iterate(robots: List<Robot>, sizeX: Int, sizeY: Int, iterations: Int): Long {
        for (robot in robots) {
            robot.x = (robot.x + robot.dx * iterations) % sizeX
            robot.y = (robot.y + robot.dy * iterations) % sizeY
            if (robot.x < 0) robot.x += sizeX
            if (robot.y < 0) robot.y += sizeY
        }

        val x1 = sizeX / 2
        val y1 = sizeY / 2
        val quad1 = robots.filter { it.x < x1 && it.y < y1 }.size
        val quad2 = robots.filter { it.x >= sizeX - x1 && it.y < y1 }.size
        val quad3 = robots.filter { it.x < x1 && it.y >= sizeY - y1 }.size
        val quad4 = robots.filter { it.x >= sizeX - x1 && it.y >= sizeY - y1 }.size

        return (quad1 * quad2 * quad3 * quad4).toLong()
    }

    fun part1(input: List<String>, sizeX: Int, sizeY: Int): Long {
        val robots = mutableListOf<Robot>()

        for (line in input) {
            var working = line.replace("""p=|v=""".toRegex(), "").split(" ")
            val (px, py) = working[0].split(",").map { it.toInt() }
            val (dx, dy) = working[1].split(",").map { it.toInt() }
            robots.add(Robot(px, py, dx, dy))
        }

        return iterate(robots, sizeX, sizeY, 100)
    }

    fun part2(input: List<String>, sizeX: Int, sizeY: Int): Long {
        val robots = mutableListOf<Robot>()

        for (line in input) {
            var working = line.replace("""p=|v=""".toRegex(), "").split(" ")
            val (px, py) = working[0].split(",").map { it.toInt() }
            val (dx, dy) = working[1].split(",").map { it.toInt() }
            robots.add(Robot(px, py, dx, dy))
        }

        val dangers = (0 until (sizeX * sizeY)).map {
            seconds -> Pair(seconds , iterate(robots.map { r -> r.copy() }, sizeX, sizeY, seconds))
        }
        val averageDanger = dangers.map { it.second }.average()
        return dangers.maxBy { abs(averageDanger - it.second) }.first.toLong()
    }

    val testInput = readInputString("year2024/day14/test")
    val input = readInputString("year2024/day14/input")

    check(part1(testInput, 11, 7) == 12L)
    val timer1 = measureTimeMillis {
        println(part1(input, 101, 103))
    }
    println("Part 1 took $timer1 ms\n")

//    check(part2(testInput) == 0L)
    val timer2 = measureTimeMillis {
        println(part2(input, 101, 103))
    }
    println("Part 2 took $timer2 ms")
}



/*    fun part2(input: List<String>, sizeX: Int, sizeY: Int): Long {
        val robots = mutableListOf<Robot>()

        //p=0,4 v=3,-3
        for (line in input) {
            var working = line.replace("""p=|v=""".toRegex(), "").split(" ")
            val (px, py) = working[0].split(",").map { it.toInt() }
            val (dx, dy) = working[1].split(",").map { it.toInt() }
            robots.add(Robot(px, py, dx, dy))
        }

        var step = 0
        var min = Int.MAX_VALUE
        while (true) {
            for (robot in robots) {
                robot.x += robot.dx
                robot.y += robot.dy
            }

            for (robot in robots) {
                robot.x = robot.x % sizeX
                robot.y = robot.y % sizeY
                if (robot.x < 0) robot.x += sizeX
                if (robot.y < 0) robot.y += sizeY
            }

            step++

//            val grid = GridUtils2d(List(sizeY) { List(sizeX) { '.' } })
//            for (robot in robots) {
//                grid.working[robot.y][robot.x] = 'R'
//            }
//            grid.expand(1, '.')
//            grid.floodFill('X', 'R')
//            println(step)
//            if (grid.working.flatten().count { it == '.' } > 10) {
//            if (robots.map { it.x to it.y}.distinct().size == robots.size) {
//                for (y in 0 until sizeY) {
//                    for (x in 0 until sizeX) {
//                        if (robots.any { it.x == x && it.y == y }) {
//                            print("#")
//                        } else {
//                            print(".")
//                        }
//                    }
//                    println()
//                }
//                return step.toLong()
//            }

            val x1 = sizeX / 2
            val y1 = sizeY / 2
            val quad1 = robots.filter { it.x < x1 && it.y < y1 }.size
            val quad2 = robots.filter { it.x >= sizeX - x1 && it.y < y1 }.size
            val quad3 = robots.filter { it.x < x1 && it.y >= sizeY - y1 }.size
            val quad4 = robots.filter { it.x >= sizeX - x1 && it.y >= sizeY - y1 }.size
            val new = quad1 * quad2 * quad3 * quad4
            if (new < min) {
                println("$step - $new")
                min = new

                for (y in 0 until sizeY) {
                    for (x in 0 until sizeX) {
                        if (robots.any { it.x == x && it.y == y }) {
                            print("#")
                        } else {
                            print(".")
                        }
                    }
                    println()
                }
            }
        }

 */