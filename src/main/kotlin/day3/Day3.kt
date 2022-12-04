package day3

import utils.DayInputs

fun main() {
    val inputs = DayInputs(3)
    println("# TEST #")
    inputs.testInput.findPriorities()
    println("# PUZZLE #")
    inputs.input.findPriorities()
}

fun Char.findPriority(): Int {
    // Lowercase from 97 to 122
    // Uppercase from 65 to 90
    return when {
        this.isUpperCase() -> this.code - 64 + 26
        else -> this.code - 96
    }
}

fun String.splitInHalf(): List<String> = this.chunked(this.length / 2)

fun List<String>.findCommonItem(): Char = this.foldIndexed(emptyList<Char>()) { index, acc, str ->
    if (index > 0) {
        acc.filter { str.contains(it) }
    } else {
        str.toCharArray().toList()
    }
}.first()

fun List<String>.findPriorities() {
    val firstResult = this.sumOf { it.splitInHalf().findCommonItem().findPriority() }
    println("First result is: $firstResult")
    val secondResult = this.windowed(3, 3).sumOf { it.findCommonItem().findPriority() }
    println("Second result is: $secondResult")
}





