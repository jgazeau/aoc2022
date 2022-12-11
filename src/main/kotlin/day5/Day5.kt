package day5

import utils.DayInputs

fun main() {
    val inputs = DayInputs(5)
    println("# TEST #")
    inputs.testInput.toCrates().performAction9000()
    inputs.testInput.toCrates().performAction9001()
    println("# PUZZLE #")
    inputs.input.toCrates().performAction9000()
    inputs.input.toCrates().performAction9001()
}

data class Crates(
    val crates: MutableList<MutableList<Char>>,
    val operations: List<List<Int>>,
)

fun List<String>.toCrates(): Crates =
    Crates(this.getCrates(), this.subList(this.indexOf("") + 1, this.size).map { it.toActions() })

fun Crates.performAction9000() {
    operations.forEach { operation ->
        repeat(operation[0]) {
            crates[operation[2] - 1].add(crates[operation[1] - 1].last())
            crates[operation[1] - 1].removeLast()
        }
    }
    val firstResult = crates.map { it.last() }.joinToString("")
    println("First result is: $firstResult")
}

fun Crates.performAction9001() {
    operations.forEach { operation ->
        crates[operation[2] - 1].addAll(
            crates[operation[1] - 1].subList(
                crates[operation[1] - 1].size - operation[0],
                crates[operation[1] - 1].size
            )
        )
        repeat(operation[0]) {
            crates[operation[1] - 1].removeLast()
        }
    }
    val secondResult = crates.map { it.last() }.joinToString("")
    println("Second result is: $secondResult")
}

fun List<String>.getCrates(): MutableList<MutableList<Char>> {
    val transposedTable: MutableList<MutableList<Char>> = mutableListOf()
    this[this.indexOf("") - 1].windowed(4, 4, true).forEachIndexed { index, _ ->
        val columnCrate = mutableListOf<Char>()
        for (i in (this.indexOf("") - 2) downTo 0) {
            this[i].windowed(4, 4, true)[index].toCrate()?.takeIf { columnCrate.add(it) } ?: break
        }
        transposedTable.add(columnCrate)
    }
    return transposedTable
}

fun String.toActions(): List<Int> {
    return Regex("\\d+").findAll(this).toList().map { it.value.toInt() }
}

fun String.toCrate(): Char? = replace(Regex("[\\[|\\]]"), "").trim().firstOrNull()
