package days

import util.pow

object Day7 : Day {
    override val name = "Bridge Repair"

    private fun parse(input: List<String>) =
        input.map {
            val (test, nums) = it.split(": ")
            test.toLong() to nums.split(" ").map { it.toLong() }
        }

    override fun part1(input: List<String>) =
        parse(input).sumOf { (test, nums) ->
            val slots = nums.size - 1
            val perms = (2 pow slots) - 1
            for (ops in 0..perms) {
                val eval = nums.reduceIndexed { i, a, b ->
                    val op = (ops shr (i - 1)) and 1
                    if (op == 0) a + b else a * b
                }
                if (eval == test) return@sumOf test
            }
            0L
        }

    override fun part2(input: List<String>) =
        parse(input).sumOf { (test, nums) ->
            val slots = nums.size - 1
            val perms = (3 pow slots) - 1
            for (perm in 0..perms) {
                val ops = perm.toString(3).padStart(slots, '0')
                val eval = nums.reduceIndexed { i, a, b ->
                    val op = ops[i - 1]
                    when (op) {
                        '0' -> a + b
                        '1' -> a * b
                        else -> "$a$b".toLong()
                    }
                }
                if (eval == test) return@sumOf test
            }
            0L
        }
}