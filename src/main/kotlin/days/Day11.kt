package days

import util.splitAt
import kotlin.math.log10

// this one was devious
object Day11 : Day {
    override val name = "Plutonian Pebbles"

    private fun part(input: List<String>, blinks: Int): Long {
        val stones = input.first().split(" ").map { it.toLong() }

        // MAP: depth -> num -> resuling counts
        val map = mutableMapOf<Int, MutableMap<Long, Long>>()
        fun calc(stone: Long, n: Int): Long {
            map[n]?.get(stone)?.let { return it }
            if (n == blinks) return 1
            val value = when {
                stone == 0L -> calc(1, n + 1)
                (stone.toString().length) % 2 == 0 -> {
                    val s = stone.toString()
                    val (a, b) = s.splitAt(s.length / 2)
                    calc(a.toLong(), n + 1) + calc(b.toLong(), n + 1)
                }
                else -> calc(stone * 2024, n + 1)
            }
            map.getOrPut(n) { mutableMapOf() }[stone] = value
            return value
        }
        return stones.sumOf { calc(it, 0) }
    }

    override fun part1(input: List<String>) = part(input, blinks = 25)
    override fun part2(input: List<String>) = part(input, blinks = 75)
}