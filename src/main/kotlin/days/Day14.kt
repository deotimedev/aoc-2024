package days

import util.Grid2
import util.Point2
import util.Vec2
import java.awt.Color
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

object Day14 : Day {
    override val name = "Restroom Redoubt"

    data class Robot(
        var pos: Point2,
        val vel: Vec2,
        val id: Int = ri++
    )

    private val robotRegex = "p=([0-9]+),([0-9]+) v=(-?[0-9]+),(-?[0-9]+)".toRegex()
    private var ri = 1
    private fun robots(input: List<String>) =
        input
            .mapNotNull { robotRegex.find(it)?.destructured }
            .map { (px, py, vx, vy) -> Robot(Point2(px.toInt(), py.toInt()), Vec2(vx.toInt(), vy.toInt())) }

    override fun part1(input: List<String>): Long {
        val grid = Grid2(101, 103)
        val robots = robots(input)
        with(grid) {
            repeat(100) {
                for (r in robots) r.pos = r.pos.wrappingAdd(r.vel)
            }
        }

        val quadrants = Array(2) { IntArray(2) }
        val midX = grid.x / 2
        val midY = grid.y / 2
        for (r in robots) {
            if (r.pos.x == midX || r.pos.y == midY) continue
            val qx = if (r.pos.x > midX) 0 else 1
            val qy = if (r.pos.y > midY) 0 else 1
            quadrants[qy][qx] += 1
        }

        return quadrants.flatMap { it.toList() }.fold(1) { acc, n -> acc * n }
    }


    private const val CreateImages = false
    override fun part2(input: List<String>): String {
        val grid = Grid2(101, 103)
        val robots = robots(input)

        fun renderRobots(i: Int) {
            val img = BufferedImage(grid.x, grid.y, BufferedImage.TYPE_INT_RGB)
            val matrix = grid.toBitMatrix().apply { robots.forEach { set(it.pos, true) } }
            var ly = 0
            for (py in 0..(grid.y - 1)) {
                for (px in 0..(grid.x - 1)) {
                    val p = Point2(px, py)
                    if (p.y > ly) {
                        ly = p.y
                    }
                    val c = if (matrix[p]) Color.BLACK else Color.WHITE
                    img.setRGB(px, py, c.rgb)
                }
            }
            val file = File("robots/$i.png")
            ImageIO.write(img, "png", file)
        }

        with(grid) {
            repeat(10000) {
                for (r in robots) r.pos = r.pos.wrappingAdd(r.vel)
                if (CreateImages) {
                    val id = it + 1
                    renderRobots(id)
                    if (id % 100 == 0) println("Progress: $id/10000")
                }
            }
        }

        return "file explorer"
    }
}