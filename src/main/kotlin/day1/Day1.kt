package day1

import utils.DayInputs

fun main() {
    val inputs = DayInputs(1)
    println("# TEST #")
    inputs.testInput.foldCaloriesPerElf().getMaxCaloriesPerElf().getMaxCaloriesPerElves(3)
    println("# PUZZLE #")
    inputs.input.foldCaloriesPerElf().getMaxCaloriesPerElf().getMaxCaloriesPerElves(3)
}

fun List<String>.foldCaloriesPerElf(): MutableList<Int> {
    val results: MutableList<Int> = mutableListOf()
    this@foldCaloriesPerElf.map { runCatching { it.toInt() }.getOrElse { 0 } }
        .foldIndexed(0) { index: Int, acc: Int, i: Int ->
            takeIf { i == 0 || index == this@foldCaloriesPerElf.lastIndex }?.let { results.add(acc + i) }
            if (i != 0) acc + i else 0
        }
    return results;
}

fun List<Int>.getMaxCaloriesPerElf() = apply { println("Result is: ${max()}") }

fun List<Int>.getMaxCaloriesPerElves(count: Int) =
    apply { println(this.sorted()); println("Result is: ${this.sorted().takeLast(count).sum()}") }
