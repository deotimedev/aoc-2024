package days

import util.readInput
import kotlin.collections.chunked
import kotlin.collections.first
import kotlin.collections.getOrElse
import kotlin.text.map

object Day9 : Day {
    override val name = "Disk Fragmenter"

    data class Segment(
        val id: Int,
        var space: Int,
        var free: Int
    )

    private fun segments(input: String) =
        input.map { it.digitToInt() }
            .chunked(2)
            .withIndex()
            .map { (i, v) -> Segment(i, v[0], v.getOrElse(1) { 0 }) }

    override fun part1(input: List<String>): Long {
        val segs = segments(input.first())
        val space = segs.sumOf { it.space + it.free }
        val arr = buildList(space) {
            for (seg in segs) {
                repeat(seg.space) { add(seg.id) }
                repeat(seg.free) { add(-1) }
            }
        }.toIntArray()
        var i = 0
        var j = arr.lastIndex
        fun check() = i < arr.size && i < j
        out@ while (check()) {
            while (check() && arr[i] != -1) i++
            while (arr[j] == -1) j--
            if (!check()) break
            arr[i] = arr[j]
            arr[j] = -1
            j--
        }
        var checksum = 0L
        var k = 0
        while (arr[k] != -1) checksum += (k * arr[k++])
        return checksum
    }

    override fun part2(input: List<String>): Long {
        val segs = segments(input.first())
        val space = segs.sumOf { it.space + it.free }
        val queue = ArrayList(segs)
        var j = queue.lastIndex
        while (j > 1) {
            val segA = queue[j]
            val i = queue.indexOfFirst { it.free >= segA.space }
            if (i == -1 || i >= j) {
                j--
                continue
            }
            val segB = queue[i]
            queue.getOrNull(j - 1)?.free += (segA.space + segA.free)
            segA.free = segB.free - segA.space
            segB.free = 0
            queue.removeAt(j)
            queue.add(i + 1, segA)
        }
        val arr = buildList(space) {
            for (seg in queue) {
                repeat(seg.space) { add(seg.id) }
                repeat(seg.free) { add(-1) }
            }
        }.toIntArray()
        var checksum = 0L
        var k = 0
        while (k < arr.size) {
            val value = arr[k]
            if (value != -1) checksum += (k * value)
            k++
        }
        return checksum
    }
}