package days

object Day4 : Day {
    override val name = "Ceres Search"

    private data class Point(val row: Int, val col: Int)
    private data class Dir(val dc: Int, val dr: Int)

    private operator fun Point.plus(dir: Dir) =
        Point(row + dir.dr, col + dir.dc)

    private fun Point.inBounds(rMax: Int, cMax: Int) =
        !(col < 0 || row < 0 || col > cMax || row > rMax)


    override fun part1(input: List<String>): Int {

        fun at(p: Point) = input[p.row][p.col]

        val rMax = input.first().lastIndex
        val cMax = input.lastIndex

        fun test(p: Point, dir: Dir): Boolean {
            var cur = p
            for (letter in charArrayOf('M', 'A', 'S')) {
                cur += dir
                if (!cur.inBounds(rMax, cMax)) return false
                if (at(cur) != letter) return false
            }
            return true
        }

        val directions =
            (-1..1).flatMap { a -> (-1..1).map { b -> Dir(a, b) } } - Dir(0, 0)

        var count = 0
        for (row in 0..cMax) {
            for (col in 0..rMax) {
                val p = Point(row, col)
                if (at(p) == 'X') count += directions.count { test(p, it) }
            }
        }

        return count
    }

    override fun part2(input: List<String>): Int {

        fun at(p: Point) = input[p.row][p.col]

        val cMax = input.lastIndex
        val rMax = input.first().lastIndex

        val directions = listOf(
            Dir(-1, -1) to Dir(1, 1),
            Dir(-1, 1) to Dir(1, -1)
        )

        fun test(p: Point): Boolean {
            for ((d1, d2) in directions) {
                val p1 = p + d1
                val p2 = p + d2

                if (!p1.inBounds(rMax, cMax) || !p2.inBounds(rMax, cMax))
                    return false

                val ch1 = at(p1)
                val ch2 = at(p2)

                // "If either ch1 or ch2 is the letter M, and
                // either ch1 or ch2 is the letter S, then continue"
                if (!((ch1 == 'M' || ch2 == 'M') && (ch1 == 'S' || ch2 == 'S')))
                    return false
            }
            return true
        }

        var count = 0
        for (row in 0..cMax) {
            for (col in 0..rMax) {
                val p = Point(row, col)
                if (at(p) == 'A' && test(p)) count++
            }
        }

        return count
    }
}