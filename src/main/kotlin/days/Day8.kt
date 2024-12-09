package days

import util.Grid2
import util.Point2
import util.at
import util.grid2
import util.inverted

object Day8 : Day {
    override val name = "Resonant Collinearity"

    private fun groups(input: List<String>, grid: Grid2) =
        grid
            .associateWith(input::at)
            .filterValues { it != '.' }
            .inverted()
            .values

    override fun part1(input: List<String>): Any {
        val grid = input.grid2()
        val groups = groups(input, grid)
        val antinodes = mutableSetOf<Point2>()
        for (group in groups) {
            for (a in group) {
                for (b in group) {
                    if (a == b) continue
                    val node = a + a.displacement(b)
                    if (node in grid) antinodes += node
                }
            }
        }
        return antinodes.size
    }

    override fun part2(input: List<String>): Any {
        val grid = input.grid2()
        val groups = groups(input, grid)
        val antinodes = mutableSetOf<Point2>()
        for (group in groups) {
            for (a in group) {
                for (b in group) {
                    if (a == b) continue
                    val displacement = a.displacement(b)
                    var node = a
                    while (node in grid) {
                        antinodes += node
                        node += displacement
                    }
                }
            }
        }
        return antinodes.size
    }
}