package util

// Utilities for working with 2d grid spaces

data class Vec2(val x: Int, val y: Int)
data class Point2(val x: Int, val y: Int) {
    operator fun plus(v: Vec2) =
        Point2(x + v.x, y + v.y)
}
data class Grid2(val x: Int, val y: Int) {
    init {
        require(x > 0 && y > 0) { "Grid is empty" }
    }

    operator fun iterator() = iterator {
        for (px in 0..(x - 1)) {
            for (py in 0..(y - 1)) {
                yield(Point2(px, py))
            }
        }
    }

    operator fun contains(p: Point2) =
        p.x >= 0 && p.y >= 0 && p.x < x && p.y < y
}

fun <T> List<List<T>>.grid2(): Grid2 {
    val y = size
    val x = firstOrNull()?.size ?: 0
    if (any { it.size != x }) throw IllegalArgumentException("Non-rectangular list")
    return Grid2(x, y)
}

@JvmName("strGrid2")
fun List<String>.grid2(): Grid2 {
    val y = size
    val x = firstOrNull()?.length ?: 0
    if (any { it.length != x }) throw IllegalArgumentException("Non-rectangular list")
    return Grid2(x, y)
}

fun <T> List<List<T>>.at(p: Point2) =
    this[size - p.y][p.x]
