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
fun <T> T.println() = also { println(this) }

fun <T> Sequence<T>.cycle() =
    sequence {
        while (true) yieldAll(this@cycle)
    }

infix fun Int.pow(other: Int) = toDouble().pow(other).toInt()

fun <K, V> Map<K, V>.inverted() =
    entries.groupBy({ it.value }, { it.key })

fun String.splitAt(index: Int) =
    substring(0, index) to substring(index, length)