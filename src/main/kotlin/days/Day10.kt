package days

import util.Point2
import util.Vec2
import util.at
import util.grid2

object Day10 : Day {
    override val name = "Hoof It"

    override fun part1(input: List<String>): Any {
        val grid = input.grid2()
        val tiles = input.map { it.map { it.digitToInt() } }
        fun score(last: Int, cur: Point2, wins: MutableSet<Point2>) {
            if (cur !in grid) return
            val value = tiles.at(cur)
            if (value != last + 1) return
            if (value == 9) {
                wins += cur
                return
            }
            Vec2.UDLR.forEach {
                score(value, cur + it, wins)
            }
        }

        val trailheads = grid.filter { tiles.at(it) == 0 }
        return trailheads.sumOf {
            val wins = mutableSetOf<Point2>()
            score(-1, it, wins)
            wins.size
        }
    }

    override fun part2(input: List<String>): Any {
        val grid = input.grid2()
        val tiles = input.map { it.map { it.digitToInt() } }
        fun score(last: Int, cur: Point2): Int {
            if (cur !in grid) return 0
            val value = tiles.at(cur)
            if (value != last + 1) return 0
            if (value == 9) return 1
            return Vec2.UDLR.sumOf {
                score(value, cur + it)
            }
        }

        val trailheads = grid.filter { tiles.at(it) == 0 }
        return trailheads.sumOf { score(-1, it) }
    }
}