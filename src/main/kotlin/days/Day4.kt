package days

import util.Point2
import util.Vec2
import util.grid2

object Day4 : Day {
    override val name = "Ceres Search"
    
    override fun part1(input: List<String>): Int {


        val grid = input.grid2()
        val cMax = input.lastIndex
        fun at(p: Point2) = input[p.x][p.y]


        fun test(p: Point2, dir: Vec2): Boolean {
            var cur = p
            for (letter in charArrayOf('M', 'A', 'S')) {
                cur += dir
                if (cur !in grid) return false
                if (at(cur) != letter) return false
            }
            return true
        }

        val directions =
            (-1..1).flatMap { a -> (-1..1).map { b -> Vec2(a, b) } } - Vec2(0, 0)

        var count = 0
        for (p in grid) {
            if (at(p) == 'X') count += directions.count { test(p, it) }
        }

        return count
    }

    override fun part2(input: List<String>): Int {

        fun at(p: Point2) = input[p.x][p.y]

        val grid = input.grid2()

        val directions = listOf(
            Vec2(-1, -1) to Vec2(1, 1),
            Vec2(-1, 1) to Vec2(1, -1)
        )

        fun test(p: Point2): Boolean {
            for ((d1, d2) in directions) {
                val p1 = p + d1
                val p2 = p + d2

                if (p1 !in grid || p2 !in grid)
                    return false

                val ch1 = at(p1)
                val ch2 = at(p2)

                // "If either ch1 or ch2 is the letter M, and
                // either ch1 or ch2 is the letter S, then continue"
                if (!((ch1 == 'M' || ch2 == 'M') && (ch1 == 'S' || ch2 == 'S')))
                    return false
            }
            return true
        }

        var count = 0
        for (p in grid) {
            if (at(p) == 'A' && test(p)) count++
        }

        return count
    }
}