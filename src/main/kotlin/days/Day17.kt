package days

import util.pow
import util.unreachable

object Day17 : Day {
    override val name = "Chronospatial Computer"

    @JvmInline
    value class Code(val value: Int)

    // opcodes
    private const val ADV = 0
    private const val BXL = 1
    private const val BST = 2
    private const val JNZ = 3
    private const val BXC = 4
    private const val OUT = 5
    private const val BDV = 6
    private const val CDV = 7

    private fun part(part: Int, input: List<String>): Any {
        val program = input[4].split(": ")[1].split(",").map { it.toInt() }

        fun register(i: Int) = input[i].split(": ")[1].toLong()
        fun runProgram(a: Long = register(0), b: Long = register(1), c: Long = register(2)): List<Int> {
            val output = mutableListOf<Int>()

            var a = a
            var b = b
            var c = c
            var ip = 0

            fun Code.combo() = when (value) {
                0, 1, 2, 3 -> value.toLong()
                4 -> a
                5 -> b
                6 -> c
                else -> error("$value is not a valid combo op")
            }

            fun Code.literal() = value

//            fun pa() {
//                if (part == 2) println("a base8: ${a.toString(8)}")
//            }
            while ((ip + 1) < program.size) {
                val op = Code(program[ip])
                val arg = Code(program[ip + 1])
                var inc = true
                fun dv() = (a / (2L pow arg.combo()))
                if (part == 2) {
//                    println("breakpoint")
                }
                when (op.value) {
                    ADV -> a = dv()
                    BXL -> b = (b xor arg.literal().toLong())
                    BST -> b = (arg.combo() % 8)
                    JNZ -> if (a != 0L) {
                        ip = arg.literal()
                        inc = false
                    }

                    BXC -> b = (b xor c)
                    OUT -> output += (arg.combo().mod(8))
                    BDV -> b = dv()
                    CDV -> c = dv()
                    else -> error("Unknown opcode $op")
                }
                if (inc) ip += 2
            }
//            println("final")
//            pa()
            return output
        }

        return when (part) {
            1 -> runProgram()
            2 -> {
                // notes: https://imgur.com/aBzSxC3
                fun attempt(cur: Long, depth: Int): Long {
                    fun search(): Long {
                        var best = Long.MAX_VALUE
                        repeat(8) { n ->
                            val new = (cur shl 3) + n
                            val result = attempt(new, depth + 1)
                            if (result < best) best = result
                        }
                        return best
                    }
                    if (depth == 0) return search()
                    return when (runProgram(cur)) {
                        program -> cur
                        program.takeLast(depth) -> search()
                        else -> Long.MAX_VALUE
                    }
                }

                attempt(0, 0)
            }

            else -> unreachable()
        }
    }

    override fun part1(input: List<String>) = part(1, input)
    override fun part2(input: List<String>) = part(2, input)
}