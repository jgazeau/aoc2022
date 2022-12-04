package utils

import java.io.File

class DayInputs(dayNumber: Int) {
    val input = File("src/main/kotlin/day$dayNumber/input").readLines()
    val testInput = File("src/main/kotlin/day$dayNumber/testInput").readLines()
}