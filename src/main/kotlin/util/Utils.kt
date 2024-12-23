package util

import java.math.BigInteger
import java.security.MessageDigest
import kotlin.io.path.Path
import kotlin.io.path.readText
import kotlin.math.pow

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = Path("inputs/$name.txt").readText().trim().lines()

/**
 * Converts string to util.md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')

/**
 * The cleaner shorthand for printing output.
 */
fun <T> T.printlnThis() = also { println(this) }

fun <T> Sequence<T>.cycle() =
    sequence {
        while (true) yieldAll(this@cycle)
    }

infix fun Int.pow(other: Int) = toDouble().pow(other).toInt()
infix fun Long.pow(other: Long) = toDouble().pow(other.toDouble()).toLong()

fun <K, V> Map<K, V>.inverted() =
    entries.groupBy({ it.value }, { it.key })

fun String.splitAt(index: Int) =
    substring(0, index) to substring(index, length)

fun <T> List<T>.splitAt(index: Int) =
    subList(0, index) to subList(index, size)

fun <T, I : Iterable<T>> I.printAll() = onEach(::println)

fun Int.wrappingAdd(other: Int, base: Int) =
    when {
        this + other == 0 -> 0
        this + other > 0 -> (this + other) % base
        else -> base + ((this + other) % base)
    }

fun <T> List<T>.chop(predicate: (T) -> Boolean): List<List<T>> {
    if (isEmpty()) return emptyList()
    val chunks = mutableListOf<List<T>>()
    val cur = mutableListOf<T>()
    for (e in this) {
        if (predicate(e)) {
            chunks += cur.toList()
            cur.clear()
        } else {
            cur += e
        }
    }
    if (cur.isNotEmpty()) chunks += cur.toList()
    return chunks.toList()
}

fun String.flatMap(transform: (Char) -> String) = map(transform).joinToString("")

fun unreachable(): Nothing = error("Shouldn't ever be here.")