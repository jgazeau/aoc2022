package day6

import utils.DayInputs

fun main() {
    val inputs = DayInputs(6)
    println("# TEST #")
    inputs.testInput.findMarkerIndex()
    println("# PUZZLE #")
    inputs.input.findMarkerIndex()
}

fun List<String>.findMarkerIndex() {
    val firstResult = this[0].windowed(4, 1).withIndex().first { indexedValue ->
        indexedValue.value.toList().groupBy { it }.all { it.value.size == 1 }
    }.let { it.index + 4 }
    println("First result is: $firstResult")
    val secondResult = this[0].windowed(14, 1).withIndex().first { indexedValue ->
        indexedValue.value.toList().groupBy { it }.all { it.value.size == 1 }
    }.let { it.index + 14 }
    println("Second result is: $secondResult")
}

