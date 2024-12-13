package days

import util.Point2
import util.Vec2
import util.at
import util.grid2
import util.setAt
import java.util.BitSet

object Day12 : Day {
    override val name = "Garden Groups"


    override fun part1(input: List<String>): Int {
        data class Shape(val area: Int, val perim: Int)

        val grid = input.grid2()
        val visited = input.map { BitSet(it.length) }
        fun shape(point: Point2): Shape {
            if (visited.at(point)) return Shape(0, 0)
            visited.setAt(point)
            val group = input.at(point)
            val (next, edges) = Vec2.UDLR
                .map { point + it }
                .partition { it in grid && input.at(it) == group }
            return next
                .filter { !visited.at(it) }
                .fold(Shape(1, edges.size)) { acc, p ->
                    val shape = shape(p)
                    Shape(acc.area + shape.area, acc.perim + shape.perim)
                }
        }

        return grid.sumOf { p ->
            if (visited.at(p)) return@sumOf 0
            val shape = shape(p)
            (shape.area * shape.perim)
        }
    }

    override fun part2(input: List<String>): Int {
        data class Shape(val area: Int, val sides: Int)

        val grid = input.grid2()
        val visited = input.map { BitSet(it.length) }
        fun shape(point: Point2): Shape {
            if (visited.at(point)) return Shape(0, 0)
            visited.setAt(point)
            val group = input.at(point)
            val next = Vec2.UDLR
                .map { point + it }
                .filter { it in grid && input.at(it) == group }
            val sides = Vec2.Corners
                .map { cs ->
                    cs.map { (point + it).takeIf(grid::contains)?.let(input::at) }
                }
                .count { (a, b, c) ->
                    (a != group && b != group) // CHECK IF OUTWARD EDGE
                                ||
                    (a == group && b == group && c != group) // CHECK IF INWARD EDGE
                }
            return next
                .filter { !visited.at(it) }
                .fold(Shape(1, sides)) { acc, p ->
                    val shape = shape(p)
                    Shape(acc.area + shape.area, acc.sides + shape.sides)
                }
        }

        return grid.sumOf { p ->
            if (visited.at(p)) return@sumOf 0
            val shape = shape(p)
            (shape.area * shape.sides)
        }
    }
}