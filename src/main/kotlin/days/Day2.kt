package days

import kotlin.math.abs
import kotlin.math.sign

object Day2 : Day {
    override val name = "Red-Nosed Reports"

    private fun reports(input: List<String>) =
        input.map { it.split(" ").map { it.toInt() } }

    private fun safe(levels: List<Int>): Boolean {
        val deltas = levels
            .windowed(2)
            .map { (a, b) -> b - a }
        val direction = deltas.first().sign
        return deltas.all { it.sign == direction && abs(it) in (1..3) }
    }

    override fun part1(input: List<String>) =
        reports(input).count(::safe)


    override fun part2(input: List<String>): Int {
        fun <T> List<T>.removeAt(index: Int) =
            subList(0, index) + subList(index + 1, size)

        return reports(input).count { levels ->
            safe(levels) || levels.indices.map(levels::removeAt).any(::safe)
        }
    }

}