import kotlin.time.measureTimedValue

interface Day {
    val name: String
    fun part1(input: List<String>): Int
    fun part2(input: List<String>): Int = TODO("Part 2 not yet available")
}

fun main() {
    val day = run {
        print("Enter day to run: ")
        readln().toIntOrNull() ?: return System.err.println("Invalid day.")
    }

    val runner =
        runCatching { Class.forName("days.Day${day}").getDeclaredField("INSTANCE").get(null) as Day }
            .getOrNull() ?: return System.err.println("Unable to load day $day, make sure it is defined correctly (days/Day$day.kt).")

    println()
    println("Running Day $day (${runner.name})...")

    val input = readInput("Day${day}")
    val (result1, time1) = measureTimedValue { runner.part1(input) }
    println("--------------------------------")
    println("Part 1: $result1 ($time1)")

    try {
        val (result2, time2) = measureTimedValue { runner.part2(input) }
        println("Part 2: $result2 ($time2)")
    } catch (e: NotImplementedError) {
        println("Part 2: Not implemented yet")
    }
    println("--------------------------------")
}