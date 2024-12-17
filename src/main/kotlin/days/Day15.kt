package days

import util.Point2
import util.Vec2
import util.at
import util.chop
import util.flatMap
import util.grid2
import util.unreachable

object Day15 : Day {
    override val name = "Warehouse Woes"

    override fun part1(input: List<String>): Int {
        val (map, lmoves) = input.chop { it.isBlank() }
        val moves = lmoves.flatMap { it.toCharArray().toList() }

        val grid = map.grid2()
        val walls = grid.toBitMatrix { map.at(it) == '#' }
        val boxes = grid.toBitMatrix { map.at(it) == 'O' }
        var robot = grid.first { map.at(it) == '@' }

        fun move(p: Point2, dir: Vec2): Boolean {
            val new = p + dir
            if (walls[new]) return false
            if (boxes[new]) {
                if (move(new, dir)) {
                    boxes[new] = false
                    boxes[new + dir] = true
                    return true
                } else return false
            }
            return true // empty space
        }

        for (m in moves) {
            val dir = Vec2(m)
            if (move(robot, dir)) robot += dir
        }


        return grid.filter { boxes[it] }.sumOf { it.x + (100 * it.y) }
    }

    override fun part2(input: List<String>): Int {
        val (lmap, lmoves) = input.chop { it.isBlank() }
        val map = lmap.map { l ->
            l.flatMap {
                when (it) {
                    '#' -> "##"
                    '.' -> ".."
                    'O' -> "[]"
                    '@' -> "@."
                    else -> unreachable()
                }
            }
        }
        val moves = lmoves.flatMap { it.toCharArray().toList() }

        data class Box(var l: Point2, var r: Point2)
        val grid = map.grid2()
        val walls = grid.toBitMatrix { map.at(it) == '#' }
        val boxes = grid
            .filter { map.at(it) == '[' }
            .map { Box(it, it + Vec2.Right) }
            .toSet()
        fun box(p: Point2) = boxes.find { (l, r) -> p == l || p == r }
        var robot = grid.first { map.at(it) == '@' }

        fun canMove(p: Point2, dir: Vec2): Boolean {
            val new = p + dir
            if (walls[new]) return false
            box(new)?.let { b ->
                return when (dir) {
                    Vec2.Left -> canMove(b.l, dir)
                    Vec2.Right -> canMove(b.r, dir)
                    else -> canMove(b.l, dir) && canMove(b.r, dir)
                }
            }
            return true
        }

        fun move(p: Point2, dir: Vec2, check: Boolean = true): Boolean {
            if (check && !canMove(p, dir)) return false
            val new = p + dir
            box(new)?.let { b ->
                when (dir) {
                    Vec2.Left -> move(b.l, dir, check = false)
                    Vec2.Right -> move(b.r, dir, check = false)
                    else -> {
                        move(b.l, dir, check = false)
                        move(b.r, dir, check = false)
                    }
                }
                b.l += dir
                b.r += dir
            }
            return true
        }

        for (m in moves) {
            val dir = Vec2(m)
            if (move(robot, dir)) robot += dir
        }

        return boxes.sumOf { (l, _) -> l.x + (100 * l.y) }
    }
}