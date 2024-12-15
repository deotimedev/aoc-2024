package util

import util.Point2
import java.util.BitSet

// Utilities for working with 2d grid spaces

data class Vec2(val x: Int, val y: Int) {

    operator fun plus(other: Vec2) = Vec2(x + other.x, y + other.y)

    fun toPoint() = Point2(x, y)

    companion object {
        val Up = Vec2(0, -1)
        val Down = Vec2(0, 1)
        val Left = Vec2(-1, 0)
        val Right = Vec2(1, 0)
        val UD = listOf(Up, Down)
        val LR = listOf(Left, Right)
        val UDLR = UD + LR
        val Corners =
            listOf(Up, Left, Down, Right, Up)
                .zipWithNext()
                .map { (a, b) -> listOf(a, b, a + b) }
    }
}

data class Point2(val x: Int, val y: Int) {
    operator fun plus(v: Vec2) =
        Point2(x + v.x, y + v.y)

    fun displacement(other: Point2) =
        Vec2(x - other.x, y - other.y)


}

data class Grid2(val x: Int, val y: Int) : Sequence<Point2> {
    init {
        require(x > 0 && y > 0) { "Grid is empty" }
    }

    override fun iterator() = iterator {
        for (px in 0..(x - 1)) {
            for (py in 0..(y - 1)) {
                yield(Point2(px, py))
            }
        }
    }

    operator fun contains(p: Point2) =
        p.x >= 0 && p.y >= 0 && p.x < x && p.y < y

    fun toBitMatrix() = BitMatrix(this)

    fun Point2.wrappingAdd(v: Vec2) =
        Point2(x.wrappingAdd(v.x, this@Grid2.x), y.wrappingAdd(v.y, this@Grid2.y))
}

@JvmInline
value class BitMatrix(val matrix: List<BitSet>) {
    constructor(grid: Grid2) : this(List(grid.y) { BitSet(grid.x) })

    operator fun get(p: Point2) =
        matrix[p.y][p.x]

    operator fun set(p: Point2, value: Boolean) =
        matrix[p.y].set(p.x, value)

    fun getAndSet(p: Point2, value: Boolean = true): Boolean {
        val old = get(p)
        this[p] = value
        return old
    }
}

fun List<String>.grid2(): Grid2 {
    val y = size
    val x = firstOrNull()?.length ?: 0
    if (any { it.length != x }) throw IllegalArgumentException("Non-rectangular list")
    return Grid2(x, y)
}

fun List<String>.at(p: Point2) =
    this[p.y][p.x]

fun <T> List<List<T>>.at(p: Point2) =
    this[p.y][p.x]