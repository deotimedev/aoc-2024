package days

object Day3 : Day {
    override val name = "Mull It Over"

    private val pattern1 = "mul\\(([0-9]+),([0-9]+)\\)".toRegex()
    override fun part1(input: List<String>) =
        input
            .flatMap(pattern1::findAll)
            .map { it.groupValues }
            .sumOf { (_, a, b) -> a.toInt() * b.toInt() }

    private val pattern2 = "mul\\(([0-9]+),([0-9]+)\\)|do\\(\\)|don't\\(\\)".toRegex()
    override fun part2(input: List<String>): Int {
        val groups = input
            .flatMap(pattern2::findAll)
            .map { it.groupValues }
        var enabled = true
        var sum = 0
        for (group in groups) {
            when (group[0]) {
                "do()" -> enabled = true
                "don't()" -> enabled = false
                else if (enabled) -> {
                    val (_, a, b) = group
                    sum += (a.toInt() * b.toInt())
                }
            }
        }
        return sum
    }

}