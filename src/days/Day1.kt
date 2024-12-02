package days

import Day
import kotlin.math.abs

object Day1 : Day {
    override val name = "Historian Hysteria"

    private fun locations(input: List<String>) = input
        .map {
            val (l1, l2) = it.split("   ")
            l1.toInt() to l2.toInt()
        }.unzip()

    override fun part1(input: List<String>): Int {
        val (a, b) = locations(input)

        return a.sorted()
            .zip(b.sorted())
            .sumOf { (l1, l2) -> abs(l1 - l2) }
    }

    override fun part2(input: List<String>): Int {
        val (a, b) = locations(input)
        val counts = b.groupingBy { it }.eachCount()
        return a.sumOf { it * (counts[it] ?: 0) }
    }
}