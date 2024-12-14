package days

import util.Point2
import util.Vec2

object Day13 : Day {
    override val name = "Claw Contraption"

    data class Machine(
        val bA: Vec2,
        val bB: Vec2,
        val goal: Point2
    )

    private val buttonRegex = "Button [A,B]: X\\+([0-9]+), Y\\+([0-9]+)".toRegex()
    private val prizeRegex = "Prize: X=([0-9]+), Y=([0-9]+)".toRegex()
    private fun machines(input: List<String>): List<Machine> {
        val machines = mutableListOf<Machine>()
        val queue = ArrayDeque(input)
        while (queue.isNotEmpty()) {
            fun next(r: Regex) = r.find(queue.removeFirst())?.destructured?.let { (a, b) -> Vec2(a.toInt(), b.toInt()) }!!
            val a = next(buttonRegex)
            val b = next(buttonRegex)
            val goal = next(prizeRegex).toPoint()
            queue.removeFirstOrNull() // pop newline
            machines += Machine(a, b, goal)
        }
        return machines
    }

    private fun List<Machine>.calc(offset: Long = 0) = sumOf { m ->
        with (m) {
            // (bA.x)x + (bB.x)y = goal.x
            // (bA.y)x + (bB.y)y = goal.ym
            // guess making this was useful https://github.com/deotimedev/matrix-rs
            val det = (bA.x * bB.y) - (bA.y * bB.x)
            val gx = goal.x + offset
            val gy = goal.y + offset
            val a = ((gx * bB.y) - (gy * bB.x)) / det
            val b = ((gy * bA.x) - (gx * bA.y)) / det
            if ((bA.x * a) + (bB.x * b) == gx && (bA.y * a) + (bB.y * b) == gy) (3 * a) + b
            else 0L
        }
    }

    override fun part1(input: List<String>) = machines(input).calc()
    override fun part2(input: List<String>) = machines(input).calc(offset = 10000000000000)
}