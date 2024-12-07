package days

interface Day {
    val name: String
    fun part1(input: List<String>): Any
    fun part2(input: List<String>): Any = TODO("Part 2 not yet available")
}