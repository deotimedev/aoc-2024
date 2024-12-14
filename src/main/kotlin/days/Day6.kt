package days

import util.Vec2
import util.at
import util.cycle
import util.grid2
import java.util.concurrent.atomic.AtomicInteger

object Day6 : Day {
    override val name = "Guard Gallivant"

    private val directions =
        listOf(
            Vec2(0, 1),
            Vec2(1, 0),
            Vec2(0, -1),
            Vec2(-1, 0)
        )

    override fun part1(input: List<String>): Int {
        val grid = input.grid2()
        val obstacles = grid.filter { input.at(it) == '#' }.toSet()

        var guardPos = grid.first { input.at(it) == '^' }
        val visited = mutableSetOf(guardPos)
        var di = 0
        while (true) {
            val newPos = guardPos + directions[di % 4]
            if (newPos in obstacles) {
                di++
                continue
            }
            if (newPos !in grid) break
            visited += newPos
            guardPos = newPos
        }
        return visited.size
    }

    override fun part2(input: List<String>): Int {
        val grid = input.grid2()
        val obstacles = grid.filter { input.at(it) == '#' }.toSet()
        val initialGuardPosition = grid.first { input.at(it) == '^' }

        var cycleCount = 0
        for (ob in (grid - obstacles)) {

            var guardPos = initialGuardPosition
            var di = 0

            val visited = List(4) { grid.toBitMatrix() }

            val newObstacles = obstacles + ob
            while (true) {
                val newPos = guardPos + directions[di % 4]
                if (newPos in newObstacles) {
                    di++
                    continue
                }
                if (newPos !in grid) break

                // if the guard has already been at this point while travelling
                // in the same direction, we know they are in a cycle
                if (visited[di % 4].getAndSet(newPos)) {
                    cycleCount += 1
                    break
                }
                guardPos = newPos
            }
        }
        return cycleCount
    }
}