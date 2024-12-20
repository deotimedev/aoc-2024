package days

import util.Point2
import util.Vec2
import util.Vec2.Companion.dirCode
import util.at
import util.grid2
import java.util.BitSet
import java.util.PriorityQueue

object Day16 : Day {
    override val name = "Reindeer Maze"


    data class Node(val pos: Point2, val dir: Vec2, val score: Int, val path: List<Point2> = emptyList())
    private val scoreComparator = compareBy(Node::score)

    // https://www.cs.usfca.edu/~galles/visualization/Dijkstra.html helpful thing
    private fun bestPathScore(input: List<String>): Int {
        val grid = input.grid2()
        val walls = grid.toBitMatrix { input.at(it) == '#' }
        val start = grid.first { input.at(it) == 'S' }
        val end = grid.first { input.at(it) == 'E' }

        val visited = grid.toBitMatrix()
        val initial = Node(start, Vec2.Right, 0)
        val q = PriorityQueue<Node>(scoreComparator)
        q.add(initial)
        var best = Int.MAX_VALUE
        while (q.isNotEmpty()) {
            val cur = q.remove() // node with least score
            if (cur.score > best) continue
            visited[cur.pos] = true
            for (d in Vec2.UDLR) {
                if (d == (cur.dir * -1)) continue // dont go backwards

                val cost = if (d == cur.dir) 1 else 1001
                val next = Node(cur.pos + d, d, cur.score + cost)
                if (visited[next.pos] || walls[next.pos]) continue

                if (next.pos == end) best = best.coerceAtMost(next.score)
                else q.add(next)
            }
        }
        return best
    }

    override fun part1(input: List<String>) = bestPathScore(input)

    override fun part2(input: List<String>): Int {
        val grid = input.grid2()
        val walls = grid.toBitMatrix { input.at(it) == '#' }
        val start = grid.first { input.at(it) == 'S' }
        val end = grid.first { input.at(it) == 'E' }

        val visited = List(4) { mutableMapOf<Point2, Int>() }
        val bestPathPoints = grid.toBitMatrix().apply { set(start) }
        val initial = Node(start, Vec2.Right, 0)
        val q = PriorityQueue<Node>(scoreComparator)
        q.add(initial)
        val best = bestPathScore(input)
        while (q.isNotEmpty()) {
            val cur = q.remove()
            if (cur.score > best) continue
            val visitedScore = visited[cur.dir.dirCode][cur.pos] ?: Int.MAX_VALUE
            if (cur.score > visitedScore) continue
            visited[cur.dir.dirCode][cur.pos] = cur.score
            for (d in Vec2.UDLR) {
                if (d == (cur.dir * -1)) continue

                val cost = if (d == cur.dir) 1 else 1001
                val newPos = cur.pos + d
                val next = Node(cur.pos + d, d, cur.score + cost, cur.path + newPos)
                if (walls[next.pos] || next.pos in cur.path) continue

                if (next.pos == end) bestPathPoints.setAll(next.path)
                else q.add(next)
            }
        }
        return bestPathPoints.count()
    }
}