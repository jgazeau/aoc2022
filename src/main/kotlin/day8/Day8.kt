package day8

import utils.DayInputs

fun main() {
    val inputs = DayInputs(8)
    println("# TEST #")
    inputs.testInput.toForest().findVisibleTrees()
    println("# PUZZLE #")
    inputs.input.toForest().findVisibleTrees()
}

fun List<String>.toForest(): List<List<Int>> = map { it.toList().map { char -> char.digitToInt() } }

fun List<List<Int>>.findVisibleTrees() {
    val lineRange = indices
    val innerLineRange = 1 until size - 1
    val columnRange = 0 until first().size
    val innerColumnRange = 1 until first().size - 1
    val firstResult = (innerColumnRange).sumOf { x ->
        (innerLineRange).count() { y ->
            this[y][x].isVisible(this, x, y)
        }
    } + (2 * size) + (2 * first().size) - 4
    println("First result is: $firstResult")
    val secondResult = (columnRange).map { x ->
        (lineRange).map { y ->
            this[y][x].scenicScore(this, x, y)
        }
    }.flatten()
    println("Second result is: ${secondResult.max()}")
}

fun Int.isVisible(
    forest: List<List<Int>>,
    x: Int,
    y: Int,
): Boolean =
    (0 until x).all { forest[y][it] < this }
            || (x + 1 until forest.first().size).all { forest[y][it] < this }
            || (0 until y).all { forest[it][x] < this }
            || (y + 1 until forest.size).all { forest[it][x] < this }

fun Int.scenicScore(
    forest: List<List<Int>>,
    x: Int,
    y: Int,
): Int =
    (0 until x)
        .dropWhile { i -> i < ((0 until x).lastOrNull { forest[y][it] >= this } ?: Int.MIN_VALUE) }
        .count() * (x + 1 until forest.first().size).takeWhile { i ->
        i <= ((x + 1 until forest.first().size).firstOrNull { forest[y][it] >= this } ?: Int.MAX_VALUE)
    }
        .count() * (0 until y)
        .dropWhile { i -> i < ((0 until y).lastOrNull { forest[it][x] >= this } ?: Int.MIN_VALUE) }
        .count() * (y + 1 until forest.size).takeWhile { i ->
        i <= ((y + 1 until forest.size).firstOrNull { forest[it][x] >= this } ?: Int.MAX_VALUE)
    }
        .count()
