package days

object Day5 : Day {
    override val name = "Print Queue"

    private fun extractRules(input: List<String>) =
        input
            .filter { "|" in it }
            .map { it.split("|") }
            .map { (a, b) -> a.toInt() to b.toInt() }

    private fun extractUpdates(input: List<String>) =
        input
            .filter { "," in it }
            .map { it.split(",").map { it.toInt() } }

    // creates a comparator based on the ruleset
    // number a is "less" than number b if there is a rule
    // that number a comes before number b, otherwise they
    // are considered equal so they stay in the same order
    private fun comparer(rules: List<Pair<Int, Int>>) =
        Comparator<Int> { a, b ->
            if ((a to b) in rules) -1 else 0
        }

    override fun part1(input: List<String>): Int {
        val rules = extractRules(input)
        val updates = extractUpdates(input)
        val comparer = comparer(rules)
        val validUpdates =
            updates.filter { it.sortedWith(comparer) == it }
        return validUpdates.sumOf { it[it.size / 2] }
    }

    override fun part2(input: List<String>): Int {
        val rules = extractRules(input)
        val updates = extractUpdates(input)

        val comparer = comparer(rules)
        val fixedUpdates =
            updates.mapNotNull {
                it.sortedWith(comparer)
                    .takeIf { new -> new != it }
            }
        return fixedUpdates.sumOf { it[it.size / 2] }
    }
}