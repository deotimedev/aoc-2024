package days

interface Day {
    val name: String
    fun part1(input: List<String>): Int
    fun part2(input: List<String>): Int = TODO("Part 2 not yet available")
}