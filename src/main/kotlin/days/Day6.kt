package days

import util.Vec2
import util.at
import util.cycle
import util.grid2

object Day6 : Day {
    override val name = "Guard Gallivant"

    private fun directions() =
        sequenceOf(
            Vec2(0, 1),
            Vec2(1, 0),
            Vec2(0, -1),
            Vec2(-1, 0)
        ).cycle().iterator()

    override fun part1(input: List<String>): Int {
        val grid = input.grid2()
        val obstacles = grid.filter { input.at(it) == '#' }.toSet()
        val directions = directions()

        var guardPos = grid.first { input.at(it) == '^' }
        val visited = mutableSetOf(guardPos)
        var dir = directions.next()
        while (true) {
            val newPos = guardPos + dir
            if (newPos in obstacles) {
                dir = directions.next()
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
            val directions = directions()

            var guardPos = initialGuardPosition
            var dir = directions.next()

            // this could be optimized to reduce cpu time but doesnt really matter for the sake of simplicity
            val visited = mutableSetOf((guardPos to dir))

            val newObstacles = obstacles + ob
            while (true) {
                val newPos = guardPos + dir
                if (newPos in newObstacles) {
                    dir = directions.next()
                    continue
                }
                if (newPos !in grid) break

                // if the guard has already been at this point while travelling
                // in the same direction, we know they are in a cycle
                if (!visited.add((newPos to dir))) {
                    cycleCount += 1
                    break
                }
                guardPos = newPos
            }
        }
        return cycleCount
    }
}